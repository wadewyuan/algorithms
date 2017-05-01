import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wadey on 4/24/2017.
 */
public class PointSET {

    private SET<Point2D> points;

    public PointSET() {
        points = new SET<Point2D>();
    } // construct an empty set of points

    public boolean isEmpty() {
        return points.isEmpty();
    } // is the set empty?

    public int size() {
        return points.size();
    } // number of points in the set

    public void insert(Point2D p) {
        if (p == null) throw new NullPointerException();
        points.add(p);
    } // add the point to the set (if it is not already in the set)

    public boolean contains(Point2D p) {
        if (p == null) throw new NullPointerException();
        return points.contains(p);
    } // does the set contain point p?

    public void draw() {
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 1);
        StdDraw.setYscale(0, 1);
        for (Point2D p : points) {
            p.draw();
        }
        StdDraw.show();
    } // draw all points to standard draw

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new NullPointerException();
        List<Point2D> resultList = new ArrayList<Point2D>();
        for (Point2D p : points) {
            if (rect.contains(p)) {
                resultList.add(p);
            }
        }
        return resultList;
    } // all points that are inside the rectangle

    public Point2D nearest(Point2D p) {
        if (p == null) throw new NullPointerException();
        Point2D nearest = null;
        double minDistance = Double.POSITIVE_INFINITY;
        for (Point2D q : points) {
            double d = p.distanceTo(q);
            if (d < minDistance) {
                minDistance = d;
                nearest = q;
            }
        }
        return nearest;
    } // a nearest neighbor in the set to point p; null if the set is empty

    public static void main(String[] args) {
        PointSET pointSET = new PointSET();
        pointSET.insert(new Point2D(0.5, 0.5));
        pointSET.insert(new Point2D(0.6, 0.4));
        pointSET.insert(new Point2D(0.9, 0.4));
        pointSET.draw();
    } // unit testing of the methods (optional)
}
