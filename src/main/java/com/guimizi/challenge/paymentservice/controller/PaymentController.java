package com.guimizi.challenge.paymentservice.controller;

import com.guimizi.challenge.paymentservice.model.api.ListResponse;
import com.guimizi.challenge.paymentservice.model.api.payment.PaymentFilterCriteria;
import com.guimizi.challenge.paymentservice.model.api.payment.PaymentResponse;
import com.guimizi.challenge.paymentservice.model.entity.payment.Payment;
import com.guimizi.challenge.paymentservice.model.api.payment.PaymentCreationRequest;
import com.guimizi.challenge.paymentservice.service.payment.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@RestController
public class PaymentController {

    private PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/payment")
    public PaymentResponse createPayment(@RequestBody PaymentCreationRequest request) {
        return this.paymentService.processRequest(request);
    }

    @GetMapping("/payment/{paymentId}")
    public PaymentResponse getPayment(@PathVariable UUID paymentId) {
        return this.paymentService.retrievePayment(paymentId);
    }

    @GetMapping("/payments")
    public ListResponse<PaymentResponse> getPaymentsByUser(@RequestParam(required = false) Integer userId,
                                                           @RequestParam(required = false) String currency, @RequestParam(required = false) LocalDate from,
                                                           @RequestParam(required = false) LocalDate to, @RequestParam(required = false) BigDecimal amountGte,
                                                           @RequestParam(required = false) BigDecimal amountLte, @RequestParam(required = false) String status,
                                                           @RequestParam(required = false) boolean withImputationDetail, @RequestParam(required = false) Integer pageNumber) {
        PaymentFilterCriteria filterCriteria = new PaymentFilterCriteria(userId, pageNumber, currency, from, to, amountGte, amountLte, status, withImputationDetail);
        return this.paymentService.retrievePayments(filterCriteria);
    }

}
