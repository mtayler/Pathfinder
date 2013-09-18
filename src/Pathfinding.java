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
import processing.core.PFont;

import java.awt.*;
import java.util.Random;

import static java.lang.Thread.*;

public class Pathfinding extends PApplet{

	Points points;
	Pathfinder pathfinder;

	private boolean foundEnd;

    private int tryCounter;
    private int tries;

    private boolean stuck;
    private int autoResetDelay = 500;

    public void setup() {
        int sparsity = 3;
        int maxDistance = 50;

        this.foundEnd = false;
        this.stuck = false;
        this.tryCounter = 0;

        size(400,400);
        background(0);
	    this.points = new Points(this,  new Point(5,5), new Point(width-5,height-5));
	    this.pathfinder = new Pathfinder(this, this.points);
        if (maxDistance > 0) {
            pathfinder.setMaxDistance(maxDistance);
        }

	    Random rand = new Random();

	    for (int i=0; i < ((height+width)/sparsity); i++) {
            int x = round(rand.nextInt(width)/10) * 10;
            int y = round(rand.nextInt(height)/10) * 10;
//            int x = rand.nextInt(width);
//            int y = rand.nextInt(height);
            while (points.hasPoint(x, y)) {
		        x = round(rand.nextInt(width)/10) * 10;
                y = round(rand.nextInt(height)/10) * 10;
            }
            points.addPoint(x, y);
	    }
        PFont f = createFont("Arial", 30, true);
        textFont(f);

        this.tries = (int)(Math.sqrt((height*height) + (width * width))/(pathfinder.getMaxDistance()/2));
    }

	public void draw() {
        if (keyPressed) {
            if (key == 'r' | key == 'R') {
                reset();
            }
        }

        if (this.foundEnd) {
            try {
                sleep(this.autoResetDelay);
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

            reset();
        }

        if (this.stuck) {
            try {
                sleep(this.autoResetDelay);
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

            reset();
        }

        int startTime = millis();
        background(0);

        this.points.drawPoints(10);

		try {
            if (pathfinder.nextPoint()) {
                tryCounter = 0;
            }
            else {
                tryCounter++;
                if (tryCounter > tries) {
                    this.stuck = true;
                }
            }
		}
		catch(InvalidPointsException e) {
			System.err.print("Adding point at (0,0)");
			points.addPoint(0,0);
		}
        if (pathfinder.atEnd()) {
            this.foundEnd = true;
        }

		pathfinder.drawPath();

        while (millis() - startTime < 100) {
            try {
                sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
	}

    private void reset() {
        background(0);
        setup();
    }
}
