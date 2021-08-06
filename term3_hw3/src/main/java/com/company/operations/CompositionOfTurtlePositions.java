package com.company.operations;

import com.company.auxiliaries.PositionOfTurtle;

public class CompositionOfTurtlePositions extends AssociativeOperation<PositionOfTurtle> {

    @Override
    public PositionOfTurtle apply(PositionOfTurtle p1, PositionOfTurtle p2) {
        PositionOfTurtle p2Rotated = p2.rotate(p1.getAngle());
        double x = p1.getX() + p2Rotated.getX();
        double y = p1.getY() + p2Rotated.getY();
        return new PositionOfTurtle(x, y, p2Rotated.getAngle());
    }
}
