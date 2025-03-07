package count.out;

import java.util.Arrays;

public class Sudoku {

    //compacted ints 9 + 1 + 4 bit
    //shorts 9x3
    //custom class?

    public short[][] charGrid = new short[9][3]; //see if both have better prim approxes
    public int[][] candGrid = new int[9][3];

    //367415892482369715915827463136274589274958631859136247721683954693542178548791326
    //9.6..2.7....94....4...578..13......2...271...6......41..158...6....14....6.7..4.5
    public Sudoku(String str) {
        //regex
        for (int i = 0; i < 9; i++) {
            short sub = 0;
            for (int j = 0; j < 9; j++) {
                char c = str.charAt(i * 9 + j);
                short value = (c == '.') ? 0 : (short) Character.getNumericValue(c);
                sub |= (value << (4 * (4 - (j % 4 + 1))));
                if (j % 4 == 3 || j == 8) {
                    charGrid[i][j / 4] = sub;
                    sub = 0;
                }
            }
        }
        autocand();
    }
    public Sudoku(int givens) {

    }
    public CountThread[] split() {
        CountThread[] threads = new CountThread[2];
        return threads;
    }
    public String toFormatString() {
        StringBuilder out = new StringBuilder();
        int sub = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) { //feels messy, can def optim
                if (j % 4 == 0) {
                    sub = charGrid[i][j / 4] & 0xFFFF;
                }
                //int mask = (sub & (15 << ((3 - k) * 4))) >> ((3 - k) * 4); L
                int mask = (sub >> ((3 - (j % 4)) * 4) & 15);
                if (mask == 0) {
                    out.append('.');
                } else {
                    out.append(mask);
                }
            }
        }
        return out.toString();
    }
    public void autocand() {
        //turn all on
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 3; j++) {
                candGrid[i][j] = -32;
            }
        }
        //do scs
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int i1 = (charGrid[i][j / 4] & 0xFFFF) >> ((3 - (j % 4)) * 4) & 15;
                if (i1 != 0) {
                    //hor
                    for (int k = 0; k < 9; k++) {
                        if (k != j) {
                            candGrid[i][k / 3] &= ~(1 << (32 - ((k % 3) * 9 + i1))); //too messy to flash
                        }
                    }
                    //vert
                    for (int k = 0; k < 9; k++) {
                        if (k != i) {
                            candGrid[k][j / 3] &= ~(1 << (32 - ((j % 3) * 9 + i1)));
                        }
                    }
                    //box
                    for (int k = 0; k < 9; k++) {
                        if (k != ((i % 3) * 3 + j % 3)) {
                            candGrid[i / 3 * 3 + (k / 3)][j / 3] &= ~(1 << (32 - ((k % 3) * 9 + i1)));
                        }
                    }
                    //cell
                    for (int k = 0; k < 9; k++) {
                        if ((k + 1) != i1) {
                            candGrid[i][j / 3] &= ~(1 << (31 - ((j % 3) * 9 + k)));
                        }
                    }
                    //System.out.println(Integer.toBinaryString(candGrid[i][j / 3] |= 1 << 31) + " " + i + " " + j); cheesed
                }
            }
        }
        /*for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(Integer.toBinaryString(candGrid[i][j / 3] |= 1 << 31).substring(j % 3 * 9, (j % 3 + 1) * 9) + " ");
                if (j % 3 == 2) {
                    System.out.print(" | ");
                }
            }
            System.out.println();
        }*/

    }
    public void autocand(int x, int y, int val) {
        for (int k = 0; k < 9; k++) {
            if (k != x) {
                candGrid[y][k / 3] &= ~(1 << (32 - ((k % 3) * 9 + val))); //too messy to flash
            }
        }
        //vert
        for (int k = 0; k < 9; k++) {
            if (k != y) {
                candGrid[k][x / 3] &= ~(1 << (32 - ((x % 3) * 9 + val)));
            }
        }
        //box
        for (int k = 0; k < 9; k++) {
            if (k != ((y % 3) * 3 + x % 3)) {
                candGrid[y / 3 * 3 + (k / 3)][x / 3] &= ~(1 << (32 - ((k % 3) * 9 + val)));
            }
        }
        //cell
        for (int k = 0; k < 9; k++) {
            if ((k + 1) != val) {
                candGrid[y][x / 3] &= ~(1 << (31 - ((x % 3) * 9 + k)));
            }
        }
    }
    //funny ns with aggregated shifting
}
