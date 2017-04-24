import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
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
        if(p == null) throw new NullPointerException();
        root = put(root, p.x(), p.y());
    } // add the point to the set (if it is not already in the set)

    public boolean contains(Point2D p) {
        if(p == null) throw new NullPointerException();
        return contains(root, p.x(), p.y());
    } // does the set contain point p?

    public void draw() {
        draw(root);
    } // draw all points to standard draw

    public Iterable<Point2D> range(RectHV rect) {
        if(rect == null) throw new NullPointerException();
        List<Point2D> resultList = new ArrayList<Point2D>();
        resultList = range(rect, root, resultList);
        return resultList;
    } // all points that are inside the rectangle

    public Point2D nearest(Point2D p) {
        if(p == null) throw new NullPointerException();
        Point2D nearest = null;
        Double minDistance = Double.POSITIVE_INFINITY;

        return nearest;
    } // a nearest neighbor in the set to point p; null if the set is empty

    private int size(Node o) {
        if (o == null) return 0;
        return o.n;
    }

    private boolean contains(Node o, double x, double y) {
        if (o == null) return false;
        int cmp = 0;
        if (o.vertical) {
            cmp = Double.compare(x, o.x);
        } else {
            cmp = Double.compare(y, o.y);
        }
        if (cmp < 0) return contains(o.left, x, y);
        else if (cmp > 0) return contains(o.right, x, y);
        else return true;
    }

    private void draw(Node o) {
        if(o == null) return;
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 1);
        StdDraw.setYscale(0, 1);
        draw(o.left);
        StdDraw.setPenColor(Color.black);
        StdDraw.filledCircle(o.x, o.y, 0.005);
//        if(o.vertical) {
//            StdDraw.setPenColor(Color.red);
//            if (o.parent == null ) StdDraw.line(o.x, 0, o.x, 1);
//            else {
//                if (o.y < o.parent.y) StdDraw.line(o.x, 0, o.x, o.parent.y);
//                else StdDraw.line(o.x, o.parent.y, o.x, 1);
//            }
//        } else {
//            StdDraw.setPenColor(Color.blue);
//            if (o.parent == null ) StdDraw.line(0, o.y, 1, o.y);
//            else {
//                if (o.x < o.parent.x) StdDraw.line(0, o.y, o.parent.x, o.y);
//                else StdDraw.line(o.parent.x, o.y, 1, o.y);
//            }
//        }
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
            o.left.parent = o;
        } else if (cmp > 0) {
            o.right = put(o.right, x, y);
            o.right.vertical = !o.vertical;
            o.right.parent = o;
        }
        o.n = size(o.left) + size(o.right) + 1;
        return o;
    }

    private List<Point2D> range(RectHV rect, Node o, List<Point2D> result) {
        if (o == null) return result;
        if (o.vertical) {
            if (rect.xmax() < o.x) result = range(rect, o.left, result);
            else if (rect.xmin() > o.x) result = range(rect, o.right, result);
            else {
                Point2D p = new Point2D(o.x, o.y);
                if (rect.contains(p)) {
                    result.add(p);
                }
                result = range(rect, o.left, result);
                result = range(rect, o.right, result);
            }
        } else {
            if (rect.ymax() < o.y) result = range(rect, o.left, result);
            else if (rect.ymin() > o.y) result = range(rect, o.right, result);
            else {
                Point2D p = new Point2D(o.x, o.y);
                if (rect.contains(p)) {
                    result.add(p);
                }
                result = range(rect, o.left, result);
                result = range(rect, o.right, result);
            }
        }

        return result;
    }

    private Point2D nearest(Point2D p, Node o, double minDistance) {
        Point2D q = new Point2D(o.x, o.y);
        double distance = q.distanceTo(q);
        if (distance < minDistance) minDistance = distance;
        if (o.vertical) {

        } else {

        }
        return q;
    }

    private class Node {
        double x;
        double y;
        Node left;
        Node right;
        Node parent;
        int n;
        boolean vertical;

        public Node(double x, double y, int n) {
            this.x = x;
            this.y = y;
            left = null;
            right = null;
            parent = null;
            this.n = n;
            vertical = true;
        }
    }

    public static void main(String[] args) {
        KdTree kdTree = new KdTree();
        String filename = args[0];
        In in = new In(filename);
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdTree.insert(p);
        }

//        StdOut.println(kdTree.contains(new Point2D(0.289063, 0.761719)));
//        StdOut.println(kdTree.contains(new Point2D(0.5, 0.5)));
        kdTree.draw();
//        kdTree.range(new RectHV(0.1, 0.5, 0.15, 0.8));
    }
}
