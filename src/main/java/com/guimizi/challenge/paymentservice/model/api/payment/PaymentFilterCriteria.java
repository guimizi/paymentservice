package com.guimizi.challenge.paymentservice.model.api.payment;

import com.guimizi.challenge.paymentservice.model.api.FilterCriteria;
import com.guimizi.challenge.paymentservice.model.entity.payment.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PaymentFilterCriteria extends FilterCriteria {

    private Integer userId;
    private String currency;
    private LocalDate from;
    private LocalDate to;
    private BigDecimal amountGte;
    private BigDecimal amountLte;
    private PaymentStatus status;
    private boolean withImputationDetail;


    public PaymentFilterCriteria(Integer userId, Integer pageNumber, String currency, LocalDate from, LocalDate to,
                                 BigDecimal amountGte, BigDecimal amountLte, String status,
                                 boolean withImputationDetail) {
        super(pageNumber);
        this.userId = userId;
        this.currency = currency;
        this.from = from;
        this.to = to;
        this.amountGte = amountGte;
        this.amountLte = amountLte;
        this.status = status != null ? PaymentStatus.valueOf(status): null;
        this.withImputationDetail = withImputationDetail;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getCurrency() {
        return currency;
    }

    public LocalDate getFrom() {
        return from;
    }

    public LocalDate getTo() {
        return to;
    }

    public BigDecimal getAmountGte() {
        return amountGte;
    }

    public BigDecimal getAmountLte() {
        return amountLte;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public boolean getWithImputationDetail() {
        return withImputationDetail;
    }
}
