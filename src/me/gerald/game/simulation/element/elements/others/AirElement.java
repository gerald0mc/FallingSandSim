package me.gerald.game.simulation.element.elements.others;

import me.gerald.game.simulation.element.Element;
import me.gerald.game.simulation.element.ElementType;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;

public class AirElement extends Element {
    public AirElement() {
        super("Air", ElementType.GAS, KeyEvent.VK_Q, new Color(0, 0, 0, 0));
        this.inertialResistance = 0f;
        this.density = 0f;
    }

    public AirElement(int spawnX, int spawnY) {
        super("Air", ElementType.GAS, KeyEvent.VK_Q, new Color(0, 0, 0, 0));
        this.x = spawnX;
        this.y = spawnY;
        this.inertialResistance = 0f;
        this.density = 0f;
    }

    @Override
    public void performCheck(List<List<Element>> elements) { }
}
