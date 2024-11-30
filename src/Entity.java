import java.awt.*;

public abstract class Entity extends Rectangle {
    int x, y;
    double xx, yy, velocity;

    /**
    * A concise description of what the method does.
    * double xx, double yy, double v
    */

    Entity(double xx, double yy, double v) {
        this.xx = xx;
        this.yy = yy;
        this.velocity = v;
        this.x = (int)xx;
        this.y = (int)yy;
    }

    public abstract void updatePosition();
}
