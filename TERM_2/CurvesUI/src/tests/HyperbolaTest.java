package tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import org.junit.Test;
//import org.junit.jupiter.api.Test;

import math.Hyperbola;

public class HyperbolaTest {

	@Test
	public void getYvaluesInSpecifiedXCoordinate() {
		Hyperbola hyperbola = new Hyperbola(3, 2);
		
		ArrayList<Double> expectedList = new ArrayList<Double>();
		expectedList.add(2.6667);
		expectedList.add(-2.6667);
		ArrayList<Double> realList = hyperbola.getYValuesInSpecifiedXCoordinate(5);
		for (int i = 0; i < expectedList.size(); i++) {
			assertEquals(expectedList.get(i), realList.get(i), CurveTest.DELTA);
		}
		
		expectedList.clear();
		expectedList.add(0d);
		realList = hyperbola.getYValuesInSpecifiedXCoordinate(3);
		assertEquals(1, realList.size());
		
		double yValue = realList.get(0);
		
		assertEquals(0d, yValue, CurveTest.DELTA);
	}
}
