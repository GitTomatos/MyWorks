package application;

import java.util.*;

public class Deck {
	private static Card trump;
	private static Stack<Card> deck = new Stack<Card>();

	public Deck() {
		Card[] pack = new Card[36];
		for (int i = 0; i < 9; i++) {
			pack[i] = new Card("Черви", i + 6);
		}
		for (int i = 9; i < 18; i++) {
			pack[i] = new Card("Буби", i - 3);
		}
		for (int i = 18; i < 27; i++) {
			pack[i] = new Card("Крести", i - 12);
		}
		for (int i = 27; i < 36; i++) {
			pack[i] = new Card("Пики", i - 21);
		}
		shuffle(pack);
		for (int i = 0; i < pack.length; i++) {
			deck.push(pack[i]);
		}

	}

	public static Card trump() {
		if (deck.size() == 24) {
			Card[] buffer = new Card[1];
			buffer[0] = deck.pop();
			trump = buffer[0];
			System.out.print("Козырь: ");
		}
		return trump;
	}

	public static Card[] shuffle(Card[] card) {
		List<Card> cardList = Arrays.asList(card);
		Collections.shuffle(cardList);
		Card[] cards = cardList.toArray(new Card[cardList.size()]);
		return cards;
	}

	public int getSize() {
		if (deck.size() > 0) {
			//System.out.println("Осталось карт в колоде: " + deck.size());
			return deck.size();
		} else {
			//System.out.println("Колода пуста");
			return 0;
		}
	}
	
	public static String getTrump() {
		return trump.getSuit();
	}

	public static Card[] giveCard(int len) {
		if (deck.size() >= len) {
			Card[] playerCard = new Card[len];
			for (int i = 0; i < playerCard.length; i++) {
				playerCard[i] = deck.pop();
			}
			return playerCard;
		} else {
			Card[] playerCard = new Card[deck.size()];
			for (int i = 0; i < playerCard.length; i++) {
				playerCard[i] = deck.pop();
			}
			System.out.println("Колода пуста");
			return playerCard;
		}
	}
	
	public Card getCard() {
		return deck.pop();
	}
	
	public static boolean isThereTrump(Card[] cards, String trump) {
		for (int i = 0; i < cards.length; i++)
			if (cards[i].getSuit().equals(trump))
				return true;
		return false;
	}

	public static void main(String args[]) {
		Deck deck = new Deck();
		Deck.giveCard(6);
		Deck.giveCard(6);
		System.out.println(Deck.trump());
		Deck.giveCard(1);
		deck.getSize();
	}

}