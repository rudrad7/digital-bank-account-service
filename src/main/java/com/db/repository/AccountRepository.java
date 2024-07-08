package com.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.db.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>{

	Account findByCustomerId(long customerId);

	Account findByAccountNumber(String accountNumber);

}
