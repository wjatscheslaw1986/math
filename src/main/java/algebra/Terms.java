/*
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025
 */

package algebra;

import series.SeriesPart;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * A series or a part of a series of terms of {@link Term} type.
 */
public class Terms implements SeriesPart {

    private List<SeriesPart> terms;

    private Terms() {
        this.terms = new ArrayList<>();
    }

    private Terms(List<SeriesPart> m) {
        this.terms = m;
    }

    public static Terms of(List<SeriesPart> m) {
        return new Terms(m);
    }

    @Override
    public int size() {
        return this.terms.size();
    }

    @Override
    public SeriesPart add(SeriesPart seriesPart) {
        return switch (seriesPart) {
            case Term m -> {
                this.terms.add(m);
                yield this;
            }
            case Terms ms -> {
                this.terms.addAll(ms.getSeriesParts());
                yield this;
            }
            default -> throw new IllegalArgumentException("Unsupported series part type " + seriesPart.getClass());
        };
    }

    @Override
    public void remove(SeriesPart seriesPart) {
        if (seriesPart instanceof Term m)
            terms.remove(m);
        else throw new IllegalArgumentException("This class supports only Term.");
    }

    @Override
    public SeriesPart getChild(int index) {
        if (index >= 0 && index < terms.size()) {
            return terms.get(index);
        }
        throw new IndexOutOfBoundsException("Invalid child index: " + index);
    }

    @Override
    public List<SeriesPart> getSeriesParts() {
        return this.terms;
    }

    /**
     * Explode the tree into the list.
     *
     * @return the list of leaf terms
     */
    public List<Term> asList() {
        return this.terms.stream().flatMap(sp -> {
            if (sp instanceof Term m) return Stream.of(m);
            else if (sp instanceof Terms ms) return ms.asList().stream();
            throw new IllegalArgumentException("This class supports only Term.");
        }).toList();
    }

    @Override
    public Terms multiply(SeriesPart seriesPart) {
        return Terms.of(getSeriesParts().stream()
                .flatMap(m -> m.multiply(seriesPart.copy()).getSeriesParts().stream())
                .toList());
    }

    @Override
    public SeriesPart copy() {
        return Terms.of(terms.stream().map(SeriesPart::copy).toList());
    }
}