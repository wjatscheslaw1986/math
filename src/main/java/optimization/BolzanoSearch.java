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

public class BolzanoSearch {

    private final List<Term> terms;
    private final List<DoubleUnaryOperator> equationTermTransformers;
    private int counter = 0;

    private BolzanoSearch(final List<Term> terms) {
        this.terms = Objects.requireNonNull(terms);
        final DoubleUnaryOperator defaultTransformer = DoubleUnaryOperator.identity();
        this.equationTermTransformers = Collections.nCopies(terms.size(), defaultTransformer);
    }

    public static BolzanoSearch of(final List<Term> terms) {
        return new BolzanoSearch(terms);
    }

    protected int getStepsCount() {
        return this.counter;
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
            else throw new IllegalStateException("Wrong algorithm step for calling this method.");
        }
    }

    private final class Step1 extends AlgorithmStep {
        public Step1(double fromX, double toX, double epsilon, double delta) {
            super(fromX, toX, epsilon, delta);
        }

        @Override
        AlgorithmStep next() {
            BolzanoSearch.this.counter++;
            if (super.toX - super.fromX <= 2.0d * super.epsilon)
                return new Finish(super.fromX, super.toX, super.epsilon, super.delta);
            var center = (super.fromX + super.toX) / 2.0;
            var f_center = FunctionUtil.calculateSingleVariableFunctionValueAtGivenX(terms, center);
            if (Math.abs(f_center) < super.delta)
                return new Finish(super.fromX, super.toX, super.epsilon, super.delta);
            if (f_center >= super.delta)
                return new Step1(super.fromX, center, super.epsilon, super.delta);
            if (f_center < -super.delta)
                return new Step1(center, super.toX, super.epsilon, super.delta);
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
