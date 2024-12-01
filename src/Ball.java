
public class Ball extends Entity {

    public int size;
    double theta;

   /**
    * constructor
    * @param xx specific x position of the ball
    * @param yy specific y position of the ball
    * @param s  size of the ball
    * @param v  velocity of the ball
    * @param t  direction of velocity vector
    */
    public Ball(double xx, double yy, int s, double v) {
        super(xx, yy, v);
        this.size = s;
    }

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

    public void setTheta(double t) {
        this.theta = t;
    }
    
}
