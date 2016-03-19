package com.coursera.randQueuesDequeues;

import edu.princeton.cs.algs4.StdIn;

public class Subset {
	
	public static void main(String[] args) {
		
		int k = Integer.parseInt(args[0]);
		RandomizedQueue<String> r = new RandomizedQueue<String>();
		
		while (!StdIn.isEmpty()) {
			r.enqueue(StdIn.readString());
		}
		
		for(int i=0; i<k; i++) {
			System.out.println(r.dequeue());
		}
			
	}

}
