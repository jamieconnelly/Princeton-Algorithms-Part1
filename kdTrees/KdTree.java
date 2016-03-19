package com.coursera.kdTrees;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
	
	private class Node {
		
		// the point
		private Point2D p;
		// the axis-aligned rectangle corresponding to this node
		private RectHV rect;
		// the left/bottom subtree
		private Node lb;
		// the right/top subtree
		private Node rt;  
		
		public Node(Point2D p, RectHV rect) {
			this.p = p;
			this.rect = rect;
			lb = null;
			rt = null;
		}
		public Node(Point2D p) {
			this.p = p;
			//this.rect = rect;
			lb = null;
			rt = null;
		}
	}
	
	private static final double X_MIN = 0.0;
	private static final double X_MAX = 1.0;
	private static final double Y_MIN = 0.0;
	private static final double Y_MAX = 1.0;
	private Node root;
	private int size;
	
	// construct an empty set of points
	public KdTree() {
		root = null;
		size = 0;
	}
	
	// is the set empty? 
	public boolean isEmpty() {
		return size == 0;
	}
	
	// number of points in the set 
	public int size() {
		return size;
	}
	
	// add the point to the set (if it is not already in the set)
	public void insert(Point2D p) {
		if(p == null) throw new NullPointerException();
		root = insert(root, p, X_MIN, Y_MIN, X_MAX, Y_MAX, 0);
	}
	
	private Node insert(Node x, Point2D p, double x_min, double y_min, double x_max, double y_max,int level) {
		
		if(x == null) {
			size++;
			return new Node(p, new RectHV(x_min, y_min, x_max, y_max));
		}
		
		int cmp = compare(p, x.p, level);
		
		if(cmp < 0 )
			if(level % 2 == 0)
				x.lb = insert(x.lb, p, x_min, y_min, x.p.x(), y_max, level+1);
			else
				x.lb = insert(x.lb, p, x_min, y_min, x_max, x.p.y(), level+1);
		if(cmp > 0)
			if(level % 2 == 0)
				x.rt = insert(x.rt, p, x.p.x(), y_min, x_max, y_max, level+1);
			else
				x.rt = insert(x.rt, p, x_min, x.p.y(), x_max, y_max, level+1);
		
		return x;
	}
	
	private int compare(Point2D curr, Point2D next, int level) {
		
		// if the level is even compare the x coordinates else compare the y coordinates
		if(level % 2 == 0) {
			
			int cmp = new Double(curr.x()).compareTo(new Double(next.x()));
			if(cmp == 0) return new Double(curr.y()).compareTo(new Double(next.y()));
			else return cmp;
			
		} else {
			
			int cmp = new Double(curr.y()).compareTo(new Double(next.y()));
			if(cmp == 0) return new Double(curr.x()).compareTo(new Double(next.x()));
			else return cmp;
			
		}
	}
	
	private Point2D contains(Node node, Point2D p, int level) {
		
		while(node != null) {
			int cmp = compare(p, node.p, level);
			if(cmp < 0) return contains(node.lb, p, level++);
			else if(cmp > 0) return contains(node.rt, p, level++);
			else return node.p;
		}
		return null;
	}
	
	// does the set contain point p?
	public boolean contains(Point2D p) {
		return(contains(root, p, 0) != null);
	}
	
	// draw all points to standard draw 
	public void draw() {
		StdDraw.clear();
        draw(root, 0);
	}
	
	private void draw(Node node, int level) {
		
		if (node != null) {
	        draw(node.lb, level+1);
	        StdDraw.setPenRadius();
	        
	        if (level % 2 == 0) {
	            StdDraw.setPenColor(StdDraw.RED);
	            StdDraw.line(node.p.x(), node.rect.ymin(), node.p.x(), node.rect.ymax());
	        } else {
	            StdDraw.setPenColor(StdDraw.BLUE);
	            StdDraw.line(node.rect.xmin(), node.p.y(), node.rect.xmax(), node.p.y());
	        }
            
	        StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(.01);
            node.p.draw();
            draw(node.rt, level + 1);
        }
	}
	
	// all points that are inside the rectangle 
	public Iterable<Point2D> range(RectHV rect) {
		
		Queue<Point2D> queue = new Queue<Point2D>();
        rangeAdd(root, rect, queue);
        return queue;
	}
	

    private void rangeAdd(Node node, RectHV rect, Queue<Point2D> queue) {
    
    	if (node != null && rect.intersects(node.rect)) {
    		if (rect.contains(node.p)) {
    			queue.enqueue(node.p);
            }
            rangeAdd(node.lb, rect, queue);
            rangeAdd(node.rt, rect, queue);
    	}
    }

	
	// a nearest neighbor in the set to point p; null if the set is empty 
	public Point2D nearest(Point2D p) {
		 
		if (isEmpty()) {
			return null;
	    } else {
	    	Point2D result = null;
	        result = nearest(root, p, result);
	        return result;
	    }

	}
	
	private Point2D nearest(Node node, Point2D point, Point2D min) {
        
		if (node != null) {
            
			if (min == null) {
                min = node.p;
            }
        
	        // If the current min point is closer to query than the current point
	        if (min.distanceSquaredTo(point) >= node.rect.distanceSquaredTo(point)) {
	            
	        	if (node.p.distanceSquaredTo(point) < min.distanceSquaredTo(point)) {
	                min = node.p;
	            }
	
	            // Check in which order should we iterate
	            if (node.rt != null && node.rt.rect.contains(point)) {
	                min = nearest(node.rt, point, min);
	                min = nearest(node.lb, point, min);
	            } else {
	                min = nearest(node.lb, point, min);
	                min = nearest(node.rt, point, min);
	            }
	        }
        }
        return min;
    }

	// unit testing of the methods (optional)
	public static void main(String[] args) {
		Point2D a = new Point2D(0.3, 0.2);
		Point2D b = new Point2D(0.5, 0.6);
		Point2D c = new Point2D(0.7, 0.1);
		KdTree t = new KdTree();
		//t.insert(a);
		//
		t.insert(c);
		//System.out.println(t.contains(a));
		System.out.println(t.contains(c));
		//System.out.println(t.contains(b));
		t.insert(a);
		t.insert(b);
		System.out.println(t.contains(a));
		System.out.println(t.contains(b));
		
		
	}
}
