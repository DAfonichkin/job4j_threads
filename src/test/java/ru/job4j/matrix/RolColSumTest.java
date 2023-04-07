package ru.job4j.matrix;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class RolColSumTest {

    @Test
    void sum() {
        int[][] matrix = new int[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}};
        Sums[] sums = RolColSum.sum(matrix);
        assertThat(sums[0]).isEqualTo(new Sums(6, 12));
        assertThat(sums[1]).isEqualTo(new Sums(15, 15));
        assertThat(sums[2]).isEqualTo(new Sums(24, 18));
    }

    @Test
    void asyncSum() {
        int[][] matrix = new int[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}};
        Sums[] sums = RolColSum.sum(matrix);
        assertThat(sums[0]).isEqualTo(new Sums(6, 12));
        assertThat(sums[1]).isEqualTo(new Sums(15, 15));
        assertThat(sums[2]).isEqualTo(new Sums(24, 18));
    }
}