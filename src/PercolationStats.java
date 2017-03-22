import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {

    private static final double CONFIDENCE_LEVEL = 1.96;

    private double results[];
    private double mean = 0, sd = 0;

    public PercolationStats(int n, int trials) {
        if(n <= 0 || trials <= 0) throw new IllegalArgumentException("Invalid inputs");
        this.results = new double[trials];

        for(int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            while(!percolation.percolates()) {
                percolation.open(StdRandom.uniform(1, n + 1), StdRandom.uniform(1, n + 1));
            }
            results[i] = (double) percolation.numberOfOpenSites() / (n * n);
        }
    }    // perform trials independent experiments on an n-by-n grid

    public double mean() {
        if(mean == 0){
            double total = 0;
            for(int i = 0; i < results.length; i++) {
                total += results[i];
            }
            mean = total / results.length;
        }
        return mean;
    } // sample mean of percolation threshold
    public double stddev() {
        if(sd == 0) {
            for (int i=0; i < results.length; i++) {
                sd = sd + Math.pow(results[i] - mean, 2);
            }
        }
        return sd;
    } // sample standard deviation of percolation threshold
    public double confidenceLo() {
        return mean() - CONFIDENCE_LEVEL * stddev() / Math.sqrt(results.length);
    } // low endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + CONFIDENCE_LEVEL * stddev() / Math.sqrt(results.length);
    } // high endpoint of 95% confidence interval

    public static void main(String[] args) {
        Stopwatch stopwatch = new Stopwatch();
        // t trials on the n-by-n percolation problem, default both t and n to 10
        int n = 10, t = 10;
        if(args.length >= 2) {
            n = Integer.parseInt(args[0]);
            t = Integer.parseInt(args[1]);
        }
        PercolationStats percolationStats = new PercolationStats(n, t);
        StdOut.printf("Mean                    = %f\n", percolationStats.mean());
        StdOut.printf("Standard deviation      = %f\n", percolationStats.stddev());
        StdOut.printf("95%% confidence interval = [%f, %f]\n", percolationStats.confidenceLo(), percolationStats.confidenceHi());
        StdOut.println(stopwatch.elapsedTime());
    }        // test client (described below)
}