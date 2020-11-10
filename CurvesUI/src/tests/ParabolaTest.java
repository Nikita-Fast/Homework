package tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import org.junit.Test;
//import org.junit.jupiter.api.Test;

import math.Parabola;

public class ParabolaTest {
	@Test
	public void getYvaluesInSpecifiedXCoordinate() {
		Parabola parabola = new Parabola(2.7);
		
		ArrayList<Double> expectedList = new ArrayList<Double>();
		expectedList.add(4.347);
		expectedList.add(-4.347);
		ArrayList<Double> realList = parabola.getYValuesInSpecifiedXCoordinate(3.5);
		for (int i = 0; i < expectedList.size(); i++) {
			assertEquals(expectedList.get(i), realList.get(i), CurveTest.DELTA);
		}
		
		expectedList.clear();
		expectedList.add(0d);
		realList = parabola.getYValuesInSpecifiedXCoordinate(0);
		assertEquals(1, realList.size());
		
		double yValue = realList.get(0);
		
		assertEquals(0d, yValue, CurveTest.DELTA);
	}
}
