package plugins;

import battleshipGame.LanguageService;
import battleshipGame.Messages;

public class RussianLanguage implements LanguageService {
    
    public void setLanguage() {
		Messages.CHOOSE_GAME_MODE = "Выберете режим игры";
		Messages.GAME_WITH_BOT = "Игра с ботом";
		Messages.GAME_WITH_ANOTHER_PLAYER = "Игра с другим игроком";
		Messages.CHOOSE_LEVEL_OF_DIFFICULTY = "Выберете уровень сложности";
		Messages.EASY = "Лёгкий";
		Messages.HARD = "Сложный";
		Messages.RANDOM_LAYOUT = "Случайная расстановка";
		Messages.CLEAR_BOARD = "Очистить доску";
		Messages.START_GAME = "Начать игру";
		Messages.MOVE_TO_SECOND_PLAYER = "Перейти к игроку 2";
		Messages.LAYOUT_GUIDE = "Нажмите ЛКМ, чтобы поставить корабль вертикально\n"
				+ "и ПКМ, чтобы поставить горизонтально.\n"
				+ "Место, где вы нажмете, будет верхним левым углом корабля.";
		Messages.PLAYER_PLACE_YOUR_SHIPS = "Игрок, пожалуйста расставьте свои корабли!";
		Messages.WIN = "ПОБЕДИЛ!";
		Messages.PLAYER_2_WIN = "ИГРОК 2 ПОБЕДИЛ!";
		Messages.PLAYER_1_TURN = "ХОДИТ ИГРОК 1!";
		Messages.PLAYER_2_TURN = "ХОДИТ ИГРОК 2!";
		Messages.HARD_BOT = "КРУТОЙ БОТ";
		Messages.EASY_BOT = "ТУПОЙ БОТ";
		Messages.PLAYER_1 = "ИГРОК 1";
		Messages.PLAYER_2 = "ИГРОК 2";
		Messages.PLAYER_1_WIN = "ИГРОК 1 ПОБЕДИЛ!";
		Messages.YOU_WIN = "ВЫ ПОБЕДИЛИ!";
		Messages.PLAYER_1_PLACE_YOUR_SHIPS = "Игрок 1, пожалуйста расставьте корабли!";
		Messages.PLAYER_2_PLACE_YOUR_SHIPS = "Игрок 2, пожалуйста расставьте корабли!";
    }

    public String getLanguage() {
        return "Русский";
    }
}
