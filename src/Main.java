package src;

import java.util.List;

import javax.crypto.Mac;
import javax.swing.SwingUtilities;

import java.awt.Container;
import java.util.ArrayList;
import src.entity.AEnemy;
import src.entity.Piafabec;
import src.entity.ATower;
import src.entity.Castle;
import src.entity.Machoc;
import src.entity.ArcherTower;
import src.entity.BombTower;
import src.ui.EnemyRender;
import src.ui.GameMap;
import src.ui.GameWindow;
import src.ui.PauseMenu;
import src.ui.HTPMenu;
import src.ui.MapRender;
import src.ui.StartingMenu;
import java.util.Iterator;
import src.ui.TowerRender;
import src.util.WaveLogic;

public class Main implements Runnable {
    protected List<AEnemy> enemies = new ArrayList<>();
    protected List<ATower> towers = new ArrayList<>();
    protected GameWindow gameWindow;
    protected StartingMenu startingMenu;
    protected PauseMenu pauseMenu;
    protected EnemyRender enemyRender;
    protected TowerRender towerRender;
    protected WaveLogic waveLogic;
    protected MapRender mapRender;
    protected HTPMenu htpMenu;
    protected GameMap gameMap;
    protected Thread gameThread;
    protected final double FPS = 120.0;
    protected final double UPS = 60.0;
    protected final int startX = 0;
    protected final int startY = 192;
    public boolean isPaused = false;
    public boolean isEnd = false;
    protected int playerMoney;
    Castle castle = new Castle(100);

    public Main() {
        initialize();
        System.out.println("START");
    }

    public void pauseGame() {
        isPaused = true;
        System.out.println("Game Paused");
    }

    public void resumeGame() {
        isPaused = false;
        System.out.println("Game Resumed");
    }

    public boolean getisPaused() {
        return isPaused;
    }

    public GameWindow getGameWindow() {
        return gameWindow;
    }

    public void changeToStartingMenu() {
        this.isPaused = false;
        this.isEnd = true;
        this.gameWindow.setContentPane(startingMenu);
    }

    public void endingGame() {
        System.out.println("END");
        deleteAllEnemy();
        deleteAllTowers();
        isEnd = false;
        castle.setLife(100);
        updateGame();
        renderGame();
        boolean wave1Spawned = false;
        boolean wave2Spawned = false;
        boolean wave3Spawned = false;
        boolean wave4Spawned = false;
        boolean wave5Spawned = false;
    }

    public void deleteAllEnemy() {
        enemies.removeAll(enemies);
    }

    public void deleteAllTowers() {
        towers.removeAll(towers);
    }

    public void initialize() {
        // WINDOW
        this.gameWindow = new GameWindow("./res/level/level1.txt", this);

        // FRAMES
        this.startingMenu = new StartingMenu(this);
        this.enemyRender = new EnemyRender(enemies);
        this.towerRender = new TowerRender(towers);
        this.pauseMenu = new PauseMenu(this, gameWindow);
        pauseMenu.setVisible(false);
        this.mapRender = new MapRender(this.gameWindow.getMap(), gameWindow);
        this.gameMap = new GameMap(mapRender, enemyRender, towerRender, pauseMenu);
        this.gameWindow.setContentPane(startingMenu);
        this.gameWindow.revalidate();
        this.gameWindow.setVisible(true);
    }

    public void runGame() {
        gameThread = new Thread(this) {
        };
        gameThread.start();
    }

    @Override
    public void run() {
        pauseMenu.setVisible(false);
        double timePerFrame = 1000000000.0 / FPS;
        double timeperUpdate = 1000000000.0 / UPS;

        long start = System.nanoTime();
        long lastFrame = start;
        long lastUpdate = start;
        long lastTimeCheck = System.currentTimeMillis();

        int frames = 0;
        int updates = 0;

        int chrono = 120;
        // WAVE MANAGEMENT
        boolean wave1Spawned = false;
        boolean wave2Spawned = false;
        boolean wave3Spawned = false;
        boolean wave4Spawned = false;
        boolean wave5Spawned = false;

        long lastChronoDecrease = System.nanoTime();
        long chronoDecreaseInterval = 1000000000;

        // CHANGE CONTENT FROM START MENU TO MAP GAME
        this.gameWindow.setContentPane(gameMap);
        this.gameWindow.revalidate();

        // FILL LIST OF ENEMY
        // ADD WAVES
        this.waveLogic = new WaveLogic(this);

        // TOWERS
        // this.towers.add(new ArcherTower(10, 2));

        while (true) {
            if (chrono == 0) {
                System.out.println("VICTORY");
                endingGame();
                changeToStartingMenu();
                break;
            }
            if (isEnd == true) {
                endingGame();
                break;
            } else if (isPaused == false) {

                // WAVES MANAGEMENT
                if (chrono == 120 && wave1Spawned == false) {
                    waveLogic.wave1();
                    wave1Spawned = true;
                }
                if (chrono == 100 && wave2Spawned == false) {
                    waveLogic.wave2();
                    wave2Spawned = true;
                }
                if (chrono == 80 && wave3Spawned == false) {
                    waveLogic.wave3();
                    wave3Spawned = true;
                }
                if (chrono == 60 && wave4Spawned == false) {
                    waveLogic.wave4();
                    wave4Spawned = true;
                }
                if (chrono == 40 && wave5Spawned == false) {
                    waveLogic.wave5();
                    wave5Spawned = true;
                }
                if (wave5Spawned == true && enemies.isEmpty()) {
                    System.out.println("VICTORY");
                    endingGame();
                    changeToStartingMenu();
                    break;
                }
                // Render the game Chrono
                if (System.nanoTime() - lastChronoDecrease >= chronoDecreaseInterval) {
                    chrono--;
                    lastChronoDecrease = System.nanoTime();
                }

                // Render the game graphics, draw towers, enemies, background, etc.
                if (System.nanoTime() - lastFrame >= timePerFrame) {
                    renderGame();

                    lastFrame = System.nanoTime();
                    frames++;
                }

                // Update game logic, handle input, move units, check for collisions, etc.
                if (System.nanoTime() - lastUpdate >= timeperUpdate) {
                    updateGame();

                    lastUpdate = System.nanoTime();
                    updates++;
                }

                if (System.currentTimeMillis() - lastTimeCheck >= 1000) {
                    int minutes = chrono / 60;
                    int seconds = chrono % 60;
                    System.out.println(
                            "FPS: " + frames + " | UPS: " + updates + " | CHRONO: " + minutes + "m " + seconds + "s");
                    frames = 0;
                    updates = 0;
                    lastTimeCheck = System.currentTimeMillis();
                }
                if (castle.getLife() <= 0) {
                    System.out.println("GAME OVER");
                    changeToStartingMenu();
                    endingGame();
                    break;
                }
            } else {
                lastChronoDecrease = System.nanoTime();
            }

        }

    }

    private void enemyRemoved(AEnemy enemy) {
        castle.looselife(enemy.getDamage());
        System.out.println("Castle life : " + castle.getLife());
    }

    private void updateGame() {
        ArrayList<AEnemy> enemyDead = new ArrayList<AEnemy>();
        for (ATower tower : towers) {
            int targetnumber = tower.Targetnumber();
            if (tower.isReadyToAttack()) {
                for (AEnemy enemy : enemies) {
                    if (targetnumber <= 0) {
                        break;
                    }
                    if (tower.isInRange(enemy)) {
                        tower.dealDamage(enemy);
                        if (enemy.getHealth() <= 0) {
                            this.playerMoney = gameMap.getMoney();
                            this.playerMoney += enemy.getGold();
                            gameMap.setMoney(playerMoney);
                            enemyDead.add(enemy);
                        }
                        targetnumber--;
                        tower.resetCooldown();
                    }
                }
            }
        }
        if (!enemies.isEmpty()) {
            for (AEnemy enemy : enemies) {
                enemy.move(AEnemy.DIR.RIGHT);
                // System.out.println("test");
                if (enemy.getX() >= 1670) {
                    enemyRemoved(enemy);
                    enemyDead.add(enemy);
                    System.out.println("Defend defend, we are being attacked !");
                }
            }
            enemies.removeAll(enemyDead);
        }
    }

    public void addEnemy(AEnemy enemy) {
        this.enemies.add(enemy);
        enemyRender.repaint();
    }

    public void addTower(ATower tower) {
        this.towers.add(tower);
        System.out.println(towers);
        towerRender.repaint();
    }

    private void renderGame() {
        SwingUtilities.invokeLater(() -> {
            gameMap.repaint();
        });
    }

    public void htpDisplay() {
        String filePath = "./res/how_to_play_text/HTP.txt";
        HTPMenu htppanel = new HTPMenu(filePath, gameWindow, this);
        this.gameWindow.setContentPane(htppanel);
        this.gameWindow.revalidate();
    }

    public Castle getCastle() {
        return castle;
    }

    public static void main(String[] args) {
        Main game = new Main();
    }
}