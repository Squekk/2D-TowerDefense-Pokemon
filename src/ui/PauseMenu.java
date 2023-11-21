package src.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import src.Main;

public class PauseMenu extends JPanel {

    protected Main main;
    protected GameWindow gameWindow;
    protected JButton resumeButton;
    protected JButton menuButton;
    protected JButton quitButton;

    public PauseMenu(Main main, GameWindow gameWindow) {
        this.main = main;
        this.gameWindow = gameWindow;

        // CONSTRAINTS
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = 3;
        gbc.gridy = 0; // Déplace le composant vers le haut de la grille
        gbc.anchor = GridBagConstraints.NORTH; 

        // BUTTON RESUME
        resumeButton = new JButton("Resume");
        resumeButton.setPreferredSize(new Dimension(100, 100));
        resumeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                main.resumeGame();
                hidePauseMenu();
            }
        });
        add(resumeButton, gbc);

        // BUTTON MENU
        menuButton = new JButton("Menu");
        menuButton.setPreferredSize(new Dimension(100, 100));
        menuButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                hidePauseMenu();
                main.changeToStartingMenu();
            }
        });
        add(menuButton, gbc);

        // BUTTON QUIT
        quitButton = new JButton("Quit");
        quitButton.setPreferredSize(new Dimension(100, 100));
        quitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        add(quitButton, gbc);

        setVisible(true);
    }

    protected void hidePauseMenu() {
            Container container = gameWindow.getContentPane();
            container.remove(this);
            container.revalidate();
            container.repaint();
            main.getGameWindow().refresh();
    
    }
}
