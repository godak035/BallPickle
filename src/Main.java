/***
 * Main.java
 * The main gameloop of the BallPickle game.
 * by: David Sue, Vadim Mironov, Avishan Ketheswaran and Owen McCarthy
 * December 2, 2024
 */

import java.awt.*;
import javax.swing.*;
import java.util.*;

public class Main implements Runnable {
    
    JFrame frame;
    //Different panels that are drawn on for the title screen, game screen, help screen and character select screen
    GamePanel title, inGame, help, characterSelect;

    //The current button that the user is hovering over (e.g. pressing enter will activate an input of that button)
    static enum hovered { charSelect1, charSelect2, charSelect3, titleStart, titleCharSelect, titleHelp, inGame, helpExit };
    static hovered currentHovered;
    static enum level {level1, level2, level3, level4, level5};
    static level currentLevel;

    //Whether or not the user has released the keys since they have pressed them (used in the menus, to ensure that you don't move down 2 options if you press the down key for 2 frames)
    boolean upPressedThisTick, downPressedThisTick, leftPressedThisTick, rightPressedThisTick, enterPressedThisTick;
    
    KeyHandler KeyH;

    Player player;
    BallShadow ballShadow;
    Ball ball;
    Enemy averageJoe, strongHercules, gradyTwin1, gradyTwin2, twoBallWalter, teleportSicilia;
    ArrayList<Enemy> enemies;

    static int playerScore, enemyScore;

    boolean lookRightLast, playerHitLast, timeSlowed, serve=true;
    

    final int 
        playerXMax = 562,
        playerYMax = 218;

    static int frames = 0;

    private Thread gameThread;
    private final int FPS = 60;
    private final long OPTIMAL_TIME = 1000000000 / FPS;

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        double deltaTime = 0;
        int frameCount = 0;

        while (gameThread != null) {
            long now = System.nanoTime();
            long elapsedTime = now - lastTime;
            lastTime = now;

            // Calculate delta time
            deltaTime += elapsedTime / (double) OPTIMAL_TIME;

            // Update game logic when delta reaches 1
            if (deltaTime >= 1) {
                updateGame();
                repaintPanels();
                deltaTime--;
                frameCount++;   
            }

            // Optional: Sleep to prevent CPU overuse
            try {
               long sleepTime = (lastTime - System.nanoTime() + OPTIMAL_TIME) / 1000000;
                if (sleepTime > 0) {
                    Thread.sleep(sleepTime);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Optional: FPS counter (every second)
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                //System.out.println("FPS: " + frameCount);
                frameCount = 0;
            }
        }
        
    }

    private void updateGame() {
        // Consolidate all game update logic here
        frames++; //counts the frame # (used for debugging, intro, etc.)

        //logic stuff
        updateValues();
        if (frames > 363) getInputs();
        tickAbilities();
        checkTimeSlow();
        checkWin();
        syncHitLast();

        //player stuff
        player.updatePosition();

        //ball shadow stuff
        ballShadow.move();
        ballShadow.updatePosition();

        //ball stuff
        ball.syncLocation(ballShadow);
        ball.updatePosition();

        //enemy stuff
        moveEnemies();
    }

        

    private void repaintPanels() {
        // Repaint all panels
        SwingUtilities.invokeLater(() -> {
            title.repaint();
            help.repaint();
            characterSelect.repaint();
            inGame.repaint();
        });
    }

    /**
     * Main method
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Main m = new Main();
            }
        });
    }

    /**
     * Constructor: where the variables are initialized. 
     */
    Main() {
        currentHovered = hovered.titleStart;
        timeSlowed = false;

        playerScore = 0;
        enemyScore = 0;

        player = new Player(282, 125, 5, 40);

        ballShadow = new BallShadow(300, 500, 0, 10);
        ballShadow.setDestination(300, 500);
        ballShadow.setDeparture(100, 100);

        ball = new Ball(300, 500, 10);

        averageJoe = new Enemy(495, 100, 2, 30, Enemy.enemyTypes.AverageJoe);
        strongHercules = new Enemy(495, 100, 2, 30, Enemy.enemyTypes.StrongHercules);
        gradyTwin1 = new Enemy(495, 100, 2, 30, Enemy.enemyTypes.GradyTwin1);
        gradyTwin2 = new Enemy(495, 100, 2, 30, Enemy.enemyTypes.GradyTwin2);
        twoBallWalter = new Enemy(495, 100, 2, 30, Enemy.enemyTypes.TwoBallWalter);
        teleportSicilia = new Enemy(495, 100, 2, 30, Enemy.enemyTypes.TeleportSicilia);

        averageJoe.setActive(false);
        strongHercules.setActive(false);
        gradyTwin1.setActive(true);
        gradyTwin2.setActive(true);
        twoBallWalter.setActive(false);
        teleportSicilia.setActive(false);

        enemies = new ArrayList<>();
        enemies.add(averageJoe);
        enemies.add(strongHercules);
        enemies.add(gradyTwin1);
        enemies.add(gradyTwin2);
        enemies.add(twoBallWalter);
        enemies.add(teleportSicilia);

        upPressedThisTick = false;
        leftPressedThisTick = false;
        downPressedThisTick = false;
        rightPressedThisTick = false;
        enterPressedThisTick = false;

        KeyH = new KeyHandler();

        frame = new JFrame("BallPickle");
        frame.setSize(1024, 768);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.addKeyListener(KeyH);

        title = new GamePanel("title");
        title.setPreferredSize(new Dimension(GamePanel.winW, GamePanel.winH));

        inGame = new GamePanel("game");
        title.setPreferredSize(new Dimension(GamePanel.winW, GamePanel.winH));

        help = new GamePanel("help");
        title.setPreferredSize(new Dimension(GamePanel.winW, GamePanel.winH));

        characterSelect = new GamePanel("character select");
        title.setPreferredSize(new Dimension(GamePanel.winW, GamePanel.winH));
        updateValues();
        
        frame.add(title);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        startGameThread();
    }

    /**
     * Gets the inputs each time it is called, and reacts accordingly
     */
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
                player.changeAbility(Player.abilityChoices.riso);
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
                player.changeAbility(Player.abilityChoices.adonis);
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
                player.changeAbility(Player.abilityChoices.tasha);
                frame.remove(characterSelect);
                frame.add(title);
                currentHovered = hovered.titleStart;
                frame.revalidate();
            }
            break;
        case helpExit:
            if (KeyH.enterPressed && !enterPressedThisTick) {
                enterPressedThisTick = true;
                frame.remove(help);
                frame.add(title);
                currentHovered = hovered.titleStart;
                frame.revalidate();
            }
            break;
        case inGame:
            if (player.abilityON && player.ability == Player.abilityChoices.riso) {
                if (lookRightLast) {
                    player.xx += 20;
                    if (player.xx + player.size > playerXMax) player.xx = playerXMax - player.size;
                } else {
                    player.xx -= 20;
                    if (player.xx < 0) player.xx = 0;
                }
            } else {
                if (KeyH.rightPressed) {
                    lookRightLast=true;
                    player.xx += player.velocity;
                    if (player.xx + player.size > playerXMax) player.xx = playerXMax - player.size;
                }
                if (KeyH.leftPressed) {
                    lookRightLast=false;
                    player.xx -= player.velocity;
                    if (player.xx < 0) player.xx = 0;
                }
                if (KeyH.upPressed) {
                    player.yy -= player.velocity;
                    if (player.yy < 0) player.yy = 0;
                }
                if (KeyH.downPressed) {
                    player.yy += player.velocity;
                    if (player.yy + player.size > playerYMax) player.yy = playerYMax - player.size;
                }
                if (KeyH.enterPressed) {
                    if (!enterPressedThisTick) {
                        enterPressedThisTick = true;
                        if (!playerHitLast) {
                            /*spots ball flies to:
                             * left: (312, 150)
                             * centre: (512, 100)
                             * right: (712, 150)
                            */
                            Rectangle playerRect = new Rectangle((int)player.xx + player.getPositionXRelativeTo(), (int)player.yy + player.getPositionYRelativeTo(), player.size, player.size);
                            Rectangle ballRect = new Rectangle((int)ball.xx, (int)ball.yy, ball.size, ball.size);
                            if (playerRect.intersects(ballRect)) {
                                if (player.abilityON && player.ability == Player.abilityChoices.adonis) {
                                   ballShadow.velocity = 10;
                                } else {
                                    if (!timeSlowed) ballShadow.velocity = 4;
                                    else ballShadow.velocity = 2;
                                }
                                
                                if (serve) {
                                    ballShadow.setDestination(712, 150);
                                    serve = false;
                                } else if (KeyH.leftPressed) {
                                   ballShadow.setDestination(312, 150);
                                } else if (KeyH.rightPressed) {
                                   ballShadow.setDestination(712, 150);
                                } else {
                                   ballShadow.setDestination(512, 100);
                                }
                                setHitLast(false);
                            }
                        }
                    }
                }
            }
            if (KeyH.abilityPressed) {
                switch (player.ability) {
                case riso:
                    player.useAbility(lookRightLast);
                    break;
                case adonis:
                    player.useAbility(lookRightLast);
                    break;
                case tasha:
                    player.useAbility(lookRightLast);
                    break;
                }
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
    }//end getInputs

    public void resetGame() {
        player.xx = 282;
        player.yy = 125;
        player.updatePosition();
        for (Enemy e: enemies) {
            e.xx = 495;
            e.yy = 100;
            e.updatePosition();
        }
        ballShadow.xx = 300;
        ballShadow.yy = 500;
        ballShadow.velocity = 0;
        ballShadow.setDestination(300, 500);
        ballShadow.setDeparture(100, 100);
        ballShadow.updatePosition();
        setHitLast(true);
        serve=true;
    }

    public void tickAbilities() {
        if (player.abilityON) {
            player.abilityTime++;
            switch (player.ability) {
            case riso:
                if (player.abilityTime == 10) {
                    player.abilityON = !player.abilityON;
                    player.abilityTime = 0;
                }
                break;
            case adonis:
                if (player.abilityTime == 100) {
                    player.abilityON = !player.abilityON;
                    player.abilityTime = 0;
                }
                break;
            case tasha:
                if (player.abilityTime == 100) {
                    player.abilityON = !player.abilityON;
                    timeSlowed = false;
                    ballShadow.velocity *= 2;
                    for (Enemy e: enemies) e.velocity *= 2;
                    player.abilityTime = 0;
                }
                break;
            }
        }
    }

    public void checkTimeSlow() {
        if (!timeSlowed && player.abilityON && player.ability == Player.abilityChoices.tasha) {
            timeSlowed = true;
            ballShadow.velocity /= 2;
            for (Enemy e: enemies) e.velocity /= 2;
        }
    }

    public void drawCooldown(Graphics2D g2, double radius, double percent) {
        g2.setColor(Color.BLUE);
        g2.fillRect(0, 0, 100, 100);
    }
    
    public void updateValues() {
        title.updateValues(player, ballShadow, ball, enemies, timeSlowed, playerScore, enemyScore);
        inGame.updateValues(player, ballShadow, ball, enemies, timeSlowed, playerScore, enemyScore);
        help.updateValues(player, ballShadow, ball, enemies, timeSlowed, playerScore, enemyScore);
        characterSelect.updateValues(player, ballShadow, ball, enemies, timeSlowed, playerScore, enemyScore);
    }

    private void syncHitLast() {
        boolean hitLast = true;
        for (Enemy e: enemies) {
            if (e.isActive) {
                if (e.hitLast) {
                    hitLast = false;
                }
            }
        }
        playerHitLast = hitLast;
    }

    private void setHitLast(boolean hitLast) {
        for (Enemy e: enemies) {
            e.hitLast = hitLast;
        }
    }

    private void moveEnemies() {
        for (Enemy e: enemies) {
            if (e.isActive) {
                e.move(ballShadow);
                e.updatePosition();
                e.hit(ballShadow, timeSlowed);
            }
        }
    }

    private void checkWin() {
        // Score and game reset logic
        if (ball.y <= 0 || ball.y >= GamePanel.winH) {
            if (playerHitLast) {
                playerScore++;
                if (playerScore == 5) {
                    resetGame();
                    enemyScore = 0;
                    playerScore = 0;
                }
                resetGame();
            } else {
                enemyScore++;
                if (enemyScore == 5) {
                    resetGame();
                    enemyScore = 0;
                    playerScore = 0;
                }
                resetGame();
            }
        }
    }
}