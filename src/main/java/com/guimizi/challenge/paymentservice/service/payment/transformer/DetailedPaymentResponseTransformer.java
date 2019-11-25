package com.guimizi.challenge.paymentservice.service.payment.transformer;

import com.guimizi.challenge.paymentservice.model.api.payment.PaymentImputationResponse;
import com.guimizi.challenge.paymentservice.model.api.payment.PaymentResponse;
import com.guimizi.challenge.paymentservice.model.entity.payment.Payment;
import com.guimizi.challenge.paymentservice.service.common.AbstractTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DetailedPaymentResponseTransformer extends AbstractTransformer<Payment, PaymentResponse> {

    private PaymentResponseTransformer paymentResponseTransformer;
    private PaymentImputationResponseTransformer paymentImputationResponseTransformer;

    @Autowired
    public DetailedPaymentResponseTransformer(PaymentResponseTransformer paymentResponseTransformer, PaymentImputationResponseTransformer paymentImputationResponseTransformer) {
        this.paymentResponseTransformer = paymentResponseTransformer;
        this.paymentImputationResponseTransformer = paymentImputationResponseTransformer;
    }

    @Override
    public PaymentResponse transform(Payment input) {
        PaymentResponse response = this.paymentResponseTransformer.transform(input);
        List<PaymentImputationResponse> imputations = paymentImputationResponseTransformer.transformAll(input.getPaymentImputations());
        response.setPaymentImputations(imputations);
        return response;
    }

}
