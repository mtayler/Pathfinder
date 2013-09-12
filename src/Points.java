/**
 * <one line to give the program's name and a brief idea of what it does.>
 * Copyright (C) 2013  Tayler Mulligan
 * <p/>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p/>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

import processing.core.PApplet;

import java.awt.*;
import java.util.ArrayList;



public class Points {

	private PApplet parent;
	private ArrayList<Point> points;

	public Points(PApplet parent) {
		this.points = new ArrayList<Point>();
		this.parent = parent;
	}

	public void drawPoints() {
		parent.ellipseMode(parent.CENTER);
		for (int currentPoint=0; currentPoint < points.size(); currentPoint++) {
			Point point = points.get(currentPoint);
			parent.ellipse((float)point.getX(), (float)point.getY(), 1, 1);
		}
	}

	public void drawPoints(int size) {
		parent.ellipseMode(parent.CENTER);
		for (int currentPoint=0; currentPoint < points.size(); currentPoint++) {
			Point point = points.get(currentPoint);
			parent.ellipse((float)point.getX(), (float)point.getY(), size, size);
		}
	}

	public void addPoint(int x, int y) {
		Point point = new Point(x,y);
		this.points.add(point);
	}

	public Point getPoint(int index) {
		return points.get(index);
	}

	public int getLength() {
		return this.points.size();
	}
}
