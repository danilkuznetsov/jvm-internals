package bank;

import java.util.List;
import java.util.stream.IntStream;

/**
 * @author Danil Kuznetsov (kuznetsov.danil.v@gmail.com)
 */
public class MyBank {

    List<Account> accounts;

    // it is not good choice, because after that we will have one-thread bank
    // one transfer one time
    final Object lock = new Object();

    public MyBank(List<Account> accounts) {
        this.accounts = accounts;
    }

    public void transfer(int fromAccId, int toAccId, int amount) {

//        System.out.println("Transfer in " + Thread.currentThread().getName());

        Account fromAcc = this.accounts.stream().filter(account -> account.id == fromAccId).findAny().orElseThrow();
        Account toAcc = this.accounts.stream().filter(account -> account.id == toAccId).findAny().orElseThrow();


        // not sync version
        fromAcc.withdraw(amount);
        toAcc.deposit(amount);

        // case with deadlock form two threads
//        synchronized (fromAcc) {
//            synchronized (toAcc) {
//                fromAcc.withdraw(amount);
//                toAcc.deposit(amount);
//            }
//        }
        // it is not good choice, because after that we will have one-thread bank
        // one transfer one time
//       synchronized (lock) {
//            fromAcc.withdraw(amount);
//            toAcc.deposit(amount);
//        }


        // solution for this task is ordering by account id for selecting a account for using with section synchronized
    }

    public int total() {
        return this.accounts.stream().mapToInt(Account::getAmount).sum(); // sum(accounts)
    }

    public static void main(String[] args) throws InterruptedException {

        MyBank bank = new MyBank(List.of(new Account(1, 1000), new Account(2, 2000)));

        System.out.println(bank.total());

        Thread firstThread = new Thread(() -> {
            IntStream.range(1, 1000)
                    .parallel()
                    .forEach(
                            transfer -> bank.transfer(2, 1, transfer)
                    );

            System.out.println("Thread with id" + Thread.currentThread().getName() + " has done");
        });

        Thread secondTread = new Thread(() -> {
            IntStream.range(1, 100000)
                    .parallel()
                    .forEach(
                            transfer -> bank.transfer(1, 2, transfer)
                    );

            System.out.println("Thread with id" + Thread.currentThread().getName() + " has done");
        });

        firstThread.start();
        secondTread.start();

        Thread.sleep(5000);
        System.out.println(bank.total());
    }
}
