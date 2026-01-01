package pathfinder.dfs;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import pathfinder.core.Node;
import pathfinder.core.PathfindingPanel;
import pathfinder.core.PathfindingScreenHost;

public class DfsPanel extends PathfindingPanel {

  public DfsPanel(int columns, int rows, int nodeSize, PathfindingScreenHost host) {
    super(columns, rows, nodeSize, host);
  }

  @Override
  protected List<Node> findPath(Node start, Node goal) {
    Stack<Node> stack = new Stack<>();
    Set<Node> visited = new HashSet<>();

    stack.push(start);
    visited.add(start);

    while (!stack.isEmpty()) {
      Node current = stack.pop();
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
        stack.push(neighbor);
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
