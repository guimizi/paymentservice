package com.guimizi.challenge.paymentservice.service.payment.persistence;

import com.guimizi.challenge.paymentservice.model.api.charge.ChargeFilterCriteria;
import com.guimizi.challenge.paymentservice.model.api.payment.PaymentFilterCriteria;
import com.guimizi.challenge.paymentservice.model.entity.charge.Charge;
import com.guimizi.challenge.paymentservice.model.entity.payment.Payment;
import com.guimizi.challenge.paymentservice.model.entity.payment.PaymentStatus;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class PaymentFilterFactory {

    public Specification<Payment> create(PaymentFilterCriteria criteria) {
        List<Specification<Payment>> filters = new ArrayList<>();

        if(criteria.getUserId() != null) {
            filters.add(integerFieldEquals(criteria.getUserId(), "userId"));
        }

        if(criteria.getAmountGte() != null) {
            filters.add(bigDecimalFieldGte(criteria.getAmountGte(), "normalizedAmount"));
        }
        if(criteria.getAmountLte() != null) {
            filters.add(bigDecimalFieldLte(criteria.getAmountLte(), "normalizedAmount"));
        }
        if(criteria.getCurrency() != null) {
            filters.add(stringFieldEquals(criteria.getCurrency(), "currency"));
        }
        if(criteria.getFrom() != null) {
            filters.add(dateFieldGte(criteria.getFrom(), "creationTime"));
        }
        if(criteria.getTo() != null) {
            filters.add(dateFieldLte(criteria.getTo(), "creationTime"));
        }
        if(criteria.getStatus() != null) {
            filters.add(statusFieldEquals(criteria.getStatus()));
        }

        return filters.stream().reduce((item1, item2) -> item1.and(item2)).orElse(null);
    }

    static Specification<Payment> bigDecimalFieldGt(BigDecimal fieldValue, String field) {
        return (payment, cq, cb) -> cb.greaterThan(payment.get(field), fieldValue);
    }

    static Specification<Payment> bigDecimalFieldGte(BigDecimal fieldValue, String field) {
        return (payment, cq, cb) -> cb.greaterThanOrEqualTo(payment.get(field), fieldValue);
    }

    static Specification<Payment> bigDecimalFieldLte(BigDecimal fieldValue, String field) {
        return (payment, cq, cb) -> cb.lessThanOrEqualTo(payment.get(field), fieldValue);
    }

    static Specification<Payment> stringFieldEquals(String fieldValue, String field) {
        return (payment, cq, cb) -> cb.equal(payment.get(field), fieldValue);
    }

    static Specification<Payment> statusFieldEquals(PaymentStatus fieldValue) {
        return (payment, cq, cb) -> cb.equal(payment.get("status"), fieldValue);
    }

    static Specification<Payment> integerFieldEquals(Integer fieldValue, String field) {
        return (payment, cq, cb) -> cb.equal(payment.get(field), fieldValue);
    }

    static Specification<Payment> dateFieldGte(LocalDate fieldValue, String field) {
        return (payment, cq, cb) -> cb.greaterThanOrEqualTo(payment.get(field), fieldValue);
    }

    static Specification<Payment> dateFieldLte(LocalDate fieldValue, String field) {
        return (payment, cq, cb) -> cb.lessThanOrEqualTo(payment.get(field), fieldValue);
    }
}
