package me.gerald.game.element;

import java.awt.*;

public abstract class Element {
    private final String name;
    private final ElementType elementType;
    private final Color defaultColor;
    public Color currentColor;

    public int x = 0;
    public int y = 0;

    public float velocity = 0.0f;
    public boolean isFalling = false;

    public Element(String name, ElementType elementType, Color defaultColor) {
        this.name = name;
        this.elementType = elementType;
        this.currentColor = this.defaultColor = defaultColor;
    }

    //Actual methods

    public void performCheck(Element[][] elements) { }

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
