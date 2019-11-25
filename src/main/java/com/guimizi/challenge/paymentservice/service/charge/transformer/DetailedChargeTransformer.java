package com.guimizi.challenge.paymentservice.service.charge.transformer;

import com.guimizi.challenge.paymentservice.model.api.charge.ChargeImputedPaymentResponse;
import com.guimizi.challenge.paymentservice.model.api.charge.ChargeResponse;
import com.guimizi.challenge.paymentservice.model.entity.charge.Charge;
import com.guimizi.challenge.paymentservice.service.common.AbstractTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Component
public class DetailedChargeTransformer extends AbstractTransformer<Charge, ChargeResponse> {

    private ChargeTransformer chargeTransformer;
    private ChargeImputedPaymentResponseTransformer chargeImputedPaymentResponseTransformer;

    @Autowired
    public DetailedChargeTransformer(ChargeTransformer chargeTransformer,
                                     ChargeImputedPaymentResponseTransformer chargeImputedPaymentResponseTransformer) {
        this.chargeTransformer = chargeTransformer;
        this.chargeImputedPaymentResponseTransformer = chargeImputedPaymentResponseTransformer;
    }

    @Override
    public ChargeResponse transform(Charge input) {
        ChargeResponse response = this.chargeTransformer.transform(input);
        response.setUserId(input.getUserId());
        List<ChargeImputedPaymentResponse> imputedPayments = this.chargeImputedPaymentResponseTransformer.transformAll(input.getPaymentImputations());
        imputedPayments.sort(Collections.reverseOrder(Comparator.comparing(ChargeImputedPaymentResponse::getAmount)));
        response.setImputedPayments(imputedPayments);
        return response;
    }
}
