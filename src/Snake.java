import java.awt.Color;

/**
 * Snake
 * 
 * This class holds information needed to create/get/manipulate instances of a Snake object.
 */

import java.awt.Graphics;

public class Snake extends GameObj {

    public static final int SIZE = 20;
    // LEFT = 1;
    // RIGHT = 2;
    // UP = 3;
    // DOWN = 4;
    private int direction;

    private Color color;

    public Snake(int px, int py, int courtWidth, int courtHeight, Color color, int direction) {
        super(px, py, SIZE, SIZE, courtWidth, courtHeight);

        this.color = color;
        this.direction = direction;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(this.color);
        g.fillRect(this.getPx(), this.getPy(), this.getWidth(), this.getHeight());
    }

    public int getDir() {
        return this.direction;
    }

    public void setDir(int direction) {
        this.direction = direction;
    }
}
