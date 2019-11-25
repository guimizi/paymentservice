package com.guimizi.challenge.paymentservice.service.payment.transformer;

import com.guimizi.challenge.paymentservice.model.api.payment.PaymentImputationResponse;
import com.guimizi.challenge.paymentservice.model.entity.payment.PaymentImputation;
import com.guimizi.challenge.paymentservice.service.common.AbstractTransformer;
import org.springframework.stereotype.Component;

@Component
public class PaymentImputationResponseTransformer extends AbstractTransformer<PaymentImputation, PaymentImputationResponse> {

    @Override
    public PaymentImputationResponse transform(PaymentImputation input) {
        return new PaymentImputationResponse(input.getCharge().getEventId(), input.getAmount());
    }
}
