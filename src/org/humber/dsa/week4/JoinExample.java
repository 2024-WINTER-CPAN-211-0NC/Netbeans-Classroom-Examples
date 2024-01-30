package org.humber.dsa.week4;


class NamedThread extends Thread {
    private final String name;

    public NamedThread(String name) {
        this.name = name;
    }

    public void run() {
        System.out.println(name + " is running.");
        try {
            int i=0;
            while(i++ < 10){
                System.out.println(name +": executing loop " + i);
            }
            Thread.sleep(2000); // Simulate some work for 2 seconds
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        System.out.println(name + " is done.");
    }
}

public class JoinExample {
    public static void main(String[] args) {

        NamedThread t1 = new NamedThread("T1");
        NamedThread t2 = new NamedThread("T2");
        NamedThread t3 = new NamedThread("T3");

        try {
            t2.start();
/*
            System.out.println("Waiting for Thread 2 to finish.");
            t2.join(); // t1,t3, and Main thread waits for Thread 2 to finish
*/

            t3.start();
/*
            System.out.println("Waiting for Thread 3 to finish.");
            t3.join(); // t1, and Main thread waits for Thread 3 to finish
*/

            t1.start();
/*
            System.out.println("Waiting for Thread 1 to finish.");
            t1.join(); // Main thread waits for Thread 1 to finish
*/
        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println("Main thread is done.");
    }
}
