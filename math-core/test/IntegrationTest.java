/**
 * 
 */
package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import linear.matrix.DoubleMatrixCalc;
import linear.matrix.exception.MatrixException;

/**
 * @author vaclav
 */
public final class IntegrationTest {

	@BeforeAll
	static void before() {
		System.out.println(String.format("Running tests in %s.class", IntegrationTest.class));
	}

	/**
	 * Даны две матрицы A и B, найти матрицу AB - BA.
	 * Примеры приведены по книге А.С.Киркинский - "Линейная Алгебра и Аналитическая Геометрия" - 2006
	 * 
	 * @throws MatrixException в случае неправильных матриц
	 */
	@Test
	public void matricesMultiplicationAndSubtraction() throws MatrixException {
		double[][] A = new double[][] { { 3.0d, -4.0d }, { 2.0d, 5.0d } };
		double[][] B = new double[][] { { -1.0d, 5.0d }, { -2.0d, -3.0d } };

		double[][] AB = new double[][] { { 5.0d, 27.0d }, { -12.0d, -5.0d } };
//		System.out.println(DoubleMatrixCalc.print(AB));
//		System.out.println(DoubleMatrixCalc.print(DoubleMatrixCalc.multiplyMatrices(A, B)));
		Assertions.assertTrue(DoubleMatrixCalc.equals(AB, DoubleMatrixCalc.multiplyMatrices(A, B)));
		
		double[][] BA = new double[][] { { 7.0d, 29.0d }, { -12.0d, -7.0d } };
		Assertions.assertTrue(DoubleMatrixCalc.equals(BA, DoubleMatrixCalc.multiplyMatrices(B, A)));
		
		double[][] AB_minus_BA = new double[][] { { -2.0d, -2.0d }, { .0d, 2.0d } };
		Assertions.assertTrue(DoubleMatrixCalc.equals(AB_minus_BA, DoubleMatrixCalc.subtract(AB, BA)));
	}
	
	/**
	 * Даны две матрицы A и B, найти определитель матрицы 3*(B^2) - 2*A.
	 * Примеры приведены по книге А.С.Киркинский - "Линейная Алгебра и Аналитическая Геометрия" - 2006
	 * 
	 * @throws MatrixException в случае неправильных матриц
	 */
	@Test
	public void findDeterminansOfSubtractedMulitpliedByNumber() throws MatrixException {
		double[][] A = new double[][] { { -9.0d, -22.0d }, { 15.0d, -25.0d } };
		double[][] B = new double[][] { { 3.0d, -4.0d }, { 5.0d, 1.0d } };

		double[][] BB = new double[][] { { -11.0d, -16.0d }, { 20.0d, -19.0d } };
		Assertions.assertTrue(DoubleMatrixCalc.equals(BB, DoubleMatrixCalc.multiplyMatrices(B, B)));
		
		double[][] B_by_B_by_3_minus_A_by_2 = new double[][] { { -15.0d, -4.0d }, { 30.0d, -7.0d } };
		Assertions.assertTrue(DoubleMatrixCalc.equals(B_by_B_by_3_minus_A_by_2, DoubleMatrixCalc.subtract(
				DoubleMatrixCalc.multiplyMatrixByNumber(DoubleMatrixCalc.multiplyMatrices(B, B), 3.0d), 
				DoubleMatrixCalc.multiplyMatrixByNumber(A, 2.0d))));
//		System.out.println(DoubleMatrixCalc.print(B_by_B_by_3_minus_A_by_2));
		double det = 225;
		Assertions.assertEquals(det, DoubleMatrixCalc.det(B_by_B_by_3_minus_A_by_2));
		
	}
}
