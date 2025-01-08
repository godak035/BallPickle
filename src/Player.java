import javax.swing.ImageIcon;

public class Player extends Entity {

    int abilityTime = 0;
    boolean abilityON = false;
    public static enum abilityChoices { riso, adonis, tasha }
    abilityChoices ability;
    
    private long lastAbilityTime; 
    private final int 
        dashCooldown = 3000,  //Time duration in milliseconds of dash ability
        timeSlowCooldown = 3000,  //Time duration in milliseconds of time slowdown ability
        strongHitCooldown = 3000,  //Time duration in milliseconds of strong hit ability
        positionXRelativeTo = (int)(280.0 / 1024.0 * GamePanel.WINW),
        positionYRelativeTo = (int)(430.0 / 768.0 * GamePanel.WINH);

    public Player(double xx, double yy, double v, int s) {
        super(xx, yy, v, s);
        this.ability = abilityChoices.riso;
        this.lastAbilityTime = System.currentTimeMillis();
    }

    public void changeAbility(abilityChoices a) {
        ability = a;
    }

    @Override
    public void updatePosition() {
        this.x = (int)xx + positionXRelativeTo;
        this.y = (int)yy + positionYRelativeTo;
    }

    public void resetCooldown() { this.lastAbilityTime = 0; }
    
    public void useAbility(boolean rightPressed) {
        switch (ability) {
            case riso -> {
                
                if (System.currentTimeMillis() - lastAbilityTime < dashCooldown) {
                    //System.out.println("Dash on cooldown!");
                    return;
                }
                }
            case adonis -> {
                
                if (System.currentTimeMillis() - lastAbilityTime < strongHitCooldown) {
                    //System.out.println("Strong hit on cooldown!");
                    return;
                }
            }
            case tasha -> {
               
                if (System.currentTimeMillis() - lastAbilityTime < timeSlowCooldown) {
                    //System.out.println("Time slow on cooldown!");
                    return;
                }
            }
        }
        lastAbilityTime = System.currentTimeMillis();
        abilityON=true;
    }

    //getter methods because encapsulation yes
    public int getPositionXRelativeTo() { return this.positionXRelativeTo; }
    public int getPositionYRelativeTo() { return this.positionYRelativeTo; }
    public int getDashCooldown() { return this.dashCooldown; }
    public int getStrongHitCooldown() { return this.strongHitCooldown; }
    public int getTimeSlowCooldown() { return this.timeSlowCooldown; }
    public long getLastAbilityTime() { return this.lastAbilityTime; }

    public int currentState = PlayerStates.idle_right;
    
    //animations for different states for Riso
    public ImageIcon risoIdleRightAnim = new ImageIcon(this.getClass().getResource("sprites/char1_idle_anim_left.gif")); //named it left but actually meant right.
    // public ImageIcon risoMoveRightAnim = new ImageIcon(this.getClass().getResource("sprites/char1_move_right.gif"));
    // public ImageIcon risoMoveDownAnim = new ImageIcon(this.getClass().getResource("sprites/char1_move_down.gif"));
    // public ImageIcon risoMoveLeftAnim = new ImageIcon(this.getClass().getResource("sprites/char1_move_left.gif"));
    // public ImageIcon risoMoveUpAnim = new ImageIcon(this.getClass().getResource("sprites/char1_move_up.gif"));
    public ImageIcon risoHitAnim = new ImageIcon(this.getClass().getResource("sprites/char1_strike_anim2.gif"));

   
}



