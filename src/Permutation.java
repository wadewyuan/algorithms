import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by wadey on 3/13/2017.
 */
public class Permutation {
    public static void main(String[] args) {
        if (args.length >= 1) {
            int k = Integer.parseInt(args[0]);
            RandomizedQueue<String> rq = new RandomizedQueue<String>();
            while (!StdIn.isEmpty()) {
                /*if (rq.size() < k) {
                    rq.enqueue(StdIn.readString());
                } else {
                    rq.dequeue();
                }*/
                rq.enqueue(StdIn.readString());
            }
            while (k > 0) {
                StdOut.println(rq.dequeue());
                k--;
            }
        }
    }
}
