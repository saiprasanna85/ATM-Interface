import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static List<User> users = new ArrayList<>();

    public static void main(String[] args) {
        // Create some sample users for demonstration
        User user1 = new User("1234", "1111");
        User user2 = new User("5678", "2222");
        users.add(user1);
        users.add(user2);

        // Prompt for user ID and PIN
        System.out.print("Enter user ID: ");
        String userID = scanner.nextLine();
        System.out.print("Enter PIN: ");
        String pin = scanner.nextLine();

        // Authenticate user
        User currentUser = authenticateUser(userID, pin);
        if (currentUser != null) {
            System.out.println("Login successful!");

            // ATM functionalities unlocked
            ATM atm = new ATM(currentUser, users);
            atm.start();
        } else {
            System.out.println("Invalid credentials. Login failed.");
        }
    }

    private static User authenticateUser(String userID, String pin) {
        for (User user : users) {
            if (user.getUserID().equals(userID) && user.getPin().equals(pin)) {
                return user;
            }
        }
        return null;
    }
}

class ATM {
    private User currentUser;
    private Scanner scanner = new Scanner(System.in);
    private List<User> users;

    public ATM(User user, List<User> users) {
        currentUser = user;
        this.users = users;
    }

    public void start() {
        System.out.println("Welcome, " + currentUser.getUserID() + "!");
        boolean quit = false;

        while (!quit) {
            System.out.println("\nPlease select an option:");
            System.out.println("1. View Transaction History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");

            System.out.print("Enter your choice: ");
            String choiceStr = scanner.nextLine();
            int choice;
            try {
                choice = Integer.parseInt(choiceStr);
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice. Please try again.");
                continue;
            }

            switch (choice) {
                case 1:
                    viewTransactionHistory();
                    break;
                case 2:
                    withdraw();
                    break;
                case 3:
                    deposit();
                    break;
                case 4:
                    transfer();
                    break;
                case 5:
                    quit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void viewTransactionHistory() {
        List<Transaction> transactions = currentUser.getTransactionHistory();
        System.out.println("Transaction History:");
        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
    }

    private void withdraw() {
        System.out.print("Enter amount to withdraw: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume the newline character

        if (amount <= 0) {
            System.out.println("Invalid amount. Please try again.");
            return;
        }

        if (currentUser.getBalance() >= amount) {
            currentUser.setBalance(currentUser.getBalance() - amount);
            Transaction transaction = new Transaction(TransactionType.WITHDRAWAL, amount);
            currentUser…