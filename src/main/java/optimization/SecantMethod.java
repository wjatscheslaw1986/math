/*
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2026
 */

package optimization;

import algebra.Term;
import visualization.Iteration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static functional.FunctionUtil.calculateSingleVariableFunctionValueAtGivenX;

public class SecantMethod {

    public static final String Ε_AND_Δ_MUST_BE_NON_NEGATIVE = "ε and δ must be non-negative";
    public static final String Ε_MUST_BE_LESS_THAN_THE_RANGE_FROM_F_TO_F = "ε must be less than the range from %f to %f";
    public static final String TOO_CLOSE = "Derivative values too close";
    public static final String DID_NOT_CONVERGE = "Secant method did not converge";
    private final List<Term> terms;
    private int counter = 0;
    private final List<Iteration> iterations = new ArrayList<>();

    private SecantMethod(final List<Term> terms) {
        this.terms = List.copyOf(Objects.requireNonNull(terms));
    }


    public static SecantMethod of(final List<Term> terms) {
        return new SecantMethod(terms);
    }

    protected int getStepsCount() {
        return this.counter;
    }

    public List<Iteration> getIterations() {
        return List.copyOf(iterations);
    }


    /**
     * Finds an extremum (critical point) by solving {@code f'(x) = 0} using the
     * derivative transformers supplied when constructing this instance.
     *
     * <p>Use this method when the instance was created with transformers that compute
     * the first derivative of the original function.
     *
     * @param fromX         initial guess 0
     * @param toX           initial guess 1
     * @param epsilon       tolerance for the width of the search interval
     * @param delta         tolerance for {@code |f'(x)|} being sufficiently close to zero
     * @param maxIterations
     * @return approximation of the x-value at the extremum
     * @throws IllegalArgumentException if input parameters are invalid
     */
    public double getExtremumX(final double fromX, final double toX,
                               final double epsilon, final double delta, int maxIterations) {
        AlgorithmStep currentStep = new Step(fromX, toX, epsilon, delta, Double.NaN);

        while (!currentStep.isFinished()) {
            if (counter >= maxIterations)
                throw new ArithmeticException(DID_NOT_CONVERGE);

            currentStep = currentStep.next();
        }

        return currentStep.optimumX();
    }

    private abstract sealed class AlgorithmStep
            implements FinishingAlgorithm<AlgorithmStep>
            permits Step, Finish {
        final double fromX;
        final double toX;
        final double ε;
        final double δ;
        final double x_extrema;

        private AlgorithmStep(double fromX, double toX, double epsilon, double delta, double x) {
            if (epsilon < 0 || delta < 0)
                throw new IllegalArgumentException(Ε_AND_Δ_MUST_BE_NON_NEGATIVE);

            double intervalLength = Math.abs(toX - fromX);
            if (epsilon >= intervalLength)
                throw new IllegalArgumentException(
                        Ε_MUST_BE_LESS_THAN_THE_RANGE_FROM_F_TO_F.formatted(fromX, toX));

            this.fromX = fromX;
            this.toX = toX;
            this.ε = epsilon;
            this.δ = delta;
            this.x_extrema = x;
        }

        private double optimumX() {
            if (this.isFinished())
                return this.x_extrema;
            else throw new IllegalStateException(BAD_STEP);
        }
    }

    /**
     * Step implementation for finding extrema by solving {@code f'(x) = 0}.
     */
    private final class Step extends AlgorithmStep {
        private Step(double fromX, double toX, double ε, double δ, double result) {
            super(fromX, toX, ε, δ, result);
        }

        @Override
        public AlgorithmStep next() {
            SecantMethod.this.counter++;

            if (Math.abs(super.toX - super.fromX) <= 2 * super.ε)
                return new Finish(super.fromX, super.toX, super.ε, super.δ, (super.fromX + super.toX) / 2.0d);

            double df_a = calculateSingleVariableFunctionValueAtGivenX(
                    terms,
                    super.fromX);

            double df_b = calculateSingleVariableFunctionValueAtGivenX(
                    terms,
                    super.toX);

            if (Math.abs(df_a) <= super.δ)
                return new Finish(super.fromX, super.toX, super.ε, super.δ, super.fromX);

            if (Math.abs(df_b) <= super.δ)
                return new Finish(super.fromX, super.toX, super.ε, super.δ, super.toX);

            double denominator = df_b - df_a;

            if (Math.abs(denominator) < 1e-15)
                throw new ArithmeticException(TOO_CLOSE);

            //
            // btw double x0 = super.fromX - df_a * (super.toX - super.fromX) / (denominator); works as well
            double x0 = super.toX - df_b * (super.toX - super.fromX) / (denominator);

            double df_x0 = calculateSingleVariableFunctionValueAtGivenX(
                    terms,
                    x0);

            iterations.add(new Iteration(
                    counter - 1,
                    super.fromX,
                    super.toX,
                    df_a,
                    df_b,
                    x0,
                    df_x0
            ));


            if (Math.abs(df_x0) <= super.δ || Math.abs(super.toX - super.fromX) <= super.ε)
                return new Finish(super.fromX, super.toX, super.ε, super.δ, x0);

            return new Step(super.toX, x0, super.ε, super.δ, Double.NaN);
        }

        @Override
        public boolean isFinished() {
            return false;
        }
    }

    private final class Finish extends AlgorithmStep {
        private Finish(double fromX, double toX, double ε, double δ, double result) {
            super(fromX, toX, ε, δ, result);
        }

        @Override
        public boolean isFinished() {
            return true;
        }

        @Override
        public AlgorithmStep next() {
            throw new IllegalStateException(BAD_STEP);
        }
    }
}
