package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class StartWindowControllerStaroe{
	
	private static Deck deck;
	
	private static Player player1;
	private static Player player2;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Button ExitButton;

	@FXML
	private Button StartButton;

	@FXML
	void exitGame() {
		System.exit(0);
	}

	@FXML
	void startGame() {
		deck = new Deck();
		player1 = new Player("");
		
		player2 = new Player("");
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("GameWindow.fxml"));
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Parent root = loader.getRoot();
		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.showAndWait();
	}
	
	public static Deck getDeck() {
		return deck;
	}
	
	public static Player getPlayer1() {
		return player1;
	}
	
	public static Player getPlayer2() {
		return player2;
	}
}
