package me.gerald.game.element;

import me.gerald.game.Game;
import me.gerald.game.GameConstants;
import me.gerald.game.element.elements.liquids.LavaElement;
import me.gerald.game.element.elements.others.AirElement;
import me.gerald.game.element.elements.others.FireElement;

import java.awt.*;
import java.util.List;

public abstract class Element {
    private final String name;
    private final ElementType elementType;
    private final Color defaultColor;

    private final int keyCode;

    public int x = 0;
    public int y = 0;

    // Element variables

    //The current color of the element for when it is being rendered
    public Color currentColor;
    //Related to how likely the element is to fall 0-1f
    public float inertialResistance;
    //Related to what the element will float on 0-inf
    public float density;
    //Related to calculations
    public boolean isFreeFalling = false;
    //Related to calculations
    public boolean isRising = false;
    //Related to how hot/cold an element is
    public float tempStrength;
    //Related to how likely the element will catch fire
    public float burnResistance;

    public Element() {
        this.name = "";
        this.elementType = null;
        this.keyCode = -1;
        this.currentColor = this.defaultColor = null;
    }

    public Element(String name, ElementType elementType, int keyCode, Color defaultColor) {
        this.name = name;
        this.elementType = elementType;
        this.keyCode = keyCode;
        this.currentColor = this.defaultColor = defaultColor;
    }

    //Actual methods

    public void performCheck(List<List<Element>> elements) { }

    public boolean densityCheck(float density) {
        return this.density > density;
    }

    public boolean burnCheck(float burnResistance) {
        return this.tempStrength > burnResistance;
    }

    public boolean setFreeFalling() {
        isFreeFalling = Math.random() > inertialResistance || isFreeFalling;
        return isFreeFalling;
    }

    public boolean setRising() {
        isRising = (Math.random() * -1) < inertialResistance || isRising;
        return isRising;
    }

    public boolean isOnScreen() {
        return x >= 0 && x <= GameConstants.SCREEN_WIDTH && y >= 0 && y <= GameConstants.SCREEN_HEIGHT;
    }

    public void performDrop(List<List<Element>> elements) {
        if (!bottomHeightCheck()) return;
        Element downElement = elements.get(y + 1).get(x);
        if (densityCheck(downElement.density) && downElement.getElementType() != ElementType.IMMOVABLE_SOLID) {
            if (setFreeFalling())
                Game.elementManager.swapPositions(x, y, x, y + 1);
        } else if (!densityCheck(downElement.density) || downElement.getElementType() == ElementType.MOVABLE_SOLID || downElement.getElementType() == ElementType.IMMOVABLE_SOLID && !densityCheck(downElement.density)) {
            isFreeFalling = false;
        }
        final int bottomCheck = bottomSideCanPlace();
        if (bottomCheck == -1) return;
        switch (bottomCheck) {
            case 0 -> {
                Element downLeftElement = elements.get(y + 1).get(x - 1);
                if (densityCheck(downLeftElement.density) && (downLeftElement.getElementType() == ElementType.LIQUID || downLeftElement.getElementType() == ElementType.GAS))
                    Game.elementManager.swapPositions(x, y, x - 1, y + 1);
            }
            case 1 -> {
                Element downRightElement = elements.get(y + 1).get(x + 1);
                if (densityCheck(downRightElement.density) && (downRightElement.getElementType() == ElementType.LIQUID || downRightElement.getElementType() == ElementType.GAS))
                    Game.elementManager.swapPositions(x, y, x + 1, y + 1);
            }
            case 2 -> {
                boolean leftAirCheck = densityCheck(elements.get(y + 1).get(x - 1).density) && (elements.get(y + 1).get(x - 1).getElementType() == ElementType.LIQUID || elements.get(y + 1).get(x - 1).getElementType() == ElementType.GAS);
                boolean rightAirCheck = densityCheck(elements.get(y + 1).get(x + 1).density) && (elements.get(y + 1).get(x + 1).getElementType() == ElementType.LIQUID || elements.get(y + 1).get(x + 1).getElementType() == ElementType.GAS);
                if (leftAirCheck && rightAirCheck) {
                    double random = Math.random();
                    if (random < 0.5) Game.elementManager.swapPositions(x, y, x - 1, y + 1);
                    else Game.elementManager.swapPositions(x, y, x + 1, y + 1);
                } else if (leftAirCheck) Game.elementManager.swapPositions(x, y, x - 1, y + 1);
                else if (rightAirCheck) Game.elementManager.swapPositions(x, y, x + 1, y + 1);
            }
        }
    }

    public void performRise(List<List<Element>> elements) {
        if (!topHeightCheck()) return;
        Element topElement = elements.get(y - 1).get(x);
        if (densityCheck(topElement.density) && topElement.getElementType() != ElementType.IMMOVABLE_SOLID) {
            if (setRising())
                Game.elementManager.swapPositions(x, y, x, y - 1);
        } else if (!densityCheck(topElement.density) || topElement.getElementType() == ElementType.MOVABLE_SOLID || topElement.getElementType() == ElementType.IMMOVABLE_SOLID) {
            isRising = false;
        }
        final int topCheck = topSideCanPlace();
        if (topCheck == -1) return;
        switch (topCheck) {
            case 0 -> {
                Element upLeftElement = elements.get(y - 1).get(x - 1);
                if (densityCheck(upLeftElement.density) && upLeftElement.getElementType() == ElementType.GAS)
                    Game.elementManager.swapPositions(x, y, x - 1, y - 1);
            }
            case 1 -> {
                Element upRightElement = elements.get(y - 1).get(x + 1);
                if (densityCheck(upRightElement.density) && upRightElement.getElementType() == ElementType.GAS)
                    Game.elementManager.swapPositions(x, y, x + 1, y - 1);
            }
            case 2 -> {
                boolean leftAirCheck = densityCheck(elements.get(y - 1).get(x - 1).density) && elements.get(y - 1).get(x - 1).getElementType() == ElementType.GAS;
                boolean rightAirCheck = densityCheck(elements.get(y - 1).get(x + 1).density) && elements.get(y - 1).get(x + 1).getElementType() == ElementType.GAS;
                if (leftAirCheck && rightAirCheck) {
                    double random = Math.random();
                    if (random < 0.5) Game.elementManager.swapPositions(x, y, x - 1, y - 1);
                    else Game.elementManager.swapPositions(x, y, x + 1, y - 1);
                } else if (leftAirCheck) Game.elementManager.swapPositions(x, y, x - 1, y - 1);
                else if (rightAirCheck) Game.elementManager.swapPositions(x, y, x + 1, y - 1);
            }
        }
    }

    public void doBurnCheck(List<List<Element>> elements) {
        Element upElement = elements.get(y - 1).get(x);
        Element leftElement = elements.get(y).get(x - 1);
        Element rightElement = elements.get(y).get(x + 1);
        Element[] elementArray = new Element[] {upElement, leftElement, rightElement};
        for (Element element : elementArray) {
            if (burnCheck(element.burnResistance) && !(element instanceof FireElement || element instanceof LavaElement || element instanceof AirElement)) {
                Game.elementManager.setPosition(new FireElement(element.x, element.y));
            }
        }
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

    public int topSideCanPlace() {
        if (topHeightCheck() && rightWidthCheck() && leftWidthCheck()) return 2;
        else if (topHeightCheck() && leftWidthCheck()) return 0;
        else if (topHeightCheck() && rightWidthCheck()) return 1;
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

    public int getKeyCode() {
        return keyCode;
    }
}
