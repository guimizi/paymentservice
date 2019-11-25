package com.guimizi.challenge.paymentservice.model.api.payment;

import java.math.BigDecimal;

public class PaymentCreationRequest {

    private BigDecimal amount;
    private String currency;
    private Integer userId;

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

    @Override
    public String toString() {
        return "PaymentCreationRequest{" +
                "amount=" + amount +
                ", currency='" + currency + '\'' +
                ", userId=" + userId +
                '}';
    }
}
