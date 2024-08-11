package main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.InputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioInputStream;

public class Player {

    private int x, y;
    private int yVelocity = 0;
    private final int gravity = 1;
    private final int groundLevel = 550;
    private int tileSize;
    private int jumpHeight = -17;
    private BufferedImage birdImage;

    public void playSound(String soundName) {
        try (InputStream audioSrc = getClass().getResourceAsStream("/resources/accets/Sounds/"+soundName);
             InputStream bufferedIn = new BufferedInputStream(audioSrc);
             AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedIn)) {
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (Exception e) {
            System.out.println("Error playing sound: " + e.getMessage());
        }
    }

    public Player(int x, int y, int tileSize) {
        this.x = x;
        this.y = y;
        this.tileSize = 2 * tileSize;

        try {
            birdImage = ImageIO.read(getClass().getResource("/resources/accets/Images/bird.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        y += yVelocity;
        yVelocity += gravity;
        // Check for collision with the ground
        if (y >= groundLevel) {
            y = groundLevel;
            yVelocity = 0;
        }
    }

    public void draw(Graphics2D g2) {
        double rotationAngle = 0;
        if (yVelocity > 0) {
            rotationAngle = Math.toRadians(30); // Rotate 30 degrees when falling
        }

        if (birdImage != null) {
            g2.rotate(rotationAngle, x + tileSize / 2, y + tileSize / 2);
            g2.drawImage(birdImage, x, y, tileSize, tileSize, null);
            g2.rotate(-rotationAngle, x + tileSize / 2, y + tileSize / 2); // Reset rotation
        } else {
            g2.setColor(Color.RED);
            g2.fillRect(x, y, tileSize, tileSize);
        }
    }

    public void jump() {
        if(y > 0)
        yVelocity = jumpHeight;
        
        // playSound("jumpSound.wav");

    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, tileSize, tileSize);
    }

    public int getX() {
        return x;
    }

    public class SoundPlayer {

    }
}
