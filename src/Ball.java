/***
 * Ball.java
 * The ball of the BallPickle game. It moves around in linear directions and bounces off of the enemy and the player.
 * by: David Sue, Vadim Mironov, Avishan Ketheswaran and Owen McCarthy
 * December 2, 2024
 */
public class Ball extends Entity {

    double destinationX, destinationY, theta;

   /**
    * Constructor
    * @param xx: The specific x position of the ball
    * @param yy: The specific y position of the ball
    * @param s: The size of the ball
    * @param v: The velocity of the ball
    * @param t: The direction of velocity vector
    */
    public Ball(double xx, double yy, int s, double v) {
        super(xx, yy, v, s);
    }

    /**
     * Updates the x and y variables of the ball to align with the xx and yy
     */
    @Override
    public void updatePosition() {
        this.x = (int)xx;
        this.y = (int)yy;
    }
    
    public void setDestination(double dX, double dY) {
        this.destinationX = dX;
        this.destinationY = dY;

        /*
         * sets the theta
         * 0 = up
         * 90 = right
         * 180 = down
         * 270 = left
         * -1 = no direction (ball is already at the right position)
         */
        if (this.yy < dY) {
            if (this.xx < dX) {
                this.theta = 90 + Math.toDegrees(Math.atan(Math.abs((this.yy - dY)/(this.xx - dX))));
            } else if (this.xx > dX) {
                this.theta = 270 - Math.toDegrees(Math.atan(Math.abs((this.yy - dY)/(this.xx - dX))));
            } else {
                this.theta = 180;
            }
        } else if (this.yy > dY) {
            if (this.xx < dX) {
                this.theta = 90 - Math.toDegrees(Math.atan(Math.abs((this.yy - dY)/(this.xx - dX))));
            } else if (this.xx > dX) {
                this.theta = 270 + Math.toDegrees(Math.atan(Math.abs((this.yy - dY)/(this.xx - dX))));
            } else {
                this.theta = 0;
            }
        } else {
            if (this.xx < dX) {
                this.theta = 270;
            } else if (this.xx > dX) {
                this.theta = 90;
            } else {
                this.theta = -1;
            }
        }
    }

    public void move() {
        double vx, vy;
        if (this.theta == 0) {
            vx = 0;
            vy = this.velocity * -1;
        } else if (this.theta < 90) {
            vx = Math.sin(Math.toRadians(this.theta)) * this.velocity;
            vy = Math.cos(Math.toRadians(this.theta)) * this.velocity * -1;
        } else if (this.theta == 90) {
            vx = this.velocity;
            vy = 0;
        } else if (this.theta < 180) {
            vx = Math.sin(Math.toRadians(180 - this.theta)) * this.velocity;
            vy = Math.cos(Math.toRadians(180 - this.theta)) * this.velocity;
        } else if (this.theta == 180) {
            vx = 0;
            vy = this.velocity;
        } else if (this.theta < 270) {
            vx = Math.sin(Math.toRadians(180 + this.theta)) * this.velocity * -1;
            vy = Math.cos(Math.toRadians(180 + this.theta)) * this.velocity;
        } else if (this.theta == 270) {
            vx = this.velocity * -1;
            vy = 0;
        } else if (this.theta < 360) {
            vx = Math.sin(Math.toRadians(360 - this.theta)) * this.velocity * -1;
            vy = Math.cos(Math.toRadians(360 - this.theta)) * this.velocity * -1;
        } else {
            vx = 0;
            vy = 0;
        }
        this.xx += vx;
        this.yy += vy;
    }
}
