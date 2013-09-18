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

package controller;

import processing.core.PApplet;

import java.awt.Point;
import java.util.ArrayList;



public class Points {

	private ArrayList<Point> points;

    private Point end;
    private Point start;
    private int maxDistance;

	public Points(Point start, Point end, int maxDistance) {
		this.points = new ArrayList<Point>();

        this.start = start;
        this.end = end;

        this.addPoint(start);
        this.addPoint(end);

        this.maxDistance = maxDistance;
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

	public ArrayList<Point> getAll() {
		return this.points;
	}

    public Point getPoint(int index) {
        return this.points.get(index);
    }

    public int size() {
        return this.points.size();
    }

    public Point getEnd() {
        return this.end;
    }

    public Point getStart() {
        return this.start;
    }

    public int getMaxDistance() {
        return this.maxDistance;
    }
}
