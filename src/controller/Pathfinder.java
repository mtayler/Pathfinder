/*
 * Pathfinder
 * Copyright (C) 2014  Tayler Mulligan
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

import processing.core.*;

import java.awt.*;
import java.util.Random;

import static java.lang.Thread.sleep;

public class Pathfinder extends PApplet {

    private boolean restart;

    private Point currentPoint;

    private AlgorithmType selection = null;

    private Points points;

    private Random rand = new Random();

    private GUIManager guiManager;
    private algorithms.Algorithm pathfinder;


	static public void main(String args[]) {
        PApplet.main(new String[] { "controller.Pathfinder" });
    }

    public void setup() {
        System.out.println("Setting up...");
        size(600,600);
        background(0);

        this.guiManager = new GUIManager(this);

        reset();
    }

    public void reset() {
	    Points points;

	    Point start = new Point(5,5);
	    Point end = new Point(width-5, height-5);
	    int maxDistance = 30;
	    points = new Points(start, end, maxDistance);

	    for (int i=0; i < (height+width)/1.8; i++) {
		    int x = Math.round(rand.nextInt(width) / 12) * 12;
		    int y = Math.round(rand.nextInt(height) / 12) * 12;

		    if (!points.contains(x, y)) {
			    points.addPoint(x,y);
		    }
		    else {
			    i--;
		    }
	    }
	    this.points = points;

	    this.currentPoint = this.points.getStart();

	    this.selection = null;

        this.restart = false;
    }

	public void draw() {
        if (restart) {
            try {
                sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            reset();
        }

        /* Hack to draw GUI with Processing: Processing only draws to the screen after draw is finished
        * Coupled with return statement below */
        if (selection == null) {
            selection = guiManager.getSelection();

            if (selection != null) {
                switch (selection) {
                    case FIRSTCLOSER:       pathfinder = new algorithms.FirstCloser(points);
                        break;
                    case GREEDYNEXTCLOSEST: pathfinder = new algorithms.GreedyNextClosest(points);
                        break;
                    case LAZYNEXTCLOSEST:   pathfinder = new algorithms.LazyNextClosest(points);
                        break;
                    case RANDOMCHOICE:      pathfinder = new algorithms.RandomChoice(points);
                        break;
                }
                /* Again, hacky because of Processing
                 * Reset screen and draw points after selection is made
                 */
                background(0);
                drawPoints();
            }
            return;
        }

        if (keyPressed) {
            if (key == 'r' | key == 'R') {
	            restart=true;
            }
            if (key == 's' | key == 'S') {
                this.guiManager.repeat.set(false);
                this.guiManager.clearSelection();
                restart=true;
            }
        }

        Point nextPoint = this.pathfinder.nextPoint(this.currentPoint);

        if (nextPoint == null) {
            System.err.println("Stuck.");
            this.restart = true;
            return;
        }

        // Needs to check for null value before drawing
        drawPath(nextPoint);

        if (!this.points.contains(nextPoint)) {
            System.err.println("false");
        }
        this.currentPoint = nextPoint;

        if (nextPoint.equals(this.points.getEnd())) {
            System.out.println("Found end.");
            this.restart = true;
        }
    }

    private void drawPoints() {
        stroke(210);
        strokeWeight(2);
        fill(0);

        ellipseMode(CENTER);
        for (int index=0; index < this.points.size(); index++) {
            Point point = this.points.getPoint(index);
            ellipse((float)point.getX(), (float)point.getY(), 10, 10);
        }
        fill(0, 255, 0);

        Point end = this.points.getEnd();
        Point start = this.points.getStart();
        ellipse((float)end.getX(), (float)end.getY(), 10, 10);

        fill(0, 0, 255);
        ellipse((float)start.getX(), (float)start.getY(), 10, 10);
    }

    private void drawPath(Point nextPoint) {
        stroke(235, 150);
        strokeWeight(4);
            line((float)this.currentPoint.getX(), (float)this.currentPoint.getY(),
                    (float)nextPoint.getX(), (float)nextPoint.getY());
    }
}