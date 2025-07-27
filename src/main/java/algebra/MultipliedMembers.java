/*
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025
 */

package algebra;

import java.util.List;

/**
 * Class representing multiplication of the members in the list.
 *
 * @param members the multiplied members
 * @param isNumenator true if the result of the multiplication is a numenator, false if denominator (i.e. power -1).
 */
public record MultipliedMembers(List<Member> members, boolean isNumenator) {

    /**
     * Factory method.
     *
     * @param members
     * @param isNumenator
     * @return the multiplication of the members
     */
    public static MultipliedMembers of(List<Member> members, boolean isNumenator) {
        return new MultipliedMembers(members, isNumenator);
    }

    /**
     * Factory method.
     *
     * @param members
     * @return the multiplication of the members
     */
    public static MultipliedMembers of(List<Member> members) {
        return new MultipliedMembers(members, true);
    }
}
