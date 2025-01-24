package com.azkivam.bankingsystem.infrastructure.repository;

import com.azkivam.bankingsystem.domain.entity.Transaction;
import com.azkivam.bankingsystem.domain.entity.party.AccountParty;
import com.azkivam.bankingsystem.domain.entity.party.PartyType;
import com.azkivam.bankingsystem.domain.repository.BankingRepository;
import com.azkivam.bankingsystem.infrastructure.entity.AccountEntity;
import com.azkivam.bankingsystem.infrastructure.entity.TransactionEntity;
import com.azkivam.bankingsystem.infrastructure.entity.TransactionType;
import com.azkivam.bankingsystem.infrastructure.exception.AccountNotFoundException;
import com.azkivam.bankingsystem.infrastructure.spring.repository.AccountRepository;
import com.azkivam.bankingsystem.infrastructure.spring.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class BankingOperations implements BankingRepository {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @Override
    public BigDecimal getBalance(Long accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .map(AccountEntity::getBalance)
                .orElseThrow(() -> new AccountNotFoundException(accountNumber));
    }

    @Override
    public BigDecimal getBalanceWithLock(Long accountNumber) {
        return accountRepository.findByAccountNumberWithLock(accountNumber)
                .map(AccountEntity::getBalance)
                .orElseThrow(() -> new AccountNotFoundException(accountNumber));
    }

    @Override
    public void addTransaction(Transaction transaction) {

        if (transaction.getSource().getType() == PartyType.ACCOUNT) {
            Long sourceAccountNumber = ((AccountParty) transaction.getSource()).getAccount().getAccountNumber();
            var sourceAccount = accountRepository.findByAccountNumber(sourceAccountNumber)
                    .orElseThrow(() -> new AccountNotFoundException(sourceAccountNumber));

            TransactionEntity sourceTransactionEntity = new TransactionEntity();
            sourceTransactionEntity.setAccountEntity(sourceAccount);
            sourceTransactionEntity.setAmount(transaction.getAmount());
            sourceTransactionEntity.setDate(transaction.getTransactionDate());
            sourceTransactionEntity.setType(TransactionType.DEBIT);
            transactionRepository.save(sourceTransactionEntity);
            sourceAccount.setBalance(sourceAccount.getBalance().subtract(transaction.getAmount()));
            accountRepository.save(sourceAccount);
        }
        if (transaction.getDestination().getType() == PartyType.ACCOUNT) {
            Long destinationAccountNumber = ((AccountParty) transaction.getDestination())
                    .getAccount().getAccountNumber();
            var destinationAccount = accountRepository.findByAccountNumber(destinationAccountNumber)
                    .orElseThrow(() -> new AccountNotFoundException(destinationAccountNumber));

            TransactionEntity destinationTransactionEntity = new TransactionEntity();
            destinationTransactionEntity.setAccountEntity(destinationAccount);
            destinationTransactionEntity.setAmount(transaction.getAmount());
            destinationTransactionEntity.setDate(transaction.getTransactionDate());
            destinationTransactionEntity.setType(TransactionType.DEBIT);
            transactionRepository.save(destinationTransactionEntity);
            destinationAccount.setBalance(destinationAccount.getBalance().add(transaction.getAmount()));
            accountRepository.save(destinationAccount);
        }

        transaction.notifyLogObservers();
    }
}
