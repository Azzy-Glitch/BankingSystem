package com.mycompany.atm_simulation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ATM_GUI {

    private JFrame frame;
    private JTextField pinField, amountField;
    private JTextArea displayArea;
    private JButton loginButton, checkBalanceButton, depositButton, withdrawButton, exitButton;

    private ATM atm;
    private User currentUser;

    public ATM_GUI(ATM atm) {
        this.atm = atm; // Pass ATM object
        initialize();
    }

    private void initialize() {
        // Frame setup
        frame = new JFrame("ATM Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new BorderLayout());

        // PIN Panel
        JPanel pinPanel = new JPanel();
        pinField = new JTextField(10);
        loginButton = new JButton("Login");
        pinPanel.add(new JLabel("Enter PIN:"));
        pinPanel.add(pinField);
        pinPanel.add(loginButton);

        // Display Area
        displayArea = new JTextArea(10, 30);
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1));
        checkBalanceButton = new JButton("Check Balance");
        depositButton = new JButton("Deposit");
        withdrawButton = new JButton("Withdraw");
        exitButton = new JButton("Exit");

        buttonPanel.add(checkBalanceButton);
        buttonPanel.add(depositButton);
        buttonPanel.add(withdrawButton);
        buttonPanel.add(exitButton);

        // Adding components to the frame
        frame.add(pinPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Action Listeners
        loginButton.addActionListener(e -> login());
        checkBalanceButton.addActionListener(e -> checkBalance());
        depositButton.addActionListener(e -> deposit());
        withdrawButton.addActionListener(e -> withdraw());
        exitButton.addActionListener(e -> exit());

        frame.setVisible(true);
    }

    private void login() {
        String pin = pinField.getText();
        System.out.println("Entered PIN: " + pin); // Debug
        currentUser = atm.findUser(pin);

        if (currentUser != null) {
            System.out.println("Login Successful for PIN: " + pin); // Debug
            displayArea.setText("Login Successful!\nWelcome to the ATM.");
        } else {
            System.out.println("Invalid PIN: " + pin); // Debug
            displayArea.setText("Invalid PIN. Please try again.");
        }
    }

    private void checkBalance() {
        if (currentUser != null) {
            displayArea.setText("Your current balance is: $" + currentUser.getBalance());
        } else {
            displayArea.setText("Please login first.");
        }
    }

    private void deposit() {
        if (currentUser != null) {
            String input = JOptionPane.showInputDialog("Enter deposit amount:");
            try {
                double amount = Double.parseDouble(input);
                atm.performDeposit(currentUser, amount);
                displayArea.setText("Deposit successful!\nNew Balance: $" + currentUser.getBalance());
            } catch (NumberFormatException ex) {
                displayArea.setText("Invalid amount entered.");
            }
        } else {
            displayArea.setText("Please login first.");
        }
    }

    private void withdraw() {
        if (currentUser != null) {
            String input = JOptionPane.showInputDialog("Enter withdrawal amount:");
            try {
                double amount = Double.parseDouble(input);
                if (atm.performWithdrawal(currentUser, amount)) {
                    displayArea.setText("Withdrawal successful!\nNew Balance: $" + currentUser.getBalance());
                } else {
                    displayArea.setText("Insufficient balance.");
                }
            } catch (NumberFormatException ex) {
                displayArea.setText("Invalid amount entered.");
            }
        } else {
            displayArea.setText("Please login first.");
        }
    }

    private void exit() {
        atm.saveUsers();
        JOptionPane.showMessageDialog(frame, "Thank you for using the ATM.");
        System.exit(0);
    }
}
