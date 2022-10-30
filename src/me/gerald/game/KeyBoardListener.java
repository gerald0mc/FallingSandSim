package me.gerald.game;

import me.gerald.game.element.Element;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyBoardListener implements KeyListener {
    public int selectedKeyCode = KeyEvent.VK_W;

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        for (Element element : Game.elementManager.elements) {
            if (element.getKeyCode() == keyCode) {
                selectedKeyCode = keyCode;
                Game.out.add("Now spawning " + element.getName() + "!");
                return;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
