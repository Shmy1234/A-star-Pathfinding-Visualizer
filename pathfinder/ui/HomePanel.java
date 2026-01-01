package pathfinder.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class HomePanel extends JPanel {
  public HomePanel(AlgorithmNavigator navigator) {
    setLayout(new BorderLayout());
    setBackground(Color.white);

    JLabel title = new JLabel("PathFinder Explorer", SwingConstants.CENTER);
    title.setOpaque(true);
    title.setBackground(Color.white);
    title.setForeground(Color.black);
    title.setFont(new Font("SansSerif", Font.BOLD, 26));
    title.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));

    JPanel buttons = new JPanel();
    buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));
    buttons.setBackground(Color.white);
    buttons.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

    JButton bfsButton = styledButton("Breadth-First Search (BFS)");
    JButton dfsButton = styledButton("Depth-First Search (DFS)");
    JButton dijkstraButton = styledButton("Dijkstra's Algorithm");
    JButton aStarButton = styledButton("A* Search");

    bfsButton.addActionListener(e -> navigator.showBfs());
    dfsButton.addActionListener(e -> navigator.showDfs());
    dijkstraButton.addActionListener(e -> navigator.showDijkstra());
    aStarButton.addActionListener(e -> navigator.showAStar());

    buttons.add(centered(bfsButton));
    buttons.add(Box.createVerticalStrut(10));
    buttons.add(centered(dfsButton));
    buttons.add(Box.createVerticalStrut(10));
    buttons.add(centered(dijkstraButton));
    buttons.add(Box.createVerticalStrut(10));
    buttons.add(centered(aStarButton));

    add(title, BorderLayout.NORTH);
    add(buttons, BorderLayout.CENTER);
  }

  private JPanel centered(JButton button) {
    JPanel wrapper = new JPanel();
    wrapper.setBackground(Color.white);
    wrapper.setLayout(new BorderLayout());
    wrapper.add(button, BorderLayout.CENTER);
    return wrapper;
  }

  private JButton styledButton(String label) {
    JButton button = new JButton(label);
    button.setFocusPainted(false);
    button.setBackground(Color.white);
    button.setForeground(Color.black);
    button.setFont(new Font("SansSerif", Font.BOLD, 14));
    button.setMargin(new Insets(8, 12, 8, 12));
    button.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    button.setPreferredSize(new Dimension(240, 42));
    button.setMaximumSize(new Dimension(240, 42));
    button.setOpaque(true);
    addHoverAndClickEffects(button, Color.white, new Color(230, 230, 230), new Color(210, 210, 210));
    return button;
  }

  private void addHoverAndClickEffects(JButton button, Color base, Color hover, Color pressed) {
    button.addMouseListener(new java.awt.event.MouseAdapter() {
      @Override
      public void mouseEntered(java.awt.event.MouseEvent e) {
        button.setBackground(hover);
      }

      @Override
      public void mouseExited(java.awt.event.MouseEvent e) {
        button.setBackground(base);
      }

      @Override
      public void mousePressed(java.awt.event.MouseEvent e) {
        button.setBackground(pressed);
      }

      @Override
      public void mouseReleased(java.awt.event.MouseEvent e) {
        button.setBackground(hover);
      }
    });
  }
}
