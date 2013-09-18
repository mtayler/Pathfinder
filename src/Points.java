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

import processing.core.PApplet;

import java.awt.*;
import java.util.ArrayList;



public class Points {

	private PApplet parent;
	private ArrayList<Point> points;

    private Point end;
    private Point start;

	public Points(PApplet parent, Point start, Point end) {
		this.points = new ArrayList<Point>();
		this.parent = parent;

        this.start = start;
        this.end = end;

        this.addPoint(start);
        this.addPoint(end);
	}

	public void drawPoints(int size) {
        parent.stroke(210);
        parent.strokeWeight(2);
        parent.fill(0);

		parent.ellipseMode(parent.CENTER);
		for (int currentPoint=0; currentPoint < points.size(); currentPoint++) {
			Point point = points.get(currentPoint);
			parent.ellipse((float)point.getX(), (float)point.getY(), size, size);
		}
        parent.fill(0, 255, 0);
        parent.ellipse((float)this.end.getX(), (float)this.end.getY(), size, size);

        parent.fill(0,0,255);
        parent.ellipse((float)this.start.getX(), (float)this.start.getY(), size, size);
	}


	public void addPoint(int x, int y) {
		Point point = new Point(x,y);
		this.points.add(point);
	}
	public void addPoint(Point point) {
		this.points.add(point);
	}

    public boolean hasPoint(int x, int y) {
        for (int i=0; i < this.points.size(); i++) {
            if (new Point(x,y).equals(this.points.get(i))) {
                return true;
            }
        }
        return false;
    }

	public ArrayList<Point> getPoints() {
		return this.points;
	}

    public Point getEnd() {
        return this.end;
    }

    public Point getStart() {
        return this.start;
    }
}
