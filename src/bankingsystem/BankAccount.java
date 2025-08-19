package bankingsystem;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class BankAccount {
    private String accountNo;
    private String password;
    private double balance;

    public BankAccount(String accountNo, String password, double balance) {
        this.accountNo = accountNo;
        this.password = password;
        this.balance = balance;
    }

    public String getAccountNo() { return accountNo; }
    public String getPassword() { return password; }
    public double getBalance() { return balance; }

    public void deposit(double amount) {
        balance += amount;
        recordTransaction("Deposit: +" + amount);
    }

    public boolean withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            recordTransaction("Withdraw: -" + amount);
            return true;
        }
        return false;
    }

    public boolean transfer(BankAccount target, double amount) {
        if (amount <= balance) {
            balance -= amount;
            target.balance += amount;
            recordTransaction("Transfer: -" + amount + " to " + target.getAccountNo());
            target.recordTransaction("Transfer: +" + amount + " from " + this.getAccountNo());
            return true;
        }
        return false;
    }

    private void recordTransaction(String message) {
        try (FileWriter fw = new FileWriter("transactions.txt", true)) {
            fw.write(LocalDateTime.now() + " [" + accountNo + "] " + message + 
                     " | Balance: " + balance + "\n");
        } catch (IOException e) {
            System.out.println("Error recording transaction: " + e.getMessage());
        }
    }
}

