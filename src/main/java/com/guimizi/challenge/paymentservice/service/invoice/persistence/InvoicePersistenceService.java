package com.guimizi.challenge.paymentservice.service.invoice.persistence;

import com.guimizi.challenge.paymentservice.model.api.invoice.InvoicefilterCriteria;
import com.guimizi.challenge.paymentservice.model.entity.invoice.Invoice;
import com.guimizi.challenge.paymentservice.model.entity.invoice.InvoiceId;
import com.guimizi.challenge.paymentservice.repository.InvoiceRepository;
import com.guimizi.challenge.paymentservice.service.common.PageableFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class InvoicePersistenceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InvoicePersistenceService.class);

    private InvoiceRepository invoiceRepository;
    private InvoiceFilterFactory invoiceFilterFactory;
    private PageableFactory pageableFactory;

    public InvoicePersistenceService(InvoiceRepository invoiceRepository, InvoiceFilterFactory invoiceFilterFactory, PageableFactory pageableFactory) {
        this.invoiceRepository = invoiceRepository;
        this.invoiceFilterFactory = invoiceFilterFactory;
        this.pageableFactory = pageableFactory;
    }

    public Invoice retrieveInvoice(Integer userId, Integer month, Integer year) {
        LOGGER.info("Retrieving invoice for - userId: {} - month: {} - year: {}", userId, month, year);
        Invoice invoice = this.invoiceRepository.findById(new InvoiceId(userId, month, year)).orElse(null);
        if(invoice == null) {
            LOGGER.info("Invoice not found - creating invoice for - userId: {} - month: {} - year: {}", userId, month, year);
            invoice = new Invoice(userId, month, year);
            this.invoiceRepository.save(invoice);
        };
        return invoice;
    }

    public void saveInvoice(Invoice invoice) {
        LOGGER.info("Save invoice - id: {}", invoice.getId());
        this.invoiceRepository.save(invoice);
    }

    public Slice<Invoice> retrieveInvoices(InvoicefilterCriteria invoicefilterCriteria) {
        LOGGER.info("Retrieving invoices for criteria: {}", invoicefilterCriteria);
        Specification<Invoice> filterSpecification = invoiceFilterFactory.create(invoicefilterCriteria);
        Pageable pageable = this.pageableFactory.create(invoicefilterCriteria);
        Slice<Invoice> invoices = this.invoiceRepository.findAll(Specification.where(filterSpecification), pageable);
        invoices.getContent().stream().map(Invoice::getCharges);
        return invoices;
    }

}
