/**
 * Main.java
 * The main menu screen. :scream:
 */

//
import java.awt.*;
import javax.swing.*;
import javax.imageio.*;
import java.awt.image.*;
import java.awt.event.*;

public class Main implements ActionListener {
    
    JFrame frame;
    TitlePanel title;
    GamePanel inGame;
    HelpPanel help;
    CharacterSelectPanel characterSelect;
    BufferedImage characterSelectBg, select, titleBg, titleSelect, helpBg, court;
    static enum hovered { charSelect1, charSelect2, charSelect3, titleStart, titleCharSelect, titleHelp, inGame, helpExit };
	static private hovered currentHovered;
    boolean upPressedThisTick, downPressedThisTick, leftPressedThisTick, rightPressedThisTick, enterPressedThisTick;
    KeyHandler KeyH;
    Timer gameLoopTimer;
    Player player;
    final int 
        playerPositionXRelativeTo = 230,
        playerPositionYRelativeTo = 383,
        playerXMax = 542,
        playerYMax = 289;

    int frameCount = 0; //for debugging

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

        gameLoopTimer = new Timer(5, this);
        gameLoopTimer.setInitialDelay(5);
        gameLoopTimer.setActionCommand("gameLoopTimer");
        gameLoopTimer.start();

        currentHovered = hovered.titleStart;

        player = new Player(0, 0, 10, 20);

        upPressedThisTick = false;
        leftPressedThisTick = false;
        downPressedThisTick = false;
        rightPressedThisTick = false;
        enterPressedThisTick = false;

        KeyH = new KeyHandler();

        frame = new JFrame();
		frame.setSize(1024, 768);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
        frame.addKeyListener(KeyH);
		ImageIcon logo = new ImageIcon("logo.png");
		frame.setIconImage(logo.getImage());

        title = new TitlePanel();
        title.setPreferredSize(new Dimension(1024, 768));

        inGame = new GamePanel();
        title.setPreferredSize(new Dimension(1024, 768));

        help = new HelpPanel();
        title.setPreferredSize(new Dimension(1024, 768));

        characterSelect = new CharacterSelectPanel();
        title.setPreferredSize(new Dimension(1024, 768));
        
        frame.add(title);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void getInputs() {
        switch (currentHovered) {
        case titleStart:
            if (KeyH.downPressed && !downPressedThisTick) {
                downPressedThisTick = true;
                currentHovered = hovered.titleCharSelect;
            }
            if (KeyH.enterPressed && !enterPressedThisTick) {
                enterPressedThisTick = true;
                frame.remove(title);
                frame.add(inGame);
                currentHovered = hovered.inGame;
                frame.revalidate();
            }
            break;
        case titleCharSelect:
            if (KeyH.downPressed && !downPressedThisTick) {
                downPressedThisTick = true;
                currentHovered = hovered.titleHelp;
            }
            if (KeyH.upPressed && !upPressedThisTick) {
                upPressedThisTick = true;
                currentHovered = hovered.titleStart;
            }
            if (KeyH.enterPressed && !enterPressedThisTick) {
                enterPressedThisTick = true;
                frame.remove(title);
                frame.add(characterSelect);
                currentHovered = hovered.charSelect1;
                frame.revalidate();
            }
            break;
        case titleHelp:
            if (KeyH.upPressed && !upPressedThisTick) {
                upPressedThisTick = true;
                currentHovered = hovered.titleCharSelect;
            }
            if (KeyH.enterPressed && !enterPressedThisTick) {
                enterPressedThisTick = true;
                frame.add(help);
                frame.remove(title);
                currentHovered = hovered.helpExit;
                frame.revalidate();
            }
            break;
        case charSelect1:
            if (KeyH.rightPressed && !rightPressedThisTick) {
                rightPressedThisTick = true;
                currentHovered = hovered.charSelect2;
            }
            if (KeyH.enterPressed && !enterPressedThisTick) {
                enterPressedThisTick = true;
                frame.remove(characterSelect);
                frame.add(title);
                currentHovered = hovered.titleStart;
                frame.revalidate();
            }
            break;
        case charSelect2:
            if (KeyH.rightPressed && !rightPressedThisTick) {
                rightPressedThisTick = true;
                currentHovered = hovered.charSelect3;
            }
            if (KeyH.leftPressed && !leftPressedThisTick) {
                leftPressedThisTick = true;
                currentHovered = hovered.charSelect1;
            }
            if (KeyH.enterPressed && !enterPressedThisTick) {
                enterPressedThisTick = true;
                frame.remove(characterSelect);
                frame.add(title);
                currentHovered = hovered.titleStart;
                frame.revalidate();
            }
            break;
        case charSelect3:
            if (KeyH.leftPressed && !leftPressedThisTick) {
                leftPressedThisTick = true;
                currentHovered = hovered.charSelect2;
            }
            if (KeyH.enterPressed && !enterPressedThisTick) {
                enterPressedThisTick = true;
                frame.remove(characterSelect);
                frame.add(title);
                currentHovered = hovered.titleStart;
                frame.revalidate();
            }
            break;
        case helpExit:
            if (KeyH.enterPressed && !enterPressedThisTick) {
                System.out.println("help exit AKLSJDHASKLDHASKLJDHAS");
                enterPressedThisTick = true;
                frame.remove(help);
                frame.add(title);
                currentHovered = hovered.titleStart;
                frame.revalidate();
            }
            break;
        case inGame:
            if (KeyH.rightPressed) {
                player.xx += player.velocity;
                if (player.xx > playerXMax) player.xx = playerXMax;
            }
            if (KeyH.leftPressed) {
                player.xx -= player.velocity;
                if (player.xx < 0) player.xx = 0;
            }
            if (KeyH.upPressed) {
                player.yy -= player.velocity;
                if (player.yy < 0) player.yy = 0;
            }
            if (KeyH.downPressed) {
                player.yy += player.velocity;
                if (player.yy > playerYMax) player.yy = playerYMax;
            }
            break;
        default:
            break;
        }
        if (!KeyH.upPressed) upPressedThisTick = false;
        if (!KeyH.downPressed) downPressedThisTick = false;
        if (!KeyH.leftPressed) leftPressedThisTick = false;
        if (!KeyH.rightPressed) rightPressedThisTick = false;
        if (!KeyH.enterPressed) enterPressedThisTick = false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String event = e.getActionCommand();
        switch (event) {
        case "gameLoopTimer":
            getInputs();
            title.repaint();
            help.repaint();
            characterSelect.repaint();
            inGame.repaint();
            player.updatePosition();
            //for debugging
            System.out.println("frame: " + frameCount + ", hovered: " + currentHovered + ", enter pressed: " + enterPressedThisTick);
            frameCount++;
        }
    }

    private class TitlePanel extends JPanel {
        @Override
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

    private class HelpPanel extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D)g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.drawImage(helpBg, 0, 0, null);
        }
    }

    private class CharacterSelectPanel extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D)g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.drawImage(characterSelectBg, 0, 0, null);
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
        }
    }

    private class GamePanel extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D)g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.drawImage(court, 0, 0, null);
            g2.fillRect(player.x + playerPositionXRelativeTo, player.y + playerPositionYRelativeTo, player.size, player.size);
        }
    }
}
