package src.ui;

import javax.swing.*;
import src.Main;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class StartingMenu extends JPanel {
    protected JButton btnStart;
    protected JButton btnHowToPlay;
    protected JButton btnExit;
    protected BufferedImage backgroundImage;

    public StartingMenu(Main main) {
        // Load the background image
        setLayout(new GridBagLayout());
        
        // CONSTRAINTS
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(100, 10, 100, 10);

        // BUTTON START
        btnStart = new JButton("Launch");
        btnStart.setPreferredSize(new Dimension(400, 100));
        btnStart.setFont(new Font("Arial", Font.BOLD, 50));
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.runGame();
            }
        });
        add(btnStart, gbc);

        // BUTTON HTP
        btnHowToPlay = new JButton("How to play?");
        btnHowToPlay.setPreferredSize(new Dimension(400, 100));
        btnHowToPlay.setFont(new Font("Arial", Font.BOLD, 50));
        btnHowToPlay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.htpDisplay();
            }
        });
        add(btnHowToPlay, gbc);

        // BUTTON EXIT
        btnExit = new JButton("Exit");
        btnExit.setPreferredSize(new Dimension(400, 100));
        btnExit.setFont(new Font("Arial", Font.BOLD, 50));
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        add(btnExit, gbc);
    }
}
