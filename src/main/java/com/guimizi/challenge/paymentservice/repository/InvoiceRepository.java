package com.guimizi.challenge.paymentservice.repository;

import com.guimizi.challenge.paymentservice.model.entity.invoice.Invoice;
import com.guimizi.challenge.paymentservice.model.entity.invoice.InvoiceId;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface InvoiceRepository extends CrudRepository<Invoice, InvoiceId>, JpaSpecificationExecutor<Invoice> {

}
