package math;

import java.util.ArrayList;

import application.Board;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;

public abstract class Curve {
	
	private final static int STEPS_NUMBER = 10_000;
	
    ArrayList<Point2D> points;
    String equation; 
    double xMin, xMax;

    public Curve(double xMin, double xMax) { 
        this.xMin = xMin;
        this.xMax = xMax;
    }



    
    
    public abstract ArrayList<Double> getYValuesInSpecifiedXCoordinate(double x);
    
    
    public ArrayList<ArrayList<Point2D>> createPointsForCurve() {
    	double coefficient = Board.PIXEL_NUMBER_IN_UNIT_SEGMENT;
    	ArrayList<Point2D> pointsQuarter1 = new ArrayList<Point2D>();
    	ArrayList<Point2D> pointsQuarter2 = new ArrayList<Point2D>();
    	ArrayList<Point2D> pointsQuarter3 = new ArrayList<Point2D>();
    	ArrayList<Point2D> pointsQuarter4 = new ArrayList<Point2D>();
    	double distanceBetweenTwoXCoordinates = (xMax - xMin) / STEPS_NUMBER;
    	int helper = 100;
    	boolean firstHelp = true;
    	boolean closeToZero = true;
    	for (double x = xMax; x >= xMin; x -= (distanceBetweenTwoXCoordinates / helper) * (50.0 / Board.PIXEL_NUMBER_IN_UNIT_SEGMENT)) {
    		
    		if (firstHelp) {
    			if (x <= (1*xMin + 999*xMax) / 1000) {
	    			helper = 1;
	    			firstHelp = false;
	    		}
    		}
    		
    		if (x <= (999*xMin + 1*xMax) / 1000) {
    			helper = 100;
    		} 
    		
    		ArrayList<Double> yValues = getYValuesInSpecifiedXCoordinate(x);
    		if (yValues != null) {
    			double maxY = 0;
    			for (double y : yValues) {
    				
    				if ((x) >= 0 && (y) >= 0) {
    					pointsQuarter1.add(new Point2D(coefficient * x + 250, 250 - coefficient * y));
    				}
    				if ((x) < 0 && (y) >= 0) {
    					pointsQuarter2.add(new Point2D(coefficient * x + 250, 250 - coefficient * y));
    				}
    				if ((x) <= 0 && (y) < 0 && (y) > -(Board.HEIGHT / (Board.PIXEL_NUMBER_IN_UNIT_SEGMENT * 2))) {
    					pointsQuarter3.add(new Point2D(coefficient * x + 250, 250 - coefficient * y));
    				}
    				if ((x) > 0 && (y) < 0 && (y) > -(Board.HEIGHT / (Board.PIXEL_NUMBER_IN_UNIT_SEGMENT * 2))) {
    					pointsQuarter4.add(new Point2D(coefficient * x + 250, 250 - coefficient * y));
    				}
    				
        			 //250 is a half of pixels amount of board
        		}
    		}
    	}
    	ArrayList<ArrayList<Point2D>> allPoints = new ArrayList<ArrayList<Point2D>>();
    	allPoints.add(pointsQuarter1);
    	allPoints.add(pointsQuarter2);
    	allPoints.add(pointsQuarter3);
    	allPoints.add(pointsQuarter4);
    	return allPoints;
    }
}
