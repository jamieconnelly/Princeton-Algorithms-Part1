package com.coursera.patternRecognition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class FastCollinearPoints {
	
	private Point[] points;
	private ArrayList<LineSegment> segments;
	private ArrayList<Point> slopePoints;
	private HashMap<Double, ArrayList<Point>> foundSegments;
	private final int N;
	
	// finds all line segments containing 4 or more points
	public FastCollinearPoints(Point[] points) {
		
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
		slopePoints = new ArrayList<Point>();
		foundSegments = new HashMap<>();
		collinear();
		
	}
	
	/*
	 *  Think of p as the origin.
	 *  For each other point q, determine the slope it makes with p.
	 *  Sort the points according to the slopes they makes with p.
	 *  Check if any 3 (or more) adjacent points in the sorted order have equal slopes with respect to p. 
	 *  If so, these points, together with p, are collinear.
	*/
	private void collinear() {
		
		for(Point p : points) {
			
			Arrays.sort(points, p.slopeOrder());
			double startSlope = p.slopeTo(p);
			int startIndex = 0;
	        int endIndex = 0;
			
	        for (int count = 0; count < N; count++) {
                
	        	Point curPoint = points[count];
                double curSlope = p.slopeTo(curPoint);
                
                if (curSlope == startSlope) {
                    endIndex = count;
                } else {
					if (endIndex-startIndex >= 2) {			                     
						addEdge(points, startIndex, endIndex, p);
					}
                }
				
                startSlope = curSlope;
                startIndex = count;
                endIndex  = count;
				
			}
			
	        if (endIndex-startIndex >= 2) {
                addEdge(points, startIndex, endIndex, p);
	        }
			
		}
		
	}
	
	// add new segment if it doesn't already exist
	private void addEdge(Point[] targetPoints, int startIndex, int endIndex, Point p) {
		
		boolean allGreater = true;
		
		for (int testind = startIndex; testind < endIndex; testind++) {
			Point testq = targetPoints[testind];
			if (p.compareTo(testq) > 0) {
				allGreater = false;
				break;
			}
		}

		if (allGreater) {
			Point endPoint = targetPoints[endIndex];
			segments.add(new LineSegment(p, endPoint));
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
}
