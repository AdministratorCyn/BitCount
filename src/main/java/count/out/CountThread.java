package count.out;
//why did i separate?
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CountThread extends Thread {
    Counter c;
    private static final String FILE_PATH = "output.txt";

    public CountThread(Counter counter) {
        c = counter;
    }

    public void run() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            while (true) {
                String output = c.s.toFormatString() + " " + c.count();
                writer.write(output);
                writer.newLine();
                writer.flush();
                c.s = new Sudoku(Main.generate().toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
