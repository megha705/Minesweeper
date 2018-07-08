package game.controller;

import game.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MenuController {

	private Integer gameSize = 10;
	private Integer bombCnt = 10;
	private final Integer MAX_SIZE = 20;
	private final Integer MIN_SIZE = 10;
	
    @FXML
    private Button startButton;

    @FXML
    private Text gameTitel;

    @FXML
    private TextField sizeField;
    
    @FXML
    private TextField bombField;

    @FXML
    private Label textFieldCorrectnessLabel;

    @FXML
    private Label bombFieldCorrectnessLabel;
    
    @FXML
    public void initialize() {
    	sizeField.textProperty().addListener((observable, oldText, newText) -> {
    		checkTheCorrectness(newText);
    	});
    	
    	bombField.textProperty().addListener((observable, oldText, newText) -> {
    		checkTheCorrectnessForBombs(newText);
    	});
    }
    
    private void checkTheCorrectnessForBombs(String toCheck) {
    	Integer bombCount = 0;
    	try {
    		bombCount = Integer.parseInt(toCheck);
    		bombFieldCorrectnessLabel.setText("");
    		bombFieldCorrectnessLabel.setTextFill(Color.BLACK);
    	} catch (Exception e) {
    		bombFieldCorrectnessLabel.setText("Incorrect input!");
    		bombFieldCorrectnessLabel.setTextFill(Color.RED);
		}
    	
    	if(bombCount < 0 || gameSize * gameSize < bombCount) {
    		bombFieldCorrectnessLabel.setText("Incorrect input!");
    		bombFieldCorrectnessLabel.setTextFill(Color.RED);
    	}else {
    		bombCnt = bombCount;
    	}
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
    	
    	if(fieldSize < MIN_SIZE || MAX_SIZE < fieldSize) {
    		textFieldCorrectnessLabel.setText("Incorrect input!");
			textFieldCorrectnessLabel.setTextFill(Color.RED);
    	}else {
    		gameSize = fieldSize;
    	}
    	
    }

    @FXML
    void startGame(ActionEvent event) {
    	
    	if(textFieldCorrectnessLabel.getText().equals("")) {
    		
    		if(bombCnt > gameSize * gameSize) {
    			Alert alert = new Alert(AlertType.ERROR);
    			alert.setTitle("Error");
    			alert.setHeaderText("Too many bombs");
    			alert.setContentText("There are too many bombs...");
    			alert.showAndWait();
    			return;
    		}
    		
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
		    	gcont.setBomb(bombCnt);
		    	gcont.setAll();
		    	
		    	stage.showAndWait();
		    	
	    	} catch (Exception e) {
				e.printStackTrace();
			}
    	}
    }

}
