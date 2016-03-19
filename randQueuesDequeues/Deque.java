package com.coursera.randQueuesDequeues;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
	
	private Node first, last;
	private int N;
	
	// Node inner class
	private class Node {
		Item item;
		Node next, prev;
		
		Node(Item item) {
			this.item = item;
		}
	}
	
	// Iterator inner class
	private class DequeIterator implements Iterator<Item> {
		
		private Node current = first;
		
		@Override
		public boolean hasNext() {
			return current != null;
		}

		@Override
		public Item next() {
			
			if (!hasNext()) throw new NoSuchElementException();
			Item item = current.item;
			current = current.next;
			return item;
			
		}
		
	}
	
	// construct an empty deque
	public Deque() {
		first = null;
		last = null;
		N = 0;
	}   
	
	// is the deque empty?
	public boolean isEmpty() {
		return N == 0;
	}
	
	// return the number of items on the deque
	public int size() {
		System.out.println(N);
		return N;
	}
	
	// add the item to the front
	public void addFirst(Item item) {
		
		if (item == null) throw new NullPointerException();
			
		if (isEmpty()) {
			first = new Node(item);
			last = first;
		} else {
			Node oldFirst = first;
			first = new Node(item);
			first.next = oldFirst;
			oldFirst.prev = first;
		}
		N++;
		
	}
	
	// add the item to the end
	public void addLast(Item item) {
		
		if (item == null) throw new NullPointerException();
		
		if (isEmpty()) {
			last = new Node(item);
			first = last;
		} else {
			Node oldLast = last;
			last = new Node(item);
			oldLast.next = last;
			last.prev = oldLast;
		}
		N++;
	}
	
	// remove and return the item from the front
	public Item removeFirst() {
		
		if (isEmpty()) throw new NoSuchElementException();
		Node removeFirst = first;
		first = first.next;
		removeFirst.next = null;
		
		if(first != null)	first.prev = null;
		else				last = null;
		
		Item item = removeFirst.item;
		removeFirst = null;
		N--;
		return item;
	}
	
	// remove and return the item from the end
	public Item removeLast() {
		
		if (isEmpty()) throw new NoSuchElementException();
		Node removeLast = last;
		last = last.prev;
		removeLast.prev = null;
		
		if(last != null)	last.next = null;
		else				first = null;
		
		Item item = removeLast.item;
		removeLast = null;
		N--;
		return item;
	}
	
	// return an iterator over items in order from front to end
	public Iterator<Item> iterator() {
		return new DequeIterator();
	} 
	
	// unit testing
	public static void main(String[] args) {
		
		Deque<String> d = new Deque<String>();
		d.isEmpty();
		d.size();
		d.addFirst("hello");
		d.size();
		d.addFirst("world");
		
		d.addLast("w");
		//d.addLast("4");
		d.addFirst("t");
		//d.addLast("d");
		//d.addLast("ld");
		d.size();
		
		Iterator<String> i = d.iterator(); 
		while(i.hasNext()) {
			
			System.out.print(i.next() + " ");
		}

		
	}
}
