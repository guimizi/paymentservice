package com.guimizi.challenge.paymentservice.model.api;

public class FilterCriteria {

    private Integer pageNumber;

    public FilterCriteria(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }
}
