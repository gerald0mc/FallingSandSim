package me.gerald.game.simulation.element.elements.others;

import me.gerald.game.simulation.Simulation;
import me.gerald.game.simulation.element.Element;
import me.gerald.game.simulation.element.ElementType;
import me.gerald.game.simulation.element.elements.gases.SmokeElement;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;

public class FireElement extends Element {
    //The lower, the less likely
    public float chanceToPutOut = 0.1f;

    public FireElement() {
        super("Fire", ElementType.GAS, KeyEvent.VK_F, new Color(250, 173, 6));
        this.burnStrength = 0.4f;
        this.inertialResistance = 0.2f;
        this.density = 0.3f;
    }

    public FireElement(int spawnX, int spawnY) {
        super("Fire", ElementType.GAS, KeyEvent.VK_F, new Color(250, 173, 6));
        this.x = spawnX;
        this.y = spawnY;
        this.burnStrength = 0.4f;
        this.inertialResistance = 0.1f;
        this.density = 0.2f;
    }

    //Might use this later when I add elements like fuel/gasoline
    public FireElement(int spawnX, int spawnY, float customPutOut) {
        super("Fire", ElementType.GAS, KeyEvent.VK_F, new Color(250, 173, 6));
        this.x = spawnX;
        this.y = spawnY;
        this.burnStrength = 0.4f;
        this.inertialResistance = 0.1f;
        this.density = 0.2f;
        this.chanceToPutOut = customPutOut;
    }

    @Override
    public void performCheck(List<List<Element>> elements) {
        performDrop(elements);
        performElementCheck(elements, CheckType.FIRE);
        final double random = Math.random();
        if (random < chanceToPutOut)
            Simulation.elementManager.setPosition(new SmokeElement(this.x, this.y));
    }
}
