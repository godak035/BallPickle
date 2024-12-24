

/***
 * Enemy.java
 * The enemy of the BallPickle game. It moves around on it's side of the court in human-like patterns and hits the ball back to you.
 * by: David Sue, Vadim Mironov, Avishan Ketheswaran and Owen McCarthy
 * December 2, 2024
 */
public class Enemy extends Entity {
    
    int level;
    int score = 0;
    double destinationX, destinationY;
    double idleX, idleY;

   /**
    * Constructor
    * @param xx: The specific x position of the enemy
    * @param yy: The specific y position of the enemy
    * @param s: The size of the enemy
    * @param v: The velocity of the enemy
    * @param level: The level difficulty of the enemy
    */
    public Enemy(double xx, double yy, double v, int s, int level, double iX, double iY) {
        super(xx, yy, v, s);
        this.level = level;
        this.idleX = iX;
        this.idleY = iY;
    }

    /**
     * Updates the x and y variables of the enemy to align with the xx and yy
     */
    @Override
    public void updatePosition() {
        this.x = (int) xx;
        this.y = (int) yy;
    }

    public void setDestination(double dX, double dY) {
        this.destinationX = dX;
        this.destinationY = dY;
    }

    public void move(BallShadow b) {
        if (b.destinationY < 300) { //ball is moving towards enemy, enemy must move towards ball's destination
            this.destinationX = b.destinationX;
            this.destinationY = b.destinationY;
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
    }
}
