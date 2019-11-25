package com.guimizi.challenge.paymentservice.model.api.invoice;

import com.guimizi.challenge.paymentservice.model.api.charge.ChargeResponse;
import com.guimizi.challenge.paymentservice.model.entity.charge.Charge;
import com.guimizi.challenge.paymentservice.model.entity.invoice.InvoiceId;

import javax.persistence.EmbeddedId;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InvoiceResponse {

    private Integer userId;
    private Integer month;
    private Integer year;
    private BigDecimal total;
    private List<ChargeResponse> charges;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public List<ChargeResponse> getCharges() {
        return charges;
    }

    public void setCharges(List<ChargeResponse> charges) {
        this.charges = charges;
    }
}
