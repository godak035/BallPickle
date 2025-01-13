/***
 * KeyHandler.java
 * The KeyHandler class handles keystrokes and makes it easier to see if a button is currently being pressed or not.
 * by: David Sue, Vadim Mironov, Avishan Ketheswaran and Owen McCarthy
 * January 14, 2024
 */

import java.awt.event.*;

public class KeyHandler implements KeyListener{

    //booleans of whether a key (up, down, left, right, enter) is currently being pressed
    private boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, abilityPressed;
    
    /**
     * Updates the corresponding variable when a key gets pressed
     * @param e: The KeyEvent that caused the method to be called
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_I) abilityPressed = true; //Ability key (i)
        if (code == KeyEvent.VK_U) enterPressed = true; //swing/enter
        if (code == KeyEvent.VK_A) leftPressed = true;
        if (code == KeyEvent.VK_D) rightPressed = true;
        if (code == KeyEvent.VK_W) upPressed = true;
        if (code == KeyEvent.VK_S) downPressed = true;
    }
    
    /**
     * Updates the corresponding variable when a key gets released
     * @param e: The KeyEvent that caused the method to be called
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_I) abilityPressed = false; //Ability key (i)
        if (code == KeyEvent.VK_U) enterPressed = false; //swing/enter
        if (code == KeyEvent.VK_A) leftPressed = false;
        if (code == KeyEvent.VK_D) rightPressed = false;
        if (code == KeyEvent.VK_W) upPressed = false;
        if (code == KeyEvent.VK_S) downPressed = false;
    }
    
    //Method from KeyListener that is not used
    @Override
    public void keyTyped(KeyEvent e) {}

    //getter methods
    public boolean getAbilityPressed() { return this.abilityPressed; }
    public boolean getEnterPressed() { return this.enterPressed; }
    public boolean getLeftPressed() { return this.leftPressed; }
    public boolean getRightPressed() { return this.rightPressed; }
    public boolean getUpPressed() { return this.upPressed; }
    public boolean getDownPressed() { return this.downPressed; }

}
