package me.gerald.game.element.elements.solids.movable;

import me.gerald.game.Game;
import me.gerald.game.element.Element;
import me.gerald.game.element.ElementManager;
import me.gerald.game.element.ElementType;
import me.gerald.game.element.elements.other.AirElement;

import java.awt.*;

public class SandElement extends Element {
    public SandElement(int spawnX, int spawnY) {
        super("Sand", ElementType.MOVABLE_SOLID, new Color(212, 176, 38));
        this.x = spawnX;
        this.y = spawnY;
    }

    @Override
    public void performCheck(Element[][] elements) {
        if (elements[this.y + 1][this.x] instanceof AirElement) {
            this.velocity = 0.2f;
            this.isFalling = true;
            Game.elementManager.swapPositions(this.x, this.y, this.x, this.y + 1);
        } else if (elements[this.y + 1][this.x].getElementType() == ElementType.MOVABLE_SOLID || elements[this.y + 1][this.x].getElementType() == ElementType.IMMOVABLE_SOLID) {
            this.velocity = 0f;
            if (isFalling) isFalling = false;
        }
    }
}
