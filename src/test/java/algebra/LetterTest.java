package algebra;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class LetterTest {

    @Test
    void givenTwoLetters_whenCompared_thenAreEqual() {
        var x0_1 = new Letter("x", 0);
        var x0_2 = new Letter("x", 0);
        assertEquals(x0_1, x0_2);
    }

    @Test
    void givenTwoLetters_whenCompared_thenAreNotEqual() {
        var x0_1 = new Letter("x", 0);
        var x0_2 = new Letter("x", 1);
        assertNotEquals(x0_1, x0_2);

        var x0_3 = new Letter("x", 0);
        var x0_4 = new Letter("X", 0);
        assertNotEquals(x0_3, x0_4);
    }
}
