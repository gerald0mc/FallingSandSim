package me.gerald.game.element.elements.solids.movable;

import me.gerald.game.Game;
import me.gerald.game.element.Element;
import me.gerald.game.element.ElementType;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;

public class DirtElement extends Element {
    public DirtElement() {
        super("Dirt", ElementType.MOVABLE_SOLID, KeyEvent.VK_R, new Color(110, 52, 2));
        this.inertialResistance = 0.5f;
        this.density = 0.3f;
    }

    public DirtElement(int spawnX, int spawnY) {
        super("Dirt", ElementType.MOVABLE_SOLID, KeyEvent.VK_R, new Color(110, 52, 2));
        this.x = spawnX;
        this.y = spawnY;
        this.inertialResistance = 0.5f;
        this.density = 0.3f;
    }

    @Override
    public void performCheck(List<List<Element>> elements) {
        performDrop(elements);
    }
}
