package org.sk.algo.unionfind;

import java.util.Random;


/**
 * TASK
 * Union-find with specific canonical element. Add a method \mathtt{find()}find() to the union-find data type so that \mathtt{find(i)}find(i) returns the largest element in the connected component containing ii. The operations, \mathtt{union()}union(), \mathtt{connected()}connected(), and \mathtt{find()}find() should all take logarithmic time or better.
 * For example, if one of the connected components is \{1, 2, 6, 9\}{1,2,6,9}, then the \mathtt{find()}find() method should return 99 for each of the four elements in the connected components.
 */
public class UnionFind {

    private int [] nodes;
    private int [] maxnode;

    private UnionFind(int totalNodes) {
        nodes = new int[totalNodes];
        maxnode = new int[totalNodes];
        for (int i=0; i < totalNodes; i++) {
            nodes[i] = i;
            maxnode[i] = i;
        }
    }

    /**
     * Connects two components of element id1 and element id2.
     */
    private void union(int id1, int id2) {
        int root1 = root(id1);
        int root2 = root(id2);
        nodes[root1] = root2;
    }

    /**
     * Returns the root element of the component
     */
    private int root(int el) {
        int max = maxnode[el];
        int i = el;
        while (nodes[i] != i) {
            nodes[i] = nodes[nodes[i]];
            i = nodes[i];
            if (i > max) max = i;
        }
        if (maxnode[i] < max) maxnode[i] = max; // save extremum of the component for root node el i

        // 2nd loop for tree flattening (optimization of algo, optional)
        int j = el;
        while (nodes[j] != j) {
            int next = nodes[j];
            nodes[j] = i;
            j = next;
        }

        return i;
    }

    /**
     * Returns true if two nodes are in the same component
     */
    private boolean connected(int id1, int id2) {
        return root(id1) == root(id2);
    }

    /**
     * Returns largest element in the connected component to id element
     */
    private int find(int id) {
        return maxnode[root(id)];
    }

    public static void main(String[] args) {
        int totalNodes = 50;
        UnionFind uf = new UnionFind(totalNodes);

        //simple test: connect all nodes with each other and check that the maximum node will be after find() call
        Random rnd = new Random();
        for (int i = 0; i < 100000000; i++) {
            int q = rnd.nextInt(totalNodes);
            int p = rnd.nextInt(totalNodes);
            if (!uf.connected(q, p)) {
                uf.union(q, p);
            }
        }
        System.out.println("Max element in component for node 29 : " + uf.find(29)); // 49
    }
}
