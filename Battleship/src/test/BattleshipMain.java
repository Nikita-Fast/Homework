package test;

import java.util.Random;

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
import test.Board.Cell;

public class BattleshipMain extends Application {
		
	VBox gameModeButtons;
	VBox gameButtons;
	
	//private boolean gameModeChose;
	
	private Difficulty gameDifficulty;
	
	private boolean twoPlayersMode;
	private boolean nowIsSecondPlayer;
	private Button gameWithBot = new Button();
	private Button gameWithAnotherPlayer = new Button();
	
    private boolean running = false;
    private Board enemyBoard, playerBoard;
    
    private Button easyLevel = new Button();
    private Button hardLevel = new Button();
    
    private Button randomLayout = new Button();
    private Button startGame = new Button();
    private Button clearBoard = new Button();
    private Button moveToSecondPlayer = new Button();
    
    private Label chooseDifficulty = new Label();
    private Label showInfo2 = new Label();
    private Label showInfo = new Label();
    private Label info = new Label("Use left click to place ship vertically "
			                     + "and right click to place horizontally.\n"
			                     + "Place where you click will be the top left corner of your ship.");

    
    private int type = 4;

    private boolean enemyTurn = false;

    private Random random = new Random();
    
    private void createDecorationForChoosingGameDifficulty(BorderPane root) {
    	HBox hbox = new HBox(50);
    	hbox.getChildren().addAll(easyLevel, hardLevel);
    	hbox.setPadding(new Insets(50, 0, 0, 210));
    	chooseDifficulty.setText("Choose level of difficulty");
    	chooseDifficulty.setFont(new Font("Arial", 20));
    	chooseDifficulty.setPadding(new Insets(150, 0, 0, 185));
    	root.setTop(chooseDifficulty);
    	root.setCenter(hbox);   	
    }

    private void createDecorationForGameWithBot(BorderPane root) {
    	
    	showInfo.setText("Player, please place your ships!");
    	gameButtons = new VBox(30, randomLayout, clearBoard, startGame);
    	
    	HBox hbox = new HBox(50);
    	hbox.getChildren().addAll(playerBoard, gameButtons);
    	
    	
    	VBox vbox = new VBox(30);
    	vbox.getChildren().addAll(showInfo, hbox, info);
    	vbox.setPadding(new Insets(200, 0, 0, 60));
    	
    	root.setCenter(vbox);
    }
    
    private void createDecorationForGameWithAnotherPlayer(BorderPane root) {
    	showInfo.setText("Player 1, please place your ships!");
    	
    	VBox buttonsVBox = new VBox(30, randomLayout, clearBoard, moveToSecondPlayer);
    	
    	HBox hbox = new HBox(50);
    	hbox.getChildren().addAll(playerBoard, buttonsVBox);
    	
    	
    	VBox vbox = new VBox(30);
    	vbox.getChildren().addAll(showInfo, hbox, info);
    	vbox.setPadding(new Insets(200, 0, 0, 60));
    	
    	root.setCenter(vbox);
    }
    
    private void createDecorationForGameWithAnotherPlayerStepTwo(BorderPane root) {
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
    
    private void createDecorationForGameStart(BorderPane root) {
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
    
    private void createDecorationForGameEnd(BorderPane root) {
    	root.getChildren().removeAll(root.getChildren());
    	//info.setText("THE END!");
    	info.setAlignment(Pos.CENTER);
    	root.setCenter(info);
    }
   
    
    private void removeGameModeButtons(BorderPane root) {
    	if (root.getChildren().contains(gameModeButtons)) {
    		root.getChildren().remove(gameModeButtons);
    	}
    }
    
    private Parent createContent() {   	
	   BorderPane root = new BorderPane();
	   root.setPrefSize(600, 800);
	    
	   gameModeButtons = new VBox(30, gameWithBot, gameWithAnotherPlayer);
	   gameModeButtons.setAlignment(Pos.CENTER);
	   root.setCenter(gameModeButtons);
	   
	   
	   
	   
	   
	   
	   easyLevel.setText("Easy");
	   easyLevel.setOnAction(event -> {
		   gameDifficulty = Difficulty.easy;
		   root.getChildren().removeAll(root.getChildren());
		   createDecorationForGameWithBot(root);
	   });
	   
	   hardLevel.setText("Hard");
	   hardLevel.setOnAction(event -> {
		   gameDifficulty = Difficulty.hard;
		   root.getChildren().removeAll(root.getChildren());
		   createDecorationForGameWithBot(root);
	   });

	   
	   
	   
	   
	   
	   gameWithAnotherPlayer.setText("Game With Another Player");
	   gameWithAnotherPlayer.setOnAction(event -> {
		   twoPlayersMode = true;
		   //gameModeChose = true;
		   removeGameModeButtons(root);
		   createDecorationForGameWithAnotherPlayer(root);
	   });
       
       gameWithBot.setText("Game With Bot");
       gameWithBot.setOnAction(event -> {
    	   twoPlayersMode = false;
    	   //gameModeChose = true;
    	   removeGameModeButtons(root);
    	   
    	   createDecorationForChoosingGameDifficulty(root);
    	   //createDecorationForGameWithBot(root);	   
       });
       
       moveToSecondPlayer.setText("Move To Player 2");
       moveToSecondPlayer.setOnAction(event -> {
    	   if (playerBoard.shipsToPlace == 0) {
	    	   nowIsSecondPlayer = true;
	    	   type = 4;
	    	   createDecorationForGameWithAnotherPlayerStepTwo(root);
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
    			   System.out.println("player 2 random ships");
    		   }
    	   }
       });
       
       startGame.setText("Start Game!");
       startGame.setOnAction(event -> {          
    	   
    	   if (!twoPlayersMode) {
    		   if (playerBoard.shipsToPlace == 0) {
        		   startGame(root);
        		   enemyBoard.hideShipsFromEnemy();
           	   } 
    	   }
    	   else {
    		   if (enemyBoard.shipsToPlace == 0) {
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
    			   type = 4;
    		   }
    		   else {
    			   enemyBoard.clearBoard();
    			   type = 4;
    		   }
       	   }
       });  
       
          
	   enemyBoard = new Board(true, event -> {
	        	
			if (!running) {
				if (twoPlayersMode && nowIsSecondPlayer) {
					if (enemyBoard.shipsToPlace <= 0) {
		            	return;
		            }
		                
		            Cell cell = (Cell) event.getSource();
		            
		            if (enemyBoard.placeShip(new Ship(type, event.getButton() == MouseButton.PRIMARY), cell.x, cell.y)) { //переместить type в board чтобы не париться с его изменением до 4х
		            	System.out.println("placed!");
		            	
		            	System.out.println(enemyBoard.shipsToPlace);
		            	
		            	if (enemyBoard.shipsToPlace == 9 || enemyBoard.shipsToPlace == 7 || 
		            			enemyBoard.shipsToPlace == 4 || enemyBoard.shipsToPlace == 0) {
		            		type--;
		            	}
		            }					
				}				
				return;
			}
						
			Cell cell = (Cell) event.getSource();
			if (cell.wasShot || enemyTurn) {   // changed here!!!
				return;
			}
			                
			enemyTurn = !cell.shoot();
			
			if (enemyTurn) {
				info.setText("PLAYER'S 2 TURN!");
			}
			
			if (enemyBoard.shipsSurvived == 0) {
				if (!twoPlayersMode) {
					//System.out.println("YOU WIN");
					info.setText("YOU WIN");
				}
				else {
					//System.out.println("PLAYER 1 WIN");
					info.setText("PLAYER 1 WIN");
				}
			    createDecorationForGameEnd(root);
		    }            
			
			if (enemyTurn && !twoPlayersMode) {
				if (gameDifficulty == Difficulty.easy) {
					enemyMoveEasy(root);
				}
				if (gameDifficulty == Difficulty.hard) {
					enemyMoveHard(root);
				}
		    }            
		                
		});            
	
        playerBoard = new Board(false, event -> {
            if (running) {
            	
            	if (twoPlayersMode && enemyTurn) {
            		Cell cell = (Cell) event.getSource();
        			if (cell.wasShot) {   
        				return;
        			}
        			
        			enemyTurn = cell.shoot();
        			
        			if (!enemyTurn) {
        				info.setText("PLAYER'S 1 TURN!");
        			}
        			
        			if (playerBoard.shipsSurvived == 0) {
        				//System.out.println("PLAYER 2 WIN");
        				info.setText("PLAYER 2 WIN");
        			    createDecorationForGameEnd(root);
        		    } 
            	}            	
            	return;
            }
            
            if (playerBoard.shipsToPlace <= 0) {
            	return;
            }
                
            Cell cell = (Cell) event.getSource();
            
            if (playerBoard.placeShip(new Ship(type, event.getButton() == MouseButton.PRIMARY), cell.x, cell.y)) {
            	System.out.println("placed!");
            	
            	System.out.println(playerBoard.shipsToPlace);
            	
            	if (playerBoard.shipsToPlace == 9 || playerBoard.shipsToPlace == 7 || 
            			playerBoard.shipsToPlace == 4 || playerBoard.shipsToPlace == 0) {
            		type--;
            	}
            }
        });
	                
        return root;
    }
    
    private void enemyMoveEasy(BorderPane root) {
    	while (enemyTurn) {
            int x = random.nextInt(10);
            int y = random.nextInt(10);

            Cell cell = playerBoard.getCell(x, y);
            if (cell.wasShot) {
                continue;
            }

            enemyTurn = cell.shoot();

            if (playerBoard.shipsSurvived == 0) {
                //System.out.println("BOT WIN");
            	info.setText("BOT WIN");
                createDecorationForGameEnd(root);
                return;
            }
        }
    }

    private void enemyMoveHard(BorderPane root) {
    	while (enemyTurn) {
    		playerBoard.killDamagedShip();
    		
    		if (Math.random() < 0.29) {  
    			playerBoard.almostKillSpecifiedShip(playerBoard.detectRandomShip());
    		}
    		            
    		playerBoard.makeShotToVoidIfPossible();
    		enemyTurn = false;

            if (playerBoard.shipsSurvived == 0) {
                //System.out.println("BOT WIN");
            	info.setText("BOT WIN");
                createDecorationForGameEnd(root);
                return;
            }
        }
    }

    private void startGame(BorderPane root) { 
    	root.getChildren().removeAll(root.getChildren());
    	createDecorationForGameStart(root);
    	
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
