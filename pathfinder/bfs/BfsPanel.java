package pathfinder.bfs;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import pathfinder.core.Node;
import pathfinder.core.PathfindingPanel;
import pathfinder.core.PathfindingScreenHost;

public class BfsPanel extends PathfindingPanel {

  public BfsPanel(int columns, int rows, int nodeSize, PathfindingScreenHost host) {
    super(columns, rows, nodeSize, host);
  }

  @Override
  protected List<Node> findPath(Node start, Node goal) {
    ArrayDeque<Node> queue = new ArrayDeque<>();
    Set<Node> visited = new HashSet<>();

    queue.add(start);
    visited.add(start);

    while (!queue.isEmpty()) {
      Node current = queue.poll();
      current.setAsChecked();
      if (current == goal) {
        return reconstructPath(goal, start);
      }

      for (Node neighbor : neighbors(current)) {
        if (!isWalkable(neighbor) || visited.contains(neighbor)) {
          continue;
        }
        neighbor.parent = current;
        neighbor.setAsOpen();
        visited.add(neighbor);
        queue.add(neighbor);
      }
    }
    return null;
  }

  private Node[] neighbors(Node current) {
    return new Node[] {
      neighbor(current.column, current.row - 1),
      neighbor(current.column, current.row + 1),
      neighbor(current.column - 1, current.row),
      neighbor(current.column + 1, current.row)
    };
  }
}
