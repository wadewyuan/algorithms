import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {

    private int n;
    private int[] sites; // 0 stands for BLOCKED site, 1 stands for OPEN site, 2 stands for FULL site
    private WeightedQuickUnionUF uf;
    private int openSites;

    public Percolation(int size) {

        if (size <= 0) throw new IllegalArgumentException("Grid size can't be zero or negative");

        openSites = 0;
        n = size;
        sites = new int[n * n];
        uf = new WeightedQuickUnionUF(n * n + 2);
        for (int i = 0; i < n * n; i++) {
            sites[i] = 0;
        }
    } // create n-by-n grid, with all sites blocked

    private int getIndex(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new ArrayIndexOutOfBoundsException("Out of bound");
        } else {
            return (row - 1) * n + (col - 1);
        }
    }

    public void open(int row, int col) {

        if (!isOpen(row, col)) {
            int idx = getIndex(row, col);

            sites[idx] = 1;

            // check neighbor sites, if any of them are open, union with this one
            if (row > 1 && sites[idx - n] > 0) uf.union(idx + 1, idx + 1 - n);
            if (row < n && sites[idx + n] > 0) uf.union(idx + 1, idx + 1 + n);
            if (col > 1 && sites[idx - 1] > 0) uf.union(idx + 1, idx);
            if (col < n && sites[idx + 1] > 0) uf.union(idx + 1, idx + 2);
//            if (row > 1 && isOpen(row - 1, col)) {
//                uf.union(idx + 1, idx + 1 - n);
//                    sites[idx] = isFull(row - 1, col) ? 2 : 1;
//            }
//            if (row < n && isOpen(row + 1, col)) {
//                uf.union(idx + 1, idx + 1 + n);
//                    sites[idx] = isFull(row + 1, col) ? 2 : 1;
//            }
//            if (col > 1 && isOpen(row, col - 1)) {
//                uf.union(idx + 1, idx);
//                    sites[idx] = isFull(row, col - 1) ? 2 : 1;
//            }
//            if (col < n && isOpen(row, col + 1)) {
//                uf.union(idx + 1, idx + 2);
//                    sites[idx] = isFull(row, col + 1) ? 2 : 1;
//            }

            if (row == 1) uf.union(0, idx + 1);         // union open sites in first row with the top virtual site
            if (row == n) uf.union(n * n + 1, idx + 1); // union open sites in last row with the bottom virtual site

            openSites++;
        }
    }    // open site (row, col) if it is not open already

    public boolean isOpen(int row, int col) {
        int idx = getIndex(row, col);
        return sites[idx] > 0;
    } // is site (row, col) open?

    // linear n in worst case
    public boolean isFull(int row, int col) {
//        int idx = getIndex(row, col);
//        return sites[idx] == 2;
        if (!isOpen(row, col)) return false;
        int idx = getIndex(row, col);
        return uf.connected(0, idx + 1);
//        for(int i = 0; i < n; i++) {
//            if(sites[i] == 2 && uf.connected(i + 1 , getIndex(row, col) + 1)) {
//                return true;
//            }
//        }
//        return false;
    }  // is site (row, col) full?

    public int numberOfOpenSites() {
        return openSites;
    } // number of open sites

    public boolean percolates() {
        return uf.connected(0, n * n + 1);
    } // does the system percolate?

    public static void main(String[] args) {
        In in = new In(args[0]);      // input file
        int n = in.readInt();         // n-by-n percolation system

        // repeatedly read in sites to open and draw resulting system
        Percolation perc = new Percolation(n);
        while (!in.isEmpty()) {
            int i = in.readInt();
            int j = in.readInt();
            perc.open(i, j);
        }
        perc.isFull(2, 4);
    }  // test client (optional)
}
