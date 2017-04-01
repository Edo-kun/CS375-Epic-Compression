/**
 * Created by Areito on 4/1/2017.
 */
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;

class Drawer extends JFrame{

    ArrayList<Point> points;
    Line2D line;

    public Drawer(int x,int y){
        JPanel panel=new JPanel();
        getContentPane().add(panel);
        setSize(x,y);
    }

    public void setPoints(ArrayList<Point> points) {
        this.points = points;
    }

    public void setLine(Point p0,Point p1) {
        this.line = new Line2D.Double(p0.getX(),p0.getY(),p1.getX(),p1.getY());
    }

    public void paint(Graphics g) {
        super.paint(g);  // fixes the immediate problem.
        Graphics2D g2 = (Graphics2D) g;

        points.forEach(e -> {g2.drawOval((int)e.getX(),(int)e.getY(),2,2);});
        g2.setStroke(new BasicStroke(50));
        g2.setColor(Color.RED);
        g2.draw(line);
    }
}