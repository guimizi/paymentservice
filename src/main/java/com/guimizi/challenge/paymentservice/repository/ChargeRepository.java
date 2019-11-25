package com.guimizi.challenge.paymentservice.repository;

import com.guimizi.challenge.paymentservice.model.entity.accum.UserStatus;
import com.guimizi.challenge.paymentservice.model.entity.charge.Charge;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.LockModeType;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ChargeRepository  extends CrudRepository<Charge, Long>, JpaSpecificationExecutor<Charge> {

    List<Charge> findByUserId(int userId);

    List<Charge> findByUserIdAndRemainingToPayGreaterThanOrderByRemainingToPayDesc(Integer userId, BigDecimal remainingToPay);

    Charge findById(long id);

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM Charge c WHERE c.eventId = :searchId")
    Charge findByIdWithLock(long searchId);

    @Query("SELECT c FROM Charge c WHERE c.userId = :userId AND c.remainingToPay > 0 AND c.normalizedAmount > c.remainingToPay ORDER BY c.remainingToPay")
    List<Charge> findByUserIdWithPartialPayment(Integer userId);

}
