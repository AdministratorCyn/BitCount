package count.out;

import java.util.Arrays;

public class Sudoku {

    //compacted ints 9 + 1 + 4 bit
    //shorts 9x3
    //custom class?

    short[][] charGrid = new short[9][3]; //see if both have better prim approxes
    int[][] candGrid = new int[9][3];

    //367415892482369715915827463136274589274958631859136247721683954693542178548791326
    //9.6..2.7....94....4...578..13......2...271...6......41..158...6....14....6.7..4.5
    public Sudoku(String str) {
        //regex
        for (int i = 0; i < 9; i++) {
            short sub = 0;
            for (int j = 0; j < 9; j++) {
                char c = str.charAt(i * 9 + j);
                short value = (c == '.') ? 0 : (short) Character.getNumericValue(c);
                if (j % 9 != 8) {
                    sub |= (value << (4 * (4 - (j % 4 + 1))));
                }
                else {
                    sub = value;
                }
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
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 3; j++) {
                int sub = charGrid[i][j] & 0xFFFF; //unsigned
                if (j != 2) {
                    for (int k = 0; k < 4; k++) {
                        //int mask = (sub & (15 << ((3 - k) * 4))) >> ((3 - k) * 4); L
                        int mask = (sub >> ((3 - k) * 4) & 15);
                        if (mask == 0) {
                            out.append('.');
                        }
                        else {
                            out.append(mask);
                        }
                    }
                } else {
                    if (sub == 0) {
                        out.append('.');
                    }
                    else {
                        out.append(sub); //"sub & 15" what the fuck are you DOING CYN
                    }
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
        System.out.println(Arrays.deepToString(candGrid));
        //do scs
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (((charGrid[i][j / 4] & 0xFFFF) >> ((3 - (j % 4)) * 4) & 15) != 0) {
                    //hor
                    for (int k = 0; k < 9; k++) {
                        if (k != j) {
                            candGrid[i][k / 3] ^= (1 << (31 - ((k % 3) * 9 + ((charGrid[i][j / 4] & 0xFFFF) >> ((3 - (j % 4)) * 4) & 15)))); //too messy to flash
                        }
                    }
                    //vert
                    for (int k = 0; k < 9; k++) {

                    }
                    //box
                    for (int k = 0; k < 9; k++) {

                    }
                    //cell
                    for (int k = 0; k < 9; k++) {
                        if (k + 1 != ((charGrid[i][j / 4] & 0xFFFF) >> ((3 - (j % 4)) * 4) & 15)) {
                            candGrid[i][j / 3] ^= (1 << (31 - ((j % 3) * 9 + k)));
                        }
                    }
                    //System.out.println(Integer.toBinaryString(candGrid[i][j / 3] |= 1 << 31) + " " + i + " " + j); cheesed
                }
            }
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(Integer.toBinaryString(candGrid[i][j / 3] |= 1 << 31).substring(j % 3 * 9, (j % 3 + 1) * 9) + " ");
                if (j % 3 == 2) {
                    System.out.print(" | ");
                }
            }
            System.out.println();
        }

    }
    public void autocand(short input) {
        //x, y, val
    }
    //funny ns with aggregated shifting
}
