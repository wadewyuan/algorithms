import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wadey on 4/24/2017.
 */
public class KdTree {

    private Node root;

    public KdTree() {
        root = null;
    }

    public boolean isEmpty() {
        return size(root) == 0;
    } // is the set empty?

    public int size() {
        return size(root);
    } // number of points in the set

    public void insert(Point2D p) {
        if (p == null) throw new NullPointerException();
        root = put(root, p.x(), p.y());
    } // add the point to the set (if it is not already in the set)

    public boolean contains(Point2D p) {
        if (p == null) throw new NullPointerException();
        return contains(root, p.x(), p.y());
    } // does the set contain point p?

    public void draw() {
        draw(root);
    } // draw all points to standard draw

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new NullPointerException();
        List<Point2D> resultList = new ArrayList<Point2D>();
        resultList = range(rect, root, resultList);
        return resultList;
    } // all points that are inside the rectangle

    public Point2D nearest(Point2D p) {
        if (p == null) throw new NullPointerException();
        if (root == null) return null;
        Point2D nearest = new Point2D(root.x, root.y);
        nearest = nearest(p, root, nearest);
        return nearest;
    } // a nearest neighbor in the set to point p; null if the set is empty

    private int size(Node o) {
        if (o == null) return 0;
        return o.n;
    }

    private boolean contains(Node o, double x, double y) {
        if (o == null) return false;
        if (Double.compare(o.x, x) == 0 && Double.compare(o.y, y) == 0) return true;
        int cmp = 0;
        if (o.vertical) {
            cmp = Double.compare(x, o.x);
        } else {
            cmp = Double.compare(y, o.y);
        }
        if (cmp < 0) return contains(o.left, x, y);
        else return contains(o.right, x, y);
    }

    private void draw(Node o) {
        if (o == null) return;
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 1);
        StdDraw.setYscale(0, 1);
        draw(o.left);
        StdDraw.setPenColor(Color.black);
        StdDraw.filledCircle(o.x, o.y, 0.01);
        if (o.vertical) {
            StdDraw.setPenColor(Color.red);
            StdDraw.line(o.x, o.rect.ymin(), o.x, o.rect.ymax());
        } else {
            StdDraw.setPenColor(Color.blue);
            StdDraw.line(o.rect.xmin(), o.y, o.rect.xmax(), o.y);
        }
        draw(o.right);
        StdDraw.show();
    }

    private Node put(Node o, double x, double y) {
        if (o == null) return new Node(x, y, 1);
        int cmp = 0;
        if (o.vertical) {
            cmp = Double.compare(x, o.x);
        } else {
            cmp = Double.compare(y, o.y);
        }
        if (cmp < 0) {
            o.left = put(o.left, x, y);
            o.left.vertical = !o.vertical;
            if (o.vertical) {
                o.left.rect = new RectHV(o.rect.xmin(), o.rect.ymin(), o.x, o.rect.ymax());
            } else {
                o.left.rect = new RectHV(o.rect.xmin(), o.rect.ymin(), o.rect.xmax(), o.y);
            }
        } else if (Double.compare(o.x, x) != 0 || Double.compare(o.y, y) != 0) {
            o.right = put(o.right, x, y);
            o.right.vertical = !o.vertical;
            if (o.vertical) {
                o.right.rect = new RectHV(o.x, o.rect.ymin(), o.rect.xmax(), o.rect.ymax());
            } else {
                o.right.rect = new RectHV(o.rect.xmin(), o.y, o.rect.xmax(), o.rect.ymax());
            }
        }
        o.n = size(o.left) + size(o.right) + 1;
        return o;
    }

    private List<Point2D> range(RectHV rect, Node o, List<Point2D> result) {
        if (o == null) return result;
        Point2D p = new Point2D(o.x, o.y);
        if (rect.contains(p)) {
            result.add(p);
        }

        if (o.left != null && rect.intersects(o.left.rect)) result = range(rect, o.left, result);
        if (o.right != null && rect.intersects(o.right.rect)) result = range(rect, o.right, result);

        return result;
    }

    private Point2D nearest(Point2D p, Node o, Point2D q) {
        if (o == null) return q;
        Point2D tmp = new Point2D(o.x, o.y);
        double distanceSquare = p.distanceSquaredTo(tmp);
        double minDistanceSquare = p.distanceSquaredTo(q);
        if (distanceSquare < minDistanceSquare) {
            minDistanceSquare = distanceSquare;
            q = tmp;
        }
        if ((o.vertical && p.x() < o.x) || (!o.vertical && p.y() < o.y)) {
            // starting with left subtree
            if (o.left != null) q = nearest(p, o.left, q);
            if (o.right != null && minDistanceSquare >= pointDistanceSquaredToRect(p, o.right.rect)) {
                q = nearest(p, o.right, q);
            }
        } else if ((o.vertical && p.x() >= o.x) || (!o.vertical && p.y() >= o.y)) {
            // starting with right subtree
            if (o.right != null) q = nearest(p, o.right, q);
            if (o.left != null && minDistanceSquare >= pointDistanceSquaredToRect(p, o.left.rect)) {
                q = nearest(p, o.left, q);
            }
        }
        return q;
    }

    private double pointDistanceSquaredToRect(Point2D p, RectHV rect) {
        if (rect.contains(p)) return 0; // inside
        double distanceSquared = 0;
        if (p.x() > rect.xmin() && p.x() < rect.xmax()) {
            distanceSquared = p.y() > rect.ymax() ? p.y() - rect.ymax() : rect.ymin() - p.y(); // top or bottom
            distanceSquared = distanceSquared * distanceSquared;
        } else if (p.y() > rect.ymin() && p.y() < rect.ymax()) {
            distanceSquared = p.x() > rect.xmax() ? p.x() - rect.xmax() : rect.xmin() - p.x(); // left or right
            distanceSquared = distanceSquared * distanceSquared;
        } else if (p.x() <= rect.xmin() && p.y() >= rect.ymax())
            distanceSquared = p.distanceSquaredTo(new Point2D(rect.xmin(), rect.ymax())); // left upper
        else if (p.x() <= rect.xmin() && p.y() <= rect.ymin())
            distanceSquared = p.distanceSquaredTo(new Point2D(rect.xmin(), rect.ymin())); // left bottom
        else if (p.x() >= rect.xmax() && p.y() >= rect.ymax())
            distanceSquared = p.distanceSquaredTo(new Point2D(rect.xmax(), rect.ymax())); // right upper
        else if (p.x() >= rect.xmax() && p.y() <= rect.ymin())
            distanceSquared = p.distanceSquaredTo(new Point2D(rect.xmax(), rect.ymin())); // right bottom
        return distanceSquared;
    }

//    private int rectIntersects(RectHV rect, Node o) {
//        if (o.vertical) {
//            if (o.x >= rect.xmin() && o.x <= rect.xmax() )
//        }
//    }

    private static class Node {
        private double x;
        private double y;
        private Node left;
        private Node right;
        private RectHV rect;
        private int n;
        private boolean vertical;

        public Node(double x, double y, int n) {
            this.x = x;
            this.y = y;
            left = null;
            right = null;
            this.n = n;
            vertical = true;
            rect = new RectHV(0, 0, 1, 1);
        }
    }

    public static void main(String[] args) {
        KdTree kdTree = new KdTree();
        PointSET pointSET = new PointSET();
        String filename = args[0];
        In in = new In(filename);

//        kdTree.isEmpty();
//        kdTree.contains(new Point2D(0.027, 0.566));
//        kdTree.range(new RectHV(0.331, 0.687, 0.598, 0.695));
//        kdTree.contains(new Point2D(0.268, 0.083));
//        kdTree.nearest(new Point2D(0.553, 0.089));
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdTree.insert(p);
            pointSET.insert(p);
        }

//        StdOut.println(kdTree.contains(new Point2D(0.206107, 0.904508)));
        StdOut.println(kdTree.contains(new Point2D(0.5, 0.5)));
//        kdTree.insert(new Point2D(0.206107, 0.904508));
//        kdTree.draw();
//        kdTree.range(new RectHV(0.1, 0.5, 0.15, 0.8));
//        StdOut.println(kdTree.nearest(new Point2D(0.37, 0.71)));
//        StdOut.println(pointSET.nearest(new Point2D(0.37, 0.71)));

//        Point2D p = new Point2D(0.25, 0.5);
//        RectHV rectHV = new RectHV(0.25, 0.25, 0.75, 0.75);
//        StdOut.println(kdTree.pointDistanceSquaredToRect(p, rectHV));
    }
}
