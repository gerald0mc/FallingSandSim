package me.gerald.game.editor;

import me.gerald.game.editor.listeners.MouseListener;
import me.gerald.game.simulation.Simulation;
import me.gerald.game.simulation.element.Element;
import me.gerald.game.simulation.element.elements.others.AirElement;
import me.gerald.game.simulation.element.elements.solids.movable.SandElement;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class ShapeEditor extends JPanel implements Runnable {
    Thread editorThread;

    public final MouseListener mouseListener = new MouseListener();

    public static List<List<Cell>> editorBoard = new LinkedList<>();
    public static HashMap<Element, Integer> selectionElements = new LinkedHashMap<>();

    public static Element currentElement = new SandElement();

    public ShapeEditor() {
        this.setPreferredSize(new Dimension(400, 250));
        this.setBackground(Color.LIGHT_GRAY);
        this.setDoubleBuffered(true);
        this.addMouseListener(mouseListener);
        this.addMouseMotionListener(mouseListener);
        this.setFocusable(true);
        for (int y = 0 ; y < (250 / 10); y++) {
            editorBoard.add(y, new LinkedList<>());
            for (int x = 0; x < 300; x += 10) {
                editorBoard.get(y).add(new Cell(x, y * 10));
            }
        }
        int yOffset = 30;
        for (Element element : Simulation.elementManager.elements) {
            selectionElements.put(element, yOffset);
            //Standard font height
            yOffset += 13;
        }
        editorThread = new Thread(this);
        editorThread.start();
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g2D = (Graphics2D) graphics;
        for (List<Cell> list : editorBoard) {
            for (Cell cell : list) {
                boolean airElement = cell.element instanceof AirElement;
                if (cell.isInside(mouseListener.x, mouseListener.y) || !airElement) {
                    g2D.setColor(airElement ? Color.BLACK : cell.element.getDefaultColor());
                    g2D.fillRect(cell.x, cell.y, 10, 10);
                }
                g2D.setColor(Color.BLACK);
                g2D.drawRect(cell.x, cell.y, 10, 10);
            }
        }
        g2D.setColor(currentElement.getDefaultColor());
        g2D.fillRect(302, 6, 10, 10);
        g2D.setColor(Color.BLACK);
        g2D.drawString(currentElement.getName(), 315, 16);
        for (Map.Entry<Element, Integer> entry : selectionElements.entrySet()) {
            g2D.setColor(entry.getKey().getDefaultColor());
            g2D.fillRect(302, entry.getValue(), 10, 10);
            g2D.setColor(Color.BLACK);
            g2D.drawString(entry.getKey().getName(), 315, entry.getValue() + 10);
        }
        g2D.dispose();
    }

    @Override
    public void run() {
        while (editorThread != null) {
            repaint();
        }
    }
}
