package org.sk.algo.unionfind;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnionFindTaskMaxTest {

    @Test
    void unionfindMainLogicTest() {
        int totalNodes = 6;
        UnionFindTaskMax uf = new UnionFindTaskMax(totalNodes);

        assertFalse(uf.connected(1,2));
        assertTrue(uf.connected(1,1));

        // Form {0,1,2,3,4,5}

        // {1,2}
        uf.union(1, 2);
        assertEquals(uf.find(1), 2);
        assertEquals(uf.find(2), 2);
        // {3,4}
        uf.union(3,4);
        assertEquals(uf.find(3), 4);
        assertEquals(uf.find(4), 4);
        // {3,4,5}
        uf.union(4,5);
        assertEquals(uf.find(3), 5);
        assertEquals(uf.find(4), 5);
        assertEquals(uf.find(5), 5);
        // {1,2,3,4,5}
        uf.union(1,5);
        assertEquals(uf.find(1), 5);
        assertEquals(uf.find(2), 5);
        assertEquals(uf.find(3), 5);
        assertEquals(uf.find(4), 5);
        assertEquals(uf.find(5), 5);

        assertFalse(uf.connected(0, 2));
        // {0,1,2,3,4,5} -> 5
        uf.union(0,2);
        assertTrue(uf.connected(0, 2));
        uf.union(1,5);

        assertEquals(uf.find(0), 5);
        assertEquals(uf.find(1), 5);
        assertEquals(uf.find(2), 5);
        assertEquals(uf.find(3), 5);
        assertEquals(uf.find(4), 5);
        assertEquals(uf.find(5), 5);
    }

    @Test
    public void unionfindBigSetRigth() {
        int totalNodes = 20;
        UnionFindTaskMax uf = new UnionFindTaskMax(totalNodes);

        for(int i = 0; i < totalNodes-1; i++) {
            uf.union(i, i+1);
            System.out.print(i + " + " + (i+1) +" | ");
        }

        printTree(uf.nodes);

        System.out.println();
        int depth = calcTreeDepth(uf.nodes, uf.find(1)) - 1;
        System.out.println("Max tree depth is " + depth);
    }

    @Test
    public void unionfindBigSetLeft() {
        int totalNodes = 20;
        UnionFindTaskMax uf = new UnionFindTaskMax(totalNodes);

        for(int i = totalNodes - 1; i > 0; i--) {
            uf.union(i, i-1);
            System.out.print(i + " + " + (i-1) +" | ");
        }

        printTree(uf.nodes);

        System.out.println();
        int depth = calcTreeDepth(uf.nodes, uf.find(1)) - 1;
        System.out.println("Max tree depth is " + depth);
    }

    private int calcTreeDepth(int[] tree, int root) {
        int maxLeafDepth = 0;
        for (int i = 0; i < tree.length; i++) {
            if (tree[i] == root && i != root) { //leaf
                int leafDepth = calcTreeDepth(tree, i);
                if (maxLeafDepth <  leafDepth ) maxLeafDepth = leafDepth;
            }
        }
        return maxLeafDepth + 1;
    }

    private void printTree(int [] tree) {
        System.out.print("\nTree array");
        System.out.print("\nIndexes: ");
        for (int i = 0; i < tree.length; i++) {
            System.out.printf("%1$2s | ", i);
        }
        System.out.print("\nLinks  : ");
        for (int i = 0; i < tree.length; i++) {
            System.out.printf("%1$2s | ", tree[i]);
        }
    }

}