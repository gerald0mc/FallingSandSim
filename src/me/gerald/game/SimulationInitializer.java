package me.gerald.game;

import javax.swing.*;

public class SimulationInitializer {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setTitle(Constants.GAME_TITLE + " " + Constants.GAME_VERSION);
        Simulation gamePanel = new Simulation();
        frame.add(gamePanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
