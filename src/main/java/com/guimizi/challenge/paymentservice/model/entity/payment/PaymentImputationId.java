package com.guimizi.challenge.paymentservice.model.entity.payment;

import com.google.common.base.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
public class PaymentImputationId implements Serializable {


    @Column(name = "payemnt_id")
    private UUID paymentId;
    @Column(name = "charge_id")
    private Long chargeId;

    protected PaymentImputationId() {

    }

    public PaymentImputationId(UUID paymentId, Long chargeId) {
        this.paymentId = paymentId;
        this.chargeId = chargeId;
    }

    public UUID getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(UUID paymentId) {
        this.paymentId = paymentId;
    }

    public Long getChargeId() {
        return chargeId;
    }

    public void setChargeId(Long chargeId) {
        this.chargeId = chargeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PaymentImputationId)) return false;
        PaymentImputationId that = (PaymentImputationId) o;
        return Objects.equal(paymentId, that.paymentId) &&
                Objects.equal(chargeId, that.chargeId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(paymentId, chargeId);
    }
}
