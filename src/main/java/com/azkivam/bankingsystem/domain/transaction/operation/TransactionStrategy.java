package com.azkivam.bankingsystem.domain.transaction.operation;

import com.azkivam.bankingsystem.domain.entity.Account;

import java.math.BigDecimal;

public interface TransactionStrategy {
    void execute(Account account, BigDecimal amount);
}