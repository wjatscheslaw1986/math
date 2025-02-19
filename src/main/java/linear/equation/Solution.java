/**
 * Wjatscheslaw Michailov (taleskeeper@yandex.com) All rights reserved © 2025.
 */
package linear.equation;

import java.util.List;

/**
 * A DTO class that represents solution of a linear equation system.
 *
 * @author Wjatscheslaw Michailov
 */
public record Solution(SolutionsCount solutionsCount, List<double[]> vectors) { }
