/***
 * Main.java
 * The main gameloop of the BallPickle game.
 * by: David Sue, Vadim Mironov, Avishan Ketheswaran and Owen McCarthy
 * December 2, 2024
 */

 import java.awt.*;
 import javax.swing.*;
 import java.util.*;
 
 public class Main implements Runnable {
     
     JFrame frame;
     //Different panels that are drawn on for the title screen, game screen, help screen and character select screen
     GamePanel title, inGame, help, characterSelect;
 
     //The current button that the user is hovering over (e.g. pressing enter will activate an input of that button)
     static enum hovered { charSelect1, charSelect2, charSelect3, titleStart, titleCharSelect, titleHelp, inGame, helpExit };
     static hovered currentHovered;

     static enum level {level1, level2, level3, level4, level5};
     static level currentLevel;
 
     //Whether or not the user has released the keys since they have pressed them (used in the menus, to ensure that you don't move down 2 options if you press the down key for 2 frames)
     boolean upPressedThisTick, downPressedThisTick, leftPressedThisTick, rightPressedThisTick, enterPressedThisTick;
     
     KeyHandler KeyH;
 
     Player player;
     Ball ball;
     Enemy enemy;
 
     boolean playerHitLast, lookRightLast, timeSlowed;
     boolean playerLose=false, playerWin=false;
 
     final int 
         playerXMax = 562,
         playerYMax = 218;
 
     static int frames = 0;
 
     private Thread gameThread;
     private final int FPS = 60;
     private final long OPTIMAL_TIME = 1000000000 / FPS;
 
     public void startGameThread() {
         gameThread = new Thread(this);
         gameThread.start();
     }
 
     @Override
     public void run() {
         long lastTime = System.nanoTime();
         long timer = System.currentTimeMillis();
         double deltaTime = 0;
         int frameCount = 0;
 
         while (gameThread != null) {
             long now = System.nanoTime();
             long elapsedTime = now - lastTime;
             lastTime = now;
 
             // Calculate delta time
             deltaTime += elapsedTime / (double) OPTIMAL_TIME;
 
             // Update game logic when delta reaches 1
             if (deltaTime >= 1) {
                 updateGame();
                 repaintPanels();
                 deltaTime--;
                 frameCount++;   
             }
 
             // Optional: Sleep to prevent CPU overuse
             try {
                 long sleepTime = (lastTime - System.nanoTime() + OPTIMAL_TIME) / 1000000;
                 if (sleepTime > 0) {
                     Thread.sleep(sleepTime);
                 }
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
 
             // Optional: FPS counter (every second)
             if (System.currentTimeMillis() - timer > 1000) {
                 timer += 1000;
                 System.out.println("FPS: " + frameCount);
                 frameCount = 0;
             }
         }
         
     }
 
     private void updateGame() {
         // Consolidate all game update logic here
         frames++;
         //System.out.println(frames); //for debugging
         updateValues();
         if (frames > 363) getInputs();
         tickAbilities();
         checkTimeSlow();
         player.updatePosition();
         ball.move();
         ball.updatePosition();
         enemy.move(ball);
         enemy.updatePosition();
         
         if (ballCollidesWithEnemy(ball, enemy)) {
             enemyStrike(enemy, ball);
         }
         
         // Score and game reset logic
         if (ball.y <= 0) {
             if (playerHitLast) {
                 player.score++;
                 if (player.score==5){
                     player.score=0;
                 }
                 resetGame();
             }
         }
                  
         if (ball.y >= 724) {
             if (!playerHitLast) {
                 enemy.score++;
                 if (enemy.score==5){
                     resetGame();
                     enemy.score=0;
                     player.score=0;
                 }
                 resetGame();
             }
         } 
     }
 
         
 
     private void repaintPanels() {
         // Repaint all panels
         SwingUtilities.invokeLater(() -> {
             title.repaint();
             help.repaint();
             characterSelect.repaint();
             inGame.repaint();
         });
     }
 
     /**
      * Main method
      */
     public static void main(String[] args) {
         SwingUtilities.invokeLater(new Runnable() {
             @Override
             public void run() {
                 Main m = new Main();
             }
         });
     }
 
     /**
      * Constructor: where the variables are initialized. 
      */
     Main() {
         currentHovered = hovered.titleStart;
         timeSlowed = false;
 
         player = new Player(282, 125, 5, 40);
 
         ball = new Ball(300, 500, 10, 0);
         ball.setDestination(300, 500);
 
         enemy = new Enemy(495, 100, 2, 30, 5, 512, 250); // prototype opponent. Level determines probability of enemy hitting the ball back.
 
         upPressedThisTick = false;
         leftPressedThisTick = false;
         downPressedThisTick = false;
         rightPressedThisTick = false;
         enterPressedThisTick = false;
 
         KeyH = new KeyHandler();
 
         frame = new JFrame("BallPickle");
         frame.setSize(1024, 768);
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         frame.setResizable(false);
         frame.addKeyListener(KeyH);
 
         title = new GamePanel("title");
         title.setPreferredSize(new Dimension(GamePanel.winW, GamePanel.winH));
         title.updateValues(player, ball, enemy, timeSlowed);
 
         inGame = new GamePanel("game");
         title.setPreferredSize(new Dimension(GamePanel.winW, GamePanel.winH));
         inGame.updateValues(player, ball, enemy, timeSlowed);
 
         help = new GamePanel("help");
         title.setPreferredSize(new Dimension(GamePanel.winW, GamePanel.winH));
         help.updateValues(player, ball, enemy, timeSlowed);
 
         characterSelect = new GamePanel("character select");
         title.setPreferredSize(new Dimension(GamePanel.winW, GamePanel.winH));
         characterSelect.updateValues(player, ball, enemy, timeSlowed);
         
         frame.add(title);
         frame.pack();
         frame.setLocationRelativeTo(null);
         frame.setVisible(true);
         startGameThread();
     }
 
     /**
      * Gets the inputs each time it is called, and reacts accordingly
      */
     public void getInputs() {
         switch (currentHovered) {
         case titleStart:
             if (KeyH.downPressed && !downPressedThisTick) {
                 downPressedThisTick = true;
                 currentHovered = hovered.titleCharSelect;
             }
             if (KeyH.enterPressed && !enterPressedThisTick) {
                 enterPressedThisTick = true;
                 frame.remove(title);
                 frame.add(inGame);
                 currentHovered = hovered.inGame;
                 frame.revalidate();
             }
             break;
         //mauyybe the real characters wre the friends we made along the way <3
         case titleCharSelect:
             if (KeyH.downPressed && !downPressedThisTick) {
                 downPressedThisTick = true;
                 currentHovered = hovered.titleHelp;
             }
             if (KeyH.upPressed && !upPressedThisTick) {
                 upPressedThisTick = true;
                 currentHovered = hovered.titleStart;
             }
             if (KeyH.enterPressed && !enterPressedThisTick) {
                 enterPressedThisTick = true;
                 frame.remove(title);
                 frame.add(characterSelect);
                 currentHovered = hovered.charSelect1;
                 frame.revalidate();
             }
             break;
         case titleHelp:
             if (KeyH.upPressed && !upPressedThisTick) {
                 upPressedThisTick = true;
                 currentHovered = hovered.titleCharSelect;
             }
             if (KeyH.enterPressed && !enterPressedThisTick) {
                 enterPressedThisTick = true;
                 frame.add(help);
                 frame.remove(title);
                 currentHovered = hovered.helpExit;
                 frame.revalidate();
             }
             break;
         case charSelect1:
             if (KeyH.rightPressed && !rightPressedThisTick) {
                 rightPressedThisTick = true;
                 currentHovered = hovered.charSelect2;
             }
             if (KeyH.enterPressed && !enterPressedThisTick) {
                 enterPressedThisTick = true;
                 player.changeAbility(Player.abilityChoices.riso);
                 frame.remove(characterSelect);
                 frame.add(title);
                 currentHovered = hovered.titleStart;
                 frame.revalidate();
             }
             break;
         case charSelect2:
             if (KeyH.rightPressed && !rightPressedThisTick) {
                 rightPressedThisTick = true;
                 currentHovered = hovered.charSelect3;
             }
             if (KeyH.leftPressed && !leftPressedThisTick) {
                 leftPressedThisTick = true;
                 currentHovered = hovered.charSelect1;
             }
             if (KeyH.enterPressed && !enterPressedThisTick) {
                 enterPressedThisTick = true;
                 player.changeAbility(Player.abilityChoices.adonis);
                 frame.remove(characterSelect);
                 frame.add(title);
                 currentHovered = hovered.titleStart;
                 frame.revalidate();
             }
             break;
         case charSelect3:
             if (KeyH.leftPressed && !leftPressedThisTick) {
                 leftPressedThisTick = true;
                 currentHovered = hovered.charSelect2;
             }
             if (KeyH.enterPressed && !enterPressedThisTick) {
                 enterPressedThisTick = true;
                 player.changeAbility(Player.abilityChoices.tasha);
                 frame.remove(characterSelect);
                 frame.add(title);
                 currentHovered = hovered.titleStart;
                 frame.revalidate();
             }
             break;
         case helpExit:
             if (KeyH.enterPressed && !enterPressedThisTick) {
                 enterPressedThisTick = true;
                 frame.remove(help);
                 frame.add(title);
                 currentHovered = hovered.titleStart;
                 frame.revalidate();
             }
             break;
         case inGame:
             if (player.abilityON && player.ability == Player.abilityChoices.riso) {
                 if (lookRightLast) {
                     player.xx += 20;
                     if (player.xx + player.size > playerXMax) player.xx = playerXMax - player.size;
                 } else {
                     player.xx -= 20;
                     if (player.xx < 0) player.xx = 0;
                 }
             } else {
                 if (KeyH.rightPressed) {
                     lookRightLast=true;
                     player.xx += player.velocity;
                     if (player.xx + player.size > playerXMax) player.xx = playerXMax - player.size;
                 }
                 if (KeyH.leftPressed) {
                     lookRightLast=false;
                     player.xx -= player.velocity;
                     if (player.xx < 0) player.xx = 0;
                 }
                 if (KeyH.upPressed) {
                     player.yy -= player.velocity;
                     if (player.yy < 0) player.yy = 0;
                 }
                 if (KeyH.downPressed) {
                     player.yy += player.velocity;
                     if (player.yy + player.size > playerYMax) player.yy = playerYMax - player.size;
                 }
                 if (KeyH.enterPressed) {
                     if (!enterPressedThisTick) {
                         enterPressedThisTick = true;
                         if (!playerHitLast) {
                             /*spots ball flies to:
                              * left: (312, 150)
                              * centre: (512, 100)
                              * right: (712, 150)
                             */
                             Rectangle playerRect = new Rectangle((int)player.xx + player.getPositionXRelativeTo(), (int)player.yy + player.getPositionYRelativeTo(), player.size, player.size);
                             Rectangle ballRect = new Rectangle((int)ball.xx, (int)ball.yy, ball.size, ball.size);
                             if (playerRect.intersects(ballRect)) {
                                 if (player.abilityON && player.ability == Player.abilityChoices.adonis) {
                                     ball.velocity = 10;
                                 } 
                                 if (ball.velocity == 0) {
                                     ball.velocity = 4;
                                     }
                                 if (KeyH.leftPressed) {
                                     ball.setDestination(312, 150);
                                     //ball.theta = Math.toDegrees(Math.atan(Math.abs((ball.yy - 100)/(ball.xx - 312)) * -1));
                                 } else if (KeyH.rightPressed) {
                                     ball.setDestination(712, 150);
                                     //ball.theta = Math.toDegrees(Math.atan(Math.abs((ball.yy - 100)/(ball.xx - 712)) * -1));
                                 } else {
                                     ball.setDestination(512, 100);
                                     //ball.theta = Math.toDegrees(Math.atan(Math.abs((ball.yy - 100)/(ball.xx - 512)) * -1));
                                 }
                                 playerHitLast = true;
                             }
                         }
                     }
                 }
             }
             if (KeyH.abilityPressed) {
                 switch (player.ability) {
                 case riso:
                     player.useAbility(lookRightLast);
                     break;
                 case adonis:
                     player.useAbility(lookRightLast);
                     break;
                 case tasha:
                     player.useAbility(lookRightLast);
                     break;
                 }
             }
             break;
         default:
             break;
         }
         if (!KeyH.upPressed) upPressedThisTick = false;
         if (!KeyH.downPressed) downPressedThisTick = false;
         if (!KeyH.leftPressed) leftPressedThisTick = false;
         if (!KeyH.rightPressed) rightPressedThisTick = false;
         if (!KeyH.enterPressed) enterPressedThisTick = false;
     }//end getInputs
 
     /**
      * Responsible for having the enemy strike the ball.
      * @param enemy needed for enemy level to be checked, in order to measure probability of a successful return.
      * @param ball needed to change velocity of the ball (return the ball)
      */
     private void enemyStrike(Enemy enemy, Ball ball) {
         if (playerHitLast) {
             Random random = new Random();
             if (random.nextInt(100) < enemy.level * 20) { // Success probability based on enemy's level. Right now I made it so that level 5 = 100%.
                 // Updated the velocity for the ball to be returned.
                 if (!timeSlowed) {
                     ball.velocity = 4;
                 } else {
                     ball.velocity = 2;
                 }
                 
                 ball.setDestination((Math.random() * 400) + 312, 500);
                 // Set playerHitLast to false.
                 playerHitLast = false;
                 System.out.println("Enemy hit the ball!");
             } else {
                 // Debug message.
                 System.out.println("Enemy missed the ball!");
             }
         }
     }
     
     /**
      * Method checks if the ball has collided with the enemy. I reference the method in the actionPerformed() method, where if this bool is true, the enemyStrike() method will run.
      * @param ball: The ball that is checked if it has a collision with the enemy.
      * @param enemy The enemy that is checked if it has a collision with the ball.
      * @return whether or not the ball rectangle intersets with the enemy rectangle (collision detection).
      */
     private boolean ballCollidesWithEnemy(Ball ball, Enemy enemy) {
         Rectangle ballRect = new Rectangle(ball.x, ball.y, ball.size, ball.size);
         Rectangle enemyRect = new Rectangle(enemy.x, enemy.y, enemy.size, enemy.size);
         return ballRect.intersects(enemyRect);
     }
 
     public void resetGame() {
         player.xx = 282;
         player.yy = 125;
         player.updatePosition();
         enemy.xx = 495;
         enemy.yy = 100;
         enemy.updatePosition();
         ball.xx = 300;
         ball.yy = 500;
         ball.velocity = 0;
         ball.setDestination(300, 500);
         ball.updatePosition();
         playerHitLast=  false;
     }
 
     public void tickAbilities() {
         if (player.abilityON) {
             player.abilityTime++;
             switch (player.ability) {
             case riso:
                 if (player.abilityTime == 10) {
                     player.abilityON = !player.abilityON;
                     player.abilityTime = 0;
                 }
                 break;
             case adonis:
                 if (player.abilityTime == 100) {
                     player.abilityON = !player.abilityON;
                     player.abilityTime = 0;
                 }
                 break;
             case tasha:
                 if (player.abilityTime == 100) {
                     player.abilityON = !player.abilityON;
                     timeSlowed = false;
                     ball.velocity *= 2;
                     enemy.velocity *= 2;
                     player.abilityTime = 0;
                 }
                 break;
             }
         }
     }
 
     public void checkTimeSlow() {
         if (!timeSlowed && player.abilityON && player.ability == Player.abilityChoices.tasha) {
             timeSlowed = true;
             ball.velocity /= 2;
             enemy.velocity /= 2;
         }
     }
 
     public void drawCooldown(Graphics2D g2, double radius, double percent) {
         g2.setColor(Color.BLUE);
         g2.fillRect(0, 0, 100, 100);
     }
     
     public void updateValues() {
         title.updateValues(player, ball, enemy, timeSlowed);
         inGame.updateValues(player, ball, enemy, timeSlowed);
         help.updateValues(player, ball, enemy, timeSlowed);
         characterSelect.updateValues(player, ball, enemy, timeSlowed);
     }
 }