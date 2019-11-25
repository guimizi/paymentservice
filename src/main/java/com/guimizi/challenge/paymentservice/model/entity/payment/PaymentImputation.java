package com.guimizi.challenge.paymentservice.model.entity.payment;

import com.guimizi.challenge.paymentservice.model.entity.charge.Charge;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
public class PaymentImputation {

    //@EmbeddedId
    //private PaymentImputationId id;

    @Id
    private UUID id;

    @ManyToOne
    //@MapsId("payment_id")
    //@JoinColumn(name = "payment_id")
    @JoinColumn(name = "paymentId", referencedColumnName = "paymentId")
    private Payment payment;

    @ManyToOne
//    @MapsId("event_id")
//    @JoinColumn(name = "event_id")
    @JoinColumn(name = "eventId", referencedColumnName = "eventId")
    private Charge charge;

    private BigDecimal amount;

    protected PaymentImputation() {

    }

    public PaymentImputation(Payment payment, Charge charge, BigDecimal amount) {
        this.id = UUID.randomUUID();
        this.payment = payment;
        this.charge = charge;
        this.amount = amount;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Charge getCharge() {
        return charge;
    }

    public void setCharge(Charge charge) {
        this.charge = charge;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
