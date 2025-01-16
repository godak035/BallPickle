/***
 * BallShadow.java
 * The ball's shadow of the BallPickle game. It moves around in linear directions and can be hit by the enemy and the player.
 * by: David Sue, Vadim Mironov, Avishan Ketheswaran and Owen McCarthy
 * January 15, 2025
 */
public class BallShadow extends Entity {

    private double destinationX, destinationY, departureX, departureY, theta;
    private boolean isActive, playerHitLast, doSpin;

   /**
    * Constructor
    * @param xx: The specific x position of the ball
    * @param yy: The specific y position of the ball
    * @param s: The size of the ball
    * @param v: The velocity of the ball
    * @param t: The direction of velocity vector
    */
    BallShadow(double xx, double yy, double v, int s) {
        super(xx, yy, v, s);
        this.isActive = true;
        this.playerHitLast = false;
        this.doSpin = false;
    }

    /**
     * Updates the x and y position to align with the xx and yy
     */
    @Override
    public void updatePosition() {
        this.setX((int)this.getXX());
        this.setY((int)this.getYY());
    }

    //getter methods
    public boolean getActive() { return this.isActive; }
    public boolean getPlayerHitLast() { return this.playerHitLast; }
    public boolean getSpin() { return this.doSpin; }
    public double getDepartureX() { return this.departureX; }
    public double getDepartureY() { return this.departureY; }
    public double getDestinationX() { return this.destinationX; }
    public double getDestinationY() { return this.destinationY; }
    
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
        double progress = Math.sqrt(Math.pow(Math.abs(this.departureX - this.getXX()), 2) + Math.pow(Math.abs(this.departureY - this.getYY()), 2));
        
        //the distance traveled by the ball, expressed as a percentage
        double progressPercent = progress / distance * 100;
        
        //sets the departure x and y
        if (progressPercent > 98) { //if the BallShadow is pretty much at it's destination, the new destination will be at its current position. This was added to patch a bug that ocurred where the ball would teleport upwards when hit near its destination point
            this.departureX = this.getXX();
            this.departureY = this.getYY();
        } else {
            if (progress < distance) {
                if (dX < this.getXX()) {
                    this.departureX = this.getXX() + ((this.getXX() - dX) / progressPercent);
                } else if (dX > this.getXX()) {
                    this.departureX = this.getXX() - ((dX - this.getXX()) / progressPercent);
                } else {
                    this.departureX = this.getXX();
                }
                if (dY < this.getYY()) {
                    this.departureY = this.getYY() + ((this.getYY() - dY) / progressPercent);
                } else if (dY > this.getYY()) {
                    this.departureY = this.getYY() - ((dY - this.getYY()) / progressPercent);
                } else {
                    this.departureY = this.getYY();
                }
            } else {
                if (dX < this.getXX()) {
                    this.departureX = this.getXX() + ((this.getXX() - dX) / (progressPercent / 100));
                } else if (dX > this.getXX()) {
                    this.departureX = this.getXX() - ((dX - this.getXX()) / (progressPercent / 100));
                } else {
                    this.departureX = this.getXX();
                }
                if (dY < this.getYY()) {
                    this.departureY = this.getYY() + ((this.getYY() - dY) / (progressPercent / 100));
                } else if (dY > this.getYY()) {
                    this.departureY = this.getYY() - ((dY - this.getYY()) / (progressPercent / 100));
                } else {
                    this.departureY = this.getYY();
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
        if (this.getYY() < dY) {
            if (this.getXX() < dX) {
                this.theta = 90 + Math.toDegrees(Math.atan(Math.abs((this.getYY() - dY)/(this.getXX() - dX))));
            } else if (this.getXX() > dX) {
                this.theta = 270 - Math.toDegrees(Math.atan(Math.abs((this.getYY() - dY)/(this.getXX() - dX))));
            } else {
                this.theta = 180;
            }
        } else if (this.getYY() > dY) {
            if (this.getXX() < dX) {
                this.theta = 90 - Math.toDegrees(Math.atan(Math.abs((this.getYY() - dY)/(this.getXX() - dX))));
            } else if (this.getXX() > dX) {
                this.theta = 270 + Math.toDegrees(Math.atan(Math.abs((this.getYY() - dY)/(this.getXX() - dX))));
            } else {
                this.theta = 0;
            }
        } else {
            if (this.getXX() < dX) {
                this.theta = 270;
            } else if (this.getXX() > dX) {
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
        if (this.getYY() > this.destinationY && this.getYY() > this.departureY && doSpin) {
            setDestination((int)((GamePanel.WINW * 0.2) + (GamePanel.WINW * 0.4)), (int)(GamePanel.WINH * 0.95));
            setDeparture(this.getXX(), this.getYY());
            doSpin = false;
        }
        double vx, vy;
        if (this.theta == 0) {
            vx = 0;
            vy = this.getVelocity() * -1;
        } else if (this.theta < 90) {
            vx = Math.sin(Math.toRadians(this.theta)) * this.getVelocity();
            vy = Math.cos(Math.toRadians(this.theta)) * this.getVelocity() * -1;
        } else if (this.theta == 90) {
            vx = this.getVelocity();
            vy = 0;
        } else if (this.theta < 180) {
            vx = Math.sin(Math.toRadians(180 - this.theta)) * this.getVelocity();
            vy = Math.cos(Math.toRadians(180 - this.theta)) * this.getVelocity();
        } else if (this.theta == 180) {
            vx = 0;
            vy = this.getVelocity();
        } else if (this.theta < 270) {
            vx = Math.sin(Math.toRadians(180 + this.theta)) * this.getVelocity() * -1;
            vy = Math.cos(Math.toRadians(180 + this.theta)) * this.getVelocity();
        } else if (this.theta == 270) {
            vx = this.getVelocity() * -1;
            vy = 0;
        } else if (this.theta < 360) {
            vx = Math.sin(Math.toRadians(360 - this.theta)) * this.getVelocity() * -1;
            vy = Math.cos(Math.toRadians(360 - this.theta)) * this.getVelocity() * -1;
        } else {
            vx = 0;
            vy = 0;
        }
        this.setXX(this.getXX() + vx);
        this.setYY(this.getYY() + vy);
    }
}