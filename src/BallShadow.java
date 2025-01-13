/***
 * BallShadow.java
 * The ball's shadow of the BallPickle game. It moves around in linear directions and can be hit by the enemy and the player.
 * by: David Sue, Vadim Mironov, Avishan Ketheswaran and Owen McCarthy
 * January 14, 2025
 */
public class BallShadow extends Entity {

    double destinationX, destinationY, departureX, departureY, theta;
    private boolean isActive, playerHitLast, doSpin;

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
        this.isActive = true;
        this.playerHitLast = false;
        this.doSpin = false;
    }

    /**
     * Updates the x and y variables of the ball to align with the xx and yy
     */
    @Override
    public void updatePosition() {
        this.x = (int)xx;
        this.y = (int)yy;
    }

    //getter methods
    public boolean getActive() { return this.isActive; }
    public boolean getPlayerHitLast() { return this.playerHitLast; }
    public boolean getSpin() { return this.doSpin; }
    
    //setter methods
    public void setActive(boolean active) { this.isActive = active; }
    public void setPlayerHitLast(boolean PlayerHitLast) { this.playerHitLast = PlayerHitLast; }
    public void setSpin(boolean spin) { this.doSpin = spin; }
    public void setDeparture(double dX, double dY) {
        this.departureX = dX;
        this.departureY = dY;
    }
    
    /**
     * Sets the destination of the BallShadow, and calculates the theta angle that the ball will have to travel, and the deprature point
     * @param dX  The x component of the destination
     * @param dY  The y component of the destination
     */
    public void setDestination(double dX, double dY) {
        //the distance from the BallShadow's departure to it's destination
        double distance = Math.sqrt(Math.pow(Math.abs(this.departureX - this.destinationX), 2) + Math.pow(Math.abs(this.departureY - this.destinationY), 2));
        
        //the distance between the BallShadow's current position and it's departure point
        double progress = Math.sqrt(Math.pow(Math.abs(this.departureX - this.xx), 2) + Math.pow(Math.abs(this.departureY - this.yy), 2));
        
        //the distance traveled by the ball, expressed as a percentage
        double progressPercent = progress / distance * 100;
        
        //sets the departure x and y
        if (progressPercent > 98) { //if the BallShadow is pretty much at it's destination, the new destination will be at its current position. This was added to patch a bug that ocurred where the ball would teleport upwards when hit near its destination point
            this.departureX = this.xx;
            this.departureY = this.yy;
        } else {
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

    /**
     * Moves the ball along the theta angle
     */
    public void move() {
        if (this.yy > this.destinationY && this.yy > this.departureY && doSpin) {
            setDestination((int)((GamePanel.WINW * 0.2) + (GamePanel.WINW * 0.4)), (int)(GamePanel.WINH * 0.95));
            setDeparture(this.xx, this.yy);
            doSpin = false;
        }
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