package ru.job4j.blockingqueue;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class SimpleBlockingQueueTest {

    @Test
    void offerWhenQueueIsEmpty() throws InterruptedException {
        SimpleBlockingQueue<String> queue = new SimpleBlockingQueue<>(5);
        Thread producer = new Thread(() -> queue.offer("test"));
        producer.start();
        producer.join();
        assertThat(queue.poll()).isEqualTo("test");
    }

    @Test
    void offerWhenQueueIsNotFull() throws InterruptedException {
        SimpleBlockingQueue<String> queue = new SimpleBlockingQueue<>(5);
        queue.offer("test");
        Thread producer = new Thread(() -> queue.offer("test2"));
        producer.start();
        producer.join();
        assertThat(queue.poll()).isEqualTo("test");
    }

    @Test
    void offerWhenQueueIsFullAndWait() throws InterruptedException {
        SimpleBlockingQueue<String> queue = new SimpleBlockingQueue<>(1);
        queue.offer("test");
        Thread producer = new Thread(() -> queue.offer("test2"));
        Thread consumer = new Thread(() -> queue.poll());
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        assertThat(queue.poll()).isEqualTo("test2");
    }

    @Test
    @Disabled
    void pollWhenQueueIsEmptyAndWait() throws InterruptedException {
        SimpleBlockingQueue<String> queue = new SimpleBlockingQueue<>(1);
        Thread producer = new Thread(() -> queue.offer("test"));
        Thread consumer = new Thread(() -> queue.poll());
        consumer.start();
        producer.start();
        consumer.join();
        producer.join();
        assertThat(queue.poll()).isNull();
    }
}