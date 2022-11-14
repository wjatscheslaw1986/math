package matrix;

public class IntMatrixCalc {
    
    public static int[][] multiplyOnInt(int[][] matrix, int multiplier) {
        for (int rowNum = 0 ; rowNum < matrix.length; rowNum++)
            for (int colNum  = 0; colNum < matrix[rowNum].length; colNum++) {
                int val = matrix[rowNum][colNum];
                val = val * multiplier;
                matrix[rowNum][colNum] = val;
            }
        return matrix;   
    }

    public static String print(int[][] matrix) {
        if (!isMatrix(matrix)) throw new IllegalArgumentException("A valid matrix must have it's rows of equal length");
        StringBuilder sb = new StringBuilder();
        for (int rowNum = 0; rowNum < matrix.length; rowNum++) {
            for (int colNum = 0; colNum < matrix[rowNum].length; colNum++) {
                if (colNum == 0) sb.append("| ");
                sb.append(" ").append(String.valueOf(matrix[rowNum][colNum])).append(" ");
                if(colNum == matrix[rowNum].length - 1) sb.append(" |");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
    
    public static boolean isMatrix(int[][] matrix) {
        int colNum = 0;
        if (matrix.length < 1) return false;
        colNum = matrix[0].length;
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i].length != colNum) return false;
        }
        return true;
    }
        
}