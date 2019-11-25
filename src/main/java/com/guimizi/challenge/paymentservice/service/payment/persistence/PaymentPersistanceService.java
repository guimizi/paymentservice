package com.guimizi.challenge.paymentservice.service.payment.persistence;

import com.guimizi.challenge.paymentservice.model.api.payment.PaymentFilterCriteria;
import com.guimizi.challenge.paymentservice.model.entity.accum.UserDebtAccum;
import com.guimizi.challenge.paymentservice.model.entity.accum.UserPaymentAccum;
import com.guimizi.challenge.paymentservice.model.entity.payment.Payment;
import com.guimizi.challenge.paymentservice.model.entity.payment.PaymentStatus;
import com.guimizi.challenge.paymentservice.repository.PaymentRepository;
import com.guimizi.challenge.paymentservice.repository.UserDebtAccumRepository;
import com.guimizi.challenge.paymentservice.repository.UserPaymentAccumRepository;
import com.guimizi.challenge.paymentservice.service.common.PageableFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentPersistanceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentPersistanceService.class);

    private PaymentRepository paymentRepository;
    private UserPaymentAccumRepository userPaymentAccumRepository;
    private UserDebtAccumRepository userDebtAccumRepository;
    private PaymentFilterFactory paymentFilterFactory;
    private PageableFactory pageableFactory;

    @Autowired
    public PaymentPersistanceService(PaymentRepository paymentRepository, UserPaymentAccumRepository userPaymentAccumRepository,
                                     UserDebtAccumRepository userDebtAccumRepository, PaymentFilterFactory paymentFilterFactory, PageableFactory pageableFactory) {
        this.paymentRepository = paymentRepository;
        this.userPaymentAccumRepository = userPaymentAccumRepository;
        this.userDebtAccumRepository = userDebtAccumRepository;
        this.paymentFilterFactory = paymentFilterFactory;
        this.pageableFactory = pageableFactory;
    }

    @Transactional
    public void createPayment(Payment payment) {
        if(payment.getStatus() != PaymentStatus.NEW) {
            LOGGER.info("Skipping payment creation, payment is not in new state");
            return;
        }
        LOGGER.info("Creating payment {}", payment);
        this.paymentRepository.save(payment);
    }

    @Transactional
    public void acceptPayment(Payment payment) {
        LOGGER.info("Accepting payment: {}", payment);
        if(payment.getStatus() != PaymentStatus.NEW) {
            LOGGER.info("Skipping payment accept process, payment is not in new state");
            return;
        }
        UserDebtAccum userDebtAccum = this.userDebtAccumRepository.findByUserId(payment.getUserId()).orElse(new UserDebtAccum(payment.getUserId()));
        UserPaymentAccum userPaymentAccum = this.userPaymentAccumRepository.findByIdWithLock(payment.getUserId()).orElse(new UserPaymentAccum(payment.getUserId()));
        BigDecimal newPaymentAccumTotal = userPaymentAccum.getAccum().add(payment.getNormalizedAmount());

        //If total payment sum is greater than debt, then reject payment
        if(newPaymentAccumTotal.compareTo(userDebtAccum.getAccum()) == 1) {
            LOGGER.info("Payment was rejected because total payment sum: {} is greater than total debt: {}",
                    newPaymentAccumTotal, userDebtAccum.getAccum());
            payment.setStatus(PaymentStatus.REJECTED);
        } else {
            LOGGER.info("Payment was accepted, updating total payment accum value");
            payment.setStatus(PaymentStatus.ACCEPTED);
            userPaymentAccum.setAccum(newPaymentAccumTotal);
            this.userPaymentAccumRepository.save(userPaymentAccum);
        }
        this.paymentRepository.save(payment);
    }

    @Transactional
    public void finalizePaymentProcessing(Payment payment) {
        if(payment.getStatus().isFinalState()) {
            LOGGER.info("Skipping payment finalized because is already in final state");
            return;
        }
        payment.setStatus(PaymentStatus.PROCESSED);
        LOGGER.info("Finalizing payment process - payment: {}", payment);
        this.paymentRepository.save(payment);
    }

    public Optional<Payment> findPayment(UUID paymentId) {
        LOGGER.info("Find payment by id: {}", paymentId);
        Optional<Payment> payment = this.paymentRepository.findById(paymentId);
        payment.ifPresent(p -> p.getPaymentImputations());
        return payment;
    }

    public void updatePayment(Payment payment) {
        LOGGER.info("Updating payment: {}", payment);
        this.paymentRepository.save(payment);
    }

    public Slice<Payment> retrievePayments(PaymentFilterCriteria filterCriteria) {
        LOGGER.info("Retrieving payments for criteria: {}", filterCriteria);
        Specification<Payment> filter = this.paymentFilterFactory.create(filterCriteria);
        Pageable pageable = this.pageableFactory.create(filterCriteria);
        return this.paymentRepository.findAll(Specification.where(filter), pageable);
    }

}
