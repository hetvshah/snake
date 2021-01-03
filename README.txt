=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 120 Game Project README
PennKey: hetvis
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

This is the basic implementation of Snake where the Snake moves around the board based on the 
arrow keys and the goal is for the user is eat the food and get their score as high as possible.


===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. 2D Array:
  
  My 2D array stores the location of the food since there can be multiple instances of food 
  depending on the user. In addition of storing the location of all the instances, I loop through 
  this array to check if the randomly generated food intersects with any parts of the snake or 
  another piece of food. I also use this array to repaint the board, where there is food drawn
  at every spot there is a "1." This is appropiate since the board is essentially a 300x300 grid
  that can be modeled using an array. It makes it simple to loop through the array to figure out
  where to place the specified amount of food and how to repaint it. 

  2. Collections:
  
  I used a ArrayList to hold the body parts of the snake. In the game, it is necessary to keep 
  track of the order of the snake's body parts - that is which side is the head and which side is
  the tail. Since the ArrayList is an ordered collection, it keep's track of the order of the 
  body parts that are put in it. This let's me order the snake body parts, while a 2D array does 
  not. In this way, the first element is always the head and adding to the ArrayList adds to the 
  tail. Thus, when repainting at each tick, the ArrayList makes sure that making the snake longer 
  after eating food adds to the tail. Keeping the head a set element, such as the first one, is 
  also helpful in figuring out whether or not the snake intersects with itself, the walls, or the 
  food since this intersection is relative to the head. I loop through this collection to both 
  repaint the snake at each tick and check for intersections.

  3. File I/O
  
  At the beginning of the game, I ask the user to input the amount of food and their username. 
  After the game is over, I write their score and username (separated by a space) to a .txt file. I
  then read all of the scores/usernames in the .txt file and put them in a list. I sorted this list
  to output the highest three scores and their respective usernames (in order) at the end of the 
  game. It is appropiate to use File I/O because the amount of users that will play the game is
  unknown and we can keep track of this using a file. If there is a tie for the highest score, 
  it will just display 3 of those players. The same person can be on the leaderboard if their score
  is the highest. The program throws an exception and displays a popup if the file does not exist. 
  If the file is buggy or empty on some lines, then I catch the exception. For each, set the file 
  to its default and call the function again.

  4. Testable
  
  I designed my code so that I could test it with JUnit. The snake's body parts are stored in an 
  ArrayList and the food is stored in a 2D array. I test whether or not the appropiate result 
  happens for different scenarios. I mainly test the state of the game, that is whether moving the 
  snake changes the positions of the snake in the Array list or whether or not the food is being 
  drawn and the array is being updated. I also create instances of the snake and test whether or 
  not colliding into the wall or itself changes the variables appropiately (like playing or 
  intersecting). Lastly, I test whether eating the food updates the food array, the snake 
  ArrayList, and the points. A lot of the larger methods that contain a lot of objects and other 
  methods were not directly tested since doing so would start the game and create an infinite 
  loop in my tests. However, I tested all the methods used within these larger functions and the
  general state of the main game relative to the snake, food, and points.
  

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.
  
  1) Game.java
  
  This class just has the frame and the GUI. I didn't change much except the name and the status
  panel, which now displays and updates the score.
  
  2) GameCourt.java
  
  This is the main class that stores the primary logic and updating needed for the game. It is the
  place for interacting with the user and all of the different objects. This class is really 
  important in checking the state of the game at each tick and updating appropiately. It contains 
  methods that ask for input and check whether what has been inputted is valid. These methods 
  include checkFoodInput(), checkUserInput, and checkPlayAgain(). Each of these methods return the 
  final user input that is valid. Other methods call on these and use their return values to update
  the game or change certain states depending on what is necessary. 
  
  addFirstFood() and addFood() are both used to draw the food and update the state of the array. 
  addFirstFood uses the users clean input to start the game by drawing that many pieces of food 
  randomly. addFood adds a piece of food to the game board and updates the array as well. Both of 
  these methods ensure that the random piece of food generated is not touching or intersecting with
  any part of the snake's body (in which if it is, it will re-generate the x and y coordinates). 
  
  When the game begins, the constructor calls the methods that ask for input and it also calls on 
  reset(). reset() will add the first few pieces of food to the game and update the array as well as
  make the snake of length 2 to begin with and update the ArrayList. As the game continues, tick()
  will keep track of what is happening. That is, tick() will check if the snake has every collided
  with the wall or itself or if the snake has eaten food. If the former happens, then gameOver() 
  will be called which essentially displays the user's score and the highest scores as well as ask
  the user if they (or another user) desire to continue playing. If they do, then the game will
  reset. If they don't then the game will close. If the latter happens (the snake eats food), then
  ateFood() will be called, which increases the points by 1 and updates the current score label and
  the food array. It also calls on addToTail, which will add a snake body part to the tail end and
  update the ArrayList correctly. 
  
  Overall, this class is very useful in checking for conditions and updating the game at each tick.
  
  3) GameObj.java
  
  This class creates game objects (food and snake body parts) and provides getters/setters for the
  x and y positions as well as the width and the height (which cannot be changed). The getters and
  setters for the x and y positions are useful for updating the positon of the snake and the food 
  based on what has happened during the game. These getters and setters keep the private state
  encapsulated. 
  
  This class also has a move method which is called on at every tick. It moves the snake in the
  indicated direction by creating and adding a "new" head and removing the tail. This creates an
  effect of moving the snake. The other 3 methods, hitItself(), intersects(), and hitWall() are 
  used to check the whether or not the user as lost, that is the snake has collided with itself or
  the wall. I use and call on these methods in my GameCourt class. 
  
  4) Snake.java
  
  This class holds all the information to create an instance of the Snake object. Whenever a new
  snake object needs to be drawn to the board, a new object is created using this class, which 
  extends GameObj and declares information about the position, size, color, court size, and 
  direction. This class also has getDir and serDir functions. getDir is a getter that returns the 
  direction of the object specified and setDir is a setter that sets the direction of the snake 
  object that was passed as a parameter
  
  5) Food.java  
  
  This class holds all the information to create an instance of the Food object. Whenever a food
  object needs to be drawn to the board, a new object is created using this class, which extends
  GameObj and declares information about the position, size, color, and court size.


- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?
  
  There were some stumbling blocks I had while implementing my game, but nothing too big. My first
  design held the snake's body in the array and the collection. I thought this would make it easier
  to check whether or not the snake collided with the wall or itself, but in retrospect, it made
  things harder. I had to keep updating both the collection and the array, which got very difficult
  at times. I ended up not storing the snake in the array at all and just using a for each every 
  time I needed a specific part of the body for some reason. Other than that, there were no 
  stumbling blocks. The use of array and collections was effective for the reasons state above
  and I was able to successfully implement the normal game of Snake with some extra features.


- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?
  
  If given the chance, I would refactor. I think I would make another class or two and split up
  the work I had in GameCourt in those classes. I don't think my implementation is too combined
  in functionality (since I do separate the different instances of Food and Snake and GameObj), but
  it might be effective to do some of the snake manipulation in a different class just to keep
  things organized. This would also making testing a little simplier. My private state is well 
  encapsulated. I used getters and setters when needing to access or change a variable in a 
  different class. I don't directly notice any place that the user can just change the variable 
  since mostly everything is kept private unless needed for testing purposes.


========================
=: External Resources :=
========================

- Cite any external resources (libraries, images, tutorials, etc.) that you may
  have used while implementing your game.
