

import java.awt.event.*;

public class KeyHandler implements KeyListener{

    boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed;
    
    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code==KeyEvent.VK_F){
            enterPressed = true;
        }
        
        if (code== KeyEvent.VK_LEFT){
            leftPressed = true;
        }

        if (code== KeyEvent.VK_RIGHT){
            rightPressed = true;
        }
        if (code== KeyEvent.VK_UP){
            upPressed = true;
        }

        if (code== KeyEvent.VK_DOWN){
            downPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code==KeyEvent.VK_F){
            enterPressed = false;
        }
        
        if (code== KeyEvent.VK_LEFT){
            leftPressed = false;
        }

        if (code== KeyEvent.VK_RIGHT){
            rightPressed = false;
        }
        if (code== KeyEvent.VK_UP){
            upPressed = false;
        }

        if (code== KeyEvent.VK_DOWN){
            downPressed = false;
        }
    }
}
