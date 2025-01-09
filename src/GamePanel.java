import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.swing.*;
import javax.swing.Timer;

public class GamePanel extends JPanel {

    final static double WINW = Toolkit.getDefaultToolkit().getScreenSize().getWidth(), WINH = Toolkit.getDefaultToolkit().getScreenSize().getHeight();

    Main main;
    String type;
    ImageIcon risoHitAnim, risoIdleLeftAnim, risoIdleRightAnim;
    Timer animTimer;

    BufferedImage
        logo,
        titleScreen,
        titleStart,
        titleHelp,
        titleExit,
        helpBg,
        characterSelectBg,
        select,
        court,
        gameOver,
        win,
        level1,
        level2,
        level3,
        level4,
        level5;
        

    GamePanel(String t) {
        type = t;
        try {
            logo = ImageIO.read(this.getClass().getResource("sprites/logo.png"));
			characterSelectBg = ImageIO.read(this.getClass().getResource("sprites/characterSelect.png"));
			select = ImageIO.read(this.getClass().getResource("sprites/select.png"));
			helpBg = ImageIO.read(this.getClass().getResource("sprites/help.png"));
            titleScreen = ImageIO.read(this.getClass().getResource("sprites/title.png"));
            titleStart = ImageIO.read(this.getClass().getResource("sprites/titleStart.png"));
            titleHelp = ImageIO.read(this.getClass().getResource("sprites/titleHelp.png"));
            titleExit = ImageIO.read(this.getClass().getResource("sprites/titleExit.png"));
            gameOver = ImageIO.read(this.getClass().getResource("sprites/gameOver.png"));
            win = ImageIO.read(this.getClass().getResource("sprites/win.png"));
            level1 = ImageIO.read(this.getClass().getResource("sprites/level1.png"));
            level2 = ImageIO.read(this.getClass().getResource("sprites/level2.png"));
            level3 = ImageIO.read(this.getClass().getResource("sprites/level3.png"));
            level4 = ImageIO.read(this.getClass().getResource("sprites/level4.png"));
            level5 = ImageIO.read(this.getClass().getResource("sprites/level5.png"));

           

        } catch (Exception e) {
			System.out.println("Failed to load image.");
		}
    }

    public void updateValues(Main m) {
        this.main = m;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        switch (type) {

            case "win" -> {
                g2.drawImage(win, 0, 0, (int)WINW, (int)WINH, null);
            }

            case "gameOver" -> {
                g2.drawImage(gameOver, 0, 0, (int)WINW, (int)WINH, null);
            }
            case "game" -> {
                switch (Main.currentLevel) {
                    
                    case level1 -> g2.drawImage(level1, 0, 0, (int)WINW, (int)WINH, null);

                    case level2 -> g2.drawImage(level2, 0, 0, (int)WINW, (int)WINH, null);

                    case level3 -> g2.drawImage(level3, 0, 0, (int)WINW, (int)WINH, null);

                    case level4 -> g2.drawImage(level4, 0, 0, (int)WINW, (int)WINH, null);

                    case level5 -> g2.drawImage(level5, 0, 0, (int)WINW, (int)WINH, null);
                    default -> {}
                    
                }
                g2.drawImage(court, 0, 0, (int)(int)WINW, (int)(int)WINH, null);
                if (main.timeSlowed) drawTimeSlowVignette(g2);
                drawCooldown(g2, 70, 500, 50, Color.BLUE);

                animateCharacters(g2);
                
                for (Enemy e: main.enemies) {
                    if (e.isActive) {
                        drawEntity(g2, e, Color.BLACK);
                    }
                }
                for (BallShadow b: main.ballShadows) {
                    if (b.getActive()) {
                        drawEntity(g2, b, Color.BLACK);
                    }
                }
                for (Ball b: main.balls) {
                    if (b.getShadow().getActive()) {
                        drawEntity(g2, b, Color.BLUE);
                    }
                }

                //drawDebugStuff(g2);
                g2.drawString("Enemy: " + main.enemyScore, 150, 60);
                g2.drawString("Player: " + main.playerScore, 800, 720);
            }
            case "help" -> g2.drawImage(helpBg, 0, 0, (int)WINW, (int)WINH, null);
            case "character select" -> {
                g2.drawImage(characterSelectBg, 0, 0, (int)GamePanel.WINW, (int)GamePanel.WINH, null);

                //Inflates the size of the button that the user is hovering
                switch (Main.currentHovered) {
                    case charSelect1 -> g2.drawImage(select, (int)(GamePanel.WINW * 0.055), (int)(GamePanel.WINH * 0.82), (int)(GamePanel.WINW * 0.27), (int)(GamePanel.WINH * 0.1), null);
                    case charSelect2 -> g2.drawImage(select, (int)((GamePanel.WINW / 2) - (GamePanel.WINW * 0.135)), (int)(GamePanel.WINH * 0.82), (int)(GamePanel.WINW * 0.27), (int)(GamePanel.WINH * 0.1), null);
                    case charSelect3 -> g2.drawImage(select, (int)(GamePanel.WINW - (GamePanel.WINW * 0.055) - (GamePanel.WINW * 0.27)), (int)(GamePanel.WINH * 0.82), (int)(GamePanel.WINW * 0.27), (int)(GamePanel.WINH * 0.1), null);
                    default -> {}
                }
            }
            case "title" -> {
                if (Main.frames < 127) {
                    g2.drawImage(logo, 0, 0, (int)WINW, (int)WINH,  null);
                    g2.setColor(new Color(0, 0, 0, 255 - (Main.frames * 2)));
                    g2.fillRect(0, 0, (int)(int)WINW, (int)WINH);
                } else if (Main.frames < 254) {
                    g2.drawImage(logo, 0, 0, (int)WINW, (int)WINH,  null);
                    g2.setColor(new Color(0, 0, 0, (Main.frames - 127) * 2));
                    g2.fillRect(0, 0, (int)WINW, (int)WINH);
                } else if (Main.frames < 300) {
                    g2.setColor(new Color(0, 0, 0));
                    g2.fillRect(0, 0, (int)WINW, (int)WINH);
                } else if (Main.frames < 363) {
                    g2.drawImage(titleScreen, 0, 0, (int)WINW, (int)WINH,  null);
                    g2.setColor(new Color(0, 0, 0, 255 - ((Main.frames - 300) * 4)));
                    g2.fillRect(0, 0, (int)WINW, (int)WINH);
                } else {
                    g2.drawImage(titleScreen, 0, 0, (int)WINW, (int)WINH,  null);
                    switch (Main.currentHovered) {
                        case titleStart -> g2.drawImage(titleStart, 0, 0, (int)WINW, (int)WINH,  null);
                        case titleExit -> g2.drawImage(titleExit, 0, 0, (int)WINW, (int)WINH, null);
                        case titleHelp -> g2.drawImage(titleHelp, 0, 0, (int)WINW, (int)WINH, null);
                        default -> {}
                    }
                }
            }
        }
    }

    private void drawTimeSlowVignette(Graphics2D g2) {
        double opacity;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        if (main.player.abilityTime < 20) {
            opacity = main.player.abilityTime * 0.05;
        } else if (main.player.abilityTime < 80) {
            opacity = 1;
        } else {
            opacity = (100 - main.player.abilityTime) * 0.05;
        }
        for (int i = 0; i < 64; i++) {
            g2.setColor(new Color(0, 0, 0, (int)(4.0 * (double)i * opacity)));
            g2.fillRect(0, 252 - (i * 4), (int)WINW, 4);
            g2.fillRect(0, (int)WINH - 252 + (i * 4), (int)WINW, 4);
            g2.fillRect(252 - (i * 4), 0, 4, (int)WINH);
            g2.fillRect((int)WINW - 252 + (i * 4), 0, 4, (int)WINH);
        }
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    private void drawEntity(Graphics2D g2, Entity e, Color c) {
        g2.setColor(c);
        g2.fillRect(e.x, e.y, e.size, e.size);
    }

    private void drawCooldown(Graphics2D g2, int x, int y, double radius, Color c) {
        double totalCooldown;
        int cooldownLeft = (int)System.currentTimeMillis() - (int)main.player.getLastAbilityTime();
        totalCooldown = switch (main.player.ability) {
            case riso -> main.player.getDashCooldown();
            case adonis -> main.player.getStrongHitCooldown();
            case tasha -> main.player.getTimeSlowCooldown();
            default -> 0;
        };
        
        g2.setColor(c);

        if (cooldownLeft < totalCooldown) {
            Polygon cooldown = new Polygon();
            cooldown.addPoint(x, y - (int)radius);
            int degrees = (int)((cooldownLeft / totalCooldown) * 360);
            for (int i = 1; i <= degrees; i++) {
                int x2, y2;
                x2 = (int)(radius * Math.sin(Math.toRadians(i)));
                y2 = (int)(radius * Math.cos(Math.toRadians(i)));
                cooldown.addPoint(x - x2, y - y2);
            }
            cooldown.addPoint(x, y);
            g2.fillPolygon(cooldown);
        } else {
            g2.fillOval(x - (int)radius + 1, y - (int)radius + 1, (int)(radius * 2) - 2, (int)(radius * 2) - 2);

        }
    }
    private void animateCharacters(Graphics2D g2) {
        if (main.characterModel == 1) {
            switch (main.player.currentState) {
                case PlayerStates.idle_right:
                    main.player.risoIdleRightAnim.paintIcon(this, g2, main.player.x, main.player.y);
                    break;
                case PlayerStates.move_right:
                    main.player.risoMoveRightAnim.paintIcon(this, g2, main.player.x, main.player.y);
                    break;
                case PlayerStates.move_down:
                    //player.risoMoveDownAnim.paintIcon(this, g2, player.x, player.y);
                    break;
                case PlayerStates.move_left:
                    //player.risoMoveLeftAnim.paintIcon(this, g2, player.x, player.y);
                    break;
                case PlayerStates.move_up:
                    //player.risoMoveUpAnim.paintIcon(this, g2, player.x, player.y);
                    break;
                case PlayerStates.hit:
                    main.player.risoHitAnim.paintIcon(this, g2, main.player.x, main.player.y);
                    
                    break;
            }
        }
    }
        
        

    
    private void drawDebugStuff(Graphics2D g2) {
        g2.setStroke(new BasicStroke(10));
        for (BallShadow b: main.ballShadows) {
            g2.setColor(Color.RED);
            g2.drawLine((int)b.destinationX, (int)b.destinationY, (int)b.destinationX, (int)b.destinationY);
            g2.setColor(Color.GREEN);
            g2.drawLine((int)b.departureX, (int)b.departureY, (int)b.departureX, (int)b.departureY);
        }
    }
}
   