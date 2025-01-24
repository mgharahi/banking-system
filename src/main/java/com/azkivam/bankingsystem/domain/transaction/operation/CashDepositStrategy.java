package com.azkivam.bankingsystem.domain.transaction.operation;

import com.azkivam.bankingsystem.domain.entity.Account;
import com.azkivam.bankingsystem.domain.entity.Transaction;

import java.math.BigDecimal;

public class CashDepositStrategy implements TransactionStrategy {
    @Override
    public void execute(Account account, BigDecimal amount) {
        account.getBankingRepository()
                .addTransaction(Transaction.getCashDepositTransaction(amount, account.getAccountNumber()));
    }
}