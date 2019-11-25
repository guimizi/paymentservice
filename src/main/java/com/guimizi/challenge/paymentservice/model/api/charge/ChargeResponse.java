package com.guimizi.challenge.paymentservice.model.api.charge;

import com.guimizi.challenge.paymentservice.model.entity.charge.ChargeEventType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChargeResponse {

    private Long eventId;
    private BigDecimal amount;
    private String currency;
    private Integer userId;
    private ChargeEventType eventType;
    private LocalDateTime date;
    private BigDecimal normalizedAmount;
    private BigDecimal remainingToPay;

    private List<ChargeImputedPaymentResponse> imputedPayments;

    public ChargeResponse() {
        this.imputedPayments = new ArrayList<>();
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public ChargeEventType getEventType() {
        return eventType;
    }

    public void setEventType(ChargeEventType eventType) {
        this.eventType = eventType;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public BigDecimal getNormalizedAmount() {
        return normalizedAmount;
    }

    public void setNormalizedAmount(BigDecimal normalizedAmount) {
        this.normalizedAmount = normalizedAmount;
    }

    public BigDecimal getRemainingToPay() {
        return remainingToPay;
    }

    public void setRemainingToPay(BigDecimal remainingToPay) {
        this.remainingToPay = remainingToPay;
    }

    public List<ChargeImputedPaymentResponse> getImputedPayments() {
        return imputedPayments;
    }

    public void setImputedPayments(List<ChargeImputedPaymentResponse> imputedPayments) {
        this.imputedPayments = imputedPayments;
    }
}
