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
import processing.core.PFont;

public class GUIManager {

    private PApplet parent;
    private Algorithm[] algorithms;

    private AlgorithmType selection;

    private int rectWidth;
    private int rectHeight;

    private PFont font;

    public TickBox repeat;

    private boolean unPressed;

    private float fontSize;

    public GUIManager(PApplet parent) {

        this.parent = parent;

        this.rectWidth = (int)((this.parent.width) * (9.0/10.0));
        this.rectHeight = (int)(this.parent.height/10.0);

        double fontY = this.rectHeight*(2.0/10.0);
        double fontX = this.rectWidth*(2.0/10.0);
        this.fontSize = (float)Math.sqrt((fontY*fontY) + (fontX*fontX))/4;

        this.font = this.parent.loadFont("Ubuntu-48.vlw");
        this.parent.textFont(this.font, this.fontSize);

        AlgorithmType[] algorithmTypes = AlgorithmType.values();
        this.algorithms = new Algorithm[algorithmTypes.length];

        this.selection = null;

        for (int index=0; index < algorithmTypes.length; index++) {
            this.algorithms[index] = new Algorithm(algorithmTypes[index]);
            this.algorithms[index].topLeft[0] = ((this.parent.width/2) - (rectWidth/2));
            this.algorithms[index].topLeft[1] = (index*rectHeight)+10+(int)(10*(index/1.0));
            this.algorithms[index].bottomRight[0] = ((this.parent.width/2) + (rectWidth/2));
            this.algorithms[index].bottomRight[1] = this.algorithms[index].topLeft[1] + rectHeight;
        }
        this.repeat = new TickBox("Repeat",
                (this.parent.width/2)-(this.parent.width/4), // Offsets slightly from the center (x axis)
                this.algorithms[this.algorithms.length-1].bottomRight[1] +      // Sets position on (y axis)
                (this.algorithms[this.algorithms.length-1].bottomRight[1] - this.algorithms[this.algorithms.length-1].topLeft[1]));

        this.unPressed = true;
    }

    public AlgorithmType getSelection() {
        if (this.repeat.isTrue() && this.selection != null) {
            return this.selection;
        }

        drawGUI();

        if (this.parent.mousePressed) {
            int mouseX = this.parent.mouseX;
            int mouseY = this.parent.mouseY;

            if (this.repeat.within(this.parent.mouseX, this.parent.mouseY)) {
                if (this.unPressed) {
                    this.selection = null;
                    this.repeat.toggle();
                }
            }

            for (Algorithm algorithm : algorithms) {
                if (algorithm.within(mouseX, mouseY)) {
                    this.selection = algorithm.type;
                    return this.selection;
                }
            }
            this.unPressed = false;
        }
        else {
            this.unPressed = true;
        }
        return null;
    }

    public void clearSelection() {
        this.selection = null;
    }


    /* Horrible, complex drawing stuff */
    private void drawGUI() {
        this.parent.background(0);

        /* Settings for drawing tickbox */
        this.parent.stroke(140);
        this.parent.fill(30);
        this.parent.strokeWeight(this.repeat.strokeWeight);
        this.parent.rectMode(this.parent.CENTER);

        /* Draws repeat tickbox text */
        this.parent.rect(this.repeat.position[0], this.repeat.position[1], this.repeat.size, this.repeat.size);
        this.parent.textAlign(this.parent.LEFT);
        this.parent.fill(200);
        this.parent.textSize(this.fontSize/(float)1.5);
        this.parent.text(this.repeat.text, this.repeat.position[0]+this.repeat.size+2, this.repeat.position[1]+this.repeat.size/2);

        /* Draws tickbox repeat checked/unchecked symbol */
        if (this.repeat.isTrue()) {
            this.parent.line(this.repeat.position[0]-this.repeat.size/2, this.repeat.position[1]-this.repeat.size/2,
                        this.repeat.position[0]+this.repeat.size/2, this.repeat.position[1]+this.repeat.size/2);

            this.parent.line(this.repeat.position[0]+this.repeat.size/2, this.repeat.position[1]+this.repeat.size/2,
                        this.repeat.position[0]-this.repeat.size/2, this.repeat.position[1]-this.repeat.size/2);
        }

        /* Draws each algorithm selection */
        this.parent.textFont(this.font,this.fontSize);
        this.parent.rectMode(this.parent.CORNERS);
        for (Algorithm algorithm : algorithms) {
            if (algorithm.within(this.parent.mouseX, this.parent.mouseY)) {
                this.parent.fill(100);
            }
            else {
                this.parent.fill(30);
            }

            this.parent.rect(algorithm.topLeft[0], algorithm.topLeft[1], algorithm.bottomRight[0], algorithm.bottomRight[1]);
            this.parent.textAlign(this.parent.CENTER);
            this.parent.fill(230);
            this.parent.text(algorithm.title, (algorithm.topLeft[0]+algorithm.bottomRight[0])/2,
                       (algorithm.topLeft[1]+algorithm.bottomRight[1])/2);

        }
    }
}

class Algorithm {
    AlgorithmType type;

    int[] topLeft;
    int[] bottomRight;

    String title;

    Algorithm(AlgorithmType type) {
        this.type = type;
        this.topLeft = new int[2];
        this.bottomRight = new int[2];
        this.title = type.toString();
    }

    boolean within(int mouseX, int mouseY) {
        if (mouseX >= topLeft[0] && mouseX <= bottomRight[0]) {
            if (mouseY >= topLeft[1] && mouseY <= bottomRight[1]) {
                return true;
            }
        }
        return false;
    }
}

class TickBox {
    String text;
    int[] position;
    final int size = 10;

    boolean state;
    public float strokeWeight = 2;

    TickBox(String text, int x, int y) {
        this.text = text;

        this.position = new int[] {x, y};
        this.state = false;
    }

    boolean isTrue() {
        return this.state;
    }

    boolean within(int mouseX, int mouseY) {
        if (mouseX >= position[0]-this.size/2 && mouseX <= position[0]+this.size/2) {
            if (mouseY >= position[1]-this.size/2 && mouseY <= position[1]+this.size/2) {
                return true;
            }
        }
        return false;
    }

    public void toggle() {
        this.state = !this.state;
    }

    public void set(boolean state) {
        this.state = state;
    }
}