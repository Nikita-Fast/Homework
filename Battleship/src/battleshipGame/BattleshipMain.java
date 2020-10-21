package battleshipGame;

import java.util.Random;

import Players.BasicPlayer;
import Players.EasyBot;
import Players.HardBot;
import Players.Human;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class BattleshipMain extends Application {
	//private Random random = new Random();
	
	private int idk = 0;
	
	private Difficulty gameDifficulty;	
	private Button gameWithBot = new Button();
	private Button gameWithAnotherPlayer = new Button();
	private Button easyLevel = new Button();
    private Button hardLevel = new Button(); 
    private Button randomLayout = new Button();
    private Button clearBoard = new Button();
    private Button startGame = new Button();
    private Button moveToSecondPlayer = new Button();
    private boolean running = false;
    private boolean enemyTurn = false;
    private boolean twoPlayersMode;
	private boolean nowIsSecondPlayer;
    private Board enemyBoard;
    private Board playerBoard;    
    private Label chooseDifficulty = new Label();
    private Label showInfo2 = new Label();
    private Label showInfo = new Label();
    private Label info = new Label();
    
    private BasicPlayer enemy;
    private BasicPlayer player;
    
    private Parent createContent() {   	
	   BorderPane root = new BorderPane();
	   Decorations.initializeLabel(info);
	   Decorations.createDecorationsForChoosingGameMode(root, gameWithBot, gameWithAnotherPlayer);
	   
	   player = new Human();
	   
	   easyLevel.setText("Easy");
	   easyLevel.setOnAction(event -> {
		   enemy = new EasyBot();
		   gameDifficulty = Difficulty.easy;
		   Decorations.createDecorationForGameWithBot(root, randomLayout, clearBoard, startGame, playerBoard, showInfo, info);
	   });
	   
	   hardLevel.setText("Hard");
	   hardLevel.setOnAction(event -> {
		   enemy = new HardBot();
		   gameDifficulty = Difficulty.hard;
		   Decorations.createDecorationForGameWithBot(root, randomLayout, clearBoard, startGame, playerBoard, showInfo, info);
	   });

	   gameWithAnotherPlayer.setText("Game With Another Player");
	   gameWithAnotherPlayer.setOnAction(event -> {
		   enemy = new Human();
		   twoPlayersMode = true;
		   Decorations.createDecorationForGameWithAnotherPlayer(root, randomLayout, clearBoard, moveToSecondPlayer, showInfo, playerBoard, info);
	   });
       
       gameWithBot.setText("Game With Bot");
       gameWithBot.setOnAction(event -> {
    	   twoPlayersMode = false;
    	   Decorations.createDecorationForChoosingGameDifficulty(root, easyLevel, hardLevel, chooseDifficulty);   
       });
       
       moveToSecondPlayer.setText("Move To Player 2");
       moveToSecondPlayer.setOnAction(event -> {
    	   if (playerBoard.getShipsToPlaceNumber() == 0) {
	    	   nowIsSecondPlayer = true;
	    	   Decorations.createDecorationForGameWithAnotherPlayerStepTwo(root, randomLayout, clearBoard, startGame, showInfo, enemyBoard, info);
    	   }
       });
       
       randomLayout.setText("Random Layout");
       randomLayout.setOnAction(event -> {
    	   if (!running) {
    		   if (!nowIsSecondPlayer) {                    
    			   playerBoard.placeShipsRandomly();
    		   }
    		   else {
    			   enemyBoard.placeShipsRandomly();
    		   }
    	   }
       });
       
       startGame.setText("Start Game!");
       startGame.setOnAction(event -> {          
    	   if (!twoPlayersMode) {
    		   if (playerBoard.getShipsToPlaceNumber() == 0) {
        		   startGame(root);
        		   enemyBoard.hideShipsFromEnemy();
           	   } 
    	   }
    	   else {
    		   if (enemyBoard.getShipsToPlaceNumber() == 0) {
    			   startGame(root);
    			   playerBoard.hideShipsFromEnemy();
    			   enemyBoard.hideShipsFromEnemy();
    		   }
    	   }
       });
       
       clearBoard.setText("Clear Board");
       clearBoard.setOnAction(event -> {
    	   if (!running) {
    		   if (!nowIsSecondPlayer) {
    			   playerBoard.clearBoard();
    		   }
    		   else {
    			   enemyBoard.clearBoard();
    		   }
       	   }
       });  
         
	   enemyBoard = new Board(event -> {	
			if (!running) {
				if (twoPlayersMode && nowIsSecondPlayer) {
					if (enemyBoard.getShipsToPlaceNumber() <= 0) {
		            	return;
		            } 
					enemyBoard.placeShipByMouseClickOnBoard(event);				
				}				
				return;
			}	
			if (!playerBoard.getBotFinishedMove()) {
				return;
			}
			
			Cell cell = (Cell) event.getSource();
			if (cell.getWasShotState() || enemyTurn) {   
				return;
			} 
			
			((Human)player).setCellForShot(cell);
			
			player.makeMoveWithAnimation(enemyBoard);
			enemyTurn = !cell.shotWasSuccessfull();
			
			if (enemyTurn) {
				info.setText("PLAYER'S 2 TURN!");
			}
			if (enemyBoard.getShipsSurvivedNumber() == 0) {
				if (!twoPlayersMode) {
					info.setText("YOU WIN");
				}
				else {
					info.setText("PLAYER 1 WIN");
				}
				Decorations.createDecorationForGameEnd(root, info);
		    }            
			
			if (enemyTurn && !twoPlayersMode) {
				if (gameDifficulty == Difficulty.easy) {
					
					enemy.makeMoveWithAnimation(playerBoard); 
					
					if (playerBoard.getShipsSurvivedNumber() == 0) {
						endTheGame(root, info, "HARD BOT");
					}
					changePlayerMakingMove();
				}
				if (gameDifficulty == Difficulty.hard) {
					enemy.makeMoveWithAnimation(playerBoard);
					
					if (playerBoard.getShipsSurvivedNumber() == 0) {
						endTheGame(root, info, "HARD BOT");
					}
					changePlayerMakingMove();
				}
		    }            
		});            
	
        playerBoard = new Board(event -> {
            if (running) {            	
            	if (twoPlayersMode && enemyTurn) {
            		Cell cell = (Cell) event.getSource();
        			if (cell.getWasShotState()) {   
        				return;
        			}
        			
        			((Human)enemy).setCellForShot(cell);
        			enemy.makeMoveWithAnimation(playerBoard);
        			enemyTurn = cell.shotWasSuccessfull();
        			
        			if (!enemyTurn) {
        				info.setText("PLAYER'S 1 TURN!");
        			}
        			if (playerBoard.getShipsSurvivedNumber() == 0) {
        				info.setText("PLAYER 2 WIN");
        				Decorations.createDecorationForGameEnd(root, info);
        		    } 
            	}            	
            	return;
            }
            if (playerBoard.getShipsToPlaceNumber() <= 0) {
            	return;
            }
            playerBoard.placeShipByMouseClickOnBoard(event);
        });           
        return root;
    }
    
    private void changePlayerMakingMove() {
    	enemyTurn = !enemyTurn;
    }
    
    private void endTheGame(BorderPane root, Label infoLbl, String winnerName) {
    	infoLbl.setText(winnerName + " WIN!");
    	Decorations.createDecorationForGameEnd(root, info);
    }
    
    private void startGame(BorderPane root) { 
    	Decorations.createDecorationForGameStart(root, twoPlayersMode, playerBoard, enemyBoard, showInfo, showInfo2, info);
    	if (!twoPlayersMode) {
    		enemyBoard.placeShipsRandomly();
    	}
        running = true;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createContent());
        primaryStage.setTitle("Battleship");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {    	
        launch(args);
    }
}
