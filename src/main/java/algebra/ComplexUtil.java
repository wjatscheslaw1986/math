/*
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2025
 */

package algebra;

import java.util.ArrayList;
import java.util.List;

/**
 * Complex number utilities class.
 *
 * @author Viacheslav Mikhailov
 */
public final class ComplexUtil {

    private ComplexUtil() {
        // static context only
    }

    public static Complex add(Complex a, Complex b) {
        return Complex.of(a.real() + b.real(), a.imaginary() + b.imaginary());
    }

    public static Complex subtract(Complex a, Complex b) {
        return Complex.of(a.real() - b.real(), a.imaginary() - b.imaginary());
    }

    public static Complex multiply(Complex a, Complex b) {
        double re = a.real() * b.real() - a.imaginary() * b.imaginary();
        double im = a.real() * b.imaginary() + a.imaginary() * b.real();
        return Complex.of(re, im);
    }

    public static Complex divide(Complex a, Complex b) {
        double denom = b.real() * b.real() + b.imaginary() * b.imaginary();
        if (denom == 0) throw new ArithmeticException("Division by zero");
        double re = (a.real() * b.real() + a.imaginary() * b.imaginary()) / denom;
        double im = (a.imaginary() * b.real() - a.real() * b.imaginary()) / denom;
        return Complex.of(re, im);
    }

    public static Complex sqrt(Complex z) {
        double r = Math.sqrt(z.real() * z.real() + z.imaginary() * z.imaginary());
        double re = Math.sqrt((r + z.real()) / 2);
        double im = Math.signum(z.imaginary()) * Math.sqrt((r - z.real()) / 2);
        return Complex.of(re, im);
    }

    public static List<Complex> cubeRoots(Complex z) {
        double r = Math.sqrt(z.real() * z.real() + z.imaginary() * z.imaginary());
        double theta = Math.atan2(z.imaginary(), z.real());
        double cr = Math.cbrt(r);
        List<Complex> roots = new ArrayList<>();
        for (int k = 0; k < 3; k++) {
            double angle = (theta + 2 * Math.PI * k) / 3;
            double re = cr * Math.cos(angle);
            double im = cr * Math.sin(angle);
            roots.add(Complex.of(re, im));
        }
        return roots;
    }

    public static Complex negate(Complex a) {
        return Complex.of(-a.real(), -a.imaginary());
    }
}
