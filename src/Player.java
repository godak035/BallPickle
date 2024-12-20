public class Player extends Entity {

    int size;
    int score = 0;
    int abilityTime = 0;
    boolean abilityON = false;
    static enum abilityChoices { riso, adonis, tasha }
    static abilityChoices ability;
    
    private long lastAbilityTime = 0; 
    private final int dashCooldown = 1000;  //Time duration in milliseconds of dash ability
    private final int timeSlowCooldown = 1000;  //Time duration in milliseconds of time slowdown ability
    private final int strongHitCooldown = 1000;  //Time duration in milliseconds of strong hit ability

    public Player(double xx, double yy, double v, int s) {
        super(xx, yy, v);
        this.size = s;
        this.ability = abilityChoices.riso;
    }

    public void changeAbility(abilityChoices a) {
        ability = a;
    }

    @Override
    public void updatePosition() {
        this.x = (int) xx;
        this.y = (int) yy;
    }
    
    public void useAbility(boolean rightPressed) {
        switch (ability) {
        case riso:
            if (System.currentTimeMillis() - lastAbilityTime < dashCooldown) {
                //System.out.println("Dash on cooldown!");
                return;
            }
            break;
        case adonis:
            if (System.currentTimeMillis() - lastAbilityTime < strongHitCooldown) {
                //System.out.println("Strong hit on cooldown!");
                return;
            }
            break;
        case tasha:
            if (System.currentTimeMillis() - lastAbilityTime < timeSlowCooldown) {
                //System.out.println("Time slow on cooldown!");
                return;
            }
            break;
        }
        lastAbilityTime = System.currentTimeMillis();
        System.out.println("Ability activated!");
        abilityON=true;
    }
}