package me.gerald.game;

import me.gerald.game.element.Element;
import me.gerald.game.element.ElementManager;
import me.gerald.game.element.elements.solids.movable.SandElement;

import javax.swing.*;
import java.awt.*;

public class Game extends JPanel implements Runnable {
    public final int tileSize = 1; //1x1 pixels

    final int FPS = 60;
    private int actualFPS;

    Thread gameThread;

    public static ElementManager elementManager = null;

    public Game() {
        elementManager = new ElementManager();
        ElementManager.instance = elementManager;
        System.out.println("Initialized the ElementManager!");
        this.setPreferredSize(new Dimension(GameConstants.SCREEN_WIDTH, GameConstants.SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        startLoop();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000f / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int debugFPS = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;
            if (delta >= 1) {
                update();
                repaint();
                delta--;
                debugFPS++;
            }
            if (timer >= 1000000000) {
                actualFPS = debugFPS;
                debugFPS = 0;
                timer = 0;
            }
        }
    }

    public void update() {
        for (int currentY = 0; currentY < GameConstants.SCREEN_HEIGHT; currentY++) {
            for (int currentX = 0; currentX < GameConstants.SCREEN_WIDTH; currentX++) {
                Element element = elementManager.elements[currentY][currentX];
                element.performCheck(elementManager.elements);
            }
        }
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;
        if (elementManager != null && elementManager.elements != null) {
            for (int currentY = 0; currentY < GameConstants.SCREEN_HEIGHT; currentY++) {
                for (int currentX = 0; currentX < GameConstants.SCREEN_WIDTH; currentX++) {
                    Element element = elementManager.elements[currentY][currentX];
                    graphics2D.setColor(element.currentColor);
                    graphics2D.drawRect(element.x, element.y, tileSize, tileSize);
                }
            }
        }
        graphics2D.dispose();
    }

    public void startLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public int getFPS() {
        return actualFPS;
    }
}
