package test;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class Decorations {
	
	public static void initializeLabel(Label info) {
		info.setText("Use left click to place ship vertically "
                     + "and right click to place horizontally.\n"
                     + "Place where you click will be the top left corner of your ship.");
	}
	
	public static void createDecorationsForChoosingGameMode(BorderPane root, Button gameWithBot, Button gameWithAnotherPlayer) {
		root.setPrefSize(600, 800);
	    VBox btns = new VBox(30, gameWithBot, gameWithAnotherPlayer);
	    btns.setAlignment(Pos.CENTER);
	    root.setCenter(btns);
	}
	
	public static void createDecorationForChoosingGameDifficulty(BorderPane root, Button easyLevelBtn, Button hardLevelBtn, Label messageLbl) {
		root.getChildren().removeAll(root.getChildren()); //  должно чистить от предыдущих элементов
		HBox hbox = new HBox(50);
    	hbox.getChildren().addAll(easyLevelBtn, hardLevelBtn);
    	hbox.setPadding(new Insets(50, 0, 0, 210));
    	messageLbl.setText("Choose level of difficulty");
    	messageLbl.setFont(new Font("Arial", 20));
    	messageLbl.setPadding(new Insets(150, 0, 0, 185));
    	root.setTop(messageLbl);
    	root.setCenter(hbox);   
	}
	
	public static void createDecorationForGameWithBot(BorderPane root, Button randomLayoutBtn, Button clearBoardBtn, Button startGameBtn, 
														Board playerBoard, Label showInfoLbl, Label infoLbl) {
		root.getChildren().removeAll(root.getChildren());
    	showInfoLbl.setText("Player, please place your ships!");
    	VBox gameButtons = new VBox(30, randomLayoutBtn, clearBoardBtn, startGameBtn);
    	HBox hbox = new HBox(50);
    	hbox.getChildren().addAll(playerBoard, gameButtons);
    	VBox vbox = new VBox(30);
    	vbox.getChildren().addAll(showInfoLbl, hbox, infoLbl);
    	vbox.setPadding(new Insets(200, 0, 0, 60));
    	root.setCenter(vbox);
    }
	
	public static void createDecorationForGameWithAnotherPlayer(BorderPane root, Button randomLayout, Button clearBoard, Button moveToSecondPlayer,
			Label showInfo, Board playerBoard, Label info) {
		root.getChildren().removeAll(root.getChildren());
    	showInfo.setText("Player 1, please place your ships!");
    	VBox buttonsVBox = new VBox(30, randomLayout, clearBoard, moveToSecondPlayer);
    	HBox hbox = new HBox(50);
    	hbox.getChildren().addAll(playerBoard, buttonsVBox);
    	VBox vbox = new VBox(30);
    	vbox.getChildren().addAll(showInfo, hbox, info);
    	vbox.setPadding(new Insets(200, 0, 0, 60));
    	root.setCenter(vbox);
    }
	
	public static void createDecorationForGameWithAnotherPlayerStepTwo(BorderPane root, Button randomLayout, Button clearBoard, Button startGame,
			Label showInfo, Board enemyBoard, Label info) {
    	root.getChildren().removeAll(root.getChildren());
    	showInfo.setText("Player 2, please place your ships!");
    	VBox buttonsVBox = new VBox(30, randomLayout, clearBoard, startGame);
    	HBox hbox = new HBox(50);
    	hbox.getChildren().addAll(enemyBoard, buttonsVBox); 	
    	VBox vbox = new VBox(30);
    	vbox.getChildren().addAll(showInfo, hbox, info);
    	vbox.setPadding(new Insets(200, 0, 0, 60)); 
    	root.setCenter(vbox); 
    }
	
	public static void createDecorationForGameStart(BorderPane root, boolean twoPlayersMode, Board playerBoard, Board enemyBoard,
			Label showInfo, Label showInfo2, Label info) {
		root.getChildren().removeAll(root.getChildren());
    	if (!twoPlayersMode) {
	    	VBox boards = new VBox(50, enemyBoard, playerBoard);
	    	boards.setPadding(new Insets(60, 0, 0, 150));
	    	root.setCenter(boards);
    	}
    	else {
    		showInfo.setText("PLAYER 2");
    		showInfo2.setText("PLAYER 1");
    		info.setText("PLAYER'S 1 TURN!");
    		VBox boards = new VBox(20, showInfo, enemyBoard, info, playerBoard, showInfo2);
	    	boards.setPadding(new Insets(25, 0, 0, 150));
	    	root.setCenter(boards);
    	}
    }
	
	public static void createDecorationForGameEnd(BorderPane root, Label info) {
    	root.getChildren().removeAll(root.getChildren());
    	//info.setText("THE END!");
    	info.setAlignment(Pos.CENTER);
    	root.setCenter(info);
    }
}
