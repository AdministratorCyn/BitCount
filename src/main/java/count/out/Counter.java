package count.out;

public class Counter{
    Sudoku s;
    public Counter(Sudoku sudoku) {
        s = sudoku;
    }
    public int count() {
        int count = 0;
        int[] res = findUSC();
        if (res[0] == -1) {
            return 1;
        }
        int y = res[1];
        int x = res[0];
        int[][] copy = new int[s.candGrid.length][];
        for (int p = 0; p < s.candGrid.length; p++) {
            copy[p] = s.candGrid[p].clone();
        }
        short old = s.charGrid[y][x / 4];
        for (int i = 0; i < 9; i++) {
            if ((s.candGrid[y][x / 3] & (1 << (31 - ((x % 3) * 9 + i)))) != 0) {
                s.charGrid[y][x >> 2] |= ((i + 1) << ((3 - (x & 3)) << 2));
                s.autocand(x, y, (i + 1));
                count += count();
                s.charGrid[y][x / 4] = old;
                for (int j = 0; j < 9; j++) {
                    System.arraycopy(copy[j], 0, s.candGrid[j], 0, 3);
                }
            }
        }
        return count;
    }
    public int[] findUSC() {
        for (int i = 0; i < 9; i++) {
            int j = 0;
            for (; j < 9; j++) {
                if (((s.charGrid[i][j >> 2]) >> ((3 - (j & 3)) << 2) & 15) == 0) {
                    return new int[]{j, i};
                }
            }
        }
        return new int[]{-1};
    }
}
