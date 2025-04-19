/**
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025.
 */
package probnstat;

import combinatorics.CombinationsGenerator;

import java.util.Arrays;

/**
 * A utility class for calculations of probabilities.
 *
 * @author Viacheslav Mikhailov
 */
public final class ProbabilityCalc {

    private ProbabilityCalc() {
        // static context only
    }

    /**
     * Calculates probability of the fact that some precondition hypotheses had been proven true,
     * if an event presumably caused by the precondition has happened.
     * <p>
     * The Bayes theorem formula.
     * </p>
     *
     * @param pAcondB conditional probability of that some event A has happened if the event B had happened
     * @param pA probability of the event A
     * @param pB probability of the event B
     * @return
     */
    public static double bayes(double pAcondB, double pB, double pA) {
        return pAcondB * pB / pA;
    }

    /**
     * Calculate probability of that at least one of the given events represented by their
     * {@code eventsProbabilities} takes place. Union of probabilities.
     *
     * @param eventsProbabilities the array of probabilities of the events
     * @return probability of that at least one of the events happens
     */
    public static double atLeastOneHappensProbability(final double[] eventsProbabilities) {
        double probability = .0d;
        boolean subtract = false;
        for (int i = 1; i <= eventsProbabilities.length; i++) {
            var combinations = CombinationsGenerator.generate(eventsProbabilities.length, i);
            var adder = combinations.stream()
                    .map(Arrays::stream)
                    .map(intStream -> intStream.mapToDouble(index -> eventsProbabilities[index]))
                    .map(stream -> stream.reduce((val1, val2) -> val1 * val2))
                    .map(optionalDouble ->
                            optionalDouble.orElseThrow(() ->
                                    new ArithmeticException("Массив примитивных значений не может содержать null по индексу.")))
                    .reduce(Double::sum)
                    .orElseThrow(() ->
                            new ArithmeticException("Сумма по массиву примитивных значений не может быть null."));
            if (subtract)
                probability -= adder;
            else
                probability += adder;
            subtract = !subtract;
        }
        return probability;
    }
}
