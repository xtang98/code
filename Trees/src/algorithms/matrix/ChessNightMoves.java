package algorithms.matrix;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class ChessNightMoves {

	/**
	 * chessboard with dimension NxN, find shortest number of steps for a knight to jump from position start to position end
	 * TODO: one variation is to mark some cells with 1 indicating that cell is occupied and knight cannot move to it
	 * Expect: Ask about how big is the board? Can we really always reach a answer? thought process? BFS with q? 
	 * e.g. (1,1) --> (2,3), the answer will be 1; (1,1)--> (3,5), the answer will be 2
	 * @param start
	 * @param end
	 * @param N
	 * @return
	 */
	public static int minStep(Cell start, Cell end, int N) {
		int steps = 0;
		Queue<Cell> q = new LinkedList<>();
		q.offer(start);
		Set<Cell> visited = new HashSet<>();
		visited.add(start); //mark as visited right after enqueuong
		while (!q.isEmpty()) {
			int rowCount = q.size();
			for (int i=0; i<rowCount; i++) {
				//dequeue current, check if it is THE answer!
				Cell cur = q.poll();
				if (cur.equals(end)) {
					return steps+1;
				}
				//enqueue kids if they are not visited yet, not out of bound
				for(Cell c: get8moves(cur)) {
					if (!visited.contains(c) && inBound(c, N)) {
						q.offer(c);
						//mark it as visited in the end!
						visited.add(c);

					}
				}
				System.out.print(cur);
			}
			System.out.println();
			steps++;
		}
		
		return steps;
	}
	
	public static List<Cell> minPath(Cell start, Cell end, int N) {
		List<Cell> path = new ArrayList<>();
		Queue<Cell> q = new LinkedList<>();
		Set<Cell> visited = new HashSet<>();
		q.offer(start);
		visited.add(start); //mark as visited as soon as we enqueue it
		int qCount = 0;
		while(!q.isEmpty()) {
			//dequeue and check if it is the answer
			Cell cur = q.poll();
			qCount++;
			if (cur.equals(end)) {
				while (cur != start) {
					path.add(0, cur);
					cur = cur.parent;
				}
				path.add(0, cur);
				System.out.println("\nqCount="+qCount);

				return path;
			}
			//enqueue 8 kids if they are not visited && still in bound
			for (Cell c: get8moves(cur)) {
				if (!visited.contains(c) && inBound(c, N)) {
					q.offer(c);
					//mark it as visited after enqueuing, to avoid enqueuing same ndoe twice!!
					visited.add(c);
				}
			}
			//visited.add(cur); //this  will enqueue 2122 nodes vs 147 nodes when mark it right after enqueue each node
		}
		return path;
	}
	
	public static List<Cell> get8moves(Cell a) {
		List<Cell> ret = new ArrayList<>();
		ret.add(new Cell(a.x+1, a.y+2, a));
		ret.add(new Cell(a.x+2, a.y+1, a));
		ret.add(new Cell(a.x+1, a.y-2, a));
		ret.add(new Cell(a.x+2, a.y-1, a));
		ret.add(new Cell(a.x-1, a.y-2, a));
		ret.add(new Cell(a.x-2, a.y-1, a));
		ret.add(new Cell(a.x-1, a.y+2, a));
		ret.add(new Cell(a.x-2, a.y+1, a));
		return ret;
	}
	
	public static boolean inBound(Cell a, int N) {
		return a.x>=0 && a.x<N && a.y>=0 && a.y<N;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int N = 30;
	    Cell knightPos = new Cell(1, 1);
	    Cell targetPos = new Cell(12, 8);
	    System.out.println("\nmin steps:"+ minStep(knightPos, targetPos, N));
	    System.out.println("\nmin path:"+ minPath(knightPos, targetPos, N));
	}

}
class Cell
{
    int x, y;
    Cell parent;
    Cell(int a, int b) {x=a; y=b;}
    Cell(int a, int b, Cell c) {this(a,b); parent = c;}
    
    @Override
    public boolean equals(Object o) {
    	if (o instanceof Cell) {
    		Cell c = (Cell)o;
    		return x == c.x && y == c.y;
    	}
    	return false;
    }
    
    @Override
    public int hashCode() {
    	return x+y;
    }
    
    @Override
    public String toString() {
    	return "("+x+","+y+")";
    }
};