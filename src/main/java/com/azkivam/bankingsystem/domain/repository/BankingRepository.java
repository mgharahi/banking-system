package com.azkivam.bankingsystem.domain.repository;

import com.azkivam.bankingsystem.domain.entity.Transaction;

import java.math.BigDecimal;

public interface BankingRepository {
    BigDecimal getBalance(Long accountNumber);

    BigDecimal getBalanceWithLock(Long accountNumber);

    void addTransaction(Transaction transaction);
}