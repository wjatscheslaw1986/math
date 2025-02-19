/**
 * Wjatscheslaw Michailov <taleskeeper@yandex.com> All rights reserved © 2025.
 */
package linear.equation;

import linear.matrix.MatrixCalc;
import linear.matrix.exception.MatrixException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static linear.equation.LinearEquationSystemUtil.isSolvable;
import static linear.equation.LinearEquationSystemUtil.resolveUsingJordanGaussMethod;
import static linear.equation.SolutionsCount.INFINITE;
import static linear.equation.SolutionsCount.ZERO;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Wjatscheslaw Michailov
 */
public class LinearEquationSystemUtilTest {

    @BeforeAll
    static void before() {
        System.out.printf("Running tests in %s%s", LinearEquationSystemUtilTest.class, System.lineSeparator());
    }

    /**
     * Решить системы уравнений методом Гаусса.
     * Примеры приведены по книге А.С.Киркинский - "Линейная Алгебра и Аналитическая
     * Геометрия" - 2006
     */
    @Test
    public void resolveUsingJordanGaussMethodTest() {

        // a)

        var linearEquationSystem = new double[][]{
                {1, 2, 3, -2, 4},
                {2, 6, 10, -2, 14},
                {1, 4, 8, -3, 12},
                {2, 2, 1, -3, 0}
        };
        var solutionWhereFreeVariableIsZero = new double[]{0, -1, 2, 0};
        var singleSolution = resolveUsingJordanGaussMethod(linearEquationSystem);
        assertEquals(INFINITE, singleSolution.solutionsCount());
        assertTrue(isSolvable(linearEquationSystem));
        assertTrue(MatrixCalc.areEqual(solutionWhereFreeVariableIsZero, singleSolution.vectors().getFirst()));

        // б)

        linearEquationSystem = new double[][]{
                {3, -2, 1, -3, 2},
                {2, 4, -1, 1, 13},
                {2, -5, 2, -3, -6},
                {3, 1, -2, 1, 15}
        };
        solutionWhereFreeVariableIsZero = new double[]{3, 1, -2, 1};
        singleSolution = resolveUsingJordanGaussMethod(linearEquationSystem);
        assertEquals(SolutionsCount.SINGLE, singleSolution.solutionsCount());
        assertTrue(isSolvable(linearEquationSystem));
        assertTrue(MatrixCalc.areEqual(solutionWhereFreeVariableIsZero, singleSolution.vectors().getFirst()));

        // в)

        linearEquationSystem = new double[][]{
                {2, 3, 1, 0, 5},
                {4, 9, 3, -2, 11},
                {2, 6, 2, -2, 9}
        };
        singleSolution = resolveUsingJordanGaussMethod(linearEquationSystem);
        assertTrue(singleSolution.vectors().isEmpty());
        assertEquals(ZERO, singleSolution.solutionsCount());
        assertFalse(isSolvable(linearEquationSystem));

        // е)

        linearEquationSystem = new double[][]{
                {2, -1, 3, 4, 5},
                {6, -3, 7, 8, 9},
                {5, -4, 9, 10, 11},
                {4, -2, 5, 6, 7}
        };
        solutionWhereFreeVariableIsZero = new double[]{0, 4, 3, 0};
        singleSolution = resolveUsingJordanGaussMethod(linearEquationSystem);
        assertEquals(INFINITE, singleSolution.solutionsCount());
        assertTrue(isSolvable(linearEquationSystem));
        assertTrue(MatrixCalc.areEqual(solutionWhereFreeVariableIsZero, singleSolution.vectors().getFirst()));
    }
}
