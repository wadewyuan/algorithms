import edu.princeton.cs.algs4.Stack;

/**
 * Created by wadey on 4/15/2017.
 */
public class Board {

    private final int[][] tiles;
    private int hamming;
    private int manhattan;

    public Board(int[][] blocks) {
        if (blocks == null) throw new NullPointerException();
        tiles = blocks;
        hamming = -1;
        manhattan = -1;
    } // construct a board from an n-by-n array of blocks

    // (where blocks[i][j] = block in row i, column j)
    public int dimension() {
        return tiles.length;
    } // board dimension n

    public int hamming() {
        if (hamming < 0) {
            hamming = 0;
            for (int i = 0; i < tiles.length; i++) {
                for (int j = 0; j < tiles[i].length; j++) {
                    if (getManhattanDistance(i, j) > 0) {
                        hamming++;
                    }
                }
            }
        }
        return hamming;
    } // number of blocks out of place

    public int manhattan() {
        if (manhattan < 0) {
            manhattan = 0;
            for (int i = 0; i < tiles.length; i++) {
                for (int j = 0; j < tiles[i].length; j++) {
                    manhattan += getManhattanDistance(i, j);
                }
            }
        }
        return manhattan;
    } // sum of Manhattan distances between blocks and goal

    public boolean isGoal() {
        return manhattan() == 0;
    } // is this board the goal board?

    public Board twin() {
        int ai = 0, aj = 0;
        if (tiles[ai][aj] == 0) aj++;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                if (tiles[i][j] != 0 && tiles[i][j] != tiles[ai][aj]) {
                    return new Board(swapTwoBlocks(ai, aj, i, j));
                }
            }
        }
        return this;
    } // a board that is obtained by exchanging any pair of blocks

    public boolean equals(Object y) {
        if (this == y) return true;
        if (y == null) return false;
        if (this.getClass() != y.getClass()) return false;
        Board that = (Board) y;
        if (this.dimension() != that.dimension()) return false;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                if (tiles[i][j] != that.tiles[i][j]) {
                    return false;
                }
            }
        }
        return true;
    } // does this board equal y?

    public Iterable<Board> neighbors() {
        Stack<Board> neighbors = new Stack<Board>();
        outerLoop:
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                if (tiles[i][j] == 0) {
                    if (i > 0) neighbors.push(new Board(swapTwoBlocks(i, j, i - 1, j)));
                    if (j > 0) neighbors.push(new Board(swapTwoBlocks(i, j, i, j - 1)));
                    if (i < dimension() - 1) neighbors.push(new Board(swapTwoBlocks(i, j, i + 1, j)));
                    if (j < dimension() - 1) neighbors.push(new Board(swapTwoBlocks(i, j, i, j + 1)));
                    break outerLoop;
                }
            }
        }
        return neighbors;
    } // all neighboring boards

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(dimension() + "\n");
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    } // string representation of this board (in the output format specified below)

    private int getManhattanDistance(int i, int j) {
        int block = tiles[i][j];
        if (block == 0) return 0;
        int goalRow = (block - 1) / dimension();
        int goalCol = (block - 1) % dimension();
        return Math.abs(i - goalRow) + Math.abs(j - goalCol);
    } // manhattan distance of the block in row i, column j

    private int[][] swapTwoBlocks(int ai, int aj, int bi, int bj) {
        int[][] newTiles = new int[tiles.length][tiles[0].length];
        for (int i = 0; i < tiles.length; i++) {
            System.arraycopy(tiles[i], 0, newTiles[i], 0, tiles[i].length);
        }
        int tmp = newTiles[ai][aj];
        newTiles[ai][aj] = newTiles[bi][bj];
        newTiles[bi][bj] = tmp;
        return newTiles;
    } // swap the block in row ai, column aj with the block in row bi, bj, return new tiles array with swapped blocks


    public static void main(String[] args) {

    } // unit tests (not graded)
}
