package com.guimizi.challenge.paymentservice.service.payment;

import com.guimizi.challenge.paymentservice.model.entity.charge.Charge;
import com.guimizi.challenge.paymentservice.model.entity.payment.Payment;
import com.guimizi.challenge.paymentservice.model.entity.payment.PaymentImputation;
import com.guimizi.challenge.paymentservice.model.entity.payment.PaymentStatus;
import com.guimizi.challenge.paymentservice.service.charge.persistence.ChargePersistanceService;
import com.guimizi.challenge.paymentservice.service.payment.persistence.PaymentImputationPersistanceService;
import com.guimizi.challenge.paymentservice.service.payment.persistence.PaymentPersistanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

@Service
public class PaymentImputationService {

    private ChargePersistanceService chargePersistanceService;
    private PaymentImputationPersistanceService paymentImputationPersistanceService;
    private PaymentPersistanceService paymentPersistanceService;

    @Autowired
    public PaymentImputationService(ChargePersistanceService chargePersistanceService, PaymentImputationPersistanceService paymentImputationPersistanceService,
                                    PaymentPersistanceService paymentPersistanceService) {
        this.chargePersistanceService = chargePersistanceService;
        this.paymentImputationPersistanceService = paymentImputationPersistanceService;
        this.paymentPersistanceService = paymentPersistanceService;
    }

    @Transactional
    public void applyPayment(Payment payment) {
        if(payment.getStatus() != PaymentStatus.ACCEPTED) {
            return;
        }

        List<Charge> charges = this.chargePersistanceService.findByUserIdOrdered(payment.getUserId());
        Iterator<Charge> it = charges.iterator();

        BigDecimal balance = payment.getNormalizedAmount();
        while(it.hasNext() && balance.compareTo(BigDecimal.ZERO) == 1) {
            Charge charge = it.next();
            charge = this.chargePersistanceService.retrieveChargeForUpdate(charge.getEventId());
            BigDecimal paid = null;
            //if payment is greater than remainingToPay
            if(balance.compareTo(charge.getRemainingToPay()) == 1) {
                paid = charge.getRemainingToPay();
                charge.setRemainingToPay(BigDecimal.ZERO);
            } else {
                paid = balance;
                charge.setRemainingToPay(charge.getRemainingToPay().subtract(balance));
            }

            payment = this.paymentPersistanceService.findPayment(payment.getPaymentId()).orElse(null);

            PaymentImputation paymentImputation = new PaymentImputation(payment, charge, paid);
            charge.getPaymentImputations().add(paymentImputation);
            payment.getPaymentImputations().add(paymentImputation);
            this.paymentImputationPersistanceService.addPaymentImputation(paymentImputation);

            this.chargePersistanceService.updateCharge(charge);
            this.paymentPersistanceService.updatePayment(payment);


            balance = balance.subtract(paid);
        }
    }

}
