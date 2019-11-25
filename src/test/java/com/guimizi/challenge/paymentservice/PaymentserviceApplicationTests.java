package com.guimizi.challenge.paymentservice;

import antlr.collections.impl.IntRange;
import com.guimizi.challenge.paymentservice.model.api.charge.ChargeEventRequest;
import com.guimizi.challenge.paymentservice.model.api.charge.ChargeResponse;
import com.guimizi.challenge.paymentservice.model.api.invoice.InvoiceResponse;
import com.guimizi.challenge.paymentservice.model.api.payment.PaymentCreationRequest;
import com.guimizi.challenge.paymentservice.model.api.payment.PaymentResponse;
import com.guimizi.challenge.paymentservice.model.api.user.UserStatusResponse;
import com.guimizi.challenge.paymentservice.model.entity.accum.UserStatus;
import com.guimizi.challenge.paymentservice.model.entity.charge.ChargeEventType;
import com.guimizi.challenge.paymentservice.model.entity.payment.Payment;
import com.guimizi.challenge.paymentservice.model.entity.payment.PaymentStatus;
import com.guimizi.challenge.paymentservice.service.charge.ChargeService;
import com.guimizi.challenge.paymentservice.service.common.CurrencyConversionService;
import com.guimizi.challenge.paymentservice.service.invoice.InvoiceService;
import com.guimizi.challenge.paymentservice.service.payment.PaymentService;
import com.guimizi.challenge.paymentservice.service.user.UserStatusService;
import org.assertj.core.util.DateUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalUnit;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

@SpringBootTest
class PaymentserviceApplicationTests {

	@Autowired
	private ChargeService chargeService;
	@Autowired
	private PaymentService paymentService;
	@Autowired
	private InvoiceService invoiceService;
	@Autowired
	private UserStatusService userStatusService;
	@Autowired
	private CurrencyConversionService currencyConversionService;

	@Test
	public void contextLoads() {
	}

	@Test
	public void testPaymentChargeBalance() {
		//Process charge
		ChargeEventRequest chargeEventRequest1 = createChangeEventRequest(1L, 1, new BigDecimal(100), "USD", LocalDateTime.now(), ChargeEventType.CREDITO);
		chargeService.processCharge(chargeEventRequest1);

		ChargeEventRequest chargeEventRequest2 = createChangeEventRequest(2L, 1, new BigDecimal(1000), "ARS", LocalDateTime.now(), ChargeEventType.CLASIFICADO);
		chargeService.processCharge(chargeEventRequest2);

		ChargeResponse chargeResponse = chargeService.retrieveCharge(chargeEventRequest1.getEventId());
		Assertions.assertEquals(chargeEventRequest1.getEventId(), chargeResponse.getEventId());

		//---Process Payment----

		//Partial payment
		Payment payment1 = createPayment(1, new BigDecimal("90"), "USD");
		paymentService.processPayment(payment1);

		PaymentResponse partialPaymentResponse = paymentService.retrievePayment(payment1.getPaymentId());
		Assertions.assertEquals(PaymentStatus.PROCESSED, partialPaymentResponse.getStatus());
		Assertions.assertEquals(1, partialPaymentResponse.getPaymentImputations().size());
		Assertions.assertEquals(chargeEventRequest1.getEventId(), partialPaymentResponse.getPaymentImputations().get(0).getChargeId());

		//Payment assigned to two charges
		Payment payment2 = createPayment(1, new BigDecimal("1100"), "ARS");
		paymentService.processPayment(payment2);

		PaymentResponse partialPaymentResponse2 = paymentService.retrievePayment(payment2.getPaymentId());
		Assertions.assertEquals(PaymentStatus.PROCESSED, partialPaymentResponse2.getStatus());
		Assertions.assertEquals(2, partialPaymentResponse2.getPaymentImputations().size());
		Assertions.assertTrue(partialPaymentResponse2.getPaymentImputations().stream().anyMatch(p -> p.getChargeId().equals(chargeEventRequest1.getEventId())));
		Assertions.assertTrue(partialPaymentResponse2.getPaymentImputations().stream().anyMatch(p -> p.getChargeId().equals(chargeEventRequest2.getEventId())));


		//Exceeded payment by 1 cent
		UserStatusResponse userStatus = userStatusService.retrieveUserStatus(1);

		Payment payment3 = createPayment(1, userStatus.getBalance().multiply(new BigDecimal("-1")).add(new BigDecimal("0.01")), "ARS");
		paymentService.processPayment(payment3);

		PaymentResponse paymentResponse3 = paymentService.retrievePayment(payment3.getPaymentId());
		Assertions.assertEquals(PaymentStatus.REJECTED, paymentResponse3.getStatus());

		//Exact payment
		Payment payment4 = createPayment(1, userStatus.getBalance().multiply(new BigDecimal("-1")), "ARS");
		paymentService.processPayment(payment4);
		PaymentResponse paymentResponse4 = paymentService.retrievePayment(payment4.getPaymentId());
		Assertions.assertEquals(PaymentStatus.PROCESSED, paymentResponse4.getStatus());

	}

	@Test
	public void testInvoice() {
		IntStream.range(10, 15).forEach(userId -> {
			IntStream.range(1, 5).forEach(item -> {
				ChargeEventRequest chargeEventRequest1 = createChangeEventRequest(new Long(item + (userId * 10)), userId, new BigDecimal("100").multiply(new BigDecimal(item)), "ARS", LocalDateTime.now(), ChargeEventType.values()[item]);
				chargeService.processCharge(chargeEventRequest1);
			});
		});
		IntStream.range(10, 15).forEach(userId -> {
			int year = LocalDate.now().getYear();
			int month = LocalDate.now().getMonthValue();
			InvoiceResponse invoiceResponse = invoiceService.retrieveInvoice(userId, month, year);
			Assertions.assertEquals(new BigDecimal("1000.00"),invoiceResponse.getTotal());
			Assertions.assertEquals(4, invoiceResponse.getCharges().size());
		});
	}



	private Payment createPayment(Integer userId, BigDecimal amount, String currency) {
		BigDecimal normalizedAmount = this.currencyConversionService.convert(amount, currency);
		return new Payment(amount, currency, userId, normalizedAmount);
	}

	private ChargeEventRequest createChangeEventRequest(Long id, Integer userId, BigDecimal amount,
									String currency, LocalDateTime localDateTime, ChargeEventType eventType) {
		ChargeEventRequest chargeEventRequest = new ChargeEventRequest();
		chargeEventRequest.setEventId(id);
		chargeEventRequest.setUserId(userId);
		chargeEventRequest.setAmount(amount);
		chargeEventRequest.setCurrency(currency);
		chargeEventRequest.setDate(localDateTime);
		chargeEventRequest.setEventType(eventType.name());
		return chargeEventRequest;
	}

}
