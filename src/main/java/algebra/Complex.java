/*
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025
 */

package algebra;

/**
 * A complex number. In case if imaginary part is NaN, then a real number.
 *
 * @author Viacheslav Mikhailov
 */
public record Complex(Double real, Double imaginary) {

    /**
     * Static factory method.
     *
     * @param real the real part
     * @param imaginary the imaginary part
     * @return the complex number
     */
    public static Complex of(Double real, Double imaginary) {
        return new Complex(real, imaginary);
    }

    @Override
    public String toString() {
        if (imaginary.isNaN()) return real.toString();
        return imaginary >= 0 ? real + " + " + imaginary + "i" : real + " - " + Math.abs(imaginary) + "i";
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Complex)) return false;
        Complex c = (Complex) o;
        return Double.compare(real, c.real) == 0 && Double.compare(imaginary, c.imaginary) == 0;
    }
}
