import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.swing.*;
public class GUI extends JPanel {

    BufferedImage characterSelect, title, select, titleSelect, help, inGame;

    GUI() {
        try {
			characterSelect = ImageIO.read(this.getClass().getResource("sprites/characterSelect.png"));
			select = ImageIO.read(this.getClass().getResource("sprites/select.png"));
			title = ImageIO.read(this.getClass().getResource("sprites/title.png"));
			titleSelect = ImageIO.read(this.getClass().getResource("sprites/titleSelect.png"));
			help = ImageIO.read(this.getClass().getResource("sprites/help.png"));
			inGame = ImageIO.read(this.getClass().getResource("sprites/inGame.png"));
		} catch (Exception e) {
			System.out.println("Failed to load image.");
		}
    }

    @Override
    public void paintComponent(Graphics  g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        if (Main.getCurrentScreen() == Main.screen.characterSelect) {				
            g2.drawImage(characterSelect, 0, 0, null);
            g2.drawImage(select, 32, 615, null);
            g2.drawImage(select, 384, 615, null);
            g2.drawImage(select, 720, 615, null);
            switch (Main.getCurrentHovered()) {
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
        } else if (Main.getCurrentScreen() == Main.screen.title) {
            g2.drawImage(title, 0, 0, null);
            switch (Main.getCurrentHovered()) {
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
        } else if (Main.getCurrentScreen() == Main.screen.help) {
            g2.drawImage(help, 0, 0, null);
        } else if (Main.getCurrentScreen() == Main.screen.inGame) {
            g2.drawImage(inGame, 0, 0, null);
        }
    }//end paintComponent(Graphics g)
}//end DrawingPanel class