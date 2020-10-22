package plugins;

import battleshipGame.LanguageService;
import battleshipGame.Messages;

public class RussianLanguage implements LanguageService {
    
    public void setLanguage() {
    	Messages.CHOOSE_GAME_MODE = "�������� ����� ����";
		Messages.GAME_WITH_BOT = "���� � �����";
		Messages.GAME_WITH_ANOTHER_PLAYER = "���� � ������ �������";
		Messages.CHOOSE_LEVEL_OF_DIFFICULTY = "�������� ������� ���������";
		Messages.EASY = "˸����";
		Messages.HARD = "�������";
		Messages.RANDOM_LAYOUT = "��������� �����������";
		Messages.CLEAR_BOARD = "�������� �����";
		Messages.START_GAME = "������ ����";
		Messages.MOVE_TO_SECOND_PLAYER = "������� � ������ 2";
		Messages.LAYOUT_GUIDE = "������� ���, ����� ��������� ������� �����������\n"
				+ "� ���, ����� ��������� �������������.\n"
				+ "�����, ��� �� �������, ����� ������� ����� ����� �������.";
		Messages.PLAYER_PLACE_YOUR_SHIPS = "�����, ���������� ���������� ���� �������!";
		Messages.WIN = "�������!";
		Messages.PLAYER_2_WIN = "����� 2 �������!";
		Messages.PLAYER_1_TURN = "����� ����� 1!";
		Messages.PLAYER_2_TURN = "����� ����� 2!";
		Messages.HARD_BOT = "������ ���";
		Messages.EASY_BOT = "����� ���";
		Messages.PLAYER_1 = "����� 1";
		Messages.PLAYER_2 = "����� 2";
		Messages.PLAYER_1_WIN = "����� 1 �������!";
		Messages.YOU_WIN = "�� ��������!";
		Messages.PLAYER_1_PLACE_YOUR_SHIPS = "����� 1, ���������� ���������� �������!";
		Messages.PLAYER_2_PLACE_YOUR_SHIPS = "����� 2, ���������� ���������� �������!";
    }

    public String getLanguage() {
        return "�������";
    }
}
