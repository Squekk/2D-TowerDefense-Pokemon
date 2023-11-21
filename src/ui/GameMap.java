package src.ui;

import javax.swing.*;

import src.Main;

import java.awt.*;

public class GameMap extends JPanel {
    private MapRender mapRender;
    private EnemyRender enemyRender;
    private TowerRender towerRender;
    private PauseMenu pauseMenu;
    protected JTextArea textArea;
    protected JTextPane timer;
    protected int money = 0;
    protected float time;

    public GameMap(MapRender mapRender, EnemyRender enemyRender, TowerRender towerRender, PauseMenu pauseMenu) {
        this.mapRender = mapRender;
        this.enemyRender = enemyRender;
        this.towerRender = towerRender;
        this.pauseMenu = pauseMenu;

        setLayout(new OverlayLayout(this));
        add(pauseMenu);
        this.textArea = new JTextArea("Gold: " + this.money);
        this.textArea.setFont(new Font("Arial", Font.PLAIN, 20));
        this.textArea.setBackground(new Color(0x00, true));
        this.timer = new JTextPane();
        this.timer.setText("TIME" + this.time);
        this.timer.setFont(new Font("Arial", Font.PLAIN, 20));
        this.timer.setBackground(new Color(0x00, true));
        this.timer.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        setLayout(new OverlayLayout(this));
        // add(this.timer);
        // add(this.textArea);
        add(enemyRender);
        add(towerRender);
        add(mapRender);
    }

    public void repaintEnemies() {
        enemyRender.repaint();
    }

    public void repaintMoney() {
        textArea.repaint();
    }

    public int getMoney() {
        return this.money;
    }

    public void setTime(float time) {
        this.time = time;
        this.timer.setText("TIME" + this.time);
    }

    public void setMoney(int money) {
        this.money = money;
        this.textArea.setText("Gold: " + this.money);
    }
}
