package algorithms.matrix;

public class Matrix {
	
	//walks spiral problem
	public static void printSpiral(int[][] a) {
		int r1=0, r2=a.length-1, c1=0, c2=a[0].length-1;
		
		//  00, 01, 02
		//  10, 11, 12
		//  20, 21, 22
		// general idea is to visit "circle" by "circle" from outer layer to inner
		// for each circle:
		//     1. visit the top row, if there are more than 1 row, continue on step 2-4
		//     2. visit the left-most column without top/bottom elements (since they are visted by row visitor)
		//     3. visit the bottom row
		//      4. visit the right-most column , without top/bottom elements again
		while (r1<=r2 && c1<=c2) { //works for single element as well
			//move right [r1][i], visit first row
			for (int i=c1;i<=c2;i++) System.out.print(a[r1][i]);
			//move down only if r1<r2, a[i][c2]
			if (r1<r2) {
				for (int i=r1+1;i<r2; i++) System.out.print(a[i][c2]);
				//move left from right a[r2][i]
				for(int i=c2;i>=c1;i--) System.out.print(a[r2][i]);
				//move up only if r1<r2, a[i][c2]
				for (int i= r2-1; i>r1; i--) System.out.print(a[i][c1]);
			}
			
			//adjust r and c going inward
			r1++;r2--;c1++;c2--;
		}
	}
	
	//island problem: return the number of islands in 0-1 matrix where
	//connected 1's are one island
	//Note: 1 can connect to another one up/down/left/right, but no diagonally
	public static int numOfIslands(int[][] a) {
		if (a == null || a.length<1) return 0;
		int n= a.length;
		if (a[0] == null || a[0].length<1) return 0;
		int m = a[0].length;
		
		boolean[][] visited = new boolean[n][m]; 
		int count = 0;
		for (int i=0; i<n; i++) {
			for (int j=0;j<m;j++) {
				//dfs on node: 1 && not visited 
				if (a[i][j] == 1 && !visited[i][j]) {
					count++;
					dfs(a,visited,i,j);
				}
			}
		}
		return count;
	}
	private static void dfs(int[][] a, boolean[][] v, int i, int j) {
		int n= a.length, m=a[0].length;
		//must be within boundary
		if (i<0 || i>=n || j<0 || j>=m) return;
		//base case: hit 0 or already visited, return
		if (a[i][j]==0 || v[i][j]) return;
		
		//now it is 1 and unvisited, mark as visited, then do recursive thing
		v[i][j] = true; //now it is visited!
		dfs(a,v,i-1,j);
		dfs(a,v,i+1,j);
		dfs(a,v,i,j-1);
		dfs(a,v,i,j+1);
	}

	public static void main(String[] args) {
		/*
		 *  0 0 1 1
		 *  0 0 1 1
		 *  1 0 0 0
		 *  0 1 0 1
		 *  
		 *  
		 */
		int[][] matrix = new int[4][4];
		matrix[0][2] = 1;
		matrix[0][3] = 1;
		matrix[1][2] = 1;
		matrix[1][3] = 1;
		matrix[2][0] = 1;
		matrix[3][1] = 1;
		matrix[3][3] = 1;
		
		int numIslands = Matrix.numOfIslands(matrix);
		System.out.println("#islands, expected 4, get "+numIslands);
		
		matrix = new int[][] {  {1,   2,  3, 4},
								{10, 11, 12, 5},
								{9,   8,  7, 6}};
		Matrix.printSpiral(matrix);						
								
	}
}
