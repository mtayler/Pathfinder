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

import java.awt.*;

import controller.Points;

public abstract class Algorithm {

    protected Points points;

    public Algorithm(Points points) {
        this.points = points;
    }

    public abstract Point nextPoint(Point currentPoint);

    protected boolean withinDistance(Point point1, Point point2) {
        return this.getDistance(point1, point2) <= this.points.getMaxDistance();
    }

    protected double getDistance(Point point1, Point point2) {
        double x = point2.getX() - point1.getX();
        double y = point2.getY() - point1.getY();

        return Math.abs(Math.sqrt((x*x) + (y*y)));
    }
}
