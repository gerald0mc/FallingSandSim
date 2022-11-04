package me.gerald.game.simulation.element.elements.gases;

import me.gerald.game.simulation.element.Element;
import me.gerald.game.simulation.element.ElementType;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;

public class SmokeElement extends Element {
    static int min = 60;
    static int max = 110;
    static int ranNum = min + (int) (Math.random() * ((max-min) + 1));

    public SmokeElement() {
        super("Smoke", ElementType.GAS, KeyEvent.VK_S, new Color(ranNum, ranNum, ranNum));
        this.density = -0.5f;
        this.inertialResistance = -0.4f;
    }

    public SmokeElement(int spawnX, int spawnY) {
        super("Smoke", ElementType.GAS, KeyEvent.VK_S, new Color(ranNum, ranNum, ranNum));
        this.x = spawnX;
        this.y = spawnY;
        this.density = -0.5f;
        this.inertialResistance = -0.4f;
    }

    @Override
    public void performCheck(List<List<Element>> elements) {
        checkGasPixel(elements);
    }
}
