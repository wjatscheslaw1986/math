/**
 * Wjatscheslaw Michailov <taleskeeper@yandex.com> All rights reserved © 2025.
 */
package linear.equation;

import linear.matrix.MatrixCalc;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static linear.equation.LinearEquationSystemUtil.isSolvable;
import static linear.equation.LinearEquationSystemUtil.resolveUsingJordanGaussMethod;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Wjatscheslaw Michailov
 */
public class LinearEquationSystemUtilTest {

    @BeforeAll
    static void before() {
        System.out.printf("Running tests in %s%s", LinearEquationSystemUtilTest.class, System.lineSeparator());
    }

    @Test
    public void resolveUsingJordanGaussMethodTest() {
        var linearEquationSystem = new double[][]{
                {1, 2, 3, -2, 4},
                {2, 6, 10, -2, 14},
                {1, 4, 8, -3, 12},
                {2, 2, 1, -3, 0}
        };
        var solutionWhereFreeVariableIsZero = new double[]{0, -1, 2, 0};
        var singleSolution = new double[4];
        assertFalse(MatrixCalc.areEqual(solutionWhereFreeVariableIsZero, singleSolution));
        assertEquals(Integer.MAX_VALUE, resolveUsingJordanGaussMethod(linearEquationSystem, singleSolution));
        assertTrue(isSolvable(linearEquationSystem));
        assertTrue(MatrixCalc.areEqual(solutionWhereFreeVariableIsZero, singleSolution));

        linearEquationSystem = new double[][]{
                {2, -1, 3, 4, 5},
                {6, -3, 7, 8, 9},
                {5, -4, 9, 10, 11},
                {4, -2, 5, 6, 7}
        };
        solutionWhereFreeVariableIsZero = new double[]{0, 4, 3, 0};
        singleSolution = new double[4];
        assertFalse(MatrixCalc.areEqual(solutionWhereFreeVariableIsZero, singleSolution));
        assertEquals(Integer.MAX_VALUE, resolveUsingJordanGaussMethod(linearEquationSystem, singleSolution));
        assertTrue(isSolvable(linearEquationSystem));
        assertTrue(MatrixCalc.areEqual(solutionWhereFreeVariableIsZero, singleSolution));
    }
}
