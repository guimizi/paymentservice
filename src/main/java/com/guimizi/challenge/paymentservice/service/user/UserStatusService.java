package com.guimizi.challenge.paymentservice.service.user;

import com.guimizi.challenge.paymentservice.model.api.ListResponse;
import com.guimizi.challenge.paymentservice.model.api.user.UserStatusResponse;
import com.guimizi.challenge.paymentservice.model.entity.accum.UserStatus;
import com.guimizi.challenge.paymentservice.service.user.persistence.UserStatusPersistenceService;
import com.guimizi.challenge.paymentservice.service.user.transformer.UserStatusResponseTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserStatusService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserStatusService.class);

    private UserStatusPersistenceService userStatusPersistenceService;
    private UserStatusResponseTransformer userStatusResponseTransformer;

    @Autowired
    public UserStatusService(UserStatusPersistenceService userStatusPersistenceService, UserStatusResponseTransformer userStatusResponseTransformer) {
        this.userStatusPersistenceService = userStatusPersistenceService;
        this.userStatusResponseTransformer = userStatusResponseTransformer;
    }

    public UserStatusResponse retrieveUserStatus(Integer userId) {
        LOGGER.info("Retrieve user status for user_id: {}", userId);
        UserStatus userStatus = userStatusPersistenceService.retrieveUserStatus(userId);
        return this.userStatusResponseTransformer.transform(userStatus);
    }

    public ListResponse<UserStatusResponse> retrieveTopDebtUsers(Integer pageNumber, Integer pageSize) {
        LOGGER.info("Retrive top debt users - pageNumber: {}, pageSize: {}");
        List<UserStatus> userStatusList = this.userStatusPersistenceService.retriveUserStatusesTopDebt(pageNumber, pageSize);
        List<UserStatusResponse> userStatusResponses = this.userStatusResponseTransformer.transformAll(userStatusList);
        LOGGER.info("Returning {} user statuses", userStatusResponses.size());
        return new ListResponse<>(pageNumber, null, userStatusResponses);
    }
}
