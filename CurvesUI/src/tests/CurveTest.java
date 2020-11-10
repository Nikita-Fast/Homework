package tests;



import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import javafx.geometry.Point2D;
import math.Curve;

public class CurveTest {
	
	public static final double DELTA = 0.001;
	
	@Test
	void quickSort() {
		ArrayList<Point2D> points = new ArrayList<Point2D>();
		points.add(new Point2D(2, 3));
		points.add(new Point2D(4, -1));
		points.add(new Point2D(3, 7));
		points.add(new Point2D(0.2, 11));
		points.add(new Point2D(7, -5));
		
		Curve.quickSort(points, 0, points.size() - 1);
		
		ArrayList<Point2D> expectedPoints = new ArrayList<Point2D>();
		expectedPoints.add(new Point2D(7, -5));
		expectedPoints.add(new Point2D(4, -1));
		expectedPoints.add(new Point2D(2, 3));
		expectedPoints.add(new Point2D(3, 7));
		expectedPoints.add(new Point2D(0.2, 11));
		
		Point2D[] pointsArray = new Point2D[points.size()];
		for (int i = 0; i < points.size(); i++) {
			assertEquals(expectedPoints.get(i).getY(), points.get(i).getY(), DELTA);
		}
	}
}
