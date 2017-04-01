// Authors:  Dale Skrien & U of Illinois CS faculty

/**
 * A class for demonstrating the non-atomic nature of the increment ++ operator.
 */
public class IncrementerThread extends Thread
{
    private int[] data;

    public IncrementerThread(int[] d) { data = d; }

    public void run() {
        for (int i = 0; i < 10000; i++) {
            data[0]++;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int[] A = new int[1];
        IncrementerThread thread1 = new IncrementerThread(A);
        IncrementerThread thread2 = new IncrementerThread(A);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println(A[0]);
    }
}
