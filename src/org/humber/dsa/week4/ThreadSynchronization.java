package org.humber.dsa.week4;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class CountKeeper {

    private static int count = 0;
    private static int faultyCount = 0;

    public synchronized static void increment() {
        count = count + 1;
    }

    public static void faultyIncrement() {
        faultyCount = faultyCount + 1;
    }

    public static int getCount() {
        return count;
    }

    public static int getFaultyCount() {
        return faultyCount;
    }
}

class PrintChars implements Runnable {

    private final Object obj;
    private int times;

    public PrintChars(Object obj, int times) {
        this.obj = obj;
        this.times = times;
    }

    @Override
    public void run() {
        while (times-- > 0) {
            System.out.print(obj);
            CountKeeper.faultyIncrement();
            CountKeeper.increment();
        }
    }
}


public class ThreadSynchronization {


    public static void main(String[] args) throws InterruptedException {

        ExecutorService service = Executors.newFixedThreadPool(3);

        service.execute(new PrintChars("a", 100000));
        service.execute(new PrintChars("b", 100000));
        service.execute(new PrintChars(1, 100000));
        service.shutdown();
        service.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

        System.out.println("\n\nTotal Count with Synchronization = " + CountKeeper.getCount());
        System.out.println("Total Count without Synchronization = " + CountKeeper.getFaultyCount());
    }

}
