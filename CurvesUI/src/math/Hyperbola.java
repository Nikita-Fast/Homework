package math;

import java.util.ArrayList;

import application.Board;

public class Hyperbola extends Curve {
    private double a, b; //x^2 / a^2  -  y^2 / b^2 = 1

    public Hyperbola(double a, double b) {
        super(-Board.WIDTH / Board.PIXEL_NUMBER_IN_UNIT_SEGMENT, Board.WIDTH / Board.PIXEL_NUMBER_IN_UNIT_SEGMENT);
        this.a = a;
        this.b = b;
    }
    
    public ArrayList<Double> getYValuesInSpecifiedXCoordinate(double x) {
    	ArrayList<Double> yValues = new ArrayList<Double>();
    	double y = Math.sqrt((((x * x) / (a * a)) - 1) * (b* b));
    	yValues.add(y);
    	if (y == 0) {
    		return yValues;
    	}
    	yValues.add(-y);
    	return yValues;
    }

   public void setXMinMax(double xMin, double xMax) {
	   this.xMin = xMin;
	   this.xMax = xMax;
   }
}

