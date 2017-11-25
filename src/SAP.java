import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;
import java.util.List;

public class SAP {

    private Digraph g;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        this.g = new Digraph(G);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(g, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(g, w);
        int distance = -1;
        for (int i = 0; i < g.V(); i++) {
            if (bfsV.hasPathTo(i) && bfsW.hasPathTo(i)) {
                if (distance < 0 || bfsV.distTo(i) + bfsW.distTo(i) < distance) {
                    distance = bfsV.distTo(i) + bfsW.distTo(i);
                }
            }
        }
        return distance;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(g, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(g, w);
        int distance = Integer.MAX_VALUE;
        int ancestor = -1;
        for (int i = 0; i < g.V(); i++) {
            if (bfsV.hasPathTo(i) && bfsW.hasPathTo(i)) {
                if (bfsV.distTo(i) + bfsW.distTo(i) < distance) {
                    distance = bfsV.distTo(i) + bfsW.distTo(i);
                    ancestor = i;
                }
            }
        }
        return ancestor;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if(v == null || w == null) throw new IllegalArgumentException();

        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(g, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(g, w);
        int distance = -1;
        for (int i = 0; i < g.V(); i++) {
            if (bfsV.hasPathTo(i) && bfsW.hasPathTo(i)) {
                if (distance < 0 || bfsV.distTo(i) + bfsW.distTo(i) < distance) {
                    distance = bfsV.distTo(i) + bfsW.distTo(i);
                }
            }
        }
        return distance;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if(v == null || w == null) throw new IllegalArgumentException();

        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(g, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(g, w);
        int distance = Integer.MAX_VALUE;
        int ancestor = -1;
        for (int i = 0; i < g.V(); i++) {
            if (bfsV.hasPathTo(i) && bfsW.hasPathTo(i)) {
                if (bfsV.distTo(i) + bfsW.distTo(i) < distance) {
                    distance = bfsV.distTo(i) + bfsW.distTo(i);
                    ancestor = i;
                }
            }
        }
        return ancestor;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);

//        List<Integer> vArr = Arrays.asList(3, 7, 8), wArr = Arrays.asList(9, 10);
//        int length = sap.length(vArr, wArr);
//        int ancestor = sap.ancestor(vArr, wArr);
//        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);

        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}