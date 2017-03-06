import edu.princeton.cs.algs4.*;

/**
 * Created by wadey on 2/26/2017.
 */
public class GeometricObjects {

    public static void main(String[] args) {
        double xlo = Double.parseDouble(args[0]);
        double xhi = Double.parseDouble(args[1]);
        double ylo = Double.parseDouble(args[2]);
        double yhi = Double.parseDouble(args[3]);
        int T = Integer.parseInt(args[4]);

        Interval1D xInterval = new Interval1D(xlo, xhi);
        Interval1D yInterval = new Interval1D(ylo, yhi);
        Interval2D box = new Interval2D(xInterval, yInterval);

        box.draw();

        Counter counter = new Counter("hits");
        for(int i = 0; i < T; i++) {
            double x = StdRandom.uniform();
            double y = StdRandom.uniform();
            Point2D p = new Point2D(x, y);

            if (box.contains(p)) {
                counter.increment();
            } else {
                p.draw();
            }
        }
        StdOut.println(counter);
        StdOut.printf("box area = %.2f\n", box.area());
    }
}

class Counter {

    private int val;
    private String id;

    public Counter(String id) {
        this.id = id;
        this.val = 0;
    }

    public void increment() {
        val++;
    }

    public int tally() {
        return this.val;
    }

    @Override
    public String toString() {
        return this.id + ": " + tally();
    }
}
