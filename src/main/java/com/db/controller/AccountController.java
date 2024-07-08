package com.db.controller;

import java.util.HashMap;
import java.util.List;

import javax.security.auth.login.AccountException;

import com.db.dto.DepositResDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.db.dto.AccountDto;
import com.db.dto.DepositMoney;
import com.db.entity.Account;
import com.db.service.AccountService;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/create")
    public ResponseEntity<String> createAccount(@RequestBody AccountDto accountDto) throws AccountException {
        return accountService.createAccount(accountDto);
    }

    @GetMapping("/getAccount/{customerId}")
    public ResponseEntity<AccountDto> getAccountDetailsByCustomerId(@PathVariable long customerId) {
        return accountService.getAccountDetailsByCustomerId(customerId);
    }

    @GetMapping("/getBalance/{accountNumber}")
    public ResponseEntity<HashMap<String, Double>> getAccountBalance(@PathVariable String accountNumber) {
        return accountService.getAccountBalance(accountNumber);
    }

    @DeleteMapping("/delete/{customerId}")
    public ResponseEntity<String> accountDeleteByCustomerId(@PathVariable String customerId) {
        return accountService.accountDeleteByCustomerId(customerId);
    }

    @GetMapping("/getAllAccounts")
    public ResponseEntity<List<Account>> fetchAllAccountDetails() {
        return accountService.fetchAllAccountDetails();
    }

    @PostMapping("/transaction/deposit")
    public ResponseEntity<DepositResDto> depositMoney(@RequestBody DepositMoney depositMoney) {
        return accountService.depositMoney(depositMoney);
    }

	@PostMapping("/transaction/withdraw")
	public ResponseEntity<DepositResDto> withdrawMoney(@RequestBody DepositMoney withdrawMoney) {
		return accountService.withdrawMoney(withdrawMoney);
	}
}
