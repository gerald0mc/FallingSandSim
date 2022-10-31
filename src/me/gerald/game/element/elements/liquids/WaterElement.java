package me.gerald.game.element.elements.liquids;

import me.gerald.game.element.Element;
import me.gerald.game.element.ElementType;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;

public class WaterElement extends Element {
    public WaterElement() {
        super("Water", ElementType.LIQUID, KeyEvent.VK_E, new Color(68, 99, 231));
        this.inertialResistance = 0.01f;
        this.burnResistance = 0.4f;
        this.density = 0.1f;
        this.corrodeStrength = 0.1f;
        this.corrodeResistance = 0.2f;
    }

    public WaterElement(int spawnX, int spawnY) {
        super("Water", ElementType.LIQUID, KeyEvent.VK_E, new Color(68, 99, 231));
        this.x = spawnX;
        this.y = spawnY;
        this.inertialResistance = 0.01f;
        this.burnResistance = 0.4f;
        this.density = 0.1f;
        this.corrodeStrength = 0.1f;
        this.corrodeResistance = 0.2f;
    }

    @Override
    public void performCheck(List<List<Element>> elements) {
        performDrop(elements);
        if (!topHeightCheck() || !bottomHeightCheck() || !leftWidthCheck() || !rightWidthCheck()) return;
        Element upElement = elements.get(y - 1).get(x);
        Element leftElement = elements.get(y).get(x - 1);
        Element rightElement = elements.get(y).get(x + 1);
        Element downElement = elements.get(y - 1).get(x);
        Element[] elementArray = new Element[] {upElement, leftElement, rightElement, downElement};
    }
}
