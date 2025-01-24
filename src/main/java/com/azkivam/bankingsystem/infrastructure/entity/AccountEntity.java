package com.azkivam.bankingsystem.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "ACCOUNTS")
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountNumber;

    @OneToMany(mappedBy = "accountEntity")
    private Set<TransactionEntity> transactionEntities;

    private BigDecimal balance;
}