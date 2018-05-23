package algorithms.matrix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BuySellStocks {

    public static int maxProfit(int[] prices) {
        //provide a general dp solution for at most k transactions (means you don't have to do k transactions!!!), 
        //maintaining two max array, one for local max, one for global max
        //l[i][j] refers to max profit on i-th day after AT MOST j sells, last sell ending at day i 
        //g[i][j] refers to gloabl max profit on i-th day after AT MOST j sells (selling can happen at any day)
        //transition fuction: (diff = prices[i]-prices[i-1]), i starts from 1 since day 0 will be 0 profit anyway
        //l[i][j] = max(g[i][j-1] + max(0,diff) /*global max plus last day sell if diff>0*/, local[i-1][j]+diff)
        //g[i][j] = max(g[i-1][j], l[i][j])
        int N=prices.length, K=2;
        if (N<2) return 0;
        int[][] g = new int[N][K], l= new int[N][K];
        for (int i=1; i<N; i++) {
            int diff = prices[i] - prices[i-1];
            for (int j=1; j<K; j++) {
                //we only need to consider g[i-1][j-1]
                System.out.println("diff="+diff);
                l[i][j] = Math.max(g[i-1][j-1]+Math.max(0, diff), l[i-1][j]+diff);
                g[i][j] = Math.max(g[i-1][j], l[i][j]);
                
            }
        }
        for (int i=0; i<N; i++) System.out.println(Arrays.toString(l[i]));
        System.out.println("----");
        for (int i=0; i<N; i++) System.out.println(Arrays.toString(g[i]));
        return g[N-1][K-1];
    }
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//maxProfit(new int[]{2,1,2,0,1});
		
	}

}
