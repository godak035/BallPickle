/***
 * Enemy.java
 * The enemy of the BallPickle game. It moves around on it's side of the court in human-like patterns and hits the ball back to you.
 * by: David Sue, Vadim Mironov, Avishan Ketheswaran and Owen McCarthy
 * January 14, 2025
 */

import java.awt.Rectangle;
import java.util.ArrayList;

public class Enemy extends Entity {
    
    public static enum enemyTypes { AverageJoe, StrongHercules, GradyTwin1, GradyTwin2, TwoBallWalter, TeleportSicilia }
    private enemyTypes enemyType;
    private double 
        destinationX, //x coordinate of the destination
        destinationY, //y coordinate of the destination
        idleX, //x coordinate of the idle position
        idleY; //y coordinate of the idle position
    private boolean isActive;
    private ArrayList<BallShadow> ballShadows;

   /**
    * Constructor
    * @param xx: The specific x position of the enemy
    * @param yy: The specific y position of the enemy
    * @param s: The size of the enemy
    * @param v: The velocity of the enemy
    * @param level: The level difficulty of the enemy
    */
    public Enemy(double xx, double yy, double v, int s, enemyTypes e) {
        super(xx, yy, v, s);
        this.enemyType = e;
        switch (e) {
            case AverageJoe, StrongHercules, TwoBallWalter, TeleportSicilia -> {                
                this.idleX = (int)(GamePanel.WINW / 2.0);
                this.idleY = (int)(250.0 / 768.0 * GamePanel.WINH);
            }
            case GradyTwin1 -> {
                this.idleX = (int)(412.0 / 1024.0 * GamePanel.WINW);
                this.idleY = (int)(250.0 / 768.0 * GamePanel.WINH);
            }
            case GradyTwin2 -> {
                this.idleX = (int)(612.0 / 1024.0 * GamePanel.WINW);
                this.idleY = (int)(250.0 / 768.0 * GamePanel.WINH);
            }
            default -> {}
        }
        this.isActive = true;
    }

    /**
     * Updates the x and y variables of the enemy to align with the xx and yy
     */
    @Override
    public void updatePosition() {
        this.x = (int) xx;
        this.y = (int) yy;
    }

    /**
     * updates its current ArrayList of BallShadows to align with that of the Main class
     * @param bs  The ArrayList of BallShadows, that includes their position and velocity
     */
    public void updateValues(ArrayList<BallShadow> bs) { this.ballShadows = bs; }

    //getter methods
    public boolean getActive() { return this.isActive; }
    public ArrayList<BallShadow> getBallShadows() { return this.ballShadows; }

    /**
     * Sets if the enemy is currently playing or not
     * @param activity  If the enemy is currently playing
     */
    public void setActive(boolean activity) { this.isActive = activity; }

    
    /**
     * Sets a new destination that tne enemy will move towards in the future
     * @param dX  the x component of the new destination
     * @param dY  the y component of the new destination
     */
    public void setDestination(double dX, double dY) {
        this.destinationX = dX;
        this.destinationY = dY;
    }

    /**
     * Decides where to move and moves towards that location
     */
    public void move() {
        if (!ballShadows.get(1).getActive()) { //only one ball in play
            if (ballShadows.get(0).getDestinationY() < 300) { //ball is moving towards enemy, enemy must move towards ball's destination
                if (this.enemyType != enemyTypes.GradyTwin1 && this.enemyType != enemyTypes.GradyTwin2) {
                    this.destinationX = ballShadows.get(0).getDestinationX();
                    this.destinationY = ballShadows.get(0).getDestinationY();
                } else if (this.enemyType == enemyTypes.GradyTwin1) {
                    if (ballShadows.get(0).getDestinationX() < (GamePanel.WINW / 2.0)) {
                        if (Main.frames % 1000 < 25) {
                            this.destinationX = ballShadows.get(0).getDestinationX();
                            this.destinationY = ballShadows.get(0).getDestinationY() - (GamePanel.WINH * 0.1);
                        } else if (Main.frames % 1000 < 125) {
                            this.destinationX = ballShadows.get(0).getDestinationX();
                            this.destinationY = ballShadows.get(0).getDestinationY();
                        } else if (Main.frames % 1000 < 150) {
                            this.destinationX = ballShadows.get(0).getDestinationX() + (GamePanel.WINW * 0.1);
                            this.destinationY = ballShadows.get(0).getDestinationY() + (GamePanel.WINH * 0.1); 
                        } else if (Main.frames % 1000 < 250) {
                            this.destinationX = ballShadows.get(0).getDestinationX();
                            this.destinationY = ballShadows.get(0).getDestinationY();
                        } else if (Main.frames % 1000 < 275) {
                            this.destinationX = ballShadows.get(0).getDestinationX() - (GamePanel.WINW * 0.1);
                            this.destinationY = ballShadows.get(0).getDestinationY();
                        } else if (Main.frames % 1000 < 375) {
                            this.destinationX = ballShadows.get(0).getDestinationX();
                            this.destinationY = ballShadows.get(0).getDestinationY();
                        } else if (Main.frames % 1000 < 400) {
                            this.destinationX = ballShadows.get(0).getDestinationX() + (GamePanel.WINW * 0.1);
                            this.destinationY = ballShadows.get(0).getDestinationY() - (GamePanel.WINH * 0.1);
                        } else if (Main.frames %  1000 < 500) {
                            this.destinationX = ballShadows.get(0).getDestinationX();
                            this.destinationY = ballShadows.get(0).getDestinationY();
                        } else if (Main.frames %  1000 < 525) {
                            this.destinationX = ballShadows.get(0).getDestinationX();
                            this.destinationY = ballShadows.get(0).getDestinationY() + (GamePanel.WINH * 0.1);
                        } else if (Main.frames %  1000 < 625) {
                            this.destinationX = ballShadows.get(0).getDestinationX();
                            this.destinationY = ballShadows.get(0).getDestinationY();
                        } else if (Main.frames %  1000 < 650) {
                            this.destinationX = ballShadows.get(0).getDestinationX() - (GamePanel.WINW * 0.1);
                            this.destinationY = ballShadows.get(0).getDestinationY() + (GamePanel.WINH * 0.1);
                        } else if (Main.frames %  1000 < 750) {
                            this.destinationX = ballShadows.get(0).getDestinationX();
                            this.destinationY = ballShadows.get(0).getDestinationY();
                        } else if (Main.frames %  1000 < 775) {
                            this.destinationX = ballShadows.get(0).getDestinationX() - (GamePanel.WINW * 0.1);
                            this.destinationY = ballShadows.get(0).getDestinationY() - (GamePanel.WINH * 0.1);
                        } else if (Main.frames %  1000 < 875) {
                            this.destinationX = ballShadows.get(0).getDestinationX();
                            this.destinationY = ballShadows.get(0).getDestinationY();
                        } else if (Main.frames %  1000 < 900) {
                            this.destinationX = ballShadows.get(0).getDestinationX() + (GamePanel.WINW * 0.1);
                            this.destinationY = ballShadows.get(0).getDestinationY();
                        } else {
                            this.destinationX = ballShadows.get(0).getDestinationX();
                            this.destinationY = ballShadows.get(0).getDestinationY();
                        }
                    } else {
                        this.destinationX = this.idleX;
                        this.destinationY = this.idleY;
                    }  
                } else if (this.enemyType == enemyTypes.GradyTwin2) {
                    if (ballShadows.get(0).getDestinationX() >= (GamePanel.WINW / 2.0)) {
                        if (Main.frames % 1000 < 25) {
                            this.destinationX = ballShadows.get(0).getDestinationX();
                            this.destinationY = ballShadows.get(0).getDestinationY() - (GamePanel.WINH * 0.1);
                        } else if (Main.frames % 1000 < 125) {
                            this.destinationX = ballShadows.get(0).getDestinationX();
                            this.destinationY = ballShadows.get(0).getDestinationY();
                        } else if (Main.frames % 1000 < 150) {
                            this.destinationX = ballShadows.get(0).getDestinationX() + (GamePanel.WINW * 0.1);
                            this.destinationY = ballShadows.get(0).getDestinationY() + (GamePanel.WINH * 0.1); 
                        } else if (Main.frames % 1000 < 250) {
                            this.destinationX = ballShadows.get(0).getDestinationX();
                            this.destinationY = ballShadows.get(0).getDestinationY();
                        } else if (Main.frames % 1000 < 275) {
                            this.destinationX = ballShadows.get(0).getDestinationX() - (GamePanel.WINW * 0.1);
                            this.destinationY = ballShadows.get(0).getDestinationY();
                        } else if (Main.frames % 1000 < 375) {
                            this.destinationX = ballShadows.get(0).getDestinationX();
                            this.destinationY = ballShadows.get(0).getDestinationY();
                        } else if (Main.frames % 1000 < 400) {
                            this.destinationX = ballShadows.get(0).getDestinationX() + (GamePanel.WINW * 0.1);
                            this.destinationY = ballShadows.get(0).getDestinationY() - (GamePanel.WINH * 0.1);
                        } else if (Main.frames %  1000 < 500) {
                            this.destinationX = ballShadows.get(0).getDestinationX();
                            this.destinationY = ballShadows.get(0).getDestinationY();
                        } else if (Main.frames %  1000 < 525) {
                            this.destinationX = ballShadows.get(0).getDestinationX();
                            this.destinationY = ballShadows.get(0).getDestinationY() + (GamePanel.WINH * 0.1);
                        } else if (Main.frames %  1000 < 625) {
                            this.destinationX = ballShadows.get(0).getDestinationX();
                            this.destinationY = ballShadows.get(0).getDestinationY();
                        } else if (Main.frames %  1000 < 650) {
                            this.destinationX = ballShadows.get(0).getDestinationX() - (GamePanel.WINW * 0.1);
                            this.destinationY = ballShadows.get(0).getDestinationY() + (GamePanel.WINH * 0.1);
                        } else if (Main.frames %  1000 < 750) {
                            this.destinationX = ballShadows.get(0).getDestinationX();
                            this.destinationY = ballShadows.get(0).getDestinationY();
                        } else if (Main.frames %  1000 < 775) {
                            this.destinationX = ballShadows.get(0).getDestinationX() - (GamePanel.WINW * 0.1);
                            this.destinationY = ballShadows.get(0).getDestinationY() - (GamePanel.WINH * 0.1);
                        } else if (Main.frames %  1000 < 875) {
                            this.destinationX = ballShadows.get(0).getDestinationX();
                            this.destinationY = ballShadows.get(0).getDestinationY();
                        } else if (Main.frames %  1000 < 900) {
                            this.destinationX = ballShadows.get(0).getDestinationX() + (GamePanel.WINW * 0.1);
                            this.destinationY = ballShadows.get(0).getDestinationY();
                        } else {
                            this.destinationX = ballShadows.get(0).getDestinationX();
                            this.destinationY = ballShadows.get(0).getDestinationY();
                        }
                    } else {
                        this.destinationX = this.idleX;
                        this.destinationY = this.idleY;
                    }
                }
            } else { //ball is moving away from enemy, enemy must move towards idle position
                this.destinationX = this.idleX;
                this.destinationY = this.idleY;
            }
        } else { //multiple balls in play, must decide which to go for
            if (ballShadows.get(0).getDestinationY() > 300 && ballShadows.get(1).getDestinationY() > 300) { //both balls are moving away from Walter
                this.destinationX = this.idleX;
                this.destinationY = this.idleY;
            } else if (ballShadows.get(0).getDestinationY() < 300 && ballShadows.get(1).getDestinationY() < 300) { //both balls are moving towards enemy, must choose one to go for
                if (ballShadows.get(0).yy < ballShadows.get(1).yy) { //ballShadows.get(0) is closer to leaking
                    this.destinationX = ballShadows.get(0).getDestinationX();
                    this.destinationY = ballShadows.get(0).getDestinationY();
                } else { //ballShadows.get(1) must be closer to leaking then
                    this.destinationX = ballShadows.get(1).getDestinationX();
                    this.destinationY = ballShadows.get(1).getDestinationY();
                }
            } else { //only one ball is moving towardds enemy
                for (BallShadow b: ballShadows) {
                    if (b.getDestinationY() < 300) {
                        this.destinationX = b.getDestinationX();
                        this.destinationY = b.getDestinationY();
                    }
                }
            }
        }
        
        double vx, vy, theta;
        double centerX = this.xx + (this.size / 2);
        double centerY = this.yy + (this.size / 2);
        /*
         * sets the theta
         * 0 = up
         * 90 = right
         * 180 = down
         * 270 = left
         * -1 = no direction (ball is already at the right position)
         */
        if (centerY < this.destinationY) {
            if (centerX < this.destinationX) {
                theta = 90 + Math.toDegrees(Math.atan(Math.abs((centerY - this.destinationY)/(centerX - this.destinationX))));
            } else if (this.xx > this.destinationX) {
                theta = 270 - Math.toDegrees(Math.atan(Math.abs((centerY - this.destinationY)/(centerX - this.destinationX))));
            } else {
                theta = 180;
            }
        } else if (centerY > this.destinationY) {
            if (centerX < this.destinationX) {
                theta = 90 - Math.toDegrees(Math.atan(Math.abs((centerY - this.destinationY)/(centerX - this.destinationX))));
            } else if (centerX > this.destinationX) {
                theta = 270 + Math.toDegrees(Math.atan(Math.abs((centerY - this.destinationY)/(centerX - this.destinationX))));
            } else {
                theta = 0;
            }
        } else {
            if (centerX < this.destinationX) {
                theta = 90;
            } else if (centerX > this.destinationX) {
                theta = 270;
            } else {
                theta = -1;
            }
        }
        
        //checks if the enemy will have to move less than 1 frame to get the the destination
        double distanceToDestination = Math.sqrt(Math.pow(Math.abs(centerX - this.destinationX), 2) + Math.pow(Math.abs(centerY - this.destinationY), 2));
        if (distanceToDestination < this.velocity) {
            this.xx = this.destinationX - (this.size / 2);
            this.yy = this.destinationY - (this.size / 2);
        } else {
            if (theta == 0) {
                vx = 0;
                vy = this.velocity * -1;
            } else if (theta < 90) {
                vx = Math.sin(Math.toRadians(theta)) * this.velocity;
                vy = Math.cos(Math.toRadians(theta)) * this.velocity * -1;
            } else if (theta == 90) {
                vx = this.velocity;
                vy = 0;
            } else if (theta < 180) {
                vx = Math.sin(Math.toRadians(180 - theta)) * this.velocity;
                vy = Math.cos(Math.toRadians(180 - theta)) * this.velocity;
            } else if (theta == 180) {
                vx = 0;
                vy = this.velocity;
            } else if (theta < 270) {
                vx = Math.sin(Math.toRadians(180 + theta)) * this.velocity * -1;
                vy = Math.cos(Math.toRadians(180 + theta)) * this.velocity;
            } else if (theta == 270) {
                vx = this.velocity * -1;
                vy = 0;
            } else if (theta < 360) {
                vx = Math.sin(Math.toRadians(360 - theta)) * this.velocity * -1;
                vy = Math.cos(Math.toRadians(360 - theta)) * this.velocity * -1;
            } else {
                vx = 0;
                vy = 0;
            }
            this.xx += vx;
            this.yy += vy;    
        }
    }//end move

    /**
     * Hits the ball if the ball is close enough to the enemy
     * @param timeSlowed   Whether or not the time is slowed down in game as a result of Tasha's ability
     * @param enemyLosing  Whether or not the enemy is losing
     */
    public void hit(boolean timeSlowed, boolean enemyLosing) {
        for (BallShadow b: ballShadows) {
            Rectangle ball = new Rectangle((int)b.xx, (int)b.yy, b.size, b.size);
            Rectangle enemy = new Rectangle((int)this.xx, (int)this.yy, this.size, this.size);
            if (b.getPlayerHitLast() && enemy.intersects(ball)) {
                //Updated the velocity for the ball to be returned.
                if (this.enemyType != enemyTypes.StrongHercules) {
                    if (!timeSlowed) b.velocity = Main.BALL_SPEED;
                    else b.velocity = Main.BALL_SPEED / 2;
                } else {
                    if (!timeSlowed) b.velocity = Main.BALL_SPEED * 1.5;
                    else b.velocity = Main.BALL_SPEED * 0.75;
                }
                b.setDestination((int)(((Math.random() * 400.0) + 312.0) / 1024.0 * GamePanel.WINW), (int)(GamePanel.WINH * 0.73));
                //Set hitLast to true.
                b.setPlayerHitLast(false);
                //System.out.println("Enemy hit the ball!");

                //checks if walter needs to throw out a second ball
                if (this.enemyType == enemyTypes.TwoBallWalter && enemyLosing && !ballShadows.get(1).getActive()) {
                    ballShadows.get(1).setActive(true);
                    ballShadows.get(1).xx = this.xx;
                    ballShadows.get(1).yy = this.yy;
                    if (!timeSlowed) ballShadows.get(1).velocity = Main.BALL_SPEED;
                    else ballShadows.get(1).velocity = Main.BALL_SPEED / 2;
                    ballShadows.get(1).setDestination((int)(((Math.random() * 400.0) + 312.0) / 1024.0 * GamePanel.WINW), (int)(GamePanel.WINH * 0.73));
                    ballShadows.get(1).setDeparture(this.xx, this.yy);
                    ballShadows.get(1).setPlayerHitLast(false);
                }

                //checks if Sicilia needs to do something cool
                if (this.enemyType == enemyTypes.TeleportSicilia) {
                    int attackType = (int)(Math.random() * 5);
                    switch (attackType) {
                        case 1, 2 -> {
                            b.setSpin(true);
                        }
                        case 3, 4 -> {
                            b.velocity *= 1.5;
                        }
                        case 5 -> {}
                        default -> {}
                    }
                }
            }
        }
    }//end hit
}