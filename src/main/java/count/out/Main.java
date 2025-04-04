package count.out;

import java.util.*;
//charge autocand
//mask with 9 instead of 16 (in char)?

/*
    use all bitwise operations
    find out how to do / !(2^n) with bitwise
 */

//go do ns please


//m<>s<<>>t(c)t(c)

//fastest 3.3609s
//.....5.7.1......38.....1..2..2.9......3.5...6...............9.7.8.6..4.....2.4...
public class Main {
    public static boolean isValidPlacement(int row, int col, int num, int[][] sudokuGrid) {
        // Check the row
        for (int i = 0; i < 9; i++) {
            if (sudokuGrid[row][i] == num) {
                return false;
            }
        }

        // Check the column
        for (int i = 0; i < 9; i++) {
            if (sudokuGrid[i][col] == num) {
                return false;
            }
        }

        // Check the 3x3 subgrid
        int startRow = (row / 3) * 3;
        int startCol = (col / 3) * 3;
        for (int i = startRow; i < startRow + 3; i++) {
            for (int j = startCol; j < startCol + 3; j++) {
                if (sudokuGrid[i][j] == num) {
                    return false;
                }
            }
        }

        return true;
    }
    public static StringBuilder generate() {
        StringBuilder str = new StringBuilder(".................................................................................");

        // Number of digits to place
        int mod = (int) (Math.random() * 5);
        int numberOfDigits = 16 + mod;

        // Sudoku grid (9x9) for checking placement rules
        int[][] sudokuGrid = new int[9][9];

        // Function to check if placing a number violates Sudoku rules


        // Fill Sudoku with random digits while ensuring no rule violations
        for (int i = 0; i < numberOfDigits; i++) {
            boolean placed = false;
            while (!placed) {
                // Random position in the grid
                int randomPos = (int) (Math.random() * 81);
                int row = randomPos / 9;
                int col = randomPos % 9;

                // Skip if already filled or invalid position
                if (str.charAt(randomPos) != '.') {
                    continue;
                }

                // Generate the digit for placement (1 to 9)
                int num = (i + 1) % 9 + 1;

                // Check if it's valid to place the number
                if (isValidPlacement(row, col, num, sudokuGrid)) {
                    // Place the digit in the string and the grid
                    str.setCharAt(randomPos, Character.forDigit(num, 10));
                    sudokuGrid[row][col] = num;
                    placed = true;
                }
            }
        }
        return str;
    }
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();
        if (input.equals("query")){
            //parse, # threads?
            //split threads
            Sudoku sudoku = new Sudoku(scan.nextLine());
            for (int i = 0; i < 100; i++) {
                sudoku.ns();
            }
            System.out.println(Arrays.deepToString(sudoku.charGrid));
            String out = sudoku.toFormatString();
            System.out.println(out);
            long start = System.nanoTime();
            Counter count = new Counter(sudoku);
            System.out.println(count.count() + " " + (System.nanoTime() - start));

        }
        else if (input.equals("batch")){
            for (int t = 0; t < 4; t++) {
                Sudoku sudoku = new Sudoku(generate().toString());
                System.out.println(sudoku.toFormatString());
                CountThread thread = new CountThread(new Counter(sudoku));
                thread.start();
            }
            //# threads?
            //split threads
        }
        else if (input.equals("debug")){
            //parse
            //split threads
        }
        else if (input.equals("exp")){
            //parse
            //split threads
        }
    }
}