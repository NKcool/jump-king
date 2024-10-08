package main;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

class GamePanel extends JPanel implements Runnable {
    final int originalTileSize = 16;
    final int scale = 3; // Scale factor for the game
    final int tileSize = originalTileSize * scale; // 48
    final int maxScreenCollumns = 16;
    final int maxScreenRows = 12;
    final int screenWidth = tileSize * maxScreenCollumns; // 288
    final int screenHeight = tileSize * maxScreenRows; // 192
    private Player player;
    private ArrayList<Pipes> pipes;
    private boolean gameOver;
    private int score;
    private Thread gameThread;
    private final int FPS =60; // Desired frames per second
    private final long frameTime = 1000000000 / FPS; // Time per frame in nanoseconds
    private BufferedImage backgroundImage ;



    public GamePanel() {
        player = new Player(50, 0, tileSize);
        pipes = new ArrayList<>();
        pipes.add(new Pipes(400, 0, 2 * tileSize, 2 * tileSize, true));
        pipes.add(new Pipes(400, 400, 2 * tileSize, 4 * tileSize, false));
        pipes.add(new Pipes(700, 0, 2 * tileSize, 5 * tileSize, true));
        pipes.add(new Pipes(700, 500, 2 * tileSize, 3 * tileSize, false));
        pipes.add(new Pipes(1000, 0, 2 * tileSize, 2 * tileSize, true));
        pipes.add(new Pipes(1000, 400, 2 * tileSize, 5 * tileSize, false));

        try {
            backgroundImage = ImageIO.read(getClass().getResource("/resources/accets/Images/background.png"));
        } catch (IOException e) {
            System.out.println("Error loading bird image: " + e.getMessage());
        }
        gameOver = false;
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setDoubleBuffered(true); // Prevents flickering
        this.addKeyListener(new KeyHandler(player, this));
        this.setFocusable(true);
        this.requestFocusInWindow();
    }
    public void startGame() {
        if (gameThread == null) {
            gameThread = new Thread(this);
            gameThread.start();
        }
    }
    @Override
    public void run() {
        long lastTime = System.nanoTime();
        long now;
        while (gameThread != null) {
            now = System.nanoTime();
            long elapsedTime = now - lastTime;

            if (elapsedTime >= frameTime) {
                lastTime = now;
                update();
                repaint();

            }
        }
    }
    public void update() {
        if (!gameOver) {
            player.update();
            movePipes();
            checkCollision();
        }
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        if (backgroundImage != null) {
            g2.drawImage(backgroundImage, 0, 0, screenWidth, screenHeight, null);
        }
        player.draw(g2);
        for (Pipes p : pipes) {
            p.draw(g2);
        }
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 30));
        g2.drawString("Score: " + score, 10, 30);
        if (gameOver) {
            g2.setColor(Color.RED);
            g2.setFont(new Font("Arial", Font.BOLD, 50));
            g2.drawString("Game Over", 250, 400);
            g2.drawString(" Score is " + score, 250, 300);
            g2.setFont(new Font("Arial", Font.PLAIN, 30));
            g2.drawString("Press R to Retry", 270, 350);
        }
    }
    private void movePipes() {
        for (Pipes p : pipes) {
            p.move(-5, 0);
            if (p.getX() + p.getWidth() < 0) {
                score += 1;
                int lastPipeX = (pipes.indexOf(p) > 0) ? pipes.get(pipes.indexOf(p) - 1).getX() : screenWidth;
                p.resetPosition(lastPipeX + 180, screenHeight, pipes.indexOf(p) % 2 == 1); // Alternate bottom and top
            }
        }
    }
    private void checkCollision() {
        Rectangle playerBounds = player.getBounds();
        for (Pipes p : pipes) {
            Rectangle pipeBounds = p.getBounds();
            // Inflate the pipe bounds to make it easier for the player to collide with them
            Rectangle inflatedPipeBounds = new Rectangle(
                    pipeBounds.x + 20,
                    pipeBounds.y + 20,
                    pipeBounds.width - 50,
                    pipeBounds.height - 50);

            // Check if the player's bounds intersect with the inflated bounds
            if (playerBounds.intersects(inflatedPipeBounds)) {
                gameOver = true;
                gameThread = null;
                repaint();
                break;
            }
        }
    }
    public void resetGame() {
        player = new Player(50, 0, tileSize);
        pipes.clear();
        pipes.add(new Pipes(400, 0, 2 * tileSize, 2 * tileSize, true));
        pipes.add(new Pipes(400, 400, 2 * tileSize, 4 * tileSize, false));
        pipes.add(new Pipes(700, 0, 2 * tileSize, 5 * tileSize, true));
        pipes.add(new Pipes(700, 500, 2 * tileSize, 3 * tileSize, false));
        pipes.add(new Pipes(1000, 0, 2 * tileSize, 2 * tileSize, true));
        pipes.add(new Pipes(1000, 400, 2 * tileSize, 5 * tileSize, false));
        score = 0;
        gameOver = false;
        startGame();
        gameOver = false;
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setDoubleBuffered(true); 
        this.addKeyListener(new KeyHandler(player, this));
        this.setFocusable(true);
        this.requestFocusInWindow();
    }
    public boolean isGameOver() {
        return gameOver;
    }
}
