// Import various libraries that we need to use
import java.awt.*;                  // Needed for graphics
import java.io.*;                   // Needed for catching errors for importing a file
import java.awt.event.KeyEvent;     // Needed for key controlled actions
import java.awt.event.KeyListener;  // Needed for key identification
import javax.swing.*;               // Needed for windows and frames
import javax.imageio.*;             // Needed for image import


// Declare a new class called Game which uses JFrame and KeyListener
public class Game extends JFrame implements KeyListener {
  
  public Image characterImg; // Image storing the character image
  public Image pipe;
  public Image sky;
  public Image grass;
  public Image cloud;
  public Image finish;
  private int characterX; // Integer storing the character's current x location
  private int characterY; // Integer storing the character's current y location
  
  private int speed; // Integer storing the character's speed
  
  private boolean upKey; // Boolean true when the up key is being pressed
  private boolean downKey; // Boolean true when the down key is being pressed
  private boolean rightKey; // Boolean true when the right key is being pressed
  private boolean leftKey; // Boolean true when the left key is being pressed
  private boolean onGround;
  
  private int screenWidth = 640;
  private int screenHeight = 480;
  
  private int tileSize = 51;
  
   private int[][] map = 
                {{ 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 },
                 { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 4 },
                 { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 4 },
                 { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 4 },
                 { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 4 },
                 { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 4 },
                 { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 4 },
                 { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 4 },
                 { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 4 },
                 { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 4 },
                 { 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2 }};
  
  private int screenOffsetX = (screenWidth/2) - (tileSize / 2);
  private int screenOffsetY = (screenHeight/2) - (tileSize / 2);
  private boolean flyKeyP = true;
  private boolean flyKeyR = true;
  private String lifeStatus = "Alive";
  private int trackScore = 0;
  
  // Main method runs automatically
  public static void main( String[] args ) {
    Game game = new Game(); // Instantiate a game object which will store all our data
    while( true ) { // Endless game loop
      try { Thread.sleep(1000/60); } catch (InterruptedException e) {} // Set framerate to 60 frames per second
      game.heartbeat(); // Execute the game's heartbeat
    }
  }
  
  // Game contructor to initialize all our data
  public Game() {
    super("Game"); // Call the super (in this case JFrame) and set the window's title to "Game"
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Tell the application to close the whole application when the window is closed
    setSize( screenWidth, screenHeight ); // Set the size of the window
    setResizable(false); // Disable the window from being resized
    addKeyListener(this); // Attach a key listener to the window
    setVisible(true); // Show the window
    
    // Try to load the image
    try {
      // Load the image called "character.png" which should be in the same folder as this code
      characterImg = ImageIO.read( new File( "Goose.png" ) );
      pipe = ImageIO.read( new File( "pipe.png" ) );
      sky = ImageIO.read( new File("sky.png"));
      cloud = ImageIO.read( new File("cloud.jpg"));
      grass = ImageIO.read( new File("grass.jpg"));
      finish = ImageIO.read( new File("finish.png"));
    } catch( IOException i ) {
      // If there was an error opening the image, output the information about the error
      i.printStackTrace();
    }
   
    // Initialize the default values for our data
    characterX = 100;
    characterY = 100;
    speed = 2;
    
    // Initialize our default keys
    upKey = false;
    downKey = false;
    rightKey = false;
    leftKey = false;
    
  }
  
  // Heartbeat executes at the beginning of every frame
  public void heartbeat() {
    // Check if the up key is currently being pressed.
    if( downKey) {
     //  System.out.println("Character going up!");
      // System.out.println("onGround: " + onGround);
      if(flyKeyR == false && flyKeyP == true){
          //CHange from 4 to 2 when testing
          characterY += 4*speed;
           try {
          // Load the image called "character.png" which should be in the same folder as this code
          characterImg = ImageIO.read( new File( "Goose.png" ) );
        } catch( IOException i ) {
          // If there was an error opening the image, output the information about the error
          i.printStackTrace();
        }
      
        }
      
    }
    // Check if the down key is currently being pressed.
    if( upKey ) {
      characterY -= speed; // Move the character up by speed number of pixels

    }
    // Check if the right key is currently being pressed.
    if( rightKey ) {
      characterX += 3*speed; // Move the character up by speed number of pixels
    }
    // Check if the left key is currently being pressed.
    if( leftKey ) {
      characterX -= 3*speed; // Move the character up by speed number of pixels
    }
    if(characterY <= -55){
            onGround = true;
            //System.out.println("Character now on Ground");
            //System.out.println("onGround: " + onGround);
            
     try {
      // Load the image called "character.png" which should be in the same folder as this code
      characterImg = ImageIO.read( new File( "Goose2.png" ) );
    } catch( IOException i ) {
      // If there was an error opening the image, output the information about the error
      i.printStackTrace();
    }
    if(characterX > 580){
        characterX = 580;
    }        
    }
    if(characterY <= 381 && characterY >= -53){
        //System.out.println("Running Gravity");
        //System.out.println("onGround: " + onGround);
        //onGround = false;
        characterY -= (2*speed);
    } else if(characterY >= 381){
        characterY = 380;
    }
    // Call the screen to redraw the content, calls paint below
    
    int tileX = ( ( characterX + ( tileSize / 2 ) ) / tileSize );
    int tileY = ( ( characterY + ( tileSize / 2 ) ) / tileSize );
    
   
    if(characterX == -182 && (characterY > 236 || characterY < 142)){
        lifeStatus = "Dead";
        System.out.println("Game Over!");
        System.out.println("Your final track score was: " + trackScore + ". Will you beat it next game?");
        System.exit(0);
    } else if(characterX == -380 && (characterY < 102 || characterY > 188)){
        lifeStatus = "Dead";
        System.out.println("Game Over!");
        System.out.println("Your final track score was: " + trackScore + ". Will you beat it next game?");
        System.exit(0);
    }else if(characterX == -584 && (characterY < 148 || characterY > 240)){
        lifeStatus = "Dead";
        System.out.println("Game Over!");
        System.out.println("Your final track score was: " + trackScore + ". Will you beat it next game?");
        System.exit(0);
    }else if(characterX == -794 && (characterY < -8 || characterY > 86)){
        lifeStatus = "Dead";
        System.out.println("Game Over!");
        System.out.println("Your final track score was: " + trackScore + ". Will you beat it next game?");
        System.exit(0);
    }else if(characterX == -998 && (characterY < 200 || characterY > 288)){
        lifeStatus = "Dead";
        System.out.println("Game Over!");
        System.out.println("Your final track score was: " + trackScore + ". Will you beat it next game?");
        System.exit(0);
    }else if(characterX == -1196 && (characterY < 98 || characterY > 186)){
        lifeStatus = "Dead";
        System.out.println("Game Over!");
        System.out.println("Your final track score was: " + trackScore + ". Will you beat it next game?");
        System.exit(0);
    }else if(characterX == -1352 && (characterY < 98 || characterY > 186)){
        lifeStatus = "Dead";
        System.out.println("Game Over!");
        System.out.println("Your final track score was: " + trackScore + ". Will you beat it next game?");
        System.exit(0);
    }else if(characterX == -1156 && (characterY < 748 || characterY > 342)){
        lifeStatus = "Dead";
        System.out.println("Game Over!");
        System.out.println("Your final track score was: " + trackScore + ". Will you beat it next game?");
        System.exit(0);
    }else if(characterX == -1832 && (characterY < -2 || characterY > 86)){
        lifeStatus = "Dead";
        System.out.println("Game Over!");
        System.out.println("Your final track score was: " + trackScore + ". Will you beat it next game?");
        System.exit(0);
    }else if(characterX == -2012 && (characterY < 146 || characterY > 240)){
        lifeStatus = "Dead";
        System.out.println("Game Over!");
        System.out.println("Your final track score was: " + trackScore + ". Will you beat it next game?");
        System.exit(0);
    }else if(characterX == -2216 && (characterY < 0 || characterY > 84)){
        lifeStatus = "Dead";
        System.out.println("Game Over!");
        System.out.println("Your final track score was: " + trackScore + ". Will you beat it next game?");
        System.exit(0);
    }else if(characterX == -2420 && (characterY < 138 || characterY > 192)){
        lifeStatus = "Dead";
        System.out.println("Game Over!");
        System.out.println("Your final track score was: " + trackScore + ". Will you beat it next game?");
        System.exit(0);
    }else if(characterX == -2594){
        characterX = 100;
        characterY = 100;
        System.out.println("You completed the track! Can you do it again?");
        trackScore += 1;
        System.out.println("Track Score: " + trackScore);
    }
        
    repaint();
  }
  
  // The paint method tells the screen where to draw things, in this case we use double-buffering
  public void paint( Graphics page ){
    // Create an image, this image is where we will draw the next frame
    Image frame = createImage(getWidth(),getHeight());
    Image pipeImage = createImage(getWidth(),getHeight());
    // Instantiate a graphics object to store all our items drawn to our frame
    Graphics frameGraphics = frame.getGraphics();
    Graphics frameGraphicsPipe = frame.getGraphics();
    // Draw the character object at the x and y coordinates defined, make it 100 pixels tall and 100 pixels wide
    
    
    for(int y=0; y<map.length; y++){
        for(int x = 0; x<map[y].length; x++){
        if(map[y][x] == 0){
        frameGraphics.drawImage(
            pipe,
            (x*tileSize) + characterX - screenOffsetX,
            (y*tileSize) + characterY - screenOffsetY,
            tileSize,
            tileSize,
            null
        );
        } else if(map[y][x] == 1){
        frameGraphics.drawImage(
            sky,
            (x*tileSize) + characterX - screenOffsetX,
            (y*tileSize) + characterY - screenOffsetY,
            tileSize,
            tileSize,
            null
        );
      } else if(map[y][x] == 2){
        frameGraphics.drawImage(
            grass,
            (x*tileSize) + characterX - screenOffsetX,
            (y*tileSize) + characterY - screenOffsetY,
            tileSize,
            tileSize,
            null
        );
    }
    else if(map[y][x] == 3){
        frameGraphics.drawImage(
            cloud,
            (x*tileSize) + characterX - screenOffsetX,
            (y*tileSize) + characterY - screenOffsetY,
            tileSize,
            tileSize,
            null
        );
    }else if(map[y][x] == 4){
        frameGraphics.drawImage(
            finish,
            (x*tileSize) + characterX - screenOffsetX,
            (y*tileSize) + characterY - screenOffsetY,
            tileSize,
            tileSize,
            null
        );
    }
}
}
    
    frameGraphics.drawImage( characterImg, screenOffsetX, screenOffsetY, tileSize, tileSize, null );
    // Set the graphics we are about to draw to blue
    frameGraphics.setColor( Color.BLUE );
    // Draw some text at coordinates 10, 42 and put the String representing the character's x and y coordinates in
    frameGraphics.drawString( characterX + ", " + characterY, 10, 42 );
    frameGraphics.drawString( upKey + ", " + downKey + ", " + leftKey + ", " + rightKey, 10, 62 );
    frameGraphics.drawString( "You are: " + lifeStatus, 10, 82 );
    frameGraphics.drawString( "Track Score: " + trackScore, 10, 102 );
    // Now swap the currently display screen with the one we just created and draw it at coordinates 0, 0 so it takes up the whole screen
    page.drawImage(frame,0,0,null);
}
  
  // We implement a KeyListener so we need three KeyListener methods
  public void keyPressed(KeyEvent e) {
    // check if either the 'w' key or the up arrow is the key being pressed
    if( (e.getKeyChar() == 'w' || e.getKeyCode() == KeyEvent.VK_UP) && flyKeyR == true) {
      flyKeyR= false;
        flyKeyP = true;
        // if either is being pressed, set the upKey boolean to true
      downKey = true;
    }
    // check if either the 's' key or the down arrow is the key being pressed
    if( e.getKeyChar() == 's' || e.getKeyCode() == KeyEvent.VK_DOWN ) {
      // if either is being pressed, set the downKey boolean to true
      upKey = true;
    }
    // check if either the 'd' key or the right arrow is the key being pressed
    if( e.getKeyChar() == 'd' || e.getKeyCode() == KeyEvent.VK_RIGHT ) {
      // if either is being pressed, set the rightKey boolean to true
      leftKey = true;
    }
    // check if either the 'a' key or the left arrow is the key being pressed
    if( e.getKeyChar() == 'a' || e.getKeyCode() == KeyEvent.VK_LEFT ) {
      // if either is being pressed, set the leftKey boolean to true
      rightKey = true;
    }
  } 
  
  public void keyReleased(KeyEvent e) {
    // check if either the 'w' key or the up arrow is the key being released
    if( e.getKeyChar() == 'w' || e.getKeyCode() == KeyEvent.VK_UP ) {
      flyKeyR = true;
        // if either is being released, set the rightKey boolean to false
      downKey = false;
    }
    // check if either the 's' key or the down arrow is the key being released
    if( e.getKeyChar() == 's' || e.getKeyCode() == KeyEvent.VK_DOWN ) {
      // if either is being released, set the rightKey boolean to false
      upKey = false;
    }
    // check if either the 'd' key or the right arrow is the key being released
    if( e.getKeyChar() == 'd' || e.getKeyCode() == KeyEvent.VK_RIGHT ) {
      // if either is being released, set the rightKey boolean to false
      leftKey = false;
    }
    // check if either the 'a' key or the left arrow is the key being released
    if( e.getKeyChar() == 'a' || e.getKeyCode() == KeyEvent.VK_LEFT ) {
      // if either is being released, set the rightKey boolean to false
      rightKey = false;
    }
  }
  
  // We need to also override the KeyTyped method for a KeyListener, but we don't actually do anything with it
  public void keyTyped(KeyEvent e) { 
  }
  
}