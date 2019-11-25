package com.guimizi.challenge.paymentservice.model.api.charge;

import java.math.BigDecimal;
import java.util.UUID;

public class ChargeImputedPaymentResponse {

    private UUID paymentId;
    private BigDecimal amount;

    public ChargeImputedPaymentResponse(UUID paymentId, BigDecimal amount) {
        this.paymentId = paymentId;
        this.amount = amount;
    }

    public UUID getPaymentId() {
        return paymentId;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
