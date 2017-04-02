import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Threaded run of ransac
 */
public class RansacThreads extends Ransac {
    public static final int NUM_THREADS = Runtime.getRuntime().availableProcessors();

    @Override
    public Line2 compute(ArrayList<Point> points) {
        Line2 model;
        //create an array of threads and start them running
        RansacThread[] ransacThreads = new RansacThread[NUM_THREADS];
        FanInThread[] fanInThreads = new FanInThread[NUM_THREADS/2];

        for (int i = 0; i < ransacThreads.length; i++) {
            ransacThreads[i] = new RansacThread(points, points.size()/NUM_THREADS);
            ransacThreads[i].start();
        }

        //wait for all threads to finish
        for (RansacThread t : ransacThreads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //initial collect and find best fits
        int i = NUM_THREADS/2;
        for(int j = 0; j<i; j++) {
            fanInThreads[j] = new FanInThread(ransacThreads[j].model, ransacThreads[j+i].model);
            fanInThreads[j].start();
        }

        //wait for initial collect
        for (FanInThread t : fanInThreads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        i=i/2;
        while(i > 1) {
            for(int j = 0; j<i; j++) {
                fanInThreads[j] = new FanInThread(fanInThreads[j].result, fanInThreads[j+i].result);
                fanInThreads[j].start();
            }

            for (int j = 0; j<i; j++) {
                try {
                    fanInThreads[j].join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            i=i/2;
        }



        return fanInThreads[0].result;
    }

    class RansacThread extends Thread {
        Line2 model;
        ArrayList<Point> points;
        int iterations;
        public RansacThread(ArrayList<Point> points, int iterations) {
            this.points = points;
            this.iterations = iterations;
        }

        @Override
        public void run() {
             this.model = ransac(this.points, iterations);
        }

    }

    class FanInThread extends Thread {
        Line2 l1;
        Line2 l2;
        Line2 result;
        public FanInThread(Line2 l1, Line2 l2) {
            this.l1 = l1;
            this.l2 = l2;
        }

        @Override
        public void run() {
            if(l1.fit > l2.fit) {
                result = l1;
            } else {
                result = l2;
            }
        }
    }

    /**
     * test dat code
     * @param args
     */
    public static void main(String[] args) {
        for(int j=0; j< 50; j++) {
            ArrayList<Point> points = new ArrayList<>();
            Random random = new Random();
            int size = 1000;

            for (int i = 0; i < 200; i++) {
                points.add(new Point(random.nextInt(size), random.nextInt(size)));
            }
            for (int i = 0; i < 1000; i++) {
                points.add(new Point(random.nextInt(size), 500));
            }
            Line2 line = new RansacThreads().compute(points);

            Drawer drawer = new Drawer(size, size);
            drawer.setLine(line.p1, line.p2);
            drawer.setPoints(points);
            drawer.setVisible(true);
        }
    }
}
