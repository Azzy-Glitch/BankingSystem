package com.mycompany.atm_simulation;

public class Main {
    public static void main(String[] args) {
        ATM atm = new ATM(); // Initialize ATM logic
        new ATM_GUI(atm);    // Pass ATM object to GUI
    }
}
