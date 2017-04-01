// Authors:  Dale Skrien & U of Illinois CS faculty

/**
 * a class for demonstrating the non-synchronized nature of threads
 */
public class PrinterThread extends Thread
{

    public PrinterThread(String name) {
        super(name);
    }

    public void run() {
        for (int i = 0; i < 50; i++) {
            System.out.println(getName());
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        PrinterThread thread1 = new PrinterThread("Thread1");
        PrinterThread thread2 = new PrinterThread("Thread  2");
        thread1.start();
        thread2.start();
        thread1.join(); // pause main thread until thread1 ends
        thread2.join(); // pause main thread until thread2 ends
        System.out.println("Done");
    }
}
