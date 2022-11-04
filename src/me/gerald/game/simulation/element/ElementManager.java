package me.gerald.game.simulation.element;

import me.gerald.game.simulation.Constants;
import me.gerald.game.simulation.element.elements.gases.SmokeElement;
import me.gerald.game.simulation.element.elements.gases.SteamElement;
import me.gerald.game.simulation.element.elements.liquids.AcidElement;
import me.gerald.game.simulation.element.elements.liquids.LavaElement;
import me.gerald.game.simulation.element.elements.liquids.WaterElement;
import me.gerald.game.simulation.element.elements.others.AirElement;
import me.gerald.game.simulation.element.elements.others.FireElement;
import me.gerald.game.simulation.element.elements.solids.immovable.DirtElement;
import me.gerald.game.simulation.element.elements.solids.movable.SaltElement;
import me.gerald.game.simulation.element.elements.solids.movable.SandElement;

import java.util.LinkedList;
import java.util.List;

public class ElementManager {
    public List<Element> elements = new LinkedList<>();
    public List<List<Element>> screenElements = new LinkedList<>();

    public ElementManager() {
        for (int y = 0; y < Constants.SCREEN_HEIGHT; y++) {
            screenElements.add(new LinkedList<>());
            for (int x = 0; x < Constants.SCREEN_WIDTH; x++) {
                screenElements.get(y).add(new AirElement(x, y));
            }
        }
        //Gases
        elements.add(new AirElement());
        elements.add(new SmokeElement());
        elements.add(new SteamElement());
        elements.add(new FireElement());
        //Liquids
        elements.add(new WaterElement());
        elements.add(new LavaElement());
        elements.add(new AcidElement());
        //Solids
        //Movable
        elements.add(new SaltElement());
        elements.add(new SandElement());
        //Immovable
        elements.add(new DirtElement());
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
