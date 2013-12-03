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

public class GreedyNextClosest extends Algorithm {

    private ArrayList<Point> path;
    private ArrayList<Point> invalidPoints;

    public GreedyNextClosest(Points points) {
        super(points);

        this.path = new ArrayList<Point>();
        this.invalidPoints = new ArrayList<Point>();
    }

    public Point nextPoint(Point currentPoint) {

        Point closestPoint = currentPoint;

        for (int index=0; index < this.points.size(); index++) {
            Point possiblePoint = this.points.getPoint(index);
            if (withinDistance(possiblePoint, currentPoint)) {
                if (getDistance(possiblePoint, this.points.getEnd()) < getDistance(currentPoint, this.points.getEnd())) {
                    if (!this.invalidPoints.contains(possiblePoint)) {
                        closestPoint = possiblePoint;
                        this.path.add(closestPoint);
                    }
                }
            }
        }

        if (closestPoint == currentPoint) {
            this.invalidPoints.add(closestPoint);

            try {
                closestPoint = this.path.get(this.path.size() - 2);
            }
            catch (ArrayIndexOutOfBoundsException backtrack) {
                return null;
            }

                this.path.remove(this.path.size()-1);
        }

        return closestPoint;
    }
}
