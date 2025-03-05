package count.out;

public class Sudoku {

    //compacted ints 9 + 1 + 4 bit
    //shorts 9x3
    //custom class?

    short[][] charGrid;
    boolean[][] candGrid;

    //367415892482369715915827463136274589274958631859136247721683954693542178548791326
    public Sudoku(String str) {
        //regex
        short[][] out = new short[9][3];
        for (int i = 0; i < 9; i++) {
            short sub = 0;
            for (int j = 0; j < 9; j++) {
                short value;
                if (str.charAt(i * 9 + j) == '.') {
                    value = 0;
                }
                else {
                    value = (short) Character.getNumericValue(str.charAt(i * 9 + j));
                }
                if (j % 9 != 8) {
                    sub |= (value << (4 * (4 - (j % 4 + 1)))); // Shift and OR the bits
                }
                else {
                    sub = value;
                }
                if (j % 4 == 3 || j == 8) {
                    out[i][j / 4] = sub;
                    sub = 0;
                }
            }
        }

        charGrid = out;
    }
    public Sudoku(int givens) {

    }
    public boolean[][] candSetup() {
        boolean[][] out = new boolean[9][81];
        return out;
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
                        int mask = (sub & (15 << ((3 - k) * 4))) >> ((3 - k) * 4);
                        if (mask == 0) {
                            out.append('.');
                        }
                        else {
                            out.append(mask);
                        }
                    }
                } else {
                    if ((sub & 15) == 0) {
                        out.append('.');
                    }
                    else {
                        out.append(sub & 15);
                    }
                }
            }
        }
        return out.toString();

    }
}
