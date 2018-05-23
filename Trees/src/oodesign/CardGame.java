package oodesign;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

enum CardType {
	Joker,
	Spade,
	Heart,
	Diamond,
	Clubs
}
class Card {
	CardType type;
	int val;
	public Card(CardType t, int v) {type = t; val = v;}
}
class Deck {
	List<Card> cards = new ArrayList<>();
	Random rand = new Random();
	
	public Deck() {
		for (CardType type: CardType.values()) {
			int max = type == CardType.Joker? 1: 13;
			for (int i=1; i<=max; i++) {
				cards.add(new Card(type, i));
			}
		}
	}
	
	public void shuffle() {
		for (int i=0; i<cards.size(); i++) {
			int j = rand.nextInt(cards.size());
			Collections.swap(cards, i, j);
		}
	}
	/**
	 * Draw a card from the deck
	 */
	public Card deal() {
		if (cards.size()<1) throw new RuntimeException("");
		return cards.remove(cards.size()-1);
	}
}
class Hand {
	List<Card> cards;
	public Hand() {
		cards = new ArrayList<>();
	}
	public void Clear() {
		cards.clear();
	}
	public void addCard(Card c) {
		cards.add(c);
	}
	public void removeCard(int idx) {
		cards.remove(idx);
	}
	public int getCount() {return cards.size();}
	public void sortBySuite() {
		
	}
}
public class CardGame {
	public static void main(String[] arg){
		String chunckedData = "4\r\nWiki\r\n5\r\npedia\r\nE\r\n in\r\n\r\nchunks.\r\n0\r\n\r\n";
		System.out.println(parseChunck(chunckedData));
	}
	public static String parseChunck(String chunckedData) {
		StringBuilder sb = new StringBuilder();
		int start = 0;
		while (start<chunckedData.length()) {
			//each loop process one chunck
			char cur = chunckedData.charAt(start++);
			int count = getHexValue(cur);
			if (count==0) break;
			start+=2; //skip start boundary
			for (int i=0; i<count; i++) sb.append(chunckedData.charAt(start++)); //fetch data
			start+=2; //skip end boundary
		}
		return sb.toString();
	}
	public static int getHexValue(char c) {
		if (c>='0' && c<='9') return c-'0';
		if (c>='A' && c<='F') return c-'A'+10;
		throw new IllegalArgumentException();
	}
}
