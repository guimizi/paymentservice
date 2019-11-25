package com.guimizi.challenge.paymentservice.repository;

import com.guimizi.challenge.paymentservice.model.entity.accum.UserDebtAccum;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface UserDebtAccumRepository  extends CrudRepository<UserDebtAccum, Integer> {

    Optional<UserDebtAccum> findByUserId(int id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT new UserDebtAccum(userId, accum) FROM UserDebtAccum a WHERE a.userId= :id")
    Optional<UserDebtAccum> findByUserIdWithLock(int id);


}
