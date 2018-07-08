package game.controller;

import game.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MenuController {

	private Integer gameSize = 10;
	
    @FXML
    private Button startButton;

    @FXML
    private Text gameTitel;

    @FXML
    private TextField sizeField;

    @FXML
    private Label textFieldCorrectnessLabel;

    @FXML
    public void initialize() {
    	sizeField.textProperty().addListener((observable, oldText, newText) -> {
    		checkTheCorrectness(newText);
    	});
    }
    
    void checkTheCorrectness(String toCheck) {
    	
    	Integer fieldSize = 0;
    	try {
    		fieldSize = Integer.parseInt(toCheck);
    		textFieldCorrectnessLabel.setText("");
    		textFieldCorrectnessLabel.setTextFill(Color.BLACK);
    	} catch (Exception e) {
			textFieldCorrectnessLabel.setText("Incorrect input!");
			textFieldCorrectnessLabel.setTextFill(Color.RED);
		}
    	
    	if(fieldSize < 8 || 20 < fieldSize) {
    		textFieldCorrectnessLabel.setText("Incorrect input!");
			textFieldCorrectnessLabel.setTextFill(Color.RED);
    	}else {
    		gameSize = fieldSize;
    	}
    	
    }

    @FXML
    void startGame(ActionEvent event) {
    	
    	if(textFieldCorrectnessLabel.getText().equals("")) {
		    try {
		    	FXMLLoader loader = new FXMLLoader();
		    	loader.setLocation(Main.class.getResource("fxml/GameScreen.fxml"));
		    	BorderPane root = loader.load();
		    	
		    	Stage stage = new Stage();
		    	stage.setScene(new Scene(root));
		    	stage.setTitle("Minespeeper!");
		    	
		    	gameTitel.getScene().getWindow().hide();
		    	
		    	GameController gcont = loader.getController();
		    	gcont.setSize(gameSize);
		    	gcont.setAll();
		    	
		    	stage.showAndWait();
		    	
	    	} catch (Exception e) {
				e.printStackTrace();
			}
    	}
    }

}
