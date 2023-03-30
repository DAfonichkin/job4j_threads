package ru.job4j.pool;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cas.CASCount;

import static org.assertj.core.api.Assertions.*;

class ThreadPoolTest {
    private static CASCount counter = new CASCount();

    private static void run() {
        for (int i = 0; i < 10000; i++) {
            counter.increment();
        }
    }

    @Test
    void whenWork() {
        counter = new CASCount();
        ThreadPool tp = new ThreadPool();
        int size = Runtime.getRuntime().availableProcessors();
        size *= 2;
        for (int i = 0; i < size; i++) {
            tp.work(ThreadPoolTest::run);
        }
        while (!tp.allThreadsWaiting()) {
            continue;
        }
        assertThat(counter.get()).isEqualTo(size * 10000);
    }

    @Test
    void whenShutdown() {
        counter = new CASCount();
        ThreadPool tp = new ThreadPool();
        int size = 100;
        for (int i = 0; i < size; i++) {
            tp.work(ThreadPoolTest::run);
        }
        tp.shutdown();
        assertThat(counter.get()).isLessThan(size * 10000);
    }
}