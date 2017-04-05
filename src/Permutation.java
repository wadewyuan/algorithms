import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

/**
 * Created by wadey on 3/13/2017.
 */
public class Permutation {
    public static void main(String[] args) {
        if (args.length >= 1) {
            int k = Integer.parseInt(args[0]);
            RandomizedQueue<String> rq = new RandomizedQueue<String>();
            while (!StdIn.isEmpty()) {
                rq.enqueue(StdIn.readString());
            }
            while (k > 0) {
                StdOut.println(rq.dequeue());
                k--;
            }
        }
    }
}
