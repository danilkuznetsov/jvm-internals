package bank;

/**
 * @author Danil Kuznetsov (kuznetsov.danil.v@gmail.com)
 */
public class Account {

    int id;
    int amount;

    public Account(int id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    public void deposit(int amount) {
        this.amount += amount;
    }

    public void withdraw(int amount) {
        this.amount -= amount;
    }

    public int getAmount() {
        return amount;
    }
}
