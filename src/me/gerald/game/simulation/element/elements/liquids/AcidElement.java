package me.gerald.game.simulation.element.elements.liquids;

import me.gerald.game.simulation.Simulation;
import me.gerald.game.simulation.element.Element;
import me.gerald.game.simulation.element.ElementType;
import me.gerald.game.simulation.element.elements.others.AirElement;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;

public class AcidElement extends Element {
    public float chanceToPutOut = 0.1f;

    public AcidElement() {
        super("Acid", ElementType.LIQUID, KeyEvent.VK_Z, new Color(21, 202, 27));
        this.density = 0.3f;
        this.inertialResistance = 0.4f;
        this.corrodeStrength = 0.6f;
        this.corrodeResistance = 0.8f;
    }

    public AcidElement(int spawnX, int spawnY) {
        super("Acid", ElementType.LIQUID, KeyEvent.VK_Z, new Color(21, 202, 27));
        this.x = spawnX;
        this.y = spawnY;
        this.density = 0.3f;
        this.inertialResistance = 0.4f;
        this.corrodeStrength = 0.6f;
        this.corrodeResistance = 0.8f;
    }

    @Override
    public void performCheck(List<List<Element>> elements) {
        performDrop(elements);
        performElementCheck(elements, CheckType.CORRODE);
        final double random = Math.random();
        if (random < chanceToPutOut)
            Simulation.elementManager.setPosition(new AirElement(this.x, this.y));
    }
}
