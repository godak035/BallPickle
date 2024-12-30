public class Ball extends Entity {

    BallShadow shadow;

    public Ball(BallShadow b) {
        super(b.xx, b.yy, 0, b.size);
        this.shadow = b;
    }
    
    @Override
    public void updatePosition() {
        this.x = (int)this.xx;
        this.y = (int)this.yy;
    }

    public void setShadow(BallShadow b) { this.shadow = b; }
    public BallShadow getShadow() { return this.shadow; }

    public void syncLocation() {
        //the distance from the BallShadow's departure to it's destination
        double distance = Math.sqrt(Math.pow(Math.abs(shadow.departureX - shadow.destinationX), 2) + Math.pow(Math.abs(shadow.departureY - shadow.destinationY), 2));
        //the distante between the BallShadow's current position and it's departure point
        double progress = Math.sqrt(Math.pow(Math.abs(shadow.departureX - shadow.xx), 2) + Math.pow(Math.abs(shadow.departureY - shadow.yy), 2));
        //the percent of it's journey the ball has traveled will determine its height
        double h = (progress / distance) * 100;
        if (h > 50) h = 100 - h;
        this.xx = shadow.xx;
        this.yy = shadow.yy - Math.abs(h);
    }
}
