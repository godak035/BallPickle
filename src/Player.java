public class Player extends Entity{

    int size;

    /**
    * A concise description of what the method does.
    * double xx, double yy, double v, int s, KeyHandler, keyH
    */
    public Player(double xx, double yy, double v, int s) {
        super(xx, yy, v);
        this.size = s;
    }

    @Override
    void updatePosition() {
        this.x = (int)xx;
        this.y = (int)yy;
    }

}