/**
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025.
 */
package algebra;

import java.util.Objects;

/**
 * A member of a single variable equation. For example <i>2.5*(x^(1/9))</i>.
 *
 * @author Viacheslav Mikhailov
 */
public class Member implements Comparable<Member> {
    private final double power;
    private final double coefficient;

    private Member(final double p, final double c) {
        this.power = p;
        this.coefficient = c;
    }

    public double getPower() {
        return power;
    }

    public double getCoefficient() {
        return coefficient;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Double.compare(power, member.power) == 0
                && Double.compare(coefficient, member.coefficient) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(power, coefficient);
    }

    @Override
    public int compareTo(Member m) {
        if (Double.compare(this.power, m.power) > 0) return 1;
        if (Double.compare(this.power, m.power) < 0) return -1;
        return Integer.compare(Double.compare(this.coefficient, m.coefficient), 0);
    }

    /**
     * @return Builder
     */
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private double power;
        private double coefficient;

        /*
         * Create it with builder()
         */
        private Builder() {
            this.power = 0.0;
            this.coefficient = 0.0;
        }

        public Builder power(double power) {
            this.power = power;
            return this;
        }

        public Builder coefficient(double coefficient) {
            this.coefficient = coefficient;
            return this;
        }

        public Member build() {
            return new Member(this.power, this.coefficient);
        }
    }
}

