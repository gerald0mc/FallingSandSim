package me.gerald.game.event.listeners;

import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;
import java.awt.event.MouseEvent;

public class MouseListener implements MouseInputListener {
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

    @Override
    public void mouseMoved(MouseEvent e) {
//        x = e.getX();
//        y = e.getY();
    }

    public boolean isSpawning() {
        return spawning;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public void mouseClicked(MouseEvent e) { }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }

    @Override
    public void mouseDragged(MouseEvent e) {
        x = e.getX();
        y = e.getY();
    }
}
