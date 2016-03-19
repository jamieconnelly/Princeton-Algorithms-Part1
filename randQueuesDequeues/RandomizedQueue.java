package com.coursera.randQueuesDequeues;

import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
	
	private final int INITIALSIZE = 10;
	private int N;
	private Item [] queue;
	
	//
	private class RandomizedQueueIterator implements Iterator<Item> {
		
		private Item [] q = queue;
		private boolean [] returned = new boolean[N];
		private int objects = N;
		
		@Override
		public boolean hasNext() {
			return objects != 0;
		}
		
		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public Item next() {
			
			if (!hasNext()) throw new NoSuchElementException();
			else {
				Item item;
				int i;
				do {
					i = StdRandom.uniform(0, N);
				} while (returned[i] == true);
				returned[i] = true;
				item = queue[i];
				objects--;
				return item;
			}
		}
		
	}
	
	// construct an empty randomized queue
	public RandomizedQueue() {
		
		N = 0;
		queue = (Item[]) new Object[INITIALSIZE];
	}
	
	// is the queue empty?
	public boolean isEmpty() {
		return N == 0;
	}
	
	// return the number of items on the queue
	public int size() {
		return N;
	}
	
	// add the item
	public void enqueue(Item item) {
		
		if (item == null) throw new NullPointerException();
		
		if (N == queue.length) resizeArray(2 * queue.length);
		queue[N++] = item;
	}
	
	// remove and return a random item
	public Item dequeue() {
		
		if (isEmpty()) throw new NoSuchElementException();
		
		if(N > 0 && N == queue.length / 4) resizeArray(queue.length / 2);
		int index = StdRandom.uniform(0, N);
		Item item = queue[index];
		queue[index] = queue[--N];
		queue[N] = null;
		return item;
	}
	
	// dynamically resize array
		private void resizeArray(int capacity) {
			
			Item [] copy = (Item[]) new Object[capacity];
			for(int i=0; i<N; i++){
				copy[i] = queue[i];
			}
			queue = copy;
		}
	
	// return (but do not remove) a random item
	public Item sample() {
		
		if (isEmpty()) throw new NoSuchElementException();
		
		int index = StdRandom.uniform(0, N);
		Item item = queue[index];
		return item;
	}
	
	// return an independent iterator over items in random order
	public Iterator<Item> iterator() {
		return new RandomizedQueueIterator();
	}
	
	// unit testing
	public static void main(String[] args) {
		
		RandomizedQueue<Integer> r = new RandomizedQueue<Integer>();
		r.enqueue(1);
		r.enqueue(3);
		r.enqueue(4);
		//r.print();
		r.enqueue(3);
		r.enqueue(0);
		r.enqueue(-3);
		r.enqueue(41);
		r.enqueue(7);
		r.enqueue(8);
		r.enqueue(9);
		r.enqueue(-4);
		//r.print();
		r.dequeue();
		//r.print();
		r.enqueue(111);
		Iterator<Integer> i = r.iterator();
		while(i.hasNext()){
			Integer num = i.next();
			System.out.print( num + " ");
		}
		
		
	}

}
