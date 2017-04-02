import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Sequential run of ransac
 */
public class RansacSequential extends Ransac {


    @Override
    public Line2 compute(ArrayList<Point> points) {
        return ransac(points, points.size());
    }

    /**
     * test dat code
     * @param args
     */
    public static void main(String[] args) {
        ArrayList<Point> points = new ArrayList<>();
        Random random = new Random();
        int size = 1000;

        for (int i = 0; i < 200; i++) {
            points.add(new Point(random.nextInt(size), random.nextInt(size)));
        }
        for (int i = 0; i < 1000; i++) {
            points.add(new Point(random.nextInt(size), 500));
        }
        Line2 line = new RansacSequential().compute(points);

        Drawer drawer = new Drawer(size, size);
        drawer.setLine(line.p1, line.p2);
        drawer.setPoints(points);
        drawer.setVisible(true);
    }
}
