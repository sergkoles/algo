package org.sk.algo.unionfind;


/**
 * TASK
 * Union-find with specific canonical element. Add a method find() to the union-find data type so that find(i) returns the largest element in the connected component containing ii. The operations, union(), connected(), and find() should all take logarithmic time or better.
 * For example, if one of the connected components is {1,2,6,9}, then the find() method should return 9 for each of the four elements in the connected components.
 */

/**
 * Thoughts:
 * Not optimal solution, test shows that for right unions the complexity for getting root is O(N), and the tree is a linked list
 * Alternatively we could use flattening by set size, but in this case additional memory required.
 */
public class UnionFindTaskMax {

    int [] nodes;

    public UnionFindTaskMax(int totalNodes) {
        nodes = new int[totalNodes];
        for (int i=0; i < totalNodes; i++) {
            nodes[i] = i;
        }
    }

    /**
     * Connects two components of element id1 and element id2.
     */
    public void union(int id1, int id2) {
        int root1 = root(id1);
        int root2 = root(id2);
        if (root1 > root2) {
            nodes[root2] = root1;
            flatten(id1, root1);
            flatten(id2, root1);
        } else {
            nodes[root1] = root2;
            flatten(id1, root2);
            flatten(id2, root2);
        }

    }

    private void flatten(int j, int root) {
        while (nodes[j] != j) {
            int next = nodes[j];
            nodes[j] = root;
            j = next;
        }
    }


    /**
     * Returns the root element of the component
     */
    private int root(int el) {
        int i = el;
        while (nodes[i] != i) {
            i = nodes[i];
        }
        return i;
    }

    /**
     * Returns true if two nodes are in the same component
     */
    public boolean connected(int id1, int id2) {
        return root(id1) == root(id2);
    }

    /**
     * Returns largest element in the connected component to id element
     */
    public int find(int id) {
        return root(id);
    }

}
