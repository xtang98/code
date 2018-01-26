/**
 * 
 */
package oodesign;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author grab from web:
 * https://www.careercup.com/question?id=5644457835757568
 *
 */

enum Ship{
	Large,Medium,Small;
}

enum Orientation{
	Vertical,Horizontal;
}


class Coordinate implements Comparable<Coordinate>{
	private int x;
	private int y;
	public Coordinate(int x,int y){
		this.x = x;
		this.y = y;
	}
	@Override
	public int compareTo(Coordinate c) {
		// TODO Auto-generated method stub
		if(this.x==c.x && this.y==c.y)
			return 0;
		return 1;
	}
	
}

class Board{
	public String playerName;
	private int[][] board = new int[20][20];
	public boolean gameOver = false;
	private int s1 = 2;
	private int s2 = 2;
	private int s3 = 1;
	Set<Coordinate> taken = new TreeSet<Coordinate>();
	int remainingShips = 5;
	
	public Board(String name){
		this.playerName = name;
	}
	
	public void placeShip(Ship s,int x,int y,Orientation o) throws Exception{
		if(remainingShips==0)
			throw new Exception("No more ship left");
		
		Set<Coordinate> tmp = new TreeSet<Coordinate>();
		switch(s){
			case Large: if(s3==0)
							throw new Exception("No more large ship left");
			
						if(o==Orientation.Horizontal){
							if(x+5>=19){
								throw new Exception("cannot place ship there");
							}
							
							for(int i=0;i<5;i++){
								Coordinate c = new Coordinate(x+i,y);
								if(taken.contains(c))
									throw new Exception("cannot place over another ship");
								tmp.add(c);
							}
							
						}else{
							if(y+5>=19){
								throw new Exception("cannot place ship there");
							}
							
							for(int i=0;i<5;i++){
								Coordinate c = new Coordinate(x,y+i);
								if(taken.contains(c))
									throw new Exception("cannot place over another ship");
								tmp.add(c);
							}
						}
						taken.addAll(tmp);
						tmp.clear();
						--s3;
						break;
			case Medium : if(s2==0)
							throw new Exception("No more medium ship left");
			
							
							if(o==Orientation.Horizontal){
								if(x+3>=19){
									throw new Exception("cannot place ship there");
								}
								
								for(int i=0;i<3;i++){
									Coordinate c = new Coordinate(x+i,y);
									if(taken.contains(c))
										throw new Exception("cannot place over another ship");
									tmp.add(c);
								}
							}else{
								if(y+3>=19){
									throw new Exception("cannot place ship there");
								}
								
								for(int i=0;i<5;i++){
									Coordinate c = new Coordinate(x,y+i);
									if(taken.contains(c))
										throw new Exception("cannot place over another ship");
									tmp.add(c);
								}
							}
							taken.addAll(tmp);
							tmp.clear();
							--s2;
							break;
			case Small : if(s1==0)
							throw new Exception("No more small ship left");
						
						
							if(o==Orientation.Horizontal){
								if(x+3>=19){
									throw new Exception("cannot place ship there");
								}
								
								for(int i=0;i<3;i++){
									Coordinate c = new Coordinate(x+i,y);
									if(taken.contains(c))
										throw new Exception("cannot place over another ship");
									tmp.add(c);
								}
							}else{
								if(y+3>=19){
									throw new Exception("cannot place ship there");
								}
								
								for(int i=0;i<5;i++){
									Coordinate c = new Coordinate(x,y+i);
									if(taken.contains(c))
										throw new Exception("cannot place over another ship");
									tmp.add(c);
								}
							}
							taken.addAll(tmp);
							tmp.clear();
							--s1;
							break;
		}
	}
	
	public boolean bombard(int x,int y, String name){
		Coordinate c = new Coordinate(x,y);
		if(this.board[x][y]==1){
			System.out.println("already hit, try again");
			return true;
		}
			
		this.board[x][y]=1;
		if(taken.contains(c)){
			taken.remove(c);
		}
		if(taken.size()==0){
			System.out.println(name+" WINNER");
			this.gameOver=true;
		}
		return false;
	}	
}

public class BattleShip {
	public static void main(String[] arg){
		Board player1 = new Board("A");
		Board player2 = new Board("B");
		
		try {
			player1.placeShip(Ship.Large, 0, 0, Orientation.Horizontal);
			player1.placeShip(Ship.Medium, 1, 1, Orientation.Vertical);
			player1.placeShip(Ship.Medium, 4, 5, Orientation.Horizontal);
			player1.placeShip(Ship.Small, 11, 12, Orientation.Vertical);
			player1.placeShip(Ship.Small, 19, 7, Orientation.Horizontal);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			player2.placeShip(Ship.Large, 0, 0, Orientation.Horizontal);
			player2.placeShip(Ship.Medium, 1, 1, Orientation.Vertical);
			player2.placeShip(Ship.Medium, 4, 5, Orientation.Horizontal);
			player2.placeShip(Ship.Small, 11, 12, Orientation.Vertical);
			player2.placeShip(Ship.Small, 19, 7, Orientation.Horizontal);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int turn = 0;
		while(!player1.gameOver && !player2.gameOver){
			if(turn==0){
				while(player2.bombard(5, 5, player1.playerName)){}
			}else{
				while(player1.bombard(5, 5, player2.playerName)){}
			}
			turn = (turn+1)%2;
		}
	}
}
