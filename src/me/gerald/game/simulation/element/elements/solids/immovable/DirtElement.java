package me.gerald.game.simulation.element.elements.solids.immovable;

import me.gerald.game.simulation.element.Element;
import me.gerald.game.simulation.element.ElementType;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;

public class DirtElement extends Element {
    public DirtElement() {
        super("Dirt", ElementType.IMMOVABLE_SOLID, KeyEvent.VK_R, new Color(110, 52, 2));
        this.inertialResistance = 0.5f;
        this.density = 0.2f;
        this.burnResistance = 0.2f;
        this.corrodeResistance = 0.1f;
    }

    public DirtElement(int spawnX, int spawnY) {
        super("Dirt", ElementType.IMMOVABLE_SOLID, KeyEvent.VK_R, new Color(110, 52, 2));
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
