package com.guimizi.challenge.paymentservice.model.entity.payment;

public enum PaymentStatus {
    NEW(false),
    REJECTED(true),
    ACCEPTED(false),
    PROCESSED(true);

    private boolean finalState;

    PaymentStatus(boolean finalState) {
        this.finalState = finalState;
    }

    public boolean isFinalState() {
        return finalState;
    }
}
