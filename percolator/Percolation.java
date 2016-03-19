package com.coursera.percolator;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation{
			
	private boolean[] grid;
	private WeightedQuickUnionUF percolation;
	private WeightedQuickUnionUF fullness;
	private int gridLength;
	private int size;
	private int virtualIndexTop;
	private int virtualIndexBot;
	
	// create N-by-N grid, with all sites blocked
	public Percolation(int N){
		
		if ( N < 1 ) throw new IllegalArgumentException();
		
		size = N*N+2;
		percolation = new WeightedQuickUnionUF(size);
		fullness = new WeightedQuickUnionUF(size);
		grid = new boolean[size];
		virtualIndexTop = 0;
		virtualIndexBot = N*N+1;
		grid[virtualIndexTop] = true;
		grid[virtualIndexBot] = true;
		gridLength = N;
		
		for(int j=1; j<=N; ++j){
			
			int i = 1;
			int topIndex = convertIndex(i,j);
			percolation.union(virtualIndexTop, topIndex);
			fullness.union(virtualIndexTop, topIndex);
			
			i = N;
			int botIndex = convertIndex(i,j);
			percolation.union(virtualIndexBot, botIndex);
			
		}
		
	}
	
	// checks to see if i and or j is within 1 - N
	private void isInBounds(int i, int j){
		if(i < 1 || i > gridLength) throw new IndexOutOfBoundsException("row index i out of bounds");
		if(j < 1 || j > gridLength) throw new IndexOutOfBoundsException("col index j out of bounds");
	}
	
	// converts a 2D array index into 1D array index
	private int convertIndex(int i, int j){
		isInBounds(i,j);
		int x = j;
        int y = i;
		return gridLength*(x-1)+y;
	}
	
	// open site (row i, column j) if it is not open already
	public void open(int i, int j){
		
		int site = convertIndex(i,j);

		if(!isOpen(i,j)){
			
			// open site if it is not already open
			grid[site] = true;
			
			// check if site to left is in bounds and is open; if so connect
			if(j>1 && isOpen(i, j-1)){
				percolation.union(site, convertIndex(i,j-1));
				fullness.union(site, convertIndex(i,j-1));
			}
			// check if site on the right is in bounds and is open; if so connect
			if(j<gridLength && isOpen(i, j+1)){
				percolation.union(site, convertIndex(i,j+1));
				fullness.union(site, convertIndex(i,j+1));
			}
			// check if site above is in bounds and is open; if so connect
			if(i>1 && isOpen(i-1, j)){
				percolation.union(site, convertIndex(i-1,j));
				fullness.union(site, convertIndex(i-1,j));
			}
			// check if site below is in bounds and is open; if so connect
			if(i<gridLength && isOpen(i+1, j)){
				percolation.union(site, convertIndex(i+1,j));
				fullness.union(site, convertIndex(i+1,j));
			}
			
		}
		
		
	}
	
	// is site (row i, column j) open?
	public boolean isOpen(int i, int j){
		return grid[convertIndex(i,j)];
	}
	
	// is site (row i, column j) full?
	public boolean isFull(int i, int j){
		int index = convertIndex(i,j);
		return (fullness.connected(virtualIndexTop, index) && grid[index]);
	}
	
	// does the system percolate?
	public boolean percolates(){
		if(gridLength > 1) {
			return percolation.connected(virtualIndexTop, virtualIndexBot);
		} else {
			return grid[convertIndex(1,1)];
		}
	}
	
	/*public static void main(String[] args){
		Percolation p = new Percolation(2);
		p.open(1, 1);
		//p.open(1, 1);
		//p.open(1, 1);
		
	}*/
		
}
