package com.db.service;

import java.util.HashMap;
import java.util.List;

import com.db.dto.DepositResDto;
import org.springframework.http.ResponseEntity;

import com.db.dto.AccountDto;
import com.db.dto.DepositMoney;
import com.db.entity.Account;

public interface AccountService {

	ResponseEntity<String> createAccount(AccountDto accountDto);

	ResponseEntity<AccountDto> getAccountDetailsByCustomerId(long customerId);

	ResponseEntity<HashMap<String, Double>> getAccountBalance(String accountNumber);

	ResponseEntity<String> accountDeleteByCustomerId(String customerId);

	ResponseEntity<List<Account>> fetchAllAccountDetails();

	ResponseEntity<DepositResDto> depositMoney(DepositMoney depositMoney);

	ResponseEntity<DepositResDto> withdrawMoney(DepositMoney withdrawMoney);
}
