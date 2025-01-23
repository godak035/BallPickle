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

    private BufferedImage
        logo,
        titleScreen, titleStart, titleHelp, titleExit, titleExtras,
        helpBg,
        characterSelectBg, select,
        court, level2, level3, level4, level5,
        gameOver,
        win,
        intramuralChampion,
        riso_right, riso_left, riso_up, riso_down, riso_idle, riso_hit, 
        adonis_right, adonis_left, adonis_up, adonis_down, adonis_idle, adonis_hit, 
        tasha_right, tasha_left, tasha_up, tasha_down, tasha_idle, tasha_hit,
        hercules_right, hercules_left, hercules_up, hercules_down, hercules_idle, hercules_hit_right, hercules_hit_left,
        joe_right, joe_left, joe_up, joe_down, joe_idle, joe_hit_right, joe_hit_left,
        grady1_right, grady1_left, grady1_up, grady1_down, grady1_idle, grady1_hit_right, grady1_hit_left,
        grady2_right, grady2_left, grady2_up, grady2_down, grady2_idle, grady2_hit_right, grady2_hit_left,
        walter_right, walter_left, walter_up, walter_down, walter_idle, walter_hit,
        sicilia_right, sicilia_left, sicilia_up, sicilia_down, sicilia_idle, sicilia_hit_right, sicilia_hit_left,
        ball_anim,
        extras,
        clock, dash, paddle;
        
    /**
     * Constructor
     * @param t  The type of GamePanel
     */
    GamePanel(String t) {
        type = t;
        switch(t) {
            case "win" -> {
                try {
                    win = ImageIO.read(this.getClass().getResource("sprites/GUI/win.png"));
                } catch (Exception e) {
                    System.out.println("Failed to load image 'win'");
                }
            }

            case "gameOver" -> {
                try {
                    gameOver = ImageIO.read(this.getClass().getResource("sprites/GUI/gameOver.png"));
                } catch (Exception e) {
                    System.out.println("Failed to load image 'gameOver'");
                }
            }

            case "Intramural Champion" -> {
                try {
                    intramuralChampion = ImageIO.read(this.getClass().getResource("sprites/GUI/Intramural Champion.png"));
                } catch (Exception e) {
                    System.out.println("Failed to load image 'intramuralChampion'");
                }
            }

            case "game" -> {
                try {
                    court = ImageIO.read(this.getClass().getResource("sprites/game/court.png"));
                    level2 = ImageIO.read(this.getClass().getResource("sprites/game/level2.png"));
                    level3 = ImageIO.read(this.getClass().getResource("sprites/game/level3.png"));
                    level4 = ImageIO.read(this.getClass().getResource("sprites/game/level4.png"));
                    level5 = ImageIO.read(this.getClass().getResource("sprites/game/level5.png"));

                    riso_right = ImageIO.read(this.getClass().getResource("sprites/players/riso_move_right.png"));
                    riso_left = ImageIO.read(this.getClass().getResource("sprites/players/riso_move_left.png"));
                    riso_up = ImageIO.read(this.getClass().getResource("sprites/players/riso_move_up.png"));
                    riso_down = ImageIO.read(this.getClass().getResource("sprites/players/riso_move_down.png"));
                    riso_hit = ImageIO.read(this.getClass().getResource("sprites/players/riso_hit.png"));
                    riso_idle = ImageIO.read(this.getClass().getResource("sprites/players/riso_idle.png"));

                    adonis_right = ImageIO.read(this.getClass().getResource("sprites/players/adonis_move_right.png"));
                    adonis_left = ImageIO.read(this.getClass().getResource("sprites/players/adonis_move_left.png"));
                    adonis_up = ImageIO.read(this.getClass().getResource("sprites/players/adonis_move_up.png"));
                    adonis_down = ImageIO.read(this.getClass().getResource("sprites/players/adonis_move_down.png"));
                    adonis_hit = ImageIO.read(this.getClass().getResource("sprites/players/adonis_hit.png"));
                    adonis_idle = ImageIO.read(this.getClass().getResource("sprites/players/adonis_idle.png"));

                    tasha_right = ImageIO.read(this.getClass().getResource("sprites/players/tasha_move_right.png"));
                    tasha_left = ImageIO.read(this.getClass().getResource("sprites/players/tasha_move_left.png"));
                    tasha_up = ImageIO.read(this.getClass().getResource("sprites/players/tasha_move_up.png"));
                    tasha_down = ImageIO.read(this.getClass().getResource("sprites/players/tasha_move_down.png"));
                    tasha_hit = ImageIO.read(this.getClass().getResource("sprites/players/tasha_hit.png"));
                    tasha_idle = ImageIO.read(this.getClass().getResource("sprites/players/tasha_idle.png"));

                    hercules_right = ImageIO.read(this.getClass().getResource("sprites/enemies/hercules_move_right.png"));
                    hercules_left = ImageIO.read(this.getClass().getResource("sprites/enemies/hercules_move_left.png"));
                    hercules_up = ImageIO.read(this.getClass().getResource("sprites/enemies/hercules_move_up.png"));
                    hercules_down = ImageIO.read(this.getClass().getResource("sprites/enemies/hercules_move_down.png"));
                    hercules_hit_left = ImageIO.read(this.getClass().getResource("sprites/enemies/hercules_hit_left.png"));
                    hercules_hit_right = ImageIO.read(this.getClass().getResource("sprites/enemies/hercules_hit_right.png"));
                    hercules_idle = ImageIO.read(this.getClass().getResource("sprites/enemies/hercules_idle.png"));

                    walter_right = ImageIO.read(this.getClass().getResource("sprites/enemies/walter_move_right.png"));
                    walter_left = ImageIO.read(this.getClass().getResource("sprites/enemies/walter_move_left.png"));
                    walter_up = ImageIO.read(this.getClass().getResource("sprites/enemies/walter_move_up.png"));
                    walter_down = ImageIO.read(this.getClass().getResource("sprites/enemies/walter_move_down.png"));
                    walter_hit = ImageIO.read(this.getClass().getResource("sprites/enemies/walter_hit.png"));
                    walter_idle = ImageIO.read(this.getClass().getResource("sprites/enemies/walter_idle.png"));

                    grady1_right = ImageIO.read(this.getClass().getResource("sprites/enemies/grady1_move_right.png"));
                    grady1_left = ImageIO.read(this.getClass().getResource("sprites/enemies/grady1_move_left.png"));
                    grady1_up = ImageIO.read(this.getClass().getResource("sprites/enemies/grady1_move_up.png"));
                    grady1_down = ImageIO.read(this.getClass().getResource("sprites/enemies/grady1_move_down.png"));
                    grady1_idle = ImageIO.read(this.getClass().getResource("sprites/enemies/grady1_idle.png"));
                    grady1_hit_right = ImageIO.read(this.getClass().getResource("sprites/enemies/grady1_hit_right.png"));
                    grady1_hit_left = ImageIO.read(this.getClass().getResource("sprites/enemies/grady1_move_left.png"));

                    grady2_right = ImageIO.read(this.getClass().getResource("sprites/enemies/grady2_move_right.png"));
                    grady2_left = ImageIO.read(this.getClass().getResource("sprites/enemies/grady2_move_left.png"));
                    grady2_up = ImageIO.read(this.getClass().getResource("sprites/enemies/grady2_move_up.png"));
                    grady2_down = ImageIO.read(this.getClass().getResource("sprites/enemies/grady2_move_down.png"));
                    grady2_idle = ImageIO.read(this.getClass().getResource("sprites/enemies/grady2_idle.png"));
                    grady2_hit_right = ImageIO.read(this.getClass().getResource("sprites/enemies/grady2_hit_right.png"));
                    grady2_hit_left = ImageIO.read(this.getClass().getResource("sprites/enemies/grady2_move_left.png"));

                    joe_right = ImageIO.read(this.getClass().getResource("sprites/enemies/joe_move_right.png"));
                    joe_left = ImageIO.read(this.getClass().getResource("sprites/enemies/joe_move_left.png"));
                    joe_up = ImageIO.read(this.getClass().getResource("sprites/enemies/joe_move_up.png"));
                    joe_down = ImageIO.read(this.getClass().getResource("sprites/enemies/joe_move_down.png"));
                    joe_hit_left = ImageIO.read(this.getClass().getResource("sprites/enemies/joe_hit_left.png"));
                    joe_hit_right = ImageIO.read(this.getClass().getResource("sprites/enemies/joe_hit_right.png"));
                    joe_idle = ImageIO.read(this.getClass().getResource("sprites/enemies/joe_idle.png"));

                    sicilia_right = ImageIO.read(this.getClass().getResource("sprites/enemies/sicilia_move_right.png"));
                    sicilia_left = ImageIO.read(this.getClass().getResource("sprites/enemies/sicilia_move_left.png"));
                    sicilia_up = ImageIO.read(this.getClass().getResource("sprites/enemies/sicilia_move_up.png"));
                    sicilia_down = ImageIO.read(this.getClass().getResource("sprites/enemies/sicilia_move_down.png"));
                    sicilia_hit_left = ImageIO.read(this.getClass().getResource("sprites/enemies/sicilia_hit_left.png"));
                    sicilia_hit_right = ImageIO.read(this.getClass().getResource("sprites/enemies/sicilia_hit_right.png"));
                    sicilia_idle = ImageIO.read(this.getClass().getResource("sprites/enemies/sicilia_idle.png"));

                    ball_anim = ImageIO.read(this.getClass().getResource("sprites/players/ball_sprite_sheet.png"));

                    clock = ImageIO.read(this.getClass().getResource("sprites/GUI/clock.png"));
                    dash = ImageIO.read(this.getClass().getResource("sprites/GUI/dash.png"));
                    paddle = ImageIO.read(this.getClass().getResource("sprites/GUI/paddle.png"));
                } catch (Exception e) {
                    System.out.println("Failed to load image in game");
                }
            }

            case "extras" -> {
                try {
                    extras = ImageIO.read(this.getClass().getResource("sprites/GUI/extras.png"));
                } catch (Exception e) {
                    System.out.println("Failed to load image 'extras'");
                }
            }

            case "help" -> {
                try {
                    helpBg = ImageIO.read(this.getClass().getResource("sprites/GUI/help.png"));
                } catch (Exception e) {
                    System.out.println("Failed to load image 'helpBg'");
                }
            }

            case "character select" -> {
                try {
                    characterSelectBg = ImageIO.read(this.getClass().getResource("sprites/GUI/characterSelect.png"));
                    select = ImageIO.read(this.getClass().getResource("sprites/GUI/select.png"));
                } catch (Exception e) {
                    System.out.println("Failed to load image in character select");
                }
            }

            case "title" -> {
                try {
                    logo = ImageIO.read(this.getClass().getResource("sprites/GUI/logo.png"));
                    titleScreen = ImageIO.read(this.getClass().getResource("sprites/GUI/title.png"));
                    titleStart = ImageIO.read(this.getClass().getResource("sprites/GUI/titleStart.png"));
                    titleHelp = ImageIO.read(this.getClass().getResource("sprites/GUI/titleHelp.png"));
                    titleExit = ImageIO.read(this.getClass().getResource("sprites/GUI/titleExit.png"));
                    titleExtras = ImageIO.read(this.getClass().getResource("sprites/GUI/titleExtras.png"));
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

            case "extras" -> {
                g2.drawImage(extras, 0, 0, (int)WINW, (int)WINH, null);
            }

            case "game" -> {
                switch (Main.getCurrentLevel()) {
                    case 1 -> {
                        g2.setColor(new Color(100, 100, 100));
                        g2.fillRect(0, 0, (int)GamePanel.WINW, (int)GamePanel.WINH);
                        g2.drawImage(court, 0, 0, (int)GamePanel.WINW, (int)GamePanel.WINH, null);
                    }

                    case 2 -> {
                        for (int i = 0; i < GamePanel.WINW; i += 64) {
                            for (int j = 0; j < GamePanel.WINH; j += 64) {
                                g2.drawImage(level2, i, j, 64, 64, null);
                            }
                        }
                        g2.drawImage(court, 0, 0, (int)GamePanel.WINW, (int)GamePanel.WINH, null);

                    }

                    case 3 -> {
                        for (int i = 0; i < GamePanel.WINW; i += 64) {
                            for (int j = 0; j < GamePanel.WINH; j += 64) {
                                g2.drawImage(level3, i, j, 64, 64, null);
                            }
                        }
                        g2.drawImage(court, 0, 0, (int)GamePanel.WINW, (int)GamePanel.WINH, null);
                    }

                    case 4 -> {
                        for (int i = 0; i < GamePanel.WINW; i += 64) {
                            for (int j = 0; j < GamePanel.WINH; j += 64) {
                                g2.drawImage(level4, i, j, 64, 64, null);
                            }
                        }
                        g2.drawImage(court, 0, 0, (int)GamePanel.WINW, (int)GamePanel.WINH, null);
                    }

                    case 5 -> {
                        for (int i = 0; i < GamePanel.WINW; i += 64) {
                            for (int j = 0; j < GamePanel.WINH; j += 64) {
                                g2.drawImage(level5, i, j, 64, 64, null);
                            }
                        }
                        g2.drawImage(court, 0, 0, (int)GamePanel.WINW, (int)GamePanel.WINH, null);
                    }

                    default -> {}
                }
                g2.drawImage(court, 0, 0, (int)GamePanel.WINW, (int)GamePanel.WINH, null);
                drawCooldown(g2, (int)(GamePanel.WINH * 0.15) + 4, (int)(GamePanel.WINH * 0.85) + 4, (int)(GamePanel.WINH * 0.1), Color.BLACK);
                drawCooldown(g2, (int)(GamePanel.WINH * 0.15), (int)(GamePanel.WINH * 0.85), (int)(GamePanel.WINH * 0.1), new Color(255,69,169));
                
                switch (main.getPlayer().getAbility()) {
                    case riso -> g2.drawImage(dash, (int)(GamePanel.WINH * 0.06), (int)(GamePanel.WINH * 0.76), (int)(GamePanel.WINH * 0.18), (int)(GamePanel.WINH * 0.18), null);
                    case adonis -> g2.drawImage(paddle, (int)(GamePanel.WINH * 0.05), (int)(GamePanel.WINH * 0.76), (int)(GamePanel.WINH * 0.19), (int)(GamePanel.WINH * 0.16), null);
                    case tasha -> g2.drawImage(clock, (int)(GamePanel.WINH * 0.02), (int)(GamePanel.WINH * 0.715), (int)(GamePanel.WINH * 0.26), (int)(GamePanel.WINH * 0.26), null);
                }

                int cooldownLeft = (int)System.currentTimeMillis() - (int)main.getPlayer().getLastAbilityTime();
                int totalCooldown = switch (main.getPlayer().getAbility()) {
                    case riso -> main.getPlayer().getDashCooldown();
                    case adonis -> main.getPlayer().getStrongHitCooldown();
                    case tasha -> main.getPlayer().getTimeSlowCooldown();
                    default -> 0;
                };
                if (cooldownLeft > totalCooldown) {
                    g2.setFont(new Font("Calibri", Font.PLAIN, (int)(GamePanel.WINW*0.03)));
                    g2.setColor(Color.BLACK);
                    g2.drawString("Ability ready!", (int)(GamePanel.WINH * 0.023) + 4, (int)(GamePanel.WINH * 0.72) + 4);
                    g2.setColor(new Color(255,69,169));
                    g2.drawString("Ability ready!", (int)(GamePanel.WINH * 0.023), (int)(GamePanel.WINH * 0.72));
                }
                for (Enemy e: main.getEnemies()) {
                    if (e.getActive()) {
                        drawEnemy(g2, e);
                    }
                }
                for (BallShadow b: main.getBallShadows()) {
                    if (b.getActive()) {
                        drawBallShadow(g2, b);
                    }
                }
                for (Ball b: main.getBalls()) {
                    if (b.getShadow().getActive()) {
                        drawBall(g2, b);
                    }
                }
                drawPlayer(g2);
                if (main.getTimeSlowed()) drawTimeSlowVignette(g2);

                //drawDebugStuff(g2);
                g2.setFont(new Font("Calibri", Font.PLAIN, (int)(GamePanel.WINW*0.035)));
                g2.setColor(Color.BLACK);
                g2.drawString("Enemy: " + Main.enemyScore,(int)(GamePanel.WINW*0.065) + 4, (int)(GamePanel.WINH*0.15) + 4);
                g2.drawString("Player: " + Main.playerScore,(int)(GamePanel.WINW*0.80) + 4, (int)(GamePanel.WINH*0.90) + 4);
                g2.setColor(new Color(255,69,169));
                g2.drawString("Enemy: " + Main.enemyScore,(int)(GamePanel.WINW*0.065), (int)(GamePanel.WINH*0.15));
                g2.drawString("Player: " + Main.playerScore,(int)(GamePanel.WINW*0.80), (int)(GamePanel.WINH*0.90));
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
                if (Main.getFrames() < 127) {
                    g2.drawImage(logo, 0, 0, (int)WINW, (int)WINH,  null);
                    g2.setColor(new Color(0, 0, 0, 255 - (Main.getFrames() * 2)));
                    g2.fillRect(0, 0, (int)(int)WINW, (int)WINH);
                } else if (Main.getFrames() < 254) {
                    g2.drawImage(logo, 0, 0, (int)WINW, (int)WINH,  null);
                    g2.setColor(new Color(0, 0, 0, (Main.getFrames() - 127) * 2));
                    g2.fillRect(0, 0, (int)WINW, (int)WINH);
                } else if (Main.getFrames() < 300) {
                    g2.setColor(new Color(0, 0, 0));
                    g2.fillRect(0, 0, (int)WINW, (int)WINH);
                } else if (Main.getFrames() < 363) {
                    g2.drawImage(titleScreen, 0, 0, (int)WINW, (int)WINH,  null);
                    g2.setColor(new Color(0, 0, 0, 255 - ((Main.getFrames() - 300) * 4)));
                    g2.fillRect(0, 0, (int)WINW, (int)WINH);
                } else {
                    g2.drawImage(titleScreen, 0, 0, (int)WINW, (int)WINH,  null);
                    switch (Main.currentHovered) {
                        case titleStart -> g2.drawImage(titleStart, 0, 0, (int)WINW, (int)WINH,  null);
                        case titleExit -> g2.drawImage(titleExit, 0, 0, (int)WINW, (int)WINH, null);
                        case titleHelp -> g2.drawImage(titleHelp, 0, 0, (int)WINW, (int)WINH, null);
                        case titleExtras -> g2.drawImage(titleExtras, 0, 0, (int)WINW, (int)WINH, null);
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
    * Draws the ball, with its animations.
    * @param g2  The Graphics2D object to be manipulated
    */
    private void drawBall(Graphics2D g2, Ball b) {
        int ballX = (int)(b.getXX() - GamePanel.WINH * 0.01);
        int ballY = (int)(b.getYY() - GamePanel.WINH * 0.01);
        int ballX2 = (int)(b.getXX() + b.getSize() + GamePanel.WINH * 0.01);
        int ballY2 = (int)(b.getYY() + b.getSize() + GamePanel.WINH * 0.01);

        if (main.getBallShadow().getActive()) {
            int sprite = (Main.getFrames() % 25) / 5;
            g2.drawImage(ball_anim, ballX, ballY, ballX2, ballY2, sprite * 64, 0, (sprite * 64) + 64, 64, null);
        }
    }

    /**
     * Draws the player
     * @param g2  The Graphics2D object to be manipulated
     */
    private void drawPlayer(Graphics2D g2) {
        //The top left corner of the player, in pixels
        int playerX = (int)(main.getPlayer().getXX() + main.getPlayer().getPositionXRelativeTo());
        int playerY = (int)(main.getPlayer().getYY() + main.getPlayer().getPositionYRelativeTo());
        //the bottom right corner of the player, in pixels
        int playerX2 = (int)(main.getPlayer().getXX() + main.getPlayer().getPositionXRelativeTo() + main.getPlayer().getSize());
        int playerY2 = (int)(main.getPlayer().getYY() + main.getPlayer().getPositionYRelativeTo() + main.getPlayer().getSize());

        BufferedImage imageToDraw;
        int sprite;
        if (Main.getFrames() - main.getLastHit() < 25) sprite = (Main.getFrames() - main.getLastHit()) / 5;
        else if (!main.getRightPressedThisTick() && !main.getLeftPressedThisTick() && !main.getUpPressedThisTick() && !main.getDownPressedThisTick()) sprite = (Main.getFrames() % 20) / 5;
        else sprite = (Main.getFrames() % 15) / 5;

        if (main.getPlayer().getAbility() == Player.abilityChoices.riso) {
            if (Main.getFrames() - main.getLastHit() < 25) imageToDraw = riso_hit;
            else if (main.getRightPressedThisTick()) imageToDraw = riso_right;
            else if (main.getLeftPressedThisTick()) imageToDraw = riso_left;
            else if (main.getUpPressedThisTick()) imageToDraw = riso_up;
            else if (main.getDownPressedThisTick()) imageToDraw = riso_down;
            else imageToDraw = riso_idle;
        } else if (main.getPlayer().getAbility() == Player.abilityChoices.adonis) {
            if (Main.getFrames() - main.getLastHit() < 25) imageToDraw = adonis_hit;
            else if (main.getRightPressedThisTick()) imageToDraw = adonis_right;
            else if (main.getLeftPressedThisTick()) imageToDraw = adonis_left;
            else if (main.getUpPressedThisTick()) imageToDraw = adonis_up;
            else if (main.getDownPressedThisTick()) imageToDraw = adonis_down;
            else imageToDraw = adonis_idle;
        } else if (main.getPlayer().getAbility() == Player.abilityChoices.tasha) {
            if (Main.getFrames() - main.getLastHit() < 25) imageToDraw = tasha_hit;
            else if (main.getRightPressedThisTick()) imageToDraw = tasha_right;
            else if (main.getLeftPressedThisTick()) imageToDraw = tasha_left;
            else if (main.getUpPressedThisTick()) imageToDraw = tasha_up;
            else if (main.getDownPressedThisTick()) imageToDraw = tasha_down;
            else imageToDraw = adonis_idle;
        } else imageToDraw = riso_idle;
        g2.drawImage(imageToDraw, playerX, playerY, playerX2, playerY2, sprite * 128, 0, (sprite * 128) + 128, 128, null);
    }

    /**
     * Draws the enemy
     * @param g2  The Graphics2D object to be manipilated
     * @param e   Tne enemy to be drawn
     */
    private void drawEnemy(Graphics2D g2, Enemy e) {
        //the top left corner of the enemy, in pixels
        int enemyX = (int)e.getXX();
        int enemyY = (int)e.getYY();
        //the bottom right corner of the enemy, in pixels
        int enemyX2 = (int)e.getXX() + e.getSize();
        int enemyY2 = (int)e.getYY() + e.getSize();

        BufferedImage imageToDraw;
        int sprite;
        if ((Main.getFrames() - main.getEnemies().get(0).getLastHit() > 25) && (e.getXX() == e.getDestinationX()) && (e.getYY() == e.getDestinationY())) sprite = (Main.getFrames() % 20) / 5;
        else sprite = (Main.getFrames() % 15) / 5;

        switch (e.getType()) {
            case AverageJoe -> {
                if ((Main.getFrames() - main.getEnemies().get(0).getLastHit() < 25) && (e.getXX() < e.getDestinationX() - (e.getSize() / 2))) imageToDraw = joe_hit_right;
                else if ((Main.getFrames() - main.getEnemies().get(0).getLastHit() < 25) && (e.getXX() >= e.getDestinationX() - (e.getSize() / 2))) imageToDraw = joe_hit_left;
                else if (e.getXX() < e.getDestinationX() - (e.getSize() / 2)) imageToDraw = joe_right;
                else if (e.getXX() > e.getDestinationX() - (e.getSize() / 2)) imageToDraw = joe_left;
                else if (e.getYY() < e.getDestinationY() - (e.getSize() / 2)) imageToDraw = joe_up;
                else if (e.getYY() > e.getDestinationY() - (e.getSize() / 2)) imageToDraw = joe_down;
                else imageToDraw = joe_idle;
            }
            case StrongHercules -> {
                if ((Main.getFrames() - main.getEnemies().get(0).getLastHit() < 25) && (e.getXX() < e.getDestinationX() - (e.getSize() / 2))) imageToDraw = hercules_hit_right;
                else if ((Main.getFrames() - main.getEnemies().get(0).getLastHit() < 25) && (e.getXX() >= e.getDestinationX() - (e.getSize() / 2))) imageToDraw = hercules_hit_left;
                else if (e.getXX() < e.getDestinationX() - (e.getSize() / 2)) imageToDraw = hercules_right;
                else if (e.getXX() > e.getDestinationX() - (e.getSize() / 2)) imageToDraw = hercules_left;
                else if (e.getYY() < e.getDestinationY() - (e.getSize() / 2)) imageToDraw = hercules_up;
                else if (e.getYY() > e.getDestinationY() - (e.getSize() / 2)) imageToDraw = hercules_down;
                else imageToDraw = hercules_idle;
            }
            case GradyTwin1 -> {
                if ((Main.getFrames() - main.getEnemies().get(0).getLastHit() < 25) && (e.getXX() < e.getDestinationX() - (e.getSize() / 2))) imageToDraw = grady1_hit_right;
                else if ((Main.getFrames() - main.getEnemies().get(0).getLastHit() < 25) && (e.getXX() >= e.getDestinationX() - (e.getSize() / 2))) imageToDraw = grady1_hit_left;
                else if (e.getXX() < e.getDestinationX() - (e.getSize() / 2)) imageToDraw = grady1_right;
                else if (e.getXX() > e.getDestinationX() - (e.getSize() / 2)) imageToDraw = grady1_left;
                else if (e.getYY() < e.getDestinationY() - (e.getSize() / 2)) imageToDraw = grady1_up;
                else if (e.getYY() > e.getDestinationY() - (e.getSize() / 2)) imageToDraw = grady1_down;
                else imageToDraw = grady1_idle;
            }
            case GradyTwin2 -> {
                if ((Main.getFrames() - main.getEnemies().get(0).getLastHit() < 25) && (e.getXX() < e.getDestinationX() - (e.getSize() / 2))) imageToDraw = grady2_hit_right;
                else if ((Main.getFrames() - main.getEnemies().get(0).getLastHit() < 25) && (e.getXX() >= e.getDestinationX() - (e.getSize() / 2))) imageToDraw = grady2_hit_left;
                else if (e.getXX() < e.getDestinationX() - (e.getSize() / 2)) imageToDraw = grady2_right;
                else if (e.getXX() > e.getDestinationX() - (e.getSize() / 2)) imageToDraw = grady2_left;
                else if (e.getYY() < e.getDestinationY() - (e.getSize() / 2)) imageToDraw = grady2_up;
                else if (e.getYY() > e.getDestinationY() - (e.getSize() / 2)) imageToDraw = grady2_down;
                else imageToDraw = grady2_idle;
            }
            case TwoBallWalter -> {
                if (Main.getFrames() - main.getEnemies().get(0).getLastHit() < 25) imageToDraw = walter_hit;
                else if (e.getXX() < e.getDestinationX() - (e.getSize() / 2)) imageToDraw = walter_right;
                else if (e.getXX() > e.getDestinationX() - (e.getSize() / 2)) imageToDraw = walter_left;
                else if (e.getYY() < e.getDestinationY() - (e.getSize() / 2)) imageToDraw = walter_up;
                else if (e.getYY() > e.getDestinationY() - (e.getSize() / 2)) imageToDraw = walter_down;
                else imageToDraw = walter_idle;
            }
            case TeleportSicilia -> {
                if ((Main.getFrames() - main.getEnemies().get(0).getLastHit() < 25) && (e.getXX() < e.getDestinationX() - (e.getSize() / 2))) imageToDraw = sicilia_hit_right;
                else if ((Main.getFrames() - main.getEnemies().get(0).getLastHit() < 25) && (e.getXX() >= e.getDestinationX() - (e.getSize() / 2))) imageToDraw = sicilia_hit_left;
                else if (e.getXX() < e.getDestinationX() - (e.getSize() / 2)) imageToDraw = sicilia_right;
                else if (e.getXX() > e.getDestinationX() - (e.getSize() / 2)) imageToDraw = sicilia_left;
                else if (e.getYY() < e.getDestinationY() - (e.getSize() / 2)) imageToDraw = sicilia_up;
                else if (e.getYY() > e.getDestinationY() - (e.getSize() / 2)) imageToDraw = sicilia_down;
                else imageToDraw = sicilia_idle;
            }
            default -> imageToDraw = riso_idle;
        }
        g2.drawImage(imageToDraw, enemyX, enemyY, enemyX2, enemyY2, sprite * 128, 0, (sprite * 128) + 128, 128, null);
    }
    
    /**
     * Draws the ball's shadow
     * @param g2  The Graphics2D object to be manipulated
     * @param b   The BallShadow to be drawn
     */
    private void drawBallShadow(Graphics2D g2, BallShadow b) {
        g2.setColor(Color.BLACK);
        g2.fillOval(b.getX() - (int)(GamePanel.WINH * 0.0048), b.getY() + b.getSize(), b.getSize() + (int)(GamePanel.WINH * 0.01), b.getSize() / 2);
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