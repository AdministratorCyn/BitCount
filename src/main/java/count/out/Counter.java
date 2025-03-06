package count.out;

public class Counter{
    Sudoku s;
    public Counter(Sudoku sudoku) {
        s = sudoku;
    }
    public int count() {
        int count = 0;
        int y = -1;
        int x = -1;
        outerloop:
        for (y = 0; y < 9; y++) {
            for (x = 0; x < 9; x++) {
                if (((s.charGrid[y][x / 4] & 0xFFFF) >> ((3 - (x % 4)) * 4) & 15) == 0) {
                    break outerloop;
                }
            }
            if (y == 8) {
                return 1;
            }
        }
        for (int i = 0; i < 9; i++) {
            if ((s.candGrid[y][x / 3] & (1 << (31 - ((x % 3) * 9 + i)))) != 0) {
                short old = s.charGrid[y][x / 4];
                s.charGrid[y][x / 4] |= ((i + 1) << (4 * (3 - (x % 4))));
                s.autocand();
                count += count();
                s.charGrid[y][x / 4] = old;
                s.autocand();
            }
        }
        return count;
    }
}
