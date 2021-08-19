package battleshipGame;

import javax.swing.text.PlainDocument;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class Decorations {
	
	public static void initializeLabel(Label info) {
		info.setText("Use left click to place ship vertically "
                     + "and right click to place horizontally.\n"
                     + "Place where you click will be the top left corner of your ship.");
	}
	
	public static void setPrefSizeOfGameWindow(Pane root) {
		root.setPrefSize(620, 800);
	}
	
	public static void createDecorationsForChoosingLanguage(Pane root, ComboBox<String> langBox, Label info) {
		info.setFont(new Font("Arial", 20));
		info.setText("Choose language");
		info.setPrefSize(200, 40);
		info.setLayoutX(root.getPrefWidth() / 2 - info.getPrefWidth() / 3);
		info.setLayoutY(root.getPrefHeight() / 4);
		root.getChildren().add(info);
		
		langBox.setPrefSize(150, 40);
		langBox.setLayoutX(info.getLayoutX());
		langBox.setLayoutY(info.getLayoutY() + info.getPrefHeight() + 30);
		root.getChildren().add(langBox);
	}
	
	public static void createDecorationsForChoosingGameMode(Pane root, Button gameWithBot, Button gameWithAnotherPlayer) {
		root.getChildren().removeAll(root.getChildren());
	    VBox btns = new VBox(30, gameWithBot, gameWithAnotherPlayer);
	    btns.setAlignment(Pos.CENTER);
	    btns.setLayoutX(root.getPrefWidth() / 3);
	    btns.setLayoutY(root.getPrefHeight() / 4 + 70);
	    root.getChildren().add(btns);
	}
	
	public static void createDecorationForChoosingGameDifficulty(Pane root, Button easyLevelBtn, Button hardLevelBtn, Label messageLbl) {
		root.getChildren().removeAll(root.getChildren()); 
		
		messageLbl.setFont(new Font("Arial", 20));
		messageLbl.setText(Messages.CHOOSE_LEVEL_OF_DIFFICULTY);
		messageLbl.setPrefSize(290, 40);
		messageLbl.setLayoutX(root.getPrefWidth() / 2 - messageLbl.getPrefWidth() / 2);
		messageLbl.setLayoutY(root.getPrefHeight() / 4);
		
		HBox hbox = new HBox(50);
    	hbox.getChildren().addAll(easyLevelBtn, hardLevelBtn);
    	hbox.setLayoutX(root.getPrefWidth() / 3);
    	hbox.setLayoutY(messageLbl.getLayoutY() + messageLbl.getPrefHeight() + 30);
    	
    	root.getChildren().add(messageLbl);
    	root.getChildren().add(hbox);   
	}
	
	public static void createDecorationForGameWithBot(Pane root, Button randomLayoutBtn, Button clearBoardBtn, Button startGameBtn, 
														Board playerBoard, Label showInfoLbl, Label infoLbl) {
		root.getChildren().removeAll(root.getChildren());
    	
    	showInfoLbl.setText(Messages.PLAYER_PLACE_YOUR_SHIPS);
    	showInfoLbl.setLayoutX(root.getPrefWidth() / 10);
    	showInfoLbl.setLayoutY(root.getPrefHeight() / 5);
    	root.getChildren().add(showInfoLbl);
    	
    	VBox gameButtons = new VBox(30, randomLayoutBtn, clearBoardBtn, startGameBtn);
    	HBox mapAndButtons = new HBox(50, playerBoard, gameButtons);
    	mapAndButtons.setLayoutX(showInfoLbl.getLayoutX());
    	mapAndButtons.setLayoutY(showInfoLbl.getLayoutY() + showInfoLbl.getPrefHeight() + 50);
    	root.getChildren().add(mapAndButtons);
    	
    	infoLbl.setFont(new Font("Arial", 15));
    	infoLbl.setText(Messages.LAYOUT_GUIDE);
    	infoLbl.setPrefSize(450, 70);
    	infoLbl.setLayoutX(mapAndButtons.getLayoutX());
    	infoLbl.setLayoutY(mapAndButtons.getLayoutY() + 350);
    	root.getChildren().add(infoLbl);
    }
	
	public static void createDecorationForGameWithAnotherPlayer(Pane root, Button randomLayout, Button clearBoard, Button moveToSecondPlayer,
			Label showInfo, Board playerBoard, Label info) {
		
		root.getChildren().removeAll(root.getChildren());
    	
    	showInfo.setText(Messages.PLAYER_1_PLACE_YOUR_SHIPS);
    	showInfo.setLayoutX(root.getPrefWidth() / 10);
    	showInfo.setLayoutY(root.getPrefHeight() / 5);
    	root.getChildren().add(showInfo);
    	
    	VBox gameButtons = new VBox(30, randomLayout, clearBoard, moveToSecondPlayer);
    	HBox mapAndButtons = new HBox(50, playerBoard, gameButtons);
    	mapAndButtons.setLayoutX(showInfo.getLayoutX());
    	mapAndButtons.setLayoutY(showInfo.getLayoutY() + showInfo.getPrefHeight() + 50);
    	root.getChildren().add(mapAndButtons);
    	
    	info.setFont(new Font("Arial", 15));
    	info.setText(Messages.LAYOUT_GUIDE);
    	info.setPrefSize(450, 70);
    	info.setLayoutX(mapAndButtons.getLayoutX());
    	info.setLayoutY(mapAndButtons.getLayoutY() + 350);
    	root.getChildren().add(info);
		
    }
	
	public static void createDecorationForGameWithAnotherPlayerStepTwo(Pane root, Button randomLayout, Button clearBoard, Button startGame,
			Label showInfo, Board enemyBoard, Label info) {
		
		root.getChildren().removeAll(root.getChildren());
    	
    	showInfo.setText(Messages.PLAYER_2_PLACE_YOUR_SHIPS);
    	showInfo.setLayoutX(root.getPrefWidth() / 10);
    	showInfo.setLayoutY(root.getPrefHeight() / 5);
    	root.getChildren().add(showInfo);
    	
    	VBox gameButtons = new VBox(30, randomLayout, clearBoard, startGame);
    	HBox mapAndButtons = new HBox(50, enemyBoard, gameButtons);
    	mapAndButtons.setLayoutX(showInfo.getLayoutX());
    	mapAndButtons.setLayoutY(showInfo.getLayoutY() + showInfo.getPrefHeight() + 50);
    	root.getChildren().add(mapAndButtons);
    	
    	info.setFont(new Font("Arial", 15));
    	info.setText(Messages.LAYOUT_GUIDE);
    	info.setPrefSize(450, 70);
    	info.setLayoutX(mapAndButtons.getLayoutX());
    	info.setLayoutY(mapAndButtons.getLayoutY() + 350);
    	root.getChildren().add(info);
		
    }
	
	public static void createDecorationForGameStart(Pane root, boolean twoPlayersMode, Board playerBoard, Board enemyBoard,
			Label showInfo, Label showInfo2, Label info) {
		root.getChildren().removeAll(root.getChildren());
    	if (!twoPlayersMode) {
	    	VBox boards = new VBox(50, enemyBoard, playerBoard);
	    	boards.setPadding(new Insets(60, 0, 0, 150));
	    	root.getChildren().add(boards);
    	}
    	else {
    		showInfo.setText(Messages.PLAYER_2);
    		showInfo2.setText(Messages.PLAYER_1);
    		info.setPrefHeight(30);
    		info.setText(Messages.PLAYER_1_TURN);
    		VBox boards = new VBox(20, showInfo, enemyBoard, info, playerBoard, showInfo2);
	    	boards.setPadding(new Insets(25, 0, 0, 150));
	    	root.getChildren().add(boards);
    	}
    }
	
	public static void createDecorationForGameEnd(Pane root, Label info) {
    	root.getChildren().removeAll(root.getChildren());
    	info.setLayoutX(root.getPrefWidth() / 2 - 50);
    	info.setLayoutY(root.getPrefHeight() / 2 - 40);
    	root.getChildren().add(info);
    }
}
