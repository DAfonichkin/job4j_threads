package ru.job4j.findindex;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class IndexFinderTest {

    @Test
    void findIndexWhenString() {
        String[] array = new String[20];
        for (int i = 0; i < 20; i++) {
            array[i] = "test" + i;
        }
        assertThat(IndexFinder.findIndex(array, "test2")).isEqualTo(2);
        assertThat(IndexFinder.findIndex(array, "test19")).isEqualTo(19);
        assertThat(IndexFinder.findIndex(array, "test30")).isEqualTo(-1);
    }

    @Test
    void findIndexWhenInt() {
        Integer[] array = new Integer[20];
        for (int i = 0; i < 20; i++) {
            array[i] = i;
        }
        assertThat(IndexFinder.findIndex(array, 1)).isEqualTo(1);
        assertThat(IndexFinder.findIndex(array, 19)).isEqualTo(19);
        assertThat(IndexFinder.findIndex(array, 30)).isEqualTo(-1);
    }

    @Test
    void findWhenLinearFind() {
        Integer[] array = new Integer[5];
        for (int i = 0; i < 5; i++) {
            array[i] = i;
        }
        assertThat(IndexFinder.findIndex(array, 1)).isEqualTo(1);
        assertThat(IndexFinder.findIndex(array, 4)).isEqualTo(4);
        assertThat(IndexFinder.findIndex(array, 30)).isEqualTo(-1);
    }

    @Test
    void findWhenHasNull() {
        Integer[] array = new Integer[5];
        for (int i = 0; i < 4; i++) {
            array[i] = i;
        }
        assertThat(IndexFinder.findIndex(array, 1)).isEqualTo(1);
        assertThat(IndexFinder.findIndex(array, 3)).isEqualTo(3);
        assertThat(IndexFinder.findIndex(array, null)).isEqualTo(4);
        assertThat(IndexFinder.findIndex(array, 30)).isEqualTo(-1);
    }
}