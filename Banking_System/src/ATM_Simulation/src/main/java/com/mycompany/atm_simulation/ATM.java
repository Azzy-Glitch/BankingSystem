package com.mycompany.atm_simulation;

import java.io.*;

public class ATM {

    private CustomLinkedList<User> users;

    public ATM() {
        users = new CustomLinkedList<>();
        loadUsers();
    }

    public void addUser(User user) {
        users.add(user);
    }

//    public User findUser(String pin) {
//        for (int i = 0; i < users.size(); i++) {
//            User user = users.get(i);
//            if (user.validatePin(pin)) {
//                return user;
//            }
//        }
//        return null;
//    }
    public void performDeposit(User user, double amount) {
        user.updateBalance(user.getBalance() + amount);
        user.addTransaction("Deposited: $" + amount);
    }

    public boolean performWithdrawal(User user, double amount) {
        if (user.getBalance() >= amount) {
            user.updateBalance(user.getBalance() - amount);
            user.addTransaction("Withdrew: $" + amount);
            return true;
        } else {
            return false;
        }
    }

    public void saveUsers() {
        try (PrintWriter writer = new PrintWriter(new File("users.txt"))) {
            for (int i = 0; i < users.size(); i++) {
                User user = users.get(i);
                writer.println(user.getPin() + "," + user.getBalance());
            }
        } catch (IOException e) {
            System.out.println("Error saving user data: " + e.getMessage());
        }
    }

    public void loadUsers() {
        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String pin = parts[0];
                double balance = Double.parseDouble(parts[1]);
                users.add(new User(pin, balance));
                System.out.println("Loaded User: PIN = " + pin + ", Balance = $" + balance); // Debug
            }
        } catch (IOException e) {
            System.out.println("Error loading user data: " + e.getMessage());
        }
    }

    public User findUser(String pin) {
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            System.out.println("Checking PIN: " + user.getPin()); // Debug
            if (user.validatePin(pin)) {
                return user;
            }
        }
        return null;
    }
}
