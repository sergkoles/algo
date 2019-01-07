import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private static final int OPEN = 1;
    private static final int BLOCKED = 0;

    private int gridSize;
    private int [] grid;
    private int openSites;
    private WeightedQuickUnionUF uf;
    private int topEmitterIdx;
    private int botEmitterIdx;


    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("Incorrect grid size");
        gridSize = n;
        grid = new int [gridSize * gridSize];
        for (int i = 0; i < grid.length; i++) {
            grid[i] = BLOCKED;
        }
        openSites = 0;
        uf = new WeightedQuickUnionUF(gridSize*gridSize + 2);
        topEmitterIdx = gridSize*gridSize;
        botEmitterIdx = gridSize*gridSize + 1;
    }

    private int coordToIdx(int row, int col) {
        validateCoord(row, col);
        int rowShift = (row-1) * gridSize;
        int colShift = col % (gridSize + 1);
        return colShift + rowShift - 1;
    }

    private void validateCoord(int row, int col) {
        if (row < 1 || col < 1 || row > gridSize || col > gridSize) throw new IllegalArgumentException("Wrong argument");
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        int idx = coordToIdx(row, col);
        grid[idx] = OPEN;
        if (row + 1 <= gridSize) {
            if (isOpen(row+ 1, col)) uf.union(idx, coordToIdx(row+1, col));
        } else {
            uf.union(idx, botEmitterIdx);
        }
        if (row - 1 > 0) {
            if (isOpen(row - 1, col)) uf.union(idx, coordToIdx(row - 1, col));
        } else {
            uf.union(idx, topEmitterIdx);
        }
        if (col + 1 <= gridSize && isOpen(row, col + 1)) uf.union(idx, coordToIdx(row, col + 1));
        if (col - 1 > 0 && isOpen(row, col -1)) uf.union(idx, coordToIdx(row, col - 1));

        openSites++;
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        return grid[coordToIdx(row, col)] == OPEN ? true : false;
    }

    // number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        return uf.connected(topEmitterIdx, coordToIdx(row, col));
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.connected(topEmitterIdx, botEmitterIdx);
    }

    // test client (optional)
    /* public static void main(String[] args) {} */

}
