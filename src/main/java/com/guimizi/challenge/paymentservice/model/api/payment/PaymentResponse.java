package com.guimizi.challenge.paymentservice.model.api.payment;

import com.guimizi.challenge.paymentservice.model.entity.payment.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class PaymentResponse {

    private UUID paymentId;
    private BigDecimal amount;
    private String currency;
    private Integer userId;
    private PaymentStatus status;
    private BigDecimal normalizedAmount;
    private LocalDateTime creationTime;

    private List<PaymentImputationResponse> paymentImputations;

    public UUID getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(UUID paymentId) {
        this.paymentId = paymentId;
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

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public BigDecimal getNormalizedAmount() {
        return normalizedAmount;
    }

    public void setNormalizedAmount(BigDecimal normalizedAmount) {
        this.normalizedAmount = normalizedAmount;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public List<PaymentImputationResponse> getPaymentImputations() {
        return paymentImputations;
    }

    public void setPaymentImputations(List<PaymentImputationResponse> paymentImputations) {
        this.paymentImputations = paymentImputations;
    }
}
