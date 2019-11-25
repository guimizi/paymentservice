package com.guimizi.challenge.paymentservice.service.invoice.transformer;

import com.guimizi.challenge.paymentservice.model.api.charge.ChargeResponse;
import com.guimizi.challenge.paymentservice.model.api.invoice.InvoiceResponse;
import com.guimizi.challenge.paymentservice.model.entity.invoice.Invoice;
import com.guimizi.challenge.paymentservice.service.charge.transformer.ChargeTransformer;
import com.guimizi.challenge.paymentservice.service.common.AbstractTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
public class InvoiceResponseTransformer extends AbstractTransformer<Invoice, InvoiceResponse> {

    private ChargeTransformer chargeTransformer;

    @Autowired
    public InvoiceResponseTransformer(ChargeTransformer chargeTransformer) {
        this.chargeTransformer = chargeTransformer;
    }

    @Override
    public InvoiceResponse transform(Invoice input) {
        InvoiceResponse response = new InvoiceResponse();
        response.setUserId(input.getId().getUserId());
        response.setMonth(input.getId().getMonth());
        response.setYear(input.getId().getYear());
        response.setTotal(input.getTotal());

        List<ChargeResponse> chargeResponses = this.chargeTransformer.transformAll(input.getCharges());
        chargeResponses.sort(Comparator.comparing(ChargeResponse::getDate));
        response.setCharges(chargeResponses);
        return response;
    }

}
