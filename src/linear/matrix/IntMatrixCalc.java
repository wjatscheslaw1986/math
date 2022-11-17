package matrix;

import java.math.BigDecimal;

public class IntMatrixCalc {
    
    public static int[][] multiplyMatrixOnInt(int[][] matrix, int multiplier) {
        validateMatrix(matrix);
        int[][] result = new int[matrix.length][matrix[0].length];
        for (int rowNum = 0 ; rowNum < matrix.length; rowNum++) {
            System.arraycopy(matrix[rowNum], 0, result[rowNum], 0, matrix[rowNum].length);
            for (int colNum  = 0; colNum < matrix[rowNum].length; colNum++) {
                result[rowNum][colNum] = result[rowNum][colNum] * multiplier;
            }
        }
        return result;   
    }
    
    public static double[][] multiplyMatrixOnNumber(int[][] matrix, double multiplier) {
        validateMatrix(matrix);
        double[][] result = new double[matrix.length][matrix[0].length];
        for (int rowNum = 0 ; rowNum < matrix.length; rowNum++) {
            for (int c = 0; c < matrix[0].length; c++) result[rowNum][c] = (double) matrix[rowNum][c];
            for (int colNum  = 0; colNum < matrix[rowNum].length; colNum++) {
                result[rowNum][colNum] = result[rowNum][colNum] * multiplier;
            }
        }
        return result;   
    }
    
    public static double[][] multiplyMatrixOnNumber(double[][] matrix, double multiplier) {
        //validateMatrix(matrix);
        double[][] result = new double[matrix.length][matrix[0].length];
        for (int rowNum = 0 ; rowNum < matrix.length; rowNum++) {
            System.arraycopy(matrix[rowNum], 0, result[rowNum], 0, matrix[rowNum].length);
            for (int colNum  = 0; colNum < matrix[rowNum].length; colNum++) {
                result[rowNum][colNum] = result[rowNum][colNum] * multiplier;
            }
        }
        return result;   
    }
    
    public static int[][] multiplyMatrices(int[][] matrixLeft, int[][] matrixRight) {
        if (!canMultiply(matrixLeft, matrixRight)) throw new IllegalArgumentException("In order to multiply matrices, the one on the left should have same row length as the column length of the one on the right");
        int[][] result = new int[matrixLeft.length][matrixRight[0].length];
        for (int rowNumLeft = 0; rowNumLeft < matrixLeft.length; rowNumLeft++) {
            for (int colNumRight = 0; colNumRight < matrixRight[0].length; colNumRight++) {
                for (int colNumLeft = 0; colNumLeft < matrixLeft[0].length; colNumLeft++) {           
                        result[rowNumLeft][colNumRight] = result[rowNumLeft][colNumRight] + matrixLeft[rowNumLeft][colNumLeft] * matrixRight[colNumLeft][colNumRight];   
                }
            }
        }
        return result;
    }
    
    public static int[][] transpondMatrix(int[][] matrix) {
        validateMatrix(matrix);
        int[][] result = new int[matrix[0].length][matrix.length];
        for (int row = 0; row < matrix.length; row++) 
            for (int col = 0; col < matrix[0].length; col++)
                result[col][row] = matrix[row][col];
        return result;
    }
    
    public static int det(int[][] matrix) {
        validateSquareMatrix(matrix);
        int result = Integer.MAX_VALUE;
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
    
    public static int detSarrus(int[][] matrix) {
        validateSquareMatrix(matrix);
        return matrix[0][0]*matrix[1][1]*matrix[2][2] + matrix[0][1]*matrix[1][2]*matrix[2][0] + matrix[1][0]*matrix[2][1]*matrix[0][2] - matrix[0][2]*matrix[1][1]*matrix[2][0] - matrix[1][0]*matrix[0][1]*matrix[2][2] - matrix[2][1]*matrix[1][2]*matrix[0][0];
        
    }
    
    public static double[][] reverse(int[][] matrix) {
        validateSquareMatrix(matrix);
        int det = det(matrix);
        if (det == 0) throw new IllegalArgumentException("The matrix is degenerate, so cannot have an inverted one");
        return multiplyMatrixOnNumber(transpondMatrix(cofactorsMatrix(matrix)), 1.0d/det);
    }
    
    /**
    BEWARE: row and col are not Java array's indices but math matrix' row and column numbers instead, from 1 to matrix.length
    */
    public static int cofactor(int[][] matrix, int row, int col) {
        return (int) Math.round(Math.pow(-1, row + col)) * det(submatrix(matrix, row, col));
    }
    
    public static int[][] cofactorsMatrix(int[][] matrix) {
        int[][] result = new int[matrix.length][matrix.length];
        for (int row = 1; row <= matrix.length; row++)
            for (int col = 1; col <= matrix.length; col++)
                result[row-1][col-1] = cofactor(matrix, row, col);
        return result;
    }
    
    /**
    BEWARE: row and col are not Java array's indices but math matrix' row and column numbers instead, from 1 to matrix.length
    */
    public static int[][] submatrix(int[][] matrix, int row, int col) {
        int[][] submatrix = new int[matrix.length-1][matrix.length-1];
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
    
    public static int[][] sumMatrices(int[][] matrix1, int[][] matrix2) {
        if (!canSum(matrix1, matrix2)) throw new IllegalArgumentException("Matrices need to be of equal size if you want to sum them up");
        int[][] result = new int[matrix1.length][matrix1[0].length];
        for (int row = 0; row < matrix1.length; row++) 
            for (int col = 0; col < matrix1[0].length; col++)
                result[row][col] = matrix1[row][col] + matrix2[row][col];
        return result;
    }

    public static String print(int[][] matrix) {
        int[] columnWiths = getPrintedColumnWiths(matrix);
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
    
    public static String print(double[][] matrix) {
        int[] columnWiths = getPrintedColumnWiths(matrix);
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
    
    public static void validateSquareMatrix(int[][] matrix) {
        validateMatrix(matrix);
        if (matrix.length < 2 || matrix.length != matrix[0].length) throw new IllegalArgumentException("Not a square matrix");
    }
    
    public static void validateMatrix(int[][] matrix) {
        if (isEmpty(matrix)) throw new IllegalArgumentException("Not a matrix argument. A matrix must have at least one element");
        int colNum = matrix[0].length;
        for (int i = 1; i < matrix.length; i++) {
            if (matrix[i].length != colNum) throw new IllegalArgumentException("Malformed matrix argument. All rows of the matrix must be of equal length");
        }
    }
    
    private static int[] getPrintedColumnWiths(int[][] matrix) {
        validateMatrix(matrix); 
        int[] result = new int[matrix[0].length];
        for (int rowNum = 0; rowNum < matrix.length; rowNum++) {
            for (int colNum = 0; colNum < matrix[rowNum].length; colNum++) {
                int stringLength = String.valueOf(matrix[rowNum][colNum]).length();
                if (stringLength > result[colNum]) result[colNum] = stringLength;
            }
        }
        return result;
    }
    
    private static int[] getPrintedColumnWiths(double[][] matrix) {
        //validateMatrix(matrix); 
        int[] result = new int[matrix[0].length];
        for (int rowNum = 0; rowNum < matrix.length; rowNum++) {
            for (int colNum = 0; colNum < matrix[rowNum].length; colNum++) {
                int stringLength = String.valueOf(matrix[rowNum][colNum]).length();
                if (stringLength > result[colNum]) result[colNum] = stringLength;
            }
        }
        return result;
    }
    
    public static boolean isEmpty(int[][] matrix) {
        if(matrix.length < 1 || matrix[0].length < 1) return true;
        return false;
    }
    
    public static boolean canMultiply(int[][] matrixLeft, int[][] matrixRight) {
        validateMatrix(matrixLeft); 
        validateMatrix(matrixRight); 
        if (matrixLeft[0].length == matrixRight.length) return true;
        return false; 
    }
    
    public static boolean canSum(int[][] matrix1, int[][] matrix2) {
        validateMatrix(matrix1); 
        validateMatrix(matrix2); 
        if (matrix1.length != matrix2.length || matrix1[0].length != matrix2[0].length) return false;
        return true;
    }
        
}