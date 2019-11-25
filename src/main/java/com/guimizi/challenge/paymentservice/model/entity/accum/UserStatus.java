package com.guimizi.challenge.paymentservice.model.entity.accum;

import java.math.BigDecimal;

public class UserStatus {

    private Integer userId;
    private BigDecimal balance;

    protected UserStatus() {

    }

    public UserStatus(Integer userId, BigDecimal balance) {
        this.userId = userId;
        this.balance = balance;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
