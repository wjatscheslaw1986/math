package app;

import characters.Captain;
import characterz.Freaker;
import matrix.DoubleMatrixCalc;

public class App {
    public static void main(String... argz) {       
        
        double[][] matrix1 = {{ 2.0d, -3.0d, 2.0d, 1.0d },{ 3.0d, -1.0d, 1.0d, 4.0d },{ 1.0d, 2.0d, -1.0d, 3.0d }};
        System.out.println("");
        System.out.println("MATRIX");
        System.out.print(DoubleMatrixCalc.print(matrix1));
        System.out.println("RANK ");
        System.out.print(DoubleMatrixCalc.rank(matrix1));
        System.out.println("");
    }
}