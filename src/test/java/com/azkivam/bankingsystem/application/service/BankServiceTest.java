package com.azkivam.bankingsystem.application.service;

import com.azkivam.bankingsystem.application.service.command.*;
import com.azkivam.bankingsystem.application.service.query.BalanceQuery;
import com.azkivam.bankingsystem.domain.repository.BankingRepository;
import com.azkivam.bankingsystem.infrastructure.entity.AccountEntity;
import com.azkivam.bankingsystem.infrastructure.exception.AccountNotFoundException;
import com.azkivam.bankingsystem.infrastructure.spring.repository.AccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.*;

class BankServiceTest {
    @Mock
    private BankingRepository bankingRepository;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private Bank bank;

    private AccountEntity account;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        account = new AccountEntity();
        account.setBalance(BigDecimal.ZERO);
        when(accountRepository.findByAccountNumber(123L)).thenReturn(Optional.ofNullable(account));
    }

    @Test
    void testGetBalance() {
        BalanceQuery balanceQuery = new BalanceQuery(123L);
        when(bankingRepository.getBalance(123L)).thenReturn(BigDecimal.valueOf(1000));
        BigDecimal balance = bank.getBalance(balanceQuery);
        Assertions.assertEquals(balance.compareTo(BigDecimal.valueOf(1000)), 0);
        verify(bankingRepository).getBalance(123L);
    }


    @Test
    void testWithdraw_AccountNotFound() {
        WithdrawCommand withdrawCommand = new WithdrawCommand(321L, BigDecimal.valueOf(200));
        when(accountRepository.findByAccountNumber(321L)).thenReturn(Optional.empty());
        Assertions.assertThrows(AccountNotFoundException.class, () -> bank.withdraw(withdrawCommand));
        verify(accountRepository).findByAccountNumber(321L);
    }
}