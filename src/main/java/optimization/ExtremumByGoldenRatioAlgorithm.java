/*
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2026
 */

package optimization;

import algebra.Equation;
import functional.FunctionUtil;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.DoubleUnaryOperator;

public class ExtremumByGoldenRatioAlgorithm {
    private static final double GOLDEN_RATIO = 0.618d;
    private final Equation equation;
    private int counter;
    private final List<DoubleUnaryOperator> equationTermTransformers;

    private ExtremumByGoldenRatioAlgorithm(final Equation equation) {
        this.equation = Objects.requireNonNull(equation);
        this.equationTermTransformers = Collections.nCopies(equation.terms().size(), DoubleUnaryOperator.identity());
    }

    private ExtremumByGoldenRatioAlgorithm(final Equation equation, final List<DoubleUnaryOperator> transformers) {
        this.equation = Objects.requireNonNull(equation);
        this.equationTermTransformers = transformers;
    }

    public static ExtremumByGoldenRatioAlgorithm of(final Equation equation) {
        return new ExtremumByGoldenRatioAlgorithm(equation);
    }

    public static ExtremumByGoldenRatioAlgorithm of(final Equation equation, final List<DoubleUnaryOperator> transformers) {
        return new ExtremumByGoldenRatioAlgorithm(equation, transformers);
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

    private abstract sealed class AlgorithmStep permits Finish, Step1, Step2 {
        private double fromX;
        private double toX;
        private final double epsilon;
        private double delta;
        private double bigDelta;
        private double x1 = .0d, x2 = .0d, f1 = .0d, f2 = .0d;

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
            super.delta = GOLDEN_RATIO * super.bigDelta;
            if (super.bigDelta <= 2.0d * super.epsilon)
                return new Finish(super.fromX, super.toX, super.epsilon, super.delta);
            super.delta = GOLDEN_RATIO * super.bigDelta;
            var x1 = super.toX - super.delta;
            var x2 = super.fromX + super.delta;
            var f1 = FunctionUtil.calculateSingleVariableFunctionValueAtGivenX(equation.terms(), equationTermTransformers, x1);
            var f2 = FunctionUtil.calculateSingleVariableFunctionValueAtGivenX(equation.terms(), equationTermTransformers, x2);
            return new Step2(super.fromX, super.toX, super.epsilon, super.delta, x1, x2, f1, f2);
        }
    }

    private final class Step2 extends AlgorithmStep {
        public Step2(double fromX, double toX, double epsilon, double delta, double x1, double x2,  double f1,  double f2) {
            super(fromX, toX, epsilon, delta);
            super.x1 = x1;
            super.x2 = x2;
            super.f1 = f1;
            super.f2 = f2;
        }

        @Override
        AlgorithmStep next() {
            counter++;
            if (super.f1 >= super.f2) {
                super.fromX = super.x1;
                super.bigDelta = super.toX - super.fromX;
                if (super.bigDelta <= 2.0d * super.epsilon)
                    return new Finish(super.fromX, super.toX, super.epsilon, super.delta);
                else {
                    super.x1 = super.x2;
                    super.f1 = super.f2;
                    super.x2 = super.fromX + GOLDEN_RATIO * super.bigDelta;
                }
            } else if (super.f1 < super.f2) {
                super.toX = super.x2;
                super.bigDelta = super.toX - super.fromX;
                if (super.bigDelta <= 2.0d * super.epsilon)
                    return new Finish(super.fromX, super.toX, super.epsilon, super.delta);
                else {
                    super.x2 = super.x1;
                    super.f2 = super.f1;
                    super.x1 = super.toX - GOLDEN_RATIO * super.bigDelta;
                }
            }
            return new Step2(super.fromX, super.toX, super.epsilon, super.delta, super.x1, super.x2, super.f1, super.f2);
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
