package com.db.dto;

public class DepositMoney {

	private String accountNumber;
	private double transactionAmount;
	public DepositMoney() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DepositMoney(String accountNumber, double transactionAmount) {
		super();
		this.accountNumber = accountNumber;
		this.transactionAmount = transactionAmount;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public double getTransactionAmount() {
		return transactionAmount;
	}
	public void setTransactionAmount(double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}
	
	
}
