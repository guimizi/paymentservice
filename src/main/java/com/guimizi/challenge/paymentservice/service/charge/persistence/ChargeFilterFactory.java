package com.guimizi.challenge.paymentservice.service.charge.persistence;

import com.guimizi.challenge.paymentservice.model.api.charge.ChargeFilterCriteria;
import com.guimizi.challenge.paymentservice.model.entity.charge.Charge;
import com.guimizi.challenge.paymentservice.model.entity.charge.ChargeCategoryType;
import com.guimizi.challenge.paymentservice.model.entity.charge.ChargeEventType;
import com.guimizi.challenge.paymentservice.model.entity.invoice.Invoice;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class ChargeFilterFactory {

    public Specification<Charge> create(ChargeFilterCriteria criteria) {
        List<Specification<Charge>> filters = new ArrayList<>();
        if(criteria.getUserId() != null) {
            filters.add(integerFieldEquals(criteria.getUserId(), "userId"));
        }

        //busca por categoria si no esta filtrado por event type
        if(criteria.getEventType() != null) {
            filters.add(eventTypeEquals(criteria.getEventType()));
        } else if(criteria.getCategoryType() != null) {
            filters.add(eventCategoryEquals(criteria.getCategoryType()));
        }

        if(Boolean.TRUE.equals(criteria.getWithDebtOnly() != null)) {
            filters.add(bigDecimalFieldGt(BigDecimal.ZERO, "remainingToPay"));
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
            filters.add(dateFieldGte(criteria.getFrom(), "date"));
        }
        if(criteria.getTo() != null) {
            filters.add(dateFieldLte(criteria.getTo(), "date"));
        }

        return filters.stream().reduce((item1, item2) -> item1.and(item2)).orElse(null);
    }

    static Specification<Charge> bigDecimalFieldGt(BigDecimal fieldValue, String field) {
        return (charge, cq, cb) -> cb.greaterThan(charge.get(field), fieldValue);
    }

    static Specification<Charge> bigDecimalFieldGte(BigDecimal fieldValue, String field) {
        return (charge, cq, cb) -> cb.greaterThanOrEqualTo(charge.get(field), fieldValue);
    }

    static Specification<Charge> bigDecimalFieldLte(BigDecimal fieldValue, String field) {
        return (charge, cq, cb) -> cb.lessThanOrEqualTo(charge.get(field), fieldValue);
    }

    static Specification<Charge> stringFieldEquals(String fieldValue, String field) {
        return (charge, cq, cb) -> cb.equal(charge.get(field), fieldValue);
    }

    static Specification<Charge> integerFieldEquals(Integer fieldValue, String field) {
        return (charge, cq, cb) -> cb.equal(charge.get(field), fieldValue);
    }

    static Specification<Charge> dateFieldGte(LocalDate fieldValue, String field) {
        return (charge, cq, cb) -> cb.greaterThanOrEqualTo(charge.get(field), fieldValue);
    }

    static Specification<Charge> dateFieldLte(LocalDate fieldValue, String field) {
        return (charge, cq, cb) -> cb.lessThanOrEqualTo(charge.get(field), fieldValue);
    }

    static Specification<Charge> eventTypeEquals(ChargeEventType eventType) {
        return (charge, cq, cb) -> cb.lessThanOrEqualTo(charge.get("eventType"), eventType);
    }

    static Specification<Charge> eventCategoryEquals(ChargeCategoryType categoryType) {
        return (charge, cq, cb) -> cb.lessThanOrEqualTo(charge.get("eventCategory"), categoryType);
    }
}
