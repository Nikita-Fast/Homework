package math;

import java.util.ArrayList;
import java.util.Arrays;

import application.Board;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;

public abstract class Curve {
	
	private final static int STEPS_NUMBER = 20_000;
	
    ArrayList<Point2D> points;
    String equation; 
    double xMin, xMax;

    public Curve(double xMin, double xMax) { 
        this.xMin = xMin;
        this.xMax = xMax;
    }

    
    public static void quickSort(ArrayList<Point2D> points, int low, int high) {
        if (points.size() == 0) {
        	return;
        }
        if (low >= high) {
        	return;
        }

        int middle = low + (high - low) / 2;
        Point2D pivot = points.get(middle);
 
        int i = low, j = high;
        while (i <= j) {
            while (points.get(i).getY() < pivot.getY()) {
                i++;
            }
 
            while (points.get(j).getY() > pivot.getY()) {
                j--;
            }
 
            if (i <= j) {
            	Point2D temporary = points.get(i);
            	points.set(i, points.get(j));
            	points.set(j, temporary);
            	i++;
                j--;
            }
        }
 
        if (low < j) {
            quickSort(points, low, j);
        }
 
        if (high > i) {
            quickSort(points, i, high);
        }
    }
    
    public void printListWithPoints(ArrayList<Point2D> points) {
    	for (Point2D point : points) {
    		System.out.println("y = " + point.getY() + "  x = " + point.getX());
    	}
    }
    
    public abstract void setXMinMax(double xMin, double xMax);

    public abstract ArrayList<Double> getYValuesInSpecifiedXCoordinate(double x);
    
    
    public ArrayList<ArrayList<Point2D>> createPointsForCurve() {
    	double coefficient = Board.getPixelNumberInUnitSegment();
    	ArrayList<Point2D> pointsQuarter1 = new ArrayList<Point2D>();
    	ArrayList<Point2D> pointsQuarter2 = new ArrayList<Point2D>();
    	double distanceBetweenTwoXCoordinates = (xMax - xMin) / STEPS_NUMBER;
    	for (double x = xMax; x >= xMin; x -= (distanceBetweenTwoXCoordinates)) {
    		ArrayList<Double> yValues = getYValuesInSpecifiedXCoordinate(x);
    		if (yValues != null) {
    			for (double y : yValues) {
    				if (x >= 0 && y > -(Board.HEIGHT / (Board.getPixelNumberInUnitSegment() * 2))) {
    					pointsQuarter1.add(new Point2D(coefficient * x + Board.WIDTH / 2, Board.HEIGHT / 2 - coefficient * y));
    				}
    				if (x < 0 && y > -(Board.HEIGHT / (Board.getPixelNumberInUnitSegment() * 2))) {
    					pointsQuarter2.add(new Point2D(coefficient * x + Board.WIDTH / 2, Board.HEIGHT / 2 - coefficient * y));
    				}
        		}
    		}
    	}
    	quickSort(pointsQuarter1, 0, pointsQuarter1.size() - 1);
    	quickSort(pointsQuarter2, 0, pointsQuarter2.size() - 1);
    	
    	ArrayList<ArrayList<Point2D>> allPoints = new ArrayList<ArrayList<Point2D>>();
    	allPoints.add(pointsQuarter1);
    	allPoints.add(pointsQuarter2);
    	return allPoints;
    }
    
    public abstract void generateEquation();
    
    @Override
    public String toString() {
        return equation;
    }
}
