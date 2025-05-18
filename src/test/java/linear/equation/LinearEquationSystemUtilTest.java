/**
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025.
 */
package linear.equation;

import linear.matrix.MatrixCalc;
import linear.matrix.MatrixUtil;
import linear.matrix.RowEchelonFormUtil;
import linear.matrix.exception.MatrixException;
import linear.spatial.VectorCalc;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

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

        // г)

        linearEquationSystem = new double[][]{
                {5, -2, -3, 4, 7},
                {10, -4, -16, 10, 22},
                {5, -2, 2, 3, 3}
        };
        solutionWhereFreeVariableIsZero = new double[]{0.92d, 0.0d, -0.8d, 0.0d};
        solution = resolveUsingJordanGaussMethod(linearEquationSystem);
        assertNotNull(solution.solution());
        assertEquals(INFINITE, solution.solutionsCount());
        assertTrue(isSolvable(linearEquationSystem));
        assertTrue(VectorCalc.areEqual(solutionWhereFreeVariableIsZero, solution.solution()));

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

        //TODO
//        var basis = solution.basis();
//        assertEquals(2, basis.size());
//        assertTrue(VectorCalc.areEqual(new double[]{2, 1, -1, 0}, basis.get(0)));
//        assertTrue(VectorCalc.areEqual(new double[]{2, 0, -1, 1}, basis.get(1)));

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
        List<Map<Integer, Double>> s2;
//        try {
//            s2 = resolve2(MatrixUtil.copy(linearEquationSystem));
//        } catch (MatrixException e) {
//            throw new RuntimeException(e);
//        }

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
    void given_linear_equations_system_find_dimensions_size_of_the_linear_space() {
        var matrix = new double[][]{
                {5.0d, -2.0d, -3.0d, 4.0d, 7.0d},
                {0.0d, 0.0d, -10.0d, 2.0d, 8.0d},
                {0.0d, 0.0d, 5.0d, -1.0d, -4.0d}};
        assertEquals(2, basisSize(matrix));
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

    @Test
    void given_equation_return_combination() {
        var linearEquationSystem = new double[][]{
                {1, 2, 3, 4, 0},
                {3, -5, 1, -2, 0},
                {4, -3, 4, 2, 0}
        };
        var freeMemberAddresses = List.of(1,3);
        List<double[]> combination = deriveCoefficientCombinationsFromTemplateForFreeMembersAfterPivot(linearEquationSystem[0], 0, freeMemberAddresses);
        assertEquals(freeMemberAddresses.size(), combination.size());
        assertArrayEquals(new double[]{1, 1.0, 3, 0.0, 0}, combination.get(0));
        assertArrayEquals(new double[]{1, 0.0, 3, 1.0, 0}, combination.get(1));
    }

    @Test
    void given_row_echelon_form_matrix_find_pivot_for_the_row() {
        var ref = new double[][]{
                {1.0096634218430616d, -8.772286757703684d, -0.44083720521676284d, 0.8588440971684381d},
                {.0d, -6.420896947702297d, 3.5253331532074323d, 4.864061213084703d},
                {.0d, .0d, -7.092716530960487d, -4.478958333291305d},
                {.0d, .0d, .0d, 2.962818038754156d}};

        for (int i = ref.length - 1; i >= 0; i--)
            assertEquals(i, findPivotIndex(ref, i));
    }

    @Test
    void given_equation_return_free_variable_indices_list() throws MatrixException {
        var linearEquationSystem = new double[][]{
                {1, 2, 3, 4, 0},
                {3, -5, 1, -2, 0},
                {4, -3, 4, 2, 0}
        };
        var expectedResult = new int[]{1, 1, -1, -1};
        assertArrayEquals(expectedResult,
                getEquationMemberFlags(RowEchelonFormUtil.toRowEchelonForm(linearEquationSystem), basisSize(linearEquationSystem)));
        linearEquationSystem = new double[][]{
                {5, -2, -3, 4, 7},
                {10, -4, -16, 10, 22},
                {5, -2, 2, 3, 3}
        };
        expectedResult = new int[]{1, -1, 1, -1};
        assertArrayEquals(expectedResult,
                getEquationMemberFlags(RowEchelonFormUtil.toRowEchelonForm(linearEquationSystem), basisSize(linearEquationSystem)));

    }
}
