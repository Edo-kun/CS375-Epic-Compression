import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * This class does ransac sequentially with 0 parallel stuff
 */

public class RansacSequential {
    /**
     * finds the intercept of the normal from a point to the estimated line model
     */
    public Point findIntercept(Line2 line, Point point ) {
        double x,y;

        x = (point.getX() + line.m*point.getY() - line.m*line.b)/(1 + line.m*line.m);
        y = (line.m*point.getX()+(line.m*line.m)*point.getY()-(line.m*line.m)*line.b)/(1+line.m*line.m)+line.b;

        return new Point((int)x,(int)y);
    }


    public Line2 ransac(ArrayList<Point> points) {
        Line2 tmpmodel;
        Line2 model = new Line2();
        ArrayList<Point> maybe_points = new ArrayList<>();
        ArrayList<Point> test_points = new ArrayList<>();
        Point p0 = new Point();
        Point p1 = new Point();
        int r0,r1;
        int i,j;
        double dist;
        int num = 0;
        int max = 0;
        Random random = new Random();

        //Perform RANSAC iterations n times!~~~~
        for(i=0;i < points.size(); i++){
            //make sure lists are empty
            maybe_points.clear();
            test_points.clear();
            r0 = 0;
            r1 = 0;
            while(points.get(r0).getX() == points.get(r1).getX()) {
                r0 = (int)(random.nextDouble()*points.size());
                r1 = (int)(random.nextDouble()*points.size());
            }

            maybe_points.add(points.get(r0));
            maybe_points.add(points.get(r1));

            for(j=0;j<points.size();j++){
                if(j != r0 && j != r1){
                    test_points.add(points.get(j));
                }
            }

            //find a line model for the randomly selected points
            tmpmodel = new Line2(maybe_points.get(0), maybe_points.get(1));

            num = 0;
            //find orthogonal lines to the model for all given points
            for(j=0;j < points.size()-2;j++){

                p0 = test_points.get(j);

                //find an intercept point of the model
                p1 = findIntercept(tmpmodel, p0);

                // distance from point to the model
                dist = Math.pow(Math.pow((p1.x - p0.x),2) + Math.pow((p1.y - p0.y),2),.5);

                // check whether it's an inlier or not
                if(dist < Driver.THRESHOLD) {
                    num++;
                }
            }
            if(num >= max){
                max = num;
                model = tmpmodel;
            }
        }

        return model;
    }

    public static void main(String[] args) {
        for(int j=0; j<50; j++) {
            ArrayList<Point> points = new ArrayList<>();
            Random random = new Random();
            int size = 1000;

            for (int i = 0; i < 200; i++) {
                points.add(new Point(random.nextInt(size), random.nextInt(size)));
            }
            for (int i = 0; i < 1000; i++) {
                points.add(new Point(random.nextInt(size), 500));
            }
            Line2 line = new RansacSequential().ransac(points);

            Drawer drawer = new Drawer(size, size);
            drawer.setLine(line.p1, line.p2);
            drawer.setPoints(points);
            drawer.setVisible(true);

        }
    }
}