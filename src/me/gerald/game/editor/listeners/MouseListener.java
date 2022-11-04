package me.gerald.game.editor.listeners;

import me.gerald.game.editor.Cell;
import me.gerald.game.editor.ShapeEditor;
import me.gerald.game.simulation.element.Element;
import me.gerald.game.simulation.element.elements.solids.movable.SandElement;

import javax.swing.event.MouseInputListener;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Map;

public class MouseListener implements MouseInputListener {
    public int x;
    public int y;

    @Override
    public void mousePressed(MouseEvent e) { }

    @Override
    public void mouseReleased(MouseEvent e) { }

    @Override
    public void mouseMoved(MouseEvent e) {
        this.x = e.getX();
        this.y = e.getY();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getX() < 300) {
            for (List<Cell> list : ShapeEditor.editorBoard) {
                for (Cell cell : list) {
                    if (cell.isInside(e.getX(), e.getY())) {
                        cell.element = ShapeEditor.currentElement;
                        break;
                    }
                }
            }
        } else {
            for (Map.Entry<Element, Integer> entry : ShapeEditor.selectionElements.entrySet()) {
                if (isInside(e.getX(), e.getY(), 302, entry.getValue(), 10, 10)) {
                    ShapeEditor.currentElement = entry.getKey();
                    break;
                }
            }
        }
    }

    public boolean isInside(int mouseX, int mouseY, int x, int y, int width, int height) {
        return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height;
    }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }

    @Override
    public void mouseDragged(MouseEvent e) { }
}
