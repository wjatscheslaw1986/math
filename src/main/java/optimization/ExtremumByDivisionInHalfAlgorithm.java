/*
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2026
 */

package optimization;

import algebra.Equation;
import functional.FunctionUtil;

import java.util.Objects;

public class ExtremumByDivisionInHalfAlgorithm {

    private final Equation equation;
    private int counter;

    private ExtremumByDivisionInHalfAlgorithm(final Equation equation) {
        this.equation = Objects.requireNonNull(equation);
    }

    public static ExtremumByDivisionInHalfAlgorithm of(final Equation equation) {
        return new ExtremumByDivisionInHalfAlgorithm(equation);
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
        private double fromX;
        private double toX;
        private final double epsilon;
        private final double delta;
        private double bigDelta;

        private AlgorithmStep(double fromX, double toX, double epsilon, double delta) {
            if (fromX < 0 || toX < 0 || epsilon < 0 || delta < 0)
                throw new IllegalArgumentException("All arguments must be non-negative");
            if (fromX >= toX)
                throw new IllegalArgumentException("fromX must be strictly less than toX");
            if (delta >= epsilon)
                throw new IllegalArgumentException("delta must be less than epsilon");
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
            else throw new IllegalStateException("Wrong algorithm step for calling this method.");
        }
    }

    private final class Step1 extends AlgorithmStep {
        public Step1(double fromX, double toX, double epsilon, double delta) {
            super(fromX, toX, epsilon, delta);
        }

        @Override
        AlgorithmStep next() {
            counter++;
            super.bigDelta = super.toX - super.fromX;
            if (super.bigDelta <= 2.0d * super.epsilon)
                return new Finish(super.fromX, super.toX, super.epsilon, super.delta);
            double center = (super.fromX + super.toX) / 2.0;
            var x1 = center - (super.delta / 2.0);
            var x2 = center + (super.delta / 2.0);
            var f1 = FunctionUtil.calculateSingleVariableFunctionValueAtGivenX(equation.terms(), x1);
            var f2 = FunctionUtil.calculateSingleVariableFunctionValueAtGivenX(equation.terms(), x2);
            if (f1 >= f2)
                super.fromX = x1;
            if (f1 < f2)
                super.toX = x2;
            return new Step1(super.fromX, super.toX, super.epsilon, super.delta);
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
