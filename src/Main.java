/**
 * Main.java
 * The main menu screen. :scream:
 */

//
import java.awt.*;
import javax.swing.*;
import javax.imageio.*;
import java.awt.image.*;

public class Main {
    JFrame frame;
    titlePanel title;
    gamePanel inGame;
    helpPanel help;
    characterSelectPanel characterSelect;
    BufferedImage characterSelectBg, select, titleBg, titleSelect, helpBg, court;
    static enum hovered { charSelect1, charSelect2, charSelect3, titleStart, titleCharSelect, titleHelp, inGame, helpExit };
	static private hovered currentHovered;
    boolean upPressedThisTick, downPressedThisTick, leftPressedThisTick, rightPressedThisTick, fPressedThisTick;
    KeyHandler KeyH;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main();
            }
        });
    }

    Main() {
        try {
			characterSelectBg = ImageIO.read(this.getClass().getResource("sprites/characterSelect.png"));
			select = ImageIO.read(this.getClass().getResource("sprites/select.png"));
			titleBg = ImageIO.read(this.getClass().getResource("sprites/title.png"));
			titleSelect = ImageIO.read(this.getClass().getResource("sprites/titleSelect.png"));
			helpBg = ImageIO.read(this.getClass().getResource("sprites/help.png"));
            court = ImageIO.read(this.getClass().getResource("sprites/court.png"));
        } catch (Exception e) {
			System.out.println("Failed to load image.");
		}

        currentHovered = hovered.titleStart;

        upPressedThisTick = false;
        leftPressedThisTick = false;
        downPressedThisTick = false;
        rightPressedThisTick = false;
        fPressedThisTick = false;

        KeyH = new KeyHandler();

        frame = new JFrame();
		frame.setSize(1024, 768);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
        frame.addKeyListener(KeyH);
		ImageIcon logo = new ImageIcon("logo.png");
		frame.setIconImage(logo.getImage());

        title = new titlePanel();
        title.setPreferredSize(new Dimension(1024, 768));
        
        frame.add(title);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void updateHovered() {
        switch (currentHovered) {
        case titleStart:
            if (KeyH.downPressed) currentHovered = hovered.titleCharSelect;
            if (KeyH.enterPressed) {
                frame.remove(title);
                frame.add(inGame);
                currentHovered = hovered.inGame;
            }
            break;
        case titleCharSelect:
            if (KeyH.downPressed) currentHovered = hovered.titleStart;
            if (KeyH.upPressed) currentHovered = hovered.titleHelp;
            if (KeyH.enterPressed) {
                frame.remove(title);
                frame.add(characterSelect);
                currentHovered = hovered.charSelect1;
            }
            break;
        case titleHelp:
            if (KeyH.upPressed) currentHovered = hovered.titleCharSelect;
            if (KeyH.enterPressed) {
                frame.remove(title);
                frame.add(help);
                currentHovered = hovered.helpExit;
            }
            break;
            
        }
    }

    private class titlePanel extends JPanel {
        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D)g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.drawImage(titleBg, 0, 0, null);
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
        }
    }

    private class helpPanel extends JPanel {
        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D)g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }
    }

    private class characterSelectPanel extends JPanel {
        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D)g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }
    }

    private class gamePanel extends JPanel {
        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D)g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }
    }
}
