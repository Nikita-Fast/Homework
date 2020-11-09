package battleshipGame;

import java.util.ArrayList;
import java.util.Random;
import java.util.ServiceLoader;

import Players.BasicPlayer;
import Players.EasyBot;
import Players.HardBot;
import Players.Human;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class BattleshipMain extends Application {

	private AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);

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
    
    private ComboBox<String> langBox = new ComboBox<String>();
    private static ServiceLoader<LanguageService> serviceLoader;
    private static ArrayList<String> plugNames;
    
    private void initPlugins() {
        serviceLoader = ServiceLoader.load(LanguageService.class);
        plugNames = new ArrayList<String>();
        for (LanguageService localeService : serviceLoader) {
            plugNames.add(localeService.getLanguage());
        }
    }
    
    private void setLanguage(String langStr) {
        for (LanguageService langService : serviceLoader) {
            if (langService.getLanguage().equals(langStr)) {
                langService.setLanguage();
                break;
            }
        }
        setButtonNames();
    }
    
    private void setButtonNames() {
    	gameWithAnotherPlayer.setText(Messages.GAME_WITH_ANOTHER_PLAYER);
    	gameWithBot.setText(Messages.GAME_WITH_BOT);
    	easyLevel.setText(Messages.EASY);
    	hardLevel.setText(Messages.HARD);
    	randomLayout.setText(Messages.RANDOM_LAYOUT);
    	startGame.setText(Messages.START_GAME);
    	moveToSecondPlayer.setText(Messages.MOVE_TO_SECOND_PLAYER);
    	clearBoard.setText(Messages.CLEAR_BOARD);
    }
    
    
    private Parent createContent() {
	   Pane root = context.getBean("pane", Pane.class);//new Pane();
	   Decorations.setPrefSizeOfGameWindow(root);	   
	   player = context.getBean("human", Human.class);//new Human();
	   
	   initPlugins();
	   langBox.setPromptText("Languages");
	   langBox.setItems(FXCollections.observableArrayList(plugNames));
	   Decorations.createDecorationsForChoosingLanguage(root, langBox, info); 
	   
	   langBox.setOnAction(actionEvent -> {
		   setLanguage(langBox.getValue().toString());
		   Decorations.initializeLabel(info);
		   Decorations.createDecorationsForChoosingGameMode(root, gameWithBot, gameWithAnotherPlayer);
	   });
	   
	   
	   //easyLevel.setText("Easy");
	   easyLevel.setOnAction(event -> {
		   enemy = context.getBean("easyBot", EasyBot.class);//new EasyBot();
		   gameDifficulty = Difficulty.easy;
		   Decorations.createDecorationForGameWithBot(root, randomLayout, clearBoard, startGame, playerBoard, showInfo, info);
	   });
	   
	   //hardLevel.setText("Hard");
	   hardLevel.setOnAction(event -> {
		   enemy = context.getBean("hardBot", HardBot.class);//new HardBot();
		   gameDifficulty = Difficulty.hard;
		   Decorations.createDecorationForGameWithBot(root, randomLayout, clearBoard, startGame, playerBoard, showInfo, info);
	   });

	   //gameWithAnotherPlayer.setText("Game With Another Player");
	   gameWithAnotherPlayer.setOnAction(event -> {
		   enemy = context.getBean("human", Human.class);//new Human();
		   twoPlayersMode = true;
		   Decorations.createDecorationForGameWithAnotherPlayer(root, randomLayout, clearBoard, moveToSecondPlayer, showInfo, playerBoard, info);
	   });
       
       //gameWithBot.setText("Game With Bot");
       gameWithBot.setOnAction(event -> {
    	   twoPlayersMode = false;
    	   Decorations.createDecorationForChoosingGameDifficulty(root, easyLevel, hardLevel, chooseDifficulty);   
       });
       
       //moveToSecondPlayer.setText("Move To Player 2");
       moveToSecondPlayer.setOnAction(event -> {
    	   if (playerBoard.getShipsToPlaceNumber() == 0) {
	    	   nowIsSecondPlayer = true;
	    	   Decorations.createDecorationForGameWithAnotherPlayerStepTwo(root, randomLayout, clearBoard, startGame, showInfo, enemyBoard, info);
    	   }
       });
       
       //randomLayout.setText("Random Layout");
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
       
       //startGame.setText("Start Game!");
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
       
       //clearBoard.setText("Clear Board");
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
				info.setText(Messages.PLAYER_2_TURN);
			}
			if (enemyBoard.getShipsSurvivedNumber() == 0) {
				if (!twoPlayersMode) {
					info.setText(Messages.YOU_WIN);
				}
				else {
					info.setText(Messages.PLAYER_1_WIN);
				}
				Decorations.createDecorationForGameEnd(root, info);
		    }            
			
			if (enemyTurn && !twoPlayersMode) {
				if (gameDifficulty == Difficulty.easy) {
					
					enemy.makeMoveWithAnimation(playerBoard); 
					
					if (playerBoard.getShipsSurvivedNumber() == 0) {
						endTheGame(root, info, Messages.EASY_BOT);
					}
					changePlayerMakingMove();
				}
				if (gameDifficulty == Difficulty.hard) {
					enemy.makeMoveWithAnimation(playerBoard);
					
					if (playerBoard.getShipsSurvivedNumber() == 0) {
						endTheGame(root, info, Messages.HARD_BOT);
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
        				info.setText(Messages.PLAYER_1_TURN);
        			}
        			if (playerBoard.getShipsSurvivedNumber() == 0) {
        				info.setText(Messages.PLAYER_2_WIN);
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
    
    private void endTheGame(Pane root, Label infoLbl, String winnerName) {
    	infoLbl.setText(winnerName + " " + Messages.WIN);
    	Decorations.createDecorationForGameEnd(root, info);
    }
    
    private void startGame(Pane root) { 
    	Decorations.createDecorationForGameStart(root, twoPlayersMode, playerBoard, enemyBoard, showInfo, showInfo2, info);
    	if (!twoPlayersMode) {
    		enemyBoard.placeShipsRandomly();
    	}
        running = true;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Scene scene = new Scene(createContent());
		Scene scene = context.getBean("scene", Scene.class);
		scene.setRoot(createContent());

        primaryStage.setTitle("Battleship");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
    	launch(args);
    }
}
