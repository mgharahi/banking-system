package com.azkivam.bankingsystem.domain.entity;

import com.azkivam.bankingsystem.domain.repository.BankingRepository;
import com.azkivam.bankingsystem.domain.transaction.operation.TransactionStrategy;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
@AllArgsConstructor
public class Account {
    private BankingRepository bankingRepository;
    private Long accountNumber;

    public void transfer(TransactionStrategy strategy, BigDecimal amount) {
        strategy.execute(this, amount);
    }

    public BigDecimal getBalance() {
        return bankingRepository.getBalance(accountNumber);
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountNumber=" + accountNumber +
                '}';
    }
}