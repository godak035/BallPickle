/***
 * Enemy.java
 * The enemy of the BallPickle game. It moves around on it's side of the court in human-like patterns and hits the ball back to you.
 * by: David Sue, Vadim Mironov, Avishan Ketheswaran and Owen McCarthy
 * December 2, 2024
 */
public class Enemy extends Entity {
    
    int size, level;

   /**
    * Constructor
    * @param xx: The specific x position of the enemy
    * @param yy: The specific y position of the enemy
    * @param s: The size of the enemy
    * @param v: The velocity of the enemy
    * @param level: The level difficulty of the enemy
    */
    public Enemy(double xx, double yy, double v, int s, int level) {
        super(xx, yy, v);
        this.size = s;
        this.level = level;
    }

    /**
     * Updates the x and y variables of the enemy to align with the xx and yy
     */
    @Override
    public void updatePosition() {
        this.x = (int) xx;
        this.y = (int) yy;
    }
}
