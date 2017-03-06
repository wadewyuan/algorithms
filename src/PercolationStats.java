import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {

    private double results[];
    private double confidenceLevel = 1.96;
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
    }                          // sample mean of percolation threshold
    public double stddev() {
        if(sd == 0) {
            for (int i=0; i < results.length; i++) {
                sd = sd + Math.pow(results[i] - mean, 2);
            }
        }
        return sd;
    }                        // sample standard deviation of percolation threshold
    public double confidenceLo() {
        return mean() - confidenceLevel * stddev() / Math.sqrt(results.length);
    }                  // low  endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + confidenceLevel * stddev() / Math.sqrt(results.length);
    }                  // high endpoint of 95% confidence interval

    public static void main(String[] args) {
        Stopwatch stopwatch = new Stopwatch();
        PercolationStats percolationStats = new PercolationStats(1000, 1000);
        StdOut.printf("Mean                    = %f\n", percolationStats.mean());
        StdOut.printf("Standard deviation      = %f\n", percolationStats.stddev());
        StdOut.printf("95%% confidence interval = [%f, %f]\n", percolationStats.confidenceLo(), percolationStats.confidenceHi());
        StdOut.println(stopwatch.elapsedTime());
    }        // test client (described below)
}