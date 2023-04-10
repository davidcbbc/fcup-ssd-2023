package Blockchain;

public class User {
    private String address;
    private double balance;

    public User(String address, double balance) {
        this.address = address;
        this.balance = balance;
    }

    public double getBalance() {
        return this.balance;
    }

    public String getAddress() {
        return this.address;
    }

    public void addBalance(double amount) {
        this.balance += amount;
    }

    public void subtractBalance(double amount) {
        this.balance -= amount;
    }
}
