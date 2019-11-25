package com.guimizi.challenge.paymentservice.service.payment.persistence;

import com.guimizi.challenge.paymentservice.model.entity.payment.PaymentImputation;
import com.guimizi.challenge.paymentservice.repository.PaymentImputationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentImputationPersistanceService {

    private PaymentImputationRepository paymentImputationRepository;

    @Autowired
    public PaymentImputationPersistanceService(PaymentImputationRepository paymentImputationRepository) {
        this.paymentImputationRepository = paymentImputationRepository;
    }

    public void addPaymentImputation(PaymentImputation paymentImputation) {
        this.paymentImputationRepository.save(paymentImputation);
    }

}
