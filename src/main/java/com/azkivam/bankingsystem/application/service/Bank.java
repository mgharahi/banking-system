package com.azkivam.bankingsystem.application.service;

import com.azkivam.bankingsystem.application.service.command.CreateAccountCommand;
import com.azkivam.bankingsystem.application.service.command.DepositCommand;
import com.azkivam.bankingsystem.application.service.command.TransferCommand;
import com.azkivam.bankingsystem.application.service.command.WithdrawCommand;
import com.azkivam.bankingsystem.application.service.query.BalanceQuery;
import com.azkivam.bankingsystem.domain.entity.Account;
import com.azkivam.bankingsystem.domain.transaction.operation.CashDepositStrategy;
import com.azkivam.bankingsystem.domain.transaction.operation.CashWithdrawStrategy;
import com.azkivam.bankingsystem.domain.repository.BankingRepository;
import com.azkivam.bankingsystem.domain.transaction.operation.TransferStrategy;
import com.azkivam.bankingsystem.infrastructure.entity.AccountEntity;
import com.azkivam.bankingsystem.infrastructure.exception.AccountNotFoundException;
import com.azkivam.bankingsystem.infrastructure.spring.repository.AccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class Bank {
    private final BankingRepository bankingRepository;
    private final AccountRepository accountRepository;

    public BigDecimal getBalance(BalanceQuery balanceQuery) {
        return bankingRepository.getBalance(balanceQuery.accountNumber());
    }

    @Transactional
    public void withdraw(WithdrawCommand withdrawCommand) {
        var sourceAccount = accountRepository.findByAccountNumber(withdrawCommand.source())
                .orElseThrow(() -> new AccountNotFoundException(withdrawCommand.source()));

        new Account(bankingRepository, sourceAccount.getAccountNumber())
                .transfer(new CashWithdrawStrategy(), withdrawCommand.amount());
    }

    @Transactional
    public void deposit(DepositCommand depositCommand) {
        var accountNumber = accountRepository.findByAccountNumber(depositCommand.destination())
                .orElseThrow(() -> new AccountNotFoundException(depositCommand.destination()))
                .getAccountNumber();

        Account account = new Account(bankingRepository, accountNumber);
        account.transfer(new CashDepositStrategy(), depositCommand.amount());
    }

    @Transactional
    public void transfer(TransferCommand transferCommand) {
        var sourceAccountNumber = accountRepository.findByAccountNumber(transferCommand.source())
                .orElseThrow(() -> new AccountNotFoundException(transferCommand.source()))
                .getAccountNumber();

        var destinationAccountNumber = accountRepository.findByAccountNumber(transferCommand.destination())
                .orElseThrow(() -> new AccountNotFoundException(transferCommand.destination()))
                .getAccountNumber();

        Account sourceAccount = new Account(bankingRepository, sourceAccountNumber);
        sourceAccount.transfer(new TransferStrategy(destinationAccountNumber), transferCommand.amount());
    }

    @Transactional
    public void createAccount(CreateAccountCommand accountCommand) {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setBalance(BigDecimal.ZERO);
        accountRepository.save(accountEntity);

        Account account = new Account(bankingRepository, accountEntity.getAccountNumber());
        account.transfer(new CashDepositStrategy(), accountCommand.initialBalance());

    }
}