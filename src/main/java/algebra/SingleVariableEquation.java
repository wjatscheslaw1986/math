/**
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025.
 */
package algebra;

import java.util.List;

/**
 * An equation with single variable with 0 (zero) in its right part (i.e. after the '=').
 *
 * @author Viacheslav Mikhailov
 */
public record SingleVariableEquation(List<Member> members){
}
