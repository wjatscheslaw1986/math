/**
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025.
 */
package algebra;

import java.util.List;

/**
 * An equation with single variable.
 *
 * @author Viacheslav Mikhailov
 */
public record Equation(List<Member> members, double equalsTo) {}
