package com.guimizi.challenge.paymentservice.model.entity.payment;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;


@Entity
public class Payment {

    @Id
    private UUID paymentId;
    private BigDecimal amount;
    private String currency;
    private Integer userId;
    private PaymentStatus status;
    private BigDecimal normalizedAmount;
    private LocalDateTime creationTime;

    @OneToMany(mappedBy = "payment", fetch = FetchType.LAZY)
    private Set<PaymentImputation> paymentImputations;

    protected Payment() {

    }

    public Payment(BigDecimal amount, String currency, Integer userId, BigDecimal normalizedAmount) {
        this.paymentId = UUID.randomUUID();
        this.amount = amount;
        this.currency = currency;
        this.userId = userId;
        this.status = PaymentStatus.NEW;
        this.normalizedAmount = normalizedAmount;
        this.creationTime = LocalDateTime.now();
    }

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

    public Set<PaymentImputation> getPaymentImputations() {
        return paymentImputations;
    }

    public void setPaymentImputations(Set<PaymentImputation> paymentImputations) {
        this.paymentImputations = paymentImputations;
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

    @Override
    public String toString() {
        return "Payment{" +
                "paymentId=" + paymentId +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", userId=" + userId +
                ", status=" + status +
                ", normalizedAmount=" + normalizedAmount +
                ", creationTime=" + creationTime +
                ", paymentImputations=" + paymentImputations +
                '}';
    }
}
