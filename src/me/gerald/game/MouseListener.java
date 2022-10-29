package me.gerald.game;

import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;
import java.awt.event.MouseEvent;

public class MouseListener extends MouseInputAdapter {
    private boolean spawning = false;
    private int x;
    private int y;

    @Override
    public void mousePressed(MouseEvent e) {
        spawning = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        spawning = false;
    }

    public boolean isSpawning() {
        return spawning;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        x = e.getX();
        y = e.getY();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
