package com.guimizi.challenge.paymentservice.model.api.payment;

import java.math.BigDecimal;

public class PaymentImputationResponse {

    private Long chargeId;
    private BigDecimal amount;

    public PaymentImputationResponse(Long chargeId, BigDecimal amount) {
        this.chargeId = chargeId;
        this.amount = amount;
    }

    public Long getChargeId() {
        return chargeId;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
