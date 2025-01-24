package com.azkivam.bankingsystem.domain.entity;

import com.azkivam.bankingsystem.domain.log.TransactionLogger;
import com.azkivam.bankingsystem.domain.log.TransactionObserver;
import com.azkivam.bankingsystem.domain.entity.party.AccountParty;
import com.azkivam.bankingsystem.domain.entity.party.CashParty;
import com.azkivam.bankingsystem.domain.entity.party.TransactionParty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



public class Transaction {
    @Getter
    @Setter
    private BigDecimal amount;
    @Getter
    @Setter
    private TransactionParty source;
    @Getter
    @Setter
    private TransactionParty destination;
    @Getter
    @Setter
    private LocalDateTime transactionDate;

    public Transaction (){
        addObserver(new TransactionLogger());
    }

    private List<TransactionObserver> observers = new ArrayList<>();


    public void addObserver(TransactionObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(TransactionObserver observer) {
        observers.remove(observer);
    }

    public void notifyLogObservers() {
        for (TransactionObserver observer : observers) {
            observer.update(source, destination, amount);
        }
    }

    private static Transaction getTransaction(BigDecimal amount) {
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setTransactionDate(LocalDateTime.now());
        return transaction;
    }

    public static Transaction getTransferTransaction(BigDecimal amount, Long sourceAccountNumber, Long destinationAccountNumber) {
        Transaction transaction = getTransaction(amount);
        transaction.setSource(new AccountParty(new Account(null, sourceAccountNumber)));
        transaction.setDestination(new AccountParty(new Account(null, destinationAccountNumber)));
        return transaction;
    }

    public static Transaction getCashWithdrawTransaction(BigDecimal amount, Long accountNumber) {
        Transaction transaction = getTransaction(amount);
        transaction.setSource(new AccountParty(new Account(null, accountNumber)));
        transaction.setDestination(new CashParty());
        return transaction;
    }

    public static Transaction getCashDepositTransaction(BigDecimal amount, Long accountNumber) {
        Transaction transaction = getTransaction(amount);
        transaction.setDestination(new AccountParty(new Account(null, accountNumber)));
        transaction.setSource(new CashParty());
        return transaction;
    }
}