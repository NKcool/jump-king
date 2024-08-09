package main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;


class GamePanel extends JPanel implements Runnable{
    Player player;
    // Screen Settings
    final int originalTileSize = 16;
    final int scale = 3; // 3x scale
    final int tileSize = originalTileSize * scale;
    final int maxScreenCollumns = 16;
    final int maxScreenRows = 12;
    // Screen Size
    final int screenWidth = tileSize * maxScreenCollumns;
    final int screenHeight = tileSize * maxScreenRows; // 192
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;

    
    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true); // Prevents flickering
        super.addKeyListener(keyH);
        super.setFocusable(true);
        super.requestFocusInWindow();
        player = new Player(300, 0,tileSize);
    }

    public void gameThreadStart(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        while (gameThread != null) {
            update(); // Update game state
            repaint(); // Calls paintComponent
            try {
                Thread.sleep(16); // 60 FPS
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update(){
        player.update();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        player.draw(g2);
        g2.dispose();
    }

}