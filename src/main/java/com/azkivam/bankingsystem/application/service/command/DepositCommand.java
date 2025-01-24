package com.azkivam.bankingsystem.application.service.command;

import java.math.BigDecimal;

public record DepositCommand(Long destination, BigDecimal amount) {
}
