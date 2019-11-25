package com.guimizi.challenge.paymentservice.service.payment;

import com.guimizi.challenge.paymentservice.model.api.ListResponse;
import com.guimizi.challenge.paymentservice.model.api.exception.ApiException;
import com.guimizi.challenge.paymentservice.model.api.exception.ErrorType;
import com.guimizi.challenge.paymentservice.model.api.payment.PaymentFilterCriteria;
import com.guimizi.challenge.paymentservice.model.api.payment.PaymentResponse;
import com.guimizi.challenge.paymentservice.model.entity.payment.Payment;
import com.guimizi.challenge.paymentservice.model.api.payment.PaymentCreationRequest;
import com.guimizi.challenge.paymentservice.service.common.CurrencyConversionService;
import com.guimizi.challenge.paymentservice.service.payment.persistence.PaymentPersistanceService;
import com.guimizi.challenge.paymentservice.service.payment.transformer.DetailedPaymentResponseTransformer;
import com.guimizi.challenge.paymentservice.service.payment.transformer.PaymentResponseTransformer;
import com.guimizi.challenge.paymentservice.service.common.AbstractTransformer;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class PaymentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentService.class);

    private MessageChannel producingChannel;
    private PaymentPersistanceService paymentPersistanceService;
    private CurrencyConversionService currencyConversionService;
    private PaymentImputationService paymentImputationService;
    private PaymentResponseTransformer paymentResponseTransformer;
    private DetailedPaymentResponseTransformer detailedPaymentResponseTransformer;

    @Autowired
    public PaymentService(MessageChannel producingChannel, PaymentPersistanceService paymentPersistanceService,
                          CurrencyConversionService currencyConversionService, PaymentImputationService paymentImputationService,
                          PaymentResponseTransformer paymentResponseTransformer, DetailedPaymentResponseTransformer detailedPaymentResponseTransformer) {
        this.producingChannel = producingChannel;
        this.paymentPersistanceService = paymentPersistanceService;
        this.currencyConversionService = currencyConversionService;
        this.paymentImputationService = paymentImputationService;
        this.paymentResponseTransformer = paymentResponseTransformer;
        this.detailedPaymentResponseTransformer = detailedPaymentResponseTransformer;
    }

    public PaymentResponse processRequest(PaymentCreationRequest request) {
        LOGGER.info("Processing payment creation request: {}", request);
        validateRequest(request);
        BigDecimal normalizedAmount = this.currencyConversionService.convert(request.getAmount(), request.getCurrency());

        Payment payment = new Payment(request.getAmount(), request.getCurrency(), request.getUserId(), normalizedAmount);
        LOGGER.info("Adding created payment to queue for further processing - payment: {}", payment);
        this.producingChannel.send(MessageBuilder.withPayload(payment).build());

        return this.paymentResponseTransformer.transform(payment);
    }

    private void validateRequest(PaymentCreationRequest request) {
        LOGGER.info("Validating request");
        if(request.getAmount().compareTo(BigDecimal.ZERO) != 1) {
            throw new ApiException(ErrorType.INVALID_AMOUNT_VALUE);
        }
    }

    public void processPayment(Payment payment) {
        LOGGER.info("Processing incoming payment: {}", payment);
        this.paymentPersistanceService.createPayment(payment);
        this.paymentPersistanceService.acceptPayment(payment);
        this.paymentImputationService.applyPayment(payment);
        this.paymentPersistanceService.finalizePaymentProcessing(payment);
    }

    @Transactional
    public PaymentResponse retrievePayment(UUID paymentId) {
        LOGGER.info("Retrieving payment for id: {}", paymentId);
        Payment payment =  this.paymentPersistanceService.findPayment(paymentId).orElseThrow(() -> new RuntimeException("not found"));
        return this.detailedPaymentResponseTransformer.transform(payment);
    }

    @Transactional
    public ListResponse<PaymentResponse> retrievePayments(PaymentFilterCriteria filterCriteria) {
        LOGGER.info("Retrieve payments using the following criteria: {}", filterCriteria);

        Slice<Payment> paymentSlice = this.paymentPersistanceService.retrievePayments(filterCriteria);
        AbstractTransformer<Payment, PaymentResponse> transformer = filterCriteria.getWithImputationDetail()
                ? this.detailedPaymentResponseTransformer : this.paymentResponseTransformer;
        List<PaymentResponse> paymentResponses = transformer.transformAll(paymentSlice.getContent());
        LOGGER.info("Returning {} payments for criteria: {}", paymentResponses.size(), filterCriteria);

        return new ListResponse<>(filterCriteria.getPageNumber(), paymentSlice.hasNext(), paymentResponses);
    }
}
