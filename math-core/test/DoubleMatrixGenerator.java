/**
 * Wjatscheslaw Michailov (taleskeeper@yandex.com) All rights reserved © 2025.
 */
package test;

import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;

/**
 * @author vaclav
 */
public class DoubleMatrixGenerator {
	
	/**
	 * Сгенерировать псевдослучайную матрицу n * m.
	 */
	public static double[][] generateRandomMatrix(final int n, final int m) {
		final double[][] result = new double[n][m];
		final RandomGenerator rnd = RandomGeneratorFactory.getDefault().create();
		for (int i = 0; i < n; i++)
			for (int j = 0; j < m; j++)
				result[i][j] = rnd.nextDouble(-10000.0d, 10000.0d);
		return result;		
	}
}
