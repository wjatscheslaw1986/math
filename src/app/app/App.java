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
        
        //int[][] matrix = {{5, 1, 5},{2, 3, 4},{6, 4, 21}};
        //int[][] result = IntMatrixCalc.multiplyOnInt(matrix, 2);
        //System.out.print(IntMatrixCalc.print(matrix));
    
        int[][] matrix0 = {{ 1, 2, 3 },{ 4, -3, 1 }}, matrix1 = {{ 0, 5, 1 },{ -4, 7, 4 }};
        System.out.print(IntMatrixCalc.print(IntMatrixCalc.sumMatrices(matrix0, matrix1)));
        System.out.println("");
        int[][] matrix2 = {{ 4, 7 },{ 1, 8 }}, matrix3 = {{ -2, 3  },{ 4, -1 }};
        System.out.print(IntMatrixCalc.print(IntMatrixCalc.multiplyMatrices(matrix2, matrix3)));
        System.out.println("");
        int[][] matrix4 = {{ -2, 3 },{ 4, -1 }}, matrix5 = {{ 4, 7  },{ 1, 8 }};
        System.out.print(IntMatrixCalc.print(IntMatrixCalc.multiplyMatrices(matrix4, matrix5)));
        System.out.println("");
        int[][] matrix6 = {{ 2, 3, 4 },{ -1, 2, 3 }}, matrix7 = {{ -2, 0  },{ 3, 1 },{ 1, 5 }};
        System.out.print(IntMatrixCalc.print(IntMatrixCalc.multiplyMatrices(matrix6, matrix7)));
        System.out.println("");
        int[][] matrix8 = {{ 4, 5, 1 },{ 7, 1, 3 }}, matrix9 = {{ 2, 4  },{ 5, 1 }};
        System.out.print(IntMatrixCalc.print(IntMatrixCalc.multiplyMatrices(matrix9, matrix8)));
        System.out.println("");
        // System.out.println(IntMatrixCalc.canMultiply(matrix7, matrix6));
//         System.out.println(IntMatrixCalc.canMultiply(matrix6, matrix7));
//         System.out.println(matrix6.length);
//         System.out.println(matrix6[0].length);
//         System.out.println(matrix7.length);
//         System.out.println(matrix7[0].length);
        
        //System.out.println(IntMatrixCalc.isEmpty(new int[][]{}));
        //System.out.println(IntMatrixCalc.isEmpty(new int[][]{{}}));
        //System.out.println(IntMatrixCalc.isEmpty(new int[][]{{},{}}));
        //System.out.println(IntMatrixCalc.isEmpty(new int[][]{{},{4}}));
        //System.out.println(IntMatrixCalc.isEmpty(new int[][]{{2},{4}}));
    }
}