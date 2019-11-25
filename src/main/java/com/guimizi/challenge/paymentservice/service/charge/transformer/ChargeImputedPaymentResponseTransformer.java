package com.guimizi.challenge.paymentservice.service.charge.transformer;

import com.guimizi.challenge.paymentservice.model.api.charge.ChargeImputedPaymentResponse;
import com.guimizi.challenge.paymentservice.model.entity.payment.PaymentImputation;
import com.guimizi.challenge.paymentservice.service.common.AbstractTransformer;
import org.springframework.stereotype.Component;

@Component
public class ChargeImputedPaymentResponseTransformer extends AbstractTransformer<PaymentImputation, ChargeImputedPaymentResponse> {

    @Override
    public ChargeImputedPaymentResponse transform(PaymentImputation input) {
        return new ChargeImputedPaymentResponse(input.getPayment().getPaymentId(), input.getAmount());
    }

}
