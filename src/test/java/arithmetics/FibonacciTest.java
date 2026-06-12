/*
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2026
 */

package arithmetics;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static arithmetics.Fibonacci.getNthFibonacci;

public class FibonacciTest {

    int fibonacci(int n) {
        if (n < 0) throw new IllegalArgumentException();
        if (n <= 2) return n;
        int prev = 1;
        int curr = 2;
        int counter = 3;
        while (counter++ < n) {
            int next = prev + curr;
            prev = curr;
            curr = next;
        }
        return curr;
    }

    @Test
    void shouldReturnExpectedResultForGivenOrdinal()  {
        Assertions.assertEquals(2, fibonacci(3));
        Assertions.assertEquals(3, fibonacci(4));
        Assertions.assertEquals(5, fibonacci(5));
        Assertions.assertEquals(8, fibonacci(6));
        Assertions.assertEquals(1134903170, fibonacci(45));

        Assertions.assertEquals(2, getNthFibonacci(3));
        Assertions.assertEquals(3, getNthFibonacci(4));
        Assertions.assertEquals(5, getNthFibonacci(5));
        Assertions.assertEquals(8, getNthFibonacci(6));
        Assertions.assertEquals(1134903170, getNthFibonacci(45));
    }
}
