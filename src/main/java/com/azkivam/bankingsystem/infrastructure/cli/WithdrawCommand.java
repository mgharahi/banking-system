package com.azkivam.bankingsystem.infrastructure.cli;

import com.azkivam.bankingsystem.application.service.Bank;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


import java.math.BigDecimal;

@Component("withdraw")
@RequiredArgsConstructor
public class WithdrawCommand implements CliCommand {
    private final Bank bank;
    private String[] args;

    @Override
    public void run() {
        bank.withdraw(new com.azkivam.bankingsystem.application.service.command.WithdrawCommand(
                Long.parseLong(args[0]),
                BigDecimal.valueOf(Double.parseDouble(args[1]))
        ));
    }

    @Override
    public void setArguments(String[] args) {
        this.args = args;
    }
}