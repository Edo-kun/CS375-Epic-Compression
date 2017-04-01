import java.awt.*;

/**
 * Created by Areito on 4/1/2017.
 */
public class Line2 {
    public double m;
    public double b;
    public Point p1;
    public Point p2;

    public Line2(){}

    public Line2(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
        this.m = ((p2.getY() - p1.getY()) / (p2.getX() - p1.getX()));
        // slope (gradient) of the line
        this.b = p2.getY() - this.m * p2.getX();
    }
}
