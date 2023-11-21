package src.ui;

import javax.imageio.ImageIO;
import javax.swing.*;

import src.Main;
import src.entity.ArcherTower;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MapRender extends JComponent {

    private static final int CELL_SIZE = 64;
    private char[][] map;
    private Map<String, BufferedImage> images;
    private GameWindow gameWindow;

    public MapRender(char[][] map, GameWindow gameWindow) {
        this.map = map;
        this.images = new HashMap<>();
        this.gameWindow = gameWindow;
        preloadImages();
    }

    private void preloadImages() {
        loadImage("0", "res/img/grass.png");
        loadImage("1", "res/img/stone.png");
        loadImage("2", "res/img/ground.png");
        loadImage("3", "res/img/castle.png");
        loadImage("4", "res/img/cave.png");
        loadImage("default", "res/img/default.png");
        loadImage("archer", "res/towers/starly.png");
        loadImage("bomb", "res/towers/magnemite.png");

    }

    private void loadImage(String type, String imagePath) {
        try {
            BufferedImage image = ImageIO.read(new File(imagePath));
            images.put(type, image);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        BufferedImage image;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                switch (map[i][j]) {
                    case '0':
                        image = images.get("0");
                        break;
                    case '1':
                        image = images.get("1");
                        break;
                    case '2':
                        image = images.get("2");
                        break;
                    case '3':
                        image = images.get("3");
                        break;
                    case '4':
                        image = images.get("4");
                        break;
                    default:
                        image = images.get("default");
                        break;
                }
                int x = j * CELL_SIZE;
                int y = i * CELL_SIZE;
                g.drawImage(image, x, y, CELL_SIZE, CELL_SIZE, null);
                g.setColor(Color.BLACK);
                g.drawRect(x, y, CELL_SIZE, CELL_SIZE);

                // THE CASTLE HEALTH BAR
                if (map[i][j] == '3') {
                    Main main = gameWindow.getMain();
                    int yLife = y - 20;
                    int xLife = x;
                    int sizeLifeW = CELL_SIZE;
                    int sizeLifeH = 10;
                    int maxLife = main.getCastle().getMAXLIFE();
                    int actLife = main.getCastle().getLife();
                    float percentLife = Math.max(0, Math.min(100, ((float) actLife / maxLife) * 100));

                    g.setColor(Color.RED);
                    g.fillRect(xLife, yLife, sizeLifeW, sizeLifeH);
                    g.setColor(Color.GREEN);
                    g.fillRect(xLife, yLife, (int) ((percentLife / 100) * sizeLifeW), sizeLifeH);
                    g.setColor(Color.BLACK);
                    g.drawRect(xLife, yLife, sizeLifeW, sizeLifeH);
                }
            }
        }

        // BUTTONS FOR TOWERS
        JButton btnArcher = new JButton();
        btnArcher.setIcon(new javax.swing.ImageIcon(images.get("archer")));
        btnArcher.setBounds(50, 600, 100, 100);
        btnArcher.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gameWindow.setSelectTower("archer");
            }
        });
        add(btnArcher);

        JButton btnBomb = new JButton();
        btnBomb.setIcon(new javax.swing.ImageIcon(images.get("bomb")));
        btnBomb.setBounds(200, 600, 100, 100);
        btnBomb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gameWindow.setSelectTower("bomb");
            }
        });
        add(btnBomb);
    }

}
