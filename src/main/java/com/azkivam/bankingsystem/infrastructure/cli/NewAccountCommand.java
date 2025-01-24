package com.azkivam.bankingsystem.infrastructure.cli;

import com.azkivam.bankingsystem.application.service.Bank;
import com.azkivam.bankingsystem.application.service.command.CreateAccountCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component("new")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
public class NewAccountCommand implements CliCommand {

    private final Bank bank;

    private String[] args;

    @Override
    public void run() {
        bank.createAccount(new CreateAccountCommand(BigDecimal.valueOf(Double.parseDouble(args[0]))));
    }

    @Override
    public void setArguments(String[] args) {
        this.args = args;
    }
}