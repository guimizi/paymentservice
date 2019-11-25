package com.guimizi.challenge.paymentservice.model.api.charge;

import com.guimizi.challenge.paymentservice.model.api.FilterCriteria;
import com.guimizi.challenge.paymentservice.model.entity.charge.ChargeCategoryType;
import com.guimizi.challenge.paymentservice.model.entity.charge.ChargeEventType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Currency;

public class ChargeFilterCriteria extends FilterCriteria {

    private String currency;
    private LocalDate from;
    private LocalDate to;
    private BigDecimal amountGte;
    private BigDecimal amountLte;
    private Boolean withDebtOnly;
    private Boolean withPaymentDetail;
    private Integer userId;
    private ChargeEventType eventType;
    private ChargeCategoryType categoryType;

    public ChargeFilterCriteria(Integer pageNumber, Integer userId, String eventType, String categoryType, String currency, LocalDate from, LocalDate to, BigDecimal amountGte, BigDecimal amountLte, Boolean withDebtOnly, Boolean withPaymentDetail) {
        super(pageNumber);
        this.userId = userId;
        this.currency = currency;
        this.from = from;
        this.to = to;
        this.amountGte = amountGte;
        this.amountLte = amountLte;
        this.withDebtOnly = withDebtOnly;
        this.withPaymentDetail = withPaymentDetail;
        this.eventType = ChargeEventType.valueOf(eventType);
        this.categoryType = ChargeCategoryType.valueOf(categoryType);
    }

    public String getCurrency() {
        return currency;
    }

    public LocalDate getFrom() {
        return from;
    }

    public LocalDate getTo() {
        return to;
    }

    public BigDecimal getAmountGte() {
        return amountGte;
    }

    public BigDecimal getAmountLte() {
        return amountLte;
    }

    public Boolean getWithDebtOnly() {
        return withDebtOnly;
    }

    public Boolean getWithPaymentDetail() {
        return withPaymentDetail;
    }

    public Integer getUserId() {
        return userId;
    }

    public ChargeEventType getEventType() {
        return eventType;
    }

    public ChargeCategoryType getCategoryType() {
        return categoryType;
    }

    @Override
    public String toString() {
        return "ChargeFilterCriteria{" +
                "currency='" + currency + '\'' +
                ", from=" + from +
                ", to=" + to +
                ", amountGte=" + amountGte +
                ", amountLte=" + amountLte +
                ", withDebtOnly=" + withDebtOnly +
                ", withPaymentDetail=" + withPaymentDetail +
                ", userId=" + userId +
                ", eventType=" + eventType +
                ", categoryType=" + categoryType +
                '}';
    }
}
