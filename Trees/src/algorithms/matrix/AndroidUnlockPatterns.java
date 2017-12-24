package algorithms.matrix;

public class AndroidUnlockPatterns {
	//possible patterns given (m,n) where 1<=m<=n<=9
	// #patterns = p(1)*4 + p(2)*4 + p(5) because 1,3,7,9 the same, 2,4,6,8 the same
	/* keypad:
	 * 1  2  3
	 * 4  5  6
	 * 7  8  9
	 */
	
	public static int[][] Cross = new int[10][10];
	static {
		//no cross for coner 1,3,5,7
		//crossing 2,4,6,8 respectively
		Cross[1][3] = Cross[3][1] = 2;
		Cross[1][7] = Cross[7][1] = 4;
		Cross[3][9] = Cross[9][3] = 6;
		Cross[7][9] = Cross[9][7] = 8;
		//crossing 5
		Cross[1][9] = Cross[9][1] = 5;
		Cross[3][7] = Cross[7][3] = 5;
		Cross[2][8] = Cross[8][2] = 5;
		Cross[4][6] = Cross[6][4] = 5;
	}
	//visited[Cross[i][j]] means (i,j) is valid for the pattern, 
	//for this purpose mark visited[0] as true always
	public static boolean[] visited = new boolean[10];
	static {
		visited[0] = true; //0 is always ok to cross since it's fake, only 1 - 9 is valid
	}
	
	public static int getAllPatterns(int m, int n) {
		if (!(m>=1 && m<=9 && n>=1 && n<=9 && m<=n)) return 0; //(m,n) must be valid
		return dfs(m, n, 1,1)*4+dfs(m,n, 2,1)*4+dfs(m,n,5,1);
	}
	public static int dfs(int m, int n, int start, int len) { //len is 1 for starting itself
		int count = 0;
		//termination conditions
		if (len == m) count++;
		if (len == n) return 1;
		//go recursive
		visited[start] = true; //mark as visited
		for (int end = 1; end<=9; end++) {
			if (!visited[end] && visited[Cross[start][end]]) { 
				//only when end is not visited && (cross no node or cross a node visited already)
				//we go recursive
				count += 1+dfs(m,n, end, len+1);
			}
		}
		visited[start] = false; //restore
		return count;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		for (int i=0; i<=9; i++) {
			for (int j=0; j<=9; j++) {
				if (Cross[i][j]>0 && i<j)
					System.out.println(i+" to " + j+ " crossing "+Cross[i][j]);
			}
		}
		System.out.println("getAllPatterns(2,4)="+getAllPatterns(1,2));
	}
}
