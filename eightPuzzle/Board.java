package com.coursera.eightPuzzle;

import java.util.Arrays;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stack;

public class Board {
	
	private final int[][] blocks;
	private final int N;
	private int manhattan;
	private int blankX;
	private int blankY;
	
	// construct a board from an N-by-N array of blocks
	// (where blocks[i][j] = block in row i, column j)
	public Board(int[][] blocks) {
		
		this.blocks = blocks;
		N = dimension();
		manhattan = -1;
		
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				if(blocks[i][j] == 0) {
					blankX = i;
					blankY = j;
				}
			}
		}
		
	}
    
	// board dimension N
	public int dimension() {
		return blocks.length;
	}
	
	// number of blocks out of place
	public int hamming() {
		
		//if(isGoal()) return 0;
		
		int hammingDistance = 0;
		int number = 0;
		
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				if(blocks[i][j] != number+1 && blocks[i][j] != 0)
					hammingDistance++;
				number++;
			}
		}
		
		return hammingDistance;
	}
	
	// sum of Manhattan distances between blocks and goal
	public int manhattan() {
		
		if(manhattan != -1) return manhattan;
		
		int distance = 0;
		int number = 0;
		
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				if(blocks[i][j] != 0) {
					int targetX = (blocks[i][j]  - 1) / N; // expected x-coordinate (row)
                	int targetY = (blocks[i][j]  - 1) % N; // expected y-coordinate (col)
                	int dx = i - targetX; // x-distance to expected coordinate
                	int dy = j - targetY; // y-distance to expected coordinate
					distance += Math.abs(dx) + Math.abs(dy);
				}
			}
		}
		
		manhattan = distance;
		return distance;
	}
	
	// is this board the goal board?
	public boolean isGoal() {
		return hamming() == 0;
	}
	
	// a board that is obtained by exchanging any pair of blocks
	public Board twin() {
		
		int x1, x2;
		int y1, y2;
		int swap1, swap2;
		int[][] twin = new int[N][N];
		
		do {
			x1 = StdRandom.uniform(N);
			y1 = StdRandom.uniform(N);
			x2 = StdRandom.uniform(N);
			y2 = StdRandom.uniform(N);
			swap1 = blocks[x1][y1];
			swap2 = blocks[x2][y2];
		} while(swap1 == 0 || swap2 == 0 || swap1 == swap2);
		
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				if(i == x1 && j == y1)
					twin[i][j] = swap2;
				else if(i == x2 && j == y2)
					twin[i][j] = swap1;
				else
					twin[i][j] = blocks[i][j];
			}
		}
		
		return new Board(twin);
	}
	
	// does this board equal y?
	public boolean equals(Object y) {
		if(y == this) return true;
		if(y == null) return false;
		if(y.getClass() != this.getClass()) return false;
		Board that = (Board) y;
		return Arrays.deepEquals(that.blocks, this.blocks);
	}
	
	// make copy of gameboard
	private int[][] copyBoard() {
		int[][] copy = new int[N][N];
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				copy[i][j] = blocks[i][j];
			}
		}
		return copy;
	}
	
	// all neighboring boards
	public Iterable<Board> neighbors() {
		
		Stack<Board> stack = new Stack<Board>();
		
		if(blankX < N-1) {
			int[][] neighbour = copyBoard();
			neighbour[blankX][blankY] = blocks[blankX+1][blankY];
			neighbour[blankX+1][blankY] = blocks[blankX][blankY];
			stack.push(new Board(neighbour));
		}
		
		if(blankX > 0) {
			int[][] neighbour = copyBoard();
			neighbour[blankX][blankY] = blocks[blankX-1][blankY];
			neighbour[blankX-1][blankY] = blocks[blankX][blankY];
			stack.push(new Board(neighbour));
		}
		
		if(blankY < N-1) {
			int[][] neighbour = copyBoard();
			neighbour[blankX][blankY] = blocks[blankX][blankY+1];
			neighbour[blankX][blankY+1] = blocks[blankX][blankY];
			stack.push(new Board(neighbour));
		}
		
		if(blankY > 0) {
			int[][] neighbour = copyBoard();
			neighbour[blankX][blankY] = blocks[blankX][blankY-1];
			neighbour[blankX][blankY-1] = blocks[blankX][blankY];
			stack.push(new Board(neighbour));
		}
		
		return stack;
	}
	
	// string representation of this board (in the output format specified below)
	public String toString() {
		
		StringBuilder s = new StringBuilder();
	    s.append(N + "\n");
	    
	    for (int i = 0; i < N; i++) {
	        for (int j = 0; j < N; j++) {
	            s.append(String.format("%2d ", blocks[i][j]));
	        }
	        s.append("\n");
	    }
	    return s.toString();
	}
	
	// unit tests (not graded)
	public static void main(String[] args) {
		int[][] grid = {{8, 1, 3}, 
					 	{4, 0, 2},
					 	{7, 6, 5}};
		
		Board board = new Board(grid);
		
		System.out.println(board.hamming());
		System.out.println(board.manhattan());
		System.out.println(board.toString());
		Board twin = board.twin();
		System.out.println(twin.toString());
	}

}
