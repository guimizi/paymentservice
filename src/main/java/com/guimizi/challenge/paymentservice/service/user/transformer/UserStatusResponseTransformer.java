package com.guimizi.challenge.paymentservice.service.user.transformer;

import com.guimizi.challenge.paymentservice.model.api.user.UserStatusResponse;
import com.guimizi.challenge.paymentservice.model.entity.accum.UserStatus;
import com.guimizi.challenge.paymentservice.service.common.AbstractTransformer;
import org.springframework.stereotype.Component;

@Component
public class UserStatusResponseTransformer extends AbstractTransformer<UserStatus, UserStatusResponse> {

    @Override
    public UserStatusResponse transform(UserStatus input) {
        return new UserStatusResponse(input.getUserId(), input.getBalance());
    }
}
