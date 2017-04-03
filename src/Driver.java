/**
 * @author Dale Skrien
 * @author Shawn Jiang
 * @author Alex Rinker
 * @author Ed Zhou
 * @author Mathias "DromeStrikeClaw" Syndrome
 */

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * A driver for testing subclasses of ImageCompressor.
 */

public class Driver
{
    final static int THRESHOLD = 1;
    final static int UP_TO = 3500000;
    static long sequentialRuntime = 0;
    final static int NUM_PROCESSORS = Runtime.getRuntime().availableProcessors();
    final static int SIZE = 1000;
    private static ArrayList<Point> points;

    public static void main(String[] args) throws Exception {

        Drawer drawer = new Drawer(SIZE, SIZE);

        points = new ArrayList<>();
        Random random = new Random();
        int size = 10000;

        for (int i = 0; i < size/5; i++) {
            points.add(new Point(random.nextInt(size), random.nextInt(size)));
        }
        for (int i = 0; i < size; i++) {
            points.add(new Point(random.nextInt(size), 500));
        }
        drawer.setPoints(points);


        System.out.println("Number of processors: " + NUM_PROCESSORS);
        test("Sequential version", new RansacSequential(), drawer);
        test("Threads version", new RansacThreads(), drawer);
        test("Fork-join version", new RansacForkJoin(), drawer);
        test("Parallel streams version", new RansacStreams(), drawer);
//        drawer.setVisible(true);
    }

    private static void test(String version, Ransac p, Drawer drawer) throws Exception {

        // warm-up
        for(int i = 0; i<NUM_PROCESSORS/2;i++) {
            p.computeRansac(points);
        }
        // computeRansac results
        Timer.start();
        Line2 result = p.computeRansac(points);
        Timer.stop();

        // output the results
        System.out.println("--------" + version + "----------");
        System.out.println("Line: " + result.p1 +" to "+ result.p2);
        // output the time needed to get da lines!
        System.out.println("Time: " + Timer.getRuntime() + "ms");

        // output the speedup
        if (sequentialRuntime == 0) {
            sequentialRuntime = Timer.getRuntime(); //sequential time
        }
        else {
            System.out.printf("Speed-up: %.2f\n", sequentialRuntime / 1.0 / Timer
                    .getRuntime());
        }

        drawer.addLine(result.p1, result.p2);
        System.out.println();
    }
}