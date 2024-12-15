package org.example;

import java.time.LocalTime;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        CopyOnWriteArrayList<Integer> matrix = new CopyOnWriteArrayList<>();
        int row = 3;
        int column = 3;
        Random random = new Random();

        ExecutorService executor = Executors.newFixedThreadPool(4);
        CompletableFuture<Void> generateTask = CompletableFuture.runAsync(() -> {
            System.out.println("Generation start at " + LocalTime.now());
            for (int i = 0; i < column; i++) {
                for (int j = 0; j < row; j++) {
                    matrix.add(i * 3 + j, random.nextInt(100));
                }
            }
            System.out.println("Generation finish at " + LocalTime.now());
        }, executor);

        generateTask.join();

        CompletableFuture<?>[] readTasks = new CompletableFuture<?>[column];
        for (int i = 0; i < column; i++) {
                int finalI = i;
                readTasks[i] = CompletableFuture.supplyAsync(() -> {
                    System.out.println("Printing column " + finalI + " start at " + LocalTime.now());
                    String col = "(" + finalI + " column) = ";
                    for (int j = 0; j < row; j++) {
                        col += matrix.get(finalI * column + j) + "; ";
                    }
                    return col;
                }, executor).thenAcceptAsync(result -> {
                    System.out.println(result);
                    System.out.println("Printing column " + finalI + " finish at " + LocalTime.now());
                }, executor);
        }

        CompletableFuture.allOf(readTasks).join();

        executor.shutdown();
    }
}
