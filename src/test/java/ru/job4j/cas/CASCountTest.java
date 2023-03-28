package ru.job4j.cas;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class CASCountTest {

    @Test
    void whenIncrement() throws InterruptedException {
        List<Thread> threadList = new ArrayList<>();
        CASCount counter = new CASCount();
        for (int i = 0; i < 10; i++) {
            threadList.add(new Thread(() -> {
                for (int j = 0; j < 10; j++) {
                    counter.increment();
                }
            }));
        }
        for (Thread thread : threadList) {
            thread.start();
            thread.join();
        }
        assertThat(counter.get()).isEqualTo(100);
    }

    @Test
    void whenGet() {
        CASCount counter = new CASCount();
        assertThat(counter.get()).isEqualTo(0);
        counter.increment();
        assertThat(counter.get()).isEqualTo(1);
    }
}