/***
 * KeyHandler.java
 * The KeyHandler class handles keystrokes and makes it easier to see if a button is currently being pressed or not.
 * by: David Sue, Vadim Mironov, Avishan Ketheswaran and Owen McCarthy
 * December 2, 2024
 */
import java.awt.event.*;

public class KeyHandler implements KeyListener{

    //booleans of whether a key (up, down, left, right, enter) is currently being pressed
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed;
    
    /**
     * Updates the corresponding variable when a key gets pressed
     * @param e: The KeyEvent that caused the method to be called
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_F) {
            enterPressed = true;
        }
        
        if (code == KeyEvent.VK_LEFT) {
            leftPressed = true;
        }
        
        if (code == KeyEvent.VK_RIGHT) {
            rightPressed = true;
        }
        if (code == KeyEvent.VK_UP) {
            upPressed = true;
        }
        
        if (code == KeyEvent.VK_DOWN) {
            downPressed = true;
        }
    }
    
    /**
     * Updates the corresponding variable when a key gets released
     * @param e: The KeyEvent that caused the method to be called
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        
        if (code == KeyEvent.VK_F) {
            enterPressed = false;
        }
        
        if (code == KeyEvent.VK_LEFT) {
            leftPressed = false;
        }
        
        if (code == KeyEvent.VK_RIGHT) {
            rightPressed = false;
        }
        if (code == KeyEvent.VK_UP) {
            upPressed = false;
        }
        
        if (code == KeyEvent.VK_DOWN) {
            downPressed = false;
        }
    }
    
    //method form KeyListener that is not used
    public void keyTyped(KeyEvent e) {}
}
