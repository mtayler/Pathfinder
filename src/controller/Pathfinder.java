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

import algorithms.*;
import processing.core.PApplet;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import static java.lang.Thread.sleep;

public class Pathfinder extends PApplet {

    private int waitTime;
    private boolean restart;

    private Point start;
    private Point end;
    private int maxDistance;
    private Point currentPoint;

    private Points points;

    private ArrayList<Point> path;
    private Random rand = new Random();
    private Algorithm pathfinder;


    public void setup() {
        size(400,400);
        background(0);

        this.waitTime = 100;
        this.restart = false;

        this.start = new Point(5,5);
        this.end = new Point(width-5, height-5);
        this.maxDistance = 30;
        this.points = new Points(this.start, this.end, this.maxDistance);

        this.path = new ArrayList<Point>();

        this.currentPoint = this.points.getStart();
        this.path.add(this.currentPoint);

        for (int i=0; i < (height+width)/2; i++) {
            int x = Math.round(rand.nextInt(width) / 12) * 12;
            int y = Math.round(rand.nextInt(height) / 12) * 12;

            if (!this.points.hasPoint(x,y)) {
                this.points.addPoint(x,y);
            }
            else {
                i--;
            }
        }

        this.pathfinder = new NextClosest(this.points);
    }

	public void draw() {
        if (keyPressed) {
            if (key == 'r' | key == 'R') {
                reset();
            }
        }
        if (restart) {
            try {
                sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            reset();
        }

        background(0);

        int startTime = millis();

        Point nextPoint = this.pathfinder.nextPoint(this.currentPoint);
        if (nextPoint == null) {
            System.err.println("Stuck.");
            this.restart = true;
            return;
        }
        this.currentPoint = nextPoint;
        this.path.add(this.currentPoint);

        drawPoints();
        drawPath();

        if (nextPoint == this.points.getEnd()) {    // down here becuase it allows line to end to be drawn
            System.out.println("Found end.");
            this.restart = true;
            return;
        }

        while (millis() - startTime < this.waitTime) {
            try {
                sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    private void reset() {
        background(0);
        setup();
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
        ellipse((float)this.end.getX(), (float)this.end.getY(), 10, 10);

        fill(0, 0, 255);
        ellipse((float)this.start.getX(), (float)this.start.getY(), 10, 10);
    }

    private void drawPath() {
        stroke(235, 180);
        strokeWeight(4);
        for (int index=1; index < this.path.size(); index++) {
            Point point1 = this.path.get(index-1);
            Point point2 = this.path.get(index);

            line((float)point1.getX(), (float)point1.getY(), (float)point2.getX(), (float)point2.getY());
        }
    }
}
