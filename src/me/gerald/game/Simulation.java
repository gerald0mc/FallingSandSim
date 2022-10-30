package me.gerald.game;

import me.gerald.game.element.Element;
import me.gerald.game.element.ElementManager;
import me.gerald.game.element.elements.others.AirElement;
import me.gerald.game.event.listeners.KeyBoardListener;
import me.gerald.game.event.listeners.MouseListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.List;

public class Simulation extends JPanel implements Runnable {
    public final int tileSize = 1; //1x1 pixels

    private long lastTick = System.currentTimeMillis();

    Thread gameThread;

    public static ElementManager elementManager = null;
    public static MouseListener mouseListener = new MouseListener();
    public static KeyBoardListener keyBoardListener = new KeyBoardListener();

    public static List<String> out = new LinkedList<>();

    public Simulation() {
        this.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
        this.setBackground(Color.LIGHT_GRAY);
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
        for (List<Element> list : elementManager.screenElements) {
            for (Element element : list) {
                element.performCheck(elementManager.screenElements);
            }
        }
        if (mouseListener.isSpawning()) {
            for (int x = 0; x < 5; x++) {
                for (int y = 0; y < 5; y++) {
                    int mouseX = mouseListener.getX();
                    int mouseY = mouseListener.getY();
                    Element spawnElement = null;
                    for (Element element : elementManager.elements) {
                        if (element.getKeyCode() == keyBoardListener.selectedKeyCode) {
                            try {
                                spawnElement = element.getClass().newInstance();
                                spawnElement.x = mouseX + x;
                                spawnElement.y = mouseY + y;
                            } catch (InstantiationException | IllegalAccessException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                    if (spawnElement == null || !spawnElement.isOnScreen() || !(elementManager.screenElements.get(spawnElement.y).get(spawnElement.x) instanceof AirElement)) return;
                    elementManager.screenElements.get(spawnElement.y).set(spawnElement.x, spawnElement);
                }
            }
        }
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;
        for (List<Element> list : elementManager.screenElements) {
            for (Element element : list) {
                graphics2D.setColor(element.currentColor);
                graphics2D.drawRect(element.x, element.y, tileSize, tileSize);
            }
        }
        int yOffsetOut = Constants.SCREEN_HEIGHT - ((graphics2D.getFont().getSize() + 2) * 10);
        graphics2D.setColor(Color.DARK_GRAY);
        if (out.size() > 10) out.remove(0);
        for (String str : out) {
            graphics2D.drawString(str, 2, yOffsetOut);
            yOffsetOut += graphics2D.getFont().getSize() + 2;
        }
        int yOffsetBinds = graphics2D.getFont().getSize() + 1;
        for (Element element : elementManager.elements) {
            graphics2D.drawString(element.getName() + " | " + KeyEvent.getKeyText(element.getKeyCode()), 1, yOffsetBinds);
            yOffsetBinds += graphics2D.getFont().getSize() + 2;
        }
        graphics2D.dispose();
    }

    public void startLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }
}
