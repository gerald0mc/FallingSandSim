package me.gerald.game.simulation.listeners;

import me.gerald.game.simulation.Simulation;
import me.gerald.game.simulation.element.Element;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyBoardListener implements KeyListener {
    public int selectedKeyCode = KeyEvent.VK_W;

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_EQUALS) {
            if (!Simulation.shapeEditorFrame.isVisible()) {
                Simulation.shapeEditorFrame.setVisible(true);
                Simulation.out.add("Shape Editor window now open.");
            } else {
                Simulation.out.add("Shape Editor window already open.");
            }
            return;
        }
        for (Element element : Simulation.elementManager.elements) {
            if (element.getKeyCode() == keyCode) {
                selectedKeyCode = keyCode;
                Simulation.out.add("Now spawning " + element.getName() + "!");
                break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
