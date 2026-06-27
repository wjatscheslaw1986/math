/*
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2026
 */

package optimization;

import algebra.Term;
import functional.FunctionUtil;

import java.util.List;
import java.util.Objects;
import java.util.function.DoubleUnaryOperator;

public class DichotomyMethod {

    private final List<Term> terms;
    private int counter;

    private DichotomyMethod(final List<Term> terms) {
        this.terms = Objects.requireNonNull(terms);
        final DoubleUnaryOperator transformer = DoubleUnaryOperator.identity();
    }

    public static DichotomyMethod of(final List<Term> terms) {
        return new DichotomyMethod(terms);
    }

    public int getStepsCount() {
        return counter;
    }

    public double getExtremumX(final double fromX, final double toX, final double epsilon, final double delta) {
        AlgorithmStep currentStep = new Step1(fromX, toX, epsilon, delta);
        while (currentStep.getClass() != Finish.class) {
            currentStep = currentStep.next();
        }
        return currentStep.optimumX();
    }

    private abstract sealed class AlgorithmStep permits Step1, Finish {
        private final double fromX;
        private final double toX;
        private final double epsilon;
        private final double delta;

        private AlgorithmStep(double fromX, double toX, double epsilon, double delta) {
            if (fromX < 0 || toX < 0 || epsilon < 0 || delta < 0)
                throw new IllegalArgumentException("All arguments must be non-negative");
            if (fromX >= toX)
                throw new IllegalArgumentException("fromX must be strictly less than toX");
            if (epsilon >= (toX - fromX))
                throw new IllegalArgumentException("Epsilon must be less than the range from %f to %f".formatted(fromX, toX));
            this.fromX = fromX;
            this.toX = toX;
            this.epsilon = epsilon;
            this.delta = delta;
        }

        abstract AlgorithmStep next();

        private double optimumX() {
            if (this.getClass() == Finish.class)
                return (this.fromX + this.toX) / 2;
            else throw new IllegalStateException("Wrong a lgorithm step for calling this method.");
        }
    }

    private final class Step1 extends AlgorithmStep {
        public Step1(double fromX, double toX, double epsilon, double delta) {
            super(fromX, toX, epsilon, delta);
        }

        @Override
        AlgorithmStep next() {
            counter++;
            if (super.toX - super.fromX <= 2.0d * super.epsilon)
                return new Finish(super.fromX, super.toX, super.epsilon, super.delta);
            double center = (super.fromX + super.toX) / 2.0;
            var x1 = center - (super.delta / 2.0);
            var x2 = center + (super.delta / 2.0);
            var f1 = FunctionUtil.calculateSingleVariableFunctionValueAtGivenX(terms, x1);
            var f2 = FunctionUtil.calculateSingleVariableFunctionValueAtGivenX(terms, x2);
            if (f1 >= f2)
                return new Step1(x1, super.toX, super.epsilon, super.delta);
            if (f1 < f2)
                return new Step1(super.fromX, x2, super.epsilon, super.delta);
            throw new RuntimeException("unreachable");
        }
    }

    private final class Finish extends AlgorithmStep {
        public Finish(double fromX, double toX, double epsilon, double delta) {
            super(fromX, toX, epsilon, delta);
        }

        @Override
        AlgorithmStep next() {
            throw new IllegalStateException("Wrong algorithm step for calling this method.");
        }
    }
}
