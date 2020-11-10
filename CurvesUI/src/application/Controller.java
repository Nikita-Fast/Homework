package application;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import math.Curve;
import math.Ellipse;
import math.Hyperbola;
import math.Parabola;

public class Controller {
	private Pane pane = new Pane();
	private Board board;
	private Button decreaseScale = new Button("Scale--");
	private Button increaseScale = new Button("Scale++");
	private ComboBox<Curve> curveBox = new ComboBox<Curve>();
	
	public Controller(Stage stage) {
		initStageController(stage);
		stage.setResizable(false);
	}
	
	private void initStageController(Stage stage) {
		
		board = new Board(pane);
		pane.getChildren().add(board);
		
		BorderPane root = new BorderPane();
		VBox buttons = new VBox(20, curveBox, decreaseScale, increaseScale);
		buttons.setPadding(new Insets(0,0,0,30));
		HBox hBox = new HBox(30, buttons, pane);
		root.setCenter(hBox);
		hBox.setPadding(new Insets(40, 0, 0, 0));
		
		Scene scene = new Scene(root, 850 , 600);
		stage.setScene(scene);
		stage.show();
		
		createCurvesForComboBox();
		curveBox.setPromptText("Curves");
		
		decreaseScale.setOnAction(event -> {
			board.decreaseScale();
		});
		
		increaseScale.setOnAction(event -> {
			board.increaseScale();
		});
		
		curveBox.setOnAction(event -> {
			board.setCurveFromComboBox(curveBox.getValue());
			board.drawSpecifiedCurve(curveBox.getValue());
		});
	}
	
	private void createCurvesForComboBox() {
		ArrayList<Curve> curvesInBox = new ArrayList<Curve>();
		curvesInBox.add(new Hyperbola(3, 2));
		curvesInBox.add(new Ellipse(2, 4));
		curvesInBox.add(new Ellipse(3, 3));
		curvesInBox.add(new Ellipse(0.1, 2));
		curvesInBox.add(new Parabola(7.9));
		curvesInBox.add(new Parabola(-0.5));
		curvesInBox.add(new Hyperbola(0.3, 2.3));
		curvesInBox.add(new Hyperbola(-4.1, 4.9));
		curveBox.setItems(FXCollections.observableArrayList(curvesInBox));
	}
}
