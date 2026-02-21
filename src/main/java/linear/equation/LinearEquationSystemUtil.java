/**
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025.
 */
package linear.equation;

import algebra.Equation;
import algebra.Letter;
import algebra.Term;
import combinatorics.JohnsonTrotter;
import linear.matrix.MatrixCalc;
import linear.matrix.MatrixUtil;
import linear.matrix.RowEchelonFormUtil;
import linear.matrix.exception.MatrixException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static algebra.EquationUtil.solveSingleVariableLinearEquation;
import static approximation.RoundingUtil.cleanDoubleArrayOfNegativeZeros;
import static linear.equation.SolutionsCount.*;
import static linear.matrix.MatrixUtil.*;

/**
 * A utility class for solving linear equation systems.
 *
 * @author Viacheslav Mikhailov
 */
public final class LinearEquationSystemUtil {

    private LinearEquationSystemUtil() {
        // static context only
    }

    /**
     * Resolve linear equation system using Cramer method.
     * <p>Only applicable to Cramer matrices. Use {@link linear.matrix.Validation#isCramer} method
     * beforehand to make sure you aren't getting wrong results.</p>
     *
     * @param coefficients matrix made of left part of the equations
     * @param freeTerms  a vector made of right part of the equations
     * @return a vector of variable values (answers) for the equations, regarding their order in the equation.
     */
    public static double[] resolveUsingCramerMethod(final double[][] coefficients, final double[] freeTerms) {
        final var resolved = new double[freeTerms.length];
        final double determinant = MatrixCalc.det(coefficients);
        for (int i = 0; i < freeTerms.length; i++)
            resolved[i] = MatrixCalc.det(substituteColumn(coefficients, freeTerms, i))
                    / determinant;
        return resolved;
    }

    /**
     * Resolve given linear equations system using reverse matrix method.
     * <p>Only applicable to Cramer matrices. Use {@link linear.matrix.Validation#isCramer} method
     * beforehand to be sure you aren't getting wrong results.</p>
     *
     * @param coefficients a square matrix of coefficients of the left parts of each equation
     * @param freeTerms  a vector of free terms (i.e. right parts of each equation)
     * @return a vector of variable values (answers) for the equations, regarding their order in the equation.
     */
    public static double[] resolveUsingReverseMatrixMethod(double[][] coefficients, double[] freeTerms) {
        var solution = MatrixCalc.multiply(MatrixCalc.inverse(coefficients), freeTerms);
        MatrixUtil.eliminateEpsilon(solution);
        return solution;
    }

    /**
     * Resolve given linear equations system using Jordan-Gauss method.
     * <p>
     *     <b>IMPORTANT:</b> this method modifies the argument as it performs
     *     its job. Use {@link MatrixUtil#copy} on the argument you pass to this method if
     *     you need it for further calculations.
     * </p>
     *
     * @param augmentedMatrix linear equations matrix including both sides of each equation
     * @return number of solutions for the system. Integer.MAX_VALUE for infinite number of solutions.
     */
    public static Solution resolveUsingJordanGaussMethod(final double[][] augmentedMatrix) throws MatrixException {
        final double[][] equations = MatrixUtil.copy(augmentedMatrix);
        int rowsCount = equations.length;
        int colsLeftSideCount = equations[0].length - 1;

        // addresses is an array that tells us at which row we may find a value (an answer) for each variable,
        // by their own index both in the equation and in this array.

        int[] addresses = new int[colsLeftSideCount];
        Arrays.fill(addresses, -1);

        for (int col = 0, row = 0; col < colsLeftSideCount && row < rowsCount; ++col) {
            int sel = row;
            for (int i = row; i < rowsCount; ++i)
                if (Math.abs(equations[i][col]) > Math.abs(equations[sel][col]))
                    sel = i;

            if (Math.abs(equations[sel][col]) < EPS) {
                equations[sel][col] = .0d;
                continue;
            }

            for (int i = col; i <= colsLeftSideCount; ++i)
                swapInColumn(equations, sel, row, i);

            addresses[col] = row;

            for (int i = 0; i < rowsCount; ++i)
                if (i != row) {
                    double c = equations[i][col] / equations[row][col];
                    for (int j = col; j <= colsLeftSideCount; ++j)
                        equations[i][j] -= equations[row][j] * c;
                }
            ++row;
        }

        final double[] solution = new double[equations[0].length - 1];
        for (int i = 0; i < colsLeftSideCount; ++i)
            if (addresses[i] != -1) {
                solution[i] = equations[addresses[i]][colsLeftSideCount] / equations[addresses[i]][i];
                MatrixUtil.eliminateEpsilon(solution);
            }

        for (int i = 0; i < rowsCount; ++i) {
            double sum = 0;
            for (int j = 0; j < colsLeftSideCount; ++j)
                sum += solution[j] * equations[i][j];

            if (Math.abs(sum - equations[i][colsLeftSideCount]) > EPS)
                return new Solution(ZERO, null, null); // No solutions
        }

        roundValues(12, solution);

        for (int i = 0; i < colsLeftSideCount; ++i)
            if (addresses[i] == -1) {
                return new Solution(INFINITE, solution, fundamental(augmentedMatrix)); // Infinite solutions
            }

        return new Solution(SINGLE, solution, null); // Unique solution
    }

    /**
     * Given homogenous linear equation system, this method returns a fundamental solution system for it, which
     * is also the basis of the solution space for the given linear equation system.
     *
     * @param augmentedMatrix the given linear equation system as a matrix with its last
     *                        column representing the right side for each equation.
     * @return a list of vectors
     * @throws MatrixException if unable to produce row echelon form for the matrix
     */
    public static List<double[]> fundamental(final double[][] augmentedMatrix) throws MatrixException {
        var ref = removeAllZeroesRows(RowEchelonFormUtil.toRowEchelonForm(augmentedMatrix));
        var freeTermsLeft = basisSize(augmentedMatrix);
        var freeVariableIndices = getEquationTermFlags(ref, freeTermsLeft);
        if (freeVariableIndices.length == 0)
            return List.of(resolveUsingReverseMatrixMethod(MatrixUtil.removeMarginalColumn(ref, false), MatrixUtil.getColumn(ref, ref[0].length)));
        var fundamental = new ArrayList<double[]>();
        var freeTermsValuesCombinations = getFreeTermsValuesCombinations(freeVariableIndices);
        Equation eq = new Equation(new ArrayList<>(), Term.asRealConstant(.0d));
        for (Double[] fmvCombination : freeTermsValuesCombinations) {
            for (int i = ref.length - 1; i >= 0; i--) {
                eq.setEqualsTo(ref[i][ref[i].length - 1]);
                int pivotIndex = findPivotIndex(ref, i);
                double[] coefficients = new double[ref[i].length - pivotIndex - 1];
                System.arraycopy(ref[i], pivotIndex, coefficients, 0, coefficients.length);
                int k = 1;
                for (; k < coefficients.length; k++) {
                        var term = eq.getTermByLetter(Letter.of("x", k + pivotIndex));
                        if (Objects.isNull(term)) {
                            var termBuilder = Term.builder().coefficient(coefficients[k])
                                    .letter(Letter.of("x", k + pivotIndex));
                            if (freeVariableIndices[k + pivotIndex]) termBuilder.value(fmvCombination[k + pivotIndex]);
                            else termBuilder.value(eq.getTermByIndex(k + pivotIndex).getValue());
                            eq.terms().add(termBuilder.build());
                        } else {
                            term.setCoefficient(coefficients[k]);
                        }
                }
                eq.terms().addFirst(Term.builder().value(null).coefficient(coefficients[0]).letter(Letter.of("x", pivotIndex)).build());
                solveSingleVariableLinearEquation(eq);
            }
            eq.terms().sort((m1, m2) -> (-1) * m1.compareTo(m2));
            fundamental.add(eq.terms().stream().map(Term::getValue).mapToDouble(Double::doubleValue).toArray());
            eq = new Equation(new ArrayList<>(), Term.asRealConstant(.0d));
        }
        for (var basisVector : fundamental) roundValues(12, basisVector);
        return fundamental;
    }

    /**
     * Returns an array of equation terms' flags as an array of {@link Boolean}.
     * <p>
     * Each array element (the flag) corresponds to an equation term by their common index.
     * The flag TRUE means the variable in the linear equation found by the same index is a free variable, so that
     * its value is arbitrary.
     * The flag FALSE means the variable of the linear equation found by the same index is <b>not</b> a free variable, so that
     * its value should be found by solving a corresponding single variable linear equation.
     * </p>
     *
     * @param ref the row echelon form of a matrix
     * @param basisSize number of free terms in each of the linear equations
     * @return an array of term flags for the equation
     */
    public static Boolean[] getEquationTermFlags(double[][] ref, int basisSize) {
        int[] addresses = new int[ref[0].length - 1];
        int freeVariablesLeft = basisSize;
        int freeVariableIndex = addresses.length - 1;
        int j = ref.length - 1;
        while (freeVariablesLeft > 0) {
            for (int i = freeVariableIndex; i > -1 && j >= 0; i--) {
                if (isZeroRow(ref, j)) {
                    j--;
                    continue;
                }
                int pivotIndex = findPivotIndex(ref, j);
                for (int k = pivotIndex + 1; k < ref[j].length - 1; k++) {
                    if (addresses[k] != 0) continue;
                    addresses[k] = -1;
                    freeVariablesLeft--;
                }
                addresses[pivotIndex] = 1;
                j--;
            }
        }
        return Arrays.stream(addresses).mapToObj(i -> i == -1).toArray(Boolean[]::new);
    }

    /**
     * Returns an index of the pivot element for the given row index for the given
     * row echelon form matrix.
     * <p>The matrix <b>must</b> be in a row echelon form, for the return of this function to be meaningful.</p>
     *
     * @param rowEchelonFormMatrix the row echelon form of a matrix
     * @param row the row index
     * @return an index of the pivot element for the given row in the given matrix
     */
    public static int findPivotIndex(double[][] rowEchelonFormMatrix, int row) {
        boolean nonZeroFound = false;
        int pivotIndex = 0;
        while (!nonZeroFound || pivotIndex >= rowEchelonFormMatrix[0].length) {
            double found = rowEchelonFormMatrix[row][pivotIndex];
            if (found != 0.0d) nonZeroFound = true;
            else pivotIndex++;
        }
        return pivotIndex;
    }

    /**
     * Check if the given system of linear equations solvable,
     * using Rouché–Capelli (Kronecker–Capelli) theorem:
     * <p>A system of linear equations has a solution iff its
     * coefficient matrix A and its augmented matrix [A|b] have the same rank.</p>
     *
     * @param augmentedMatrix a matrix of vectors. Each vector represents an equation
     *                        with its last element being the right side of the equation
     * @return true if the given system of linear equations is solvable, false otherwise
     */
    public static boolean isSolvable(final double[][] augmentedMatrix) {
        return MatrixCalc.rank(MatrixUtil.removeMarginalColumn(augmentedMatrix, false))
                == MatrixCalc.rank(augmentedMatrix);
    }

    /**
     * The number of dimensions of the linear space for the given linear equation system.
     * The given linear equation system is represented by the given augmented matrix as a parameter.
     *
     * @param augmentedMatrix a matrix for linear equation system coefficients including the right side of each equation
     * @return the size of the basis of the linear space
     */
    public static int basisSize(final double[][] augmentedMatrix) {
        return augmentedMatrix[0].length - 1 - MatrixCalc.rank(augmentedMatrix);
    }

    /**
     * Convert a list of left parts of linear equation system from List<List<Term>> type to
     * a 2D-array.
     *
     * @param linearEquationSystem list of sums of terms of an equation, only the left parts.
     *                             The right part of each of them is considered to be 0
     * @return augmented matrix
     */
    public static double[][] convertLinearEquationSystem(final List<List<Term>> linearEquationSystem) {
        double[][] augmentedMatrix = new double[linearEquationSystem.size()][linearEquationSystem.getFirst().size() + 1];
        for (int i = 0; i < linearEquationSystem.getFirst().size(); i++)
            for (int j = 0; j < linearEquationSystem.size(); j++)
                augmentedMatrix[i][j] = linearEquationSystem.get(i).get(j).getCoefficient();
        return augmentedMatrix;
    }

    /**
     * Given an array of size of an equation, that reflects which element in the equation may
     * have voluntary value (TRUE) and which may not (FALSE) by their index similarity, return all combinations
     * of values for the voluntary value elements (i.e. free terms) for the case where only one free term
     * has value 1, while the rest of them have value 0.
     *
     * @param freeTermsFlagMask logical flags mask for an equation as an array.
     *                            TRUE if the element has the same index as the free term in the equation,
     *                            FALSE otherwise.
     * @return a list of combinations of free term values for fundamental solution system
     */
    public static List<Double[]> getFreeTermsValuesCombinations(final Boolean[] freeTermsFlagMask) {
        final List<Integer> freeTermIndexAddresses = new ArrayList<>();
        final List<Double[]> basisVectors = new ArrayList<>();
        for(int i = 0; i < freeTermsFlagMask.length; i++) {
            if (freeTermsFlagMask[i]) {
                freeTermIndexAddresses.add(i);
            }
        }
        final List<int[]> indexPermutations = JohnsonTrotter.generate(freeTermIndexAddresses.size(), freeTermIndexAddresses.size());
        for (int[] permutation : indexPermutations) {
            final Double[] arr = new Double[freeTermsFlagMask.length];
            final List<Integer> permutationValues = new ArrayList<>();
            permutationValues.add(1);
            for (int n = 1; n < permutation.length; n++)
                permutationValues.add(0);
            int j = 0;
            for (int i = 0; i < freeTermsFlagMask.length; i++)
                if (freeTermsFlagMask[i]) arr[i] = (double) permutationValues.get(permutation[j++]);
                else arr[i] = null;
            basisVectors.add(arr);
        }
        for (Double[] vector : basisVectors) cleanDoubleArrayOfNegativeZeros(vector);
        return reorderBasisVectorsAfterCyclicShiftPermutationsGenerator(basisVectors);
    }

    private static List<Double[]> reorderBasisVectorsAfterCyclicShiftPermutationsGenerator(List<Double[]> permutations) {
        final List<Double[]> result = new ArrayList<>();
        result.addFirst(permutations.getFirst());
        int j = 1;
        for (int i = permutations.size() - 1; i > 0; i--)
            result.add(j++, permutations.get(i));
        return result;
    }
}
