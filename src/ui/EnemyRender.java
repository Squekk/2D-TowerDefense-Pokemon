package src.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import src.entity.AEnemy;
import src.entity.Machoc;
import src.entity.Piafabec;

public class EnemyRender extends JComponent {

    private List<AEnemy> enemies;
    protected GameMap money;

    public EnemyRender(List<AEnemy> enemies) {
        this.enemies = enemies;
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (!enemies.isEmpty()) {
            for (AEnemy enemy : enemies) {
                String imagePath;
                if (enemy instanceof Piafabec) {
                    imagePath = "res/enemies/spearow.png";
                } else if (enemy instanceof Machoc) {
                    imagePath = "res/enemies/machop.png";
                } else {
                    imagePath = "";
                }

                BufferedImage enemyImage;
                try {
                    enemyImage = ImageIO.read(new File(imagePath));
                } catch (IOException e) {
                    e.printStackTrace();
                    continue;
                }

                int x = (int) enemy.getX();
                int y = (int) enemy.getY();
                int size = 64;

                // ROTATE CLOCKWISE
                // enemyImage = rotateImage90Degrees(enemyImage);
                g.drawImage(enemyImage, x, y, size, size, null);

                // THE HEALTH BAR
                int yLife = y - 5;
                int xLife = x;
                int sizeLifeW = size;
                int sizeLifeH = 5;
                int maxLife = enemy.getMAX_HEALTH();
                int actLife = enemy.getHealth();
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

    private BufferedImage rotateImage90Degrees(BufferedImage originalImage) {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        BufferedImage rotatedImage = new BufferedImage(height, width, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotatedImage.createGraphics();
        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.PI / 2, width / 2.0, height / 2.0);
        g2d.setTransform(transform);
        g2d.drawImage(originalImage, 0, 0, null);
        g2d.dispose();

        return rotatedImage;
    }
}