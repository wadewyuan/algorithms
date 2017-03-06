package com.cousera.algorithm.unionfind;

/**
 * Created by wadey on 2/25/2017.
 */
public class QuickUnion {
    private int[] id;

    /**
     * initialize the object set, each object would be isolated at the very beginning, so we simply assign unique values to each object
     * N array accesses
     *
     * @param N count of objects
     */
    public QuickUnion(int N) {
        id = new int[N];

        // In quick union, the value of array items represents its parent node in a <strong>tree</strong>
        for(int i = 0; i < N; i++) {
            id[i] = i;
        }
    }

    /**
     * A root node's parent is itself, so continue to search up until i equals id[i]
     *
     * @param i
     * @return
     */
    private int root(int i) {
        while (i != id[i]) {
            i = id[i];
        }
        return i;
    }

    /**
     * Returns true while p and q have the same root
     *
     * @param p
     * @param q
     * @return
     */
    public boolean connected(int p, int q) {
        return root(p) == root(q);
    }

    /**
     * Just move p's root as a child of q's root.
     *
     * @param p
     * @param q
     */
    public void union(int p, int q) {
        int i = root(p);
        int j = root(q);

        id[i] = j;
    }
}
