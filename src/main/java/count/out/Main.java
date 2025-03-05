package count.out;

import java.util.*;
//struct first
//mask with 9 instead of 16?

//m<>s<<>>t(c)t(c)
//0n 315 729 846
//0b 10000010 10101010 10110010 0101
public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();
        if (input.equals("query")){
            //parse, # threads?
            //split threads
            Sudoku sudoku = new Sudoku(scan.nextLine());
            System.out.println(Arrays.deepToString(sudoku.charGrid));
            String out = sudoku.toFormatString();
            System.out.println(out);
            Counter count = new Counter(sudoku);
            count.count();
        }
        else if (input.equals("batch")){
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