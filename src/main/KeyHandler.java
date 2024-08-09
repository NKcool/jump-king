package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener { 
    Player player;
    public KeyHandler(Player player) {
        this.player = player;
        System.out.println("KeyHandler created");
    }
    @Override
    public void keyPressed(KeyEvent e) {
       System.out.println("Key Pressed");


       if(e.getKeyCode() == KeyEvent.VK_SPACE)
       player.jump();
       
    }
    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("Key Released");
    }
    @Override
    public void keyTyped(KeyEvent e) {
        System.out.println("Key Typed");
    }
}