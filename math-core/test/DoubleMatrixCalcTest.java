/**
 * Wjatscheslaw Michailov <taleskeeper@yandex.com> All rights reserved © 2025.
 */
package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import linear.matrix.DoubleMatrixCalc;
import linear.matrix.exception.MatrixException;

public class DoubleMatrixCalcTest {

	@BeforeAll
	static void before() {
		System.out.printf("Running tests in %s.class", DoubleMatrixCalcTest.class);
	}

	@Test
	public void equals() {
		double[][] a1 = new double[][] { { 2.0d, 1.0d, 3.0d }, { 1.0d, -1.0d, 7.0d }, { 0.0d, 2.0d, -7.0d } };
		double[][] b1 = new double[][] { { 2.0f, 1.0f, 3.0f }, { 1.0f, -1.0f, 7.0f }, { 0.0f, 2.0f, -7.0f } };
		Assertions.assertTrue(DoubleMatrixCalc.equals(a1, b1));
		a1 = new double[][] { { 2.0d, 1.0d }, { 2.0d, -7.0d } };
		b1 = new double[][] { { 2, 1 }, { 2, -7 } };
		Assertions.assertTrue(DoubleMatrixCalc.equals(a1, b1));
		a1 = new double[][] { { 2.0d, 1.0d }, { 2.0d, -7.0d } };
		b1 = new double[][] { { 2, 1 }, { 3, -7 } };
		Assertions.assertFalse(DoubleMatrixCalc.equals(a1, b1));
		a1 = new double[][] { { 2.0d, -1.0d }, { 2.0d, -7.0d } };
		b1 = new double[][] { { 2, 1 }, { 2, -7 } };
		Assertions.assertFalse(DoubleMatrixCalc.equals(a1, b1));
		a1 = new double[][] { { 2.0d, 1.0d }, { 2.0d, -7.0d } };
		b1 = new double[][] { { -2, 1 }, { 2, -7 } };
		Assertions.assertFalse(DoubleMatrixCalc.equals(a1, b1));
		a1 = new double[][] { { 2.0d, 1.0d }, { 2.0d, 7.0d } };
		b1 = new double[][] { { 2, 1 }, { 2, -7 } };
		Assertions.assertFalse(DoubleMatrixCalc.equals(a1, b1));
		a1 = new double[][] { { 2.0d, 3.0d }, { 2.0d, -7.0d } };
		b1 = new double[][] { { 2, 1 }, { 2, -7 } };
		Assertions.assertFalse(DoubleMatrixCalc.equals(a1, b1));
		a1 = new double[][] { { 2.1d, 1.0d }, { 2.0d, -7.0d } };
		b1 = new double[][] { { 2, 1 }, { 2, -7 } };
		Assertions.assertFalse(DoubleMatrixCalc.equals(a1, b1));
	}

	@Test
	public void equalDimensions() {
		double[][] a1 = new double[][] { { 2.0d, 1.0d, 3.0d }, { 1.0d, -1.0d, 7.0d }, { 0.0d, 2.0d, -7.0d } };
		double[][] b1 = new double[][] { { 2.0f, 1.0f, 3.0f }, { 1.0f, -1.0f, 7.0f }, { 0.0f, 2.0f, -7.0f } };
		Assertions.assertTrue(DoubleMatrixCalc.equalDimensions(a1, b1));
		a1 = new double[][] { { 2.0d, 1.0d, 3.0d }, { 1.0d, -1.0d, 7.0d }, { 0.0d, 2.0d, -7.0d } };
		b1 = new double[][] { { 2.0f, 1.0f }, { 1.0f, -1.0f }, { 0.0f, 2.0f } };
		Assertions.assertFalse(DoubleMatrixCalc.equalDimensions(a1, b1));
		a1 = new double[][] { { 2.0d, 1.0d }, { -1.0d, 7.0d }, { 0.0d, -7.0d } };
		b1 = new double[][] { { 2.0f, 1.0f, 3.0f }, { 1.0f, -1.0f, 7.0f }, { 0.0f, 2.0f, -7.0f } };
		Assertions.assertFalse(DoubleMatrixCalc.equalDimensions(a1, b1));
		a1 = new double[][] { { 2.0d, 1.0d, 3.0d }, { 1.0d, -1.0d, 7.0d } };
		b1 = new double[][] { { 2.0f, 1.0f, 3.0f }, { 1.0f, -1.0f, 7.0f }, { 0.0f, 2.0f, -7.0f } };
		Assertions.assertFalse(DoubleMatrixCalc.equalDimensions(a1, b1));
		a1 = new double[][] { { 2.0d, 1.0d, 3.0d }, { 1.0d, -1.0d, 7.0d }, { 0.0f, 2.0f, -7.0f } };
		b1 = new double[][] { { 2.0f, 1.0f, 3.0f }, { 1.0f, -1.0f, 7.0f } };
		Assertions.assertFalse(DoubleMatrixCalc.equalDimensions(a1, b1));
	}

	/**
	 * Даны две матрицы A и B, найти произведение этих матриц.
	 * 
	 * Примеры приведены по книге А.С.Киркинский - "Линейная Алгебра и Аналитическая
	 * Геометрия" - 2006
	 * 
	 * @throws MatrixException в случае неправильных матриц
	 */
	@Test
	public void multiplyMatrices() throws MatrixException {
		final double[][] A = new double[][] { { 1.0d, 3.0d, -1.0d }, { 0.0d, 5.0d, 2.0d }, { 3.0d, -2.0d, 4.0d } };
		final double[][] B = new double[][] { { 1.0f, 2.0f, 0.0f }, { 3.0f, 1.0f, 2.0f }, { -4.0f, 1.0f, 5.0f } };
		final double[][] result = new double[][] { { 14, 4, 1 }, { 7, 7, 20 }, { -19, 8, 16 } };
		Assertions.assertDoesNotThrow(() -> DoubleMatrixCalc.multiplyMatrices(A, B));
		Assertions.assertTrue(DoubleMatrixCalc.equals(result, DoubleMatrixCalc.multiplyMatrices(A, B)));

		/*
		 * Перемножение матриц требует, чтобы количество колонок матрицы слева равнялось
		 * количеству строк матрицы справа.
		 */
		final double[][] b2 = new double[][] { { 1.0f, 2.0f, 0.0f }, { 3.0f, 1.0f, 2.0f } };
		Assertions.assertThrows(MatrixException.class, () -> DoubleMatrixCalc.multiplyMatrices(A, b2));
	}

	/**
	 * Даны матрицы, найти обратные для каждой из них.
	 * 
	 * Примеры приведены по книге А.С.Киркинский - "Линейная Алгебра и Аналитическая
	 * Геометрия" - 2006
	 * 
	 * @throws MatrixException в случае неправильных матриц
	 */
	@Test
	public void reverse() throws MatrixException {
		final double[][] a = new double[][] { { 6.0d, 4.0d }, { -2.0d, -1.0d } };
		final double[][] b = new double[][] { { 3.0f, 4.0f }, { 9.0f, 12.0f } };
		final double[][] c = new double[][] { { 2.0d, 0.0d, 2.0d }, { -3.0d, 2.0d, 0.0d }, { 6.0d, -2.0d, 4.0d } };
		
		final double[][] a_rev = new double[][] { { -.5d, -2.0d }, { 1.0d, 3.0d } };
		final double[][] c_rev = new double[][] { { 2.0d, -1.0d, -1.0d }, { 3.0d, -1.0d, -1.5d }, { -1.5d, 1.0d, 1.0d } };
		
		Assertions.assertDoesNotThrow(() -> DoubleMatrixCalc.reverse(a));
		Assertions.assertTrue(DoubleMatrixCalc.equals(DoubleMatrixCalc.reverse(a), a_rev));
		
		/*
		 * У матрицы с определителем == 0 не может быть обратной.
		 */
		Assertions.assertThrows(MatrixException.class, () -> DoubleMatrixCalc.reverse(b));
		
		Assertions.assertDoesNotThrow(() -> DoubleMatrixCalc.reverse(c));
		Assertions.assertTrue(DoubleMatrixCalc.equals(DoubleMatrixCalc.reverse(c), c_rev));
		
		
		
//		try {
//			DoubleMatrixCalc.reverse(b);
//		} catch(MatrixException e) {
//			System.err.println(e.getLocalizedMessage());
//		}
		
	}

}
