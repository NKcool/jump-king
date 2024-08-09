package main;
import javax.swing.JFrame;

class App{
    public static void main(String[] args) {
        JFrame frame = new JFrame("jump king");
        frame.setResizable(false);
        // frame.setLocationRelativeTo(null); // Center the window
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GamePanel gamePanel = new GamePanel();
        frame.add(gamePanel);
        frame.pack();
        gamePanel.gameThreadStart(); // Start the game
    }
}