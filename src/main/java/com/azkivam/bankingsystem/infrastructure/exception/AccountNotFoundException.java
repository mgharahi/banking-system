package com.azkivam.bankingsystem.infrastructure.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AccountNotFoundException extends RuntimeException {
    private Long accountNumber;
}
