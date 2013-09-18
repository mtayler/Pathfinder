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

import com.sun.org.apache.xpath.internal.functions.FuncFalse;
import processing.core.PApplet;

import java.awt.*;
import java.util.ArrayList;

public class Pathfinder {

	private PApplet parent;

	private Points points;
	private ArrayList<Point> path;

	private Point currentPoint;

	private double maxDistance = 40;
    private ArrayList<Point> invalidPoints;

    public Pathfinder(PApplet parent, Points points) {
		this.path = new ArrayList<Point>();
        this.invalidPoints = new ArrayList<Point>();

		this.parent = parent;
		this.points = points;

        this.currentPoint = points.getStart();
        this.path.add(this.currentPoint);
	}

	public boolean nextPoint() throws InvalidPointsException {
		ArrayList<Point> points = this.points.getPoints();
		if (points.size() < 2) {
			throw new InvalidPointsException("Requires 2 or more points added.");
		}

        for (int index=0; index < points.size()-1; index++) {
            Point possiblePoint = points.get(index);
            if (possiblePoint.equals(this.currentPoint)) { continue; }
            if (closer(possiblePoint)) {
                this.currentPoint = possiblePoint;
                this.path.add(this.currentPoint);
                return true;
            }
            this.invalidPoints.add(currentPoint);
        }

        for (int index=0; index < points.size()-1; index++) {
            Point possiblePoint = points.get(index);
            if (possiblePoint.equals(this.currentPoint)) { continue; }
            if (valid(possiblePoint)) {
                this.currentPoint = possiblePoint;
                this.path.add(this.currentPoint);
                return false;
            }
        }
        this.invalidPoints.clear();
        return false;
	}

    private boolean valid(Point point) {
        if (this.invalidPoints.contains(point)) {
            return false;
        }
        if (withinDistance(this.currentPoint, point)) {
//            if (!this.path.contains(point)) {
//                this.invalidPoints.clear();
//            }
            return true;
        }
        return false;
    }

	private boolean closer(Point point) {
        if ((getDistance(point, this.points.getEnd()) < (getDistance(this.currentPoint, this.points.getEnd())))) {
            if (!this.path.contains(point)) {
                if (valid(point)) {
                    return true;
                }
            }
        }
        return false;
	}

	public void drawPath() {
		Point pointA;
		Point pointB;
        parent.strokeWeight(5);
        parent.stroke(255,200);
		for (int point=0; point < path.size()-1; point++) {
			pointA = path.get(point);
			pointB = path.get(point+1);
			parent.line((float)pointA.getX(), (float)pointA.getY(), (float)pointB.getX(), (float)pointB.getY());
		}
	}

    public boolean atEnd() {
        if (this.currentPoint.equals(this.points.getEnd())) {
            return true;
        }
        else {
            return false;
        }
    }

	private boolean withinDistance(Point point1, Point point2) {
		return this.getDistance(point1, point2) <= this.getMaxDistance();
	}

	private double getDistance(Point point1, Point point2) {
		double x = point2.getX() - point1.getX();
		double y = point2.getY() - point1.getY();

		return Math.abs(Math.sqrt((x * x) + (y * y)));
	}


	public double getMaxDistance() {
		return this.maxDistance;
	}
    public void setMaxDistance(int distance) {
        this.maxDistance = distance;
	}
}