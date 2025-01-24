package com.azkivam.bankingsystem.application.service.command;

import java.math.BigDecimal;

public record WithdrawCommand(Long source, BigDecimal amount) {
}
