/***
 * Entity.java
 * The abstract class Entity is the base for all moving entities in the BallPickle game.
 * by: David Sue, Vadim Mironov, Avishan Ketheswaran and Owen McCarthy
 * January 14, 2024
 */
public abstract class Entity {
    int x, y, size;
    double xx, yy, velocity;

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
}
