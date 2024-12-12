/***
 * Main.java
 * The main gameloop of the BallPickle game.
 * by: David Sue, Vadim Mironov, Avishan Ketheswaran and Owen McCarthy
 * December 2, 2024
 */

import java.awt.*;
import javax.swing.*;
import javax.imageio.*;
import java.awt.image.*;
import java.util.Random;
import java.awt.event.*;

public class Main implements ActionListener {
    
    JFrame frame;
    //Different panels that are drawn on for the title screen, game screen, help screen and character select screen
    TitlePanel title;
    GamePanel inGame;
    HelpPanel help;
    CharacterSelectPanel characterSelect;

    BufferedImage characterSelectBg, select, titleBg, titleSelect, helpBg, court;

    //The current button that the user is hovering over (e.g. pressing enter will activate an input of that button)
    static enum hovered { charSelect1, charSelect2, charSelect3, titleStart, titleCharSelect, titleHelp, inGame, helpExit };
	static private hovered currentHovered;

    //Whether or not the user has released the keys since they have pressed them (used in the menus, to ensure that you don't move down 2 options if you press the down key for 2 frames)
    boolean upPressedThisTick, downPressedThisTick, leftPressedThisTick, rightPressedThisTick, enterPressedThisTick;
    
    KeyHandler KeyH;
    Timer gameLoopTimer;
    Player player;
    Ball ball;
    Enemy enemy;

    boolean playerHitLast, lookRightLast;

    final int 
        playerPositionXRelativeTo = 230,
        playerPositionYRelativeTo = 475,
        playerXMax = 562,
        playerYMax = 218;

    // int frameCount = 0; //for debugging

    /**
     * Main method
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Main m = new Main();
            }
        });
    }

    /**
     * Constructor: where the variables are initialized. 
     */
    Main() {
        try {
			characterSelectBg = ImageIO.read(this.getClass().getResource("sprites/characterSelect.png"));
			select = ImageIO.read(this.getClass().getResource("sprites/select.png"));
			titleBg = ImageIO.read(this.getClass().getResource("sprites/title.png"));
			titleSelect = ImageIO.read(this.getClass().getResource("sprites/titleSelect.png"));
			helpBg = ImageIO.read(this.getClass().getResource("sprites/help.png"));
            court = ImageIO.read(this.getClass().getResource("sprites/court.png"));
        } catch (Exception e) {
			System.out.println("Failed to load image.");
		}

        gameLoopTimer = new Timer(5, this);
        gameLoopTimer.setInitialDelay(5);
        gameLoopTimer.setActionCommand("gameLoopTimer");
        gameLoopTimer.start();

        currentHovered = hovered.titleStart;

        player = new Player(282, 125, 5, 40);

        ball = new Ball(300, 500, 10, 0);
        ball.setDestination(300, 500);

        enemy = new Enemy(495, 100, 2, 30, 5, 512, 100); // prototype opponent. Level determines probability of enemy hitting the ball back.

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

        title = new TitlePanel();
        title.setPreferredSize(new Dimension(1024, 768));

        inGame = new GamePanel();
        title.setPreferredSize(new Dimension(1024, 768));

        help = new HelpPanel();
        title.setPreferredSize(new Dimension(1024, 768));

        characterSelect = new CharacterSelectPanel();
        title.setPreferredSize(new Dimension(1024, 768));
        
        frame.add(title);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
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
                    /*spots ball flies to:
                     * left: (312, 100)
                     * centre: (512, 100)
                     * right: (712, 100)
                    */
                    if (player.x + playerPositionXRelativeTo < ball.x && player.x + playerPositionXRelativeTo + player.size > ball.x && player.y + playerPositionYRelativeTo < ball.y && player.y + playerPositionYRelativeTo + player.size > ball.y) {
                        ball.velocity = 4;
                        if (KeyH.leftPressed) {
                            ball.setDestination(312, 100);
                            //ball.theta = Math.toDegrees(Math.atan(Math.abs((ball.yy - 100)/(ball.xx - 312)) * -1));
                        } else if (KeyH.rightPressed) {
                            ball.setDestination(712, 100);
                            //ball.theta = Math.toDegrees(Math.atan(Math.abs((ball.yy - 100)/(ball.xx - 712)) * -1));
                        } else {
                            ball.setDestination(512, 100);
                            //ball.theta = Math.toDegrees(Math.atan(Math.abs((ball.yy - 100)/(ball.xx - 512)) * -1));
                        }
                        playerHitLast = true;
                    }
                    
                    
                }
            }

            if (KeyH.dashPressed){
                if(lookRightLast){
                    player.dash(10);
                    if (player.xx + player.size > playerXMax) player.xx = playerXMax - player.size;
                }
                if(!lookRightLast){
                    player.dash(-10);
                    if (player.xx < 0) player.xx = 0;
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
     * Responds to game loop timer, updates all values.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String event = e.getActionCommand();
        switch (event) {
        case "gameLoopTimer":
            getInputs();

            //Repaints each panel
            title.repaint();
            help.repaint();
            characterSelect.repaint();
            inGame.repaint();
            
            //updates the position of the player, ball, and enemy
            player.updatePosition();
            ball.move();
            ball.updatePosition();
            enemy.move(ball);
            //enemyMove(enemy, ball);
            enemy.updatePosition();
            if (ballCollidesWithEnemy(ball, enemy)) { //Checks the bool to see if ball has hit enemy, if true, tries to do enemy strike (success based on enemy level and probability)
               enemyStrike(enemy, ball);
            }

            if (ball.y <=0){
                if (playerHitLast) {
                    player.score++;
                    resetGame();
                }
            }
                 
            if (ball.y >= 724) {
                if (!playerHitLast){
                    enemy.score++;
                    resetGame();
                }
            } 
            //for debugging
            //System.out.println("BALL DESTINATION X: " + ball.destinationX + ", Y: " + ball.destinationY);
            //System.out.println("ENEMY DESTINATION X: " + enemy.destinationX + ", Y: " + enemy.destinationY);
            //System.out.println("Enemy score: " + enemy.score + ", Player score: " + player.score);
            //System.out.println("Player hitlast: " + playerHitLast);
            // System.out.print("frame: " + frameCount + ", hovered: " + currentHovered + ", key pressed: ");
            // if (KeyH.upPressed) System.out.print("UP ");
            // if (KeyH.downPressed) System.out.print("DOWN ");
            // if (KeyH.leftPressed) System.out.print("LEFT ");
            // if (KeyH.rightPressed) System.out.print("RIGHT ");
            // if (KeyH.enterPressed) System.out.print("F ");
            // System.out.println();
            // frameCount++;
            break;
        default:
            break;
        }
    }//end actionPerformed

    /***
     * TitlePanel is the panel where the title screen is drawn
     */
    private class TitlePanel extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D)g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.drawImage(titleBg, 0, 0, null);
            switch (currentHovered) {
            case titleStart:
                g2.drawImage(titleSelect, 385, 379, null);
                g2.rotate(Math.PI);
                g2.drawImage(titleSelect, -630, -438, null);
                g2.rotate(Math.PI);
                break;
            case titleCharSelect:
                g2.drawImage(titleSelect, 152, 428, null);
                g2.rotate(Math.PI);
                g2.drawImage(titleSelect, -867, -490, null);
                g2.rotate(Math.PI);
                break;
            case titleHelp:
                g2.drawImage(titleSelect, 407, 479, null);
                g2.rotate(Math.PI);
                g2.drawImage(titleSelect, -612, -538, null);
                g2.rotate(Math.PI);
                break;
            default:
                break;
            }
        }
    }

    /***
     * HelpPanel is the panel where the help screen is drawn
     */
    private class HelpPanel extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D)g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.drawImage(helpBg, 0, 0, null);
        }
    }

    /***
     * CharacterSelectPanel is the panel where the character select screen is drawn
     */
    private class CharacterSelectPanel extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D)g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.drawImage(characterSelectBg, 0, 0, null);
            g2.drawImage(select, 32, 615, null);
            g2.drawImage(select, 384, 615, null);
            g2.drawImage(select, 720, 615, null);

            //Inflates the size of the button that the user is hovering
            switch (currentHovered) {
            case charSelect1:
                g2.drawImage(select, 12, 605, select.getWidth() + 40, select.getHeight() + 20, null);
                break;
            case charSelect2:
                g2.drawImage(select, 364, 605, select.getWidth() + 40, select.getHeight() + 20, null);
                break;
            case charSelect3:
                g2.drawImage(select, 700, 605, select.getWidth() + 40, select.getHeight() + 20, null);
                break;
            default:
                break;
            }
        }
    }

    /***
     * GamePanel is the panel where the game screen is drawn
     */
    private class GamePanel extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D)g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.drawImage(court, 0, 0, null);
            g2.fillRect(player.x + playerPositionXRelativeTo, player.y + playerPositionYRelativeTo, player.size, player.size);
            g2.fillRect(ball.x, ball.y, ball.size, ball.size); //draws the ball
            g2.fillRect(enemy.x, enemy.y, enemy.size, enemy.size); //draws the enemy
            g2.drawString("Enemy: " + enemy.score, 150, 60);
            g2.drawString("Player: " + player.score, 800, 720);
        }
    }
    
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
    }
}