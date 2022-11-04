package me.gerald.game.editor;

import me.gerald.game.simulation.element.Element;
import me.gerald.game.simulation.element.elements.others.AirElement;

public class Cell {
    public int x;
    public int y;
    public int width = 10;
    public int height = 10;
    public Element element = new AirElement();

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isInside(int mouseX, int mouseY) {
        return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height;
    }
}
