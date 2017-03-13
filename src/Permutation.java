import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by wadey on 3/13/2017.
 */
public class Permutation {
    public static void main(String[] args) {
        if(args.length >= 1) {
            int k = Integer.parseInt(args[0]);
            RandomizedQueue<String> rq = new RandomizedQueue();
            for(int n = 8; n > 0; n--) {
                if(rq.size() < k) {
                    rq.enqueue(StdIn.readLine());
                } else {
                    rq.dequeue();
                }
            }
            while(rq.size() > 0) {
                StdOut.println(rq.dequeue());
            }
        }
    }
}
