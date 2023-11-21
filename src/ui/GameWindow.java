package src.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import src.Main;
import src.entity.ATower;
import src.entity.ArcherTower;
import src.entity.BombTower;

public class GameWindow extends JFrame implements MouseListener {
    private char[][] map;
    private Main main;
    private PauseMenu pauseMenu;
    private ArrayList<Point> validTile;
    protected String selectTower;

    public GameWindow(String mapFileName, Main main) {
        loadMap(mapFileName);
        this.main = main;
        this.pauseMenu = new PauseMenu(main, this);

        setTitle("Pokemon defense");
        setPreferredSize(new Dimension(2000, 1000));
        setMaximumSize(new Dimension(2000, 1000));
        setMinimumSize(new Dimension(2000, 1000));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        addMouseListener(this);
        keyBindings();
        getValidTile();

    }

    public void getValidTile() {
        validTile = new ArrayList<Point>();
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                if (map[y][x] == '1') {
                    validTile.add(new Point(x, y));
                    // System.out.println("added x" + x + ", y" + y);
                }
            }
        }
    }

    private void keyBindings() {
        InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getRootPane().getActionMap();

        inputMap.put(KeyStroke.getKeyStroke("ESCAPE"), "pause");
        actionMap.put("pause", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!main.isPaused) {
                    main.pauseGame();
                    showPauseMenu();
                } else {
                    hidePauseMenu();
                    main.resumeGame();
                }
            }
        });
    }

    public void refresh() {
        Container contentPane = getContentPane();
        contentPane.remove(this);
        addMouseListener(this);
        keyBindings();
        revalidate();
        repaint();
    }

    private void loadMap(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            int rows = 0;
            int cols = 0;

            // CREATE ARRAY BASED ON FILE
            while ((line = br.readLine()) != null) {
                rows++;
                cols = line.length();
            }
            br.close();

            map = new char[rows][cols];
            // FILL ARRAY WITH FILE CONTENT
            try (BufferedReader br2 = new BufferedReader(new FileReader(fileName))) {
                for (int i = 0; i < rows; i++) {
                    line = br2.readLine();
                    map[i] = line.toCharArray();
                }
                br2.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showPauseMenu() {
        Container contentPane = getContentPane();
        contentPane.add(pauseMenu);
        contentPane.revalidate();
        contentPane.repaint();
        this.refresh();
    }

    private void hidePauseMenu() {
        Container contentPane = getContentPane();
        contentPane.remove(pauseMenu);
        contentPane.revalidate();
        contentPane.repaint();
        this.refresh();
    }

    public void setSelectTower(String selectTower) {
        this.selectTower = selectTower;
    }

    // MOUSE LISTENERS
    @Override
    public void mouseClicked(MouseEvent e) {
        Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
        SwingUtilities.convertPointFromScreen(mouseLocation, this);

        // Adjust for insets
        Insets insets = this.getInsets();
        mouseLocation.x -= insets.left;
        mouseLocation.y -= insets.top;

        int x = (int) mouseLocation.getX() / 64;
        int y = (int) mouseLocation.getY() / 64;

        System.out.println("Mouse Clicked - X: " + x + ", Y: " + y);

        for (Point point : validTile) {
            if (point.getX() >= x && point.getX() <= x && point.getY() >= y && point.getY() <= y) {
                if (selectTower != null) {
                    switch (selectTower) {
                        case "archer":
                            main.addTower(new ArcherTower(x, y));
                            break;
                        case "bomb":
                            main.addTower(new BombTower(x, y));
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    public char[][] getMap() {
        return map;
    }

    public Main getMain() {
        return main;
    }
}
