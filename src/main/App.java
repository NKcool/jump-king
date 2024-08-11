package main;
import javax.swing.JFrame;

class App{
    public static void main(String[] args) {
        JFrame frame = new JFrame("jump king");
        frame.setResizable(false);
        // frame.setLocationRelativeTo(null); // Center the window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GamePanel gamePanel = new GamePanel();
        frame.add(gamePanel);
        frame.pack();
        frame.setVisible(true);
        gamePanel.startGame(); // Start the game
    }
}