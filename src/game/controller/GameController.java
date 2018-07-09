package game.controller;

import java.util.Random;

import game.classes.ExtendedButton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameController {

	private Integer gameSize = 10;
	private Integer bombCount = 20;
	private Integer bombsLeft = 0;
	private final Integer tileSize = 35;
	private ExtendedButton[][] tile;
	private final Integer bomb = 15689;
	private ObservableList<ExtendedButton> bombs = FXCollections.observableArrayList();
	private ObservableList<ExtendedButton> clearTiles = FXCollections.observableArrayList();
	private Timeline tl;
	private Integer hours = 0, minutes = 0, seconds = 0;
	
    @FXML
    private Text timeText;
    
    @FXML
    private Text bombsLeftText;
    
    @FXML
    private GridPane gameGrid;
    
    public void setAll() {
    	
    	System.out.println("Setting the game board...");
    	
    	try {
	    	//bombCount = gameSize * 2;
	    	
	    	tile = new ExtendedButton[gameSize][];
	    	for(int i = 0; i < gameSize; i++) {
	    		tile[i] = new ExtendedButton[gameSize];
	    		for(int j = 0; j < gameSize; j++) {
	    			tile[i][j] = new ExtendedButton();
	    			tile[i][j].setPrefSize(tileSize, tileSize);
	    			tile[i][j].setMaxSize(tileSize, tileSize);
	    			tile[i][j].setMinSize(tileSize, tileSize);
	    			tile[i][j].setX(i); tile[i][j].setY(j);
	    			tile[i][j].setColor("grey");
	    			//tile[i][j].setStyle("-fx-background-color: LightGray;");
	    			tile[i][j].addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
	
						@Override
						public void handle(MouseEvent event) {
							if(event.getButton() == MouseButton.PRIMARY) {
								updateButtonWhenLeftClick((ExtendedButton)event.getSource());
							}else if(event.getButton() == MouseButton.SECONDARY) {
								updateButtonWhenRigthClick((ExtendedButton)event.getSource());
							}
						}
					});
	    		}
	    	}
	    	
	    	Random rand = new Random();
	    	for(int i = 0; i < bombCount; i++) {
	    		int x = rand.nextInt(gameSize);
	    		int y = rand.nextInt(gameSize);
	    		while(tile[x][y].getValue() == bomb) {
	    			x = rand.nextInt(gameSize);
	        		y = rand.nextInt(gameSize);
	    		}
	    		tile[x][y].setValue(bomb);
	    		//tile[x][y].setText("B");
	    		bombs.add(tile[x][y]);
	    	}
	    	
	    	for(int i = 0; i < gameSize; i++) {
	    		for(int j = 0; j < gameSize; j++) {
	    			if(tile[i][j].getValue() == bomb) continue;
	    			int sum = 0;
	    			int dx[] = {-1, 0, 1, -1, 1, -1, 0, 1};
	    			int dy[] = {-1, -1, -1, 0, 0, 1, 1, 1};
	    			for(int k = 0; k < dx.length; k++) {
	    				int newX = i + dx[k];
	    				int newY = j + dy[k];
	    				boolean isXRange = (0 <= newX && newX < gameSize);
	    				boolean isYRange = (0 <= newY && newY < gameSize);
	    				if(isXRange && isYRange && tile[newX][newY].getValue() == bomb) {
	    					sum++;
	    				}
	    			}
	    			clearTiles.add(tile[i][j]);
	    			tile[i][j].setValue(sum);
	    		}
	    	}
	    	
	    	gameGrid.setPadding(new Insets(10, 10, 10, 10));
	    	
	    	gameGrid.setVgap(5);
	    	gameGrid.setHgap(5);
	    	
	    	gameGrid.setAlignment(Pos.CENTER);
	    	
	    	for(int x = 0; x < gameSize; x++) {
	    		for(int y = 0; y < gameSize; y++) {
	    			gameGrid.add(tile[x][y], x, y);
	    		}
	    	}
	    	
	    	tl = new Timeline(new KeyFrame(Duration.millis(1000), ae -> {
	    		addSecondToTimer();
	    	}));
	    	
	    	tl.setCycleCount(Animation.INDEFINITE);
	    	tl.play();
	    	
	    	addToBombsLeft(bombCount);
	    	
    	} catch (Exception e) {
			System.out.println("Really bad error!!!");
			Stage st = (Stage)gameGrid.getScene().getWindow();
			st.close();
		}
    	System.out.println("...done!");
    	
    	checkIfGameIsEnded();
    }

	private void addToBombsLeft(int i) {
		bombsLeft += i;
		bombsLeftText.setText("Bombs left: " + bombsLeft + " out of " + bombCount);
	}

	private void addSecondToTimer() {
		seconds++;
		minutes = seconds / 60; seconds %= 60;
		hours = minutes / 60; minutes %= 60;
		hours %= 24;
		timeText.setText("Time: " + doTime());
	}

	private String doTime() {
		String h = hours.toString(); if(h.length() == 1) h = "0" + h;
		String m = minutes.toString(); if(m.length() == 1) m = "0" + m;
		String s = seconds.toString(); if(s.length() == 1) s = "0" + s;
		return h + ":" + m + ":" + s;
	}

	public void setSize(Integer gameSizeNew) {
		System.out.println("Setting game size with: " + gameSizeNew);
		gameSize = gameSizeNew;
	}
	
	public void setBomb(Integer bombCnt) {
		System.out.println("Setting bomb count: " + bombCnt);
		bombCount = bombCnt;
	}
	
	private void updateButtonWhenRigthClick(ExtendedButton toUpdate) {
		System.out.println("Right click on: " + toUpdate.getCords());
		markButton(toUpdate);
	}
	
	private void updateButtonWhenLeftClick(ExtendedButton toUpdate) {
		System.out.println("Left click on: " + toUpdate.getCords());
		if(toUpdate.getValue() == bomb) {
			System.out.println("Game over!");
			gameEnds();
			return;
		}
		
		//System.out.println(toUpdate.getColor());
		
		if(buttonReachable(toUpdate)) {
			if(toUpdate.getValue() == 0) {
				spreadZero(toUpdate);
			}
			showButton(toUpdate);
		}
	}
	
	private void showButton(ExtendedButton button) {
		System.out.println("Revealing: " + button.getCords());
		clearTiles.remove(button);
		//button.setStyle("-fx-background-color: lime;");
		button.setStyle("-fx-background-color: LightGray;");
		button.setColor("green");
		if(button.getValue() != 0) {
			button.setStyle("-fx-font-weight: bold;");
			button.setText(button.getValue() + "");
		}
		checkIfGameIsEnded();
	}
	
	private void markButton(ExtendedButton button) {
		
		if(button.getColor().equals("grey")) {
			System.out.println("Marking: " + button.getCords());
			addToBombsLeft(-1);
			button.setStyle("-fx-background-color: red;");
			button.setColor("yellow");
			button.setText("");
			if(button.getValue() == bomb) {
				bombs.remove(button);
			}
		}else if(button.getColor().equals("yellow")) {
			System.out.println("UnMarking: " + button.getCords());
			addToBombsLeft(1);
			//button.setStyle("-fx-background-color: LightGray;");
			button.setStyle("-fx-font-weight: bold;");
			button.setColor("grey");
			button.setText("");
			if(button.getValue() == bomb) {
				bombs.add(button);
			}
		}else {
			System.out.println("Can't Mark: " + button.getCords());
		}
	}
	
	private void spreadZero(ExtendedButton toUpdate) {
		if(buttonReachable(toUpdate)) {
			showButton(toUpdate);
			int dx[] = {-1, 0, 1, -1, 1, -1, 0, 1};
			int dy[] = {-1, -1, -1, 0, 0, 1, 1, 1};
			for(int k = 0; k < dx.length; k++) {
				int newX = toUpdate.getX() + dx[k];
				int newY = toUpdate.getY() + dy[k];
				boolean isXRange = (0 <= newX && newX < gameSize);
				boolean isYRange = (0 <= newY && newY < gameSize);
				if(isXRange && isYRange && buttonReachable(tile[newX][newY])) {
					if(tile[newX][newY].getValue() == 0)
						spreadZero(tile[newX][newY]);
					else
						showButton(tile[newX][newY]);
				}
			}
		}
	}
	
	private boolean buttonReachable(ExtendedButton button) {
		return button.getColor().equals("grey") || button.getColor().equals("yellow");
	}
	
	private void gameEnds() {
		System.out.println("The game is ended! Couse: lost");
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Game over!");
		alert.setHeaderText("You lost!");
		alert.setContentText("The game is over, because you have lost!");
		alert.showAndWait();
		Stage st = (Stage)gameGrid.getScene().getWindow();
		st.close();
	}
	
	private void checkIfGameIsEnded() {
		if(clearTiles.size() == 0) {
			System.out.println("The game is ended! Couse: win");
			for(int i = bombs.size() - 1; i >= 0; i--) markButton(bombs.get(i));
			tl.stop();
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("You won!");
			alert.setHeaderText("You won the game!");
			alert.setContentText("Congratulations\nYou won the game in: " + doTime());
			alert.showAndWait();
			Stage st = (Stage)gameGrid.getScene().getWindow();
			st.close();
		}
	}

}
