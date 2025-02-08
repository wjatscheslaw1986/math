/**
 * Wjatscheslaw Michailov <taleskeeper@yandex.com> All rights reserved © 2025.
 */
package linear.equation;

import linear.CalcTest;
import linear.matrix.MatrixCalc;
import static org.junit.jupiter.api.Assertions.*;

import linear.matrix.MatrixUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * @author Wjatscheslaw Michailov
 */
public class CramerLinearEquationSystemTest {

    @BeforeAll
    static void before() {
        System.out.printf("Running tests in %s%s", CramerLinearEquationSystemTest.class, System.lineSeparator());
    }

    @Test
    public void testCramerMethodSolution() {
        var matrix = new double[][]{{2,1,1,2},{1,3,1,5},{1,1,5,-7}};
        var leftParts = new double[][]{{2,1,1},{1,3,1},{1,1,5}};
        var rightParts = new double[]{2,5,-7};
        var solutions = new double[]{1,2,-2};
        var equationSystem = new CramerLinearEquationSystem(LinearEquationSystemUtil::resolveUsingCramerMethod, matrix);
        assertTrue(MatrixCalc.areEqual(equationSystem.getCoefficients(), leftParts));
        assertTrue(MatrixCalc.areEqual(equationSystem.getFreeMembers(), rightParts));
        assertTrue(MatrixCalc.areEqual(equationSystem.getResolved(), solutions));

        matrix = new double[][]{{3,-5,7,10},{2,-3,2,7},{1,4,-4,9}};
        leftParts = MatrixUtil.removeMarginalColumn(matrix, false);
        rightParts = new double[]{10,7,9};
        solutions = new double[]{5,1,0};
        equationSystem = new CramerLinearEquationSystem(LinearEquationSystemUtil::resolveUsingCramerMethod, matrix);
        assertTrue(MatrixCalc.areEqual(equationSystem.getCoefficients(), leftParts));
        assertTrue(MatrixCalc.areEqual(equationSystem.getFreeMembers(), rightParts));
        assertTrue(MatrixCalc.areEqual(equationSystem.getResolved(), solutions));
    }

    @Test
    public void testReverseMatrixSolution() {
        var matrix = new double[][]{{2,1,1,2},{1,3,1,5},{1,1,5,-7}};
        var leftParts = new double[][]{{2,1,1},{1,3,1},{1,1,5}};
        var rightParts = new double[]{2,5,-7};
        var solutions = new double[]{1,2,-2};
        var equationSystem = new CramerLinearEquationSystem(LinearEquationSystemUtil::resolveUsingReverseMatrixMethod, matrix);
        assertTrue(MatrixCalc.areEqual(equationSystem.getCoefficients(), leftParts));
        assertTrue(MatrixCalc.areEqual(equationSystem.getFreeMembers(), rightParts));
        assertTrue(MatrixCalc.areEqual(equationSystem.getResolved(), solutions));

        matrix = new double[][]{{3,-5,7,10},{2,-3,2,7},{1,4,-4,9}};
        leftParts = MatrixUtil.removeMarginalColumn(matrix, false);
        rightParts = new double[]{10,7,9};
        solutions = new double[]{5,1,0};
        equationSystem = new CramerLinearEquationSystem(LinearEquationSystemUtil::resolveUsingReverseMatrixMethod, matrix);
        assertTrue(MatrixCalc.areEqual(equationSystem.getCoefficients(), leftParts));
        assertTrue(MatrixCalc.areEqual(equationSystem.getFreeMembers(), rightParts));
        assertTrue(MatrixCalc.areEqual(equationSystem.getResolved(), solutions));
    }
}
