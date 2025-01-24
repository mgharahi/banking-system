package com.azkivam.bankingsystem.infrastructure.cli;

import com.azkivam.bankingsystem.application.service.Bank;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component("deposit")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
public class DepositCommand implements CliCommand {
    private final Bank bank;
    private String[] args;

    @Override
    public void run() {
        bank.deposit(new com.azkivam.bankingsystem.application.service.command.DepositCommand(
                Long.parseLong(args[0]),
                BigDecimal.valueOf(Double.parseDouble(args[1]))
        ));
    }

    @Override
    public void setArguments(String[] args) {
        this.args = args;
    }
}