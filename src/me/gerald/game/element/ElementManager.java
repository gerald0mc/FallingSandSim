package me.gerald.game.element;

import me.gerald.game.Game;
import me.gerald.game.GameConstants;
import me.gerald.game.element.elements.gases.SmokeElement;
import me.gerald.game.element.elements.liquids.LavaElement;
import me.gerald.game.element.elements.liquids.WaterElement;
import me.gerald.game.element.elements.others.AirElement;
import me.gerald.game.element.elements.others.FireElement;
import me.gerald.game.element.elements.solids.movable.DirtElement;
import me.gerald.game.element.elements.solids.movable.SandElement;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ElementManager {
    public List<Element> elements = new LinkedList<>();
    public Map<Class<?>, Element> elementMap = new HashMap<>();
    public List<List<Element>> screenElements = new LinkedList<>();

    public ElementManager() {
        for (int y = 0; y < GameConstants.SCREEN_HEIGHT; y++) {
            screenElements.add(new LinkedList<>());
            for (int x = 0; x < GameConstants.SCREEN_WIDTH; x++) {
                screenElements.get(y).add(new AirElement(x, y));
            }
        }
        elements.add(new AirElement());
        elements.add(new SandElement());
        elements.add(new WaterElement());
        elements.add(new DirtElement());
        elements.add(new LavaElement());
        elements.add(new FireElement());
        elements.add(new SmokeElement());
        elements.forEach(element -> elementMap.put(element.getClass(), element));
    }

    public void swapPositions(int startX, int startY, int endX, int endY) {
        if (endY > screenElements.size() || endX > screenElements.get(0).size()) return;
        Element startTarget = screenElements.get(startY).get(startX);
        startTarget.x = endX;
        startTarget.y = endY;
        Element endTarget = screenElements.get(endY).get(endX);
        endTarget.x = startX;
        endTarget.y = startY;
        screenElements.get(endY).set(endX, startTarget);
        screenElements.get(startY).set(startX, endTarget);
    }

    public void setPosition(Element replacement) {
        if (replacement.x > screenElements.size() || replacement.y > screenElements.get(0).size()) return;
        screenElements.get(replacement.y).set(replacement.x, replacement);
    }
}
