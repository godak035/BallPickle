
public class Ball extends Entity {

    int size;
    double theta; //the direction of the velocity vector

    /**
    * A concise description of what the method does.
    * double xx, double yy, double v, int s
    */

    public Ball(double xx, double yy, double v, double t, int s) {
        super(xx, yy, v);
        this.theta = t;
        this.size = s;
    }

    @Override
    void updatePosition() {
        double vx, vy; //x and y components of velocity
        vx = Math.cos(Math.toRadians(theta)) * velocity;
        vy = Math.sin(Math.toRadians(theta)) * velocity;
        xx += vx;
        yy += vy;
        this.x = (int)xx;
        this.y = (int)yy;
    }
    
}
