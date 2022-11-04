package me.gerald.game.simulation.element.elements.solids.movable;

import me.gerald.game.simulation.element.Element;
import me.gerald.game.simulation.element.ElementType;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;

public class SaltElement extends Element {
    public SaltElement() {
        super("Salt", ElementType.MOVABLE_SOLID, KeyEvent.VK_X, new Color(249, 228, 228));
        this.inertialResistance = 0.5f;
        this.density = 0.2f;
        this.burnResistance = 0.2f;
        this.corrodeResistance = 0.1f;
    }

    public SaltElement(int spawnX, int spawnY) {
        super("Salt", ElementType.MOVABLE_SOLID, KeyEvent.VK_X, new Color(249, 228, 228));
        this.x = spawnX;
        this.y = spawnY;
        this.inertialResistance = 0.5f;
        this.density = 0.2f;
        this.burnResistance = 0.2f;
        this.corrodeResistance = 0.1f;
    }

    @Override
    public void performCheck(List<List<Element>> elements) {
        performDrop(elements);
    }
}
