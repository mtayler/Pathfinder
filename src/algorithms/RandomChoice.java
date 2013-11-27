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

import controller.Points;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: tayler
 * Date: 26/11/13
 * Time: 8:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class RandomChoice extends Algorithm {

    private int counter;
    private Random rand;
    private ArrayList<Point> attempts;
    private final int limit = 70;

    public RandomChoice(Points points) {
        super(points);
        this.rand = new Random();

        this.counter = 0;
        this.attempts = new ArrayList();
    }

    public Point nextPoint(Point currentPoint) {
        if (this.counter > 100)
            return null;

        int index;

        do {
            index = rand.nextInt(this.points.size());
        } while (! withinDistance(currentPoint, this.points.getPoint(index)));

        Point possiblePoint = this.points.getPoint(index);

        if (! this.attempts.contains(possiblePoint)) {
            this.attempts.add(possiblePoint);
            counter = 0;
        }
        else
            counter++;


        if (this.counter > this.limit)
            return null;

        return this.points.getPoint(index);
    }
}