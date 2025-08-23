/**
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025.
 */
package algebra;

import linear.matrix.MatrixUtil;
import series.SeriesPart;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static algebra.TermUtil.doubleArrayToTermsArrayForCharacteristicPolynomial;
import static approximation.RoundingUtil.roundToNDecimals;
import static linear.matrix.Validation.isSquareMatrix;

/**
 * A utility class for equations.
 *
 * @author Viacheslav Mikhailov
 */
public class EquationUtil {

    /**
     * Modifies the input list of terms of a single variable equation,
     * by grouping its terms by power of their variable using summation,
     * then sorts the result, then returns.
     *
     * @param input a list of terms of a single variable equation with only 0 in its right part
     * @return a list of terms of a single variable equation, gathered distinct by power, ordered
     */
    public static List<Term> distinct(final List<Term> input) {
        return input.stream()
                .collect(Collectors.groupingBy(
                        (Term m) -> {
                            if (m.getPower() != .0d)
                                return m.getLetter().toString() + m.getPower();
                            else return String.valueOf(m.getPower());
                        }, Collectors.toList())).values().stream()
                .map(listOfTerms -> {
                    var iterator = listOfTerms.iterator();
                    Term result = iterator.next();
                    while (iterator.hasNext()) {
                        var next = iterator.next();
                        result = Term.builder()
                                .power(result.getPower())
                                .coefficient(result.getCoefficient() + next.getCoefficient())
                                .letter(result.getLetter())
                                .build();
                    }
                    return result;
                })
//                .filter(term -> term.getCoefficient() != 0)
                .sorted(Comparator.reverseOrder()).toList();
    }

    /**
     * Are the given terms grouped from left to right descending by their power?
     *
     * @param terms left part of the equation
     * @return true if terms of the given equation are grouped by their power. False otherwise.
     */
    public static boolean isDistinct(final List<Term> terms) {
        return isDistinct(Equation.of(terms, Term.asRealConstant(.0d)));
    }

    /**
     * Is the given equation has its terms grouped from left to right descending by their power?
     *
     * @param equation the given equation
     * @return true if terms of the given equation are grouped by their power. False otherwise.
     */
    public static boolean isDistinct(final Equation equation) {
        if (Objects.requireNonNull(Objects.requireNonNull(equation).terms()).isEmpty()) {
            return true;
        }

        Map<String, List<Term>> grouped = equation.terms().stream()
                .collect(Collectors.groupingBy(
                        (Term m) -> {
                            if (m.getPower() != 0.0d)
                                return m.getLetter().toString() + m.getPower();
                            else
                                return String.valueOf(m.getPower());
                        }));

        return grouped.size() == equation.terms().size();
    }

    /**
     * Converts the two given arguments to an {@link Equation}.
     *
     * @param coefficients  the both sides of the given linear single variable equation, as an array.
     * @param variableIndex the index of the {@code coefficients} array with the variable whose value we want to find
     * @return the Equation object
     */
    public static Equation toSingleVariableEquation(final double[] coefficients, final int variableIndex) {
        List<Term> terms = new ArrayList<>();
        for (int i = 0; i < coefficients.length - 1; i++) {
            var builder = Term.builder()
                    .coefficient(coefficients[i])
                    .power(1.0d);
            if (i != variableIndex) {
                builder.value(1.0d);
            } else {
                builder.value(null);
            }
            terms.add(builder.build());
        }
        return Equation.of(terms, Term.asRealConstant(coefficients[coefficients.length - 1]));
    }

    /**
     * Solves the single variable linear equation.
     * The right side of the equation is represented by the last element of the {@code coefficients} array.
     *
     * @param coefficients  the both sides of the linear single variable equation, as an array.
     * @param variableIndex the index of the {@code coefficients} array with the variable
     * @return the value of the variable found
     */
    public static double solveSingleVariableLinearEquation(final double[] coefficients, final int variableIndex) {
        if (coefficients.length < 2)
            throw new IllegalArgumentException("Not an equation.");
        if (coefficients.length - 1 == variableIndex)
            throw new IllegalArgumentException("The variable shouldn't be found on the right side of the equation.");
        if (coefficients[variableIndex] == .0d)
            throw new IllegalArgumentException("The single variable of the equation is multiplied by the coefficient equal to 0.");
        double sum = .0d;
        for (int i = 0; i < coefficients.length - 1; i++) {
            if (i == variableIndex) continue;
            sum = sum + coefficients[i];
        }
        sum = coefficients[coefficients.length - 1] - sum;
        return sum / coefficients[variableIndex];
    }

    /**
     * Solves the single variable linear equation.
     *
     * @param equation the equation given
     * @return the value of the variable found
     */
    public static void solveSingleVariableLinearEquation(final Equation equation) {
        var hits = 0;
        Term variable = null;
        for (Term eqTerm : equation.terms())
            if (Objects.isNull(eqTerm.getValue())) {
                variable = eqTerm;
                hits++;
            }
        if (hits != 1) throw new IllegalArgumentException("Not a single variable equation.");
        double sum = .0d;

        for (Term eqTerm : equation.terms()) {
            if (Objects.isNull(eqTerm.getValue()))
                continue;
            sum = sum + eqTerm.getCoefficient() * eqTerm.getValue();
        }
        sum = equation.equalsTo().getCoefficient() - sum;
        variable.setValue(roundToNDecimals(sum / variable.getCoefficient(), 12));
    }

    /**
     * Solves the given polynomial equation.
     *
     * @param equation equation the equation given
     * @return quadratic equation roots
     */
    public static EquationRoots<Complex> solvePolynomial(final Equation equation) {
        var equationType = EquationValidator.determinePolynomialEquationType(equation);
        var groupedByPowerTermsEquation = Equation.of(distinct(equation.terms()), equation.equalsTo());
        return switch (equationType) {
            case LINEAR -> LinearEquationSolver.solve(groupedByPowerTermsEquation);
            case QUADRATIC -> QuadraticEquationSolver.solve(groupedByPowerTermsEquation);
            case CUBIC -> CubicEquationSolver.solve(groupedByPowerTermsEquation);
            // TODO QUARTIC ->
            default -> throw new IllegalArgumentException(String.format("The equation type %s is not supported.", equationType.name()));
        };
    }

    /**
     * Cleans the given equation of terms that are multiplied by zero coefficient.
     *
     * @param equation the given equation
     * @return the cleaned equation
     */
    public static Equation removeZeroTerms(final Equation equation) {
        var result = new ArrayList<>(equation.terms());
        result.removeIf(term -> term.getCoefficient() == .0d);
        return new Equation(result, equation.equalsTo());
    }

    /**
     * The method calculates a determinant of the given matrix.
     * Only a square matrix may have a determinant.
     *
     * @param matrix - the given matrix
     * @return the determinant of the matrix
     */
    public static Equation toCharacteristicPolynomial(final double[][] matrix) {
        if (!isSquareMatrix(matrix))
            throw new IllegalArgumentException("A non-square matrix has no determinant.");
        if (matrix.length < 2)
            return Equation.of(List.of(Term.asRealConstant(matrix[0][0]), Term.asVariableX(-1.0d)), Term.asRealConstant(.0d));
        if (matrix.length == 2)
            return Equation.of(distinct(Stream.concat(
                    Terms.of(List.of(Term.asRealConstant(matrix[0][0]), Term.asVariableX(-1.0d)))
                            .multiply(Terms.of(List.of(Term.asRealConstant(matrix[1][1]), Term.asVariableX(-1.0d)))).asList().stream(),
                    Stream.of(Term.asRealConstant(-1.0d).multiply(Term.asRealConstant(matrix[0][1]).multiply(
                            Term.asRealConstant(matrix[1][0]))))).sorted().toList()), Term.asRealConstant(.0d));
        if (matrix.length == 3)
            return Equation.of(distinct(Stream.of(
                    Terms.of(List.of(Term.asRealConstant(matrix[0][0]), Term.asVariableX(-1.0d)))
                            .multiply(Terms.of(List.of(Term.asRealConstant(matrix[1][1]), Term.asVariableX(-1.0d))))
                            .multiply(Terms.of(List.of(Term.asRealConstant(matrix[2][2]), Term.asVariableX(-1.0d)))).asList(),
                    Terms.of(List.of(Term.asRealConstant(matrix[0][1]).multiply(
                            Term.asRealConstant(matrix[1][2])).multiply(
                            Term.asRealConstant(matrix[2][0])))).asList(),
                    Terms.of(List.of(Term.asRealConstant(matrix[1][0]).multiply(
                            Term.asRealConstant(matrix[2][1])).multiply(
                            Term.asRealConstant(matrix[0][2])))).asList(),
                    Terms.of(List.of(Term.asRealConstant(-1.0d)
                            .multiply(Term.asRealConstant(matrix[0][2]).multiply(
                                    Terms.of(List.of(Term.asRealConstant(matrix[1][1]), Term.asVariableX(-1.0d))).multiply(
                                            Term.asRealConstant(matrix[2][0])))))).asList(),
                    Terms.of(List.of(Term.asRealConstant(-1.0d)
                            .multiply(Term.asRealConstant(matrix[1][0]).multiply(
                                    Term.asRealConstant(matrix[0][1])).multiply(
                                    Terms.of(List.of(Term.asRealConstant(matrix[2][2]), Term.asVariableX(-1.0d))))))).asList(),
                    Terms.of(List.of(Term.asRealConstant(-1.0d)
                            .multiply(Terms.of(List.of(Term.asRealConstant(matrix[0][0]), Term.asVariableX(-1.0d))).multiply(
                                    Term.asRealConstant(matrix[2][1])).multiply(
                                    Term.asRealConstant(matrix[1][2]))))).asList()
            ).flatMap(List::stream).sorted().toList()), Term.asRealConstant(.0d));
        Terms[][] diagonalPolynomnialMatrix = doubleArrayToTermsArrayForCharacteristicPolynomial(matrix);
        Terms sumOfSums = Terms.of(new ArrayList<>());
        for (int col = 0; col < diagonalPolynomnialMatrix[0].length; col++) {
                var mmbrz = Terms.of(List.of(
                        diagonalPolynomnialMatrix[0][col].multiply(
                        Terms.of(toCharacteristicPolynomial(MatrixUtil.excludeColumnAndRow(matrix, 1, col + 1)).terms()
                                .stream().map(SeriesPart.class::cast).collect(Collectors.toList())))));
                if (col % 2 != 0) mmbrz = mmbrz.multiply(Term.asRealConstant(-1.0d));
            sumOfSums = (Terms) sumOfSums.add(mmbrz);
            }
        return Equation.of(distinct(sumOfSums.asList().stream().sorted().toList()), Term.asRealConstant(.0d));
    }
}
