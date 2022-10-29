package me.gerald.game.element.elements.solids.movable;

import me.gerald.game.Game;
import me.gerald.game.GameConstants;
import me.gerald.game.element.Element;
import me.gerald.game.element.ElementManager;
import me.gerald.game.element.ElementType;
import me.gerald.game.element.elements.other.AirElement;

import java.awt.*;
import java.util.List;
import java.util.Random;

public class SandElement extends Element {
    public SandElement(int spawnX, int spawnY) {
        super("Sand", ElementType.MOVABLE_SOLID, new Color(212, 176, 38));
        this.x = spawnX;
        this.y = spawnY;
        this.inertialResistance = 0.1f;
    }

    @Override
    public void performCheck(List<List<Element>> elements) {
        if (bottomHeightCheck() && elements.get(y + 1).get(x) instanceof AirElement) {
            if (setFreeFalling())
                Game.elementManager.swapPositions(x, y, x, y + 1);
        } else if (bottomHeightCheck() && (elements.get(y + 1).get(x).getElementType() == ElementType.MOVABLE_SOLID || elements.get(y + 1).get(x).getElementType() == ElementType.IMMOVABLE_SOLID)) {
            isFreeFalling = false;
        }
        final int bottomCheck = bottomSideCanPlace();
        switch (bottomCheck) {
            case 0:
                if (elements.get(y + 1).get(x - 1) instanceof AirElement) Game.elementManager.swapPositions(x, y, x - 1, y + 1);
                break;
            case 1:
                if (elements.get(y + 1).get(x + 1) instanceof AirElement) Game.elementManager.swapPositions(x, y, x + 1, y + 1);
                break;
            case 2:
                boolean leftAirCheck = elements.get(y + 1).get(x - 1) instanceof AirElement;
                boolean rightAirCheck = elements.get(y + 1).get(x - 1) instanceof AirElement;
                if (leftAirCheck && rightAirCheck) {
                    double random = Math.random();
                    if (random < 0.5) Game.elementManager.swapPositions(x, y, x - 1, y + 1);
                    else Game.elementManager.swapPositions(x, y, x + 1, y + 1);
                } else if (leftAirCheck) Game.elementManager.swapPositions(x, y, x - 1, y + 1);
                else if (rightAirCheck) Game.elementManager.swapPositions(x, y, x + 1, y + 1);
                break;
        }
    }
}
