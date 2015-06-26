/**
 * 
 *  Author:        Akshay Goyal
 *  Written:       6/25/2015
 *  Last updated:  6/25/2015
 *
 *  Compilation:   javac PercolationStats.java
 *  Execution	   java PercolationStats 100 200
 *
 *	Tests he performance of the Percolation class
 *  
 */

public class PercolationStats {

	private int times;				// total number of computations
	private double fraction[];		//array having fraction values of each computation
	
	
	/** perform T independent experiments on an N-by-N grid
	 * 
	 * @param N		size of the grid
	 * @param T		total number of computations
	 */
	public PercolationStats(int N, int T) {

		if (N < 1 || T < 1)
			throw new IllegalArgumentException("Use values graeter than 0");
		
		times = T;
		fraction = new double[times];
		
		for (int t = 0; t < times; t++) {
			Percolation p = new Percolation(N);
			int openedSites = 0;
			while (!p.percolates()) {
				int i = StdRandom.uniform(1, N + 1);
				int j = StdRandom.uniform(1, N + 1);
				if (!p.isOpen(i, j)) {
					p.open(i, j);
					++openedSites;
				}
			}
			fraction[t] = (double) openedSites / (N * N);
		}
	}

	/** sample mean of percolation threshold
	 * 
	 * @return	the  mean of all the computations
	 */
	public double mean() {
		return StdStats.mean(fraction);
	}

	/** sample standard deviation of percolation threshold
	 * 
	 * @return	the standard deviation of all computations
	 */
	public double stddev() {
		return (StdStats.stddev(fraction));
	}

	/** low end point of 95% confidence interval
	 * 
	 * @return low end point of 95% confidence interval
	 */
	public double confidenceLo() {
		return (mean() - (1.96 * stddev() / Math.sqrt(times))); 
	}

	/** high end point of 95% confidence interval
	 * 
	 * @return high end point of 95% confidence interval
	 */
	public double confidenceHi() {
		return (mean() + (1.96 * stddev() / Math.sqrt(times)));
	}

	// test client
	public static void main(String[] args) {

		int N = Integer.parseInt(args[0]);
		int T = Integer.parseInt(args[1]);
		PercolationStats ps = new PercolationStats(N, T);
		
		String confidence = ps.confidenceLo() + ", " + ps.confidenceHi();
        StdOut.println("mean                    = " + ps.mean());
        StdOut.println("stddev                  = " + ps.stddev());
        StdOut.println("95% confidence interval = " + confidence);
	}

}
