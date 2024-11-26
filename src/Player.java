public class Player extends Entity{

    int size;
    KeyHandler keyH;

    /**
    * A concise description of what the method does.
    * double xx, double yy, double v, int s, KeyHandler, keyH
    */
    public Player(double xx, double yy, double v, int s, KeyHandler keyH){
        super(xx, yy, v);
        this.size = s;
        this.keyH = keyH;
    }

    //The player's movement
    public void update(){
        if (keyH.enterPressed==true){
            //Swings
        }
        
        if(keyH.upPressed== true){
            y -= velocity;
        }

        if(keyH.downPressed== true){
            y += velocity;
        }

        if(keyH.rightPressed== true){
            x += velocity;
        }

        if(keyH.leftPressed== true){
            x-=velocity;
        }
    }
}