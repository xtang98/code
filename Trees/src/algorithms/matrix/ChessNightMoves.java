package algorithms.matrix;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class ChessNightMoves {

	public static int minStep(Cell start, Cell end, int N) {
		int steps = 0;
		Queue<Cell> q = new LinkedList<>();
		q.offer(start);
		Set<Cell> visited = new HashSet<>();
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
					}
				}
				//mark it as visited in the end!
				visited.add(cur);
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
		while(!q.isEmpty()) {
			//dequeue and check if it is the answer
			Cell cur = q.poll();
			if (cur.equals(end)) {
				while (cur != start) {
					path.add(0, cur);
					cur = cur.parent;
				}
				path.add(0, cur);
				return path;
			}
			//enqueue 8 kids if they are not visited && still in bound
			for (Cell c: get8moves(cur)) {
				if (!visited.contains(cur) && inBound(c, N)) {
					q.offer(c);
				}
			}
			//mark it as visited after done with it
			visited.add(cur);
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