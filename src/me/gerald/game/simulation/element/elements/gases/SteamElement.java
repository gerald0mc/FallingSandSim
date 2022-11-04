package me.gerald.game.simulation.element.elements.gases;

import me.gerald.game.simulation.element.Element;
import me.gerald.game.simulation.element.ElementType;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;

public class SteamElement extends Element {
    public SteamElement() {
        super("Steam", ElementType.GAS, KeyEvent.VK_A, new Color(232, 227, 227));
        this.density = -0.3f;
        this.inertialResistance = -0.2f;
    }

    public SteamElement(int spawnX, int spawnY) {
        super("Steam", ElementType.GAS, KeyEvent.VK_A, new Color(232, 227, 227));
        this.x = spawnX;
        this.y = spawnY;
        this.density = -0.3f;
        this.inertialResistance = -0.2f;
    }

    @Override
    public void performCheck(List<List<Element>> elements) {
        checkGasPixel(elements);
    }
}
