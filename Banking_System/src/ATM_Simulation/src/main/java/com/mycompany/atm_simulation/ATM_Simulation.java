package com.mycompany.atm_simulation;

public class ATM_Simulation {
    public static void main(String[] args) {
        ATM atm = new ATM(); // Create an ATM object
        new ATM_GUI(atm);    // Pass the ATM object to the GUI
    }
}
