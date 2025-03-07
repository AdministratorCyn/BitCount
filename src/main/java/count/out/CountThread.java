package count.out;
//why did i separate?
public class CountThread extends Thread{
    Counter c;
    public CountThread(Counter counter) {
        c = counter;
    }
    public void run() {
        for (; ;) {
            System.out.println(c.s.toFormatString() + " " + c.count());
            c.s = new Sudoku(Main.generate().toString());
        }
    }
}
