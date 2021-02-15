package application;

public class Card {
	private int rank;
	private String suit;

	public Card() {
		
	}
	
	public Card(String suit, int rank) {
		this.suit = suit;
		this.rank = rank;
	}

	public static int comparison(Card first, Card second, String trump) {
		int isBigger = 1;
		// Если хотя бы одна из карт козырная
		if ((first.suit.equals(trump) == true) && (second.suit.equals(trump) == true))
			if (first.rank > second.rank)
				isBigger = 1;
			else
				isBigger = -1;
		if ((first.suit.equals(trump) == true) && (second.suit.equals(trump) == false))
			isBigger = 1;
		if ((first.suit.equals(trump) == false) && (second.suit.equals(trump) == true))
			isBigger = -1;

		// Если ни одна из карт не козырная
		if (first.suit.equals(second.suit)) {
			if (first.rank > second.rank)
				isBigger = 1;
			if (first.rank < second.rank)
				isBigger = -1;
		} else
			isBigger = 0;

		// if a > b
		// сравниваем с козырной
		// находим одинаковые масти
		// сравниваем и возвращает разницу между рангами
		return isBigger;

	}

	/*public void printInfo() {
		System.out.println(this.rank + " " + this.suit);
	}
	*/
	
	public int getRank() {
		return this.rank;
	}

	public String getSuit() {
		return this.suit;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public void setSuit(String suit) {
		this.suit = suit;
	}
}