package com.guimizi.challenge.paymentservice.service.user.persistence;

import com.guimizi.challenge.paymentservice.model.entity.accum.UserDebtAccum;
import com.guimizi.challenge.paymentservice.model.entity.accum.UserPaymentAccum;
import com.guimizi.challenge.paymentservice.model.entity.accum.UserStatus;
import com.guimizi.challenge.paymentservice.repository.UserDebtAccumRepository;
import com.guimizi.challenge.paymentservice.repository.UserPaymentAccumRepository;
import com.guimizi.challenge.paymentservice.repository.ChargeRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserStatusPersistenceService {

    private UserDebtAccumRepository userDebtAccumRepository;
    private UserPaymentAccumRepository userPaymentAccumRepository;
    private ChargeRepositoryImpl chargeRepositoryImpl;

    @Autowired
    public UserStatusPersistenceService(UserDebtAccumRepository userDebtAccumRepository, UserPaymentAccumRepository userPaymentAccumRepository, ChargeRepositoryImpl chargeRepositoryImpl) {
        this.userDebtAccumRepository = userDebtAccumRepository;
        this.userPaymentAccumRepository = userPaymentAccumRepository;
        this.chargeRepositoryImpl = chargeRepositoryImpl;
    }

    public UserStatus retrieveUserStatus(Integer userId) {
        UserDebtAccum userDebtAccum = this.userDebtAccumRepository.findByUserId(userId).orElse(new UserDebtAccum(userId));
        UserPaymentAccum userPaymentAccum = this.userPaymentAccumRepository.findById(userId).orElse(new UserPaymentAccum(userId));
        return new UserStatus(userId, userPaymentAccum.getAccum().subtract(userDebtAccum.getAccum()));
    }

    public List<UserStatus> retriveUserStatusesTopDebt(int pageNumber, int pageSize) {
       return this.chargeRepositoryImpl.retriveUserStatusesTopDebt(pageNumber, pageSize);
    }

}
