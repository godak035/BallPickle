/***
 * Ball.java
 * A ball will follow a BallShadow and look like it has gravity
 * By: David Sue, Vadim Mironov, Avishan Ketheswaran and Owen McCarthy
 * January 15, 2025
 */
public class Ball extends Entity {

    private final BallShadow shadow;

    /**
     * Constructor
     * @param b  the BallShadow to be followed
     */
    Ball(BallShadow b) {
        super(b.getXX(), b.getYY(), 0, b.getSize());
        this.shadow = b;
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
    public BallShadow getShadow() { return this.shadow; }

    /**
     * Syncs the location of the ball to the location of the BallShadow
     */
    public void syncLocation() {
        //the distance from the BallShadow's departure to it's destination
        double distance = Math.sqrt(Math.pow(Math.abs(shadow.getDepartureX() - shadow.getDestinationX()), 2) + Math.pow(Math.abs(shadow.getDepartureY() - shadow.getDestinationY()), 2));
        //the distante between the BallShadow's current position and it's departure point
        double progress = Math.sqrt(Math.pow(Math.abs(shadow.getDepartureX() - shadow.getXX()), 2) + Math.pow(Math.abs(shadow.getDepartureY() - shadow.getYY()), 2));
        //the percent of it's journey the ball has traveled will determine its height
        double h = (progress / distance) * 100;
        if (h > 50) h = 100 - h;
        this.setXX(shadow.getXX());
        this.setYY(shadow.getYY() - Math.abs(h));
    }
}