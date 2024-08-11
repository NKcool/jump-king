package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;


public class Pipes {
    private int x, y, width, height;
    private static final int tileSize = 48;
    private static final int MIN_HEIGHT = 100; // Minimum height of the pipe
    private static final int MAX_HEIGHT = 300; // Maximum height of the pipe
    private BufferedImage pipeImage;
    
   


    public Pipes(int x, int y, int width, int height,boolean isTop) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
       if (isTop) {
            try {
                pipeImage = ImageIO.read(getClass().getResource("/resources/accets/Images/topPipe.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        
       }else{
            try {
                pipeImage = ImageIO.read(getClass().getResource("/resources/accets/Images/pipes.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void draw(Graphics2D g2) {

        if (pipeImage != null) {
            g2.drawImage(pipeImage, x, y, width, height, null);
        } else {
            g2.setColor(Color.GREEN);
            g2.fillRect(x, y, width, height);
        }

       
    }
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
    public void move(int dx, int dy) {
        x += dx;
        y += dy;
    }

    public void resetPosition(int screenWidth, int screenHeight , boolean isBottomPipe) { 
        x = screenWidth; // Move pipe to the right edge of the screen

        Random rand = new Random();
        height = MIN_HEIGHT + rand.nextInt(MAX_HEIGHT - MIN_HEIGHT + 1);
        height = (height / tileSize) * tileSize; // Adjust height to be a multiple of tileSize

        if (isBottomPipe) {
            // Bottom pipe is placed at the bottom of the screen
            y = screenHeight - height;
        } else {
            // Top pipe is placed at the top of the screen
            y = 0;
        }

    }

    // Getter methods
    public int getX() {
        return x;
    }

    public int getWidth() {
        return width;
    }


    

    
}
