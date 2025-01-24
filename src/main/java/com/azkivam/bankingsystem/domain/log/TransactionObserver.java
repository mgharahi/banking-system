package com.azkivam.bankingsystem.domain.log;

import com.azkivam.bankingsystem.domain.entity.party.TransactionParty;

import java.math.BigDecimal;

public interface TransactionObserver {
    void update(TransactionParty sourceAccount, TransactionParty destinationAccount, BigDecimal amount);
}
