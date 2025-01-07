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
    BallShadow ballShadow, ballShadowWalter;
    ArrayList<BallShadow> ballShadows;
    Ball ball, ballWalter;
    ArrayList<Ball> balls;
    Enemy averageJoe, strongHercules, gradyTwin1, gradyTwin2, twoBallWalter, teleportSicilia;
    ArrayList<Enemy> enemies;

    // timer for the animations.
    private Timer animTimer;
    // default charactermodel selection. This is to determine which character model is used for animations, based on your character selections.
    int characterModel = 1;

    static int playerScore, enemyScore;

    boolean lookRightLast, timeSlowed, serve=true;
    
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
        syncBalls();
        if (frames > 363) getInputs();
        tickAbilities();
        checkTimeSlow();
        checkWin();

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
        timeSlowed = false;

        playerScore = 0;
        enemyScore = 0;

        player = new Player(282, 125, 5, 40);

        ballShadow = new BallShadow(300, 500, 0, 10);
        ballShadow.setDestination(300, 500);
        ballShadow.setDeparture(100, 100);
        ballShadow.setActive(true);

        ballShadowWalter = new BallShadow(300, 500, 0, 10);
        ballShadowWalter.setDestination(300, 500);
        ballShadowWalter.setDeparture(100, 100);
        ballShadowWalter.setActive(false);

        ballShadows = new ArrayList<>();
        ballShadows.add(ballShadow);
        ballShadows.add(ballShadowWalter);

        ball = new Ball(ballShadow);
        ballWalter = new Ball(ballShadowWalter);

        balls = new ArrayList<>();
        balls.add(ball);
        balls.add(ballWalter);

        averageJoe = new Enemy(495, 100, 2, 30, Enemy.enemyTypes.AverageJoe);
        strongHercules = new Enemy(495, 100, 2, 40, Enemy.enemyTypes.StrongHercules);
        gradyTwin1 = new Enemy(495, 100, 2, 20, Enemy.enemyTypes.GradyTwin1);
        gradyTwin2 = new Enemy(495, 100, 2, 20, Enemy.enemyTypes.GradyTwin2);
        twoBallWalter = new Enemy(495, 100, 2, 30, Enemy.enemyTypes.TwoBallWalter);
        teleportSicilia = new Enemy(495, 100, 2, 30, Enemy.enemyTypes.TeleportSicilia);

        averageJoe.setActive(false);
        strongHercules.setActive(false);
        gradyTwin1.setActive(false);
        gradyTwin2.setActive(false);
        twoBallWalter.setActive(false);
        teleportSicilia.setActive(true);

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

        title = new GamePanel("title", this);
        title.setPreferredSize(new Dimension(GamePanel.WINW, GamePanel.WINH));

        inGame = new GamePanel("game", this);
        title.setPreferredSize(new Dimension(GamePanel.WINW, GamePanel.WINH));

        help = new GamePanel("help", this);
        title.setPreferredSize(new Dimension(GamePanel.WINW, GamePanel.WINH));

        characterSelect = new GamePanel("character select", this);
        title.setPreferredSize(new Dimension(GamePanel.WINW, GamePanel.WINH));
        
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
                    currentHovered = hovered.titleCharSelect;
                }
                if (KeyH.enterPressed && !enterPressedThisTick) {
                    enterPressedThisTick = true;
                    frame.remove(title);
                    frame.add(inGame);
                    currentHovered = hovered.inGame;
                    frame.revalidate();
                }
            }
            case titleCharSelect -> {
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
            }
            case titleHelp -> {
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
                    frame.add(title);
                    currentHovered = hovered.titleStart;
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
                    frame.add(title);
                    currentHovered = hovered.titleStart;
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
                    frame.add(title);
                    currentHovered = hovered.titleStart;
                    frame.revalidate();
                }
            }
            case helpExit -> {
                if (KeyH.enterPressed && !enterPressedThisTick) {
                    enterPressedThisTick = true;
                    frame.remove(help);
                    frame.add(title);
                    currentHovered = hovered.titleStart;
                    frame.revalidate();
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
                        if (player.currentState != PlayerStates.move_right) {
                            player.currentState = PlayerStates.move_right;
                            player.lastKeyPressed = "d";
                        }
                        player.xx += player.velocity;
                        if (player.xx + player.size > playerXMax) player.xx = playerXMax - player.size;
                    }
                    if (KeyH.leftPressed) {
                        lookRightLast=false;
                        if (player.currentState != PlayerStates.move_left) {
                            player.currentState = PlayerStates.move_left;
                            player.lastKeyPressed = "a";
                        }
                        player.xx -= player.velocity;
                        if (player.xx < 0) player.xx = 0;
                    }
                    if (KeyH.upPressed) {
                        if (player.currentState != PlayerStates.move_up) {
                            player.currentState = PlayerStates.move_up;
                            player.lastKeyPressed = "w";
                        }
                        player.yy -= player.velocity;
                        if (player.yy < 0) player.yy = 0;
                    }
                    if (KeyH.downPressed) {
                        if (player.currentState != PlayerStates.move_down) {
                            player.currentState = PlayerStates.move_down;
                            player.lastKeyPressed = "s";
                        }
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
                                    */
                                   
                                    Rectangle playerRect = new Rectangle((int)player.xx + player.getPositionXRelativeTo(), (int)player.yy + player.getPositionYRelativeTo(), player.size, player.size);
                                    Rectangle ballRect = new Rectangle((int)balls.get(i).xx, (int)balls.get(i).yy, balls.get(i).size, balls.get(i).size);
                                    if (playerRect.intersects(ballRect)) {
                                        if (player.abilityON && player.ability == Player.abilityChoices.adonis) {
                                            ballShadows.get(i).velocity = 10;
                                        } else {
                                            if (!timeSlowed) ballShadows.get(i).velocity = 4;
                                            else ballShadows.get(i).velocity = 2;
                                        }
                                        
                                        if (serve) ballShadows.get(i).setDestination(712, 150);
                                        else if (KeyH.leftPressed) ballShadows.get(i).setDestination(312, 150);
                                        else if (KeyH.rightPressed) ballShadows.get(i).setDestination(712, 150);
                                        else ballShadows.get(i).setDestination(512, 100);
                                        
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
        if (!KeyH.upPressed) 
            upPressedThisTick = false;
            player.currentState = PlayerStates.idle_right;
          

        if (!KeyH.downPressed) 
            downPressedThisTick = false;
            player.currentState = PlayerStates.idle_right;
            

        if (!KeyH.leftPressed) 
            leftPressedThisTick = false;
            player.currentState = PlayerStates.idle_right;
            

        if (!KeyH.rightPressed) 
            rightPressedThisTick = false;
            player.currentState = PlayerStates.idle_right;
            

        if (!KeyH.enterPressed) 
            enterPressedThisTick = false;
            
           

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
        for (BallShadow b: ballShadows) {
            b.xx = 300;
            b.yy = 500;
            b.velocity = 0;
            b.setDestination(300, 500);
            b.setDeparture(100, 100);
            b.updatePosition();
            setHitLast(false);
        }
        ballShadowWalter.setActive(false);
        
        serve=true;
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
        title.updateValues(player, ballShadows, balls, enemies, timeSlowed, playerScore, enemyScore);
        inGame.updateValues(player, ballShadows, balls, enemies, timeSlowed, playerScore, enemyScore);
        help.updateValues(player, ballShadows, balls, enemies, timeSlowed, playerScore, enemyScore);
        characterSelect.updateValues(player, ballShadows, balls, enemies, timeSlowed, playerScore, enemyScore);
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


    public void startPlayerHitAnim() {
       
        if (player.currentState != PlayerStates.hit) {
           
            player.currentState = PlayerStates.hit;
    
            
            animTimer = new Timer(1000, e -> {
                
                player.currentState = PlayerStates.idle_right;
                animTimer.stop(); //Stop the timer
            });
            animTimer.setRepeats(false);
            animTimer.start(); //Start the timer
        }
    }
    


    private void checkWin() {
        // Score and game reset logic
        for (BallShadow b: ballShadows) {
            if (b.y <= 0 || b.y >= GamePanel.WINH) {
                if (b.getPlayerHitLast()) {
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
}