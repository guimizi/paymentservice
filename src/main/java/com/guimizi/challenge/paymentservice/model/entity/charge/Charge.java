package com.guimizi.challenge.paymentservice.model.entity.charge;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.guimizi.challenge.paymentservice.model.entity.invoice.Invoice;
import com.guimizi.challenge.paymentservice.model.entity.payment.PaymentImputation;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class Charge {

    @Id
    private Long eventId;
    private BigDecimal amount;
    private String currency;
    private Integer userId;
    private ChargeEventType eventType;
    private ChargeCategoryType eventCategory;
    private LocalDateTime date;
    private BigDecimal normalizedAmount;
    private BigDecimal remainingToPay;

    @OneToMany(mappedBy = "charge", fetch = FetchType.LAZY)
    private Set<PaymentImputation> paymentImputations;

    /*
    @ManyToOne
    @JoinColumns({
            @JoinColumn(
                    name = "invoice_user_id",
                    referencedColumnName = "user_id"),
            @JoinColumn(
                    name = "month",
                    referencedColumnName = "month"),
            @JoinColumn(
                    name = "year",
                    referencedColumnName = "year")
    })
    private Invoice invoice;
    */
    protected Charge() {

    }

    public Charge(Long eventId, BigDecimal amount, String currency, Integer userId, ChargeEventType eventType,
                  LocalDateTime date, BigDecimal normalizedAmount) {
        this.eventId = eventId;
        this.amount = amount;
        this.currency = currency;
        this.userId = userId;
        this.eventType = eventType;
        this.eventCategory = eventType.getCategory();
        this.date = date;
        this.normalizedAmount = normalizedAmount;
        this.remainingToPay = this.normalizedAmount;
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

    public BigDecimal getRemainingToPay() {
        return remainingToPay;
    }

    public void setRemainingToPay(BigDecimal remainingToPay) {
        this.remainingToPay = remainingToPay;
    }

    public Set<PaymentImputation> getPaymentImputations() {
        return paymentImputations;
    }

    public void setPaymentImputations(Set<PaymentImputation> paymentImputations) {
        this.paymentImputations = paymentImputations;
    }

    public BigDecimal getNormalizedAmount() {
        return normalizedAmount;
    }

    public void setNormalizedAmount(BigDecimal normalizedAmount) {
        this.normalizedAmount = normalizedAmount;
    }

    public ChargeCategoryType getEventCategory() {
        return eventCategory;
    }

    public void setEventCategory(ChargeCategoryType eventCategory) {
        this.eventCategory = eventCategory;
    }

    @Override
    public String toString() {
        return "Charge{" +
                "eventId=" + eventId +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", userId=" + userId +
                ", eventType=" + eventType +
                ", eventCategory=" + eventCategory +
                ", date=" + date +
                ", normalizedAmount=" + normalizedAmount +
                ", remainingToPay=" + remainingToPay +
                ", paymentImputations=" + paymentImputations +
                '}';
    }
}
