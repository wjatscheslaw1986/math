/*
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2026
 */

package optimization;

import exception.MathException;

import java.util.Objects;
import java.util.function.DoubleUnaryOperator;

public class SwannAlgorithm {
    private int counter;
    private final DoubleUnaryOperator function;
    private final double t;

    private SwannAlgorithm(final DoubleUnaryOperator func, final double searchStepSize) {
        if (searchStepSize <= 0)
            throw new IllegalArgumentException("Search step size must be greater than zero");

        this.counter = 0;
        this.function = Objects.requireNonNull(func);
        this.t = searchStepSize;
    }

    public static SwannAlgorithm of(final DoubleUnaryOperator func, final double searchStepSize) {
        return new SwannAlgorithm(func, searchStepSize);
    }

    public double[] getInterval(final double initialX) throws MathException {
        AlgorithmStep stateMachine = new InitStep(initialX);
        while (!stateMachine.isFinished())
            stateMachine = stateMachine.next();
        return stateMachine.interval();
    }

    public int getStepsCount() {
        return counter;
    }

    private abstract sealed class AlgorithmStep permits InitStep, SearchStep, Finish {
        abstract AlgorithmStep next() throws MathException;
        abstract boolean isFinished();
        abstract double[] interval();
    }

    // PHASE 1: Determine Direction
    private final class InitStep extends AlgorithmStep {
        private final double x0;

        private InitStep(final double x0) {
            super();
            this.x0 = x0;
        }

        @Override
        AlgorithmStep next() throws MathException {
            double f_center = SwannAlgorithm.this.function.applyAsDouble(x0);
            double f_left = SwannAlgorithm.this.function.applyAsDouble(x0 - SwannAlgorithm.this.t);
            double f_right = SwannAlgorithm.this.function.applyAsDouble(x0 + SwannAlgorithm.this.t);

            if (f_left >= f_center && f_center >= f_right) {
                // Decreasing to the right
                return new SearchStep(x0, x0 + SwannAlgorithm.this.t, SwannAlgorithm.this.t, true);
            } else if (f_left <= f_center && f_center <= f_right) {
                // Decreasing to the left
                return new SearchStep(x0, x0 - SwannAlgorithm.this.t, -SwannAlgorithm.this.t, false);
            } else if (f_left >= f_center) { // We skip the `&& f_center <= f_right` part for brevity, since it is logically included
                // Minimum is already trapped!
                return new Finish(x0 - SwannAlgorithm.this.t, x0 + SwannAlgorithm.this.t);
            } else {
                throw new MathException("Function is not unimodal or step size is too large.");
            }
        }

        @Override
        boolean isFinished() {
            return false;
        }

        @Override
        public double[] interval() {
            throw new UnsupportedOperationException("Wrong algorithm step for calling this method.");
        }
    }

    // PHASE 2: Exponential Search
    private final class SearchStep extends AlgorithmStep {
        private final double prevX;
        private final double currX;
        private final double step;
        private final boolean leftToRight;

        private SearchStep(double prevX, double currX, double step, boolean leftToRight) {
            this.prevX = prevX;
            this.currX = currX;
            this.step = step;
            this.leftToRight = leftToRight;
        }

        @Override
        AlgorithmStep next() {
            counter++;
            // Calculate next step: x_{k+1} = x_k + 2^k * h
            double nextX = currX + Math.pow(2, counter) * step;

            double f_curr = SwannAlgorithm.this.function.applyAsDouble(currX);
            double f_next = SwannAlgorithm.this.function.applyAsDouble(nextX);

            if (f_curr <= f_next) {
                // It started going uphill. We pass the bounds to Finish.
                return new Finish(prevX, nextX);
            }

            // Still going downhill, keep searching
            return new SearchStep(currX, nextX, step, this.leftToRight);
        }

        @Override
        boolean isFinished() {
            return false;
        }

        @Override
        public double[] interval() {
            throw new UnsupportedOperationException("Wrong algorithm step for calling this method.");
        }
    }

    // PHASE 3: Complete
    private final class Finish extends AlgorithmStep {
        private final double startFromX;
        private final double finishAtX;

        private Finish(final double startFromX, final double finishAtX) {
            super();
            this.startFromX = startFromX;
            this.finishAtX = finishAtX;
        }

        @Override
        AlgorithmStep next() {
            throw new IllegalStateException("Wrong algorithm step for calling this method.");
        }

        @Override
        boolean isFinished() {
            return true;
        }

        @Override
        public double[] interval() {
            if (startFromX < finishAtX)
                return new double[]{startFromX, finishAtX};
            else
                return new double[]{finishAtX, startFromX};
        }
    }
}
