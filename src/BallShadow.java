/***
 * Ball.java
 * The ball of the BallPickle game. It moves around in linear directions and bounces off of the enemy and the player.
 * by: David Sue, Vadim Mironov, Avishan Ketheswaran and Owen McCarthy
 * December 2, 2024
 */
public class BallShadow extends Entity {

    double destinationX, destinationY, departureX, departureY, theta;

   /**
    * Constructor
    * @param xx: The specific x position of the ball
    * @param yy: The specific y position of the ball
    * @param s: The size of the ball
    * @param v: The velocity of the ball
    * @param t: The direction of velocity vector
    */
    public BallShadow(double xx, double yy, double v, int s) {
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

    public void setDeparture(double dX, double dY) {
        this.departureX = dX;
        this.departureY = dY;
    }
    
    public void setDestination(double dX, double dY) {
        //the distance from the BallShadow's departure to it's destination
        double distance = Math.sqrt(Math.pow(Math.abs(this.departureX - this.destinationX), 2) + Math.pow(Math.abs(this.departureY - this.destinationY), 2));
        //the distance between the BallShadow's current position and it's departure point
        double progress = Math.sqrt(Math.pow(Math.abs(this.departureX - this.xx), 2) + Math.pow(Math.abs(this.departureY - this.yy), 2));
        //the distance traveled by the ball, expressed as a percentage
        double progressPercent = progress / distance * 100;
        
        if (progress < distance) {
            if (dX < this.xx) {
                this.departureX = this.xx + ((this.xx - dX) / progressPercent);
            } else if (dX > this.xx) {
                this.departureX = this.xx - ((dX - this.xx) / progressPercent);
            } else {
                this.departureX = this.xx;
            }
    
            if (dY < this.yy) {
                this.departureY = this.yy + ((this.yy - dY) / progressPercent);
            } else if (dY > this.yy) {
                this.departureY = this.yy - ((dY - this.yy) / progressPercent);
            } else {
                this.departureY = this.yy;
            }
        } else {
            if (dX < this.xx) {
                this.departureX = this.xx + ((this.xx - dX) / (progressPercent / 100));
            } else if (dX > this.xx) {
                this.departureX = this.xx - ((dX - this.xx) / (progressPercent / 100));
            } else {
                this.departureX = this.xx;
            }
    
            if (dY < this.yy) {
                this.departureY = this.yy + ((this.yy - dY) / (progressPercent / 100));
            } else if (dY > this.yy) {
                this.departureY = this.yy - ((dY - this.yy) / (progressPercent / 100));
            } else {
                this.departureY = this.yy;
            }
        }
        
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
