package me.gerald.game.element.elements.gases;

import me.gerald.game.element.Element;
import me.gerald.game.element.ElementType;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Random;

public class SmokeElement extends Element {
    public SmokeElement() {
        super("Smoke", ElementType.GAS, KeyEvent.VK_S, new Color(ranNum, ranNum, ranNum));
        this.density = -0.5f;
        this.inertialResistance = -0.4f;
    }

    static int min = 60;
    static int max = 110;
    static int ranNum = min+(int)(Math.random()*((max-min) + 1));

    public SmokeElement(int spawnX, int spawnY) {
        super("Smoke", ElementType.GAS, KeyEvent.VK_D, new Color(ranNum, ranNum, ranNum));
        this.x = spawnX;
        this.y = spawnY;
        this.density = -0.5f;
        this.inertialResistance = -0.4f;
    }

    @Override
    public void performCheck(List<List<Element>> elements) {
        performRise(elements);
    }
}