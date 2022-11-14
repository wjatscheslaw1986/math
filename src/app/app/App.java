package app;

import characters.Captain;
import characterz.Freaker;
import matrix.IntMatrixCalc;

public class App {
    public static void main(String... argz) {
        Captain cap = new Captain();
        Freaker freak = new Freaker();
        cap.payRespects();
        freak.freakOut();
        
        int[][] matrix = {{5, 1, 5},{2, 3, 4},{6, 4, 21}};
        int[][] result = IntMatrixCalc.multiplyOnInt(matrix, 2);
        
        System.out.print(IntMatrixCalc.print(matrix));
        
    }
}