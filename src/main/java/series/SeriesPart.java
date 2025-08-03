/*
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025
 */
package series;

import java.util.List;

/**
 * The common compositor contract for both leaf and subtree implementations.
 * The leaf is a term of a series, and the subtree is a subseries.
 *
 * @author Viacheslav Mikhailov
 */
public interface SeriesPart {
    int size();
    SeriesPart add(SeriesPart seriesPart);
    void remove(SeriesPart seriesPart);
    SeriesPart getChild(int index);
    List<SeriesPart> getSeriesParts();
    SeriesPart multiply(SeriesPart seriesPart);
    SeriesPart copy();
}
