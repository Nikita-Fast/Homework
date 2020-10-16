package math;

import java.util.ArrayList;

import application.Board;
import javafx.scene.canvas.Canvas;

public class Parabola extends Curve {
    private double p;
    // y^2 = 2*p*x
    public Parabola(double p) {
    	
        super(p == 0 ? -Board.WIDTH / Board.PIXEL_NUMBER_IN_UNIT_SEGMENT : p > 0 ? 0 : -Board.WIDTH / Board.PIXEL_NUMBER_IN_UNIT_SEGMENT,
        		p == 0 ? Board.WIDTH / Board.PIXEL_NUMBER_IN_UNIT_SEGMENT : p > 0 ? Board.WIDTH / Board.PIXEL_NUMBER_IN_UNIT_SEGMENT : 0); 
        this.p = p;
    }

    public ArrayList<Double> getYValuesInSpecifiedXCoordinate(double x) {
    	ArrayList<Double> yValues = new ArrayList<Double>();
    	double y = Math.sqrt(2 * p * x);
    	yValues.add(y);
    	if (y == 0) {
    		return yValues;
    	}
    	yValues.add(-y);
    	return yValues;
    }
}
