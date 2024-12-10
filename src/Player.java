/***
 * Player.java
 * The player of the BallPickle game must move around according to the user's inputs and interact with the court and the ball.
 * by: David Sue, Vadim Mironov, Avishan Ketheswaran and Owen McCarthy
 * December 2, 2024
 */

public class Player extends Entity{

    int size;
    int score = 0;

    /**
     * Constructor
     * @param xx: The precise x position of the player
     * @param yy: The precise y position of the player
     * @param v: The velocity of the player
     * @param s: the size of the player
     */
    public Player(double xx, double yy, double v, int s) {
        super(xx, yy, v);
        this.size = s;
    }

    @Override
    public void updatePosition() {
        this.x = (int)xx;
        this.y = (int)yy;
    }

    public void dash(int paces) {
        this.xx += paces; // Update player's position by the specified number of paces
        System.out.println("Player dashed to position: " + this.xx);
    }

}