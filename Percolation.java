/*------------------------------------------------------
 *  Author:        Akshay Goyal
 *  Written:       6/24/2015
 *  Last updated:  6/25/2015
 *
 *  Compilation:   javac Percolation.java
 *
 *  This class checks if the grid of size N, percolates.
 */

public class Percolation {

	private boolean[][] opened;			//contains the opened status of all elements 
	private int top = 0;				//virtual top
	private int bottom;					//virtual bottom
	private int size;					//size of the grid
	private WeightedQuickUnionUF uf;	//WeightedQuickUnionUF checks percolation
	private WeightedQuickUnionUF uf1;	//WeightedQuickUnionUF used in checking isFull(), to avoid backwash
	
	/** create N-by-N grid, with all sites blocked
	 * 
	 * @param N size of the grid
	 */
	public Percolation(int N) {

		if (N <= 0)
			throw new IllegalArgumentException();

		size = N;
		bottom = size * size + 1;
		uf = new WeightedQuickUnionUF(size * size + 2);
		uf1 = new WeightedQuickUnionUF(size * size + 1);
		opened = new boolean[size][size];		
	}

	/** open site (row i, column j) if it is not open already
	 * 
	 * @param i		row
	 * @param j		column
	 */
	public void open(int i, int j) {

		validate(i, j);
		opened[i - 1][j - 1] = true;
		
		if (i == 1) {
			uf.union(top, xyTo1D(i, j));
			uf1.union(top, xyTo1D(i, j));
		}
		if (i == size) {
			uf.union(bottom, xyTo1D(i, j));
		}
		if (i > 1 && isOpen(i - 1, j)) {
			uf.union(xyTo1D(i, j), xyTo1D(i - 1, j));
			uf1.union(xyTo1D(i, j), xyTo1D(i - 1, j));
		}
		if (i < size && isOpen(i + 1, j)) {
			uf.union(xyTo1D(i, j), xyTo1D(i + 1, j));
			uf1.union(xyTo1D(i, j), xyTo1D(i + 1, j));
		}
		if (j > 1 && isOpen(i, j - 1)) {
			uf.union(xyTo1D(i, j), xyTo1D(i, j - 1));
			uf1.union(xyTo1D(i, j), xyTo1D(i, j - 1));
		}
		if (j < size && isOpen(i, j + 1)) {
			uf.union(xyTo1D(i, j), xyTo1D(i, j + 1));
			uf1.union(xyTo1D(i, j), xyTo1D(i, j + 1));
		}
	}
	
	/** checks if the site is open
	 * 
	 * @param i 	row
	 * @param j		column
	 * @return	true if site is open else false
	 */
	public boolean isOpen(int i, int j) { // is site (row i, column j) open?

		validate(i, j);
		return opened[i - 1][j - 1];
	}
	
	/** checks if the site is full (connected to top)
	 * 
	 * @param i 	row
	 * @param j		column
	 * @return	true if site is full else false
	 */
	public boolean isFull(int i, int j) { // is site (row i, column j) full?
		
		validate(i, j);
		return (uf1.connected(top, xyTo1D(i, j)));
	}
	
	/** checks if the system percolates
	 * 
	 * @return true if percolates else false
	 */
	public boolean percolates() { // does the system percolate?
		
		return (uf.connected(top, bottom));
	}
	
	/** converts 2D coordinates into 1D 
	 * 
	 * @param i		row
	 * @param j		column
	 * @return	the 1D coordinate
	 */
	private int xyTo1D(int i, int j) {

		return (size * (i - 1) + j);
	}

	/** validate the input and check if it's out of bound
	 * 
	 * @param i		row
	 * @param j		column
	 */
	private void validate(int i, int j) {

		if (i < 1 || i > size)
			throw new ArrayIndexOutOfBoundsException("row index " + i
					+ " out of bounds");
		if (j < 1 || j > size)
			throw new ArrayIndexOutOfBoundsException("column index " + j
					+ " out of bounds");
	}

}
