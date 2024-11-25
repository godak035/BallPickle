import java.awt.*;

public class Entity extends Rectangle {
    int x, y; //pixel position of entity
    double xx, yy; //precise position of entity
    double velocity; //speed
    
    Entity(double xx, double yy, double v) {
        this.xx = xx;
        this.yy = yy;
        this.velocity = v;
        this.x = (int)xx;
        this.y = (int)yy;
    }
}
