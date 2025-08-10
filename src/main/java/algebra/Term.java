/**
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025.
 */
package algebra;

import series.SeriesPart;

import java.util.List;
import java.util.Objects;

import static algebra.TermUtil.isConstant;

/**
 * Either a term of a series, or a term of an equation.
 * For example <i>2.5*(x^(1/9))</i>.
 *
 * @author Viacheslav Mikhailov
 */
public class Term implements Comparable<Term>, SeriesPart {
    private final Letter letter;
    private double power;
    private double coefficient;
    private Double value = null;

    private Term(final double pow, final double coeff, final Letter name, final Double val) {
        this.power = pow;
        this.coefficient = coeff;
        this.letter = Objects.requireNonNull(name);
        this.value = val;
    }

    public double getPower() {
        return power;
    }

    public double setPower(double power) {
        return this.power = power;
    }

    public double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }

    public Letter getLetter() {
        return letter;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Term term = (Term) o;
        return letter.equals(term.letter) && Double.compare(power, term.power) == 0
                && Double.compare(coefficient, term.coefficient) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(letter, power, coefficient);
    }

    @Override
    public int compareTo(Term m) {
        int result = -this.letter.compareTo(m.letter);
        if (result == 0) result = Double.compare(this.power, m.power);
        if (result == 0) result = Double.compare(this.coefficient, m.coefficient);
        return result;
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public SeriesPart add(SeriesPart seriesPart) {
        return switch (seriesPart) {
            case Term m -> TermUtil.sum(this, m);
            case Terms ms -> Terms.of(ms.getSeriesParts().stream().map(this::multiply).toList());
            default -> throw new IllegalArgumentException("Unsupported series part type " + seriesPart.getClass());
        };
    }

    @Override
    public void remove(SeriesPart seriesPart) {
        throw new UnsupportedOperationException();
    }

    @Override
    public SeriesPart getChild(int index) {
        throw new UnsupportedOperationException("A Term is a leaf in the graph");
    }

    @Override
    public List<SeriesPart> getSeriesParts() {
        return List.of(this);
    }

    @Override
    public SeriesPart multiply(SeriesPart seriesPart) {
        return switch (seriesPart) {
            case Term m -> TermUtil.multiply(this, m);
            case Terms ms -> Terms.of(ms.getSeriesParts().stream().map(this::multiply).toList());
            default -> throw new IllegalArgumentException("Unsupported series part type " + seriesPart.getClass());
        };
    }

    /**
     * Multiplies two Terms.
     *
     * @param factor the second Term
     * @return a new Term representing the product
     * @throws IllegalArgumentException if both are variable terms with different letters
     */
    public Term multiply(Term factor) {
        if (isConstant(this) && isConstant(factor))
            return Term.asRealConstant(this.getCoefficient() * factor.getCoefficient());
        else if (isConstant(this) && !isConstant(factor))
            return Term.builder()
                    .letter(factor.getLetter())
                    .power(factor.getPower())
                    .coefficient(this.getCoefficient() * factor.getCoefficient())
                    .build();
        else if (!isConstant(this) && isConstant(factor))
            return Term.builder()
                    .letter(this.getLetter())
                    .power(this.getPower())
                    .coefficient(this.getCoefficient() * factor.getCoefficient())
                    .build();
        else if (!isConstant(this) && !isConstant(factor) && this.getLetter().equals(factor.getLetter()))
            return Term.builder()
                    .letter(this.getLetter())
                    .power(this.getPower() + factor.getPower())
                    .coefficient(this.getCoefficient() * factor.getCoefficient())
                    .build();
        else throw new IllegalArgumentException("Cannot multiply terms with different letters");
    }

    /**
     * @return Builder
     */
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Letter letter;
        private double power;
        private double coefficient;
        private Double value;

        /*
         * Create it with builder()
         */
        private Builder() {
            this.power = 1.0;
            this.coefficient = 0.0;
            this.letter = Letter.of("x", 0);
            this.value = null;
        }

        public Builder power(double power) {
            this.power = power;
            return this;
        }

        public Builder coefficient(double coefficient) {
            this.coefficient = coefficient;
            return this;
        }

        public Builder letter(Letter letter) {
            this.letter = letter;
            return this;
        }

        public Builder letter(String letter) {
            this.letter = Letter.of(letter, 0);
            return this;
        }

        public Builder value(Double value) {
            this.value = value;
            return this;
        }

        public Term build() {
            return new Term(this.power, this.coefficient, this.letter, this.value);
        }
    }

    /**
     * Return a real constant {@link Term}.
     *
     * @param value the real value of the constant
     * @return a term of a real coefficient
     */
    public static Term asRealConstant(double value) {
        return Term.builder()
                .value(Double.NaN)
                .power(.0d)
                .letter(Letter.of("x", 0))
                .coefficient(value)
                .build();
    }

    /**
     * Return a {@link Term} of 'x' variable with power of 1 with the given coefficient.
     *
     * @param coefficient the given coefficient
     * @return the variable
     */
    public static Term asVariableX(double coefficient) {
        return Term.builder()
                .value(Double.NaN)
                .power(1.0d)
                .letter("x")
                .coefficient(coefficient)
                .build();
    }

    /**
     * Return a deep copy of this instance.
     *
     * @return the new Term instance
     */
    @Override
    public Term copy() {
        return new Term(this.power, this.coefficient, this.letter.copy(), Objects.isNull(this.value) ? null : this.value);
    }

    /**
     * Returns textual representation of this equation term.
     *
     * @return the textual representation of this term.
     */
    @Override
    public String toString() {
        if (this.power == .0d) {
            return String.valueOf(this.coefficient);
        }
        var sb = new StringBuilder()
                .append(this.coefficient)
                .append(this.letter);
        if (this.power != 1.0d) {
            sb.append("^").append(this.power);
        }
        return sb.toString();
    }
}
