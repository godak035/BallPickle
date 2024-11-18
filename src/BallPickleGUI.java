import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.swing.*;
public class BallPickleGUI implements KeyListener {
	JFrame frame;
	DrawingPanel panel;
	BufferedImage characterSelect, title, select, titleSelect, help, inGame;
	enum screen { title, help, characterSelect, inGame };
	screen currentScreen;
	enum hovered { charSelect1, charSelect2, charSelect3, titleStart, titleCharSelect, titleHelp, inGame, helpExit };
	hovered currentHovered;
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new BallPickleGUI();
			}
		});
	}
	
	BallPickleGUI() {
		currentScreen = screen.title;
		currentHovered = hovered.titleStart;
		
		frame = new JFrame();
		frame.setSize(1024, 768);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.addKeyListener(this);
		
		panel = new DrawingPanel();
		panel.setPreferredSize(new Dimension(1024, 768));

		
		
		try {
			characterSelect = ImageIO.read(this.getClass().getResource("characterSelect.png"));
			select = ImageIO.read(this.getClass().getResource("select.png"));
			title = ImageIO.read(this.getClass().getResource("title.png"));
			titleSelect = ImageIO.read(this.getClass().getResource("titleSelect.png"));
			help = ImageIO.read(this.getClass().getResource("help.png"));
			inGame = ImageIO.read(this.getClass().getResource("inGame.png"));
		} catch (Exception e) {
			System.out.println("Failed to load image.");
		}
		
		frame.setContentPane(panel);
		frame.pack();
		frame.setVisible(true);
	}
	
	private class DrawingPanel extends JPanel {
		/**
		 * eclipse wants me to do this
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void paintComponent(Graphics  g) {
			Graphics2D g2 = (Graphics2D)g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			
			if (currentScreen == screen.characterSelect) {				
				g2.drawImage(characterSelect, 0, 0, null);
				g2.drawImage(select, 32, 615, null);
				g2.drawImage(select, 384, 615, null);
				g2.drawImage(select, 720, 615, null);
				switch (currentHovered) {
				case charSelect1:
					g2.drawImage(select, 12, 605, select.getWidth() + 40, select.getHeight() + 20, null);
					break;
				case charSelect2:
					g2.drawImage(select, 364, 605, select.getWidth() + 40, select.getHeight() + 20, null);
					break;
				case charSelect3:
					g2.drawImage(select, 700, 605, select.getWidth() + 40, select.getHeight() + 20, null);
					break;
				default:
					break;
				}
			} else if (currentScreen == screen.title) {
				g2.drawImage(title, 0, 0, null);
				switch (currentHovered) {
				case titleStart:
					g2.drawImage(titleSelect, 385, 379, null);
					g2.rotate(Math.PI);
					g2.drawImage(titleSelect, -630, -438, null);
					g2.rotate(Math.PI);
					break;
				case titleCharSelect:
					g2.drawImage(titleSelect, 152, 428, null);
					g2.rotate(Math.PI);
					g2.drawImage(titleSelect, -867, -490, null);
					g2.rotate(Math.PI);
					break;
				case titleHelp:
					g2.drawImage(titleSelect, 407, 479, null);
					g2.rotate(Math.PI);
					g2.drawImage(titleSelect, -612, -538, null);
					g2.rotate(Math.PI);
					break;
				default:
					break;
				}
			} else if (currentScreen == screen.help) {
				g2.drawImage(help, 0, 0, null);
			} else if (currentScreen == screen.inGame) {
				g2.drawImage(inGame, 0, 0, null);
			}
		}//end paintComponent(Graphics g)
	}//end DrawingPanel class
	
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
}