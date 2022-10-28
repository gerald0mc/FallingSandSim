package me.gerald.game;

import javax.swing.*;

public class GameInitializer {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setTitle(GameConstants.GAME_TITLE + " " + GameConstants.GAME_VERSION);
        Game gamePanel = new Game();
        frame.add(gamePanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
