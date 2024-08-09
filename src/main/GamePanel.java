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
    KeyHandler keyH = new KeyHandler(player);
    Thread gameThread;

    
    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true); // Prevents flickering
        this.addKeyListener(keyH);
        this.setFocusable(true);
        player = new Player(300, 0,tileSize);
    }

    public void gameThreadStart(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / 60; // 60 FPS
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;


        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if(delta >= 1){
                update(); // Update game state
                repaint(); // Calls paintComponent
                delta--;
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