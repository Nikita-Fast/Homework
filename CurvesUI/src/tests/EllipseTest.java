package tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import math.Ellipse;

public class EllipseTest {
	
	
	
	@Test
	public void getYValuesInSpecifiedXCoordinate() {
		Ellipse ellipse = new Ellipse(2, 4);
		ArrayList<Double> expectedList = new ArrayList<Double>();
		expectedList.add(4d);
		expectedList.add(-4d);
		ArrayList<Double> realList = ellipse.getYValuesInSpecifiedXCoordinate(0);
		for (int i = 0; i < expectedList.size(); i++) {
			assertEquals(expectedList.get(i), realList.get(i), CurveTest.DELTA);
		}
		
		expectedList.clear();
		expectedList.add(0d);
		realList = ellipse.getYValuesInSpecifiedXCoordinate(-2);
		assertEquals(1, realList.size());
		
		double yValue = realList.get(0);
		
		assertEquals(0d, yValue, CurveTest.DELTA);
	}
}
