/**
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025.
 */
package linear.equation;

import java.util.List;

/**
 * A class that represents a solution of a linear equation system.
 *
 * @param solutionsCount enum flag denoting amount of solutions for the linear equation system.
 * @param solution a zero solution which is either a single solution for the case of {@linkplain SolutionsCount.SINGLE},
 *                 or a solution with all variable terms are zeroes for the case of {@linkplain SolutionsCount.INFINITE},
 *                 or {@code null} for the case of {@linkplain SolutionsCount.ZERO}
 * @param basis a basis on linear space of solutions for current linear equation system
 *                  or {@code null} for the cases of {@linkplain SolutionsCount.ZERO} and {@linkplain SolutionsCount.SINGLE}
 * @author Viacheslav Mikhailov
 */
public record Solution(SolutionsCount solutionsCount, double[] solution, List<double[]> basis){}
