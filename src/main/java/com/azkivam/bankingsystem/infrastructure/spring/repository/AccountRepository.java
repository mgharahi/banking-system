package com.azkivam.bankingsystem.infrastructure.spring.repository;

import com.azkivam.bankingsystem.domain.entity.Account;
import com.azkivam.bankingsystem.infrastructure.entity.AccountEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AccountRepository extends CrudRepository<AccountEntity, String> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select b from AccountEntity b where b.accountNumber = :accountNumber")
    Optional<AccountEntity> findByAccountNumberWithLock(Long accountNumber);

    Optional<AccountEntity> findByAccountNumber(Long accountNumber);
}