public class Ball extends Entity {
    private int size; //Size fo the object

    Ball(double xx, double yy, double v, int size) {
        super(xx, yy, v);
        this.size = size;
        this.x = (int)xx;
        this.y = (int)yy;
    }

    public void move() {
        
    }

    public void reset() {
        
    }
}