public class Player extends Entity{

    int size; //Size fo the object

    Player(double xx, double yy, double v, int size) {
        super(xx, yy, v);
        this.size = size;
        this.x = (int)xx;
        this.y = (int)yy;
    }


    public void setDefaultValues(){
        x=2;
        y=2;

        // vx=5;
        // vx=5;
        
    }

}
