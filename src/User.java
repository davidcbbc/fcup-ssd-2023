public class User {
    private String address;
    private int balance;

    public User(String address) {
        this.address = address;
        this.balance = 0;
    }

    public int getBalance() {
        return this.balance;
    }

    public String getAddress() {
        return this.address;
    }

    public void addBalance(int amount) {
        this.balance += amount;
    }

    public void subtractBalance(int amount) {
        this.balance -= amount;
    }
}
