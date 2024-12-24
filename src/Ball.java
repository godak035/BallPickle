public class Ball extends Entity {
    
    public Ball(double xx, double yy, int size) {
        super(xx, yy, 0, size);
    }
    
    @Override
    public void updatePosition() {
        this.x = (int)this.xx;
        this.y = (int)this.yy;
    }

    public void syncLocation(BallShadow b) {
        //the distance from the BallShadow's departure to it's destination
        double distance = Math.sqrt(Math.pow(Math.abs(b.departureX - b.destinationX), 2) + Math.pow(Math.abs(b.departureY - b.destinationY), 2));
        //the distante between the BallShadow's current position and it's departure point
        double progress = Math.sqrt(Math.pow(Math.abs(b.departureX - b.xx), 2) + Math.pow(Math.abs(b.departureY - b.yy), 2));
        //the percent of it's journey the ball has traveled will determine its height
        double h = (progress / distance) * 100;
        if (h > 50) h = 100 - h;
        this.xx = b.xx;
        this.yy = b.yy - Math.abs(h);
    }
}
