package com.guimizi.challenge.paymentservice.repository;

import com.guimizi.challenge.paymentservice.model.entity.accum.UserPaymentAccum;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface UserPaymentAccumRepository extends CrudRepository<UserPaymentAccum, Integer> {

    Optional<UserPaymentAccum> findById(int id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT new UserPaymentAccum(userId, accum) FROM UserPaymentAccum a WHERE a.userId= :id")
    Optional<UserPaymentAccum> findByIdWithLock(int id);



}
