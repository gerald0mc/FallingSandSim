package me.gerald.game.element;

import me.gerald.game.GameConstants;

import java.awt.*;
import java.util.List;

public abstract class Element {
    private final String name;
    private final ElementType elementType;
    private final Color defaultColor;
    public Color currentColor;

    public int x = 0;
    public int y = 0;

    public float inertialResistance;
    public boolean isFreeFalling = false;

    public Element(String name, ElementType elementType, Color defaultColor) {
        this.name = name;
        this.elementType = elementType;
        this.currentColor = this.defaultColor = defaultColor;
    }

    //Actual methods

    public void performCheck(List<List<Element>> elements) { }

    public boolean setFreeFalling() {
        isFreeFalling = Math.random() > inertialResistance || isFreeFalling;
        return isFreeFalling;
    }

    public boolean isOnScreen() {
        return x >= 0 && x <= GameConstants.SCREEN_WIDTH && y >= 0 && y <= GameConstants.SCREEN_HEIGHT;
    }

    public boolean topHeightCheck() {
        return y - 1 >= 0;
    }

    public boolean bottomHeightCheck() {
        return y + 1 < GameConstants.SCREEN_HEIGHT;
    }

    public boolean leftWidthCheck() {
        return x - 1 >= 0;
    }

    public boolean rightWidthCheck() {
        return x + 1 < GameConstants.SCREEN_WIDTH;
    }

    public int bottomSideCanPlace() {
        if (bottomHeightCheck() && rightWidthCheck() && leftWidthCheck()) return 2;
        else if (bottomHeightCheck() && leftWidthCheck()) return 0;
        else if (bottomHeightCheck() && rightWidthCheck()) return 1;
        return -1;
    }

    //Getters and Setters

    public String getName() {
        return name;
    }

    public ElementType getElementType() {
        return elementType;
    }

    public Color getDefaultColor() {
        return defaultColor;
    }
}
