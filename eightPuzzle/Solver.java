package com.coursera.eightPuzzle;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

	private class SearchNode implements Comparable<SearchNode> {
		
		private final Board board;
		private final int moves;
		private final SearchNode prevNode;
		private final int priority;
		
		public SearchNode(Board board, int moves, SearchNode prevNode) {
			this.board = board;
			this.moves = moves;
			this.prevNode = prevNode;
			priority = moves + board.manhattan();
		}

		@Override
		public int compareTo(SearchNode that) {
			if(this.priority > that.priority)
				return 1;
			if(this.priority < that.priority)
				return -1;
			return 0;
		}
		
	}

	private boolean isSolvable;
	private int moves;
	private MinPQ<Solver.SearchNode> searchQueue; 
	private MinPQ<Solver.SearchNode> searchTwin; 
	
	// find a solution to the initial board (using the A* algorithm)
	public Solver(Board initial) {
		
		if(initial == null) throw new NullPointerException();
		
		searchQueue = new MinPQ<Solver.SearchNode>();
		searchTwin = new MinPQ<Solver.SearchNode>();
		
		searchQueue.insert(new SearchNode(initial, 0, null));
		searchTwin.insert(new SearchNode(initial.twin(), 0, null));
		
		while(!searchQueue.min().board.isGoal() && ! searchTwin.min().board.isGoal()) {
			
			SearchNode min = searchQueue.delMin();
			SearchNode twinMin = searchTwin.delMin();
			
			Iterable<Board> neighbours = min.board.neighbors();
			Iterable<Board> twinNeighbours = twinMin.board.neighbors();
			
			for(Board board : neighbours) {
                if (min.prevNode != null && board.equals(min.prevNode.board)) {
                    continue;
                }
                SearchNode next = new SearchNode(board, min.moves + 1, min);
                searchQueue.insert(next);
			}
			for(Board board : twinNeighbours) {
				if (twinMin.prevNode != null && board.equals(min.prevNode.board)) {
                    continue;
                }
                SearchNode twinNext = new SearchNode(board, twinMin.moves + 1, twinMin);
                searchTwin.insert(twinNext);
			}
		}
		
		if(searchQueue.min().board.isGoal()) {
			moves = searchQueue.min().moves;
			isSolvable = true;
		}
		
		if(searchTwin.min().board.isGoal()) {
			moves = -1;
			isSolvable = false;
		}
	}
	
	// is the initial board solvable?
    public boolean isSolvable() {
    	return isSolvable;
    }
    
    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
    	return moves;
    }
    
    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
    	
    	if(!isSolvable) return null;
    	Stack<Board> stack = new Stack<Board>();
    	SearchNode node = searchQueue.min();
    	while(node != null) {
    		stack.push(node.board);
    		node = node.prevNode;
    	}
    	return stack;
    }
    
    // solve a slider puzzle (given below)
    public static void main(String[] args) {
    	// create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}
