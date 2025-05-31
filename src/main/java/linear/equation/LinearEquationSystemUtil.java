/**
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025.
 */
package linear.equation;

import algebra.EquationUtil;
import combinatorics.CyclicShiftPermutationsGenerator;
import linear.matrix.MatrixCalc;
import linear.matrix.MatrixUtil;
import linear.matrix.RowEchelonFormUtil;
import linear.matrix.Validation;
import linear.matrix.exception.MatrixException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static algebra.EquationUtil.cleanDoubleArrayOfNegativeZeros;
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
     * <p>Only applicable to Cramer matrices. Use {@link linear.matrix.Validation}.isCramer() method
     * beforehand to make sure you aren't getting wrong results.</p>
     *
     * @param coefficients matrix made of left part of the equations
     * @param freeMembers  a vector made of right part of the equations
     * @return a vector of variable values (answers) for the equations, regarding their order in the equation.
     */
    public static double[] resolveUsingCramerMethod(final double[][] coefficients, final double[] freeMembers) {
        final var resolved = new double[freeMembers.length];
        final double determinant = MatrixCalc.det(coefficients);
        for (int i = 0; i < freeMembers.length; i++)
            resolved[i] = MatrixCalc.det(substituteColumn(coefficients, freeMembers, i))
                    / determinant;
        return resolved;
    }

    /**
     * Resolve given linear equations system using reverse matrix method.
     * <p>Only applicable to Cramer matrices. Use {@link linear.matrix.Validation}.isCramer() method
     * beforehand to be sure you aren't getting wrong results.</p>
     *
     * @param coefficients a square matrix of coefficients of the left parts of each equation
     * @param freeMembers  a vector of free members (i.e. right parts of each equation)
     * @return a vector of variable values (answers) for the equations, regarding their order in the equation.
     */
    public static double[] resolveUsingReverseMatrixMethod(double[][] coefficients, double[] freeMembers) {
        var solution = MatrixCalc.multiply(MatrixCalc.inverse(coefficients), freeMembers);
        MatrixUtil.eliminateEpsilon(solution);
        return solution;
    }

    /**
     * Resolve given linear equations system using Jordan-Gauss method.
     * <p>
     *     <b>IMPORTANT:</b> this method modifies the argument as it performs
     *     its job. Use {@link MatrixUtil.copy()} on the argument you pass to this method if
     *     you need it for further calculations.
     * </p>
     *
     * @param equations linear equations matrix including both sides of each equation
     * @return number of solutions for the system. Integer.MAX_VALUE for infinite number of solutions.
     */
    public static Solution resolveUsingJordanGaussMethod(final double[][] equations) {
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
                //TODO basis
                return new Solution(INFINITE, solution, List.of()); // Infinite solutions
            }

        return new Solution(SINGLE, solution, null); // Unique solution
    }

    /**
     * TODO
     *
     * @param augmentedMatrix
     * @return
     * @throws MatrixException
     */
    public static List<double[]> fundamental(final double[][] augmentedMatrix) throws MatrixException {
        var ref = removeAllZeroesRows(RowEchelonFormUtil.toRowEchelonForm(augmentedMatrix));
        var freeMembersLeft = basisSize(augmentedMatrix);
        var freeVariableIndices = getEquationMemberFlags(ref, freeMembersLeft);
        if (freeVariableIndices.length == 0)
            return List.of(resolveUsingReverseMatrixMethod(
                    MatrixUtil.removeMarginalColumn(ref, false),
                    MatrixUtil.getColumn(ref, ref[0].length)));
        var fundamental = new ArrayList<double[]>();
        var freeMembersMultiplierCombinations = getFreeMembersMultiplierCombinations(freeVariableIndices);
        for (int i = 0; i < freeMembersMultiplierCombinations.size(); i++) {
            double[] singleVariableEquationSolution = new double[ref[0].length - 1];
            for (int j = ref[0].length - freeMembersMultiplierCombinations.size() - 2; j >= 0; j--) {
                singleVariableEquationSolution[j] = solveForRowEchelonFormRowForGivenFreeMemberValues(ref, j, freeMembersMultiplierCombinations.get(i), freeVariableIndices);
            }
            fundamental.add(singleVariableEquationSolution);
        }
        return fundamental;
    }

    public static double solveForRowEchelonFormRowForGivenFreeMemberValues(double[][] augmentedMatrix, int row, double[] freeMembersMultiplierCombination, Boolean[] freeMembersMask) {
        int pivotIndex = findPivotIndex(MatrixUtil.removeMarginalColumn(augmentedMatrix, false), row);
        double[] coefficients = new double[augmentedMatrix[row].length - pivotIndex - 1];
        System.arraycopy(augmentedMatrix[row], pivotIndex, coefficients, 0, coefficients.length);
        for (int i = 0; i < coefficients.length; i++) {
            if (freeMembersMask[i + pivotIndex]) {
                coefficients[i] *= freeMembersMultiplierCombination[i + pivotIndex];
            }
        }
        return EquationUtil.solveSingleVariableLinearEquation(coefficients, 0);
    }



    /**
     * Returns an array of equation members' flags as an array of {@link Boolean}.
     * <p>
     * Each array element (the flag) corresponds to an equation member by their common index.
     * The flag TRUE means the variable in the linear equation found by the same index is a free variable, so that
     * its value is arbitrary.
     * The flag FALSE means the variable of the linear equation found by the same index is <b>not</b> a free variable, so that
     * its value should be found by solving a corresponding single variable linear equation.
     * </p>
     *
     * @param ref the row echelon form of a matrix
     * @param basisSize number of free members in each of the linear equations
     * @return an array of member flags for the equation
     */
    public static Boolean[] getEquationMemberFlags(double[][] ref, int basisSize) {
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
     * TODO Remove it?
     * The method returns a list of coefficient combinations, size of {@code freeMemberIndices}. Each combination is
     * the given template {@code coefficientsTemplate} with only one of its free members
     * (according to the index found in the {@code freeMemberIndices}) is equal to 1.0d,
     * with the rest of the free members == 0.0d.
     *
     * @param coefficientsTemplate the given template for the resulting equations
     * @param pivot the index of the member of the coefficientsTemplate which is supposed to be an index of a single
     *              unknown variable in any of the resulting equations
     * @param freeMemberIndices indices of free (i.e. volatile, may be of any value) members of the given coefficientsTemplate
     * @return the list of coefficient combinations
     * @deprecated use getEquationMemberFlags() instead.
     */
    @Deprecated(forRemoval = true)
    public static List<double[]> deriveCoefficientCombinationsFromTemplateForFreeMembersAfterPivot(final double[] coefficientsTemplate,
                                                                                                   final int pivot,
                                                                                                   final List<Integer> freeMemberIndices) {
        final List<double[]> coefficientCombinations = new ArrayList<>();
        int counter = 0;
        while (counter < freeMemberIndices.size()) {
            var basisVectorTemplate = new double[coefficientsTemplate.length];
            System.arraycopy(coefficientsTemplate, 0, basisVectorTemplate, 0, coefficientsTemplate.length);
            int cc = 0;
            for (int i = pivot + 1; i < basisVectorTemplate.length - 1; ++i) {
                if (freeMemberIndices.contains(i))
                    if (i == freeMemberIndices.get(counter))
                        basisVectorTemplate[freeMemberIndices.get(cc++)] = 1.0d;
                    else
                        basisVectorTemplate[freeMemberIndices.get(cc++)] = .0d;
                }
            coefficientCombinations.add(basisVectorTemplate);
            counter++;
        }
        return coefficientCombinations;
    }

    /**
     * Решить систему линейных уравнений.
     *
     * @param equations система линейных уравнений, в виде массива уравнений. Каждое уравнение представлено
     *                  массивом коэффициентов. Последний элемент каждого уравнения является правой
     *                  частью уравнения, обязательно константу.
     * @return решение системы линейных уравнений
     */
    public static Solution resolve(final double[][] equations) {
        var ref = RowEchelonFormUtil.toREF(equations);
        int rowIndex = getLastNonFreeVariableIndex(ref);
        var addresses = new int[ref[0].length - 1];
        if (rowIndex == ref.length - 1) {
            // No all-zero rows in REF matrix case
            if (!Validation.isCramer(ref)) return new Solution(ZERO, null, null);
            var solution = LinearEquationSystemUtil.resolveUsingCramerMethod(
                    MatrixUtil.removeMarginalColumn(ref, false),
                    MatrixUtil.getColumn(ref, ref[0].length));
            for (int i = 0; i < addresses.length; i++) addresses[i] = i;
            return new Solution(SINGLE, solution, null);
        }
        var zeroSolutionTemplate = new double[ref[0].length];
        for (int i = rowIndex + 1; i < zeroSolutionTemplate.length - 1; i++) {
            // Assign free members a value of 0, for convenience
            zeroSolutionTemplate[i] = 0.0d; // since a free equation member can be any number we choose 0
            addresses[i] = -1;
        }
        var basisVectorsTemplates = new ArrayList<double[]>();
        Outer:
        for (int i = rowIndex; i < addresses.length; i++) {
            double[] c = new double[zeroSolutionTemplate.length];
            System.arraycopy(zeroSolutionTemplate, 0, c, 0, zeroSolutionTemplate.length);
            for (int j = rowIndex; j < addresses.length; j++) {
                if (addresses[i] == -1 && i == j) {
                    c[j] = 1.0d;
                    basisVectorsTemplates.add(c);
                    continue Outer;
                }
            }
        }
        var basisVectors = new ArrayList<double[]>();
        var zeroSolution = getVector(ref, zeroSolutionTemplate, rowIndex);
        for (var template : basisVectorsTemplates) {
            basisVectors.add(getVector(ref, template, rowIndex));
        }
        return new Solution(INFINITE, zeroSolution, basisVectors);
    }

    /**
     * TODO this does not cover the case where free variables are scattered. Delete it.
     * Get the biggest index of a non-free variable. The very last index of a variable in any equation minus
     * this index should result in basis size, i.e. number of dimensions of the linear space.
     *
     * @param rowEchelonFormMatrix REF for the augmented matrix made of a linear equations system
     * @return the biggest index of a non-free variable
     * @deprecated use findPivotIndex() instead.
     */
    @Deprecated(forRemoval = true)
    private static int getLastNonFreeVariableIndex(final double[][] rowEchelonFormMatrix) {
        int rowIndex = rowEchelonFormMatrix.length - 1;
        double[] row = rowEchelonFormMatrix[rowIndex];
        while (rowIndex > -1 && allZeroes(row)) {
            row = rowEchelonFormMatrix[--rowIndex];
        }
        return rowIndex;
    }

    /**
     * Returns a vector of a basis on linear space of solutions for a given linear equation system.
     * The given linear equation system is represented by a row echelon form for the matrix
     * made of equations' coefficients.
     * <p></p>
     * The algorithm is based on features of matrix row echelon form.
     *
     * @param rowEchelonFormMatrix a row echelon form matrix for the matrix made of the linear equations system coefficients
     * @param freeMemberValuesTemplate a result template with pre-filled values for the result after {@code rowIndex}
     * @param rowIndex the largest index for a non-free member for the equation system
     * @return a single vector of a fundamental solution system, for the linear equation system
     */
    private static double[] getVector(final double[][] rowEchelonFormMatrix,
                                      final double[] freeMemberValuesTemplate,
                                      final int rowIndex) {
        var result = new double[freeMemberValuesTemplate.length - 1];
        int j = rowIndex;
        System.arraycopy(freeMemberValuesTemplate, 0, result, 0, freeMemberValuesTemplate.length - 1);
        while (j > -1) {
            freeMemberValuesTemplate[j] = rowEchelonFormMatrix[j][j];
            for (int i = j + 1; i < freeMemberValuesTemplate.length - 1; i++) {
                freeMemberValuesTemplate[i] = result[i] * rowEchelonFormMatrix[j][i];
            }
            result[j] = EquationUtil.solveSingleVariableLinearEquation(freeMemberValuesTemplate, j);
            j--;
        }
        return result;
    }

    private static boolean allZeroes(final double[] numbers) {
        for (double d : numbers)
            if (d != .0d && d != -.0d) return false;
        return true;
    }

    /*
     * This method substitutes one column of a given matrix with a given vector,
     * at a certain position 'index'.
     *
     * @param matrix  the original matrix
     * @param column  the given vector
     * @param index   column index
     * @return        new matrix of same size but with one of its columns substituted by <i>column</i>
     */
    static double[][] substituteColumn(final double[][] matrix, final double[] column, final int index) {
        if (matrix[0].length <= index)
            throw new ArrayIndexOutOfBoundsException(String
                    .format("Column index %d is out of matrix columns size of %d", index, matrix[0].length));
        final double[][] result = MatrixUtil.copy(matrix);
        for (int row = 0; row < matrix.length; row++)
            result[row][index] = column[row];
        return result;
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
        return MatrixCalc.rank(MatrixUtil.removeMarginalColumn(augmentedMatrix, false)) == MatrixCalc.rank(augmentedMatrix);
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

    public static List<double[]> getFreeMembersMultiplierCombinations(final Boolean[] freeMembersFlagMask) {
        final List<Integer> freeMemberIndexAddresses = new ArrayList<>();
        final List<double[]> basisVectors = new ArrayList<>();
        for(int i = 0; i < freeMembersFlagMask.length; i++) {
            if (freeMembersFlagMask[i]) {
                freeMemberIndexAddresses.add(i);
            }
        }
        final List<int[]> indexPermutations = CyclicShiftPermutationsGenerator.generate(freeMemberIndexAddresses.size())
                .stream()
                .limit(freeMemberIndexAddresses.size())
                .toList();
        for (int[] permutation : indexPermutations) {
            final double[] arr = new double[freeMembersFlagMask.length];
            final List<Integer> permutationValues = new ArrayList<>();
            permutationValues.add(1);
            for (int n = 1; n < permutation.length; n++)
                permutationValues.add(0);
            int j = 0;
            for (int i = 0; i < freeMembersFlagMask.length; i++)
                if (freeMembersFlagMask[i]) arr[i] = permutationValues.get(permutation[j++]);
                else arr[i] = 1.0d;
            basisVectors.add(arr);
        }
        for (double[] vector : basisVectors) cleanDoubleArrayOfNegativeZeros(vector);
        return reorderBasisVectorsAfterCyclicShiftPermutationsGenerator(basisVectors);
    }

    private static List<double[]> reorderBasisVectorsAfterCyclicShiftPermutationsGenerator(List<double[]> permutations) {
        final List<double[]> result = new ArrayList<>();
        result.addFirst(permutations.getFirst());
        int j = 1;
        for (int i = permutations.size() - 1; i > 0; i--)
            result.add(j++, permutations.get(i));
        return result;
    }
}
