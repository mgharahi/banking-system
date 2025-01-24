package com.azkivam.bankingsystem.domain.transaction.operation;

import com.azkivam.bankingsystem.domain.entity.Account;
import com.azkivam.bankingsystem.domain.entity.Transaction;
import com.azkivam.bankingsystem.domain.exception.InsufficientBalanceException;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
public class TransferStrategy implements TransactionStrategy {
    private final Long destinationAccountNumber;

    @Override
    public void execute(Account sourceAccount, BigDecimal amount) {

        var balance = sourceAccount.getBankingRepository().getBalanceWithLock(sourceAccount.getAccountNumber());

        if (balance.compareTo(amount) < 0) {
            throw new InsufficientBalanceException();
        }

        sourceAccount.getBankingRepository().addTransaction(Transaction.getTransferTransaction(amount,sourceAccount.getAccountNumber(),destinationAccountNumber));
    }
}