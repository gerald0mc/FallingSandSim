package me.gerald.game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyBoardListener implements KeyListener {
    public int selectedKeyCode = KeyEvent.VK_W;

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_Q -> {
                selectedKeyCode = KeyEvent.VK_Q;
                Game.out.add("You are now spawning Air!");
            }
            case KeyEvent.VK_W -> {
                selectedKeyCode = KeyEvent.VK_W;
                Game.out.add("You are now spawning Sand!");
            }
            case KeyEvent.VK_E -> {
                selectedKeyCode = KeyEvent.VK_E;
                Game.out.add("You are now spawning Water!");
            }
            case KeyEvent.VK_R -> {
                selectedKeyCode = KeyEvent.VK_R;
                Game.out.add("You are not spawning Dirt!");
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
