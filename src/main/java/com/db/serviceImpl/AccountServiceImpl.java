package com.db.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import com.db.dto.DepositResDto;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.db.commonUtil.AccountValidation;
import com.db.dto.AccountDto;
import com.db.dto.DepositMoney;
import com.db.entity.Account;
import com.db.exception.AccountException;
import com.db.repository.AccountRepository;
import com.db.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {
	
	private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

	@Autowired
	private AccountRepository accountRepository;

	@Override
	public ResponseEntity<String> createAccount(AccountDto accountDto) {
		AccountValidation.validateFields(accountDto);
		if (accountRepository.findByCustomerId(Long.valueOf(accountDto.getCustomerId())) == null) {
			accountRepository.save(AccountValidation.convertDtoToEntity(accountDto));
		} else {
			throw new AccountException("Account Already Exist for this customer...", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>("Account Created Successfull !", HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<AccountDto> getAccountDetailsByCustomerId(long customerId) throws AccountException {
		Account account = accountRepository.findByCustomerId(customerId);
		if (Objects.isNull(account)) {
			throw new AccountException("Account not found !", HttpStatus.NOT_FOUND);
		}
		AccountDto accountDto = AccountValidation.convertEntityToDto(account);
		return ResponseEntity.ok(accountDto);
	}

	@Override
	public ResponseEntity<HashMap<String, Double>> getAccountBalance(String accountNumber) {
		if (Strings.isBlank(accountNumber)) {
			throw new AccountException("Account number should not null or empty !", HttpStatus.BAD_REQUEST);
		}
		if (!NumberUtils.isDigits(accountNumber)) {
			throw new AccountException("accountNumber should be a valid number!", HttpStatus.BAD_REQUEST);
		}
		Account account = accountRepository.findByAccountNumber(accountNumber);
		if (Objects.isNull(account)) {
			throw new AccountException("Account not found for account number :" + accountNumber, HttpStatus.NOT_FOUND);
		}
		HashMap<String, Double> hashMap = new HashMap<>();
		hashMap.put("balance", Double.valueOf(account.getAccountBalance()));
		return ResponseEntity.ok(hashMap);
	}

	@Override
	public ResponseEntity<String> accountDeleteByCustomerId(String customerId) throws AccountException {
		if (Strings.isBlank(customerId)) {
			throw new AccountException("customerId should not null or empty !", HttpStatus.BAD_REQUEST);
		}
		if (!NumberUtils.isDigits(customerId)) {
			throw new AccountException("customerId should be a valid number!", HttpStatus.BAD_REQUEST);
		}
		Account accountDetails = accountRepository.findByCustomerId(Long.valueOf(customerId));
		if (accountDetails != null) {
			accountRepository.delete(accountDetails);
			return new ResponseEntity<String>("Account Deleted successfully for customerId :" + customerId,
					HttpStatus.OK);
		} else {
			throw new AccountException("Account not found for customerId :" + customerId, HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public ResponseEntity<List<Account>> fetchAllAccountDetails() {
		List<Account> listOfAccountDetails = accountRepository.findAll();
		return new ResponseEntity<List<Account>>(listOfAccountDetails, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<DepositResDto> depositMoney(DepositMoney depositMoney) throws AccountException {
		logger.info("Enter into depositMoney");
		if (depositMoney.getAccountNumber() == null) {
			throw new AccountException("customerId should not null or empty !", HttpStatus.BAD_REQUEST);
		}
		Account account = accountRepository.findByAccountNumber(depositMoney.getAccountNumber());
		if (account == null) {
			throw new AccountException("Account not found for account number: " + depositMoney.getAccountNumber(),
					HttpStatus.NOT_FOUND);
		}
		// Validate transaction amount
		if (depositMoney.getTransactionAmount() <= 0) {
			throw new AccountException("Transaction amount should be greater than zero!", HttpStatus.BAD_REQUEST);
		}
		// Calculate and update the account balance
		double accountBalance = account.getAccountBalance();
		double depositedAmount = accountBalance + depositMoney.getTransactionAmount();
		account.setAccountBalance(depositedAmount);

		// Save the updated account
		accountRepository.save(account);

		DepositResDto depositResDto = new DepositResDto();
		depositResDto.setAccountId(String.valueOf(account.getAccountId()));
		depositResDto.setBalance(account.getAccountBalance());
		depositResDto.setAccountNo(account.getAccountNumber());
		depositResDto.setMessage("Amount deposited successfully");

		// Return success response
		return new ResponseEntity<DepositResDto>(depositResDto,HttpStatus.OK);
	}

	@Override
	public ResponseEntity<DepositResDto> withdrawMoney(DepositMoney withdrawMoney) {
		logger.info("Enter into withdrawMoney");
		if (withdrawMoney.getAccountNumber() == null) {
			throw new AccountException("customerId should not null or empty !", HttpStatus.BAD_REQUEST);
		}
		Account account = accountRepository.findByAccountNumber(withdrawMoney.getAccountNumber());
		if (account == null) {
			throw new AccountException("Account not found for account number: " + withdrawMoney.getAccountNumber(),
					HttpStatus.NOT_FOUND);
		}
		// Validate transaction amount
		if (withdrawMoney.getTransactionAmount() <= 0) {
			throw new AccountException("Transaction amount should be greater than zero!", HttpStatus.BAD_REQUEST);
		}
		// Calculate and update the account balance
		double accountBalance = account.getAccountBalance();
		double depositedAmount = accountBalance - withdrawMoney.getTransactionAmount();
		account.setAccountBalance(depositedAmount);

		// Save the updated account
		accountRepository.save(account);

		DepositResDto depositResDto = new DepositResDto();
		depositResDto.setAccountId(String.valueOf(account.getAccountId()));
		depositResDto.setAccountNo(account.getAccountNumber());
		depositResDto.setBalance(account.getAccountBalance());
		depositResDto.setMessage("Amount Withdraw successfully");

		// Return success response
		return new ResponseEntity<DepositResDto>(depositResDto,HttpStatus.OK);
	}

}
