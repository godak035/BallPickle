import java.awt.Rectangle;
import java.util.ArrayList;

/***
 * Enemy.java
 * The enemy of the BallPickle game. It moves around on it's side of the court in human-like patterns and hits the ball back to you.
 * by: David Sue, Vadim Mironov, Avishan Ketheswaran and Owen McCarthy
 * December 2, 2024
 */
public class Enemy extends Entity {
    
    public static enum enemyTypes { AverageJoe, StrongHercules, GradyTwin1, GradyTwin2, TwoBallWalter, TeleportSicilia }
    enemyTypes enemyType;
    double destinationX, destinationY;
    double idleX, idleY;
    boolean isActive;
    ArrayList<BallShadow> ballShadows;

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

    public void setActive(boolean activity) { this.isActive = activity; }
    public void updateValues(ArrayList<BallShadow> bs) { this.ballShadows = bs; }

    public void setDestination(double dX, double dY) {
        this.destinationX = dX;
        this.destinationY = dY;
    }

    public void move(ArrayList<BallShadow> bs) {
        if (!bs.get(1).getActive()) { //only one ball in play
            if (bs.get(0).destinationY < 300) { //ball is moving towards enemy, enemy must move towards ball's destination
                if (this.enemyType != enemyTypes.GradyTwin1 && this.enemyType != enemyTypes.GradyTwin2) {
                    this.destinationX = bs.get(0).destinationX;
                    this.destinationY = bs.get(0).destinationY;
                } else if (this.enemyType == enemyTypes.GradyTwin1) {
                    if (bs.get(0).destinationX < (GamePanel.WINW / 2.0)) {
                        if (Main.frames % 1000 < 25) {
                            this.destinationX = bs.get(0).destinationX;
                            this.destinationY = bs.get(0).destinationY - (GamePanel.WINH * 0.1);
                        } else if (Main.frames % 1000 < 125) {
                            this.destinationX = bs.get(0).destinationX;
                            this.destinationY = bs.get(0).destinationY;
                        } else if (Main.frames % 1000 < 150) {
                            this.destinationX = bs.get(0).destinationX + (GamePanel.WINW * 0.1);
                            this.destinationY = bs.get(0).destinationY + (GamePanel.WINH * 0.1); 
                        } else if (Main.frames % 1000 < 250) {
                            this.destinationX = bs.get(0).destinationX;
                            this.destinationY = bs.get(0).destinationY;
                        } else if (Main.frames % 1000 < 275) {
                            this.destinationX = bs.get(0).destinationX - (GamePanel.WINW * 0.1);
                            this.destinationY = bs.get(0).destinationY;
                        } else if (Main.frames % 1000 < 375) {
                            this.destinationX = bs.get(0).destinationX;
                            this.destinationY = bs.get(0).destinationY;
                        } else if (Main.frames % 1000 < 400) {
                            this.destinationX = bs.get(0).destinationX + (GamePanel.WINW * 0.1);
                            this.destinationY = bs.get(0).destinationY - (GamePanel.WINH * 0.1);
                        } else if (Main.frames %  1000 < 500) {
                            this.destinationX = bs.get(0).destinationX;
                            this.destinationY = bs.get(0).destinationY;
                        } else if (Main.frames %  1000 < 525) {
                            this.destinationX = bs.get(0).destinationX;
                            this.destinationY = bs.get(0).destinationY + (GamePanel.WINH * 0.1);
                        } else if (Main.frames %  1000 < 625) {
                            this.destinationX = bs.get(0).destinationX;
                            this.destinationY = bs.get(0).destinationY;
                        } else if (Main.frames %  1000 < 650) {
                            this.destinationX = bs.get(0).destinationX - (GamePanel.WINW * 0.1);
                            this.destinationY = bs.get(0).destinationY + (GamePanel.WINH * 0.1);
                        } else if (Main.frames %  1000 < 750) {
                            this.destinationX = bs.get(0).destinationX;
                            this.destinationY = bs.get(0).destinationY;
                        } else if (Main.frames %  1000 < 775) {
                            this.destinationX = bs.get(0).destinationX - (GamePanel.WINW * 0.1);
                            this.destinationY = bs.get(0).destinationY - (GamePanel.WINH * 0.1);
                        } else if (Main.frames %  1000 < 875) {
                            this.destinationX = bs.get(0).destinationX;
                            this.destinationY = bs.get(0).destinationY;
                        } else if (Main.frames %  1000 < 900) {
                            this.destinationX = bs.get(0).destinationX + (GamePanel.WINW * 0.1);
                            this.destinationY = bs.get(0).destinationY;
                        } else {
                            this.destinationX = bs.get(0).destinationX;
                            this.destinationY = bs.get(0).destinationY;
                        }
                    } else {
                        this.destinationX = this.idleX;
                        this.destinationY = this.idleY;
                    }  
                } else if (this.enemyType == enemyTypes.GradyTwin2) {
                    if (bs.get(0).destinationX >= (GamePanel.WINW / 2.0)) {
                        if (Main.frames % 1000 < 25) {
                            this.destinationX = bs.get(0).destinationX;
                            this.destinationY = bs.get(0).destinationY - (GamePanel.WINH * 0.1);
                        } else if (Main.frames % 1000 < 125) {
                            this.destinationX = bs.get(0).destinationX;
                            this.destinationY = bs.get(0).destinationY;
                        } else if (Main.frames % 1000 < 150) {
                            this.destinationX = bs.get(0).destinationX + (GamePanel.WINW * 0.1);
                            this.destinationY = bs.get(0).destinationY + (GamePanel.WINH * 0.1); 
                        } else if (Main.frames % 1000 < 250) {
                            this.destinationX = bs.get(0).destinationX;
                            this.destinationY = bs.get(0).destinationY;
                        } else if (Main.frames % 1000 < 275) {
                            this.destinationX = bs.get(0).destinationX - (GamePanel.WINW * 0.1);
                            this.destinationY = bs.get(0).destinationY;
                        } else if (Main.frames % 1000 < 375) {
                            this.destinationX = bs.get(0).destinationX;
                            this.destinationY = bs.get(0).destinationY;
                        } else if (Main.frames % 1000 < 400) {
                            this.destinationX = bs.get(0).destinationX + (GamePanel.WINW * 0.1);
                            this.destinationY = bs.get(0).destinationY - (GamePanel.WINH * 0.1);
                        } else if (Main.frames %  1000 < 500) {
                            this.destinationX = bs.get(0).destinationX;
                            this.destinationY = bs.get(0).destinationY;
                        } else if (Main.frames %  1000 < 525) {
                            this.destinationX = bs.get(0).destinationX;
                            this.destinationY = bs.get(0).destinationY + (GamePanel.WINH * 0.1);
                        } else if (Main.frames %  1000 < 625) {
                            this.destinationX = bs.get(0).destinationX;
                            this.destinationY = bs.get(0).destinationY;
                        } else if (Main.frames %  1000 < 650) {
                            this.destinationX = bs.get(0).destinationX - (GamePanel.WINW * 0.1);
                            this.destinationY = bs.get(0).destinationY + (GamePanel.WINH * 0.1);
                        } else if (Main.frames %  1000 < 750) {
                            this.destinationX = bs.get(0).destinationX;
                            this.destinationY = bs.get(0).destinationY;
                        } else if (Main.frames %  1000 < 775) {
                            this.destinationX = bs.get(0).destinationX - (GamePanel.WINW * 0.1);
                            this.destinationY = bs.get(0).destinationY - (GamePanel.WINH * 0.1);
                        } else if (Main.frames %  1000 < 875) {
                            this.destinationX = bs.get(0).destinationX;
                            this.destinationY = bs.get(0).destinationY;
                        } else if (Main.frames %  1000 < 900) {
                            this.destinationX = bs.get(0).destinationX + (GamePanel.WINW * 0.1);
                            this.destinationY = bs.get(0).destinationY;
                        } else {
                            this.destinationX = bs.get(0).destinationX;
                            this.destinationY = bs.get(0).destinationY;
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
            if (ballShadows.get(0).destinationY > 300 && ballShadows.get(1).destinationY > 300) { //both balls are moving away from Walter
                this.destinationX = this.idleX;
                this.destinationY = this.idleY;
            } else if (ballShadows.get(0).destinationY < 300 && ballShadows.get(1).destinationY < 300) { //both balls are moving towards enemy, must choose one to go for
                if (ballShadows.get(0).yy < ballShadows.get(1).yy) { //ballShadows.get(0) is closer to leaking
                    this.destinationX = ballShadows.get(0).destinationX;
                    this.destinationY = ballShadows.get(0).destinationY;
                } else { //ballShadows.get(1) must be closer to leaking then
                    this.destinationX = ballShadows.get(1).destinationX;
                    this.destinationY = ballShadows.get(1).destinationY;
                }
            } else { //only one ball is moving towardds enemy
                for (BallShadow b: ballShadows) {
                    if (b.destinationY < 300) {
                        this.destinationX = b.destinationX;
                        this.destinationY = b.destinationY;
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

    public void hit(boolean timeSlowed, boolean enemyLosing) {
        for (BallShadow b: ballShadows) {
            Rectangle ball = new Rectangle((int)b.xx, (int)b.yy, b.size, b.size);
            Rectangle enemy = new Rectangle((int)this.xx, (int)this.yy, this.size, this.size);
            if (b.getPlayerHitLast() && enemy.intersects(ball)) {
                //Updated the velocity for the ball to be returned.
                if (this.enemyType != enemyTypes.StrongHercules) {
                    if (!timeSlowed) b.velocity = 4;
                    else b.velocity = 2;
                } else {
                    if (!timeSlowed) b.velocity = 6;
                    else b.velocity = 3;
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
                    if (!timeSlowed) ballShadows.get(1).velocity = 4;
                    else ballShadows.get(1).velocity = 2;
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
