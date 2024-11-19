import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
public class Main implements KeyListener, ActionListener {
	static private JFrame frame;
	static private GUI panel;
	static  enum screen { title, help, characterSelect, inGame };
	static private screen currentScreen;
	static  enum hovered { charSelect1, charSelect2, charSelect3, titleStart, titleCharSelect, titleHelp, inGame, helpExit };
	static private hovered currentHovered;
    static private Timer gameLoopTimer;
    
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Main();
			}
		});
	}
	
	Main() {
		currentScreen = screen.title;
		currentHovered = hovered.titleStart;

        gameLoopTimer = new Timer(5, this);
        

		
		frame = new JFrame();
		frame.setSize(1024, 768);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.addKeyListener(this);
		
		panel = new GUI();
		panel.setPreferredSize(new Dimension(1024, 768));
		
		frame.setContentPane(panel);
		frame.pack();
		frame.setVisible(true);

        
	}
	
	//required but useless methods
	public void keyReleased(KeyEvent e) {} 
	public void keyTyped(KeyEvent e) {}
	
	public void keyPressed(KeyEvent e) {
		if (currentScreen == screen.characterSelect) {
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				if (currentHovered == hovered.charSelect1) {
					currentHovered = hovered.charSelect2;
					panel.repaint();
				} else if (currentHovered == hovered.charSelect2) {
					currentHovered = hovered.charSelect3;
					panel.repaint();
				}
			} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				if (currentHovered == hovered.charSelect2) {
					currentHovered = hovered.charSelect1;
					panel.repaint();
				} else if (currentHovered == hovered.charSelect3) {
					currentHovered = hovered.charSelect2;
					panel.repaint();
				}
			} else if (e.getKeyCode() == KeyEvent.VK_J) {
				currentScreen = screen.title;
				currentHovered = hovered.titleStart;
				panel.repaint();
			}
		} else if (currentScreen == screen.title) {
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				if (currentHovered == hovered.titleCharSelect) {
					currentHovered = hovered.titleStart;
					panel.repaint();
				} else if (currentHovered == hovered.titleHelp) {
					currentHovered = hovered.titleCharSelect;
					panel.repaint();
				}
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				if (currentHovered == hovered.titleStart) {
					currentHovered = hovered.titleCharSelect;
					panel.repaint();
				} else if (currentHovered == hovered.titleCharSelect) {
					currentHovered = hovered.titleHelp;
					panel.repaint();
				}
			} else if (e.getKeyCode() == KeyEvent.VK_J) {
				switch (currentHovered) {
				case titleStart:
					currentScreen = screen.inGame;
					currentHovered = hovered.inGame;
					panel.repaint();
					break;
				case titleCharSelect:
					currentScreen = screen.characterSelect;
					currentHovered = hovered.charSelect1;
					panel.repaint();
					break;
				case titleHelp:
					currentScreen = screen.help;
					currentHovered = hovered.helpExit;
					panel.repaint();
					break;
				default:
					break;
				}
			}
		} else if (currentScreen == screen.help) {
			if (e.getKeyCode() == KeyEvent.VK_J) {
				currentScreen = screen.title;
				currentHovered = hovered.titleStart;
				panel.repaint();
			}
		}
	}//end keyPressed

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    /*
     * getter methods
     */
    public static screen getCurrentScreen() { return currentScreen; }
    public static hovered getCurrentHovered() { return currentHovered; }
}