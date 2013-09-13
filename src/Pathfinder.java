/**
 * <one line to give the program's name and a brief idea of what it does.>
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

import processing.core.PApplet;

import java.awt.*;
import java.util.ArrayList;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class Pathfinder {

	private PApplet parent;

	private Points points;
	private ArrayList<Point> path;

	private Point currentPoint;
	private Point end;

	private double maxDistance = 50;
	private boolean found = true;

	public Pathfinder(PApplet parent, Points points, Point start, Point end) {
		this.path = new ArrayList<Point>();

		this.parent = parent;
		this.points = points;

		this.currentPoint = start;
		this.points.addPoint(start);
		this.path.add(start);

		this.end = end;
		this.points.addPoint(end);
	}

	public boolean nextPoint() throws InvalidPointsException {
		ArrayList<Point> points = this.points.getPoints();
		if (points.size() < 2) {
			throw new InvalidPointsException("Requires 2 or more points added.");
		}

		Point startPoint = this.currentPoint;
		for (int index=0; index < points.size()-1; index++) {
			this.checkPoint(points.get(index));
		}

		if (this.currentPoint == startPoint) {
			this.found = false;
		}
		else {
			this.found = true;
		}

		if (this.end.equals(this.currentPoint)) {
			return true;
		}
		else {
			return false;
		}
	}

	private void checkPoint(Point possiblePoint) {
		if (withinDistance(this.currentPoint, possiblePoint)) {
			if (this.found) {
				if (getDistance(possiblePoint, this.end) < getDistance(this.currentPoint, this.end)) {
					this.path.add(possiblePoint);
					this.currentPoint = possiblePoint;
				}
			}
			else {
				this.path.add(possiblePoint);
				this.currentPoint = possiblePoint;
			}
		}
	}

	public void drawPath() {
		Point pointA;
		Point pointB;
		for (int point=0; point < path.size()-1; point++) {
			parent.stroke(255, 100);
			pointA = path.get(point);
			pointB = path.get(point+1);
			parent.line((float)pointA.getX(), (float)pointA.getY(), (float)pointB.getX(), (float)pointB.getY());
		}
	}

	private boolean withinDistance(Point point1, Point point2) {
		return this.getDistance(point1, point2) < this.getMaxDistance();
	}

	private double getDistance(Point point1, Point point2) {
		double x = point2.getX() - point1.getX();
		double y = point2.getY() - point1.getY();

		return sqrt( (pow(x,2) + pow(y,2)) );
	}

	public void setMaxDistance(double maxDistance) {
		this.maxDistance = maxDistance;
	}

	private double getMaxDistance() {
		return this.maxDistance;
	}

	public void drawEnd() {
		parent.point((float)this.end.getX(), (float)this.end.getY());
	}
}