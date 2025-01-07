package test;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import linear.matrix.DoubleMatrixCalc;
import linear.matrix.exception.MatrixException;

public class Runner {
	
	@BeforeAll
	static void before() {
		System.out.println(String.format("Running tests %s.class", Runner.class));
	}

	@Test
	public void runTest() throws MatrixException {
		  double[][] matrix = new double[][]{
              {2.0d, 1.0d, 3.0d},
              {1.0d, -1.0d, 7.0d},
              {0.0d, 2.0d, -7.0d}
      };

      double det = DoubleMatrixCalc.det(matrix);
      double[][] revMatrix = DoubleMatrixCalc.multiplyMatrixOnNumber(
              DoubleMatrixCalc.transposeMatrix(
                      DoubleMatrixCalc.cofactorsMatrix(matrix)), 1/det);
      double[] col = new double[]{2.0d, 2.0d, -1.0d};

      System.out.println("det = " + det);
      System.out.println("REV MATRIX");
      System.out.println(DoubleMatrixCalc.print(revMatrix));
      System.out.println("VECTOR");
      System.out.println(DoubleMatrixCalc.print(col));
      col = DoubleMatrixCalc.multiplyMatrixOnColumn(revMatrix, col);
      System.out.println("X = " + col[0]);
      System.out.println("Y = " + col[1]);
      System.out.println("Z = " + col[2]);
	}
}
