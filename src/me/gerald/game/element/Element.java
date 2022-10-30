package me.gerald.game.element;

import me.gerald.game.Simulation;
import me.gerald.game.Constants;
import me.gerald.game.element.elements.gases.SteamElement;
import me.gerald.game.element.elements.liquids.AcidElement;
import me.gerald.game.element.elements.liquids.LavaElement;
import me.gerald.game.element.elements.liquids.WaterElement;
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

    //Related to calculations
    public boolean isFreeFalling = false;
    //Related to calculations
    public boolean isRising = false;
    //The current color of the element for when it is being rendered
    public Color currentColor;
    //Related to how likely the element is to fall 0-1f
    public float inertialResistance;
    //Related to what the element will float on 0-inf
    public float density;
    //Related to how hot/cold an element is
    public float burnStrength;
    //Related to how likely the element will catch fire
    public float burnResistance;
    //Related to how corrosive an element is
    public float corrodeStrength;
    //Related to how likely the element will corrode
    public float corrodeResistance;

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

    public abstract void performCheck(List<List<Element>> elements);

    public boolean densityCheck(float density) {
        return this.density > density;
    }

    public boolean burnCheck(float burnResistance) {
        return this.burnStrength > burnResistance;
    }

    public boolean corrodeCheck(float corrodeResistance) {
        return this.corrodeStrength > corrodeResistance;
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
        return x >= 0 && x <= Constants.SCREEN_WIDTH && y >= 0 && y <= Constants.SCREEN_HEIGHT;
    }

    public boolean canRiseUp(List<List<Element>> elements) {
        if (!topHeightCheck()) return false;
        Element upElement = elements.get(y - 1).get(x);
        return !densityCheck(upElement.density)
                && (upElement.getElementType() == ElementType.GAS
                || upElement.getElementType() == ElementType.LIQUID
                || upElement.getElementType() == ElementType.MOVABLE_SOLID);
    }

    public boolean canRiseExact(Element targetElement) {
        return !densityCheck(targetElement.density)
                && (targetElement.getElementType() == ElementType.GAS
                || targetElement.getElementType() == ElementType.LIQUID
                || targetElement.getElementType() == ElementType.MOVABLE_SOLID);
    }

    public boolean canFallDown(List<List<Element>> elements) {
        if (!bottomHeightCheck()) return false;
        Element downElement = elements.get(y + 1).get(x);
        return densityCheck(downElement.density)
                && (downElement.getElementType() == ElementType.GAS
                || downElement.getElementType() == ElementType.LIQUID
                || downElement.getElementType() == ElementType.MOVABLE_SOLID);
    }

    public boolean canFallExact(Element targetElement) {
        return densityCheck(targetElement.density)
                && (targetElement.getElementType() == ElementType.GAS
                || targetElement.getElementType() == ElementType.LIQUID
                || targetElement.getElementType() == ElementType.MOVABLE_SOLID);
    }

    public void performDrop(List<List<Element>> elements) {
        if (canFallDown(elements)) {
            if (setFreeFalling())
                Simulation.elementManager.swapPositions(x, y, x, y + 1);
        } else {
            isFreeFalling = false;
        }
        final int bottomSidesCheck = bottomSideCanPlace();
        if (bottomSidesCheck == -1) return;
        switch (bottomSidesCheck) {
            case 0 -> {
                Element downLeftElement = elements.get(y + 1).get(x - 1);
                if (canFallExact(downLeftElement))
                    Simulation.elementManager.swapPositions(x, y, x - 1, y + 1);
            }
            case 1 -> {
                Element downRightElement = elements.get(y + 1).get(x + 1);
                if (canFallExact(downRightElement))
                    Simulation.elementManager.swapPositions(x, y, x + 1, y + 1);
            }
            case 2 -> {
                boolean leftAirCheck = canFallExact(elements.get(y + 1).get(x - 1));
                boolean rightAirCheck = canFallExact(elements.get(y + 1).get(x + 1));
                if (leftAirCheck && rightAirCheck) {
                    double random = Math.random();
                    if (random < 0.5) Simulation.elementManager.swapPositions(x, y, x - 1, y + 1);
                    else Simulation.elementManager.swapPositions(x, y, x + 1, y + 1);
                } else if (leftAirCheck) Simulation.elementManager.swapPositions(x, y, x - 1, y + 1);
                else if (rightAirCheck) Simulation.elementManager.swapPositions(x, y, x + 1, y + 1);
            }
        }
    }

    public void performRise(List<List<Element>> elements) {
        if (canRiseUp(elements)) {
            if (setRising())
                Simulation.elementManager.swapPositions(x, y, x, y - 1);
        } else {
            isRising = false;
        }
        final int topSidesCheck = topSideCanPlace();
        if (topSidesCheck == -1) return;
        switch (topSidesCheck) {
            case 0 -> {
                Element upLeftElement = elements.get(y - 1).get(x - 1);
                if (canRiseExact(upLeftElement))
                    Simulation.elementManager.swapPositions(x, y, x - 1, y - 1);
            }
            case 1 -> {
                Element upRightElement = elements.get(y - 1).get(x + 1);
                if (canRiseExact(upRightElement))
                    Simulation.elementManager.swapPositions(x, y, x + 1, y - 1);
            }
            case 2 -> {
                boolean leftAirCheck = canRiseExact(elements.get(y - 1).get(x - 1));
                boolean rightAirCheck = canRiseExact(elements.get(y - 1).get(x + 1));
                if (leftAirCheck && rightAirCheck) {
                    double random = Math.random();
                    if (random < 0.5) Simulation.elementManager.swapPositions(x, y, x - 1, y - 1);
                    else Simulation.elementManager.swapPositions(x, y, x + 1, y - 1);
                } else if (leftAirCheck) Simulation.elementManager.swapPositions(x, y, x - 1, y - 1);
                else if (rightAirCheck) Simulation.elementManager.swapPositions(x, y, x + 1, y - 1);
            }
        }
    }

    public void performElementCheck(List<List<Element>> elements, CheckType checkType) {
        if (!topHeightCheck() || !bottomHeightCheck() || !leftWidthCheck() || !rightWidthCheck()) return;
        Element upElement = elements.get(y - 1).get(x);
        Element leftElement = elements.get(y).get(x - 1);
        Element rightElement = elements.get(y).get(x + 1);
        Element downElement = elements.get(y - 1).get(x);
        Element[] elementArray = new Element[] {upElement, leftElement, rightElement, downElement};
        switch (checkType) {
            case FIRE -> {
                for (Element element : elementArray) {
                    if (burnCheck(element.burnResistance) && element instanceof WaterElement) {
                        Simulation.elementManager.setPosition(new SteamElement(element.x, element.y));
                    } else if (burnCheck(element.burnResistance) && element.getElementType() != ElementType.GAS && !(element instanceof FireElement || element instanceof LavaElement)) {
                        Simulation.elementManager.setPosition(new FireElement(element.x, element.y));
                    }
                }
            }
            case CORRODE -> {
                for (Element element : elementArray) {
                    if (corrodeCheck(element.corrodeResistance) && !(element instanceof AirElement)) {
                        Simulation.elementManager.setPosition(new AcidElement(element.x, element.y));
                    }
                }
            }
        }
    }

    public enum CheckType {FIRE, CORRODE}

    public void checkGasPixel(List<List<Element>> elements) {
        if (density > 0) performDrop(elements);
        else performRise(elements);
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

    public boolean topHeightCheck() {
        return y - 1 >= 0;
    }

    public boolean bottomHeightCheck() {
        return y + 1 < Constants.SCREEN_HEIGHT;
    }

    public boolean leftWidthCheck() {
        return x - 1 >= 0;
    }

    public boolean rightWidthCheck() {
        return x + 1 < Constants.SCREEN_WIDTH;
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
