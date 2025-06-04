package algebra;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * Tests for {@linkplain algebra.Member} class.
 *
 * @author Viacheslav Mikhailov
 */
public class MemberTest {

    @BeforeAll
    static void before() {
        System.out.printf("Running tests in %s%s", MemberTest.class,
                System.lineSeparator());
    }

    @Test
    void given_a_member_get_deep_copy_verify_value_immutable() {
        var letter = "a";
        var member = Member.builder().coefficient(5).letter(letter).power(2.0d).value(1.0d).build();
        assertEquals(1.0d, member.getValue());
        var copy = member.copy();
        assertEquals(1.0d, copy.getValue());
        member.setValue(2.0d);
        assertEquals(2.0d, member.getValue());
        assertNotEquals(2.0d, copy.getValue());
        assertEquals(1.0d, copy.getValue());

        assertEquals(2.0d, copy.getPower());
        assertEquals(letter, copy.getLetter());
        assertEquals(5, copy.getCoefficient());
    }

}
