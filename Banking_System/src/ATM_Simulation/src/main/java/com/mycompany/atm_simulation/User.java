package com.mycompany.atm_simulation;

public class User {
    private String pin;
    private double balance;
    private CustomQueue<String> transactionHistory;

    public User(String pin, double balance) {
        this.pin = pin;
        this.balance = balance;
        this.transactionHistory = new CustomQueue<>();
    }

    public boolean validatePin(String inputPin) { return pin.equals(inputPin); }
    public void setPin(String newPin) { pin = newPin; }
    public double getBalance() { return balance; }
    public void updateBalance(double amount) { balance = amount; }
    public void addTransaction(String transaction) { transactionHistory.enqueue(transaction); }
    public String getPin() { return pin; }
}