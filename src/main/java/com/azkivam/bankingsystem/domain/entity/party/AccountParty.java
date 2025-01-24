package com.azkivam.bankingsystem.domain.entity.party;

import com.azkivam.bankingsystem.domain.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AccountParty implements TransactionParty {

    private Account account;

    @Override
    public PartyType getType() {
        return PartyType.ACCOUNT;
    }

    @Override
    public String toString() {
        return account.toString();
    }
}