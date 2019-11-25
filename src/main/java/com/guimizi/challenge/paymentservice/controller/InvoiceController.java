package com.guimizi.challenge.paymentservice.controller;

import com.guimizi.challenge.paymentservice.model.api.ListResponse;
import com.guimizi.challenge.paymentservice.model.api.invoice.InvoiceResponse;
import com.guimizi.challenge.paymentservice.model.api.invoice.InvoicefilterCriteria;
import com.guimizi.challenge.paymentservice.service.invoice.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InvoiceController {

    private InvoiceService invoiceService;

    @Autowired
    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping("/invoice")
    public InvoiceResponse getInvoices(@RequestParam Integer userId, @RequestParam Integer month,
                                             @RequestParam  Integer year) {
        return this.invoiceService.retrieveInvoice(userId, month, year);
    }

    @GetMapping("/invoices")
    public ListResponse<InvoiceResponse> getInvoices(@RequestParam(required = false) Integer userId, @RequestParam(required = false) Integer fromMonth
            , @RequestParam(required = false) Integer fromYear, @RequestParam(required = false) Integer toMonth, @RequestParam(required = false) Integer toYear,
                                                     @RequestParam(required = false) Integer pageNumber) {
        InvoicefilterCriteria invoicefilterCriteria = new InvoicefilterCriteria(pageNumber, userId, fromMonth, fromYear, toMonth, toYear);

        return this.invoiceService.retrieveInvoices(invoicefilterCriteria);
    }

}
