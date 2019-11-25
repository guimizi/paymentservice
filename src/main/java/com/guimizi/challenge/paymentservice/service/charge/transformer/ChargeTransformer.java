package com.guimizi.challenge.paymentservice.service.charge.transformer;

import com.guimizi.challenge.paymentservice.model.api.charge.ChargeResponse;
import com.guimizi.challenge.paymentservice.model.entity.charge.Charge;
import com.guimizi.challenge.paymentservice.service.common.AbstractTransformer;
import org.springframework.stereotype.Component;

@Component
public class ChargeTransformer extends AbstractTransformer<Charge, ChargeResponse> {

    @Override
    public ChargeResponse transform(Charge input) {
        ChargeResponse response = new ChargeResponse();
        response.setEventId(input.getEventId());
        response.setEventType(input.getEventType());
        response.setAmount(input.getAmount());
        response.setCurrency(input.getCurrency());
        response.setNormalizedAmount(response.getNormalizedAmount());
        response.setRemainingToPay(response.getRemainingToPay());
        response.setDate(input.getDate());
        return response;
    }
}
