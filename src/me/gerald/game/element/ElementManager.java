package me.gerald.game.element;

import me.gerald.game.Game;
import me.gerald.game.GameConstants;
import me.gerald.game.element.elements.liquids.WaterElement;
import me.gerald.game.element.elements.others.AirElement;
import me.gerald.game.element.elements.solids.movable.DirtElement;
import me.gerald.game.element.elements.solids.movable.SandElement;

import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.List;

public class ElementManager {
    public List<List<Element>> elements = new LinkedList<>();

    public ElementManager() {
        for (int y = 0; y < GameConstants.SCREEN_HEIGHT; y++) {
            elements.add(new LinkedList<>());
            for (int x = 0; x < GameConstants.SCREEN_WIDTH; x++) {
                elements.get(y).add(new AirElement(x, y));
            }
        }
    }

    public void swapPositions(int startX, int startY, int endX, int endY) {
        if (endY > elements.size() || endX > elements.get(0).size()) return;
        Element startTarget = elements.get(startY).get(startX);
        startTarget.x = endX;
        startTarget.y = endY;
        Element endTarget = elements.get(endY).get(endX);
        endTarget.x = startX;
        endTarget.y = startY;
        elements.get(endY).set(endX, startTarget);
        elements.get(startY).set(startX, endTarget);
    }

    public Element returnSpawnElement(int selectedKeyCode, int mouseX, int mouseY) {
        return switch (selectedKeyCode) {
            case KeyEvent.VK_Q -> new AirElement(mouseX, mouseY);
            case KeyEvent.VK_E -> new WaterElement(mouseX, mouseY);
            case KeyEvent.VK_R -> new DirtElement(mouseX, mouseY);
            default -> new SandElement(mouseX, mouseY);
        };
    }
}
