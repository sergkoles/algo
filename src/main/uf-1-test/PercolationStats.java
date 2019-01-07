import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {

    private double [] thresholds;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        thresholds = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            simulate(p, n);
            thresholds[i] = (double) p.numberOfOpenSites()/(n*n);
        }
    }

    private void simulate(Percolation p, int n) {
        while (!p.percolates()) {
            int row;
            int col;
            do {
                row = StdRandom.uniform(1, n + 1);
                col = StdRandom.uniform(1, n + 1);
            } while (p.isOpen(row, col));
            p.open(row, col);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(thresholds);
    }

    public double stddev() {
        return StdStats.stddev(thresholds);
    }                        // sample standard deviation of percolation threshold

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - 1.96*stddev()/Math.sqrt(thresholds.length);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + 1.96*stddev()/Math.sqrt(thresholds.length);
    }

    public static void main(String[] args) {
        Stopwatch sw = new Stopwatch();
        PercolationStats ps = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        System.out.printf("mean : %f%n", ps.mean());
        System.out.printf("stddev : %f%n", ps.stddev());
        System.out.printf("95%% confidence : [%f, %f]%n", ps.confidenceLo(), ps.confidenceHi());
        System.out.println("Time : " + sw.elapsedTime());
    }
}
