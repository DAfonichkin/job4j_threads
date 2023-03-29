package ru.job4j.cache;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CacheTest {
    @Test
    void whenAdd() {
        Cache cache = new Cache();
        cache.add(new Base(1, 1));
        assertThat(cache.get(1)).isEqualTo(new Base(1, 1));
    }

    @Test
    void whenNotAdd() {
        Cache cache = new Cache();
        cache.add(new Base(1, 1));
        assertThat(cache.add(new Base(1, 2))).isFalse();
    }

    @Test
    void whenUpdate() {
        Cache cache = new Cache();
        Base base1 = new Base(1, 1);
        base1.setName("test");
        cache.add(base1);
        Base base2 = new Base(1, 1);
        base2.setName("test2");
        assertThat(cache.update(base2)).isTrue();
        assertThat(cache.get(1).getName()).isEqualTo("test2");
        assertThat(cache.get(1).getVersion()).isEqualTo(2);
    }

    @Test
    void whenNotUpdate() {
        Cache cache = new Cache();
        Base base1 = new Base(1, 1);
        base1.setName("test");
        cache.add(base1);
        Base base2 = new Base(2, 1);
        base2.setName("test2");
        assertThat(cache.update(base2)).isFalse();
        assertThat(cache.get(1).getName()).isEqualTo("test");
        assertThat(cache.get(1).getVersion()).isEqualTo(1);
    }

    @Test
    void whenNotUpdateWithException() {
        Cache cache = new Cache();
        Base base1 = new Base(1, 3);
        cache.add(base1);
        Base base2 = new Base(1, 1);
        Exception exception = assertThrows(OptimisticException.class, () -> cache.update(base2));
        assertEquals("Versions are not equal", exception.getMessage());
    }

    @Test
    void whenDelete() {
        Cache cache = new Cache();
        Base base1 = new Base(1, 1);
        cache.add(base1);
        cache.delete(base1);
        assertThat(cache.get(base1.getId())).isNull();
    }
}