package com.nurs.moneyApp.models;

public class Account {

    private double accountAmount;
    private String accountName;

    private int accountColor;

    public Account() {
    }

    public Account(double accountAmount, String accountName, int accountColor) {
        this.accountAmount = accountAmount;
        this.accountName = accountName;
        this.accountColor = accountColor;
    }

    public int getAccountColor() {
        return accountColor;
    }

    public void setAccountColor(int accountColor) {
        this.accountColor = accountColor;
    }

    public double getAccountAmount() {
        return accountAmount;
    }

    public void setAccountAmount(double accountAmount) {
        this.accountAmount = accountAmount;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
}
