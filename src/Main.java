/***
 * Main.java
 * The main gameloop of the BallPickle game.
 * by: David Sue, Vadim Mironov, Avishan Ketheswaran and Owen McCarthy
 * January 14, 2025
 */

import java.awt.*;
import javax.swing.*;
import java.util.*;
import javax.swing.Timer;

public class Main implements Runnable {

    Sound sound = new Sound();
    public void playMusic(int i) {
        sound.setFile(i);
        sound.play();
        sound.loop();  
     }

    public void stopMusic(){

    }

    public void playSE(int i){
        sound.setFile(i);
        sound.play();
    }
    
    private JFrame frame;
    //Different panels that are drawn on for the title screen, game screen, help screen and character select screen
    private GamePanel title, inGame, help, characterSelect, win, gameOver, intramuralChampion;

    //The current button that the user is hovering over (e.g. pressing enter will activate an input of that button)
    public static enum hovered { charSelect1, charSelect2, charSelect3, titleStart, titleExit, titleHelp, inGame, helpExit,gameOverExit,victoryExit , nextLevel };
    public static hovered currentHovered;
    public static enum level {level1, level2, level3, level4, level5};
    public static level currentLevel;

    //Whether or not the user has released the keys since they have pressed them 
    //(used in the menus, to ensure that you don't move down 2 options if you press the down key for 2 frames)
    private boolean upPressedThisTick, downPressedThisTick, leftPressedThisTick, rightPressedThisTick, enterPressedThisTick;
    
    //The KeyHandler will hadle all key presses
    private KeyHandler KeyH;

    //All entities
    private Player player;
    private BallShadow ballShadow, ballShadowWalter;
    private ArrayList<BallShadow> ballShadows;
    private Ball ball, ballWalter;
    private ArrayList<Ball> balls;
    private Enemy averageJoe, strongHercules, gradyTwin1, gradyTwin2, twoBallWalter, teleportSicilia;
    private ArrayList<Enemy> enemies;

    //score
    static int playerScore, enemyScore;

    //miscellaneous other booleans
    private boolean lookRightLast, timeSlowed, serve=true;
    
    //The players movement area
    private final int 
        playerXMax = (int)(450.0 / 1024.0 * GamePanel.WINW),
        playerYMax = (int)(250.0 / 768.0 * GamePanel.WINH);

    //Soething with sound

    
    //Entity speeds and sizes
    public final static double 
        BALL_SPEED = 4.0 / 768.0 * GamePanel.WINH,
        ENEMY_SPEED = 1.5 / 768.0 * GamePanel.WINH,
        PLAYER_SPEED = 4.0 / 768.0 * GamePanel.WINH,
        PLAYER_SIZE = 35.0 / 768.0 * GamePanel.WINH;

    public static int frames = 0;
    private int lastHit;

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
        playMusic(0);
        currentHovered = hovered.titleStart;
        currentLevel = level.level1;
        timeSlowed = false;
        lastHit = 0;

        playerScore = 0;
        enemyScore = -1;
        playMusic(0);

        player = new Player(282, 125, PLAYER_SPEED, (int)PLAYER_SIZE * 2);

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

        averageJoe = new Enemy(495, 100, ENEMY_SPEED, (int)PLAYER_SIZE, Enemy.enemyTypes.AverageJoe);
        strongHercules = new Enemy(495, 100, ENEMY_SPEED, (int)PLAYER_SIZE, Enemy.enemyTypes.StrongHercules);
        gradyTwin1 = new Enemy(495, 100, ENEMY_SPEED, (int)PLAYER_SIZE, Enemy.enemyTypes.GradyTwin1);
        gradyTwin2 = new Enemy(495, 100, ENEMY_SPEED, (int)PLAYER_SIZE, Enemy.enemyTypes.GradyTwin2);
        twoBallWalter = new Enemy(495, 100, ENEMY_SPEED, (int)PLAYER_SIZE, Enemy.enemyTypes.TwoBallWalter);
        teleportSicilia = new Enemy(495, 100, ENEMY_SPEED, (int)PLAYER_SIZE, Enemy.enemyTypes.TeleportSicilia);

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
                if (KeyH.getDownPressed() && !downPressedThisTick) {
                    downPressedThisTick = true;
                    currentHovered = hovered.titleHelp;
                }
                if (KeyH.getEnterPressed() && !enterPressedThisTick) {
                    stopMusic();
                    playMusic(1);
                    enterPressedThisTick = true;
                    frame.remove(title);
                    frame.add(characterSelect);
                    currentHovered = hovered.charSelect1;
                    frame.revalidate();
                }
            }
            case titleHelp -> {
                if (KeyH.getDownPressed() && !downPressedThisTick) {
                    downPressedThisTick = true;
                    currentHovered = hovered.titleExit;
                }
                if (KeyH.getUpPressed() && !upPressedThisTick) {
                    upPressedThisTick = true;
                    currentHovered = hovered.titleStart;
                }
                if (KeyH.getEnterPressed() && !enterPressedThisTick) {
                    enterPressedThisTick = true;
                    frame.remove(title);
                    frame.add(help);
                    currentHovered = hovered.helpExit;
                    frame.revalidate();
                }
            }
            case titleExit -> {
                if (KeyH.getUpPressed() && !upPressedThisTick) {
                    upPressedThisTick = true;
                    currentHovered = hovered.titleHelp;
                }
                if (KeyH.getEnterPressed() && !enterPressedThisTick) {
                    System.exit(0);
                }
            }
            case charSelect1 -> {
                if (KeyH.getRightPressed() && !rightPressedThisTick) {
                    rightPressedThisTick = true;
                    currentHovered = hovered.charSelect2;
                }
                if (KeyH.getEnterPressed() && !enterPressedThisTick) {
                    enterPressedThisTick = true;
                    player.changeAbility(Player.abilityChoices.riso);
                    frame.remove(characterSelect);
                    frame.add(inGame);
                    currentHovered = hovered.inGame;
                    frame.revalidate();
                }
            }
            case charSelect2 -> {
                if (KeyH.getRightPressed() && !rightPressedThisTick) {
                    rightPressedThisTick = true;
                    currentHovered = hovered.charSelect3;
                }
                if (KeyH.getLeftPressed() && !leftPressedThisTick) {
                    leftPressedThisTick = true;
                    currentHovered = hovered.charSelect1;
                }
                if (KeyH.getEnterPressed() && !enterPressedThisTick) {
                    enterPressedThisTick = true;
                    player.changeAbility(Player.abilityChoices.adonis);
                    frame.remove(characterSelect);
                    frame.add(inGame);
                    currentHovered = hovered.inGame;
                    frame.revalidate();
                }
            }
            case charSelect3 -> {
                if (KeyH.getLeftPressed() && !leftPressedThisTick) {
                    leftPressedThisTick = true;
                    currentHovered = hovered.charSelect2;
                }
                if (KeyH.getEnterPressed() && !enterPressedThisTick) {
                    enterPressedThisTick = true;
                    player.changeAbility(Player.abilityChoices.tasha);
                    frame.remove(characterSelect);
                    frame.add(inGame);
                    currentHovered = hovered.inGame;
                    frame.revalidate();
                }
            }
            case helpExit -> {
                if (KeyH.getEnterPressed() && !enterPressedThisTick) {
                    enterPressedThisTick = true;
                    // frame.remove(help);
                    frame.remove(help);
                    frame.add(title);
                    currentHovered = hovered.titleStart;
                    frame.revalidate();
                }    
            }
            case gameOverExit -> {
                if (KeyH.getEnterPressed() && !enterPressedThisTick) {
                    enterPressedThisTick = true;
                    frame.remove(gameOver);
                    frame.add(title);
                    currentHovered = hovered.titleStart;
                    frame.revalidate();
                }    

            }
            case victoryExit -> {
                if (KeyH.getEnterPressed() && !enterPressedThisTick) {
                    enterPressedThisTick = true;
                    frame.remove(intramuralChampion);
                    frame.add(title);
                    currentHovered = hovered.titleStart;
                    frame.revalidate();
                }    

            }
            case nextLevel -> {
                if (KeyH.getEnterPressed() && !enterPressedThisTick) {
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
                if (player.getAbilityON() && player.getAbility() == Player.abilityChoices.riso) {
                    if (lookRightLast) {
                        player.xx += player.velocity * 4;
                        if (player.xx + player.size > playerXMax) player.xx = playerXMax - player.size;
                    } else {
                        player.xx -= player.velocity * 4;
                        if (player.xx < 0) player.xx = 0;
                    }
                } else {
                    if (KeyH.getRightPressed()) {
                        rightPressedThisTick = true;
                        lookRightLast=true;
                        player.xx += player.velocity;
                        if (player.xx + player.size > playerXMax) player.xx = playerXMax - player.size;
                    }
                    if (KeyH.getLeftPressed()) {
                        leftPressedThisTick = true;
                        lookRightLast=false;
                        player.xx -= player.velocity;
                        if (player.xx < 0) player.xx = 0;
                    }
                    if (KeyH.getUpPressed()) { 
                        upPressedThisTick = true;
                        player.yy -= player.velocity;
                        if (player.yy < 0) player.yy = 0;
                    }
                    if (KeyH.getDownPressed()) {    
                        downPressedThisTick = true;
                        player.yy += player.velocity;
                        if (player.yy + player.size > playerYMax) player.yy = playerYMax - player.size;
                    }
                    if (KeyH.getEnterPressed()) {
                        if (!enterPressedThisTick) {
                            enterPressedThisTick = true;
                            lastHit = this.frames;
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
                                        if (player.getAbilityON() && player.getAbility() == Player.abilityChoices.adonis) {
                                            ballShadows.get(i).velocity = this.BALL_SPEED * 2;
                                        } else {
                                            if (!timeSlowed) ballShadows.get(i).velocity = this.BALL_SPEED;
                                            else ballShadows.get(i).velocity = this.BALL_SPEED / 2;
                                        }
                                        
                                        if (serve) ballShadows.get(i).setDestination((int)(712.0 / 1024.0 * GamePanel.WINW), (int)(150.0 / 768.0 * GamePanel.WINH));
                                        else if (KeyH.getLeftPressed()) ballShadows.get(i).setDestination((int)(312.0 / 1024.0 * GamePanel.WINW), (int)(150.0 / 768.0 * GamePanel.WINH));
                                        else if (KeyH.getRightPressed()) ballShadows.get(i).setDestination((int)(712.0 / 1024.0 * GamePanel.WINW), (int)(150.0 / 768.0 * GamePanel.WINH));
                                        else ballShadows.get(i).setDestination((int)(512.0 / 1024.0 * GamePanel.WINW), (int)(100.0 / 768.0 * GamePanel.WINH));
                                        
                                        serve = false;
                                        ballShadows.get(i).setPlayerHitLast(true);
                                    }
                                }
                            }
                        }
                    }
                }
                if (KeyH.getAbilityPressed()) {
                    switch (player.getAbility()) {
                        case riso -> player.useAbility(lookRightLast);
                        case adonis -> player.useAbility(lookRightLast);
                        case tasha -> player.useAbility(lookRightLast);
                    }
                }
            }
            default -> {}
        }
        if (!KeyH.getUpPressed()) upPressedThisTick = false;
        if (!KeyH.getDownPressed()) downPressedThisTick = false;
        if (!KeyH.getLeftPressed()) leftPressedThisTick = false;
        if (!KeyH.getRightPressed()) rightPressedThisTick = false;
        if (!KeyH.getEnterPressed()) enterPressedThisTick = false;
    }//end getInputs

    /**
     * Resets the game
     */
    public void resetGame() {
        player.xx = GamePanel.WINW * 0.2;
        player.yy = GamePanel.WINH * 0.1;
        player.setAbilityON(false);
        player.setAbilityTime(0);
        player.setFullCooldown();
        player.updatePosition();

        //resets the position of all enemies
        for (Enemy e: enemies) {
            e.xx = GamePanel.WINW / 2.0;
            e.yy = 100.0 / 768.0 * GamePanel.WINH;
            e.updatePosition();
        }
        
        //resets the position of all balls
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
        
        //sets the enemies to active or not depending on the level
        averageJoe.setActive(false);
        strongHercules.setActive(false);
        gradyTwin1.setActive(false);
        gradyTwin2.setActive(false);
        twoBallWalter.setActive(false);
        teleportSicilia.setActive(false);
        switch (Main.currentLevel) {   
            case level1 -> averageJoe.setActive(true);
            case level2 -> strongHercules.setActive(true);
            case level3 -> {
                gradyTwin1.setActive(true);
                gradyTwin2.setActive(true);
            }
            case level4 -> twoBallWalter.setActive(true);
            case level5 -> teleportSicilia.setActive(true);
            default -> {}
        }
    }

    /**
     * Ticks each ability cooldown
     */
    public void tickAbilities() {
        if (player.getAbilityON()) {
            player.setAbilityTime(player.getAbilityTime() + 1);
            switch (player.getAbility()) {
                case riso -> {
                    if (player.getAbilityTime() == 10) {
                        player.setAbilityON(false);
                        player.setAbilityTime(0);
                    }
                }
                case adonis -> {
                    if (player.getAbilityTime() == 100) {
                        player.setAbilityON(false);
                        player.setAbilityTime(0);
                    }
                }
                case tasha -> {
                    if (player.getAbilityTime() == 100) {
                        player.setAbilityON(false);
                        timeSlowed = false;
                        ballShadow.velocity *= 2;
                        for (Enemy e: enemies) e.velocity *= 2;
                        player.setAbilityTime(0);
                    }
                }
            }
        }
    }//end tickAbilities

    /**
     * Checks if Tasha's time slow ability has been activated
     */
    public void checkTimeSlow() {
        if (!timeSlowed && player.getAbilityON() && player.getAbility() == Player.abilityChoices.tasha) {
            timeSlowed = true;
            ballShadow.velocity /= 2;
            for (Enemy e: enemies) e.velocity /= 2;
        }
    }
    
    /**
     * Calls all the updateValues methods for every GamePanel
     */
    public void updateValues() {
        title.updateValues(this);
        inGame.updateValues(this);
        help.updateValues(this);
        characterSelect.updateValues(this);
        for (Enemy e: enemies) e.updateValues(ballShadows);
    }

    /**
     * Sets the playerHitLast variable of every ball to hitLast
     * @param hitLast  What to set all balls playerHitLast variable to
     */
    private void setHitLast(boolean hitLast) {
        for (BallShadow b: ballShadows) {
            b.setPlayerHitLast(hitLast);
        }
    }

    /**
     * Ensures that all enemies have accurate information regarding the ball's position and velocity
     */
    private void syncBalls() {
        for (Enemy e: enemies) {
            for (int i = 0; i < ballShadows.size(); i++) {
                if (ballShadows.get(i).getDestinationX() != e.getBallShadows().get(i).getDestinationX() || ballShadows.get(i).getDestinationY() != e.getBallShadows().get(i).getDestinationY()) {
                    ballShadows.get(i).setDestination(e.getBallShadows().get(i).getDestinationX(), e.getBallShadows().get(i).getDestinationY());
                }
                if (ballShadows.get(i).getActive() != e.getBallShadows().get(i).getActive()) {
                    ballShadows.get(i).setActive(e.getBallShadows().get(i).getActive());
                }
                if (ballShadows.get(i).getSpin() != e.getBallShadows().get(i).getSpin()) {
                    ballShadows.get(i).setSpin(e.getBallShadows().get(i).getSpin());
                }
            }
        }
    }

    /**
     * Moves all actve enemies
     */
    private void moveEnemies() {
        for (Enemy e: enemies) {
            if (e.getActive()) {
                e.move();
                e.updatePosition();
                if (playerScore > enemyScore) e.hit(timeSlowed, true);
                else e.hit(timeSlowed, false);
            }
        }
    }

    /**
     * Moves to the next level
     */
    public void next() {
        if (currentLevel==level.level1) {
            currentLevel=level.level2;
            stopMusic();
            playMusic(2);
        }
        else if (currentLevel==level.level2) {
            currentLevel=level.level3;
            stopMusic();
            playMusic(1);
        }
        else if (currentLevel==level.level3) {
            currentLevel=level.level4;
            stopMusic();
            playMusic(2);
        }
        else if (currentLevel==level.level4) {
            currentLevel=level.level5;
            stopMusic();
            playMusic(1);
        }
        else if (currentLevel==level.level5) {
            frame.remove(inGame);
            frame.add(intramuralChampion);
            frame.revalidate();
            frame.pack();
            frame.repaint();
            currentHovered = hovered.victoryExit;
        }
    }
    
    /**
     * Checks if the enemy or the player has won the game. If so, reset game
     */
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

    //getter methods
    public ArrayList<Enemy> getEnemies() { return this.enemies; }
    public ArrayList<Ball> getBalls() { return this.balls; }
    public ArrayList<BallShadow> getBallShadows() { return this.ballShadows; }
    public boolean getTimeSlowed() { return this.timeSlowed; }
    public Player getPlayer() { return this.player; }
    public int getLastHit() { return this.lastHit; }
    public KeyHandler getKeyH() { return this.KeyH; }
    public boolean getLeftPressedThisTick() { return this.leftPressedThisTick; }
    public boolean getRightPressedThisTick() { return this.rightPressedThisTick; }
    public boolean getUpPressedThisTick() { return this.upPressedThisTick; }
    public boolean getDownPressedThisTick() { return this.downPressedThisTick; }
    public boolean getEnterPressedThisTick() { return this.enterPressedThisTick; }
}