import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;


public class PercolationStats {
    private double[] expData;

    // perform T independent computational experiments on an N-by-N grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }

        expData = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            int countOpen = 0;
            while (!p.percolates()) {
                int openi = StdRandom.uniform(1, n + 1);
                int openj = StdRandom.uniform(1, n + 1);
                while (p.isOpen(openi, openj)) {
                    openi = StdRandom.uniform(1, n + 1);
                    openj = StdRandom.uniform(1, n + 1);
                }
                p.open(openi, openj);
                countOpen++;
            }
            expData[i] = (double) countOpen / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(expData);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(expData);
    }

    // returns lower bound of the 95% confidence interval
    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(expData.length);
    }

    // returns upper bound of the 95% confidence interval
    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(expData.length);
    }

    // test client, described below
    public static void main(String[] args) {
        PercolationStats pStats
                = new PercolationStats(StdIn.readInt(), StdIn.readInt());
        StdOut.printf("mean                    = %.16f%n", pStats.mean());
        StdOut.printf("stddev                  = %.16f%n", pStats.stddev());
        StdOut.printf("95%% confidence interval = %.16f, %.16f",
                pStats.confidenceLo(), pStats.confidenceHi());
    }
}