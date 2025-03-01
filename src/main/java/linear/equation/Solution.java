/**
 * Wjatscheslaw Michailov (taleskeeper@yandex.com) All rights reserved © 2025.
 */
package linear.equation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A class that represents a solution of a linear equation system.
 *
 * @param solutionsCount enum flag denoting amount of solutions for the linear equation system.
 * @param solution a zero solution which is either a single solution for the case of {@linkplain SolutionsCount.SINGLE},
 *                 or a solution with all variable members are zeroes for the case of {@linkplain SolutionsCount.INFINITE},
 *                 or {@code null} for the case of {@linkplain SolutionsCount.ZERO}
 * @param addresses an array of index addresses of variable members in equations
 * @author Wjatscheslaw Michailov
 */
public record Solution(SolutionsCount solutionsCount, double[] solution, int[] addresses) {

    /**
     * Returns a basis in linear space of solutions for current linear equation system.
     * The basis (as well as the space) exists only in case when rank of the main matrix (i.e. matrix made of left
     * sides of the equations) is strictly less than the number of variables in the equations.
     *
     * @return fundamental solution system for the linear equation system
     */
    public List<double[]> basis() {
        if (Objects.requireNonNull(solutionsCount) == SolutionsCount.INFINITE) {
            var basis = new ArrayList<double[]>();
            for (int i = 0; i < solution.length; i++) {
                if (addresses[i] == -1) {
                    var s = new double[solution.length];
                    System.arraycopy(solution, 0, s, 0, solution.length);
                    s[i] = 1;
                    basis.add(s);
                }
            }
            return basis;
        }
        return List.of();
    }
}
