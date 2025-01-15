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

    BufferedImage
        logo,
        titleScreen, titleStart, titleHelp, titleExit,
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
        ball_anim,
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

                    joe_right = ImageIO.read(this.getClass().getResource("sprites/enemies/joe_move_right.png"));
                    joe_left = ImageIO.read(this.getClass().getResource("sprites/enemies/joe_move_left.png"));
                    joe_up = ImageIO.read(this.getClass().getResource("sprites/enemies/joe_move_up.png"));
                    joe_down = ImageIO.read(this.getClass().getResource("sprites/enemies/joe_move_down.png"));
                    joe_hit_left = ImageIO.read(this.getClass().getResource("sprites/enemies/joe_hit_left.png"));
                    joe_hit_right = ImageIO.read(this.getClass().getResource("sprites/enemies/joe_hit_right.png"));
                    joe_idle = ImageIO.read(this.getClass().getResource("sprites/enemies/joe_idle.png"));

                    ball_anim = ImageIO.read(this.getClass().getResource("sprites/players/ball_sprite_sheet.png"));

                    clock = ImageIO.read(this.getClass().getResource("sprites/GUI/clock.png"));
                    dash = ImageIO.read(this.getClass().getResource("sprites/GUI/dash.png"));
                    paddle = ImageIO.read(this.getClass().getResource("sprites/GUI/paddle.png"));
                } catch (Exception e) {
                    System.out.println("Failed to load image in game");
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
                        g2.fillRect(0, 0, (int)GamePanel.WINW, (int)GamePanel.WINH);
                        g2.drawImage(court, 0, 0, (int)GamePanel.WINW, (int)GamePanel.WINH, null);
                    }

                    case level2 -> {
                        for (int i = 0; i < GamePanel.WINW; i += 64) {
                            for (int j = 0; j < GamePanel.WINH; j += 64) {
                                g2.drawImage(level2, i, j, 64, 64, null);
                            }
                        }
                        g2.drawImage(court, 0, 0, (int)GamePanel.WINW, (int)GamePanel.WINH, null);

                    }

                    case level3 -> {
                        for (int i = 0; i < GamePanel.WINW; i += 64) {
                            for (int j = 0; j < GamePanel.WINH; j += 64) {
                                g2.drawImage(level3, i, j, 64, 64, null);
                            }
                        }
                        g2.drawImage(court, 0, 0, (int)GamePanel.WINW, (int)GamePanel.WINH, null);
                    }

                    case level4 -> {
                        for (int i = 0; i < GamePanel.WINW; i += 64) {
                            for (int j = 0; j < GamePanel.WINH; j += 64) {
                                g2.drawImage(level4, i, j, 64, 64, null);
                            }
                        }
                        g2.drawImage(court, 0, 0, (int)GamePanel.WINW, (int)GamePanel.WINH, null);
                    }

                    case level5 -> {
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
                        drawEntity(g2, e, Color.BLACK);
                    }
                }
                for (Enemy e: main.getEnemies()) {
                    if (e.getActive()) {
                        drawEnemy(g2, e);
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
                drawBall(g2);
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
    * Draws the ball, with its animations.
    * @param g2  The Graphics2D object to be manipulated
    */
    private void drawBall(Graphics2D g2) {
        int ballX = (int)(main.getBall().xx);
        int ballY = (int)(main.getBall().yy);
        int ballX2 = (int)(main.getBall().xx + main.getBall().size);
        int ballY2 = (int)(main.getBall().yy + main.getBall().size);

        if (main.getBallShadow().getActive()) {
            int sprite = (Main.frames % 25) / 5;
            g2.drawImage(ball_anim, ballX, ballY, ballX2, ballY2, sprite * 128, 0, (sprite * 128) + 128, 128, null);
        }
    }

    /**
     * Draws the player
     * @param g2  The Graphics2D object to be manipulated
     */
    private void drawPlayer(Graphics2D g2) {
        //The top left corner of the player, in pixels
        int playerX = (int)(main.getPlayer().xx + main.getPlayer().getPositionXRelativeTo());
        int playerY = (int)(main.getPlayer().yy + main.getPlayer().getPositionYRelativeTo());
        //the bottom right corner of the player, in pixels
        int playerX2 = (int)(main.getPlayer().xx + main.getPlayer().getPositionXRelativeTo() + main.getPlayer().size);
        int playerY2 = (int)(main.getPlayer().yy + main.getPlayer().getPositionYRelativeTo() + main.getPlayer().size);

        if (main.getPlayer().getAbility() == Player.abilityChoices.riso) {
            if (Main.frames - main.getLastHit() < 25) {
                int sprite = (Main.frames - main.getLastHit()) / 5;
                g2.drawImage(riso_hit, playerX, playerY, playerX2, playerY2, sprite * 128, 0, (sprite * 128) + 128, 128, null);
            } else if (main.getRightPressedThisTick()) {
                int sprite = (Main.frames % 15) / 5;
                g2.drawImage(riso_right, playerX, playerY, playerX2, playerY2, sprite * 128, 0, (sprite * 128) + 128, 128, null);
            } else if (main.getLeftPressedThisTick()) {
                int sprite = (Main.frames % 15) / 5;
                g2.drawImage(riso_left, playerX, playerY, playerX2, playerY2, sprite * 128, 0, (sprite * 128) + 128, 128, null);
            } else if (main.getUpPressedThisTick()) {
                int sprite = (Main.frames % 15) / 5;
                g2.drawImage(riso_up, playerX, playerY, playerX2, playerY2, sprite * 128, 0, (sprite * 128) + 128, 128, null);
            } else if (main.getDownPressedThisTick()) {
                int sprite = (Main.frames % 15) / 5;
                g2.drawImage(riso_down, playerX, playerY, playerX2, playerY2, sprite * 128, 0, (sprite * 128) + 128, 128, null);
            } else {
                int sprite = (Main.frames % 20) / 5;
                g2.drawImage(riso_idle, playerX, playerY, playerX2, playerY2, sprite * 128, 0, (sprite * 128) + 128, 128, null);
            }
        } else if (main.getPlayer().getAbility() == Player.abilityChoices.adonis) {
            if (Main.frames - main.getLastHit() < 25) {
                int sprite = (Main.frames - main.getLastHit()) / 5;
                g2.drawImage(adonis_hit, playerX, playerY, playerX2, playerY2, sprite * 128, 0, (sprite * 128) + 128, 128, null);
            } else if (main.getRightPressedThisTick()) {
                int sprite = (Main.frames % 15) / 5;
                g2.drawImage(adonis_right, playerX, playerY, playerX2, playerY2, sprite * 128, 0, (sprite * 128) + 128, 128, null);
            } else if (main.getLeftPressedThisTick()) {
                int sprite = (Main.frames % 15) / 5;
                g2.drawImage(adonis_left, playerX, playerY, playerX2, playerY2, sprite * 128, 0, (sprite * 128) + 128, 128, null);
            } else if (main.getUpPressedThisTick()) {
                int sprite = (Main.frames % 15) / 5;
                g2.drawImage(adonis_up, playerX, playerY, playerX2, playerY2, sprite * 128, 0, (sprite * 128) + 128, 128, null);
            } else if (main.getDownPressedThisTick()) {
                int sprite = (Main.frames % 15) / 5;
                g2.drawImage(adonis_down, playerX, playerY, playerX2, playerY2, sprite * 128, 0, (sprite * 128) + 128, 128, null);
            } else {
                int sprite = (Main.frames % 20) / 5;
                g2.drawImage(adonis_idle, playerX, playerY, playerX2, playerY2, sprite * 128, 0, (sprite * 128) + 128, 128, null);
            }
        } else if (main.getPlayer().getAbility() == Player.abilityChoices.tasha) {
            if (Main.frames - main.getLastHit() < 25) {
                int sprite = (Main.frames - main.getLastHit()) / 5;
                g2.drawImage(tasha_hit, playerX, playerY, playerX2, playerY2, sprite * 128, 0, (sprite * 128) + 128, 128, null);
            } else if (main.getRightPressedThisTick()) {
                int sprite = (Main.frames % 15) / 5;
                g2.drawImage(tasha_right, playerX, playerY, playerX2, playerY2, sprite * 128, 0, (sprite * 128) + 128, 128, null);
            } else if (main.getLeftPressedThisTick()) {
                int sprite = (Main.frames % 15) / 5;
                g2.drawImage(tasha_left, playerX, playerY, playerX2, playerY2, sprite * 128, 0, (sprite * 128) + 128, 128, null);
            } else if (main.getUpPressedThisTick()) {
                int sprite = (Main.frames % 15) / 5;
                g2.drawImage(tasha_up, playerX, playerY, playerX2, playerY2, sprite * 128, 0, (sprite * 128) + 128, 128, null);
            } else if (main.getDownPressedThisTick()) {
                int sprite = (Main.frames % 15) / 5;
                g2.drawImage(tasha_down, playerX, playerY, playerX2, playerY2, sprite * 128, 0, (sprite * 128) + 128, 128, null);
            } else {
                int sprite = (Main.frames % 20) / 5;
                g2.drawImage(tasha_idle, playerX, playerY, playerX2, playerY2, sprite * 128, 0, (sprite * 128) + 128, 128, null);
            }
        }
    }

    /**
     * Draws the enemy
     * @param g2  The Graphics2D object to be manipilated
     * @param e   Tne enemy to be drawn
     */
    private void drawEnemy(Graphics2D g2, Enemy e) {
        //the top left corner of the enemy, in pixels
        int enemyX = (int)e.xx;
        int enemyY = (int)e.yy;
        //the bottom right corner of the enemy, in pixels
        int enemyX2 = (int)e.xx + e.size;
        int enemyY2 = (int)e.yy + e.size;

        //first, check hit stuff
        //im not gonna do it in this example, but lmk if you struggle with it, i can always help out

        //next, check where the enemy is in relation to it's destination to see where it's moving
        //need to subtract (e.size / 2) drom the destination for both the x and y since the destination is to the centre of the enemy
        //for idle you can just check if e.xx == e.getDestinationX() && e.yy == e.getDestinationY() since i made the enemy snap to its destination if it gets close
        

        if (Main.checkLevel == 1) {
            if (e.xx < e.getDestinationX() - (e.size / 2)) {
                int sprite = (Main.frames % 15) / 5;
                g2.drawImage(joe_right, enemyX, enemyY, enemyX2, enemyY2, sprite * 128, 0, (sprite * 128) + 128, 128, null);
            } else if (e.xx > e.getDestinationX() - (e.size / 2)) {
                int sprite = (Main.frames % 15) / 5;
                g2.drawImage(joe_left, enemyX, enemyY, enemyX2, enemyY2, sprite * 128, 0, (sprite * 128) + 128, 128, null);
            } else if (e.yy < e.getDestinationY() - (e.size / 2)) {
                int sprite = (Main.frames % 15) / 5;
                g2.drawImage(joe_up, enemyX, enemyY, enemyX2, enemyY2, sprite * 128, 0, (sprite * 128) + 128, 128, null);
            } else if (e.yy > e.getDestinationY() - (e.size / 2)) {
                int sprite = (Main.frames % 15) / 5;
                g2.drawImage(joe_down, enemyX, enemyY, enemyX2, enemyY2, sprite * 128, 0, (sprite * 128) + 128, 128, null);
            } else {
                int sprite = (Main.frames % 20) / 5;
                g2.drawImage(joe_idle, enemyX, enemyY, enemyX2, enemyY2, sprite * 128, 0, (sprite * 128) + 128, 128, null);
            }
        } else if (Main.checkLevel == 2) {
            if (e.xx < e.getDestinationX() - (e.size / 2)) {
                int sprite = (Main.frames % 15) / 5;
                g2.drawImage(hercules_right, enemyX, enemyY, enemyX2, enemyY2, sprite * 128, 0, (sprite * 128) + 128, 128, null);
            } else if (e.xx > e.getDestinationX() - (e.size / 2)) {
                int sprite = (Main.frames % 15) / 5;
                g2.drawImage(hercules_left, enemyX, enemyY, enemyX2, enemyY2, sprite * 128, 0, (sprite * 128) + 128, 128, null);
            } else if (e.yy < e.getDestinationY() - (e.size / 2)) {
                int sprite = (Main.frames % 15) / 5;
                g2.drawImage(hercules_up, enemyX, enemyY, enemyX2, enemyY2, sprite * 128, 0, (sprite * 128) + 128, 128, null);
            } else if (e.yy > e.getDestinationY() - (e.size / 2)) {
                int sprite = (Main.frames % 15) / 5;
                g2.drawImage(hercules_down, enemyX, enemyY, enemyX2, enemyY2, sprite * 128, 0, (sprite * 128) + 128, 128, null);
            } else {
                int sprite = (Main.frames % 20) / 5;
                g2.drawImage(hercules_idle, enemyX, enemyY, enemyX2, enemyY2, sprite * 128, 0, (sprite * 128) + 128, 128, null);
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