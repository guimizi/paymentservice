package com.guimizi.challenge.paymentservice.repository;

import com.guimizi.challenge.paymentservice.model.entity.payment.PaymentImputation;
import com.guimizi.challenge.paymentservice.model.entity.payment.PaymentImputationId;
import org.springframework.data.repository.CrudRepository;

public interface PaymentImputationRepository extends CrudRepository<PaymentImputation, PaymentImputationId> {
}
