package com.coursera.patternRecognition;

import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
	
	private Point[] points;
	private ArrayList<LineSegment> segments;
	private final int N;
	
	// finds all line segments containing 4 points
	public BruteCollinearPoints(Point[] points) {
		
		if(points == null) throw new NullPointerException();
		
		Arrays.sort(points);
		
		for(int i=0; i<points.length; ++i) {
			if(points[i] == null) throw new NullPointerException();
			if(i < points.length-1)
				if(points[i].compareTo(points[i+1]) == 0)
					throw new IllegalArgumentException();
		}
		
		this.points = points;
		N = points.length;
		segments = new ArrayList<LineSegment>();
		collinear();
		
	}
	
	private void collinear() {
		
		for(int i=0; i<N-3; i++){
			for(int j=i+1; j<N-2; j++){
				double ij = points[i].slopeTo(points[j]);
					
				for(int k=j+1; k<N-1; k++){
						double jk = points[j].slopeTo(points[k]);
						if(jk == ij) {
				
						for(int l=k+1; l<N; l++){
							double il = points[k].slopeTo(points[l]);
							if(ij == il){
								segments.add(new LineSegment(points[i], points[l]));
							}
						}
					}
				}	
			}
		}
		
	}
	
	// the number of line segments
	public int numberOfSegments() {
		return segments.size();
	}
	
	// the line segments
	public LineSegment[] segments() {
		
		return segments.toArray(new LineSegment[segments.size()]);
	}
	
	public static void main(String[] args) {

	    // read the N points from a file
	    In in = new In(args[0]);
	    int N = in.readInt();
	    Point[] points = new Point[N];
	    for (int i = 0; i < N; i++) {
	        int x = in.readInt();
	        int y = in.readInt();
	        points[i] = new Point(x, y);
	    }

	    // draw the points
	    StdDraw.show(0);
	    StdDraw.setXscale(0, 32768);
	    StdDraw.setYscale(0, 32768);
	    for (Point p : points) {
	        p.draw();
	    }
	    StdDraw.show();

	    // print and draw the line segments
	    BruteCollinearPoints collinear = new BruteCollinearPoints(points);
	    for (LineSegment segment : collinear.segments()) {
	        StdOut.println(segment);
	        segment.draw();
	    }
	}
}
