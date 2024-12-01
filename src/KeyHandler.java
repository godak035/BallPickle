

import java.awt.event.*;

public class KeyHandler implements KeyListener{

    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed;
    
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
        
        if (code== KeyEvent.VK_A){
            leftPressed = true;
        }

        if (code== KeyEvent.VK_D){
            rightPressed = true;
        }
        if (code== KeyEvent.VK_W){
            upPressed = true;
        }

        if (code== KeyEvent.VK_S){
            downPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code==KeyEvent.VK_F){
            enterPressed = false;
        }
        
        if (code== KeyEvent.VK_A){
            leftPressed = false;
        }

        if (code== KeyEvent.VK_D){
            rightPressed = false;
        }
        if (code== KeyEvent.VK_W){
            upPressed = false;
        }

        if (code== KeyEvent.VK_S){
            downPressed = false;
        }
    }
}
