package com.guimizi.challenge.paymentservice.model.entity.accum;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class UserDebtAccum {

    @Id
    private Integer userId;
    private BigDecimal accum;

    protected UserDebtAccum() {

    }

    public UserDebtAccum(Integer userId) {
        this.userId = userId;
        this.accum = BigDecimal.ZERO;
    }

    public UserDebtAccum(Integer userId, BigDecimal accum) {
        this.userId = userId;
        this.accum = accum;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public BigDecimal getAccum() {
        return accum;
    }

    public void setAccum(BigDecimal accum) {
        this.accum = accum;
    }
}
