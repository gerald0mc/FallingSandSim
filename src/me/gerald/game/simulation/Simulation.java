package me.gerald.game.simulation;

import me.gerald.game.editor.ShapeEditor;
import me.gerald.game.simulation.element.Element;
import me.gerald.game.simulation.element.ElementManager;
import me.gerald.game.simulation.element.elements.others.AirElement;
import me.gerald.game.simulation.listeners.KeyBoardListener;
import me.gerald.game.simulation.listeners.MouseListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.List;

public class Simulation extends JPanel implements Runnable {
    public final int tileSize = 1; //1x1 pixels

    Thread gameThread;

    public static ElementManager elementManager = null;
    public static MouseListener mouseListener = new MouseListener();
    public static KeyBoardListener keyBoardListener = new KeyBoardListener();

    public static List<String> out = new LinkedList<>();

    public static JFrame shapeEditorFrame;

    private int FPS = -1;

    public Simulation() {
        this.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addMouseListener(mouseListener);
        this.addMouseMotionListener(mouseListener);
        this.addKeyListener(keyBoardListener);
        this.setFocusable(true);
        elementManager = new ElementManager();
        System.out.println("Initialized the ElementManager!");
        startLoop();
        out.add("Welcome to the simulation!");
        out.add("Created by gerald0mc for fun :D");
        out.add("All the elements are used via the KeyBinds. (See top left)");
        // Shape Editor tingz
        shapeEditorFrame = new JFrame();
        shapeEditorFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        shapeEditorFrame.setResizable(false);
        shapeEditorFrame.setTitle(Constants.GAME_TITLE + " " + Constants.GAME_VERSION + " | Shape Editor");
        ShapeEditor shapeEditorPanel = new ShapeEditor();
        shapeEditorFrame.add(shapeEditorPanel);
        shapeEditorFrame.pack();
        shapeEditorFrame.setLocationRelativeTo(null);
        shapeEditorFrame.setVisible(true);
    }

    @Override
    public void run() {
        int TARGET_FPS = 60;
        double drawInterval = 1000000000f / TARGET_FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int countFPS = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;
            if (delta >= 1) {
                update();
                repaint();
                delta--;
                countFPS++;
            }
            if (timer >= 1000000000) {
                timer = 0;
                FPS = countFPS;
                countFPS = 0;
            }
        }
    }

    public void update() {
        for (int y = 0; y < elementManager.screenElements.size(); y++) {
            for (int x = 0; x < elementManager.screenElements.get(y).size(); x++) {
                Element element = elementManager.screenElements.get(y).get(x);
                element.performCheck(elementManager.screenElements);
            }
        }
        if (mouseListener.isSpawning()) {
            for (int x = 0; x < 5; x++) {
                for (int y = 0; y < 5; y++) {
                    int mouseX = mouseListener.getX();
                    int mouseY = mouseListener.getY();
                    Element spawnElement = null;
                    for (int i = 0; i < elementManager.elements.size(); i++) {
                        Element element = elementManager.elements.get(i);
                        if (element.getKeyCode() == keyBoardListener.selectedKeyCode) {
                            try {
                                spawnElement = element.getClass().newInstance();
                                spawnElement.x = mouseX + x;
                                spawnElement.y = mouseY + y;
                            } catch (InstantiationException | IllegalAccessException e) {
                                throw new RuntimeException(e);
                            }
                            break;
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
        // Pixel Rendering
        for (int y = 0; y < elementManager.screenElements.size(); y++) {
            for (int x = 0; x < elementManager.screenElements.get(y).size(); x++) {
                Element element = elementManager.screenElements.get(y).get(x);
                graphics2D.setColor(element.currentColor);
                graphics2D.fillRect(element.x, element.y, tileSize, tileSize);
            }
        }
        // Element Binds
        graphics2D.setColor(Color.DARK_GRAY);
        int yOffsetBinds = graphics2D.getFont().getSize() + 1;
        for (Element element : elementManager.elements) {
            graphics2D.drawString(element.getName() + " | " + KeyEvent.getKeyText(element.getKeyCode()), 1, yOffsetBinds);
            yOffsetBinds += graphics2D.getFont().getSize() + 2;
        }
        graphics2D.drawString("Shape Editor | =", 1, yOffsetBinds);
        // Out Log
        int yOffsetOut = Constants.SCREEN_HEIGHT - ((graphics2D.getFont().getSize() + 2) * 10);
        graphics2D.setColor(Color.DARK_GRAY);
        if (out.size() > 10) out.remove(0);
        for (String str : out) {
            graphics2D.drawString(str, 2, yOffsetOut);
            yOffsetOut += graphics2D.getFont().getSize() + 2;
        }
        graphics2D.dispose();
    }

    public void startLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public int getFPS() {
        return FPS;
    }
}
