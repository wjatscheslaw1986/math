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
        int[][] result = IntMatrixCalc.multiplyMatrixOnInt(matrix, 2);
        System.out.print(IntMatrixCalc.print(matrix));
        System.out.println("");
        System.out.print(IntMatrixCalc.print(result));
        System.out.println("");
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
        
        int[][] matrix10 = {{ 3, 2 },{ 1, 4 }};
        System.out.print(IntMatrixCalc.print(IntMatrixCalc.transpondMatrix(matrix10)));
        int[][] matrix11 = {{ 1, 3, -5 }};
        System.out.print(IntMatrixCalc.print(IntMatrixCalc.transpondMatrix(matrix11)));
        
        int[][] matrix12 = {{ 7, 2 },{ 5, 3 }};
        System.out.print(IntMatrixCalc.det(matrix12));
        System.out.println("");
        
        int[][] matrix13 = {{ 1, 3, -2},{ 4, 2, 5 },{ -3, 1, 7}};
        System.out.print(IntMatrixCalc.det(matrix13));
        System.out.println("");
        
        int[][] matrix14 = {{ 2, -4, 3, 1 },{ 5, -1, 0, 4 },{ 2, 3, 0, 1 },{ -3, 4, 2, 0 }};
        System.out.print(IntMatrixCalc.det(matrix14));
        System.out.println("");
        
        int[][] matrix15 = {{ 8, 5, 2, -4 },{ 2, 7, 0, 1 },{ 4, 4, 1, -2 },{ 3, -2, 5, 1 }};
        System.out.print(IntMatrixCalc.det(matrix15));
        System.out.println("");
        
        int[][] matrix16 = {{ -9, -22 },{ 15, -25 }}, matrix17 = {{ 3, -4 },{ 5, 1 }};
        System.out.print(IntMatrixCalc.print(
            IntMatrixCalc.sumMatrices(
                IntMatrixCalc.multiplyMatrixOnInt(IntMatrixCalc.multiplyMatrices(matrix17, matrix17), 3),
                IntMatrixCalc.multiplyMatrixOnInt(matrix16, -2)
        ))
        );
        System.out.print(
            IntMatrixCalc.det(IntMatrixCalc.sumMatrices(
                IntMatrixCalc.multiplyMatrixOnInt(IntMatrixCalc.multiplyMatrices(matrix17, matrix17), 3),
                IntMatrixCalc.multiplyMatrixOnInt(matrix16, -2)
        )));
        System.out.println("");
        
        int[][] matrix18 = {{ 3, -4 },{ 2, 5 }}, matrix19 = {{ -1, 5 },{ -2, -3 }};
        System.out.print(IntMatrixCalc.print(IntMatrixCalc.sumMatrices(
            IntMatrixCalc.multiplyMatrices(matrix18, matrix19),
            IntMatrixCalc.multiplyMatrixOnInt(IntMatrixCalc.multiplyMatrices(matrix19, matrix18), -1)
        )));
        System.out.println("");
        
        int[][] matrix20 = {{ 1, 3, -1 },{ 0, 5, 2 },{ 3, -2, 4 }}, matrix21 = {{ 1, 2, 0 },{ 3, 1, 2 },{ -4, 1, 5 }};
        System.out.print(IntMatrixCalc.print(IntMatrixCalc.multiplyMatrices(matrix20, matrix21)));
        System.out.println("");
        
        int[][] matrix22 = {{ 2, 0, 2 },{ -3, 2, 0 },{ 6, -2, 4 }};
        System.out.println("`det:` " + IntMatrixCalc.det(matrix22));
        System.out.println("");
        System.out.println("cofactor A11 : " + IntMatrixCalc.cofactor(matrix22, 1, 1));
        System.out.println("");
        System.out.println("cofactors matrix : \n" + IntMatrixCalc.print(IntMatrixCalc.cofactorsMatrix(matrix22)));
        System.out.println("");
        System.out.println("" + IntMatrixCalc.print(IntMatrixCalc.transpondMatrix(IntMatrixCalc.cofactorsMatrix(matrix22))));
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println(IntMatrixCalc.print(IntMatrixCalc.reverse(matrix22)));
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