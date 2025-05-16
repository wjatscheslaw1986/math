/**
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025.
 */
package linear;

import linear.matrix.MatrixUtil;

import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;

/**
 * @author vaclav
 */
public class MatrixGenerator {
	
	/**
	 * Сгенерировать псевдослучайную матрицу n * m действительных чисел.
	 *
	 * @param n количество рядов
	 * @param m количество столбцов
	 * @return псевдослучайная матрица действительных чисел
	 */
	public static double[][] generateRandomDoubleMatrix(final int n, final int m) {
		final double[][] result = new double[n][m];
		final RandomGenerator rnd = RandomGeneratorFactory.getDefault().create();
		for (int i = 0; i < n; i++)
			for (int j = 0; j < m; j++)
				result[i][j] = rnd.nextDouble(-10.0d, 10.0d);
		MatrixUtil.eliminateEpsilon(result);
		MatrixUtil.roundValues(2, result);
		return result;		
	}

	/**
	 * Сгенерировать псевдослучайную матрицу n * m целых чисел, типа чисел с плавающей точкой.
	 *
	 * @param n количество рядов
	 * @param m количество столбцов
	 * @return псевдослучайная матрица действительных чисел, равных ближайшим целым
	 */
	public static double[][] generateRandomIntMatrix(final int n, final int m) {
		final double[][] result = new double[n][m];
		final RandomGenerator rnd = RandomGeneratorFactory.getDefault().create();
		for (int i = 0; i < n; i++)
			for (int j = 0; j < m; j++)
				result[i][j] = rnd.nextInt(-10, 10);
		return result;
	}
}
