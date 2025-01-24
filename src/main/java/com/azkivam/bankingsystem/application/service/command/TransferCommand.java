package com.azkivam.bankingsystem.application.service.command;

import java.math.BigDecimal;

public record TransferCommand(Long source, Long destination, BigDecimal amount) {
}
