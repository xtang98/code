package algorithms.Trie;

import java.util.ArrayList;
import java.util.List;

public class SearchWordTwo212 {
	
	
    public static List<String> findWords(char[][] board, String[] words) {
        //put words in Trie, record max len as well
        int maxLen = -1;
        ArrayTrie trie = new ArrayTrie();
        for (String w: words) {
            trie.insert(w);
            maxLen = Math.max(maxLen, w.length());
        }
        int M = board.length, N = board[0].length;
        boolean[][] visited = new boolean[M][N];
        List<String> ret = new ArrayList<>();
        //for each cell, go recursive, use a visisted array to avoid hitting visited cell
            //terminate if word len exeeds max len
        for (int i=0; i<M; i++) {
            for (int j=0; j<N; j++) {
                rec(trie, maxLen, board, visited, i, j, M, N, "", ret);
            }
        }
        return ret;
    }
    private static void rec(ArrayTrie trie, int maxLen, char[][] board, boolean[][] visited, int i, int j, int M, int N, String pre, List<String> ret) {
        //termination condition
        if (i>=M || i<0 || j>=N || j<0) return;
        if (visited[i][j]) return;
        String cur = pre + board[i][j];
        if (trie.find(cur)) {
        	System.out.println("i="+i+",j="+j+"pre="+pre+",cur="+cur);
        	 
            if (!ret.contains(cur)) ret.add(cur);
            //return; //canot return yet, since this solution could be a prefix for another solution
            //e.g. [[a,a] [a,b]], words ={"aaa","aaab"}, 
            //once we find "aaa", we need to continue to find "aaab"
        }
        if (!trie.startWith(cur)) return;
        if (cur.length()>maxLen) return;
        //go recursive for unvisited neighbors
        visited[i][j] = true;
        rec(trie, maxLen, board, visited, i+1,j, M,N, cur, ret);
        rec(trie, maxLen, board, visited, i-1,j, M,N, cur, ret);
        rec(trie, maxLen, board, visited, i,j+1, M,N, cur, ret);
        rec(trie, maxLen, board, visited, i,j-1, M,N, cur, ret);
        visited[i][j] = false;
    }	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		char[][] board = {{'a','b'},{'a','a'}};
		String[] words = {"aba","baa","bab","aaab","aaa","aaaa","aaba"};
		System.out.println(findWords(board, words));
	}

}
