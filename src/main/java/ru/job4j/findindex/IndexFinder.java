package ru.job4j.findindex;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class IndexFinder<T> extends RecursiveTask<Integer> {
    private final T[] array;
    private final T el;
    private final int from;
    private final int to;

    public IndexFinder(T[] array, T el, int from, int to) {
        this.array = array;
        this.el = el;
        this.from = from;
        this.to = to;
    }

    @Override
    protected Integer compute() {
        if ((from - to) <= 10) {
            return findElement();
        }
        int mid = (from + to) / 2;
        IndexFinder<T> left = new IndexFinder<>(array, el, from, mid);
        IndexFinder<T> right = new IndexFinder<>(array, el, mid + 1, to);
        left.fork();
        right.fork();
        return Math.max(left.join(), right.join());
    }

    private int findElement() {
        for (int i = from; i <= to; i++) {
            if (array[i] == null && el != null) {
                continue;
            }
            if ((array[i] == null && el == null) || array[i].equals(el)) {
                return i;
            }
        }
        return -1;
    }

    public static <T> int findIndex(T[] array, T el) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new IndexFinder<>(array, el, 0, array.length - 1));
    }

}
