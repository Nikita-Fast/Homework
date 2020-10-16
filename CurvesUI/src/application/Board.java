package application;



import java.util.ArrayList;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import math.Ellipse;
import math.Hyperbola;
import math.Parabola;
import javafx.scene.shape.Polyline;
import math.Curve;

public class Board extends Canvas {
	
	public static final int WIDTH = 500;
	public static final int HEIGHT = WIDTH;
	public static double PIXEL_NUMBER_IN_UNIT_SEGMENT = WIDTH / 10;
	private double scale = 1;
	private Pane paneForCurve;
	private ArrayList<Polyline> polylines = new ArrayList<Polyline>();
	private double step = PIXEL_NUMBER_IN_UNIT_SEGMENT;
	public static final int NUMBER_OF_CURVE = 2;
	
	public Board(Pane paneForCurve) {
		super(WIDTH, HEIGHT);
		init(paneForCurve);
	}
	
	public void decreaseScale() {
		getGraphicsContext2D().clearRect(0, 0, WIDTH, HEIGHT);
		if (PIXEL_NUMBER_IN_UNIT_SEGMENT > 1 && PIXEL_NUMBER_IN_UNIT_SEGMENT <= 10) {
			PIXEL_NUMBER_IN_UNIT_SEGMENT -= 1;
		}
		if (PIXEL_NUMBER_IN_UNIT_SEGMENT > 10 && PIXEL_NUMBER_IN_UNIT_SEGMENT <= 20) {
			PIXEL_NUMBER_IN_UNIT_SEGMENT -= 2;
		}
		if (PIXEL_NUMBER_IN_UNIT_SEGMENT > 20 && PIXEL_NUMBER_IN_UNIT_SEGMENT <= 30) {
			PIXEL_NUMBER_IN_UNIT_SEGMENT -= 3;
		}
		if (PIXEL_NUMBER_IN_UNIT_SEGMENT > 30 && PIXEL_NUMBER_IN_UNIT_SEGMENT <= 60) {
			PIXEL_NUMBER_IN_UNIT_SEGMENT -= 5;
		}
		if (PIXEL_NUMBER_IN_UNIT_SEGMENT > 60 && PIXEL_NUMBER_IN_UNIT_SEGMENT <= 100) {
			PIXEL_NUMBER_IN_UNIT_SEGMENT -= 8;
		}
		if (PIXEL_NUMBER_IN_UNIT_SEGMENT > 100) {
			PIXEL_NUMBER_IN_UNIT_SEGMENT -= 10;
		}
		
		reloadBoard();
		clearBoard();
		drawUserCurve(NUMBER_OF_CURVE);
		//drawEllipse();
	}
	
	public void increaseScale() {
		getGraphicsContext2D().clearRect(0, 0, WIDTH, HEIGHT);
		if (PIXEL_NUMBER_IN_UNIT_SEGMENT > 0 && PIXEL_NUMBER_IN_UNIT_SEGMENT <= 10) {
			PIXEL_NUMBER_IN_UNIT_SEGMENT += 1;
		}
		if (PIXEL_NUMBER_IN_UNIT_SEGMENT > 10 && PIXEL_NUMBER_IN_UNIT_SEGMENT <= 20) {
			PIXEL_NUMBER_IN_UNIT_SEGMENT += 2;
		}
		if (PIXEL_NUMBER_IN_UNIT_SEGMENT > 20 && PIXEL_NUMBER_IN_UNIT_SEGMENT <= 30) {
			PIXEL_NUMBER_IN_UNIT_SEGMENT += 3;
		}
		if (PIXEL_NUMBER_IN_UNIT_SEGMENT > 30 && PIXEL_NUMBER_IN_UNIT_SEGMENT <= 60) {
			PIXEL_NUMBER_IN_UNIT_SEGMENT += 5;
		}
		if (PIXEL_NUMBER_IN_UNIT_SEGMENT > 60 && PIXEL_NUMBER_IN_UNIT_SEGMENT <= 100) {
			PIXEL_NUMBER_IN_UNIT_SEGMENT += 8;
		}
		if (PIXEL_NUMBER_IN_UNIT_SEGMENT > 100 && PIXEL_NUMBER_IN_UNIT_SEGMENT <= 200) {
			PIXEL_NUMBER_IN_UNIT_SEGMENT += 10;
		}
		if (PIXEL_NUMBER_IN_UNIT_SEGMENT > 200 && PIXEL_NUMBER_IN_UNIT_SEGMENT <= 500) {
			PIXEL_NUMBER_IN_UNIT_SEGMENT += 25;
		}
		reloadBoard();
		clearBoard();
		drawUserCurve(NUMBER_OF_CURVE);
		//drawEllipse();
	}
	
	public void setPaneForCurve(Pane paneForCurve) {
        this.paneForCurve = paneForCurve;
    }
	
	public void clearBoard() {
		for (Polyline polyline : polylines) {
			paneForCurve.getChildren().remove(polyline);
		}
	}
	
	public void drawUserCurve(int number) {
		if (number == 0) {
			drawEllipse();
		}
		if (number == 1) {
			drawParabola();
		}
		if (number == 2) {
			drawHyperbola();
		}
	}
	
	public void drawHyperbola() {
		Hyperbola hyperbola = new Hyperbola(1.7, -3.1);
		
		hyperbola.setXMinMax(-Board.WIDTH / Board.PIXEL_NUMBER_IN_UNIT_SEGMENT, Board.WIDTH / Board.PIXEL_NUMBER_IN_UNIT_SEGMENT);
		
		for (Polyline polyline : createPolylines(hyperbola.createPointsForCurve())) {
			drawSpecifiedPolyline(polyline);
		}
		
		System.out.println("successfull drawing of hyperbola!");
		
	}
	
	public void drawParabola() {
		Parabola parabola = new Parabola(0.07);
		
		
		for (Polyline polyline : createPolylines(parabola.createPointsForCurve())) {
			drawSpecifiedPolyline(polyline);
		}
		
		System.out.println("successfull drawing of parabola!");
	}
	
	public void drawEllipse() {
		Ellipse ellipse = new Ellipse(345, 1);
		
		for (Polyline polyline : createPolylines(ellipse.createPointsForCurve())) {
			drawSpecifiedPolyline(polyline);
		}
		
		System.out.println("successfull drawing of ellipse!");
		//drawSpecifiedPolyline(createPolyline(ellipse.createPointsForCurve().get(3)));
	}
	
	public Polyline createPolyline(ArrayList<Point2D> points) {
		Polyline polyline = new Polyline();
		for (Point2D point : points) {
			polyline.getPoints().add(point.getX());
			polyline.getPoints().add(point.getY());
		}
		return polyline;
	}
	
	public ArrayList<Polyline> createPolylines(ArrayList<ArrayList<Point2D>> allPoints) {
		ArrayList<Polyline> polylines = new ArrayList<Polyline>(); 
		
		for (ArrayList<Point2D> pointsInQuarter : allPoints) {
			Polyline polyline = new Polyline();
			for (Point2D point : pointsInQuarter) {
				if (point.getX() >= 2 && point.getX() <= getWidth() - 2 && point.getY() <= getHeight() - 2 && point.getY() >= 2) { //2 is the size of edging
					polyline.getPoints().add(point.getX());
					polyline.getPoints().add(point.getY());
				}
			}
			polylines.add(polyline);
		} 
		
		return polylines;
	}
	
	public void drawSpecifiedPolyline(Polyline polyline) {
		polyline.setStroke(Color.PURPLE);
	    polyline.setStrokeWidth(3);
	    paneForCurve.getChildren().add(polyline);
	    polylines.add(polyline);
	}
	/*
	public void drawCurve() {
		
		Polyline polyline = new Polyline();  
	    polyline.getPoints().addAll(new Double[]{        
	         200.0, 50.0, 
	         400.0, 50.0, 
	         450.0, 150.0,          
	         400.0, 250.0, 
	         200.0, 250.0,                   
	         150.0, 150.0, 
	    }); 
	    polyline.setStroke(Color.PURPLE);
	    polyline.setStrokeWidth(3);
	    paneForCurve.getChildren().add(polyline);
	    polylines.add(polyline);
		
		
	}*/
	
	private void reloadBoard() {
		GraphicsContext gc = this.getGraphicsContext2D();
		gc.setFill(Color.WHITE);
		gc.fillRect(0, 0, getWidth(), getHeight());
		
		createEdging();
		
		drawCartesianÑoordinatSystem();
	}
	
	private void init(Pane paneForCurve) {
		setPaneForCurve(paneForCurve);
		fillBoardWithWhite();
		
		createEdging();
		
		drawCartesianÑoordinatSystem();	
	}
	
	private void fillBoardWithWhite() {
		GraphicsContext gc = this.getGraphicsContext2D();
		gc.setFill(Color.WHITE);
		gc.fillRect(0, 0, getWidth(), getHeight());
	}
	
	private void createEdging() {
		GraphicsContext gc = getGraphicsContext2D();
		gc.setStroke(Color.BLACK);
		gc.setLineWidth(3);
		gc.strokeRect(0, 0, getWidth(), getHeight());
	}
	
	private void drawXYAxises() {
		GraphicsContext gc = this.getGraphicsContext2D();
		gc.setStroke(Color.BLACK);
		gc.setLineWidth(2);
		gc.strokeLine(getWidth() / 2, 0, getWidth() / 2, getHeight());
		gc.strokeLine(0, getHeight() / 2, getWidth(), getHeight() / 2);
	}
	
	private void drawCenterPoint() {
		GraphicsContext gc = this.getGraphicsContext2D();
		gc.setFill(Color.BLACK);
		gc.fillOval(getWidth() / 2 - 5, getHeight() / 2 - 5, 10, 10);
	}
	
	private void drawCartesianÑoordinatSystem() {
		drawCenterPoint();
		drawXYAxises();
		drawStrokesScalable();
	}
	
	private boolean isMultipleOf(int num1, int num2) {
		int q = num1 / num2;
		if (num2 * q == num1) {
			return true;
		}
		return false;
	}
	
	private String createString(int number, double coefficient) {
		String str;
		if (coefficient >= 1) {
			if (isMultipleOf(number, (int)Math.round(coefficient))) {
				str = new String(Integer.toString(number / (int)Math.round(coefficient)));
			}
			else {
				str = new String(number + "/" + (int)Math.round(coefficient));
			}
		}
		else {
			int res = number * ((int)Math.round(1 / coefficient));
			str = new String(Integer.toString(res));
		}
		return str;
	}
	
	private void drawNumberAssociatedWithStroke(double x, double y, boolean strokeIsVertical, int number, double coefficient) {  //x, y point to center of stroke
		GraphicsContext gc = this.getGraphicsContext2D();
		gc.setLineWidth(1);
		
		String str = createString(number, coefficient);
		
		if (!strokeIsVertical) {
			int offset = 0;
			if (number > 0) {
				offset = 3;
			}
			else {
				offset = 9;
			}
			gc.strokeText(str, x - offset, y + 25);  
		}
		else {
			int offset = 0;
			if (number > 0) {
				offset = 15;
			}
			else {
				offset = 10;
			}
			gc.strokeText(str, x + offset, y + 5);
		}
	}
	
	private void drawStrokesScalable() {
		GraphicsContext gc = this.getGraphicsContext2D();
		
		step = PIXEL_NUMBER_IN_UNIT_SEGMENT / scale;
		
		if (step >= 80) {
			scale *= 2;
			step = PIXEL_NUMBER_IN_UNIT_SEGMENT / scale;
			
		}
		
		if (step < 40) {
			scale /= 2;
			step = PIXEL_NUMBER_IN_UNIT_SEGMENT / scale;
		}
		
		double coefficient = scale;
		
		
		System.out.println("pixel number = " + PIXEL_NUMBER_IN_UNIT_SEGMENT);
		System.out.println("scale = " + scale);
		System.out.println("step = " + step);
		
		
		int number = 1;
		for (double x = (getWidth() / 2) + step ; x < getWidth(); x += step) {
			gc.setLineWidth(2);
			double y = getHeight() / 2;
			gc.strokeLine(x, y - 5, x, y + 5);
			
			drawNumberAssociatedWithStroke(x, y, false, number, coefficient);
			number++;
		}
		number = -1;
		for (double x = (getWidth() / 2) - step ; x > 0; x -= step) {
			gc.setLineWidth(2);
			double y = getHeight() / 2;
			gc.strokeLine(x, y - 5, x, y + 5);
			
			drawNumberAssociatedWithStroke(x, y, false, number, coefficient);
			number--;
		}
		number = 1;
		for (double y = (getHeight() / 2) - step ; y > 0; y -= step) {
			gc.setLineWidth(2);
			double x = getWidth() / 2;
			gc.strokeLine(x - 5, y, x + 5, y);
			
			drawNumberAssociatedWithStroke(x, y, true, number, coefficient);
			number++;
		}
		number = -1;
		for (double y = (getHeight() / 2) + step ; y < getHeight(); y += step) {
			gc.setLineWidth(2);
			double x = getWidth() / 2;
			gc.strokeLine(x - 5, y, x + 5, y);
			
			drawNumberAssociatedWithStroke(x, y, true, number, coefficient);
			number--;
		}
	}
	/*
	private void drawStrokes(double offset) {
		GraphicsContext gc = this.getGraphicsContext2D();
		double x = getWidth() / 2;
		double y = getHeight() / 2;
		int h = 0;
		int w = 0;
		
		while (x - offset > 5) { //5 ÷òîáû íå ðèñîâàòü øòðèõè áëèçêî ê ãðàíèöå äîñêè
			x -= offset;
			w++;
		}
		while (y - offset > 5) {
			y -= offset;
			h++;
		}
		
		gc.setStroke(Color.BLACK);
		for (int xCoord = w; xCoord >= -w; xCoord--) {
			gc.setLineWidth(2);
			gc.strokeLine(getWidth() / 2 + xCoord * offset, getHeight() / 2 + 5, 
					getWidth() / 2 + xCoord * offset, getHeight() / 2 - 5);
			if (xCoord != 0) {
				String str = Integer.toString(xCoord);
				if (xCoord > 0) {
					gc.setLineWidth(1);
					gc.strokeText(str, getWidth() / 2 + xCoord * offset - 3, getHeight() / 2 + 25);
				}
				else {
					gc.setLineWidth(1);
					gc.strokeText(str, getWidth() / 2 + xCoord * offset - 10, getHeight() / 2 + 25);
				}
			}
		}
		for (int yCoord = h; yCoord >= -h; yCoord--) {
			gc.setLineWidth(2);
			gc.strokeLine(getWidth() / 2 - 5, getHeight() / 2 + yCoord * offset, 
					getWidth() / 2 + 5, getHeight() / 2 + yCoord * offset);
			if (yCoord != 0) {
				String str = Integer.toString(-yCoord);
				if (yCoord > 0) {
					gc.setLineWidth(1);
					gc.strokeText(str, getWidth() / 2 + 10, getHeight() / 2 + yCoord * offset + 5);
				}
				else {
					gc.setLineWidth(1);
					gc.strokeText(str, getWidth() / 2 + 15, getHeight() / 2 + yCoord * offset + 5);
				}
			}
		}
	} */
}
