package com.guimizi.challenge.paymentservice.service.payment.transformer;

import com.guimizi.challenge.paymentservice.model.api.payment.PaymentResponse;
import com.guimizi.challenge.paymentservice.model.entity.payment.Payment;
import com.guimizi.challenge.paymentservice.service.common.AbstractTransformer;
import org.springframework.stereotype.Component;

@Component
public class PaymentResponseTransformer extends AbstractTransformer<Payment, PaymentResponse> {

    @Override
    public PaymentResponse transform(Payment input) {
        PaymentResponse response = new PaymentResponse();
        response.setPaymentId(input.getPaymentId());
        response.setAmount(input.getAmount());
        response.setCreationTime(input.getCreationTime());
        response.setNormalizedAmount(input.getNormalizedAmount());
        response.setUserId(input.getUserId());
        response.setStatus(input.getStatus());
        return response;
    }
}
