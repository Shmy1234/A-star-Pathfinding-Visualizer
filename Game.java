import javax.swing.JFrame;

public class Game {
    public static void main(String[] args) {
        JFrame window = new JFrame("Alan's A* Pathfinding");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(true);
        window.add(new Panel());
        window.setSize(1200, 600); // Set window size
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}