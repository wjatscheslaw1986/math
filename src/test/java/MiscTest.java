import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class MiscTest {

    @Test
    void test1() {
        int[] arr = {10, 4, 3, 8, 5, 66, 7, 18, 9, 1};
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }

    void sort(int[] array) {
        int[] supportArray = new int[array.length];
        System.arraycopy(array, 0, supportArray, 0, array.length);
        for (int size = 1; size < array.length; size *= 2) {
            for (int i = 0; i < array.length - size; i += size * 2) {
                merge(array, supportArray, i, i + size - 1, i + size, Math.min(i + size * 2 - 1, array.length - 1));
            }
        }
    }

    void merge(int[] array, int[] memo, int ls, int le, int rs, int re) {
        System.arraycopy(array, ls, memo, ls, re - ls);
        int l = ls;
        for (int i = ls; i <= re; i++) {
            if (l > le) {
                array[i] = memo[rs++];
            } else if (rs > re) {
                array[i] = memo[l++];
            } else if (memo[l] < memo[rs]) {
                array[i] = memo[l++];
            } else {
                array[i] = memo[rs++];
            }
        }
    }
}
