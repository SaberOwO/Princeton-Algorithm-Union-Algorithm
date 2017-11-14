import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int size;
    private int openCount;
    private WeightedQuickUnionUF unionGridFull;
    private WeightedQuickUnionUF unionGridPerco;
    private boolean[] grid;

    // create N-by-N grid, with all sites blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();

        size = n;
        openCount = 0;
        unionGridFull = new WeightedQuickUnionUF(n * n + 1);
        unionGridPerco = new WeightedQuickUnionUF(n * n + 2);
        grid = new boolean[n * n];
    }

    // open site (row i, column j) if it is not already
    public void open(int i, int j) {
        if (!isOpen(i, j)) {
            openCount++;
            int index = findIndex(i, j);
            grid[index - 1] = true;
            if (i == 1) {
                unionGridFull.union(0, index);
                unionGridPerco.union(0, index);
            }

            if (size != 1) {
                if (i == 1) {
                    unionDown(i, j);
                } else if (i == size) {
                    unionUp(i, j);
                    unionGridPerco.union(index, size * size + 1);
                } else {
                    unionUp(i, j);
                    unionDown(i, j);
                }

                if (j == 1) {
                    unionRight(i, j);
                } else if (j == size) {
                    unionLeft(i, j);
                } else {
                    unionLeft(i, j);
                    unionRight(i, j);
                }
            } else {
                unionGridPerco.union(index, size * size + 1);
            }
        }
    }

    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        return grid[findIndex(i, j) - 1];
    }

    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {
        return unionGridFull.connected(0, findIndex(i, j));
    }

    // does the system percolate?
    public boolean percolates() {
        return unionGridPerco.connected(0, size * size + 1);
    }

    // number of open sites
    public int numberOfOpenSites() {
        return openCount;
    }

    private int findIndex(int i, int j) {
        if (i <= 0 || i > size || j <= 0 || j > size) {
            throw new IndexOutOfBoundsException();
        }
        return (i - 1) * size + j;
    }

    private void unionUp(int i, int j) {
        if (isOpen(i - 1, j)) {
            unionGridFull.union(findIndex(i, j),
                    findIndex(i - 1, j));
            unionGridPerco.union(findIndex(i, j),
                    findIndex(i - 1, j));
        }
    }

    private void unionDown(int i, int j) {
        if (isOpen(i + 1, j)) {
            unionGridFull.union(findIndex(i, j),
                    findIndex(i + 1, j));
            unionGridPerco.union(findIndex(i, j),
                    findIndex(i + 1, j));
        }
    }

    private void unionLeft(int i, int j) {
        if (isOpen(i, j - 1)) {
            unionGridFull.union(findIndex(i, j),
                    findIndex(i, j - 1));
            unionGridPerco.union(findIndex(i, j),
                    findIndex(i, j - 1));
        }
    }

    private void unionRight(int i, int j) {
        if (isOpen(i, j + 1)) {
            unionGridFull.union(findIndex(i, j),
                    findIndex(i, j + 1));
            unionGridPerco.union(findIndex(i, j),
                    findIndex(i, j + 1));
        }
    }
}
