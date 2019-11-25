package com.guimizi.challenge.paymentservice.model.api.invoice;

import com.guimizi.challenge.paymentservice.model.api.FilterCriteria;

public class InvoicefilterCriteria extends FilterCriteria {

    private Integer userId;
    private Integer fromMonth;
    private Integer fromYear;
    private Integer toMonth;
    private Integer toYear;

    public InvoicefilterCriteria(Integer pageNumber, Integer userId, Integer fromMonth, Integer fromYear, Integer toMonth, Integer toYear) {
        super(pageNumber);
        this.userId = userId;
        this.fromMonth = fromMonth;
        this.fromYear = fromYear;
        this.toMonth = toMonth;
        this.toYear = toYear;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getFromMonth() {
        return fromMonth;
    }

    public Integer getFromYear() {
        return fromYear;
    }

    public Integer getToMonth() {
        return toMonth;
    }

    public Integer getToYear() {
        return toYear;
    }

    @Override
    public String toString() {
        return "InvoicefilterCriteria{" +
                "userId=" + userId +
                ", fromMonth=" + fromMonth +
                ", fromYear=" + fromYear +
                ", toMonth=" + toMonth +
                ", toYear=" + toYear +
                '}';
    }
}
