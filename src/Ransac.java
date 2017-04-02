import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * This class does ransac sequentially with 0 parallel stuff
 */

public abstract class Ransac {
    /**
     * finds the intercept of the normal from a point to the estimated lines model
     */
    public Point findIntercept(Line2 line, Point point ) {
        double x,y;

        x = (point.getX() + line.m*point.getY() - line.m*line.b)/(1 + line.m*line.m);
        y = (line.m*point.getX()+(line.m*line.m)*point.getY()-(line.m*line.m)*line.b)/(1+line.m*line.m)+line.b;

        return new Point((int)x,(int)y);
    }


    /** the ransac algorithm
     *
     * @param points
     * @return
     */
    public Line2 ransac(ArrayList<Point> points, int iterations) {
        Line2 tmpmodel;
        Line2 model = new Line2();
        ArrayList<Point> maybe_points = new ArrayList<>();
        ArrayList<Point> test_points = new ArrayList<>();
        Point p0;
        Point p1;
        int r0,r1;
        int i,j;
        double dist;
        int max = 0;
        Random random = new Random();

        //Perform RANSAC iterations n times!~~~~
        for(i=0;i < iterations; i++){
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

            //find a lines model for the randomly selected points
            tmpmodel = new Line2(maybe_points.get(0), maybe_points.get(1));

            //find orthogonal lines to the model for all given points
            for(j=0;j < points.size()-2;j++){

                p0 = test_points.get(j);

                //find an intercept point of the model
                p1 = findIntercept(tmpmodel, p0);

                // distance from point to the model
                dist = Math.pow(Math.pow((p1.x - p0.x),2) + Math.pow((p1.y - p0.y),2),.5);

                // check whether it's an inlier or not
                if(dist < Driver.THRESHOLD) {
                    tmpmodel.fit++;
                }
            }
            if(tmpmodel.fit >= max){
                max = tmpmodel.fit;
                model = tmpmodel;
            }
        }

        return model;
    }

    /**
     * compute the ransac algorithm
     */
    public abstract Line2 compute(ArrayList<Point> points);
}