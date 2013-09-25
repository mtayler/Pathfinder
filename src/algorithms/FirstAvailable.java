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

import java.awt.*;
import java.util.ArrayList;

public class FirstAvailable extends Algorithm {

    int counter;

    private ArrayList<Point> invalidPoints;
    private ArrayList<Point> returnedPoints;
    private int fails = 0;

    public FirstAvailable(Points points) {
        super(points);

        this.invalidPoints = new ArrayList<Point>();
        this.returnedPoints = new ArrayList<Point>();
    }

    /**
     * Returns the next available point based on given parameters.
     * <p>
     * Returns null if there are no valid points available
     *
     * @param currentPoint  the point the path finder is currently at
     * @return              the next available point
     */
    public Point nextPoint(Point currentPoint) {

        for (int index=0; index < this.points.size(); index++) {
            Point possiblePoint = this.points.getPoint(index);
            if (possiblePoint.equals(currentPoint)) { continue; }

            if (closer(currentPoint, possiblePoint)) {
                fails = 0;
                this.returnedPoints.add(possiblePoint);
                return possiblePoint;
            }
        }
        this.invalidPoints.add(currentPoint);

        for (int index=0; index < this.points.size(); index++) {
            Point possiblePoint = this.points.getPoint(index);
            if (possiblePoint.equals(currentPoint)) { continue; }

            if (valid(currentPoint, possiblePoint)) {
                fails++;
                this.returnedPoints.add(possiblePoint);
                return possiblePoint;
            }
        }
        if (fails > 10 && this.getDistance(this.returnedPoints.get(this.returnedPoints.size()-1), this.points.getEnd()) <= this.getDistance(currentPoint, this.points.getEnd())) {
            return null;
        }
        this.invalidPoints.clear();
        return currentPoint;
    }

    private boolean valid(Point currentPoint, Point point) {
        if (this.invalidPoints.contains(point)) {
            return false;
        }
        if (withinDistance(currentPoint, point)) {
            return true;
        }
        return false;
    }

    private boolean closer(Point currentPoint, Point point) {
        if (valid(currentPoint, point)) {
            if ((getDistance(point, this.points.getEnd()) < (getDistance(currentPoint, this.points.getEnd())))) {
                if (!returnedPoints.contains(point)) {
                    return true;
                }
            }
        }
        return false;
    }
}
