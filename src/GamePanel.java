/***
 * GamePanel.java
 * A panel that can display the BallPickle game
 * By: David Sue, Vadim Mironov, Avishan Ketheswaran and Owen McCarthy
 * January 14, 2025
 */

import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.swing.*;

public class GamePanel extends JPanel {

    final static double 
        WINW = Toolkit.getDefaultToolkit().getScreenSize().getWidth(), 
        WINH = Toolkit.getDefaultToolkit().getScreenSize().getHeight();

    private Main main;
    private String type;
    private ImageIcon risoHitAnim, risoIdleLeftAnim, risoIdleRightAnim;
    private Timer animTimer;

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
        level2,
        level3,
        level4,
        level5,
        intramuralChampion;
        
    /**
     * Constructor
     * @param t  The type of GamePanel
     */
    GamePanel(String t) {
        type = t;
        switch(t) {
            case "win" -> {
                try {
                    win = ImageIO.read(this.getClass().getResource("sprites/win.png"));
                } catch (Exception e) {
                    System.out.println("Failed to load image 'win'");
                }
            }

            case "gameOver" -> {
                try {
                    gameOver = ImageIO.read(this.getClass().getResource("sprites/gameOver.png"));
                } catch (Exception e) {
                    System.out.println("Failed to load image 'gameOver'");
                }
            }

            case "Intramural Champion" -> {
                try {
                    intramuralChampion = ImageIO.read(this.getClass().getResource("sprites/Intramural Champion.png"));
                } catch (Exception e) {
                    System.out.println("Failed to load image 'intramuralChampion'");
                }
            }

            case "game" -> {
                try {
                    court = ImageIO.read(this.getClass().getResource("sprites/court.png"));
                    level2 = ImageIO.read(this.getClass().getResource("sprites/level2.png"));
                    level3 = ImageIO.read(this.getClass().getResource("sprites/level3.png"));
                    level4 = ImageIO.read(this.getClass().getResource("sprites/level4.png"));
                    level5 = ImageIO.read(this.getClass().getResource("sprites/level5.png"));
                } catch (Exception e) {
                    System.out.println("Failed to load image in game");
                }
            }

            case "help" -> {
                try {
                    helpBg = ImageIO.read(this.getClass().getResource("sprites/help.png"));
                } catch (Exception e) {
                    System.out.println("Failed to load image 'helpBg'");
                }
            }

            case "character select" -> {
                try {
                    characterSelectBg = ImageIO.read(this.getClass().getResource("sprites/characterSelect.png"));
                    select = ImageIO.read(this.getClass().getResource("sprites/select.png"));
                } catch (Exception e) {
                    System.out.println("Failed to load image in character select");
                }
            }

            case "title" -> {
                try {
                    logo = ImageIO.read(this.getClass().getResource("sprites/logo.png"));
                    titleScreen = ImageIO.read(this.getClass().getResource("sprites/title.png"));
                    titleStart = ImageIO.read(this.getClass().getResource("sprites/titleStart.png"));
                    titleHelp = ImageIO.read(this.getClass().getResource("sprites/titleHelp.png"));
                    titleExit = ImageIO.read(this.getClass().getResource("sprites/titleExit.png"));
                } catch (Exception e) {
                    System.out.println("Failed to load image in title");
                }
            }
        }
    }
    
    /**
     * Updates the values that the GamePanel knows to align with those of the Main class
     * @param m  The Main class object that it will take values from
     */
    public void updateValues(Main m) {
        this.main = m;
    }

    /**
     * Paints the frame
     * @param g  The graphics object to be painted
     */
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

            case "Intramural Champion" -> {
                g2.drawImage(intramuralChampion, 0, 0, (int)WINW, (int)WINH, null);
            }
            case "game" -> {
                switch (Main.currentLevel) {
                    case level1 -> {
                        g2.setColor(new Color(100, 100, 100));
                        g2.fillRect(0, 0, (int)this.WINW, (int)this.WINH);
                        g2.drawImage(court, 0, 0, (int)this.WINW, (int)this.WINH, null);
                    }

                    case level2 -> {
                        for (int i = 0; i < GamePanel.WINW; i += 64) {
                            for (int j = 0; j < GamePanel.WINH; j += 64) {
                                g2.drawImage(level2, i, j, 64, 64, null);
                            }
                        }
                        g2.drawImage(court, 0, 0, (int)this.WINW, (int)this.WINH, null);

                    }

                    case level3 -> {
                        for (int i = 0; i < GamePanel.WINW; i += 64) {
                            for (int j = 0; j < GamePanel.WINH; j += 64) {
                                g2.drawImage(level3, i, j, 64, 64, null);
                            }
                        }
                        g2.drawImage(court, 0, 0, (int)this.WINW, (int)this.WINH, null);
                    }

                    case level4 -> {
                        for (int i = 0; i < GamePanel.WINW; i += 64) {
                            for (int j = 0; j < GamePanel.WINH; j += 64) {
                                g2.drawImage(level4, i, j, 64, 64, null);
                            }
                        }
                        g2.drawImage(court, 0, 0, (int)this.WINW, (int)this.WINH, null);
                    }

                    case level5 -> {
                        for (int i = 0; i < GamePanel.WINW; i += 64) {
                            for (int j = 0; j < GamePanel.WINH; j += 64) {
                                g2.drawImage(level5, i, j, 64, 64, null);
                            }
                        }
                        g2.drawImage(court, 0, 0, (int)this.WINW, (int)this.WINH, null);
                    }

                    default -> {}
                    
                }
                g2.drawImage(court, 0, 0, (int)(int)WINW, (int)(int)WINH, null);
                if (main.getTimeSlowed()) drawTimeSlowVignette(g2);
                drawCooldown(g2, 70, 500, 50, Color.BLUE);

                animateCharacters(g2);
                
                for (Enemy e: main.getEnemies()) {
                    if (e.getActive()) {
                        drawEntity(g2, e, Color.BLACK);
                    }
                }
                for (BallShadow b: main.getBallShadows()) {
                    if (b.getActive()) {
                        drawEntity(g2, b, Color.BLACK);
                    }
                }
                for (Ball b: main.getBalls()) {
                    if (b.getShadow().getActive()) {
                        drawEntity(g2, b, Color.BLUE);
                    }
                }

                //drawDebugStuff(g2);
                g2.setFont(new Font("Calibri", Font.PLAIN, (int)(this.WINW*0.035)));
                g2.setColor(Color.BLACK);
                g2.drawString("Enemy: " + main.enemyScore,(int)(this.WINW*0.0653), (int)(this.WINH*0.153));
                g2.drawString("Player: " + main.playerScore,(int)(this.WINW*0.802), (int)(this.WINH*0.902));
                g2.setColor(new Color(255,69,169));
                g2.drawString("Enemy: " + main.enemyScore,(int)(this.WINW*0.065), (int)(this.WINH*0.15));
                g2.drawString("Player: " + main.playerScore,(int)(this.WINW*0.80), (int)(this.WINH*0.90));
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

    /**
     * Draws a time slow vignette around the sides of the screen
     * @param g2  The Graphics2D objects to be manipulated
     */
    private void drawTimeSlowVignette(Graphics2D g2) {
        double opacity;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        if (main.getPlayer().getAbilityTime() < 20) {
            opacity = main.getPlayer().getAbilityTime() * 0.05;
        } else if (main.getPlayer().getAbilityTime() < 80) {
            opacity = 1;
        } else {
            opacity = (100 - main.getPlayer().getAbilityTime()) * 0.05;
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

    /**
     * Draws the Entity e on the Graphics2D Object g2 as a square with the colour c
     * @param g2  The Graphics2D object to be manipulated
     * @param e   The entity to be drawn
     * @param c   The colour to draw the Entity in
     */
    private void drawEntity(Graphics2D g2, Entity e, Color c) {
        g2.setColor(c);
        g2.fillRect(e.x, e.y, e.size, e.size);
    }

    /**
     * Draws the ability cooldown of the main class as a circle with a certain progress, with the centre at (x, y), a radius of radius, and the color c on the Graphics2D object
     * @param g2      The Graphics2D object to be manipulated
     * @param x       The x coordinate of the centre
     * @param y       The y coordinate of the centre
     * @param radius  The radius of the cooldown circle
     * @param c       The colour for the circle to be drawn in
     */
    private void drawCooldown(Graphics2D g2, int x, int y, double radius, Color c) {
        double totalCooldown;
        int cooldownLeft = (int)System.currentTimeMillis() - (int)main.getPlayer().getLastAbilityTime();
        totalCooldown = switch (main.getPlayer().getAbility()) {
            case riso -> main.getPlayer().getDashCooldown();
            case adonis -> main.getPlayer().getStrongHitCooldown();
            case tasha -> main.getPlayer().getTimeSlowCooldown();
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

    /**
     * Animates the characters
     * @param g2  The Graphics2D object to be manipulated
     */
    private void animateCharacters(Graphics2D g2) {
        if (main.getCharacterModel() == 1) {
            switch (main.getPlayer().currentState) {
                case idle_right:
                    main.getPlayer().risoIdleRightAnim.paintIcon(this, g2, main.getPlayer().x, main.getPlayer().y);
                    break;
                case move_right:
                    main.getPlayer().risoMoveRightAnim.paintIcon(this, g2, main.getPlayer().x, main.getPlayer().y);
                    break;
                case move_down:
                    //player.risoMoveDownAnim.paintIcon(this, g2, player.x, player.y);
                    break;
                case move_left:
                    //player.risoMoveLeftAnim.paintIcon(this, g2, player.x, player.y);
                    break;
                case move_up:
                    //player.risoMoveUpAnim.paintIcon(this, g2, player.x, player.y);
                    break;
                case hit:
                    main.getPlayer().risoHitAnim.paintIcon(this, g2, main.getPlayer().x, main.getPlayer().y);
                    
                    break;
            }
        }
    }
        
    /**
     * draws the destination and departure point of the ball (for debugging)
     * @param g2  The Graphics2D object to be manipulated
     */
    private void drawDebugStuff(Graphics2D g2) {
        g2.setStroke(new BasicStroke(10));
        for (BallShadow b: main.getBallShadows()) {
            g2.setColor(Color.RED);
            g2.drawLine((int)b.getDestinationX(), (int)b.getDestinationY(), (int)b.getDestinationX(), (int)b.getDestinationY());
            g2.setColor(Color.GREEN);
            g2.drawLine((int)b.getDepartureX(), (int)b.getDepartureY(), (int)b.getDepartureX(), (int)b.getDepartureY());
        }
    }
}
   