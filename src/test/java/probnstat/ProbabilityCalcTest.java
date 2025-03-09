/**
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025.
 */
package probnstat;

import org.junit.jupiter.api.Test;

import static combinatorics.CombinatoricsCalc.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static probnstat.ProbabilityCalc.atLeastOneHappensProbability;

/**
 * Tests for {@linkplain probnstat.ProbabilityCalc} class.
 *
 * @author Viacheslav Mikhailov
 */
public class ProbabilityCalcTest {

    /**
     * Примеры взяты из книги Я.К.Колде
     * "Практикум по теории вероятностей и математической статистике" - Москва "Высшая школа" 1991.
     * <p>Бросают две монеты. Найти вероятность того, что:</p>
     */
    @Test
    void given_two_coins_are_thrown_find_probability_of_that() {

        // На обеих монетах появится герб
        assertEquals(.25d, Math.pow(1.0d / (double) binomialCoefficient(2, 1), 2));

        // Хотя бы на одной монете появится герб
        assertEquals(.75d, atLeastOneHappensProbability(new double[]{.5d,.5d}));

        // Ни на одной монете не появится герб
        assertEquals(.25d, 1.0d - atLeastOneHappensProbability(new double[]{.5d,.5d}));
    }

    /**
     * Примеры взяты из книги Я.К.Колде
     * "Практикум по теории вероятностей и математической статистике" - Москва "Высшая школа" 1991.
     * <p>Бросают три монеты. Найти вероятность того, что:</p>
     */
    @Test
    void given_three_coins_are_thrown_find_probability_of_that() {

        // Вероятность выпадения одной стороны монеты
        var oneCoinSideProbability = 1.0d / (double) binomialCoefficient(2, 1);
        assertEquals(.5d, oneCoinSideProbability);

        // На всех монетах появится герб
        assertEquals(.125d, Math.pow(oneCoinSideProbability, 3));

        // Хотя бы на одной монете появится герб
        assertEquals(.875d, atLeastOneHappensProbability(new double[]{
                oneCoinSideProbability,
                oneCoinSideProbability,
                oneCoinSideProbability
        }));

        // Только на двух монетах появится герб
        assertEquals(.5d, (double) binomialCoefficient(3, 2) / countPermutations(3));

        // Только на одной монете появится герб
        assertEquals(.5d, (double) binomialCoefficient(3, 1) / countPermutations(3));

        // Ни на одной монете не появится герб
        assertEquals(.125d, Math.pow(oneCoinSideProbability, 3));
    }
}
