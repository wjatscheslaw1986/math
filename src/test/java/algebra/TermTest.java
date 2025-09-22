package algebra;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * Tests for {@link Term} class.
 *
 * @author Viacheslav Mikhailov
 */
public class TermTest {

    @BeforeAll
    static void before() {
        System.out.printf("Running tests in %s%s", TermTest.class,
                System.lineSeparator());
    }

    @Test
    void given_a_term_get_deep_copy_verify_value_immutable() {
        var letter = "a";
        var term = Term.builder().coefficient(5).letter(letter).power(2.0d).value(1.0d).build();
        assertEquals(1.0d, term.getValue());
        var copy = term.copy();
        assertEquals(1.0d, copy.getValue());
        term.setValue(2.0d);
        assertEquals(2.0d, term.getValue());
        assertNotEquals(2.0d, copy.getValue());
        assertEquals(1.0d, copy.getValue());

        assertEquals(2.0d, copy.getPower());
        assertEquals(letter, copy.getLetter().symbol());
        assertEquals(5, copy.getCoefficient());
    }

    @Test
    void given_terms_when_multiply_then_get_expected_opening_of_brackets() {
        var given = Terms.of(List.of(
                Terms.of(List.of(
                        Term.builder().coefficient(5).letter("a").power(2.0d).build(),
                        Term.builder().coefficient(20).letter("a").power(1.0d).build()
                )).multiply(Terms.of(List.of(
                        Term.builder().coefficient(1).letter("a").power(2.0d).build(),
                        Term.builder().coefficient(3).letter("a").power(3.0d).build()
                ))))).asList();

        var expectedPolynomialAsSumAfterOpeningBrackets = List.of(
                Term.builder().coefficient(5).letter("a").power(4.0d).build(),
                Term.builder().coefficient(15).letter("a").power(5.0d).build(),
                Term.builder().coefficient(20).letter("a").power(3.0d).build(),
                Term.builder().coefficient(60).letter("a").power(4.0d).build());

        assertEquals(expectedPolynomialAsSumAfterOpeningBrackets, given);
    }
}
