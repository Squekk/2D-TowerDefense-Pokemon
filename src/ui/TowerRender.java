package src.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JLabel;
import src.entity.ATower;
import src.entity.ArcherTower;
import src.entity.BombTower;

public class TowerRender extends JComponent {
    protected List<ATower> towers = new ArrayList<ATower>();
    protected JLabel imageLabel;
    protected GameMap gameMap;

    public TowerRender(List<ATower> towers) {
        this.towers = towers;
    }

    @Override
    protected void paintComponent(Graphics g) {
        String imagePath;
        for (ATower tower : towers) {
            if (tower instanceof ArcherTower) {
                imagePath = "res/towers/starly.png";
            } else if (tower instanceof BombTower) {
                imagePath = "res/towers/magnemite.png";
            } else {
                imagePath = "res/img/default.png";
            }

            BufferedImage towerImage;
            try {
                towerImage = ImageIO.read(new File(imagePath));
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }

            // PLACE TOWER CENTER OF CELL
            int x = (int) tower.getX() - 16;
            int y = (int) tower.getY() - 16;
            int size = 64;
            g.drawImage(towerImage, x, y, size, size, null);

            // Circle for the range
            int sizeRange = tower.getRange() * 2;
            int xRange = x - (sizeRange - size) / 2;
            int yRange = y - (sizeRange - size) / 2;
            g.setColor(Color.BLACK);
            g.drawOval(xRange, yRange, sizeRange, sizeRange);
        }
    }
}