package com.guimizi.challenge.paymentservice.model.api.charge;

import org.apache.tomcat.jni.Local;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ChargeEventRequest {

    private Long eventId;
    private BigDecimal amount;
    private String currency;
    private Integer userId;
    private String eventType;
    private LocalDateTime date;

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

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "ChargeEventRequest{" +
                "eventId=" + eventId +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", userId=" + userId +
                ", eventType='" + eventType + '\'' +
                ", date=" + date +
                '}';
    }
}
