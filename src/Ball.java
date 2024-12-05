/***
 * Ball.java
 * The ball of the BallPickle game. It moves around in linear directions and bounces off of the enemy and the player.
 * by: David Sue, Vadim Mironov, Avishan Ketheswaran and Owen McCarthy
 * December 2, 2024
 */
public class Ball extends Entity {

    public int size;
    double theta;

   /**
    * Constructor
    * @param xx: The specific x position of the ball
    * @param yy: The specific y position of the ball
    * @param s: The size of the ball
    * @param v: The velocity of the ball
    * @param t: The direction of velocity vector
    */
    public Ball(double xx, double yy, int s, double v) {
        super(xx, yy, v);
        this.size = s;
    }

    /**
     * Updates the x and y variables of the ball to align with the xx and yy
     */
    @Override
    public void updatePosition() {
        double vx, vy; //x and y components of velocity
        vx = Math.cos(Math.toRadians(theta)) * velocity;
        vy = Math.sin(Math.toRadians(theta)) * velocity;
        xx += vx;
        yy += vy;
        this.x = (int)xx;
        this.y = (int)yy;

        
        
    }
    public int checkPosition(){

        return this.y;
    }

    /**
     * Sets the theta angle of the ball
     * @param t: The new angle, in degrees, of the ball
     */
    public void setTheta(double t) {
        this.theta = t;
    }
    
}
