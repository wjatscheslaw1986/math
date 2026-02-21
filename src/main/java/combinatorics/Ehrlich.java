/**
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025.
 */
package combinatorics;

import linear.spatial.VectorUtil;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static combinatorics.CombinatoricsUtil.generateArrayOfIndicesOfSize;
import static combinatorics.CombinatoricsUtil.getPrintArrayFunction;

/**
 * A utility class for generating all possible permutations without repetitions
 * of indices for a given generic array, using Gideon Ehrlich's algorithm.
 * 
 * @author Viacheslav Mikhailov
 */
public final class Ehrlich {

    private Ehrlich() {
        // static context only
    }

    /**
     * Generate all the possible permutations for the given size of a sequence of indices, starting at 0, all at once.
     *
     * @param cardinality the size given
     * @return all the permutations for the given size of a sequence of indices
     */
    public static List<int[]> generate(final int cardinality) {
        final var permutations = new ArrayList<int[]>();
        var generator = new EhrlichPermutationsGenerator(cardinality);
        while (generator.hasNext()) {
            permutations.add(generator.next());
        }
        return permutations;
    }

    /**
     * Generate first <i>n</i> permutations for the given size of a sequence of indices,
     * starting at 0, all at once.
     *
     * @param cardinality the given size of the sequence of indices
     * @param n the number of the first permutations to return
     * @return n permutations for the given size of a sequence of indices
     */
    public static List<int[]> generate(final int cardinality, final int n) {
        final var permutations = new ArrayList<int[]>();
        final var generator = new EhrlichPermutationsGenerator(cardinality);
        while (generator.hasNext() &&  permutations.size() < n) {
            permutations.add(generator.next());
        }
        return permutations;
    }

    public static int count(int n) {
        return CombinatoricsCalc.countPermutationsNoRepetitions(n);
    }
}
