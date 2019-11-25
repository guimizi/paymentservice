package com.guimizi.challenge.paymentservice.repository;

import com.guimizi.challenge.paymentservice.model.entity.accum.UserStatus;
import com.guimizi.challenge.paymentservice.model.entity.charge.Charge;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ChargeRepositoryImpl {


    @PersistenceContext
    private EntityManager entityManager;


    public List<UserStatus> retriveUserStatusesTopDebt(int pageNumber, int pageSize) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = cb.createTupleQuery();

        Root<Charge> root = query.from(Charge.class);
        query.multiselect(root.get("userId"), cb.sum(root.get("remainingToPay")));
        query.groupBy(root.get("userId"));
        query.orderBy(cb.desc(cb.sum(root.get("remainingToPay"))));

        List<Tuple> tuples = this.entityManager.createQuery(query).setFirstResult(pageNumber * pageSize).setMaxResults(pageSize).getResultList();
        List<UserStatus> userStatuses = tuples.stream().map(tuple -> new UserStatus(tuple.get(0, Integer.class), tuple.get(1, BigDecimal.class).multiply(BigDecimal.valueOf(-1)))).collect(Collectors.toList());

        return userStatuses;
    }

}
