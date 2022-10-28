package me.gerald.game.element;

import me.gerald.game.GameConstants;
import me.gerald.game.element.elements.other.AirElement;
import me.gerald.game.element.elements.solids.movable.SandElement;

public class ElementManager {
    public Element[][] elements = new Element[GameConstants.SCREEN_HEIGHT][GameConstants.SCREEN_WIDTH];

    public static ElementManager instance;

    public ElementManager() {
        int xOffset = 0;
        int rows = 0;
        for (int i = 0; i < (GameConstants.SCREEN_WIDTH * GameConstants.SCREEN_HEIGHT); i++) {
            if (xOffset == GameConstants.SCREEN_WIDTH) {
                xOffset = 0;
                rows++;
            } else if (rows == GameConstants.SCREEN_HEIGHT) break;
            if (rows == 0)
                elements[rows][xOffset] = new SandElement(xOffset, rows);
            else
                elements[rows][xOffset] = new AirElement(xOffset, rows);
            xOffset++;
        }
    }

    public void swapPositions(int startX, int startY, int endX, int endY) {
        if (elements[endY][endX] == null) return;
        elements[startY][startX] = elements[endY][endX];
        elements[endY][endX] = elements[startY][startX];
        System.out.println(startY);
    }
}
