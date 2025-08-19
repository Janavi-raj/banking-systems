package bankingsystem;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class BankingSystem {
    private Map<String, BankAccount> accounts = new HashMap<>();
    private Scanner sc = new Scanner(System.in);
    private SecureRandom random = new SecureRandom();

    public BankingSystem() {
        // Hardcoded accounts
        accounts.put("12345", new BankAccount("12345", "pass", 5000));
        accounts.put("67890", new BankAccount("67890", "hello", 3000));
    }

    public void start() {
        System.out.println("=== Welcome to Indian Bank ===");
        System.out.print("Enter Account Number: ");
        String accNo = sc.nextLine();
        System.out.print("Enter Password: ");
        String pass = sc.nextLine();

        BankAccount account = authenticate(accNo, pass);

        if (account == null) {
            System.out.println("Invalid credentials. Exiting...");
            return;
        }

        int choice;
        do {
            System.out.println("\n1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Transfer");
            System.out.println("4. Logout");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1 -> handleDeposit(account);
                case 2 -> handleWithdraw(account);
                case 3 -> handleTransfer(account);
                case 4 -> System.out.println("Logging out...");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 4);
    }

    private BankAccount authenticate(String accNo, String pass) {
        BankAccount acc = accounts.get(accNo);
        if (acc != null && acc.getPassword().equals(pass)) {
            return acc;
        }
        return null;
    }

    private void handleDeposit(BankAccount acc) {
        System.out.print("Enter deposit amount: ");
        double amt = sc.nextDouble();
        acc.deposit(amt);
        System.out.println("Deposit successful. Balance: " + acc.getBalance());
    }

    private void handleWithdraw(BankAccount acc) {
        System.out.print("Enter withdrawal amount: ");
        double amt = sc.nextDouble();
        if (acc.withdraw(amt)) {
            System.out.println("Withdrawal successful. Balance: " + acc.getBalance());
        } else {
            System.out.println("Insufficient balance.");
        }
    }

    private void handleTransfer(BankAccount acc) {
        System.out.print("Enter target account number: ");
        String targetNo = sc.next();
        BankAccount target = accounts.get(targetNo);

        if (target == null) {
            System.out.println("Target account not found.");
            return;
        }

        System.out.print("Enter transfer amount: ");
        double amt = sc.nextDouble();

        // Generate OTP
        int otp = 1000 + random.nextInt(9000);
        System.out.println("Generated OTP: " + otp);
        System.out.print("Enter OTP to confirm: ");
        int enteredOtp = sc.nextInt();

        if (enteredOtp == otp) {
            if (acc.transfer(target, amt)) {
                System.out.println("Transfer successful. Balance: " + acc.getBalance());
            } else {
                System.out.println("Insufficient balance.");
            }
        } else {
            System.out.println("Invalid OTP. Transfer cancelled.");
        }
    }
}

