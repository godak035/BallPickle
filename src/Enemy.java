public class Enemy extends Entity {
     int size;
     int level;


    /**
    * 
    * @param xx
    * @param yy
    * @param v
    * @param s
    */
    public Enemy(double xx, double yy, double v, int s, int level) {
        super(xx, yy, v);
        this.size = s;
        this.level = level;
    }


    @Override
    public void updatePosition() {
        this.x = (int) xx;
        this.y = (int) yy;
    }
}
