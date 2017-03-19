import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wadey on 3/18/2017.
 */
public class FastCollinearPoints {

    private List<LineSegment> lineSegmentList;

    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new NullPointerException();
        if(points.length < 4) throw new IllegalArgumentException();
        for(Point p : points) {
            if(p == null) throw new NullPointerException();
        }

        lineSegmentList = new ArrayList<>();
        for (int i = 0; i < points.length; i++) {
            Point p = points[i];
            Arrays.sort(points);
            Arrays.sort(points, p.slopeOrder());
            for(int j = 0; j < points.length; j++) {
                int n = 1;
                while(j + n < points.length && p.slopeTo(points[j]) == p.slopeTo(points[j + n])) {
                    n++;
                }
                if(n > 3) {
                    lineSegmentList.add(new LineSegment(p, points[j + n - 1]));
                    j += n;
                }
            }
        }


    } // finds all line segments containing 4 or more points
    public int numberOfSegments() {
        return lineSegmentList.size();
    } // the number of line segments
    public LineSegment[] segments() {
        return lineSegmentList.toArray(new LineSegment[numberOfSegments()]);
    } // the line segments

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
