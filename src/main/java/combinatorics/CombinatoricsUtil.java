package combinatorics;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * A utility class for {@linkplain combinatorics} package.
 *
 * @author Wjatscheslaw Michailov
 */
public final class CombinatoricsUtil {

    private CombinatoricsUtil() {
        // static context only
    }

    /**
     * A function to print combination to {@linkplain OutputStream}.
     *
     * @param o the output stream
     * @return the printing function
     */
    public static BiConsumer<List<int[]>, int[]> printCombinationFunction(final OutputStream o) {
        return (final List<int[]> l, final int[] currentPermutation) -> {
            try {
                o.write(Arrays.toString(currentPermutation).getBytes());
                o.write(System.lineSeparator().getBytes());
            } catch (IOException e) {
                System.err.print(e.getLocalizedMessage());
            }
        };
    }

    /**
     * A function to for collecting combinations into a {@linkplain List}
     *
     * @param resultList the list for storing combinations
     * @return the storing function
     */
    public static BiConsumer<int[], Integer> collectToListFunction(final List<int[]> resultList) {
        return (final int[] currentCombination, final Integer toIndex) ->
                resultList.add(cutFrom(currentCombination, toIndex));
    }

    /**
     * Cuts off all the elements from the given array starting with the
     * given <i>index</i>, inclusive.
     *
     * @param array given
     * @param index the index since which we throw away elements from the given array
     * @return the shortened array
     */
    public static int[] cutFrom(final int[] array, final int index) {
        final int[] subArray = new int[index];
        System.arraycopy(array, 0, subArray, 0, index);
        return subArray;
    }
}
