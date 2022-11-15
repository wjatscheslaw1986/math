package matrix;

public class IntMatrixCalc {
    
    public static int[][] multiplyOnInt(int[][] matrix, int multiplier) {
        validateMatrix(matrix);
        for (int rowNum = 0 ; rowNum < matrix.length; rowNum++)
            for (int colNum  = 0; colNum < matrix[rowNum].length; colNum++) {
                int val = matrix[rowNum][colNum];
                val = val * multiplier;
                matrix[rowNum][colNum] = val;
            }
        return matrix;   
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
    
    public static int[][] sumMatrices(int[][] matrix1, int[][] matrix2) {
        if (!canSum(matrix1, matrix2)) throw new IllegalArgumentException("Matrices need to be of equal size if you want to sum them");
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
    
    public static void validateMatrix(int[][] matrix) {
        if (isEmpty(matrix)) throw new IllegalArgumentException("Matrix must have at least one element");
        int colNum = matrix[0].length;
        for (int i = 1; i < matrix.length; i++) {
            if (matrix[i].length != colNum) throw new IllegalArgumentException("All rows of the matrix must be of equal length");
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