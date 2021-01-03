import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.util.ArrayList;

/** 
 *  You can use this file (and others) to test your
 *  implementation.
 */

public class GameTest {

    Snake snake1 = new Snake(100, 100, 300, 300, Color.PINK, 2);
    Snake snake2 = new Snake(80, 100, 300, 300, Color.PINK, 2);

    ArrayList<Snake> snake = new ArrayList<>();

    @Test 
    public void changeDir() {
        snake.add(snake1);
        snake.add(snake2);
        
        snake1.setDir(3);

        assertEquals(3, snake1.getDir());
    }

    @Test
    public void moveSnakeCorrectly() {
        Snake snakeTest = new Snake(120, 100, 300, 300, Color.PINK, 2);

        snake.add(snake1);
        snake.add(snake2);

        snake1.move(snake);

        assertEquals(120, snakeTest.getPx());
        assertEquals(100, snakeTest.getPy());

        assertEquals(120, snake.get(0).getPx());
        assertEquals(100, snake.get(0).getPy());
    }

    @Test
    public void moveSnakeAfterChangingRightDir() {
        snake.add(snake1);
        snake.add(snake2);

        snake1.setDir(4);
        snake1.move(snake);
        
        assertEquals(100, snake.get(0).getPx());
        assertEquals(120, snake.get(0).getPy());

        assertEquals(100, snake1.getPx());
        assertEquals(100, snake1.getPy());
    }
    
    @Test
    public void moveSnakeAfterChangingWrongDir() {
        snake.add(snake1);
        snake.add(snake2);

        snake1.setDir(1);
        snake1.move(snake);

        assertEquals(80, snake.get(0).getPx());
        assertEquals(100, snake.get(0).getPy());

        assertEquals(100, snake1.getPx());
        assertEquals(100, snake1.getPy());
    }

    @Test
    public void runIntoTopWall() {
        snake.add(snake1);
        snake.add(snake2);

        snake1.setDir(3);

        for (int i = 0; i < 10; i++) {
            snake1.move(snake);
        }

        Snake head = snake.get(0);

        assertEquals(100, head.getPx());
        assertEquals(-100, head.getPy());
        
        assertEquals(head.hitWall(), Direction.UP);
    }

    @Test
    public void runIntoBottomWall() {
        snake.add(snake1);
        snake.add(snake2);
        
        snake1.setDir(4);

        for (int i = 0; i < 10; i++) {
            snake1.move(snake);
        }

        Snake head = snake.get(0);

        assertEquals(100, head.getPx());
        assertEquals(300, head.getPy());
        
        assertEquals(head.hitWall(), Direction.DOWN);
    }

    @Test
    public void runIntoLeftWall() {
        snake.add(snake1);
        snake.add(snake2);

        snake1.setDir(1);

        for (int i = 0; i < 10; i++) {
            snake1.move(snake);
        }

        Snake head = snake.get(0);

        assertEquals(-100, head.getPx());
        assertEquals(100, head.getPy());
        
        assertEquals(head.hitWall(), Direction.LEFT);
    }

    @Test
    public void runIntoRightWall() {
        snake.add(snake1);
        snake.add(snake2);

        snake1.setDir(2);

        for (int i = 0; i < 10; i++) {
            snake1.move(snake);
        }

        Snake head = snake.get(0);

        assertEquals(300, head.getPx());
        assertEquals(100, head.getPy());
        
        assertEquals(head.hitWall(), Direction.RIGHT);
    }

    @Test
    public void runIntoItself() {
        snake.add(snake1);
        snake.add(snake2);
        snake.add(new Snake(80, 120, 300, 300, Color.PINK, 2));
        snake.add(new Snake(80, 140, 300, 300, Color.PINK, 2));
        snake.add(new Snake(100, 140, 300, 300, Color.PINK, 2));
        snake.add(new Snake(120, 140, 300, 300, Color.PINK, 2));
        snake.add(new Snake(140, 140, 300, 300, Color.PINK, 2));
        snake.add(new Snake(160, 140, 300, 300, Color.PINK, 2));
        snake.add(new Snake(180, 140, 300, 300, Color.PINK, 2));
        snake.add(new Snake(200, 140, 300, 300, Color.PINK, 2));
        snake.add(new Snake(220, 140, 300, 300, Color.PINK, 2));

        Snake head = snake.get(0);
        snake.get(0).setDir(4);
        snake.get(0).move(snake);
        snake.get(0).move(snake);

        assertTrue(head.hitItself(snake));
    }

}
