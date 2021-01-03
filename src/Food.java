import java.awt.Color;
import java.awt.Graphics;

/**
 * Food
 * 
 * This class holds information needed to create an instance of a Food object.
 */

public class Food extends GameObj {
    public static final int SIZE = 20;
    public static final int INIT_POS_X = 170;
    public static final int INIT_POS_Y = 170;

    private Color color;

    public Food(int courtWidth, int courtHeight, Color color) {
        super(INIT_POS_X, INIT_POS_Y, SIZE, SIZE, courtWidth, courtHeight);
        this.color = color;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(this.color);
        g.fillOval(this.getPx(), this.getPy(), this.getWidth(), this.getHeight());
    }
}
