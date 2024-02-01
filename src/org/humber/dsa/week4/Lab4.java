package org.humber.dsa.week4;

class DepositRunnable implements Runnable {
    private final BankAccount bankAccount;

    public DepositRunnable(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    @Override
    public void run() {
        System.out.println("Deposit process started...");
        //TODO: Deposit 1$ and notify the withdraw thread
        //After depositing 1$, wait for 1 second, repeat this process 10 times
        System.out.println("Deposit process completed.");
    }
}

class WithdrawRunnable implements Runnable {
    private final BankAccount bankAccount;

    public WithdrawRunnable(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    @Override
    public void run() {
        System.out.println("Withdrawal process started...");
        //TODO: Withdraw 2$ only if the balance is sufficient
        //Withdraw complete amount before completing the process
        System.out.println("Withdrawal Process Completed.");
    }
}


/**
 * DO NOT CHANGE BankAccount class
 */
class BankAccount {
    private int balance;
    private int totalDepositsMade;
    private int totalWithdrawalsMade;

    public BankAccount(int balance) {
        this.balance = balance;
        this.totalDepositsMade = 0;
        this.totalWithdrawalsMade = 0;
    }

    public synchronized void deposit(int amount) {
        balance += amount;
        totalDepositsMade++;
    }

    public void withdraw(int amount) {
        balance -= amount;
        totalWithdrawalsMade++;
        if(balance < 0){
            throw new RuntimeException("Insufficient balance");
        }
    }

    public int getBalance() {
        return balance;
    }

    public void getStats(){
        System.out.println("Total deposits made: " + totalDepositsMade);
        System.out.println("Total withdrawals made: " + totalWithdrawalsMade);
    }
}
public class Lab4 {

    /**
     * DO NOT CHANGE main method implementation
     */
    public static void main(String[] args) {
        BankAccount bankAccount = new BankAccount(0);
        DepositRunnable depositRunnable = new DepositRunnable(bankAccount);
        WithdrawRunnable withdrawRunnable = new WithdrawRunnable(bankAccount);

        try {
            Thread depositThread = new Thread(depositRunnable);
            Thread withdrawThread = new Thread(withdrawRunnable);

            depositThread.start();
            withdrawThread.start();

            depositThread.join(); //Ensures Deposit thread completes before main thread
            withdrawThread.join(); //Ensures Withdrawal thread completes before main thread

            System.out.println("\n\nFinal balance: " + bankAccount.getBalance());
            bankAccount.getStats();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
