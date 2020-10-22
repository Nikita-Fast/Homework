package plugins;

import battleshipGame.LanguageService;
import battleshipGame.Messages;

public class EnglishLanguage implements LanguageService {
	
	public void setLanguage() {
		Messages.CHOOSE_GAME_MODE = "Choose game mode";
		Messages.GAME_WITH_BOT = "Game with bot";
		Messages.GAME_WITH_ANOTHER_PLAYER = "Game with another player";
		Messages.CHOOSE_LEVEL_OF_DIFFICULTY = "Choose level of difficulty";
		Messages.EASY = "Easy";
		Messages.HARD = "Hard";
		Messages.RANDOM_LAYOUT = "Random layout";
		Messages.CLEAR_BOARD = "Clear board";
		Messages.START_GAME = "Start game";
		Messages.MOVE_TO_SECOND_PLAYER = "Move to player 2";
		Messages.LAYOUT_GUIDE = "Use left click to place ship vertically and\n"
				+ "right click to place horizontally.\n"
				+ "Place where you click will be the top left corner of your ship.";
		Messages.PLAYER_PLACE_YOUR_SHIPS = "Player, please place your ships!";
		Messages.WIN = "WIN!";
		Messages.PLAYER_2_WIN = "PLAYER 2 WIN";
		Messages.PLAYER_1_TURN = "PLAYER'S 1 TURN!";
		Messages.PLAYER_2_TURN = "PLAYER'S 2 TURN!";
		Messages.HARD_BOT = "HARD BOT";
		Messages.EASY_BOT = "EASY BOT";
		Messages.PLAYER_1 = "PLAYER 1";
		Messages.PLAYER_2 = "PLAYER 2";
		Messages.PLAYER_1_WIN = "PLAYER 1 WIN";
		Messages.YOU_WIN = "YOU WIN";
		Messages.PLAYER_1_PLACE_YOUR_SHIPS = "Player 1, please place your ships!";
		Messages.PLAYER_2_PLACE_YOUR_SHIPS = "Player 2, please place your ships!";
	}
	
	public String getLanguage() {
		return "English";
	}
}
