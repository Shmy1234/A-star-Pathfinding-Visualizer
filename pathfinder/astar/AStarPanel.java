package pathfinder.astar;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import pathfinder.core.Node;
import pathfinder.core.PathfindingPanel;
import pathfinder.core.PathfindingScreenHost;

public class AStarPanel extends PathfindingPanel {

  public AStarPanel(int columns, int rows, int nodeSize, PathfindingScreenHost host) {
    super(columns, rows, nodeSize, host);
  }

  @Override
  protected List<Node> findPath(Node start, Node goal) {
    PriorityQueue<Node> openQueue =
        new PriorityQueue<>(Comparator.<Node>comparingInt(n -> n.fCost).thenComparingInt(n -> n.gCost));
    Set<Node> openSet = new HashSet<>();
    Set<Node> closedSet = new HashSet<>();

    start.gCost = 0;
    start.hCost = manhattan(start, goal);
    start.fCost = start.gCost + start.hCost;
    openQueue.add(start);
    openSet.add(start);

    while (!openQueue.isEmpty()) {
      Node current = openQueue.poll();
      openSet.remove(current);
      current.setAsChecked();
      closedSet.add(current);

      if (current == goal) {
        return reconstructPath(goal, start);
      }

      for (Node neighbor : neighbors(current)) {
        if (!isWalkable(neighbor) || closedSet.contains(neighbor)) {
          continue;
        }
        int tentativeG = current.gCost + 1;
        boolean betterPath = tentativeG < neighbor.gCost || !openSet.contains(neighbor);
        if (betterPath) {
          neighbor.parent = current;
          neighbor.gCost = tentativeG;
          neighbor.hCost = manhattan(neighbor, goal);
          neighbor.fCost = neighbor.gCost + neighbor.hCost;
          if (!openSet.contains(neighbor)) {
            neighbor.setAsOpen();
            openSet.add(neighbor);
            openQueue.add(neighbor);
          }
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
