package com.guimizi.challenge.paymentservice.model.entity.invoice;

import com.guimizi.challenge.paymentservice.model.entity.charge.Charge;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Invoice {

    @EmbeddedId
    private InvoiceId id;

    @OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "invoice_user_id", referencedColumnName = "user_id"),
            @JoinColumn(name = "month", referencedColumnName = "month"),
            @JoinColumn(name = "year", referencedColumnName = "year")
    })
    private Set<Charge> charges;
    private BigDecimal total;

    protected Invoice() {
    }

    public Invoice(Integer userId, int month, int year) {
        this.id = new InvoiceId(userId, month, year);
        this.total = BigDecimal.ZERO;
        this.charges = new HashSet<>();
    }

    public InvoiceId getId() {
        return id;
    }

    public void setId(InvoiceId id) {
        this.id = id;
    }

    public Set<Charge> getCharges() {
        return charges;
    }

    public void setCharges(Set<Charge> charges) {
        this.charges = charges;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }


}
