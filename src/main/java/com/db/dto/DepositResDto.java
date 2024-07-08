package com.db.dto;

public class DepositResDto {

    private String accountNo;
    private double balance;
    private String accountId;
    private String message;

    public DepositResDto(String accountNo) {
        this.accountNo = accountNo;
    }

    public DepositResDto() {
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
