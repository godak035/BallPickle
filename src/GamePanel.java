import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.swing.*;


public class GamePanel extends JPanel {

    final static int winW = 1024, winH = 768;

    Player player;
    Enemy enemy;
    Ball ball;
    boolean timeSlowed;

    String type;

    BufferedImage
        logo,
        titleScreen,
        titleStart,
        titleCharSelect,
        titleHelp,
        helpBg,
        characterSelectBg,
        select,
        court;

    GamePanel(String t) {
        type = t;
        try {
            logo = ImageIO.read(this.getClass().getResource("sprites/logo.png"));
			characterSelectBg = ImageIO.read(this.getClass().getResource("sprites/characterSelect.png"));
			select = ImageIO.read(this.getClass().getResource("sprites/select.png"));
			helpBg = ImageIO.read(this.getClass().getResource("sprites/help.png"));
            court = ImageIO.read(this.getClass().getResource("sprites/court.png"));
            titleScreen = ImageIO.read(this.getClass().getResource("sprites/title.png"));
            titleStart = ImageIO.read(this.getClass().getResource("sprites/titleStart.png"));
            titleCharSelect = ImageIO.read(this.getClass().getResource("sprites/titleCharSelect.png"));
            titleHelp = ImageIO.read(this.getClass().getResource("sprites/titleHelp.png"));
        } catch (Exception e) {
			System.out.println("Failed to load image.");
		}
    }

    public void updateValues(Player p, Ball b, Enemy e, boolean ts) {
        this.player = p;
        this.ball = b;
        this.enemy = e;
        this.timeSlowed = ts;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        switch (type) {
        case "game":
            g2.drawImage(court, 0, 0, winW, winH, null);
            if (timeSlowed) drawTimeSlowVignette(g2);
            drawCooldown(g2, 50, 500, 20, Color.BLUE);
            drawEntity(g2, player, Color.BLACK);
            drawEntity(g2, ball, Color.BLACK);
            drawEntity(g2, enemy, Color.BLACK);
            g2.drawString("Enemy: " + enemy.score, 150, 60);
            g2.drawString("Player: " + player.score, 800, 720);
            break;
        case "help":
            g2.drawImage(helpBg, 0, 0, null);
            break;
        case "character select":
            g2.drawImage(characterSelectBg, 0, 0, null);
            g2.drawImage(select, 32, 615, null);
            g2.drawImage(select, 384, 615, null);
            g2.drawImage(select, 720, 615, null);

            //Inflates the size of the button that the user is hovering
            switch (Main.currentHovered) {
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
            break;
        case "title":
            if (Main.frames < 127) {
                g2.drawImage(logo, 0, 0, winW, winH,  null);
                g2.setColor(new Color(0, 0, 0, 255 - (Main.frames * 2)));
                g2.fillRect(0, 0, winW, winH);
            } else if (Main.frames < 254) {
                g2.drawImage(logo, 0, 0, winW, winH,  null);
                g2.setColor(new Color(0, 0, 0, (Main.frames - 127) * 2));
                g2.fillRect(0, 0, winW, winH);
            } else if (Main.frames < 300) {
                g2.setColor(new Color(0, 0, 0));
                g2.fillRect(0, 0, winW, winH);
            } else if (Main.frames < 363) {
                g2.drawImage(titleScreen, 0, 0, winW, winH,  null);
                g2.setColor(new Color(0, 0, 0, 255 - ((Main.frames - 300) * 4)));
                g2.fillRect(0, 0, winW, winH);
            } else {
                g2.drawImage(titleScreen, 0, 0, winW, winH,  null);
                switch (Main.currentHovered) {
                case titleStart:
                    g2.drawImage(titleStart, 0, 0, winW, winH,  null);
                    break;
                case titleCharSelect:
                    g2.drawImage(titleCharSelect, 0, 0, winW, winH, null);
                    break;
                case titleHelp:
                    g2.drawImage(titleHelp, 0, 0, winW, winH, null);
                    break;
                default:
                    break;
                }
                break;
            }
        }
    }

    private void drawTimeSlowVignette(Graphics2D g2) {
        double opacity;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        if (player.abilityTime < 20) {
            opacity = player.abilityTime * 0.05;
        } else if (player.abilityTime < 80) {
            opacity = 1;
        } else {
            opacity = (100 - player.abilityTime) * 0.05;
        }
        for (int i = 0; i < 64; i++) {
            g2.setColor(new Color(0, 0, 0, (int)(4.0 * (double)i * opacity)));
            g2.fillRect(0, 252 - (i * 4), winW, 4);
            g2.fillRect(0, winH - 252 + (i * 4), winW, 4);
            g2.fillRect(252 - (i * 4), 0, 4, winH);
            g2.fillRect(winW - 252 + (i * 4), 0, 4, winH);
        }
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    private void drawEntity(Graphics2D g2, Entity e, Color c) {
        g2.setColor(c);
        g2.fillRect(e.x, e.y, e.size, e.size);
    }

    private void drawCooldown(Graphics2D g2, int x, int y, double radius, Color c) {
        double totalCooldown;
        int cooldownLeft = (int)System.currentTimeMillis() - (int)player.getLastAbilityTime();
        switch (player.ability) {
        case riso:
            totalCooldown = player.getDashCooldown();
            break;
        case adonis:
            totalCooldown = player.getStrongHitCooldown();
            break;
        case tasha:
            totalCooldown = player.getTimeSlowCooldown();
            break;
        default:
            totalCooldown = 0;
            break;
        }
        
        g2.setColor(c);
        if (totalCooldown == cooldownLeft) {
            g2.fillOval(x - (int)radius, y - (int)radius, (int)radius * 2, (int)radius * 2);
        } else {
            Polygon cooldown = new Polygon();
            cooldown.addPoint(x, y - (int)radius);
            int degrees = (int)((cooldownLeft / totalCooldown) * 360);
            for (int i = 1; i <= degrees; i++) {
                int x2, y2;
                if (i < 90) {
                    x2 = (int)(radius * Math.sin(Math.toRadians(i))) * -1;
                    y2 = (int)(radius * Math.cos(Math.toRadians(i))) * -1;
                } else if (i == 90) {
                    x2 = (int)radius * -1;
                    y2 = 0;
                } else if (i < 180) {
                    x2 = (int)(radius * Math.sin(Math.toRadians(i))) * -1;
                    y2 = (int)(radius * Math.cos(Math.toRadians(i)));
                } else if (i == 180) {
                    x2 = 0;
                    y2 = (int)radius * -1;
                } else if (i < 270) {
                    x2 = (int)(radius * Math.sin(Math.toRadians(i)));
                    y2 = (int)(radius * Math.cos(Math.toRadians(i)));
                } else if (i == 270) {
                    x2 = (int)radius;
                    y2 = 0;
                } else {
                    x2 = (int)(radius * Math.sin(Math.toRadians(i)));
                    y2 = (int)(radius * Math.cos(Math.toRadians(i))) * -1;
                }
                cooldown.addPoint(x + x2, y + y2);
            }
            cooldown.addPoint(x, y);
            g2.fillPolygon(cooldown);
        }
    }
}