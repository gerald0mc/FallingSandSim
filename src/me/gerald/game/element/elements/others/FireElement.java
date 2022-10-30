package me.gerald.game.element.elements.others;

import me.gerald.game.Game;
import me.gerald.game.element.Element;
import me.gerald.game.element.ElementType;
import me.gerald.game.element.elements.gases.SmokeElement;
import me.gerald.game.element.elements.liquids.LavaElement;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;

public class FireElement extends Element {
    public float chanceToPutOut = 0.2f;

    public FireElement() {
        super("Fire", ElementType.GAS, KeyEvent.VK_F, new Color(250, 173, 6));
        this.tempStrength = 0.4f;
        this.inertialResistance = 0.1f;
        this.density = 0.2f;
    }

    public FireElement(int spawnX, int spawnY) {
        super("Fire", ElementType.GAS, KeyEvent.VK_F, new Color(250, 173, 6));
        this.x = spawnX;
        this.y = spawnY;
        this.tempStrength = 0.4f;
        this.inertialResistance = 0.1f;
        this.density = 0.2f;
    }

    @Override
    public void performCheck(List<List<Element>> elements) {
        performDrop(elements);
        doBurnCheck(elements);
        final double random = Math.random();
        if (random < chanceToPutOut)
            Game.elementManager.setPosition(new SmokeElement(this.x, this.y));
    }
}
