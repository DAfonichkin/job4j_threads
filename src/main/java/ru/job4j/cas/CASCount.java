package ru.job4j.cas;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>();

    public void increment() {
        Integer ref;
        do {
            ref = count.get();
            if (ref == null) {
                throw new UnsupportedOperationException("Count is not impl.");
            }
        } while (!count.compareAndSet(ref, ref++));
    }

    public int get() {
        Integer ref;
        ref = count.get();
        if (ref == null) {
            throw new UnsupportedOperationException("Count is not impl.");
        }
        return ref;
    }
}