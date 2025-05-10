/**
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025.
 */
package linear.equation;

import linear.matrix.MatrixCalc;
import linear.matrix.MatrixUtil;
import linear.matrix.RowEchelonFormUtil;
import linear.spatial.VectorCalc;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static linear.equation.LinearEquationSystemUtil.*;
import static linear.equation.SolutionsCount.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@linkplain linear.equation.LinearEquationSystemUtil } class.
 *
 * @author Viacheslav Mikhailov
 */
public class LinearEquationSystemUtilTest {

    @BeforeAll
    static void before() {
        System.out.printf("Running tests in %s%s", LinearEquationSystemUtilTest.class, System.lineSeparator());
    }

    /**
     * Решить системы уравнений методом Гаусса.
     * <p>
     * Примеры приведены по книге А.С.Киркинский - "Линейная Алгебра и Аналитическа Геометрия" - 2006
     * </p>
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
        var solution = resolveUsingJordanGaussMethod(linearEquationSystem);
        assertEquals(INFINITE, solution.solutionsCount());
        assertTrue(isSolvable(linearEquationSystem));
        assertTrue(VectorCalc.areEqual(solutionWhereFreeVariableIsZero, solution.solution()));

        // б)

        linearEquationSystem = new double[][]{
                {3, -2, 1, -3, 2},
                {2, 4, -1, 1, 13},
                {2, -5, 2, -3, -6},
                {3, 1, -2, 1, 15}
        };
        solutionWhereFreeVariableIsZero = new double[]{3, 1, -2, 1};
        solution = resolveUsingJordanGaussMethod(linearEquationSystem);
        assertEquals(SINGLE, solution.solutionsCount());
        assertTrue(isSolvable(linearEquationSystem));
        assertTrue(VectorCalc.areEqual(solutionWhereFreeVariableIsZero, solution.solution()));

        // в)

        linearEquationSystem = new double[][]{
                {2, 3, 1, 0, 5},
                {4, 9, 3, -2, 11},
                {2, 6, 2, -2, 9}
        };
        solution = resolveUsingJordanGaussMethod(linearEquationSystem);
        assertNull(solution.solution());
        assertEquals(ZERO, solution.solutionsCount());
        assertFalse(isSolvable(linearEquationSystem));

        // д)

        linearEquationSystem = new double[][]{
                {6, 9, 5, 6, 7},
                {4, 6, 3, 4, 5},
                {2, 3, 1, 2, 3},
                {2, 3, 2, 2, 2}
        };
        solutionWhereFreeVariableIsZero = new double[]{2, 0, -1, 0};
        solution = resolveUsingJordanGaussMethod(linearEquationSystem);
        assertEquals(INFINITE, solution.solutionsCount());
        assertTrue(isSolvable(linearEquationSystem));
        assertTrue(VectorCalc.areEqual(solutionWhereFreeVariableIsZero, solution.solution()));

        var basis = solution.basis();
        assertEquals(2, basis.size());
        assertTrue(VectorCalc.areEqual(new double[]{2, 1, -1, 0}, basis.get(0)));
        assertTrue(VectorCalc.areEqual(new double[]{2, 0, -1, 1}, basis.get(1)));

        // е)

        linearEquationSystem = new double[][]{
                {2, -1, 3, 4, 5},
                {6, -3, 7, 8, 9},
                {5, -4, 9, 10, 11},
                {4, -2, 5, 6, 7}
        };
        solutionWhereFreeVariableIsZero = new double[]{0, 4, 3, 0};
        solution = resolveUsingJordanGaussMethod(linearEquationSystem);
        assertEquals(INFINITE, solution.solutionsCount());
        assertTrue(isSolvable(linearEquationSystem));
        assertTrue(VectorCalc.areEqual(solutionWhereFreeVariableIsZero, solution.solution()));
    }

    /**
     * Найти фундаментальную систему решений.
     * <p>
     * Примеры приведены по книге А.С.Киркинский - "Линейная Алгебра и Аналитическа Геометрия" - 2006
     * </p>
     */
    @Test
    public void given_linear_equations_system_find_fundamental_solutions_system() {
        var linearEquationSystem = new double[][]{
                {1, 2, 3, 4, 0},
                {3, -5, 1, -2, 0},
                {4, -3, 4, 2, 0}
        };

        int variablesCount = linearEquationSystem[0].length - 1;
        var solution = resolve(MatrixUtil.copy(linearEquationSystem));

        /*
         * Number of dimensions (vectors of the basis) of the equation system solution space must be n - r,
         * where n is number of variables, and r is rank of the matrix.
         */
        assertEquals(2, MatrixCalc.rank(linearEquationSystem));
        assertEquals(2, solution.basis().size());
        assertEquals(2, variablesCount
                - MatrixCalc.rank(MatrixUtil.removeMarginalColumn(MatrixUtil.copy(linearEquationSystem), false)));

        //TODO find Jordan-Gauss limitations
        assertArrayEquals(new double[]{-(17d/11d),-(8d/11d), 1d, 0d}, solution.basis().get(0));
        assertArrayEquals(new double[]{-(16d/11d),-(14d/11d), 0d, 1d}, solution.basis().get(1));
    }

    @Test
    void given_resolve() {
        var linearEquationSystem = new double[][]{
                {1, 2, 3, -2, 4},
                {2, 6, 10, -2, 14},
                {1, 4, 8, -3, 12},
                {2, 2, 1, -3, 0}
        };
        var left = MatrixUtil.removeMarginalColumn(linearEquationSystem, false);
        System.out.println(MatrixUtil.print(linearEquationSystem));
        RowEchelonFormUtil.toRREF(linearEquationSystem);
        System.out.println(MatrixUtil.print(linearEquationSystem));

    }
}
