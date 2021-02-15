package application;

import java.io.IOException;
import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class GameWindowController {

	private Player player1 = StartWindowController.getPlayer1();

	private Player player2 = StartWindowController.getPlayer2();

	private Deck deck = StartWindowController.getDeck();
	
	private Card[] CardsOnTableAtack = new Card[18];
	
	private Card[] CardsOnTableDefence = new Card[18];
	
	private int lengthScore1 = 0;
	
	private int lengthScore2 = 0;
	
	private int begin;
	
	@FXML
	private JFXButton addCardButton1;

	@FXML
	private JFXButton addCardButton2;
	
	@FXML
	private JFXButton takeCardButton1;

	@FXML
	private JFXButton takeCardButton2;


	@FXML
	private FlowPane deskAttackCardPane;

	@FXML
	private FlowPane deskAnswerCardPane;

	@FXML
	private ScrollPane firstPlayerScroll;

	@FXML
	private ScrollPane secondPlayerScroll;

	@FXML
	private FlowPane firstPlayerPane;

	@FXML
	private FlowPane secondPlayerPane;

	@FXML
	void addCard1(ActionEvent event) throws IOException, InterruptedException {
		int copyLength = player1.length;

		player1.length = 6;
		
		int kostil1 = lengthScore1;
		
		if (kostil1 < 6) {
			for (int i = 0; i < player1.length - kostil1; i++) {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("Card1.fxml"));
				
				Pane newPane = (Pane) loader.load();

				CardController cardController = loader.getController();
				Card card = deck.getCard();
				cardController.setCardParameters(card.getSuit(), Integer.toString(card.getRank()), this, newPane);
				
				firstPlayerPane.getChildren().add(newPane);

				firstPlayerScroll = new ScrollPane();
				firstPlayerScroll.setContent(firstPlayerPane);
				lengthScore1++;
			}
		}
	}

	@FXML
	void addCard2(ActionEvent event) throws IOException, InterruptedException {

		int copyLength2 = player2.length;

		player2.length = 6;
		
		int kostil2 = lengthScore2;
		
		if (kostil2 < 6) {
			for (int i = 0; i < player2.length - kostil2; i++) {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("Card2.fxml"));

				Pane newPane = (Pane) loader.load();

				CardController cardController = loader.getController();
				player1.myCards[i] = deck.getCard();
				Card card = player1.myCards[i];
				cardController.setCardParameters(card.getSuit(), Integer.toString(card.getRank()), this, newPane);
				
				secondPlayerPane.getChildren().add(newPane);

				secondPlayerScroll = new ScrollPane();
				secondPlayerScroll.setContent(secondPlayerPane);
				lengthScore2++;
			}
		}
		
		if (deck.getSize() == 24) {
			Card minTrump1 = new Card();
			Card minTrump2 = new Card();
			int play1 = 0;
			int play2 = 0;
			
			if (Deck.isThereTrump(player1.myCards, Deck.getTrump()) == true) {
				minTrump1 = Player.FindLowestTrump(player1.myCards, Deck.getTrump());
				play1 = 1;
			}
			if (Deck.isThereTrump(player2.myCards, Deck.getTrump()) == true) {
				minTrump2 = Player.FindLowestTrump(player2.myCards, Deck.getTrump());
				play2 = 1;
			}
			
			if ((play1 == 1) && (play2 == 1)) {
				if (minTrump1.getRank() > minTrump2.getRank())
					begin = 0;
				else
					begin = 1;
			}
			
			if ((play1 == 0) && (play2 == 1))
				begin = 1;
			
			if ((play1 == 1) && (play2 == 0))
				begin = 0;
			
			if ((play1 == 0) && (play2 == 0)) {
				java.util.Random rand = new java.util.Random();
				begin = rand.nextInt(2);
			}
			
		}
	}

	public void addCardOnTable1(CardController card) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("Card1.fxml"));

		Pane newPane = (Pane) loader.load();

		CardController cardController = loader.getController();
		cardController.setCardParameters(card.getNominal(), card.getMask(), this, newPane);
		deskAttackCardPane.getChildren().add(newPane);
		//deskAttackCardPane.add(newPane, deskAttackCardPane.getChildren().size(), 0);
		lengthScore1--;
	}

	public void addCardOnTable2(CardController card) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("Card2.fxml"));

		Pane newPane = (Pane) loader.load();

		CardController cardController = loader.getController();
		cardController.setCardParameters(card.getNominal(), card.getMask(), this, newPane);
		deskAnswerCardPane.getChildren().add(newPane);
		//deskAnswerCardPane.add(newPane, deskAnswerCardPane.getChildren().size(), 0);
		lengthScore2--;
	}
	
	@FXML
    void takeCardPlayer1(ActionEvent event) {
		
    }
	
	@FXML
    void takeCardPlayer2(ActionEvent event) {

    }
}
