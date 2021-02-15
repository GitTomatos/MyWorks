package application;

import java.util.ArrayList;
import java.util.Arrays;

public class Player {

	String name;
	Card a = new Card("s", 2);
	Card[] myCards = new Card[6];
	int length;
	// Card[] myCards = { a, null, null, null, null, null };

	Player(String name) {
		this.name = name;
		// this.myCards = myCards;
		addTheCard();
		Game.createDeck();
		length = 0;
	}

	public int emptyCards(Card[] myCards) {
		int len = 0;
		for (int i = 0; i < myCards.length; i++) {
			if (myCards[i] == null) {
				len++;
			}
		}
		return len;
	}

	public void addTheCard() {

		Card[] newCards = Arrays.copyOf(Deck.giveCard(emptyCards(myCards)), Deck.giveCard(emptyCards(myCards)).length);
		int k = 0;
		for (int i = 0; i < myCards.length; i++) {
			if (myCards[i] == null) {
				myCards[i] = newCards[k++];
			}
		}

	}

	public static Card FindLowestTrump(Card[] myCards, String trump) {
		Card minTrCard = new Card(trump, 14);
		for (int i = 0; i < myCards.length; i++)
			if ((myCards[i].getSuit().equals(trump)) && (myCards[i].getRank() < minTrCard.getRank()))
				minTrCard = new Card(trump, myCards[i].getRank());
		return minTrCard;
	}

	public ArrayList<Card> coverBy(Card[][] onTable) {
		ArrayList<Card> coverage = new ArrayList<Card>();
		for (int i = 0; i < myCards.length; i++) {
			for (int j = 0; j < onTable[0].length; j++)
				for (int m = 0; m < onTable[1].length; m++)
					if (Card.comparison(myCards[i], onTable[j][m], Deck.getTrump()) == 1) {
						coverage.add(myCards[i]);
					}

		}
		if (coverage.size() > 0) {
			System.out.println(coverage);
			return coverage;
		} else {
			System.out.println("Ќет карт, чтобы покрыть карту на столе");
			return null;
		}
	}

	public void TakeCards(Card[][] onTable) {
		Card[] newCards = new Card[myCards.length + onTable[0].length + onTable[1].length];
		int j = 0;
		for (int i = 0; i < myCards.length; i++) {
			newCards[i] = myCards[i];
		}
		for (int i = myCards.length + 1; i < myCards.length + onTable[0].length; i++) {
			newCards[i] = onTable[0][j++];
		}
		j = 0;
		for (int i = myCards.length + onTable[0].length + 1; i < newCards.length; i++) {
			newCards[i] = onTable[1][j++];
		}
		for (int i = 0; i < newCards.length; i++) {
			myCards[i] = newCards[i];
		}
	}

	public Card[] GiveACard(Card[][] onTable) {
		Card[] PossibleCards = new Card[myCards.length];
		for (int i = 0; i < myCards.length; i++) {
			for (int m = 0; m < PossibleCards.length; m++) {
				for (int j = 0; j < onTable[0].length; j++) {
					if (Card.comparison(myCards[i], onTable[0][j], Deck.getTrump()) == 0) {
						PossibleCards[m] = myCards[i];
					}
				}
				for (int j = 0; j < onTable[1].length; j++) {
					if (Card.comparison(myCards[i], onTable[1][j], Deck.getTrump()) == 0) {
						PossibleCards[m] = myCards[i];
					}
				}
				for (int k = PossibleCards.length - 1; k >= 0; k--) {
					for (int j = 0; j < k; j++) {
						if (Card.comparison(PossibleCards[k], PossibleCards[j], Deck.getTrump()) == 0) {
							PossibleCards[k] = null;
						}
					}
				}
			}
		}
		return PossibleCards;
	}

	public Card MakeFirstMove() {
		Card minCard = new Card(myCards[0].getSuit(), myCards[0].getRank());
		for (int i = 1; i < myCards.length; i++) {
			if (Card.comparison(myCards[i], minCard, Deck.getTrump()) == -1) {
				minCard = myCards[i];
			}
		}
		return minCard;
	}
}