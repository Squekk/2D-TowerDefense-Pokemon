package src.ui;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import src.Main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HTPMenu extends JPanel {
    protected JTextArea textArea;
    protected Main main;
    protected GameWindow gameWindow;
    protected JButton btnMenu;

    public HTPMenu(String filePath, GameWindow gameWindow, Main main) {
        this.gameWindow = gameWindow;
        this.main = main;

        // CONSTRAINTS
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(100, 10, 100, 10);

        // TEXTAREA
        this.textArea = new JTextArea();
        this.textArea.setFont(new Font("Arial", Font.PLAIN, 30));
        loadTextFromFile(filePath);
        add(textArea, gbc);

        // BUTTON
        btnMenu = new JButton("Menu");
        btnMenu.setPreferredSize(new Dimension(200, 100));
        btnMenu.setFont(new Font("Arial", Font.BOLD, 50));
        btnMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                main.changeToStartingMenu();
            }
        });
        add(btnMenu, gbc);
        setOpaque(false);
    }

    private void loadTextFromFile(String filePath) {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            textArea.setText(stringBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
