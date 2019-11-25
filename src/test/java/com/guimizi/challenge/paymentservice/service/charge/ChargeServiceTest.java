package com.guimizi.challenge.paymentservice.service.charge;

import com.guimizi.challenge.paymentservice.model.api.ListResponse;
import com.guimizi.challenge.paymentservice.model.api.charge.ChargeEventRequest;
import com.guimizi.challenge.paymentservice.model.api.charge.ChargeEventResponse;
import com.guimizi.challenge.paymentservice.model.api.charge.ChargeFilterCriteria;
import com.guimizi.challenge.paymentservice.model.api.charge.ChargeResponse;
import com.guimizi.challenge.paymentservice.model.api.exception.ApiException;
import com.guimizi.challenge.paymentservice.model.api.exception.ErrorType;
import com.guimizi.challenge.paymentservice.model.entity.charge.Charge;
import com.guimizi.challenge.paymentservice.model.entity.charge.ChargeEventType;
import com.guimizi.challenge.paymentservice.service.charge.persistence.ChargePersistanceService;
import com.guimizi.challenge.paymentservice.service.charge.transformer.ChargeTransformer;
import com.guimizi.challenge.paymentservice.service.charge.transformer.DetailedChargeTransformer;
import com.guimizi.challenge.paymentservice.service.common.CurrencyConversionService;
import com.guimizi.challenge.paymentservice.service.invoice.InvoiceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Slice;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class ChargeServiceTest {

    @InjectMocks
    private ChargeService chargeService;
    @Mock
    private ChargePersistanceService chargePersistanceService;
    @Mock
    private CurrencyConversionService currencyConversionService;
    @Mock
    private InvoiceService invoiceService;
    @Mock
    private ChargeTransformer chargeTransformer;
    @Mock
    private DetailedChargeTransformer detailedChargeTransformer;
    @Mock
    private ChargeEventRequest request;
    @Mock
    private Charge charge;
    @Mock
    private ChargeResponse chargeResponse;
    @Mock
    private ChargeFilterCriteria criteria;
    @Mock
    private Slice<Charge> chargeSlice;
    @Mock
    private List<Charge> charges;
    @Mock
    private List<ChargeResponse> chargeResponses;

    @Test
    public void testProcessChargeNotAllowed() {
        LocalDateTime monthBefore = LocalDateTime.now().minus(1, ChronoUnit.MONTHS);
        given(request.getDate()).willReturn(monthBefore);
        ApiException apiException = assertThrows(ApiException.class, () -> {
            this.chargeService.processCharge(request);
        });
        assertEquals(ErrorType.CHARGE_NOT_ALLOWED.getErrorCode(), apiException.getErrorCode());
    }

    @Test
    public void testProcessChargeInvalidEventType() {
        given(request.getDate()).willReturn(LocalDateTime.now());
        given(request.getEventType()).willReturn("invalid_type");
        ApiException apiException = assertThrows(ApiException.class, () -> {
            this.chargeService.processCharge(request);
        });
        assertEquals(ErrorType.INVALID_EVENT_TYPE.getErrorCode(), apiException.getErrorCode());
    }

    @Test
    public void testProcessCharge() {
        long eventId = 1L;
        BigDecimal amount = new BigDecimal("10.1");
        BigDecimal normalizedAmount = new BigDecimal("622");
        String currency = "USD";
        Integer userId = 1;
        LocalDateTime date = LocalDateTime.now();
        given(request.getDate()).willReturn(date);
        given(request.getEventType()).willReturn(ChargeEventType.CLASIFICADO.name());
        given(request.getEventId()).willReturn(eventId);
        given(request.getCurrency()).willReturn(currency);
        given(request.getAmount()).willReturn(amount);
        given(request.getUserId()).willReturn(userId);

        when(this.currencyConversionService.convert(amount, currency)).thenReturn(normalizedAmount);

        ChargeEventResponse response = this.chargeService.processCharge(request);
        assertEquals(eventId, response.getEventId());

        ArgumentCaptor<Charge> chargeArgumentCaptor = ArgumentCaptor.forClass(Charge.class);
        verify(this.chargePersistanceService).addCharge(chargeArgumentCaptor.capture());
        verify(this.invoiceService).addCharge(chargeArgumentCaptor.capture());

        Charge charge1 = chargeArgumentCaptor.getAllValues().get(0);
        Charge charge2 = chargeArgumentCaptor.getAllValues().get(1);

        assertTrue(charge1 == charge2, "should be same reference");

        assertEquals(eventId, charge1.getEventId());
        assertEquals(userId, charge1.getUserId());
        assertEquals(date, charge1.getDate());
        assertEquals(normalizedAmount, charge1.getNormalizedAmount());
        assertEquals(normalizedAmount, charge1.getRemainingToPay());
        assertEquals(amount, charge1.getAmount());
        assertEquals(currency, charge1.getCurrency());
        assertEquals(ChargeEventType.CLASIFICADO, charge1.getEventType());
        assertEquals(ChargeEventType.CLASIFICADO.getCategory(), charge1.getEventCategory());
    }

    @Test
    public void testRetrieveCharge() {
        long eventId = 1L;

        when(this.chargePersistanceService.retrieveCharge(eventId)).thenReturn(charge);
        when(this.detailedChargeTransformer.transform(charge)).thenReturn(chargeResponse);
        ChargeResponse response = this.chargeService.retrieveCharge(eventId);
        assertTrue(response == chargeResponse,"should be same reference");
    }

    @Test
    public void testRetrieveChargesWithNoPaymentDetail() {
        int resultsSize = 20;
        int pageNumber = 2;
        given(criteria.getWithPaymentDetail()).willReturn(false);
        given(criteria.getPageNumber()).willReturn(pageNumber);
        given(chargeSlice.getContent()).willReturn(charges);
        given(chargeSlice.hasNext()).willReturn(true);
        given(chargeResponses.size()).willReturn(resultsSize);
        when(this.chargePersistanceService.retriveCharges(criteria)).thenReturn(chargeSlice);
        when(this.chargeTransformer.transformAll(charges)).thenReturn(chargeResponses);

        ListResponse<ChargeResponse> listResponse = this.chargeService.retrieveCharges(criteria);

        assertTrue(chargeResponses == listResponse.getList(),"should be same reference");
        assertTrue(listResponse.getHasNext());
        assertEquals(resultsSize, listResponse.getSize());
        assertEquals(pageNumber, listResponse.getPage());

        verify(this.detailedChargeTransformer, never()).transform(any());
    }

    @Test
    public void testRetrieveChargesWithPaymentDetail() {
        int resultsSize = 20;
        int pageNumber = 2;
        given(criteria.getWithPaymentDetail()).willReturn(true);
        given(criteria.getPageNumber()).willReturn(pageNumber);
        given(chargeSlice.getContent()).willReturn(charges);
        given(chargeSlice.hasNext()).willReturn(true);
        given(chargeResponses.size()).willReturn(resultsSize);
        when(this.chargePersistanceService.retriveCharges(criteria)).thenReturn(chargeSlice);
        when(this.detailedChargeTransformer.transformAll(charges)).thenReturn(chargeResponses);

        ListResponse<ChargeResponse> listResponse = this.chargeService.retrieveCharges(criteria);

        assertTrue(chargeResponses == listResponse.getList(),"should be same reference");
        assertTrue(listResponse.getHasNext());
        assertEquals(resultsSize, listResponse.getSize());
        assertEquals(pageNumber, listResponse.getPage());

        verify(this.chargeTransformer, never()).transform(any());
    }

}
