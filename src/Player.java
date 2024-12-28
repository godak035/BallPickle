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
        positionXRelativeTo = 230,
        positionYRelativeTo = 475;

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
        System.out.println("Ability activated!");
        abilityON=true;
    }

    //getter methods because encapsulation yes
    public int getPositionXRelativeTo() { return this.positionXRelativeTo; }
    public int getPositionYRelativeTo() { return this.positionYRelativeTo; }
    public int getDashCooldown() { return this.dashCooldown; }
    public int getStrongHitCooldown() { return this.strongHitCooldown; }
    public int getTimeSlowCooldown() { return this.timeSlowCooldown; }
    public long getLastAbilityTime() { return this.lastAbilityTime; }
}