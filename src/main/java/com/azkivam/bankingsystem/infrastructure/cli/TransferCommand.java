package com.azkivam.bankingsystem.infrastructure.cli;

import com.azkivam.bankingsystem.application.service.Bank;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component("transfer")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
public class TransferCommand implements CliCommand {
    private final Bank bank;
    private String[] args;

    @Override
    public void run() {
        bank.transfer(new com.azkivam.bankingsystem.application.service.command.TransferCommand(
                Long.parseLong(args[0]),
                Long.parseLong(args[1]),
                BigDecimal.valueOf(Double.parseDouble(args[2]))
        ));
    }

    @Override
    public void setArguments(String[] args) {
        this.args = args;
    }
}