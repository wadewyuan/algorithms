import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import java.util.Comparator;

/**
 * Created by wadey on 4/15/2017.
 */
public class Solver {

    private final Board board;
    private final Stack<Board> solutionStack;
    private int moves;
    private Solver prev;

    private Solver goal;

    public Solver(Board initial) {
        if (initial == null) throw new NullPointerException();
        moves = 0;
        prev = null;
        board = initial;
        solutionStack = new Stack<Board>();
        goal = null;
    } // find a solution to the initial board (using the A* algorithm)

    public boolean isSolvable() {
        Solver origin = new Solver(this.board);
        Solver twin = new Solver(this.board.twin());
        MinPQ<Solver> minPQ1 = new MinPQ<Solver>(manhattanComparator());
        MinPQ<Solver> minPQ2 = new MinPQ<Solver>(manhattanComparator());

        minPQ1.insert(origin);
        Solver current1 = minPQ1.delMin();
        minPQ2.insert(twin);
        Solver current2 = minPQ2.delMin();

        while (!current1.board.isGoal() && !current2.board.isGoal()) {
            current1 = aStarSearch(current1, minPQ1);
            current2 = aStarSearch(current2, minPQ2);
        }

        if(current1.board.isGoal()) {
            goal = current1;
            return true;
        } else {
            return false;
        }
    } // is the initial board solvable?

    public int moves() {
        if (moves == 0) solution();
        return moves;
    } // min number of moves to solve initial board; -1 if unsolvable

    public Iterable<Board> solution() {
        if (!isSolvable()) {
            moves = -1;
            return null;
        }

        moves = 0;
        while (goal.prev != null) {
            solutionStack.push(goal.board);
            goal = goal.prev;
            moves++;
        }
        solutionStack.push(board); // solution() starts with the initial board
        return solutionStack;
    } // sequence of boards in a shortest solution; null if unsolvable

    private Comparator<Solver> manhattanComparator() {
        return new Comparator<Solver>() {

            @Override
            public int compare(Solver x, Solver y) {
                int xPriority = x.moves + x.board.manhattan();
                int yPriority = y.moves + y.board.manhattan();
                return xPriority == yPriority ? 0 : (xPriority > yPriority ? 1 : -1);
            }
        };
    }

    private Solver aStarSearch(Solver searchNode, MinPQ<Solver> minPQ) {
        for (Board b : searchNode.board.neighbors()) {
            if (searchNode.prev == null || !searchNode.prev.board.equals(b)) {
                Solver s = new Solver(b);
                s.prev = searchNode;
                s.moves = searchNode.moves + 1;
                minPQ.insert(s);
            }
        }
        return minPQ.delMin();
    }

    public static void main(String[] args) {

    } // solve a slider puzzle (given below)
}
