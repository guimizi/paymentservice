package com.guimizi.challenge.paymentservice.model.api.user;

import java.math.BigDecimal;

public class UserStatusResponse {

    private Integer userId;
    private BigDecimal balance;

    public UserStatusResponse(Integer userId, BigDecimal balance) {
        this.userId = userId;
        this.balance = balance;
    }

    public Integer getUserId() {
        return userId;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
