package com.azkivam.bankingsystem.infrastructure.spring.repository;

import com.azkivam.bankingsystem.infrastructure.entity.AccountEntity;
import com.azkivam.bankingsystem.infrastructure.entity.TransactionEntity;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<TransactionEntity, Long> {
}