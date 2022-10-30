package me.gerald.game;

import me.gerald.game.element.Element;
import me.gerald.game.element.ElementManager;
import me.gerald.game.element.elements.liquids.WaterElement;
import me.gerald.game.element.elements.others.AirElement;
import me.gerald.game.element.elements.solids.movable.SandElement;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class Game extends JPanel implements Runnable {
    public final int tileSize = 1; //1x1 pixels

    private long lastTick = System.currentTimeMillis();

    Thread gameThread;

    public static ElementManager elementManager = null;
    public static MouseListener mouseListener = new MouseListener();
    public static KeyBoardListener keyBoardListener = new KeyBoardListener();

    public static List<String> out = new LinkedList<>();

    public Game() {
        this.setPreferredSize(new Dimension(GameConstants.SCREEN_WIDTH, GameConstants.SCREEN_HEIGHT));
        this.setBackground(Color.GRAY);
        this.setDoubleBuffered(true);
        this.addMouseListener(mouseListener);
        this.addMouseMotionListener(mouseListener);
        this.addKeyListener(keyBoardListener);
        this.setFocusable(true);
        elementManager = new ElementManager();
        System.out.println("Initialized the ElementManager!");
        startLoop();
        out.add("Welcome to the simulation!");
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
                    Element spawnElement = elementManager.returnSpawnElement(keyBoardListener.selectedKeyCode, mouseListener.getX() + x, mouseListener.getY() + y);
                    if (!spawnElement.isOnScreen() || !(elementManager.elements.get(spawnElement.y).get(spawnElement.x) instanceof AirElement)) continue;
                    elementManager.elements.get(spawnElement.y).set(spawnElement.x, spawnElement);
                }
            }
        }
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;
        for (List<Element> list : elementManager.elements) {
            for (Element element : list) {
                graphics2D.setColor(element.currentColor);
                graphics2D.drawRect(element.x, element.y, tileSize, tileSize);
            }
        }
        int yOffset = GameConstants.SCREEN_HEIGHT - ((graphics2D.getFont().getSize() + 2) * 10);
        graphics2D.setColor(Color.WHITE);
        for (String str : out) {
            graphics2D.drawString(str, 2, yOffset);
            yOffset += graphics2D.getFont().getSize() + 2;
        }
        graphics2D.dispose();
    }

    public void startLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }
}
