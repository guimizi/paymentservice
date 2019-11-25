package com.guimizi.challenge.paymentservice.service.charge;

import com.guimizi.challenge.paymentservice.model.api.ListResponse;
import com.guimizi.challenge.paymentservice.model.api.charge.ChargeEventResponse;
import com.guimizi.challenge.paymentservice.model.api.charge.ChargeFilterCriteria;
import com.guimizi.challenge.paymentservice.model.api.charge.ChargeResponse;
import com.guimizi.challenge.paymentservice.model.api.exception.ApiException;
import com.guimizi.challenge.paymentservice.model.api.exception.ErrorType;
import com.guimizi.challenge.paymentservice.model.entity.charge.Charge;
import com.guimizi.challenge.paymentservice.model.api.charge.ChargeEventRequest;
import com.guimizi.challenge.paymentservice.model.entity.charge.ChargeEventType;
import com.guimizi.challenge.paymentservice.service.common.CurrencyConversionService;
import com.guimizi.challenge.paymentservice.service.charge.transformer.ChargeTransformer;
import com.guimizi.challenge.paymentservice.service.charge.transformer.DetailedChargeTransformer;
import com.guimizi.challenge.paymentservice.service.invoice.InvoiceService;
import com.guimizi.challenge.paymentservice.service.charge.persistence.ChargePersistanceService;
import com.guimizi.challenge.paymentservice.service.common.AbstractTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChargeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChargeService.class);

    private ChargePersistanceService chargePersistanceService;
    private CurrencyConversionService currencyConversionService;
    private InvoiceService invoiceService;

    private ChargeTransformer chargeTransformer;
    private DetailedChargeTransformer detailedChargeTransformer;

    public ChargeService(ChargePersistanceService chargePersistanceService, CurrencyConversionService currencyConversionService, InvoiceService invoiceService, ChargeTransformer chargeTransformer, DetailedChargeTransformer detailedChargeTransformer) {
        this.chargePersistanceService = chargePersistanceService;
        this.currencyConversionService = currencyConversionService;
        this.invoiceService = invoiceService;
        this.chargeTransformer = chargeTransformer;
        this.detailedChargeTransformer = detailedChargeTransformer;
    }

    @Transactional
    public ChargeEventResponse processCharge(ChargeEventRequest request) {
        LOGGER.info("Processing charge request - {}", request);
        this.validate(request);

        BigDecimal normalizedAmount = this.currencyConversionService.convert(request.getAmount(), request.getCurrency());

        Charge charge = new Charge(request.getEventId(), request.getAmount(), request.getCurrency(), request.getUserId(),
                ChargeEventType.valueOf(request.getEventType()), request.getDate(), normalizedAmount);
        this.chargePersistanceService.addCharge(charge);
        this.invoiceService.addCharge(charge);
        return new ChargeEventResponse(charge.getEventId());
    }

    private void validate(ChargeEventRequest request) {
        LOGGER.info("Validating request");
        if(request.getAmount().compareTo(BigDecimal.ZERO) != 1) {
            throw new ApiException(ErrorType.INVALID_AMOUNT_VALUE);
        }
        if(request.getDate().getMonthValue() != LocalDateTime.now().getMonthValue()) {
            throw new ApiException(ErrorType.CHARGE_NOT_ALLOWED);
        }
        try {
            ChargeEventType.valueOf(request.getEventType());
        } catch(Exception e) {
            throw new ApiException(ErrorType.INVALID_EVENT_TYPE);
        }
    }

    @Transactional
    public ChargeResponse retrieveCharge(long eventId) {
        LOGGER.info("Retrieve charge for event_id: {}", eventId);
        Charge charge = this.chargePersistanceService.retrieveCharge(eventId);
        return this.detailedChargeTransformer.transform(charge);
    }

    @Transactional
    public ListResponse<ChargeResponse> retrieveCharges(ChargeFilterCriteria chargeFilterCriteria) {
        LOGGER.info("Retrieve charges using the following criteria: {}", chargeFilterCriteria);
        Slice<Charge> chargeSlice = this.chargePersistanceService.retriveCharges(chargeFilterCriteria);

        AbstractTransformer<Charge, ChargeResponse> transformer = chargeFilterCriteria.getWithPaymentDetail() ? this.detailedChargeTransformer : this.chargeTransformer;
        List<ChargeResponse> chargeResponses = transformer.transformAll(chargeSlice.getContent());
        LOGGER.info("Returning {} charges for criteria: {}", chargeResponses.size(), chargeFilterCriteria);

        return new ListResponse<>(chargeFilterCriteria.getPageNumber(), chargeSlice.hasNext(), chargeResponses);
    }

}
