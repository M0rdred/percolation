/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Mordred
 */
public class Percolation {

    private final int BLOCKED = 0;
    private final int OPEN = 1;
    
    private int rows;

    int virtualTop;
    int virtualBottom;

    private int[][] grid;

    QuickFindUF quickFind;

    // create N-by-N grid, with all sites blocked
    public Percolation(int N) {
        if (N > 0) {

            quickFind = new QuickFindUF(N * N + 2);
            
            this.rows = N;
            
            virtualTop = 0;
            virtualBottom = quickFind.count() - 1;

            grid = new int[N][N];
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    grid[i][j] = BLOCKED;
                }
            }
        } else {
            throw new IllegalArgumentException("N can't be lower or equal to zero");
        }
    }

    // open site (row i, column j) if it is not already
    public void open(int i, int j) {
        if (!isOpen(i, j)) {
            grid[i - 1][j - 1] = OPEN;
            int linearSite = linearize(i, j);

            if (i == 1) {
                union(virtualTop, linearSite);
            } else if (i == this.rows) {
                union(virtualBottom, linearSite);
            } else {
                union(linearSite, linearize(i + 1, j));
                union(linearSite, linearize(i - 1, j));
                union(linearSite, linearize(i, j + 1));
                union(linearSite, linearize(i, j - 1));
            }
        }
    }

    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        return grid[i - 1][j - 1] == OPEN;
    }

    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {
        return quickFind.connected(virtualTop, linearize(i, j));
    }

    // does the system percolate?
    public boolean percolates() {
        return quickFind.connected(virtualTop, virtualBottom);
    }

    private void union(int p, int q) {
        System.out.println(p + " " + q);
        quickFind.union(p, q);
    }

    //transform 2D grid coordinates to 1D array indexes
    private int linearize(int row, int col) {
        return (row - 1) * 20 + col;
    }
}
