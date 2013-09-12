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
import processing.core.PApplet.*;

import java.util.Random;

public class Pathfinding extends PApplet{

	Points points;
	Pathfinder pathfinder;

    public void setup() {
        size(600,600);
        background(0);
	    this.points = new Points(this);

	    Random rand = new Random();

	    for (int i=0; i < ((height+width)/4); i++) {
		    points.addPoint(rand.nextInt(width), rand.nextInt(height));
		    points.addPoint(rand.nextInt(width), rand.nextInt(height));
	    }
    }

	public void draw() {
		this.drawPoints();


	}

	private void drawPoints() {
		stroke(200);
		strokeWeight(2);
		fill(0);
		this.points.drawPoints(5);
	}
}
