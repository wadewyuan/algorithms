import edu.princeton.cs.algs4.*;

/**
 * TODO: Backwash not handled
 */
public class Percolation {

    private int N;
    private int[] sites;
    private WeightedQuickUnionUF uf;
    private int openSites;

    public Percolation(int n) {

        if (n <= 0) throw new IllegalArgumentException("Grid size can't be zero or negative");

        openSites = 0;
        N = n;
        sites = new int[N * N];
        uf = new WeightedQuickUnionUF(N * N + 2);
        for(int i = 0; i < N * N; i++) {
            sites[i] = 0; // 0 stands for BLOCKED site
        }

        // connect the virtual top site with the first row, and the virtual bottom site with last row
        for(int i = 0; i < N; i++) {
            uf.union(0, i + 1);
            uf.union(N * N + 1, N * N - i);
        }

    } // create n-by-n grid, with all sites blocked

    private int getIndex(int row, int col) {
        if(row < 1 || row > N || col < 1 || col > N) {
            throw new ArrayIndexOutOfBoundsException("Out of bound");
        } else {
            return (row - 1) * N + (col - 1);
        }
    }

    public void open(int row, int col) {

        if(!isOpen(row, col)) {
            int idx = getIndex(row, col);
            sites[idx] = 1;

            // check neighbor sites, if any of them are open, union with this one
            if(row > 1 && isOpen(row - 1, col)) uf.union(idx + 1, idx + 1 - N);
            if(row < N && isOpen(row + 1, col)) uf.union(idx + 1, idx + 1 + N);
            if(col > 1 && isOpen(row, col - 1)) uf.union(idx + 1, idx);
            if(col < N && isOpen(row, col + 1)) uf.union(idx + 1, idx + 2);

            openSites++;
        }
    }    // open site (row, col) if it is not open already

    public boolean isOpen(int row, int col) {
        int idx = getIndex(row, col);
        return sites[idx] == 1;
    } // is site (row, col) open?

    public boolean isFull(int row, int col) {
        int idx = getIndex(row, col);
        return uf.connected(0, idx + 1);
    }  // is site (row, col) full?

    public int numberOfOpenSites() {
        return openSites;
    } // number of open sites

    public boolean percolates() {
        return uf.connected(0, N * N + 1);
    } // does the system percolate?

    public static void main(String[] args) {

    }  // test client (optional)
}
