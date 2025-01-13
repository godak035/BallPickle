/***
 * Main.java
 * The main gameloop of the BallPickle game.
 * by: David Sue, Vadim Mironov, Avishan Ketheswaran and Owen McCarthy
 * December 2, 2024
 */

import java.awt.*;
import javax.swing.*;
import java.util.*;
import javax.swing.Timer;

public class Main implements Runnable {
    
    JFrame frame;
    //Different panels that are drawn on for the title screen, game screen, help screen and character select screen
    GamePanel title, inGame, help, characterSelect, win, gameOver, intramuralChampion;

    //The current button that the user is hovering over (e.g. pressing enter will activate an input of that button)
    static enum hovered { charSelect1, charSelect2, charSelect3, titleStart, titleExit, titleHelp, inGame, helpExit,gameOverExit,victoryExit , nextLevel };
    static hovered currentHovered;
    static enum level {level1, level2, level3, level4, level5};
    static level currentLevel;

    //Whether or not the user has released the keys since they have pressed them (used in the menus, to ensure that you don't move down 2 options if you press the down key for 2 frames)
    boolean upPressedThisTick, downPressedThisTick, leftPressedThisTick, rightPressedThisTick, enterPressedThisTick;
    
    KeyHandler KeyH;

    Player player;
    BallShadow ballShadow, ballShadowWalter;
    ArrayList<BallShadow> ballShadows;
    Ball ball, ballWalter;
    ArrayList<Ball> balls;
    static Enemy averageJoe, strongHercules, gradyTwin1, gradyTwin2, twoBallWalter, teleportSicilia;
    ArrayList<Enemy> enemies;

    // timer for the animations.
    private Timer animTimer;
    // default charactermodel selection. This is to determine which character model is used for animations, based on your character selections.
    int characterModel = 1;

    static int playerScore, enemyScore;

    boolean lookRightLast, timeSlowed, serve=true;
    
    final int 
        playerXMax = (int)(450.0 / 1024.0 * GamePanel.WINW),
        playerYMax = (int)(250.0 / 768.0 * GamePanel.WINH);
    final static double 
        ballSpeed = 4.0 / 768.0 * GamePanel.WINH,
        enemySpeed = 1.5 / 768.0 * GamePanel.WINH;

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
        syncBalls();
        if (frames > 363) getInputs();
        tickAbilities();
        checkTimeSlow();
        checkWin();
        updatePlayerState();

        //player stuff
        player.updatePosition();

        //ball shadow stuff
        for (BallShadow b: ballShadows) {
            if (b.getActive()) {
                b.move();
                b.updatePosition();
            }
        }
        
        //ball stuff
        for (Ball b: balls) {
            if (b.getShadow().getActive()) {
                b.syncLocation();
                b.updatePosition();
            }
        }

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
        currentLevel = level.level1;
        timeSlowed = false;

        playerScore = 0;
        enemyScore = -1;

        player = new Player(282, 125, 4, 100);

        ballShadow = new BallShadow((int)(300.0 / 1024.0 * GamePanel.WINW), (int)(600.0 * 768.0 * GamePanel.WINH), 0, 10);
        ballShadow.setDestination((int)(300.0 / 1024.0 * GamePanel.WINW), (int)(500.0 * 768.0 * GamePanel.WINH));
        ballShadow.setDeparture((int)(100.0 / 1024.0 * GamePanel.WINW), (int)(100.0 * 768.0 * GamePanel.WINH));
        ballShadow.setActive(true);

        ballShadowWalter = new BallShadow((int)(300.0 / 1024.0 * GamePanel.WINW), (int)(600.0 * 768.0 * GamePanel.WINH), 0, 10);
        ballShadowWalter.setDestination((int)(300.0 / 1024.0 * GamePanel.WINW), (int)(500.0 * 768.0 * GamePanel.WINH));
        ballShadowWalter.setDeparture((int)(100.0 / 1024.0 * GamePanel.WINW), (int)(100.0 * 768.0 * GamePanel.WINH));
        ballShadowWalter.setActive(false);

        ballShadows = new ArrayList<>();
        ballShadows.add(ballShadow);
        ballShadows.add(ballShadowWalter);

        ball = new Ball(ballShadow);
        ballWalter = new Ball(ballShadowWalter);

        balls = new ArrayList<>();
        balls.add(ball);
        balls.add(ballWalter);

        averageJoe = new Enemy(495, 100, enemySpeed, 30, Enemy.enemyTypes.AverageJoe);
        strongHercules = new Enemy(495, 100, enemySpeed, 40, Enemy.enemyTypes.StrongHercules);
        gradyTwin1 = new Enemy(495, 100, enemySpeed, 20, Enemy.enemyTypes.GradyTwin1);
        gradyTwin2 = new Enemy(495, 100, enemySpeed, 20, Enemy.enemyTypes.GradyTwin2);
        twoBallWalter = new Enemy(495, 100, enemySpeed, 30, Enemy.enemyTypes.TwoBallWalter);
        teleportSicilia = new Enemy(495, 100, enemySpeed, 30, Enemy.enemyTypes.TeleportSicilia);

        averageJoe.setActive(true);
        strongHercules.setActive(false);
        gradyTwin1.setActive(false);
        gradyTwin2.setActive(false);
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
        frame.setUndecorated(true);

        title = new GamePanel("title");
        title.setPreferredSize(new Dimension((int)GamePanel.WINW, (int)GamePanel.WINH));

        inGame = new GamePanel("game");
        inGame.setPreferredSize(new Dimension((int)GamePanel.WINW, (int)GamePanel.WINH));

        help = new GamePanel("help");
        help.setPreferredSize(new Dimension((int)GamePanel.WINW, (int)GamePanel.WINH));

        characterSelect = new GamePanel("character select");
        characterSelect.setPreferredSize(new Dimension((int)GamePanel.WINW, (int)GamePanel.WINH));

        gameOver = new GamePanel("gameOver");
        gameOver.setPreferredSize(new Dimension((int)GamePanel.WINW, (int)GamePanel.WINH));

        win = new GamePanel("win");
        win.setPreferredSize(new Dimension((int)GamePanel.WINW, (int)GamePanel.WINH));

        intramuralChampion = new GamePanel("Intramural Champion");
        intramuralChampion.setPreferredSize(new Dimension((int)GamePanel.WINW, (int)GamePanel.WINH));
        
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
            case titleStart -> {
                if (KeyH.downPressed && !downPressedThisTick) {
                    downPressedThisTick = true;
                    currentHovered = hovered.titleHelp;
                }
                if (KeyH.enterPressed && !enterPressedThisTick) {
                    enterPressedThisTick = true;
                    frame.remove(title);
                    frame.add(characterSelect);
                    currentHovered = hovered.charSelect1;
                    frame.revalidate();
                }
            }
            case titleHelp -> {
                if (KeyH.downPressed && !downPressedThisTick) {
                    downPressedThisTick = true;
                    currentHovered = hovered.titleExit;
                }
                if (KeyH.upPressed && !upPressedThisTick) {
                    upPressedThisTick = true;
                    currentHovered = hovered.titleStart;
                }
                if (KeyH.enterPressed && !enterPressedThisTick) {
                    enterPressedThisTick = true;
                    frame.remove(title);
                    frame.add(help);
                    currentHovered = hovered.helpExit;
                    frame.revalidate();
                }
            }
            case titleExit -> {
                if (KeyH.upPressed && !upPressedThisTick) {
                    upPressedThisTick = true;
                    currentHovered = hovered.titleHelp;
                }
                if (KeyH.enterPressed && !enterPressedThisTick) {
                    System.exit(0);
                }
            }
            case charSelect1 -> {
                if (KeyH.rightPressed && !rightPressedThisTick) {
                    rightPressedThisTick = true;
                    currentHovered = hovered.charSelect2;
                }
                if (KeyH.enterPressed && !enterPressedThisTick) {
                    enterPressedThisTick = true;
                    player.changeAbility(Player.abilityChoices.riso);
                    characterModel = 1;
                    frame.remove(characterSelect);
                    frame.add(inGame);
                    currentHovered = hovered.inGame;
                    frame.revalidate();
                }
            }
            case charSelect2 -> {
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
                    characterModel = 2;
                    frame.remove(characterSelect);
                    frame.add(inGame);
                    currentHovered = hovered.inGame;
                    frame.revalidate();
                }
            }
            case charSelect3 -> {
                if (KeyH.leftPressed && !leftPressedThisTick) {
                    leftPressedThisTick = true;
                    currentHovered = hovered.charSelect2;
                }
                if (KeyH.enterPressed && !enterPressedThisTick) {
                    enterPressedThisTick = true;
                    player.changeAbility(Player.abilityChoices.tasha);
                    characterModel = 3;
                    frame.remove(characterSelect);
                    frame.add(inGame);
                    currentHovered = hovered.inGame;
                    frame.revalidate();
                }
            }
            case helpExit -> {
                if (KeyH.enterPressed && !enterPressedThisTick) {
                    enterPressedThisTick = true;
                    // frame.remove(help);
                    frame.remove(help);
                    frame.add(title);
                    currentHovered = hovered.titleStart;
                    frame.revalidate();
                }    
            }
            case gameOverExit -> {
                if (KeyH.enterPressed && !enterPressedThisTick) {
                    enterPressedThisTick = true;
                    frame.remove(gameOver);
                    frame.add(title);
                    currentHovered = hovered.titleStart;
                    frame.revalidate();
                }    

            }
            case victoryExit -> {
                if (KeyH.enterPressed && !enterPressedThisTick) {
                    enterPressedThisTick = true;
                    frame.remove(intramuralChampion);
                    frame.add(title);
                    currentHovered = hovered.titleStart;
                    frame.revalidate();
                }    

            }
            case nextLevel -> {
                if (KeyH.enterPressed && !enterPressedThisTick) {
                    enterPressedThisTick = true;
                    frame.remove(win);
                    frame.add(inGame);
                    frame.pack();
                    frame.revalidate();
                    frame.repaint();
                    currentHovered = hovered.inGame;
                }            
            }
            case inGame -> {
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
                        startPlayerHitAnim();
                        if (!enterPressedThisTick) {
                            enterPressedThisTick = true;
                            for (int i = 0; i < ballShadows.size(); i++) {
                                if (!ballShadows.get(i).getPlayerHitLast()) {
                                    /*spots ball flies to:
                                    * left: (312, 150)
                                    * centre: (512, 100)
                                    * right: (712, 150)
                                    * based on a 1024 x 768 resolution
                                    */
                                    Rectangle playerRect = new Rectangle((int)player.xx + player.getPositionXRelativeTo(), (int)player.yy + player.getPositionYRelativeTo(), player.size, player.size);
                                    Rectangle ballRect = new Rectangle((int)balls.get(i).xx, (int)balls.get(i).yy, balls.get(i).size, balls.get(i).size);
                                    if (playerRect.intersects(ballRect)) {
                                        if (player.abilityON && player.ability == Player.abilityChoices.adonis) {
                                            ballShadows.get(i).velocity = this.ballSpeed * 2;
                                        } else {
                                            if (!timeSlowed) ballShadows.get(i).velocity = this.ballSpeed;
                                            else ballShadows.get(i).velocity = this.ballSpeed / 2;
                                        }
                                        
                                        if (serve) ballShadows.get(i).setDestination((int)(712.0 / 1024.0 * GamePanel.WINW), (int)(150.0 / 768.0 * GamePanel.WINH));
                                        else if (KeyH.leftPressed) ballShadows.get(i).setDestination((int)(312.0 / 1024.0 * GamePanel.WINW), (int)(150.0 / 768.0 * GamePanel.WINH));
                                        else if (KeyH.rightPressed) ballShadows.get(i).setDestination((int)(712.0 / 1024.0 * GamePanel.WINW), (int)(150.0 / 768.0 * GamePanel.WINH));
                                        else ballShadows.get(i).setDestination((int)(512.0 / 1024.0 * GamePanel.WINW), (int)(100.0 / 768.0 * GamePanel.WINH));
                                        
                                        serve = false;
                                        ballShadows.get(i).setPlayerHitLast(true);
                                    }
                                }
                            }
                        }
                    }
                }
                if (KeyH.abilityPressed) {
                    switch (player.ability) {
                        case riso -> player.useAbility(lookRightLast);
                        case adonis -> player.useAbility(lookRightLast);
                        case tasha -> player.useAbility(lookRightLast);
                    }
                }
            }
            default -> {}
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
        player.abilityON = false;
        player.abilityTime = 0;
        player.resetCooldown();
        player.updatePosition();
        for (Enemy e: enemies) {
            e.xx = GamePanel.WINW / 2.0;
            e.yy = 100.0 / 768.0 * GamePanel.WINH;
            e.updatePosition();
        }
        for (BallShadow b: ballShadows) {
            b.xx = (int)(350.0 / 1024.0 * GamePanel.WINW);
            b.yy = (int)(650.0 / 768.0 * GamePanel.WINH);
            b.velocity = 0;
            b.setDestination((int)(300.0 / 1024.0 * GamePanel.WINW), (int)(500.0 * 768.0 * GamePanel.WINH));
            b.setDeparture((int)(100.0 / 1024.0 * GamePanel.WINW), (int)(100.0 * 768.0 * GamePanel.WINH));
            b.updatePosition();
            setHitLast(false);
        }
        ballShadowWalter.setActive(false);
        serve=true;
        
        switch (Main.currentLevel) {   
            case level1 -> {
                averageJoe.setActive(true);
                strongHercules.setActive(false);
                gradyTwin1.setActive(false);
                gradyTwin2.setActive(false);
                twoBallWalter.setActive(false);
                teleportSicilia.setActive(false);
            }
            case level2 -> {
                averageJoe.setActive(false);
                strongHercules.setActive(true);
                gradyTwin1.setActive(false);
                gradyTwin2.setActive(false);
                twoBallWalter.setActive(false);
                teleportSicilia.setActive(false);
            }
            case level3 -> {
                averageJoe.setActive(false);
                strongHercules.setActive(false);
                gradyTwin1.setActive(true);
                gradyTwin2.setActive(true);
                twoBallWalter.setActive(false);
                teleportSicilia.setActive(false);
            }
            case level4 -> {
                averageJoe.setActive(false);
                strongHercules.setActive(false);
                gradyTwin1.setActive(false);
                gradyTwin2.setActive(false);
                twoBallWalter.setActive(true);
                teleportSicilia.setActive(false);
            }
            case level5 -> {
                averageJoe.setActive(false);
                strongHercules.setActive(false);
                gradyTwin1.setActive(false);
                gradyTwin2.setActive(false);
                twoBallWalter.setActive(false);
                teleportSicilia.setActive(true);}
            default -> {}
        }
    }

    public void tickAbilities() {
        if (player.abilityON) {
            player.abilityTime++;
            switch (player.ability) {
                case riso -> {
                    if (player.abilityTime == 10) {
                        player.abilityON = !player.abilityON;
                        player.abilityTime = 0;
                    }
                }
                case adonis -> {
                    if (player.abilityTime == 100) {
                        player.abilityON = !player.abilityON;
                        player.abilityTime = 0;
                    }
                }
                case tasha -> {
                    if (player.abilityTime == 100) {
                        player.abilityON = !player.abilityON;
                        timeSlowed = false;
                        ballShadow.velocity *= 2;
                        for (Enemy e: enemies) e.velocity *= 2;
                        player.abilityTime = 0;
                    }
                }
            }
        }
    }//end tickAbilities

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
        title.updateValues(this);
        inGame.updateValues(this);
        help.updateValues(this);
        characterSelect.updateValues(this);
        for (Enemy e: enemies) e.updateValues(ballShadows);
    }

    private void setHitLast(boolean hitLast) {
        for (BallShadow b: ballShadows) {
            b.setPlayerHitLast(hitLast);
        }
    }

    private void syncBalls() {
        for (Enemy e: enemies) {
            for (int i = 0; i < ballShadows.size(); i++) {
                if (ballShadows.get(i).destinationX != e.ballShadows.get(i).destinationX || ballShadows.get(i).destinationY != e.ballShadows.get(i).destinationY) {
                    ballShadows.get(i).setDestination(e.ballShadows.get(i).destinationX, e.ballShadows.get(i).destinationX);
                }
                if (ballShadows.get(i).getActive() != e.ballShadows.get(i).getActive()) {
                    ballShadows.get(i).setActive(e.ballShadows.get(i).getActive());
                }
                if (ballShadows.get(i).getSpin() != e.ballShadows.get(i).getSpin()) {
                    ballShadows.get(i).setSpin(e.ballShadows.get(i).getSpin());
                }
            }
        }
    }

    private void moveEnemies() {
        for (Enemy e: enemies) {
            if (e.isActive) {
                e.move(ballShadows);
                e.updatePosition();
                if (playerScore > enemyScore) e.hit(timeSlowed, true);
                else e.hit(timeSlowed, false);
            }
        }
    }

    public void updatePlayerState() {
        if (!KeyH.upPressed && !KeyH.downPressed && !KeyH.rightPressed && !KeyH.leftPressed && !KeyH.enterPressed) {
            player.currentState = PlayerStates.idle_right;
        } else if (KeyH.enterPressed) {
            player.currentState = PlayerStates.hit;
        } else {
            // If player is moving, determine direction
            if (KeyH.rightPressed) {
                player.currentState = PlayerStates.move_right;
            //} else if (player.x + player.velocity < 0) {
                //player.currentState = PlayerStates.move_left;
           // } else if (player.y + player.velocity > 0) {
                //player.currentState = PlayerStates.move_down;
           // } else if (player.y + player.velocity < 0) {
                //player.currentState = PlayerStates.move_up;
           }
        }
    }

    public void startPlayerHitAnim() {
        // if (KeyH.enterPressed) {
        //     player.currentState = PlayerStates.hit;
           
    
        //     animTimer = new Timer(400, e -> {
                    
        //     player.currentState = PlayerStates.idle_right;
        //     animTimer.stop(); //Stop the timer
        // });
        //     animTimer.setRepeats(false);
        //     animTimer.start(); //Start the timer
        // }
    }

    public void next() {

        if (currentLevel==level.level1) currentLevel=level.level2;
        else if (currentLevel==level.level2) currentLevel=level.level3;
        else if (currentLevel==level.level3) currentLevel=level.level4;
        else if (currentLevel==level.level4) currentLevel=level.level5;
        else if (currentLevel==level.level5) {
            frame.remove(inGame);
            frame.add(intramuralChampion);
            frame.revalidate();
            frame.pack();
            frame.repaint();
            currentHovered = hovered.victoryExit;
        }
    }

    private void checkWin() {
        // Score and game reset logic
        for (BallShadow b: ballShadows) {
            //checks if the ball is outside the screen
            if (b.yy <= 0 || b.yy >= (int)GamePanel.WINH || b.xx <= 0 || b.xx >= (int)GamePanel.WINW) {
                if (b.getPlayerHitLast()) {
                    playerScore++;
                    if (playerScore == 5) {
                        if (currentLevel != level.level5) {
                            resetGame();
                            enemyScore = 0;
                            playerScore = 0;
                            frame.remove(inGame);
                            frame.add(win);
                            frame.revalidate();
                            frame.pack();
                            frame.repaint();
                            next();
                            currentHovered = hovered.nextLevel;
                        } else {
                            next();
                        }
                    } 
                    resetGame();
                } else {
                    enemyScore++;
                    if (enemyScore == 5) {
                        resetGame();
                        enemyScore = 0;
                        playerScore = 0;
                        frame.remove(inGame);
                        frame.add(gameOver);
                        frame.revalidate();
                        currentHovered = hovered.helpExit;
                    }
                    resetGame();
                }
            }
        }
    }
}