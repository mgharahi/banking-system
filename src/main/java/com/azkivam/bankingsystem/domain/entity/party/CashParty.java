package com.azkivam.bankingsystem.domain.entity.party;

public class CashParty implements TransactionParty {
    @Override
    public PartyType getType() {
        return PartyType.CASH;
    }

    @Override
    public String toString() {
        return "Cash";
    }
}