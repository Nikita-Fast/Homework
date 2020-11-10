package math;

import java.util.ArrayList;

public class Ellipse extends Curve {
    private double a, b;

    public Ellipse(double a, double b) { //у эллипса |x| <= a
        super(-Math.abs(a), Math.abs(a));
        this.a = a;
        this.b = b;
        //x^2 / a^2  +  y^2 / b^2 == 1
       generateEquation();
    }

    public ArrayList<Double> getYValuesInSpecifiedXCoordinate(double x) {
    	ArrayList<Double> yValues = new ArrayList<Double>();
    	double y = Math.sqrt((1 - ((x * x) / (a * a))) * (b* b));
    	yValues.add(y);
    	if (y == 0) {
    		return yValues;
    	}
    	yValues.add(-y);
    	return yValues;
    }
    
    
   public void generateEquation() {
	   equation = new String("x^2 / " + a + "^2 + y^2 / " + b + "^2 = 1");
   }

   public void setXMinMax(double xMin, double xMax) {
	   
   }

}

