import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {

    private int[] sites; // 0 stands for BLOCKED site, 1 stands for OPEN site
    private WeightedQuickUnionUF uf, ufAux;
    private int openSites;

    public Percolation(int n) {

        if (n <= 0) throw new IllegalArgumentException("Grid size can't be zero or negative");

        openSites = 0;
        sites = new int[n * n];
        uf = new WeightedQuickUnionUF(n * n + 2);
        ufAux = new WeightedQuickUnionUF(n * n + 1);
        for (int i = 0; i < n * n; i++) {
            sites[i] = 0;
        }
    } // create n-by-n grid, with all sites blocked

    private int getIndex(int row, int col) {
        int n = (int) Math.sqrt(sites.length);
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new ArrayIndexOutOfBoundsException("Out of bound");
        } else {
            return (row - 1) * n + (col - 1);
        }
    }

    private void unionBothUnionFind(int p, int q) {
        uf.union(p, q);
        ufAux.union(p, q);
    }

    public void open(int row, int col) {

        if (!isOpen(row, col)) {
            int n = (int) Math.sqrt(sites.length);
            int idx = getIndex(row, col);

            sites[idx] = 1;

            // check neighbor sites, if any of them are open, union with this one
            if (row > 1 && sites[idx - n] > 0) unionBothUnionFind(idx + 1, idx + 1 - n);
            if (row < n && sites[idx + n] > 0) unionBothUnionFind(idx + 1, idx + 1 + n);
            if (col > 1 && sites[idx - 1] > 0) unionBothUnionFind(idx + 1, idx);
            if (col < n && sites[idx + 1] > 0) unionBothUnionFind(idx + 1, idx + 2);

            if (row == 1) unionBothUnionFind(0, idx + 1); // union open sites in first row with the top virtual site
            if (row == n) uf.union(n * n + 1, idx + 1); // union open sites in last row with the bottom virtual site

            openSites++;
        }
    }    // open site (row, col) if it is not open already

    public boolean isOpen(int row, int col) {
        int idx = getIndex(row, col);
        return sites[idx] > 0;
    } // is site (row, col) open?

    public boolean isFull(int row, int col) {
        // A site is full when it's open AND it's connected with the top virtual site
        return isOpen(row, col) && ufAux.connected(0, getIndex(row, col) + 1);
    }  // is site (row, col) full?

    public int numberOfOpenSites() {
        return openSites;
    } // number of open sites

    public boolean percolates() {
        return uf.connected(0, sites.length + 1);
    } // does the system percolate?

    public static void main(String[] args) {

    }  // test client (optional)
}
