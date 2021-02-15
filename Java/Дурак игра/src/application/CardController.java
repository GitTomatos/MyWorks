package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;

public class CardController {
	Pane cardPane;

	GameWindowController gameWindow;

	@FXML
	private Text nominal;

	@FXML
	private Text mask;

	public void setCardParameters(String nominal, String mask, GameWindowController gameWindow, Pane cardPane) {
		this.nominal.setText(nominal);
		this.mask.setText(mask);
		this.gameWindow = gameWindow;
		this.cardPane = cardPane;
	}

	public String getNominal() {
		return this.nominal.getText();
	}

	public String getMask() {
		return this.mask.getText();
	}

	@FXML
	void replaceCardToTable1(MouseEvent event) throws IOException {
		gameWindow.addCardOnTable1(this);
		cardPane.setVisible(false);
		FlowPane firstPlayerPane = (FlowPane) cardPane.getParent();
		firstPlayerPane.getChildren().remove(cardPane);
	}
	
	@FXML
	void replaceCardToTable2(MouseEvent event) throws IOException {
		gameWindow.addCardOnTable2(this);
		cardPane.setVisible(false);
		FlowPane secondPlayerPane = (FlowPane) cardPane.getParent();
		secondPlayerPane.getChildren().remove(cardPane);
	}
}
