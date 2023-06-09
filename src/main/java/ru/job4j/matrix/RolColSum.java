package ru.job4j.matrix;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {

    public static Sums[] sum(int[][] matrix) {
        Sums[] array = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            Sums sums = new Sums();
            for (int j = 0; j < matrix.length; j++) {
                sums.setRowSum(sums.getRowSum() + matrix[i][j]);
                sums.setColSum(sums.getColSum() + matrix[j][i]);
            }
            array[i] = sums;
        }
        return array;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException  {
        Sums[] resultArray = new Sums[matrix.length];
        CompletableFuture<Sums>[] cfArray = new CompletableFuture[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            cfArray[i] = getTask(matrix, i);
        }
        for (int i = 0; i < matrix.length; i++) {
            resultArray[i] = cfArray[i].get();
        }
        return resultArray;
    }

    public static CompletableFuture<Sums> getTask(int[][] matrix, int row) {
        return CompletableFuture.supplyAsync(() -> {
            Sums sums = new Sums();
            for (int j = 0; j < matrix.length; j++) {
                sums.setRowSum(sums.getRowSum() + matrix[row][j]);
                sums.setColSum(sums.getColSum() + matrix[j][row]);
            }
            return sums;
        });
    }

}
