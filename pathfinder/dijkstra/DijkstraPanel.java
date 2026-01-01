package pathfinder.dijkstra;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import pathfinder.core.Node;
import pathfinder.core.PathfindingPanel;
import pathfinder.core.PathfindingScreenHost;

public class DijkstraPanel extends PathfindingPanel {

  public DijkstraPanel(int columns, int rows, int nodeSize, PathfindingScreenHost host) {
    super(columns, rows, nodeSize, host);
  }

  @Override
  protected List<Node> findPath(Node start, Node goal) {
    PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(n -> n.distance));
    Set<Node> visited = new HashSet<>();

    start.distance = 0;
    queue.add(start);

    while (!queue.isEmpty()) {
      Node current = queue.poll();
      if (visited.contains(current)) {
        continue;
      }
      visited.add(current);
      current.setAsChecked();

      if (current == goal) {
        return reconstructPath(goal, start);
      }

      for (Node neighbor : neighbors(current)) {
        if (!isWalkable(neighbor) || visited.contains(neighbor)) {
          continue;
        }
        int tentative = current.distance + 1;
        if (tentative < neighbor.distance) {
          neighbor.distance = tentative;
          neighbor.parent = current;
          neighbor.setAsOpen();
          queue.add(neighbor);
        }
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
