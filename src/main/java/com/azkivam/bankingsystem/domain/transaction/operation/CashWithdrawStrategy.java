package com.azkivam.bankingsystem.domain.transaction.operation;

import com.azkivam.bankingsystem.domain.entity.Account;
import com.azkivam.bankingsystem.domain.entity.Transaction;
import com.azkivam.bankingsystem.domain.exception.InsufficientBalanceException;

import java.math.BigDecimal;

public class CashWithdrawStrategy implements TransactionStrategy {
    @Override
    public void execute(Account account, BigDecimal amount) {

        var balance = account.getBankingRepository().getBalanceWithLock(account.getAccountNumber());

        if (balance.compareTo(amount) < 0) {
            throw new InsufficientBalanceException();
        }

        account.getBankingRepository().addTransaction(Transaction.getCashWithdrawTransaction(amount,account.getAccountNumber()));
    }
}