package count.out;
//why did i separate?
public class CountThread extends Thread{
    Counter c;
    public CountThread(Counter counter) {
        c = counter;
    }
    public void run() {
        System.out.println(c.s.toFormatString() + " " + c.count());
    }
}
