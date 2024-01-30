package org.humber.dsa.week4;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class ListSum implements Callable<Integer> {

    private final List<Integer> list;
    private final String name;

    ListSum(List<Integer> list, String name) {
        this.list = list;
        this.name = name;
    }

    @Override
    public Integer call() throws Exception {
        int sum = 0;
        for (Integer each : list) {
            sum += each;
        }
        System.out.println("Sum of " + name + " is " + sum + " and thread is " + Thread.currentThread().getName());
        return sum;
    }
}

public class SumOfListThreadsExample {


    public static void main(String[] args) {
        try {
            List<Integer> integerList = new ArrayList<>();
            int i = 1;
            while (i <= 1000) {
                integerList.add(i++);
            }

            List<ListSum> partitions = partitionList(integerList, 8);

            //Create Threads
            ExecutorService executorService = Executors.newFixedThreadPool(4);

            List<Future<Integer>> listSumFutures = new ArrayList<>();
            listSumFutures = executorService.invokeAll(partitions);
            int totalSum = 0;
            for (Future<Integer> integerFuture : listSumFutures) {
                if (integerFuture.isDone()) {
                    totalSum += integerFuture.get();
                }
            }
            System.out.println("Total Sum is " + totalSum);

            //Shutdown Threads
            executorService.shutdown();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static List<ListSum> partitionList(List<Integer> inputList, int partitions) {
        List<ListSum> resultList = new ArrayList<>();

        int listSize = inputList.size();
        int elementsPerPartition = listSize / partitions;
        int remainder = listSize % partitions;
        int startIndex = 0;

        for (int i = 0; i < partitions; i++) {
            int partitionSize = elementsPerPartition + (i < remainder ? 1 : 0);
            List<Integer> partition = new ArrayList<>(partitionSize);

            for (int j = startIndex; j < startIndex + partitionSize; j++) {
                partition.add(inputList.get(j));
            }

            resultList.add(new ListSum(partition, "Partition " + i));
            startIndex += partitionSize;
        }

        return resultList;
    }
}
