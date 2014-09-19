
public class PercolationStats {

    private Percolation perc;
    private int N;
    private int T;
    private int randX;
    private int randY;
    private double[] thresholdValues;

    // perform T independent computational experiments on an N-by-N grid
    public PercolationStats(int N, int T) {
        if (N > 0 || T > 0) {
            perc = new Percolation(N);
            this.T = T;
            this.N = N;
            thresholdValues = new double[T];
            calulateThresholdValues();
        } else {
            throw new IllegalArgumentException("N and T must be more than zero");
        }
    }

    private void calulateThresholdValues() {
        for (int i = 0; i < T; i++) {
            thresholdValues[i] = calculateOneThreshold();
        }
    }

    private double calculateOneThreshold() {
        double openSites = 0;
        perc = new Percolation(N);

        while (!perc.percolates()) {
            randX = StdRandom.uniform(1, N + 1);
            randY = StdRandom.uniform(1, N + 1);

            try {
                perc.open(randX, randY);
                openSites++;
            } catch (Exception e) {

            }
        }
        return openSites / (N * N);
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(thresholdValues);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(thresholdValues);
    }

    // returns lower bound of the 95% confidence interval
    public double confidenceLo() {
        return mean() - ((1.96 * stddev()) / Math.sqrt(T));
    }

    // returns upper bound of the 95% confidence interval
    public double confidenceHi() {
        return mean() + ((1.96 * stddev()) / Math.sqrt(T));
    }

    // test client, described below
    public static void main(String[] args) {

//        int N = Integer.parseInt(args[0]);
//        int T = Integer.parseInt(args[1]);
        int N = 5000;
        int T = 10;

        Stopwatch watch = new Stopwatch();
        PercolationStats pStats = new PercolationStats(N, T);
        
        System.out.println(watch.elapsedTime());
        System.out.println("mean = " + pStats.mean());
        System.out.println("stddec = " + pStats.stddev());
        System.out.println("95% confidence interval = "
                + pStats.confidenceLo()
                + ","
                + pStats.confidenceHi());
    }

}
