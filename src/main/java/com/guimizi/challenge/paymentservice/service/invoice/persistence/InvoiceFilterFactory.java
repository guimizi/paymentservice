package com.guimizi.challenge.paymentservice.service.invoice.persistence;

import com.guimizi.challenge.paymentservice.model.api.invoice.InvoicefilterCriteria;
import com.guimizi.challenge.paymentservice.model.entity.invoice.Invoice;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InvoiceFilterFactory {

    public Specification<Invoice> create(InvoicefilterCriteria criteria) {
        List<Specification<Invoice>> filters = new ArrayList<>();
        if(criteria.getUserId() != null) {
            filters.add(belongsToUserId(criteria.getUserId()));
        }
        if(criteria.getFromYear() != null) {
            filters.add(integerFieldGte(criteria.getFromYear(), "year"));
            if(criteria.getFromMonth() != null) {
                filters.add(integerFieldGte(criteria.getFromMonth(), "month"));
            }
        }
        if(criteria.getToYear() != null) {
            filters.add(integerFieldLte(criteria.getToYear(), "year"));
            if(criteria.getToMonth() != null) {
                filters.add(integerFieldLte(criteria.getToMonth(), "month"));
            }
        }
        return filters.stream().reduce((item1, item2) -> item1.and(item2)).orElse(null);
    }

    static Specification<Invoice> belongsToUserId(Integer userId) {
        return (invoice, cq, cb) -> cb.equal(invoice.get("id").get("userId"), userId);
    }

    static Specification<Invoice> integerFieldGte(Integer fieldValue, String field) {
        return (invoice, cq, cb) -> cb.greaterThanOrEqualTo(invoice.get("id").get(field), fieldValue);
    }

    static Specification<Invoice> integerFieldLte(Integer fieldValue, String field) {
        return (invoice, cq, cb) -> cb.lessThanOrEqualTo(invoice.get("id").get(field), fieldValue);
    }

}
