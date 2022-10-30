package me.gerald.game.element.elements.liquids;

import me.gerald.game.element.Element;
import me.gerald.game.element.ElementType;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;

public class LavaElement extends Element {
    public LavaElement() {
        super("Lava", ElementType.LIQUID, KeyEvent.VK_D, new Color(232, 81, 16));
        this.burnStrength = 0.7f;
        this.inertialResistance = 0.7f;
        this.corrodeResistance = 0.5f;
        this.density = 0.4f;
    }

    public LavaElement(int spawnX, int spawnY) {
        super("Lava", ElementType.LIQUID, KeyEvent.VK_D, new Color(232, 81, 16));
        this.x = spawnX;
        this.y = spawnY;
        this.burnStrength = 0.7f;
        this.inertialResistance = 0.7f;
        this.corrodeResistance = 0.5f;
        this.density = 0.4f;
    }

    @Override
    public void performCheck(List<List<Element>> elements) {
        performDrop(elements);
        performElementCheck(elements, CheckType.FIRE);
    }
}
