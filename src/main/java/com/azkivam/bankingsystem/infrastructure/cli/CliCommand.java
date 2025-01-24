package com.azkivam.bankingsystem.infrastructure.cli;

public interface CliCommand extends Runnable {
    void setArguments(String[] args);
}
