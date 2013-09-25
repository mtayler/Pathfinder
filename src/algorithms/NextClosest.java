/*
 * Pathfinder
 * Copyright (C) 2013  Tayler Mulligan
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package algorithms;

import controller.Points;
import algorithms.FirstAvailable;

import java.awt.*;
import java.util.ArrayList;

public class NextClosest extends Algorithm {

    ArrayList<Point> invalidPoints;
    ArrayList<Point> returnedPoints;

    int fails;
    private boolean invalidPointsCleared;

    public NextClosest(Points points) {
        super(points);

        this.invalidPoints = new ArrayList<Point>();
        this.returnedPoints = new ArrayList<Point>();

        this.invalidPointsCleared = false;

        this.fails = 0;
    }

    public Point nextPoint(Point currentPoint) {

        Point closestPoint = currentPoint;

        for (int index=0; index < this.points.size(); index++) {
            Point possiblePoint = this.points.getPoint(index);
            if (withinDistance(possiblePoint, currentPoint)) {
                if (getDistance(possiblePoint, this.points.getEnd()) < getDistance(currentPoint, this.points.getEnd())) {
                    if (!this.invalidPoints.contains(possiblePoint)) {
                        closestPoint = possiblePoint;
                    }
                }
            }
        }
        if (closestPoint == currentPoint) {
            this.invalidPoints.add(currentPoint);
        }

        if (closestPoint == currentPoint) {
            for (int index=0; index < this.points.size(); index++) {
                Point possiblePoint = this.points.getPoint(index);
                if (withinDistance(possiblePoint, currentPoint)) {
                    if (!this.returnedPoints.contains(possiblePoint)) {
                        closestPoint = possiblePoint;
                    }
                }
            }
        }
        if (closestPoint == currentPoint) {
            for (int index=0; index < this.points.size(); index++) {
                Point possiblePoint = this.points.getPoint(index);
                if (withinDistance(possiblePoint, currentPoint) && possiblePoint != currentPoint) {
                    closestPoint = possiblePoint;
                }
            }
        }
        if (this.returnedPoints.contains(closestPoint)) {
            this.fails++;
        }
        else {
            this.fails = 0;
        }

        if (this.fails > 10 && this.getDistance(closestPoint, this.points.getEnd()) <= this.getDistance(currentPoint, this.points.getEnd())) {
            return null;
        }

        this.returnedPoints.add(closestPoint);
        return closestPoint;
    }
}
