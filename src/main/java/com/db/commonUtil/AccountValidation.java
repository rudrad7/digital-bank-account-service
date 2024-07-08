package com.db.commonUtil;

import java.util.Random;

import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;

import com.db.dto.AccountDto;
import com.db.entity.Account;
import com.db.exception.AccountException;

public class AccountValidation {

	public static void validateFields(AccountDto accountDto) throws AccountException {
		if (Strings.isEmpty(accountDto.getAccountType())) {
			throw new AccountException("Account type cannot be blank", HttpStatus.BAD_REQUEST);
		}
		if (accountDto.getCustomerId() == null || accountDto.getCustomerId().isBlank()) {
			throw new AccountException("CustomerId should not empty or null !", HttpStatus.BAD_REQUEST);
		}
		if (Strings.isEmpty(accountDto.getAccountStatus())) {
			accountDto.setAccountStatus(CommonUtil.ACTIVE);
		}
	}

	public static String generateRandom10DigitNumber() {
		Random random = new Random();
		int firstDigit = random.nextInt(9) + 1;
		long remainingDigits = (long) (random.nextDouble() * 1_000_000_000L);
		String randomNumber = String.valueOf(firstDigit) + String.format("%09d", remainingDigits);
		return randomNumber;
	}

	public static Account convertDtoToEntity(AccountDto accountDto) {
		Account account = new Account();
		account.setAccountNumber(generateRandom10DigitNumber());
		account.setCustomerId(Long.valueOf(accountDto.getCustomerId()));
		account.setAccountStatus(accountDto.getAccountStatus());
		account.setAccountType(accountDto.getAccountType());
		return account;
	}

	public static AccountDto convertEntityToDto(Account account){
		try {
			AccountDto accountDto = new AccountDto();
			accountDto.setAccountNumber(account.getAccountNumber());
			accountDto.setAccountBalance(Double.valueOf(account.getAccountBalance()));
			accountDto.setAccountId(account.getAccountId());
			accountDto.setAccountStatus(account.getAccountStatus());
			accountDto.setAccountType(account.getAccountType());
			accountDto.setCustomerId(String.valueOf(account.getCustomerId()));
			return accountDto;
		} catch (Exception e) {
			throw new AccountException(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}

}
