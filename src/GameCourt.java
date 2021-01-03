/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.1, Apr 2017
 */

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.*;

/**
 * GameCourt
 * 
 * This class holds the primary game logic for how different objects interact
 * with one another. Take time to understand how the timer interacts with the
 * different methods and how it repaints the GUI on every tick().
 */
@SuppressWarnings("serial")
public class GameCourt extends JPanel {

    // the state of the game logic

    private ArrayList<Snake> snake;
    private Food food;

    private boolean playing = false; // whether the game is running
    private JLabel currentScore; // current score label
    private int points = 0; // initial score
    private int numFoods; // amount of food to display based on user
    private String username; // user's username

    // Game constants
    public static final int COURT_WIDTH = 300;
    public static final int COURT_HEIGHT = 300;

    private int[][] foodArr = new int[300][300];

    // Update interval for timer, in milliseconds
    public static final int INTERVAL = 100;

    public GameCourt(JLabel currentScore) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // instructions
        JOptionPane.showMessageDialog(null,
                "Welcome to Snake!" + " Eat the food to make the snake grow. You get a point for "
                        + "each food the snake eats. Here are the instructions: "
                        + "\n\n1. Use the arrow keys to control the snake."
                        + "\n2. Don't run into yourself or the boundaries of the game or else "
                        + "you'll die."
                        + "\n3. Choose how many food pieces you want displayed at a"
                        + " time (max 4) and enter your username by following the prompts on the "
                        + "next window."
                        + "\n\nGood luck!",
                "SNAKE", JOptionPane.PLAIN_MESSAGE);

        // gets num of food that user desires at a time
        numFoods = checkFoodInput();
        // gets the username of the user
        username = checkUserInput();
        
     // The timer is an object which triggers an action periodically with the given
        // INTERVAL. We
        // register an ActionListener with this timer, whose actionPerformed() method is
        // called each
        // time the timer triggers. We define a helper method called tick() that
        // actually does
        // everything that should be done in a single timestep.

        Timer timer = new Timer(INTERVAL, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    tick();
                } catch (IOException e1) {

                }
            }
        });
        timer.start(); // MAKE SURE TO START THE TIMER!

        // Enable keyboard focus on the court area.
        // When this component has the keyboard focus, key events are handled by its key
        // listener.
        setFocusable(true);

        // This key listener allows the square to move as long as an arrow key is
        // pressed, by
        // changing the square's velocity accordingly. (The tick method below actually
        // moves the
        // square.)

        // initializing variables and resetting the board
        snake = new ArrayList<Snake>();
        this.currentScore = currentScore;
        reset();

        // key presses that move the direction of the snake
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                Snake head = snake.get(0);
                if (e.getKeyCode() == KeyEvent.VK_LEFT && head.getDir() != 2) {
                    for (Snake body : snake) {
                        body.setDir(1);
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && head.getDir() != 1) {
                    for (Snake body : snake) {
                        body.setDir(2);
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN && head.getDir() != 3) {
                    for (Snake body : snake) {
                        body.setDir(4);
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_UP && head.getDir() != 4) {
                    for (Snake body : snake) {
                        body.setDir(3);
                    }
                }
            }
        });
    }
    
    /**
     * Gets the number of food items to display at a time based on user input
     * 
     * @return The num of food to display at a time
     */
    
    private int checkFoodInput() {
        int num = 0;

        String amount = JOptionPane.showInputDialog(
                "Enter the amount of food you would like " + 
                        "displayed at a time. " + "\nMax value 4.");

        try {
            num = Integer.parseInt(amount);
        } catch (NumberFormatException e) {

        }

        while (num > 4 || num < 1) {
            amount = JOptionPane.showInputDialog("You did not enter a valid number. "
                    + "Enter the amount of food you would like displayed at a time. " + 
                    "Max value 4.");

            try {
                num = Integer.parseInt(amount);
            } catch (NumberFormatException e) {
                num = -1;
            }
        }

        return num;
    }
    
    /**
     * Adds the appropiate amount of food to the GUI and updates foodArr
     * Checks if the randomly generated food intersects the snake's body and regenerates
     * if it does
     * 
     */

    private void addFirstFood(int num) {
        for (int i = 0; i < num; i++) {

            food = new Food(COURT_WIDTH, COURT_HEIGHT, Color.RED);

            int newX = (int) ((COURT_WIDTH) * Math.random());
            int newY = (int) ((COURT_HEIGHT) * Math.random());

            Food trial = new Food(COURT_WIDTH, COURT_HEIGHT, Color.RED);

            trial.setPx(newX);
            trial.setPy(newY);

            for (Snake body : snake) {
                if (body.intersects(trial)) {
                    addFirstFood(0);
                } else {
                    food.setPx(newX);
                    food.setPy(newY);
                }

            }
            foodArr[food.getPx()][food.getPy()] = 1;
        }
    }
    
    
    //Adds a piece of food to the board each time one is eaten
    private void addFood() {
        int num = -1;

        while (num == -1) {
            food = new Food(COURT_WIDTH, COURT_HEIGHT, Color.RED);
            int newX = (int) ((COURT_WIDTH) * Math.random());
            int newY = (int) ((COURT_HEIGHT) * Math.random());

            Food trial = new Food(COURT_WIDTH, COURT_HEIGHT, Color.RED);

            trial.setPx(newX);
            trial.setPy(newY);

            for (Snake body : snake) {
                if (body.intersects(trial)) {
                    num = -1;
                    break;
                } else {
                    food.setPx(newX);
                    food.setPy(newY);
                    num = 0;
                }
            }
        }

        foodArr[food.getPx()][food.getPy()] = 1;
    }
    
    private String checkUserInput() {
        String userInput = JOptionPane.showInputDialog("Enter your username. No whitespaces.");

        while (userInput.contains(" ") || userInput.isEmpty()) {
            userInput = JOptionPane
                    .showInputDialog("You did not enter a valid username. " + 
            "\nEnter your username. No whitespaces.");
        }

        return userInput;
    }

    /**
     * Finds out whether or not the user (or another user) wants to play
     * 
     * @return 0 if the user selects no and 1 if the user selects yes
     */
    private int checkPlayAgain() {
        int check;

        check = JOptionPane.showConfirmDialog(null,
                "Would you like to play again? "
                        + "\nNote that if you would like to play again, you "
                        + "will be asked to enter the "
                        + "\namount of food and your username again.",
                "Play Again?", JOptionPane.YES_NO_OPTION);

        return check;
    }

    /**
     * Adds on to the snake and calls addFood() to add another food item to the board
     * Called on whenever the snake eats a food item
     * 
     */
    
    private void addToTail(Snake tail) {
        snake.add(new Snake(tail.getPx(), tail.getPy(), 
                COURT_WIDTH, COURT_HEIGHT, Color.PINK, tail.getDir()));

        addFood();
    }
    
    /**
     * Resets the state of certain variables whenever the snake eats food
     * 
     * @param tail The tail of the snake
     * @param i The x coord of the previous food eaten
     * @param j The y coord of the previous food eaten
     */

    public void ateFood(Snake tail, int i, int j) {
        points += 1;
        currentScore.setText("Current Score: " + points);
        addToTail(tail);
        foodArr[i][j] = 0;
    }
    
    /**
     * Displays the score when the game is over and resets variables based on whether or not the
     * user wants to play again
     * 
     */

    private void gameOver() throws IOException {
        playing = false;
        JOptionPane.showMessageDialog(null, "Your score was " + points + "!", "You Lose", 
                JOptionPane.PLAIN_MESSAGE);
        printScores(points, username);
        if (checkPlayAgain() == 0) {
            numFoods = checkFoodInput();
            username = checkUserInput();
            reset();
        } else {
            System.exit(1);
        }
    }

    /**
     * (Re-)set the game to its initial state.
     */
    public void reset() {
        foodArr = new int[COURT_WIDTH][COURT_HEIGHT];
        food = new Food(COURT_WIDTH, COURT_HEIGHT, Color.RED);

        playing = true;
        currentScore.setText("Current Score: " + points);
        points = 0;

        snake.clear();
        Snake newSnake = new Snake(20, 20, COURT_WIDTH, COURT_HEIGHT, Color.ORANGE, 2);
        snake.add(newSnake);
        Snake tail = new Snake(0, 20, COURT_WIDTH, COURT_HEIGHT, Color.ORANGE, 2);
        snake.add(tail);

        addFirstFood(numFoods);

        // Make sure that this component has the keyboard focus
        requestFocusInWindow();
    }

    // for testing purposes
    public boolean getPlaying() {
        return this.playing;
    }
    
    public void setPlaying(Boolean newVal) {
        this.playing = newVal;
    }
    
    public void clear() {
        playing = false;
        System.exit(1);
    }

    /**
     * This method is called every time the timer defined in the constructor
     * triggers.
     * 
     * @throws IOException
     */

    void tick() throws IOException {
        if (playing) {
            // advance the square and snitch in their current direction.
            Snake tail = snake.get(snake.size() - 1);
            Snake head = snake.get(0);
            head.move(snake);

            // check for the game end conditions

            for (int i = 0; i < COURT_HEIGHT; i++) {
                for (int j = 0; j < COURT_WIDTH; j++) {
                    if (foodArr[i][j] == 1) {
                        food.setPx(i);
                        food.setPy(j);
                        if (snake.get(0).intersects(food)) {
                            ateFood(tail, i, j);
                        }
                    }
                }
            }

            if (head.hitItself(snake) || snake.get(0).hitWall() == Direction.LEFT || 
                    snake.get(0).hitWall() == Direction.RIGHT
                    || snake.get(0).hitWall() == Direction.UP || 
                    snake.get(0).hitWall() == Direction.DOWN) {
                gameOver();
            }
        }

        // update the display

        repaint();
    }

    /**
     *  Writes the new score/username to the file, reads the scores, and calculates 
     *  the three highest scores and displays them
     *  
     *  Users can be on the leaderboard more than once
     *  
     *  If there is an error with the file, that is it is buggy, then this method will 
     *  wipe the file clean back to it's default state (with 3 users who have scores 0 and
     *  usernames like "no_name") and add the new user and their score to the file
     * 
     * @throws IOException
     */
    public void printScores(int newScore, String username) throws IOException {
        String current = "";
        String total = "";
        String[] lineArr = new String[2];
        List<String[]> scores = new ArrayList<String[]>();
        
        try {
            BufferedWriter writer = new BufferedWriter(
                    new FileWriter("./files/high_scores.txt", true));
            writer.write("\n" + newScore + " " + username);
            writer.close();

            BufferedReader reader = new BufferedReader(new FileReader("./files/high_scores.txt"));

            while ((current = reader.readLine()) != null) {
                lineArr = current.split(" ");
                scores.add(lineArr);
            }

            Comparator<String[]> comp = new Comparator<String[]>() {
                @Override
                public int compare(String[] o1, String[] o2) {
                    int num1 = Integer.parseInt(o1[0]);
                    int num2 = Integer.parseInt(o2[0]);

                    if (num1 > num2) {
                        return 1;
                    } else if (num1 < num2) {
                        return -1;
                    } else {
                        return 0;
                    }
                }
            };

            Collections.sort(scores, comp);

            String[] highScore1 = scores.get(scores.size() - 1);
            String[] highScore2 = scores.get(scores.size() - 2);
            String[] highScore3 = scores.get(scores.size() - 3);

            String first = highScore1[0] + " " + highScore1[1];
            String second = highScore2[0] + " " + highScore2[1];
            String third = highScore3[0] + " " + highScore3[1];

            total = "1. " + first + "\n" + "2. " + second + "\n" + "3. " + third;

            JOptionPane.showMessageDialog(null,
                    "Here are the three highest scores and the respective usernames so far: \n\n" + 
                            total, "Highest Scores",
                            JOptionPane.PLAIN_MESSAGE);

            reader.close();  
        } catch (FileNotFoundException e1) {
            JOptionPane.showMessageDialog(null,
                    "The high_scores file has not been found.", "File not Found",
                            JOptionPane.PLAIN_MESSAGE);
        } catch (NumberFormatException e2) {
            BufferedWriter writer;
            try {
                writer = new BufferedWriter(new FileWriter("./files/high_scores.txt", false));
                writer.write("0 no_name1\n0 no_name2\n0 no_name3");
                writer.close();
                printScores(newScore, username);
            } catch (IOException e1) {
                
            }
        } catch (IOException e) {
            BufferedWriter writer;
            try {
                writer = new BufferedWriter(new FileWriter("./files/high_scores.txt", false));
                writer.write("0 no_name1 \n 0 no_name2 \n 0 no_name3");
                writer.close();
                printScores(newScore, username);
            } catch (IOException e1) {
                
            }     
        } 
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 0; i < 300; i++) {
            for (int j = 0; j < 300; j++) {
                if (foodArr[i][j] == 1) {
                    g.setColor(Color.RED);
                    g.fillOval(i, j, 20, 20);
                }
            }
        }

        for (Snake body : snake) {
            body.draw(g);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(COURT_WIDTH, COURT_HEIGHT);
    }
}