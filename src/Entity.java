/***
 * Entity.java
 * The abstract class Entity is the base for all moving entities in the BallPickle game.
 * by: David Sue, Vadim Mironov, Avishan Ketheswaran and Owen McCarthy
 * January 15, 2024
 */
public abstract class Entity {
    private int x, y, size;
    private double xx, yy, velocity;

    /**
     * Constructor
     * @param xx: The precise x position of the entity
     * @param yy: The precise y position of the entity
     * @param v: The velocity of the entity
     */
    Entity(double xx, double yy, double v, int s) {
        this.xx = xx;
        this.yy = yy;
        this.velocity = v / 1024.0 * GamePanel.WINW;
        this.size = s;
        this.x = (int)xx;
        this.y = (int)yy;
    }

    /**
     * Updates the x and y value of the entity to align with the xx and yy
     */
    public abstract void updatePosition();

    //Setter methods
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    public void setSize(int s) { this.size = s; }
    public void setXX(double xx) { this.xx = xx; }
    public void setYY(double yy) { this.yy = yy; }
    public void setVelocity(double v) { this.velocity = v; }

    //Getter methods
    public int getX() { return this.x; }
    public int getY() { return this.y; }
    public int getSize() { return this.size; }
    public double getXX() { return this.xx; }
    public double getYY() { return this.yy; }
    public double getVelocity() { return this.velocity; }
}