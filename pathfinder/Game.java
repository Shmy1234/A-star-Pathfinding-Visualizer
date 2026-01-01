package pathfinder;

import java.awt.CardLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import pathfinder.astar.AStarPanel;
import pathfinder.bfs.BfsPanel;
import pathfinder.core.PathfindingScreenHost;
import pathfinder.dfs.DfsPanel;
import pathfinder.dijkstra.DijkstraPanel;
import pathfinder.ui.AlgorithmNavigator;
import pathfinder.ui.HomePanel;

public class Game extends JFrame implements PathfindingScreenHost, AlgorithmNavigator {
  private final CardLayout layout = new CardLayout();
  private final JPanel cards = new JPanel(layout);

  private final HomePanel homePanel;
  private final BfsPanel bfsPanel;
  private final DfsPanel dfsPanel;
  private final DijkstraPanel dijkstraPanel;
  private final AStarPanel aStarPanel;

  public Game() {
    super("Pathfinding Visualizer");
    int columns = 16;
    int rows = 12;
    int nodeSize = 40;

    homePanel = new HomePanel(this);
    bfsPanel = new BfsPanel(columns, rows, nodeSize, this);
    dfsPanel = new DfsPanel(columns, rows, nodeSize, this);
    dijkstraPanel = new DijkstraPanel(columns, rows, nodeSize, this);
    aStarPanel = new AStarPanel(columns, rows, nodeSize, this);

    cards.add(homePanel, "home");
    cards.add(bfsPanel, "bfs");
    cards.add(dfsPanel, "dfs");
    cards.add(dijkstraPanel, "dijkstra");
    cards.add(aStarPanel, "astar");

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setResizable(true);
    setContentPane(cards);
    showHome();
    pack();
    setLocationRelativeTo(null);
  }

  @Override
  public void showHome() {
    layout.show(cards, "home");
    resizeFor(homePanel);
  }

  @Override
  public void showBfs() {
    layout.show(cards, "bfs");
    resizeFor(bfsPanel);
  }

  @Override
  public void showDfs() {
    layout.show(cards, "dfs");
    resizeFor(dfsPanel);
  }

  @Override
  public void showDijkstra() {
    layout.show(cards, "dijkstra");
    resizeFor(dijkstraPanel);
  }

  @Override
  public void showAStar() {
    layout.show(cards, "astar");
    resizeFor(aStarPanel);
  }

  private void resizeFor(JPanel panel) {
    Dimension preferred = panel.getPreferredSize();
    setSize(preferred.width + 20, preferred.height + 80);
  }

  public static void main(String[] args) {
    java.awt.EventQueue.invokeLater(() -> {
      new Game().setVisible(true);
    });
  }
}
