package com.guimizi.challenge.paymentservice.service.charge.persistence;

import com.guimizi.challenge.paymentservice.model.api.charge.ChargeFilterCriteria;
import com.guimizi.challenge.paymentservice.model.api.exception.ApiException;
import com.guimizi.challenge.paymentservice.model.api.exception.ErrorType;
import com.guimizi.challenge.paymentservice.model.entity.accum.UserDebtAccum;
import com.guimizi.challenge.paymentservice.model.entity.charge.Charge;
import com.guimizi.challenge.paymentservice.repository.UserDebtAccumRepository;
import com.guimizi.challenge.paymentservice.repository.ChargeRepository;
import com.guimizi.challenge.paymentservice.service.common.PageableFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ChargePersistanceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChargePersistanceService.class);

    private ChargeRepository chargeRepository;
    private UserDebtAccumRepository userDebtAccumRepository;
    private ChargeFilterFactory chargeFilterFactory;
    private PageableFactory pageableFactory;

    @Autowired
    public ChargePersistanceService(ChargeRepository chargeRepository, UserDebtAccumRepository userDebtAccumRepository,
                                    ChargeFilterFactory chargeFilterFactory, PageableFactory pageableFactory) {
        this.chargeRepository = chargeRepository;
        this.userDebtAccumRepository = userDebtAccumRepository;
        this.chargeFilterFactory = chargeFilterFactory;
        this.pageableFactory = pageableFactory;
    }

    public void addCharge(Charge charge) {
        LOGGER.info("Add charge: {}", charge);
        boolean alreadyExists = chargeRepository.findById(charge.getEventId()).isPresent();
        if(alreadyExists) {
            throw new ApiException(ErrorType.CHARGE_ALREADY_EXISTS);
        }

        UserDebtAccum userDebtAccum = this.userDebtAccumRepository.findByUserIdWithLock(charge.getUserId()).orElse(new UserDebtAccum(charge.getUserId()));
        LOGGER.debug("DebtAccum - adding {} to previous accum of {}", charge.getNormalizedAmount(), userDebtAccum.getAccum());
        userDebtAccum.setAccum(userDebtAccum.getAccum().add(charge.getNormalizedAmount()));
        this.userDebtAccumRepository.save(userDebtAccum);
        this.chargeRepository.save(charge);
        LOGGER.info("Charge was succesfully added");
    }

    public List<Charge> findByUserIdOrdered(Integer userId) {
        LOGGER.info("Find charges for user_id: {}", userId);
        return this.chargeRepository.findByUserIdAndRemainingToPayGreaterThanOrderByRemainingToPayDesc(userId, BigDecimal.ZERO);
    }

    public List<Charge> findByUserIdWithPartialPaymentOrdered(Integer userId) {
        LOGGER.info("Find charges with partial payment for user_id: {}", userId);
        return this.chargeRepository.findByUserIdWithPartialPayment(userId);
    }

    public void updateCharge(Charge charge) {
        this.chargeRepository.save(charge);
    }

    public Charge retrieveChargeForUpdate(long eventId) {
        LOGGER.info("Retrieving charge for update - event_id: {}", eventId);
        Charge charge = this.chargeRepository.findByIdWithLock(eventId);
        charge.getPaymentImputations();
        return charge;
    }

    public Charge retrieveCharge(long eventId) {
        LOGGER.info("Retrieving charge- event_id: {}", eventId);
        Charge charge = this.chargeRepository.findById(eventId);
        charge.getPaymentImputations();
        return charge;
    }

    public Slice<Charge> retriveCharges(ChargeFilterCriteria chargeFilterCriteria) {
        LOGGER.info("Retrieve charges - filter criteria: {}", chargeFilterCriteria);
        Specification<Charge> filter = this.chargeFilterFactory.create(chargeFilterCriteria);
        Pageable pageable = this.pageableFactory.create(chargeFilterCriteria);
        return this.chargeRepository.findAll(filter, pageable);
    }


}
