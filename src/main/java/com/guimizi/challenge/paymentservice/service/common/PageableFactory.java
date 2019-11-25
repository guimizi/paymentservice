package com.guimizi.challenge.paymentservice.service.common;

import com.guimizi.challenge.paymentservice.model.api.FilterCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class PageableFactory {

    private Integer defaultPageSize;

    @Autowired
    public PageableFactory(@Value("${response.default-page-size}") Integer defaultPageSize) {
        this.defaultPageSize = defaultPageSize;
    }

    public Pageable create(FilterCriteria criteria) {
        if(criteria.getPageNumber() != null) {
            return PageRequest.of(criteria.getPageNumber(), defaultPageSize);
        }
        return Pageable.unpaged();
    }

}
