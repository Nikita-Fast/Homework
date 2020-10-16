package application;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import math.Curve;

public class Controller {
	private Pane pane = new Pane();
	private Board board;
	private Button btn = new Button("Draw");
	private Button clearBtn = new Button("Clear Board");
	private Button decreaseScale = new Button("Scale--");
	private Button increaseScale = new Button("Scale++");
	
	public Controller(Stage stage) {
		initStageController(stage);
	}
	
	private void initStageController(Stage stage) {
		
		board = new Board(pane);
		pane.getChildren().add(board);
		/*
		AnchorPane root = new AnchorPane(pane, btn, clearBtn);
		root.setLeftAnchor(btn, 20.0);
		root.setRightAnchor(pane, 20.0);
		root.setBottomAnchor(clearBtn, 20.0);*/
		
		BorderPane root = new BorderPane();
		VBox buttons = new VBox(20, btn, clearBtn, decreaseScale, increaseScale);
		root.setLeft(buttons);
		root.setCenter(pane);
		
		Scene scene = new Scene(root, 600 , 600);
		stage.setScene(scene);
		stage.show();
		
		btn.setOnAction(event -> {
			//board.drawCurve();
			board.drawUserCurve(board.NUMBER_OF_CURVE);;
		});
		
		clearBtn.setOnAction(event -> {
			board.clearBoard();
		});
		
		decreaseScale.setOnAction(event -> {
			board.decreaseScale();
		});
		
		increaseScale.setOnAction(event -> {
			board.increaseScale();
		});
	}
}
