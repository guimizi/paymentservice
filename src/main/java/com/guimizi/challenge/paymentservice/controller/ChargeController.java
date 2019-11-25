package com.guimizi.challenge.paymentservice.controller;

import com.guimizi.challenge.paymentservice.model.api.ListResponse;
import com.guimizi.challenge.paymentservice.model.api.charge.ChargeEventResponse;
import com.guimizi.challenge.paymentservice.model.api.charge.ChargeFilterCriteria;
import com.guimizi.challenge.paymentservice.model.api.charge.ChargeResponse;
import com.guimizi.challenge.paymentservice.model.api.charge.ChargeEventRequest;
import com.guimizi.challenge.paymentservice.service.charge.ChargeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@RestController
public class ChargeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChargeController.class);

    private ChargeService chargeService;

    @Autowired
    public ChargeController(ChargeService chargeService) {
        this.chargeService = chargeService;
    }

    @PostMapping("/charge")
    public ChargeEventResponse processCharge(@RequestBody ChargeEventRequest chargeEventRequest) {
        return this.chargeService.processCharge(chargeEventRequest);
    }

    @GetMapping("/charge/{eventId}")
    public ChargeResponse getCharge(@PathVariable(value = "eventId") int eventId) {
        return this.chargeService.retrieveCharge(eventId);
    }

    @GetMapping("/charges")
    public ListResponse<ChargeResponse> getCharges(@RequestParam(required = false) Integer userId,
                                                   @RequestParam(required = false) String eventType, @RequestParam(required = false) String eventCategory,
                                                   @RequestParam(required = false) String currency, @RequestParam(required = false) LocalDate from,
                                                   @RequestParam(required = false) LocalDate to, @RequestParam(required = false) BigDecimal amountGte,
                                                   @RequestParam(required = false) BigDecimal amountLte, @RequestParam(required = false) Boolean withDebtOnly,
                                                   @RequestParam(required = false) Boolean withPaymentDetail, @RequestParam(required = false) Integer pageNumber) {
        ChargeFilterCriteria chargeFilterCriteria = new ChargeFilterCriteria(pageNumber, userId, eventType, eventCategory, currency, from, to, amountGte, amountLte, withDebtOnly, withPaymentDetail);
        return this.chargeService.retrieveCharges(chargeFilterCriteria);
    }

}
