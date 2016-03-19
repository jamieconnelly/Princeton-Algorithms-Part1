package com.coursera.percolator;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {
	
	private double [] threshold;
	private int T;
	
	// perform T independent experiments on an N-by-N grid
	public PercolationStats(int N, int T){
		
		if( N <= 0 || T <= 0) throw new IllegalArgumentException();
		
		threshold = new double[T];
		this.T = T;
		
		for(int t=0; t<T; ++t){
			
			Percolation p = new Percolation(N);
			
			int openSites = 0;
			
			while(!p.percolates()){
				
				int i = StdRandom.uniform(1, N+1);
				int j = StdRandom.uniform(1, N+1);
				
				if(!p.isOpen(i, j)){
					p.open(i, j);
					openSites += 1;
				}
			}
			double thresh = (double)openSites / (double)(N*N);
			threshold[t] = thresh;
		}
	}  
	
	// sample mean of percolation threshold
	public double mean(){
		return StdStats.mean(threshold);
	}                      
	
	// sample standard deviation of percolation threshold
	public double stddev() {
		return StdStats.stddev(threshold);
	}                   
	
	// low  endpoint of 95% confidence interval
	public double confidenceLo() {
		return mean() - (1.96 * stddev() / Math.sqrt(T));
	}
	
	// high endpoint of 95% confidence interval
	public double confidenceHi() {
		return mean() + (1.96 * stddev() / Math.sqrt(T));
	}

	public static void main(String[] args) {
		
		int N = StdIn.readInt();
		int T = StdIn.readInt();
	}
}
