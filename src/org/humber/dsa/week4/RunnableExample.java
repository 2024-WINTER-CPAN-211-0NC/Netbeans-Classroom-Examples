package org.humber.dsa.week4;

class CustomRunnable implements Runnable {

    @Override
    public void run() {
        int i = 3;
        while (i-- > 0) {
            System.out.println("CustomRunnable Thread is running");
        }
    }
}

public class RunnableExample {

    public static void main(String[] args) {
        System.out.println("Main Thread started");
        Runnable customRunnable = new CustomRunnable();
        Thread thread = new Thread(customRunnable);
        thread.start();
        int i=100;
        while (i-- > 0) {
            System.out.println(i);
        }
        System.out.println("Main Thread completed");
    }
}
