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
    private final String letter;
    private final double power;
    private final double coefficient;

    private Member(final double p, final double c, final String l) {
        this.power = p;
        this.coefficient = c;
        this.letter = l;
    }

    public double getPower() {
        return power;
    }

    public double getCoefficient() {
        return coefficient;
    }

    public String getLetter() {
        return letter;
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
        if (this.letter.compareTo(m.letter) != 0) return -this.letter.compareTo(m.letter);
        if (Double.compare(this.power, m.power) != 0) return Double.compare(this.power, m.power);
        return Double.compare(this.coefficient, m.coefficient);
    }

    /**
     * @return Builder
     */
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String letter;
        private double power;
        private double coefficient;

        /*
         * Create it with builder()
         */
        private Builder() {
            this.power = 0.0;
            this.coefficient = 0.0;
            this.letter = "x";
        }

        public Builder power(double power) {
            this.power = power;
            return this;
        }

        public Builder coefficient(double coefficient) {
            this.coefficient = coefficient;
            return this;
        }

        public Builder letter(String letter) {
            this.letter = letter;
            return this;
        }

        public Member build() {
            return new Member(this.power, this.coefficient, this.letter);
        }
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
