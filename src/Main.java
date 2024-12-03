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

    final int 
        playerPositionXRelativeTo = 230,
        playerPositionYRelativeTo = 383,
        playerXMax = 562,
        playerYMax = 309;

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

        player = new Player(0, 0, 5, 40);

        ball = new Ball(300, 500, 10, 0);
        ball.setTheta(0.0);

        enemy = new Enemy(495, 100, 2, 30, 5); // prototype opponent. Level determines probability of enemy hitting the ball back.

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
                System.out.println("help exit AKLSJDHASKLDHASKLJDHAS");
                enterPressedThisTick = true;
                frame.remove(help);
                frame.add(title);
                currentHovered = hovered.titleStart;
                frame.revalidate();
            }
            break;
        case inGame:
            if (KeyH.rightPressed) {
                player.xx += player.velocity;
                if (player.xx + player.size > playerXMax) player.xx = playerXMax - player.size;
            }
            if (KeyH.leftPressed) {
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
                        ball.velocity = 1;
                        if (KeyH.leftPressed) {
                            ball.theta = Math.toDegrees(Math.atan(Math.abs((ball.yy - 100)/(ball.xx - 312)) * -1));
                        } else if (KeyH.rightPressed) {
                            ball.theta = Math.toDegrees(Math.atan(Math.abs((ball.yy - 100)/(ball.xx - 712)) * -1));
                        } else {
                            ball.theta = Math.toDegrees(Math.atan(Math.abs((ball.yy - 100)/(ball.xx - 512)) * -1));
                        }
                    }
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
            moveBall(ball);
            ball.updatePosition();
            enemyMove(enemy, ball);
            enemy.updatePosition();
            if (ballCollidesWithEnemy(ball, enemy)) { //Checks the bool to see if ball has hit enemy, if true, tries to do enemy strike (success based on enemy level and probability)
               enemyStrike(enemy, ball);
            }
            //for debugging
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
            g2.fillRect(ball.x, ball.y, ball.size, ball.size);
            g2.fillRect(enemy.x, enemy.y, enemy.size, enemy.size); //draws the enemy
        }
    }

    /**
     * Responsible for moving the enemy to the ball as well as back to the center position.
     * @param enemy needed to move the enemy character, check its position relative to the ball
     * @param ball needed to check the position of the ball, in order for the enemy to "intercept" it.
     */
    public void enemyMove(Enemy enemy, Ball ball) {
        // Enemy will be idle at these center coordinated if the ball is far away, and the enemy will also return to these coordinates if the ball is far enough away.
        int centerX = 495; 
        int centerY = 100;
        // The enemy shouldn't exceed these y coordinates, so as to not overlap with the net and such.
        int maxY = 255;
        // Ensures the enemy stays under y = 255.
        if (enemy.yy >= maxY) enemy.yy = maxY;

        if (ball.yy >= 425) { 
            // If the ball is far from the enemy the enemy will try to move back to their center coordinates
            if (enemy.yy < centerY) enemy.yy += enemy.velocity; //checks if the enemy is not on the center coordinates, if so, moves the enemy back to the center coordinates (this is only if the ball is at y >= 425)  
            if (enemy.yy > centerY) enemy.yy -= enemy.velocity;
            if (enemy.xx < centerX) enemy.xx += enemy.velocity; //same thing but for x coordinates.
            if (enemy.xx > centerX) enemy.xx -= enemy.velocity;
            return; 
        }
    
        // track (follow) ball's X position if ball is close to the enemy
        if (ball.yy <= 385)
            if (enemy.xx < ball.xx) enemy.xx += enemy.velocity; 
            else if (enemy.xx > ball.xx) enemy.xx -= enemy.velocity;
    
            // same thing but for ball's Y posiition.
            if (ball.yy < enemy.yy) enemy.yy -= enemy.velocity; 
            else if (ball.yy > enemy.yy) enemy.yy += enemy.velocity;
    }
    
    /**
     * Responsible for having the enemy strike the ball.
     * @param enemy needed for enemy level to be checked, in order to measure probability of a successful return.
     * @param ball needed to change velocity of the ball (return the ball)
     */
    private void enemyStrike(Enemy enemy, Ball ball) {
        Random random = new Random();
        if (random.nextInt(100) < enemy.level * 20) { // Success probability based on enemy's level. Right now I made it so that level 5 = 100%.
            // The y-coordinate of the ball gets pushed up by 10px to avoid hitbox overlap. This stops the jittering, and makes the hits look a little more realistic.
            ball.yy += 10;
            // Updated the velocity for the ball to be returned.
            ball.velocity *= -1;
        } else {
            // Debug message.
            System.out.println("Enemy missed the ball!");
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

    /**
     * Moves the ball
     * @param b: The ball to me moved
     */
    private void moveBall(Ball b) {
        double vx, vy;
        if (b.theta < 90) {
            vx = Math.cos(Math.toRadians(b.theta)) * b.velocity;
            vy = Math.sin(Math.toRadians(b.theta)) * b.velocity;
        } else if (b.theta < 180) {
            vx = Math.cos(Math.toRadians(180 - b.theta)) * b.velocity;
            vy = Math.sin(Math.toRadians(180 - b.theta)) * b.velocity;
        } else if (b.theta < 270) {
            vx = Math.cos(Math.toRadians(180 + b.theta)) * b.velocity;
            vy = Math.sin(Math.toRadians(180 + b.theta)) * b.velocity;
        } else {
            vx = Math.cos(Math.toRadians(360 - b.theta)) * b.velocity;
            vy = Math.sin(Math.toRadians(360 - b.theta)) * b.velocity;
        }
        b.xx += vx;
        b.yy += vy;
    }
}