/*
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2026
 */

package optimization;

import algebra.Term;
import functional.FunctionUtil;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.DoubleUnaryOperator;

public class GoldenRatioMethod {
    private static final double GOLDEN_RATIO = (Math.sqrt(5.0) - 1.0) / 2.0;
    private final List<Term> terms;
    private int counter;
    private final List<DoubleUnaryOperator> termTransformers;

    private GoldenRatioMethod(final List<Term> terms) {
        this.counter = 0;
        this.terms = Objects.requireNonNull(terms);
        final DoubleUnaryOperator transformer = DoubleUnaryOperator.identity();
        this.termTransformers = Collections.nCopies(terms.size(), transformer);
    }

    private GoldenRatioMethod(final List<Term> terms, final List<DoubleUnaryOperator> transformers) {
        if (terms.size() != transformers.size()) {
            throw new IllegalArgumentException("Number of terms and transformers don't match");
        }
        this.terms = Objects.requireNonNull(terms);
        this.termTransformers = transformers;
    }

    public static GoldenRatioMethod of(final List<Term> terms) {
        return new GoldenRatioMethod(terms);
    }

    public static GoldenRatioMethod of(final List<Term> terms, final List<DoubleUnaryOperator> transformers) {
        return new GoldenRatioMethod(terms, transformers);
    }

    public int getStepsCount() {
        return counter;
    }

    public double getExtremum(final double fromX, final double toX, final double epsilon) {
        if (fromX < 0 || toX < 0 || epsilon < 0)
            throw new IllegalArgumentException("All arguments must be non-negative");
        if (fromX >= toX)
            throw new IllegalArgumentException("fromX must be strictly less than toX");
        if (epsilon >= (toX - fromX))
            throw new IllegalArgumentException("Epsilon must be less than the range from %f to %f".formatted(fromX, toX));
        AlgorithmStep currentStep = new Step1(fromX, toX, epsilon);
        while (!currentStep.isFinished()) {
            currentStep = currentStep.next();
        }
        return currentStep.optimum();
    }

    private abstract sealed class AlgorithmStep permits Finish, Step1, Step2 {
        private final double epsilon;
        private double fromX;
        private double toX;
        private double bigDelta;
        private AlgorithmStep(double fromX, double toX, double epsilon) {
            this.fromX = fromX;
            this.toX = toX;
            this.epsilon = epsilon;
        }

        abstract AlgorithmStep next();

        abstract boolean isFinished();

        private double optimum() {
            if (this.isFinished())
                return (this.fromX + this.toX) / 2;
            else throw new IllegalStateException("Wrong algorithm step for calling this method.");
        }
    }

    private final class Step1 extends AlgorithmStep {
        public Step1(final double fromX, final double toX, final double epsilon) {
            super(fromX, toX, epsilon);
        }

        @Override
        AlgorithmStep next() {
            counter++;
            super.bigDelta = super.toX - super.fromX;
            if (super.bigDelta <= 2.0d * super.epsilon)
                return new Finish(super.fromX, super.toX, super.epsilon);
            var x1 = super.toX - GOLDEN_RATIO * super.bigDelta;
            var x2 = super.fromX + GOLDEN_RATIO * super.bigDelta;
            var f1 = FunctionUtil.calculateSingleVariableFunctionValueAtGivenX(terms, termTransformers, x1);
            var f2 = FunctionUtil.calculateSingleVariableFunctionValueAtGivenX(terms, termTransformers, x2);
            return new Step2(super.fromX, super.toX, super.epsilon, x1, x2, f1, f2);
        }

        @Override
        boolean isFinished() {
            return false;
        }
    }

    private final class Step2 extends AlgorithmStep {
        private double x1 = .0d, x2 = .0d, f1 = .0d, f2 = .0d;

        private Step2(double fromX, double toX, double epsilon, double x1, double x2,  double f1,  double f2) {
            super(fromX, toX, epsilon);
            this.x1 = x1;
            this.x2 = x2;
            this.f1 = f1;
            this.f2 = f2;
        }

        @Override
        AlgorithmStep next() {
            counter++;
            if (this.f1 >= this.f2) {
                super.fromX = this.x1;
                super.bigDelta = super.toX - super.fromX;
                if (super.bigDelta <= 2.0d * super.epsilon)
                    return new Finish(super.fromX, super.toX, super.epsilon);
                else {
                    this.x1 = this.x2;
                    this.f1 = this.f2;
                    this.x2 = super.fromX + GOLDEN_RATIO * super.bigDelta;
                    this.f2 = FunctionUtil.calculateSingleVariableFunctionValueAtGivenX(terms, termTransformers, this.x2);
                }
            } else {
                super.toX = this.x2;
                super.bigDelta = super.toX - super.fromX;
                if (super.bigDelta <= 2.0d * super.epsilon)
                    return new Finish(super.fromX, super.toX, super.epsilon);
                else {
                    this.x2 = this.x1;
                    this.f2 = this.f1;
                    this.x1 = super.toX - GOLDEN_RATIO * super.bigDelta;
                    this.f1 = FunctionUtil.calculateSingleVariableFunctionValueAtGivenX(terms, termTransformers, this.x1);
                }
            }
            return new Step2(super.fromX, super.toX, super.epsilon, this.x1, this.x2, this.f1, this.f2);
        }

        @Override
        boolean isFinished() {
            return false;
        }
    }

    private final class Finish extends AlgorithmStep {
        private Finish(double fromX, double toX, double epsilon) {
            super(fromX, toX, epsilon);
        }

        @Override
        AlgorithmStep next() {
            throw new IllegalStateException("Wrong algorithm step for calling this method.");
        }

        @Override
        boolean isFinished() {
            return true;
        }
    }
}
