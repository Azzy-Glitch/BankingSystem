import java.util.Scanner;

// Custom Queue Implementation
class CustomQueue<T> 
{
    private static class Node<T> 
    {
        T data;
        Node<T> next;

        Node(T data) 
        {
            this.data = data;
            this.next = null;
        }
    }

    private Node<T> front;
    private Node<T> rear;
    private int size;

    public CustomQueue()
    {
        front = null;
        rear = null;
        size = 0;
    }

    public void enqueue(T data) 
    {
        Node<T> newNode = new Node<>(data);
        if (rear == null) 
        {
            front = rear = newNode;
        } 
        
        else 
        {
            rear.next = newNode;
            rear = newNode;
        }
        size++;
    }

    public T dequeue() 
    {
        if (front == null) 
        {
            throw new IllegalStateException("Queue is empty");
        }
        T data = front.data;
        front = front.next;
        if (front == null) 
        {
            rear = null;
        }
        size--;
        return data;
    }

    public T peek() 
    {
        if (front == null) 
        {
            throw new IllegalStateException("Queue is empty");
        }
        return front.data;
    }

    public boolean isEmpty() 
    {
        return size == 0;
    }

    public int size() 
    {
        return size;
    }
}

// Custom LinkedList Implementation
class CustomLinkedList<T> 
{
    private static class Node<T> 
    {
        T data;
        Node<T> next;

        Node(T data) 
        {
            this.data = data;
            this.next = null;
        }
    }

    private Node<T> head;
    private int size;

    public CustomLinkedList() 
    {
        head = null;
        size = 0;
    }

    public void add(T data) 
    {
        Node<T> newNode = new Node<>(data);
        if (head == null) 
        {
            head = newNode;
        } 
        
        else 
        {
            Node<T> temp = head;
            while (temp.next != null) 
            {
                temp = temp.next;
            }
            temp.next = newNode;
        }
        size++;
    }

    public T get(int index) 
    {
        if (index < 0 || index >= size) 
        {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }

        Node<T> temp = head;
        for (int i = 0; i < index; i++) 
        {
            temp = temp.next;
        }
        return temp.data;
    }

    public int size()
    {
        return size;
    }

    public boolean isEmpty() 
    {
        return size == 0;
    }
}

// User Class
class User {
    private String pin;
    private double balance;
    private CustomQueue<String> transactionHistory;

    public User(String pin, double initialBalance) 
    {
        this.pin = pin;
        this.balance = initialBalance;
        this.transactionHistory = new CustomQueue<>();
    }

    public boolean validatePin(String inputPin) 
    {
        return this.pin.equals(inputPin);
    }

    public void setPin(String newPin) 
    {
        this.pin = newPin;
    }

    public double getBalance() 
    {
        return balance;
    }

    public void setBalance(double balance) 
    {
        this.balance = balance;
    }

    public void addTransaction(String transaction) 
    {
        transactionHistory.enqueue(transaction);
        if (transactionHistory.size() > 5) 
        {
            transactionHistory.dequeue();
        }
    }

    public CustomQueue<String> getTransactionHistory() 
    {
        return transactionHistory;
    }
}

// ATM Class
class ATM {
    private CustomLinkedList<User> users;

    public ATM() 
    {
        users = new CustomLinkedList<>();
    }

    public void addUser(User user) 
    {
        users.add(user);
    }

    public User findUser(String pin) 
    {
        for (int i = 0; i < users.size(); i++) 
        {
            User user = users.get(i);
            if (user.validatePin(pin)) 
            {
                return user;
            }
        }
        return null;
    }

    public void performDeposit(User user, double amount) 
    {
        user.setBalance(user.getBalance() + amount);
        user.addTransaction("Deposited: " + amount);
    }

    public boolean performWithdrawal(User user, double amount) 
    {
        if (user.getBalance() >= amount) {
            user.setBalance(user.getBalance() - amount);
            user.addTransaction("Withdrew: " + amount);
            return true;
        } 
        
        else 
        {
            return false;
        }
    }

    public void performTransfer(User sender, User receiver, double amount) 
    {
        if (sender.getBalance() >= amount) 
        {
            sender.setBalance(sender.getBalance() - amount);
            receiver.setBalance(receiver.getBalance() + amount);
            sender.addTransaction("Transferred: " + amount + " to User");
            receiver.addTransaction("Received: " + amount + " from User");
        }
    }
}

public class Main {
    public static void main(String[] args) 
    {
        try (Scanner scanner = new Scanner(System.in)) 
        {
            ATM atm = new ATM();

            atm.addUser(new User("1234", 1000));
            atm.addUser(new User("2917", 2000));

            while (true) 
            {
                System.out.println("Welcome to the ATM");
                int attempts = 3;
                User user = null; 

                while (attempts > 0)
                {
                    System.out.print("Enter your PIN: ");
                    String pin = scanner.nextLine();

                    user = atm.findUser(pin);
                    if (user != null) 
                    {
                        break;
                    }
                    attempts--;
                    if (attempts > 0)
                    {
                        System.out.println("Invalid PIN. Attempts remaining: " + attempts);
                    } 
                    
                    else
                    {
                        System.out.println("Invalid PIN. Please contact customer service.");
                        return;
                    }
                }

                System.out.println("Login successful.");

                while (true) {
                    System.out.println("\n1. Check Balance");
                    System.out.println("2. Deposit");
                    System.out.println("3. Withdraw");
                    System.out.println("4. View Transaction History");
                    System.out.println("5. Forgot PIN");
                    System.out.println("6. Exit");
                    System.out.print("Choose an option: ");

                    int choice = scanner.nextInt();
                    scanner.nextLine();

                    switch (choice) 
                    {
                        case 1:
                            System.out.println("Balance: " + user.getBalance());
                            break;
                        case 2:
                            System.out.print("Enter deposit amount: ");
                            double deposit = scanner.nextDouble();
                            scanner.nextLine();
                            atm.performDeposit(user, deposit);
                            System.out.println("Deposit successful.");
                            break;
                        case 3:
                            System.out.print("Enter withdrawal amount: ");
                            double withdrawal = scanner.nextDouble();
                            scanner.nextLine();
                            if (atm.performWithdrawal(user, withdrawal)) 
                            {
                                System.out.println("Withdrawal successful.");
                            } 
                            
                            else 
                            {
                                System.out.println("Insufficient balance.");
                            }
                            break;
                        case 4:
                            System.out.println("Transaction History:");
                            CustomQueue<String> history = user.getTransactionHistory();
                            while (!history.isEmpty())
                            {
                                System.out.println(history.dequeue());
                            }
                            break;
                        case 5:
                            System.out.print("Enter new PIN: ");
                            String newPin = scanner.nextLine();
                            user.setPin(newPin);
                            System.out.println("PIN reset successful.");
                            break;
                        case 6:
                            System.out.println("Thank you for using the ATM.");
                            return;
                        default:
                            System.out.println("Invalid choice. Try again.");
                    }
                }
            }
        } // Scanner will be closed here
    }
}
