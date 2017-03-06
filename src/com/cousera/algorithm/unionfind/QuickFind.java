package com.cousera.algorithm.unionfind;

/**
 * Created by wadey on 2/25/2017.
 */
public class QuickFind {
    private int[] id;

    /**
     * initialize the object set, each object would be isolated at the very beginning, so we simply assign unique values to each object
     * N array accesses
     *
     * @param N count of objects
     */
    public QuickFind(int N) {
        id = new int[N];

        for(int i = 0; i < N; i++) {
            id[i] = i;
        }
    }

    /**
     * Returns true when two objects have the same value, meaning they are in the same component
     * 2 array accesses
     *
     * @param p
     * @param q
     * @return
     */
    public boolean connected(int p, int q) {
        return id[p] == id[q];
    }

    /**
     * Union two objects, not just this 2 objects, but also the components they belong to. Find all objects in p's component, and update their values to q's value
     * at most 2N + 2 array accesses
     *
     * @param p
     * @param q
     */
    public void union(int p, int q) {
        int pid = id[p];
        int qid = id[q];
        for(int i = 0; i < id.length; i++) {
            if(pid == id[i]) {
                id[i] = qid;
            }
        }
    }
}
