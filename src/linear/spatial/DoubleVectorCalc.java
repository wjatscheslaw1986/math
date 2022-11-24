/*≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈
 ≈ Copyright © 2022. This code has an author and the author is me. 
 ≈ My name is Viacheslav Mikhailov. 
 ≈ You may contact me via EMail taleskeeper@yandex.ru
 ≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈≈*/

package spatial;

import spatial.exception.VectorException;

public class DoubleVectorCalc {

    /**
     * The method multiplies the given vector on a rational number
     * The method does not modify the original Vector object
     * 
     * @param vector - the original Vector object
     * @param number - the rational number to multiply the vector on
     * @return a new vector which is a result of the multiplication
     */
    public static Vector multiplyVectorOnNumber(Vector vector, double number) {
        double[] coordinates = vector.getCoordinates();
        double[] multipliedCoordinates = new double[coordinates.length];
        for (int i = 0; i < coordinates.length; i++) 
            multipliedCoordinates[i] = coordinates[i] * number;
        return new Vector(multipliedCoordinates);
    }

    /**
     * The method sums up two vectors of same spatial base (same amount of coordinate axes).
     * The method does not modify the summands
     *
     * @param vector1 - the first addendum object
     * @param vector2 - the second addendum object
     * @return a new Vector object which is a product of the summation
     * @throws VectorException - if vectors are from different bases
     */
    public static Vector summarizeVectors(Vector vector1, Vector vector2) throws VectorException {
        double[] coordinates1 = vector1.getCoordinates();
        double[] coordinates2 = vector2.getCoordinates();
        if (coordinates1.length != coordinates2.length) throw new VectorException("In order to sum both vectors must be of a same base (same number of coordinates)");
        double[] summarizeCoordinates = new double[coordinates1.length];
        for (int i = 0; i < coordinates1.length; i++)
            summarizeCoordinates[i] = coordinates1[i]+coordinates2[i];
        return new Vector(summarizeCoordinates);
    }
}