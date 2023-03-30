package ru.job4j.pool;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cas.CASCount;

import static org.assertj.core.api.Assertions.*;

class ThreadPoolTest {

    @Test
    void whenWork() {
        CASCount counter = new CASCount();
        ThreadPool tp = new ThreadPool();
        int size = Runtime.getRuntime().availableProcessors();
        size *= 2;
        for (int i = 0; i < size; i++) {
            tp.work(() -> {
                for (int i1 = 0; i1 < 10000; i1++) {
                    counter.increment();
                }
            });
        }
        while (!tp.allThreadsWaiting()) {
            continue;
        }
        assertThat(counter.get()).isEqualTo(size * 10000);
    }

    @Test
    void whenShutdown() {
        CASCount counter = new CASCount();
        ThreadPool tp = new ThreadPool();
        int size = 100;
        for (int i = 0; i < size; i++) {
            tp.work(() -> {
                for (int i1 = 0; i1 < 10000; i1++) {
                    counter.increment();
                }
            });
        }
        tp.shutdown();
        assertThat(counter.get()).isLessThan(size * 10000);
    }
}