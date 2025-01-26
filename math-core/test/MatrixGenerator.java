/**
 * Wjatscheslaw Michailov (taleskeeper@yandex.com) All rights reserved © 2025.
 */
package test;

import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;

/**
 * @author vaclav
 */
public class MatrixGenerator {
	
	/**
	 * Сгенерировать псевдослучайную матрицу n * m действительных чисел.
	 * 
	 * @return псевдослучайная матрица действительных чисел
	 */
	public static double[][] generateRandomDoubleMatrix(final int n, final int m) {
		final double[][] result = new double[n][m];
		final RandomGenerator rnd = RandomGeneratorFactory.getDefault().create();
		for (int i = 0; i < n; i++)
			for (int j = 0; j < m; j++)
				result[i][j] = rnd.nextDouble(-10.0d, 10.0d);
		return result;		
	}
	
	/**
     * Сгенерировать псевдослучайную матрицу n * m целых чисел.
     * 
     * @return псевдослучайная матрица целых чисел
     */
    public static int[][] generateRandomIntegerMatrix(final int n, final int m) {
        final int[][] result = new int[n][m];
        final RandomGenerator rnd = RandomGeneratorFactory.getDefault().create();
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                result[i][j] = rnd.nextInt(-10000, 10000);
        return result;      
    }
}
