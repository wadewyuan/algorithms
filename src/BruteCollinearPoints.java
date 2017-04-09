import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wadey on 3/18/2017.
 */
public class BruteCollinearPoints {

    private List<LineSegment> lineSegmentList;

    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new NullPointerException();
//        if (points.length < 4) throw new IllegalArgumentException();

        lineSegmentList = new ArrayList<LineSegment>();
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                for (int k = j + 1; k < points.length; k++) {
                    for (int l = k + 1; l < points.length; l++) {
                        if (points[i] == null || points[j] == null || points[k] == null || points[l] == null)
                            throw new NullPointerException();

                        if (points[i].slopeTo(points[j]) == points[j].slopeTo(points[k]) &&
                                points[j].slopeTo(points[k]) == points[k].slopeTo(points[l])) {
                            Point[] tmpPoints = new Point[]{points[i], points[j], points[k], points[l]};
                            Arrays.sort(tmpPoints);
                            lineSegmentList.add(new LineSegment(tmpPoints[0], tmpPoints[3]));
                        }
                    }
                }
            }
        }
    } // finds all line segments containing 4 points

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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
