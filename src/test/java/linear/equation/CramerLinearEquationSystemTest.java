package linear.equation;

import linear.equation.exception.LinearEquationSystemException;
import linear.matrix.MatrixCalc;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * @author Wjatscheslaw Michailov
 */
public class CramerLinearEquationSystemTest {
    
    @Test
    public void testCramerMethodSolution() throws LinearEquationSystemException {
        var matrix = new double[][]{{2,1,1,2},{1,3,1,5},{1,1,5,-7}};
        var leftParts = new double[][]{{2,1,1},{1,3,1},{1,1,5}};
        var rightParts = new double[]{2,5,-7};
        var solutions = new double[]{1,2,-2};
        var equationSystem = new CramerLinearEquationSystem(LinearEquationSystemUtil::resolveUsingCramerMethod, matrix);
        assertTrue(MatrixCalc.areEqual(equationSystem.getCoefficients(), leftParts));
        assertTrue(MatrixCalc.areEqual(equationSystem.getFreeMembers(), rightParts));
        assertTrue(MatrixCalc.areEqual(equationSystem.getResolved(), solutions));
    }

    @Test
    public void testReverseMatrixSolution() throws LinearEquationSystemException {
        var matrix = new double[][]{{2,1,1,2},{1,3,1,5},{1,1,5,-7}};
        var leftParts = new double[][]{{2,1,1},{1,3,1},{1,1,5}};
        var rightParts = new double[]{2,5,-7};
        var solutions = new double[]{1,2,-2};
        var equationSystem = new CramerLinearEquationSystem(LinearEquationSystemUtil::resolveUsingReverseMatrixMethod, matrix);
        assertTrue(MatrixCalc.areEqual(equationSystem.getCoefficients(), leftParts));
        assertTrue(MatrixCalc.areEqual(equationSystem.getFreeMembers(), rightParts));
        // TODO approximate double values to < EPS
//        assertTrue(MatrixCalc.areEqual(equationSystem.getResolved(), solutions));
    }
}
