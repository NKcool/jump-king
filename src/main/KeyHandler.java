package main;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    Player player;
    GamePanel gamePanel;
    public KeyHandler(Player player, GamePanel gamePanel) {
        this.player = player;
        this.gamePanel = gamePanel;
    }
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_SPACE) {
            player.jump();
        } else if (keyCode == KeyEvent.VK_R && gamePanel.isGameOver()) {
            gamePanel.resetGame();
        }

    }
    @Override
    public void keyReleased(KeyEvent e) {

    }
    @Override
    public void keyTyped(KeyEvent e) {

    }
}