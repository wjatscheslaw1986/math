package matrix;

import java.math.BigDecimal;

public class DoubleMatrixCalc {
    
    public static double[][] multiplyMatrixOnNumber(double[][] matrix, double multiplier) {
        validateMatrix(matrix);
        double[][] result = new double[matrix.length][matrix[0].length];
        for (int rowNum = 0 ; rowNum < matrix.length; rowNum++) {
            System.arraycopy(matrix[rowNum], 0, result[rowNum], 0, matrix[rowNum].length);
            for (int colNum  = 0; colNum < matrix[rowNum].length; colNum++) {
                result[rowNum][colNum] = result[rowNum][colNum] * multiplier;
            }
        }
        return result;   
    }
    
    public static double[][] multiplyMatrices(double[][] matrixLeft, double[][] matrixRight) {
        if (!canMultiply(matrixLeft, matrixRight)) throw new IllegalArgumentException("In order to multiply matrices, the one on the left should have same row length as the column length of the one on the right");
        double[][] result = new double[matrixLeft.length][matrixRight[0].length];
        for (int rowNumLeft = 0; rowNumLeft < matrixLeft.length; rowNumLeft++) {
            for (int colNumRight = 0; colNumRight < matrixRight[0].length; colNumRight++) {
                for (int colNumLeft = 0; colNumLeft < matrixLeft[0].length; colNumLeft++) {           
                        result[rowNumLeft][colNumRight] = result[rowNumLeft][colNumRight] + matrixLeft[rowNumLeft][colNumLeft] * matrixRight[colNumLeft][colNumRight];   
                }
            }
        }
        return result;
    }
    
    public static double[][] transpondMatrix(double[][] matrix) {
        validateMatrix(matrix);
        double[][] result = new double[matrix[0].length][matrix.length];
        for (int row = 0; row < matrix.length; row++) 
            for (int col = 0; col < matrix[0].length; col++)
                result[col][row] = matrix[row][col];
        return result;
    }
    
    public static double det(double[][] matrix) {
        validateSquareMatrix(matrix);
        double result = Double.MAX_VALUE;
        if (matrix.length == 2) return matrix[0][0]*matrix[1][1] - matrix[0][1]*matrix[1][0];
        if (matrix.length == 3) return detSarrus(matrix);
        if (matrix.length > 3) {
            result = 0;
            for(int col = 0; col < matrix[0].length; col++) {
                if (matrix[0][col] != 0)
                result = result + matrix[0][col] * cofactor(matrix, 1, col + 1);
            }
        }
        return result;
    }
    
    public static double detSarrus(double[][] matrix) {
        validateSquareMatrix(matrix);
        return matrix[0][0]*matrix[1][1]*matrix[2][2] + matrix[0][1]*matrix[1][2]*matrix[2][0] + matrix[1][0]*matrix[2][1]*matrix[0][2] - matrix[0][2]*matrix[1][1]*matrix[2][0] - matrix[1][0]*matrix[0][1]*matrix[2][2] - matrix[2][1]*matrix[1][2]*matrix[0][0];
        
    }
    
    public static double[][] reverse(double[][] matrix) {
        double det = validateCramerReturnDeterminant(matrix);
        return multiplyMatrixOnNumber(transpondMatrix(cofactorsMatrix(matrix)), 1.0d/det);
    }
    
    /**
    BEWARE: row and col are not Java array's indices but math matrix' row and column numbers instead, from 1 to matrix.length
    */
    public static double cofactor(double[][] matrix, int row, int col) {
        return (Math.pow(-1.0f, row + col)) * det(submatrix(matrix, row, col));
    }
    
    public static double[][] cofactorsMatrix(double[][] matrix) {
        double[][] result = new double[matrix.length][matrix.length];
        for (int row = 1; row <= matrix.length; row++)
            for (int col = 1; col <= matrix.length; col++)
                result[row-1][col-1] = cofactor(matrix, row, col);
        return result;
    }
    
    /**
    BEWARE: row and col are not Java array's indices but math matrix' row and column numbers instead, from 1 to matrix.length
    returns a matrix one row one column less in size. The row and column to exclude are provided as two arguments
    */
    public static double[][] submatrix(double[][] matrix, int row, int col) {
        double[][] submatrix = new double[matrix.length-1][matrix.length-1];
         int submatrixRow = 1, submatrixCol = 1;
         for (int rowNum = 1; rowNum <= matrix.length; rowNum++) {
            if (rowNum == row) continue;
            for (int colNum = 1; colNum <= matrix.length; colNum++) {
                if (colNum == col) continue;
                submatrix[submatrixRow-1][submatrixCol-1] = matrix[rowNum-1][colNum-1];
                submatrixCol++;
            }
            submatrixCol = 1;
            submatrixRow++;
        }
        return submatrix;
    }
    
    public static double[][] squareSubmatrix(double[][] matrix, int rowNum, int colNum, int squareSideSize) {
        double[][] result = new double[squareSideSize][squareSideSize];
        int resultRow = 0, resultCol = 0;
        for (int row = rowNum-1; row < rowNum+squareSideSize - 1; row++) {
            for (int col = colNum-1; col < colNum+squareSideSize - 1; col++) {
            try {
                result[resultRow][resultCol] = matrix[row][col];
                } catch (java.lang.ArrayIndexOutOfBoundsException e) {
                    System.err.print("=============ERROR====================\n");
                    System.err.print("\nresultRow=" + resultRow + "\n");
                    System.err.print("resultCol=" + resultCol + "\n");
                    System.err.print("squareSideSize=" + squareSideSize + "\n");
                    System.err.print("row=" + row + "\n");
                    System.err.print("col=" + col + "\n");
                    System.err.print("rowNum=" + rowNum + "\n");
                    System.err.print("colNum=" + colNum + "\n");
                    System.err.print("MATRIX:\n");
                    System.err.print(print(matrix));
                    System.err.print("RESULT:\n");
                    System.err.print(print(result));
                    System.err.print("\n=============/ERROR====================\n");
                    return result;
                }
                resultCol++;
                    // System.out.print("=============OK====================\n");
//                     System.out.print("\nresultRow=" + resultRow + "\n");
//                     System.out.print("resultCol=" + resultCol + "\n");
//                     System.out.print("squareSideSize=" + squareSideSize + "\n");
//                     System.out.print("row=" + row + "\n");
//                     System.out.print("col=" + col + "\n");
//                     System.out.print("rowNum=" + rowNum + "\n");
//                     System.out.print("colNum=" + colNum + "\n");
//                     System.out.print("MATRIX:\n");
//                     System.out.print(print(matrix));
//                     System.out.print("RESULT:\n");
//                     System.out.print(print(result));
//                     System.out.print("\n=============/OK====================\n");
            }
        resultRow++;
        resultCol = 0;
        }
        return result;
    }
    
    public static double[][] sumMatrices(double[][] matrix1, double[][] matrix2) {
        if (!canSum(matrix1, matrix2)) throw new IllegalArgumentException("Matrices need to be of equal size if you want to sum them up");
        double[][] result = new double[matrix1.length][matrix1[0].length];
        for (int row = 0; row < matrix1.length; row++) 
            for (int col = 0; col < matrix1[0].length; col++)
                result[row][col] = matrix1[row][col] + matrix2[row][col];
        return result;
    }
    
    public static int rank(double[][] matrix) {
        int rows = matrix.length, cols = matrix[0].length;
        int squareSideSize = Math.min(rows, cols);
       //  System.out.println("square size : " + squareSideSize);
        for (int rank = squareSideSize; rank > 1; rank--) {
            // System.out.println("rank : " + rank);
            for (int row = 0; row <= matrix.length - squareSideSize; row++) {
                // System.out.println("row : " + row);
                for (int col = 0; col <= matrix[row].length - squareSideSize; col++) {
                    // System.out.println("col : " + col);
                    double[][] temp = squareSubmatrix(matrix, row+1, col+1, rank);
                    // System.out.println(print(temp));
                    if (det(temp) != .0d) return rank;
                }
            }
        }
        for (int row = 0; row < matrix.length; row++) 
                for (int col = 0; col < matrix[row].length; col++) 
                    if (matrix[row][col] != 0) return 1;

        return 0;
    }
    
    //public static double[][] trapezoid(double[][] matrix) {
        
    //}

    public static String print(double[][] matrix) {
        double[] columnWiths = getPrintedColumnWiths(matrix);
        StringBuilder sb = new StringBuilder();
        for (int rowNum = 0; rowNum < matrix.length; rowNum++) {
            for (int colNum = 0; colNum < matrix[rowNum].length; colNum++) {
                if (colNum == 0) sb.append("| ");
                sb.append(" ").append(String.valueOf(matrix[rowNum][colNum])).append(" ");
                for (int j = 0; j <= columnWiths[colNum] - String.valueOf(matrix[rowNum][colNum]).length(); j++) {
                    sb.append(" ");
                }
                if(colNum == matrix[rowNum].length - 1) sb.append(" |");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
    
    public static void validateSquareMatrix(double[][] matrix) {
        validateMatrix(matrix);
        if (matrix.length < 2 || matrix.length != matrix[0].length) throw new IllegalArgumentException("Not a square matrix");
    }
    
    public static void validateMatrix(double[][] matrix) {
        if (isEmpty(matrix)) throw new IllegalArgumentException("Not a matrix argument. A matrix must have at least one element");
        int colNum = matrix[0].length;
        for (int i = 1; i < matrix.length; i++) {
            if (matrix[i].length != colNum) throw new IllegalArgumentException("Malformed matrix argument. All rows of the matrix must be of equal length");
        }
    }
    
    private static double[] getPrintedColumnWiths(double[][] matrix) {
        validateMatrix(matrix); 
        double[] result = new double[matrix[0].length];
        for (int rowNum = 0; rowNum < matrix.length; rowNum++) {
            for (int colNum = 0; colNum < matrix[rowNum].length; colNum++) {
                int stringLength = String.valueOf(matrix[rowNum][colNum]).length();
                if (stringLength > result[colNum]) result[colNum] = stringLength;
            }
        }
        return result;
    }
    
    public static boolean isEmpty(double[][] matrix) {
        if(matrix.length < 1 || matrix[0].length < 1) return true;
        return false;
    }
    
    public static boolean canMultiply(double[][] matrixLeft, double[][] matrixRight) {
        validateMatrix(matrixLeft); 
        validateMatrix(matrixRight); 
        if (matrixLeft[0].length == matrixRight.length) return true;
        return false; 
    }
    
    public static boolean canSum(double[][] matrix1, double[][] matrix2) {
        validateMatrix(matrix1); 
        validateMatrix(matrix2); 
        if (matrix1.length != matrix2.length || matrix1[0].length != matrix2[0].length) return false;
        return true;
    }
    
    public static double validateCramerReturnDeterminant(double[][] matrix) {
        validateSquareMatrix(matrix);
        double det = det(matrix);
        if (det == .0d) throw new IllegalArgumentException("The matrix is degenerate, so cannot have an inverted one");
        return det;
    }
    
    
        
}