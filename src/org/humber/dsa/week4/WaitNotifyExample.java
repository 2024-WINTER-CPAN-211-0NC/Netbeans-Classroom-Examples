package org.humber.dsa.week4;

class Message {
    private String message;
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}

class Producer implements Runnable {
    private final Message message;
    public Producer(Message message) {
        this.message = message;
    }
    @Override
    public void run() {
        System.out.println("Producer started...");
        synchronized (message) {
            try {
                Thread.sleep(500);
                System.out.println("Producing message...");
                Thread.sleep(2000);
                message.setMessage("Hello World!");
                message.notify();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Producer completed.");
    }
}

class Consumer implements Runnable {
    private final Message message;
    public Consumer(Message message) {
        this.message = message;
    }
    @Override
    public void run() {
        System.out.println("Consumer started...");
        synchronized (message) {
            while (message.getMessage() == null){
                try {
                    System.out.println("Message is not available yet. Waiting...");
                    message.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Consumer has consumed the message: " + message.getMessage());
        }
        System.out.println("Consumer completed.");
    }
}
public class WaitNotifyExample {

    public static void main(String[] args) {
        try {
            Message message = new Message();

            Consumer consumer = new Consumer(message);
            Thread consumerThread = new Thread(consumer);
            consumerThread.start();

            Thread.sleep(1000); //Giving 5 secs for Producer to start

            Producer producer = new Producer(message);
            Thread producerThread = new Thread(producer);
            producerThread.start();

            producerThread.join();
            consumerThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Main thread is done.");
    }
}
