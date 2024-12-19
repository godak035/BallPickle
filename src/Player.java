public class Player extends Entity {

    int size;
    int playerXMax;
    int score = 0;
    int dashStart, dashCount = 0;
    boolean dashON = false;

    public Player(double xx, double yy, double v, int s) {
        super(xx, yy, v);
        this.size = s;
    }

    @Override
    public void updatePosition() {
        this.x = (int) xx;
        this.y = (int) yy;
    }
    
    private long lastDashTime = 0; 
    private final int DASH_COOLDOWN = 1000; 
        
    public void dash(boolean rightPressed, int currentFrame) {
        dashStart = currentFrame;

        if (System.currentTimeMillis() - lastDashTime < DASH_COOLDOWN) {
            System.out.println("Dash on cooldown!");
            return;
        }
        
        lastDashTime = System.currentTimeMillis();
        System.out.println("Dash activated!");
        dashON=true;

        // if (!rightPressed) {
        //     this.xx -= 100;
        //     dashON = false;
        // } else if (rightPressed) {
        //     this.xx += 100;
        //     dashON = false;
        // }
    }
}