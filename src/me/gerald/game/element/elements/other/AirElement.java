package me.gerald.game.element.elements.other;

import me.gerald.game.element.Element;
import me.gerald.game.element.ElementType;

import java.awt.*;
import java.util.List;

public class AirElement extends Element {
    public AirElement(int spawnX, int spawnY) {
        super("Air", ElementType.GAS, new Color(0, 0, 0, 0));
        this.x = spawnX;
        this.y = spawnY;
    }

    @Override
    public void performCheck(List<List<Element>> elements) { }
}
