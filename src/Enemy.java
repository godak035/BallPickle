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
                this.idleX = 512;
                this.idleY = 250;
                }
            case GradyTwin1 -> {
                this.idleX = 412;
                this.idleY = 250;
                }
            case GradyTwin2 -> {
                this.idleX = 612;
                this.idleY = 250;
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

    public void move(BallShadow b) {
        if (b.destinationY < 300) { //ball is moving towards enemy, enemy must move towards ball's destination
            if (this.enemyType != enemyTypes.GradyTwin1 && this.enemyType != enemyTypes.GradyTwin2) {
                this.destinationX = b.destinationX;
                this.destinationY = b.destinationY;
            } else if (this.enemyType == enemyTypes.GradyTwin1) {
                if (b.destinationX < 512) {
                    this.destinationX = b.destinationX;
                    this.destinationY = b.destinationY;
                } else {
                    this.destinationX = this.idleX;
                    this.destinationY = this.idleY;
                }  
            } else if (this.enemyType == enemyTypes.GradyTwin2) {
                if (b.destinationX >= 512) {
                    this.destinationX = b.destinationX;
                    this.destinationY = b.destinationY;
                } else {
                    this.destinationX = this.idleX;
                    this.destinationY = this.idleY;
                }
            }
        } else { //ball is moving away from enemy, enemy must move towards idle position
            this.destinationX = this.idleX;
            this.destinationY = this.idleY;
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
         * -1 = no direction (ball is alreathis.destinationY at the right position)
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

    public void hit(boolean timeSlowed) {
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
                b.setDestination((Math.random() * 400) + 312, 500);
                //Set hitLast to true.
                b.setPlayerHitLast(false);
                //System.out.println("Enemy hit the ball!");S
            }
        }
    }//end hit
}
