package com.guimizi.challenge.paymentservice.repository;

import com.guimizi.challenge.paymentservice.model.entity.payment.Payment;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface PaymentRepository extends CrudRepository<Payment, UUID>, JpaSpecificationExecutor<Payment> {

    Payment findByUserId(String userId);

}
