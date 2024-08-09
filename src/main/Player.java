package main;

import java.awt.Graphics2D;
import java.awt.Color;

public class Player {
    private int x, y;
    private int yVelocity = 0;
    private final int gravity = 1;
    private final int groundLevel = 520;
    private int tileSize;
    private int jumpHeight = -20;

    public Player(int x, int y,int tileSize) {
        this.x = x;
        this.y = y;
        this.tileSize = tileSize;
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
            g2.setColor(Color.RED);
            g2.fillRect(x, y, tileSize, tileSize);
        }
        public void jump() {
            jumpHeight = -20;
            yVelocity = jumpHeight;
        }
        public void jump(int i) {
            yVelocity = jumpHeight;
            jumpHeight -= i;
        }
}
