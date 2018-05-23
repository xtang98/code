package algorithms.matrix;

import java.util.ArrayList;
import java.util.List;

public class ChessQueen {

	/**
	 * Put N queens in chessboard "peacefully" meaning one cannot each another, return all possible solutions.
	 * @param N
	 * @return
	 */
	public static List<List<Integer>> getQueenPlacements(int N) {
		List<List<Integer>> sols = new ArrayList<>();
		recursive(N, sols, new ArrayList<Integer>());
		return sols;
	}
	private static void recursive(int N, List<List<Integer>> sols, List<Integer> sol) {
		if (sol.size() == N) {
			sols.add(new ArrayList<Integer>(sol));
		}
		for (int col=0; col<N; col++) {
			if (canPut(N, sol, col)) {
				sol.add(col);
				recursive(N, sols, sol);
				sol.remove(sol.size()-1);
			}
		}
	}
	
	private static boolean canPut(int N, List<Integer> sol, int col) {
		int row = sol.size();
		for (int i=0; i<sol.size(); i++) {
			int j = sol.get(i);
			if (j == col || Math.abs(j-col) == Math.abs(i-row)) {
				return false;
			}
		}
		return true;
	}
	
	public static void main(String[] args) {
		System.out.println(getQueenPlacements(6));
	}
	
	
}
