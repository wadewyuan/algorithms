package com.cousera.algorithm.unionfind;

/**
 * Created by wadey on 2/25/2017.
 */
public class WeightedQuickUnion {
    private int[] id;
    private int[] sz;

    /**
     * initialize the object set, each object would be isolated at the very beginning, so we simply assign unique values to each object
     * N array accesses
     *
     * @param N count of objects
     */
    public WeightedQuickUnion(int N) {
        id = new int[N]; // value of id array represents its parent node in a <strong>tree</strong>
        sz = new int[N]; // value of sz array represents the size of tree rooted with "n" (number of nodes)

        for(int i = 0; i < N; i++) {
            id[i] = i;
            sz[i] = 1;
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
            id[i] = id[id[i]]; // flatten the tree during the searching, by point current node to it's grand-parent
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
        if(i == j) return;

        if(sz[i] < sz[j]) {
            id[i] = j;
            sz[j] += sz[i];
        } else {
            id[j] = i;
            sz[i] += sz[j];
        }
    }
}
