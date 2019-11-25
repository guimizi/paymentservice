package com.guimizi.challenge.paymentservice.model.entity.invoice;

import com.google.common.base.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class InvoiceId implements Serializable {


    @Column(name = "user_id")
    private Integer userId;
    private Integer month;
    private Integer year;

    protected InvoiceId() {

    }

    public InvoiceId(Integer userId, Integer month, Integer year) {
        this.userId = userId;
        this.month = month;
        this.year = year;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InvoiceId)) return false;
        InvoiceId invoiceId = (InvoiceId) o;
        return Objects.equal(userId, invoiceId.userId) &&
                Objects.equal(month, invoiceId.month) &&
                Objects.equal(year, invoiceId.year);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userId, month, year);
    }

    @Override
    public String toString() {
        return "InvoiceId{" +
                "userId=" + userId +
                ", month=" + month +
                ", year=" + year +
                '}';
    }
}
