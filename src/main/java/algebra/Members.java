/*
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025
 */

package algebra;

import series.SeriesPart;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * A series or a part of a series of terms of {@link Member} type.
 */
public class Members implements SeriesPart {

    private List<SeriesPart> members;

    private Members() {
        this.members = new ArrayList<>();
    }

    private Members(List<SeriesPart> m) {
        this.members = m;
    }

    public static Members of(List<SeriesPart> m) {
        return new Members(m);
    }

    @Override
    public int size() {
        return this.members.size();
    }

    @Override
    public void add(SeriesPart seriesPart) {
        if (seriesPart instanceof Member m)
            members.add(m);
        else throw new IllegalArgumentException("This class supports only Member.");
    }

    @Override
    public void remove(SeriesPart seriesPart) {
        if (seriesPart instanceof Member m)
            members.remove(m);
        else throw new IllegalArgumentException("This class supports only Member.");
    }

    @Override
    public SeriesPart getChild(int index) {
        if (index >= 0 && index < members.size()) {
            return members.get(index);
        }
        throw new IndexOutOfBoundsException("Invalid child index: " + index);
    }

    @Override
    public List<SeriesPart> getSeriesParts() {
        return this.members;
    }

    /**
     * Explode the tree into the list.
     *
     * @return the list of leaf members
     */
    public List<Member> asList() {
        return this.members.stream().flatMap(sp -> {
            if (sp instanceof Member m) return Stream.of(m);
            else if (sp instanceof Members ms) return ms.asList().stream();
            throw new IllegalArgumentException("This class supports only Member.");
        }).toList();
    }

    @Override
    public Members multiply(SeriesPart seriesPart) {
        return Members.of(getSeriesParts().stream()
                .flatMap(m -> m.multiply(seriesPart.copy()).getSeriesParts().stream())
                .toList());
    }

    @Override
    public SeriesPart copy() {
        return Members.of(members.stream().map(SeriesPart::copy).toList());
    }
}