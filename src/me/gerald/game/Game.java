package me.gerald.game;

import me.gerald.game.element.Element;
import me.gerald.game.element.ElementManager;
import me.gerald.game.element.elements.other.AirElement;
import me.gerald.game.element.elements.solids.movable.SandElement;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Game extends JPanel implements Runnable {
    public final int tileSize = 1; //1x1 pixels

    private long lastTick = System.currentTimeMillis();

    Thread gameThread;

    public static ElementManager elementManager = null;
    public static MouseListener mouseListener = new MouseListener();

    public Game() {
        this.setPreferredSize(new Dimension(GameConstants.SCREEN_WIDTH, GameConstants.SCREEN_HEIGHT));
        this.setBackground(Color.GRAY);
        this.setDoubleBuffered(true);
        this.addMouseListener(mouseListener);
        this.setFocusable(true);
        elementManager = new ElementManager();
        System.out.println("Initialized the ElementManager!");
        startLoop();
    }

    @Override
    public void run() {
        while (gameThread != null) {
            if (System.currentTimeMillis() - lastTick >= 250 /*4 ticks a second or a tick every 250 milliseconds*/) {
                lastTick = System.currentTimeMillis();
                update();
                repaint();
            }
        }
    }

    public void update() {
        for (List<Element> list : elementManager.elements) {
            for (Element element : list) {
                element.performCheck(elementManager.elements);
            }
        }
        if (mouseListener.isSpawning()) {
            for (int x = 0; x < 5; x++) {
                for (int y = 0; y < 5; y++) {
                    SandElement sandElement = new SandElement(mouseListener.getX() + x, mouseListener.getY() + y);
                    if (!sandElement.isOnScreen() || !(elementManager.elements.get(y).get(x) instanceof AirElement)) continue;
                    elementManager.elements.get(y).set(x, sandElement);
                }
            }
        }
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;
        for (int currentY = 0; currentY != GameConstants.SCREEN_HEIGHT; currentY++) {
            for (int currentX = 0; currentX != GameConstants.SCREEN_WIDTH; currentX++) {
                Element element = elementManager.elements.get(currentY).get(currentX);
                graphics2D.setColor(element.currentColor);
                graphics2D.drawRect(element.x, element.y, tileSize, tileSize);
            }
        }
        graphics2D.dispose();
    }

    public void startLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }
}
