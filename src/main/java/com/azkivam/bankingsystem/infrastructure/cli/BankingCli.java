package com.azkivam.bankingsystem.infrastructure.cli;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@RequiredArgsConstructor
public class BankingCli implements CommandLineRunner {

    private final ApplicationContext applicationContext;

    private boolean running = true;

    @Override
    public void run(String... args) {

        try (Scanner scanner = new Scanner(System.in);
             ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            while (running) {
                System.out.print("What would u like to do? ");
                String input = scanner.nextLine();

                String[] commandArgs = input.split("\\s+");

                String command = commandArgs[0].toLowerCase();
                if ("exit".equals(command)) {
                    running = false;
                } else {
                    var commandBean = (CliCommand) applicationContext.getBean(command);
                    commandBean.setArguments(Arrays.copyOfRange(commandArgs, 1, commandArgs.length));

                    executorService.submit(commandBean);
                }
            }
            executorService.shutdown();
        }
    }
}