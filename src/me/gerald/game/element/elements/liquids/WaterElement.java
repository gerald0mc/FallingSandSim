package me.gerald.game.element.elements.liquids;

import me.gerald.game.Game;
import me.gerald.game.element.Element;
import me.gerald.game.element.ElementType;
import me.gerald.game.element.elements.others.AirElement;

import java.awt.*;
import java.util.List;

public class WaterElement extends Element {
    public WaterElement(int spawnX, int spawnY) {
        super("Water", ElementType.LIQUID, new Color(68, 99, 231));
        this.x = spawnX;
        this.y = spawnY;
        this.inertialResistance = 0.0f;
        this.density = 0.0f;
    }

    @Override
    public void performCheck(List<List<Element>> elements) {
        performDrop(elements);
    }
}
