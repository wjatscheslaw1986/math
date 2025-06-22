/**
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025.
 */
package algebra;

import java.util.Objects;

/**
 * A member of an equation. For example <i>2.5*(x^(1/9))</i>.
 *
 * @author Viacheslav Mikhailov
 */
public class Member implements Comparable<Member> {
    private final Letter letter;
    private final double power;
    private double coefficient;
    private Double value = null;

    private Member(final double pow, final double coeff, final Letter name, final Double val) {
        this.power = pow;
        this.coefficient = coeff;
        this.letter = name;
        this.value = val;
    }

    public double getPower() {
        return power;
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
        Member member = (Member) o;
        return letter.equals(member.letter) && Double.compare(power, member.power) == 0
                && Double.compare(coefficient, member.coefficient) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(letter, power, coefficient);
    }

    @Override
    public int compareTo(Member m) {
        int result = -this.letter.compareTo(m.letter);
        if (result == 0) result = Double.compare(this.power, m.power);
        if (result == 0) result = Double.compare(this.coefficient, m.coefficient);
        return result;
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

        public Member build() {
            return new Member(this.power, this.coefficient, this.letter, this.value);
        }
    }

    /**
     * Return a deep copy of this instance.
     *
     * @return the new Member instance
     */
    public Member copy() {
        return new Member(this.power, this.coefficient, this.letter, this.value);
    }

    /**
     * Returns textual representation of this equation member.
     *
     * @return the textual representation of this member.
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
