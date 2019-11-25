package com.guimizi.challenge.paymentservice.service.invoice;

import com.guimizi.challenge.paymentservice.model.api.ListResponse;
import com.guimizi.challenge.paymentservice.model.api.invoice.InvoiceResponse;
import com.guimizi.challenge.paymentservice.model.api.invoice.InvoicefilterCriteria;
import com.guimizi.challenge.paymentservice.model.entity.charge.Charge;
import com.guimizi.challenge.paymentservice.model.entity.invoice.Invoice;
import com.guimizi.challenge.paymentservice.service.invoice.persistence.InvoicePersistenceService;
import com.guimizi.challenge.paymentservice.service.invoice.transformer.InvoiceResponseTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InvoiceService {

    private static Logger LOGGER = LoggerFactory.getLogger(InvoiceService.class);

    private InvoicePersistenceService invoicePersistenceService;
    private InvoiceResponseTransformer invoiceResponseTransformer;

    @Autowired
    public InvoiceService(InvoicePersistenceService invoicePersistenceService,
                          InvoiceResponseTransformer invoiceResponseTransformer) {
        this.invoicePersistenceService = invoicePersistenceService;
        this.invoiceResponseTransformer = invoiceResponseTransformer;
    }

    @Transactional
    public void addCharge(Charge charge) {
        LOGGER.info("Adding charge: {} - to invoice", charge);
        int month = charge.getDate().getMonthValue();
        int year = charge.getDate().getYear();

        Invoice invoice = this.invoicePersistenceService.retrieveInvoice(charge.getUserId(), month, year);
        invoice.getCharges().add(charge);
        invoice.setTotal(invoice.getTotal().add(charge.getNormalizedAmount()));
        this.invoicePersistenceService.saveInvoice(invoice);
    }

    @Transactional
    public InvoiceResponse retrieveInvoice(Integer userId, Integer month, Integer year) {
        LOGGER.info("Retrieving invoice");
        Invoice invoice = this.invoicePersistenceService.retrieveInvoice(userId, month, year);
        return this.invoiceResponseTransformer.transform(invoice);
    }

    @Transactional
    public ListResponse<InvoiceResponse> retrieveInvoices(InvoicefilterCriteria invoicefilterCriteria) {
        LOGGER.info("Retrieving invoices");
        Slice<Invoice> invoiceSlice = this.invoicePersistenceService.retrieveInvoices(invoicefilterCriteria);
        List<InvoiceResponse> invoiceResponses = this.invoiceResponseTransformer.transformAll(invoiceSlice.getContent());
        LOGGER.info("Returning {} invoices for criteria: {}", invoiceResponses.size(), invoicefilterCriteria);
        return new ListResponse<>(invoicefilterCriteria.getPageNumber(), invoiceSlice.hasNext(), invoiceResponses);
    }
}

