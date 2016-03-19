package com.coursera.kdTrees;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;

import java.util.TreeSet;

public class PointSET {
	
	private TreeSet<Point2D> pointSet;
	
	// construct an empty set of points
	public PointSET() {
		pointSet = new TreeSet<Point2D>();
	}
	
	// is the set empty? 
	public boolean isEmpty() {
		return pointSet.isEmpty();
	}
	
	// number of points in the set 
	public int size() {
		return pointSet.size();
	}
	
	// add the point to the set (if it is not already in the set)
	public void insert(Point2D p) {
		
		if(p == null) throw new NullPointerException();
		if(!contains(p)) pointSet.add(p);
	}
	
	// does the set contain point p?
	public boolean contains(Point2D p) {
		
		if(p == null) throw new NullPointerException();
		return pointSet.contains(p);
	}
	
	// draw all points to standard draw 
	public void draw() {
		
		for(Point2D p : pointSet) {
			p.draw();
		}
		
	}
	
	// all points that are inside the rectangle 
	public Iterable<Point2D> range(RectHV rect) {
		
		if(rect == null) throw new NullPointerException();
		
		Stack<Point2D> stack = new Stack<>();
		for(Point2D p : pointSet) {
			if(rect.contains(p)) stack.push(p);
		}
		return stack;
	}
	
	// a nearest neighbor in the set to point p; null if the set is empty 
	public Point2D nearest(Point2D p) {
		
		if(p == null) throw new NullPointerException();
		if(pointSet.isEmpty()) return null;
		
		Point2D nearestPoint = pointSet.first();
		double distance = nearestPoint.distanceTo(p);
		
		for(Point2D point : pointSet) {
			if(p.distanceTo(point) < distance) {
				nearestPoint = point;
				distance = point.distanceTo(p);
			}
		}
		
		return nearestPoint;
		
	}

	// unit testing of the methods (optional)
	public static void main(String[] args) {
		
	}
}
