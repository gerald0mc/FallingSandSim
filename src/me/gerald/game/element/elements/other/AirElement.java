package me.gerald.game.element.elements.other;

import me.gerald.game.element.Element;
import me.gerald.game.element.ElementType;

import java.awt.*;

public class AirElement extends Element {
    public AirElement(int spawnX, int spawnY) {
        super("Air", ElementType.GAS, new Color(0, 0, 0, 0));
        this.x = spawnX;
        this.y = spawnY;
    }

    @Override
    public void performCheck(Element[][] elements) { }
}
