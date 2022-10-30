package me.gerald.game.element.elements.solids.movable;

import me.gerald.game.Game;
import me.gerald.game.element.Element;
import me.gerald.game.element.ElementType;
import me.gerald.game.element.elements.others.AirElement;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;

public class SandElement extends Element {
    public SandElement() {
        super("Sand", ElementType.MOVABLE_SOLID, KeyEvent.VK_W, new Color(212, 176, 38));
        this.inertialResistance = 0.2f;
        this.density = 0.1f;
        this.burnResistance = 0.3f;
    }
    public SandElement(int spawnX, int spawnY) {
        super("Sand", ElementType.MOVABLE_SOLID, KeyEvent.VK_W, new Color(212, 176, 38));
        this.x = spawnX;
        this.y = spawnY;
        this.inertialResistance = 0.2f;
        this.density = 0.1f;
    }

    @Override
    public void performCheck(List<List<Element>> elements) {
        performDrop(elements);
    }
}
