/*------------------------------------------------------
 *  Author:        Akshay Goyal
 *  Written:       6/24/2015
 *  Last updated:  6/25/2015
 *
 *  Compilation:   javac Percolation.java
 *  Execution:     java Percolation
 *  
 *  Estimates the probabbilty p, above the the system almost always percolates and 
 *  below wich the system almost never percolates.
 *   
 */

public class Percolation {
	
	private boolean[][] opened;
	private int top = 0;
	private int bottom;
	private int size;
	private WeightedQuickUnionUF uf;
	
	public Percolation(int N) {              
		// create N-by-N grid, with all sites blocked
		if (N < 1) 
			throw new IndexOutOfBoundsException("Grid size " + N + " is out of bounds");
		size = N;
		bottom = size * size + 1;
		uf = new WeightedQuickUnionUF(size * size + 2);
		opened = new boolean[size][size];
		
	}
	   public void open(int i, int j) {         
		   // open site (row i, column j) if it is not open already
		   if (i <= 0 || i > size) throw new IndexOutOfBoundsException("row index " + i + " is out of bounds");
		   if (i <= 0 || i > size) throw new IndexOutOfBoundsException("column index " + j + " is out of bounds");
		   
		   if (i == 1) {
			   uf.union(getIndex(i, j), top);
		   }
		   if (i == size) {
			   uf.union(getIndex(i, j), bottom);
		   }
		   if (j > 1 && isOpen(i, j-1)) {
			   uf.union(getIndex(i, j), getIndex(i, j - 1));
		   }
		   if (j < size && isOpen(i, j+1)) {
			   uf.union(getIndex(i, j), getIndex(i, j + 1));
		   }
		   if (i > 1 && isOpen(i - 1, j)) {
			   uf.union(getIndex(i, j), getIndex(i - 1, j));
		   }
		   if (i < size && isOpen(i + 1, j)) {
			   uf.union(getIndex(i, j), getIndex(i + 1, j));
		   }
	   }
	   
	   public boolean isOpen(int i, int j) {     
		   // is site (row i, column j) open?
		   return opened[i - 1][j - 1];
	   }
	   
	   public boolean isFull(int i, int j) {
	   // is site (row i, column j) full?
		   if (i > 0 && i <= size && j > 0 && j <= size) {
			   return uf.connected(getIndex(i, j), top);
		   }else {
			   throw new IndexOutOfBoundsException();
		   }
	   }
	   public boolean percolates() {            
		   // does the system percolate?
	   return uf.connected(top, bottom);
	   }
	   
	   private int getIndex(int i, int j) {
	        return size * (i - 1) + j;
	    }

	   public static void main(String[] args) {   // test client (optional)
		   
	   }

}
