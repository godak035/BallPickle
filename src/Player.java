/***
 * Player.java
 * A Player will be either riso, adonis, or tasha and will have abilities that can be activated.
 * By: David Sue, Vadim Mironov, Avishan Ketheswaran and Owen McCarthy
 * January 14, 2025
 */

public class Player extends Entity {

    private int abilityTime = 0;
    private boolean abilityON = false;
    public static enum abilityChoices { riso, adonis, tasha }
    private abilityChoices ability;
    private long lastAbilityTime; 

    private final int 
        dashCooldown = 3000,  //Time duration in milliseconds of dash ability
        timeSlowCooldown = 3000,  //Time duration in milliseconds of time slowdown ability
        strongHitCooldown = 3000,  //Time duration in milliseconds of strong hit ability
        positionXRelativeTo = (int)(280.0 / 1024.0 * GamePanel.WINW),
        positionYRelativeTo = (int)(430.0 / 768.0 * GamePanel.WINH);

    /**
     * Constructor
     * @param xx  the x position of the player
     * @param yy  the y position of the player
     * @param v   the velocity of the player
     * @param s   the size of the player
     */
    public Player(double xx, double yy, double v, int s) {
        super(xx, yy, v, s);
        this.ability = abilityChoices.riso;
        this.lastAbilityTime = System.currentTimeMillis();
    }

    /**
     * Changes the player's ability to a
     * @param a  the new ability
     */
    public void changeAbility(abilityChoices a) {
        ability = a;
    }

    /**
     * Updates the x and y position of the player to align with the xx and yy
     */
    @Override
    public void updatePosition() {
        this.x = (int)xx + positionXRelativeTo;
        this.y = (int)yy + positionYRelativeTo;
    }

    /**
     * Resets the cooldown of the player's ability
     */
    public void resetCooldown() { this.lastAbilityTime = 0; }
    
    /**
     * Uses the player's ability
     * @param rightPressed  Whether right or left was the last direction to be pressed (true if right, false if left)
     */
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

    //getter methods 
    public int getPositionXRelativeTo() { return this.positionXRelativeTo; }
    public int getPositionYRelativeTo() { return this.positionYRelativeTo; }
    public int getDashCooldown() { return this.dashCooldown; }
    public int getStrongHitCooldown() { return this.strongHitCooldown; }
    public int getTimeSlowCooldown() { return this.timeSlowCooldown; }
    public long getLastAbilityTime() { return this.lastAbilityTime; }
    public int getAbilityTime() { return this.abilityTime; }
    public boolean getAbilityON() { return this.abilityON; }
    public abilityChoices getAbility() { return this.ability; }
    
    //setter methods
    public void setAbilityON(boolean on) { this.abilityON = on; }
    public void setAbilityTime(int time) { this.abilityTime = time; }
}