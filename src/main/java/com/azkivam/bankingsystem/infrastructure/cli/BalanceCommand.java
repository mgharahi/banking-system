package com.azkivam.bankingsystem.infrastructure.cli;

import com.azkivam.bankingsystem.application.service.Bank;
import com.azkivam.bankingsystem.application.service.query.BalanceQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("balance")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
public class BalanceCommand implements CliCommand {
    private final Bank bank;
    private String[] args;

    @Override
    public void run() {
        System.out.printf("The balance is: %s",bank.getBalance(new BalanceQuery(Long.parseLong(args[0]))));
        System.out.println();
    }

    @Override
    public void setArguments(String[] args) {
        this.args = args;
    }
}