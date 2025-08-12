import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JPanel;

public class Panel extends JPanel {
  final int maxColumn = 16;
  final int maxRow = 16;
  final int nodeSize = 70;
  final int screenWidth = maxColumn * nodeSize;
  final int screenHeight = maxRow * nodeSize;

  Node[][] node = new Node[maxColumn][maxRow];
  Node startNode, goalNode, currentNode;
  ArrayList<Node> openList = new ArrayList<>();
  ArrayList<Node> checkedList = new ArrayList<>();

  boolean goalReached = false;

  public Panel() {
    this.setPreferredSize(new java.awt.Dimension(screenWidth, screenHeight));
    this.setBackground(java.awt.Color.black);
    this.setDoubleBuffered(true);
    this.setLayout(new java.awt.GridLayout(maxRow, maxColumn));
    this.setFocusable(true);

    int column = 0;
    int row = 0;
    while (row < maxRow && column < maxColumn) {
      node[column][row] = new Node(column, row, this);
      this.add(node[column][row]);
      column++;
      if (column == maxColumn) {
        column = 0;
        row++;
      }
    }

    setStartNode(2, 3);
    setGoalNode(12, 7);

    // setSolidNode(10, 0);
    // setSolidNode(10, 1);
    // setSolidNode(10, 2);
    // setSolidNode(10, 3);
    // setSolidNode(10, 4);
    // setSolidNode(10, 5);
    // setSolidNode(10, 6);
    // setSolidNode(10, 7);
    // setSolidNode(10, 8);
    // setSolidNode(8, 1);
    // setSolidNode(8, 2);
    // setSolidNode(8, 3);
    // setSolidNode(8, 4);
    // setSolidNode(8, 5);
    // setSolidNode(8, 6);
    // setSolidNode(8, 7);
    // setSolidNode(8, 8);
    // setSolidNode(8, 9);


    int column2 = 0;
    int row2 = 0;

    while (row2 < maxRow && column2 < maxColumn) {
      getCosts(node[column2][row2]);
      column2++;
      if (column2 == maxColumn) {
        column2 = 0;
        row2++;
      }
    }
  }

  private void setStartNode(int col, int row) {
    node[col][row].setAsStart();
    startNode = node[col][row];
    currentNode = startNode;
  }
  private void setGoalNode(int col, int row) {
    node[col][row].setAsGoal();
    goalNode = node[col][row];
  }

  private void setSolidNode(int col, int row) {
    node[col][row].setAsSolid();
  }

  private void getCosts(Node node) { 
    int xDistance = Math.abs(node.column - startNode.column);
    int yDistance = Math.abs(node.row - startNode.row);
    node.gCost = xDistance + yDistance; 

    int xDistance2 = Math.abs(node.column - goalNode.column);
    int yDistance2 = Math.abs(node.row - goalNode.row);
    node.hCost = xDistance2 + yDistance2; 

    node.fCost = node.gCost + node.hCost;

    if(node!= startNode && node != goalNode) {
      node.setText(node.fCost + "," + node.gCost + "," + node.hCost);
    }
  }

  public void search(){
    System.out.println("hi");
    int bestIndex = 0;
    int bestCost = 999;
    while(goalReached==false) {
      int column = currentNode.column;
      int row = currentNode.row;
      currentNode.setAsChecked();
      checkedList.add(currentNode);
      openList.remove(currentNode);

      if(row-1 >= 0) {
        openNode(node[column][row - 1]);
      }
      if(row+1 < maxRow) {
        openNode(node[column][row + 1]);
      }
      if(column-1 >= 0) {
        openNode(node[column - 1][row]);
      }
      if(column+1 < maxColumn) {
        openNode(node[column + 1][row]); 
      }

      for (int i = 0; i < openList.size(); i++) {
        if (openList.get(i).fCost < bestCost) {
          bestCost = openList.get(i).fCost;
          bestIndex = i;
        }
        else if (openList.get(i).fCost == bestCost) {
          if (openList.get(i).gCost < openList.get(bestIndex).gCost) {
            bestIndex = i;
          }
        }
      }

      currentNode = openList.get(bestIndex);

      if (currentNode == goalNode) {
        goalReached = true;
        trackPath();
      }
    }
  }

  public void resetSearch() {
    System.out.println("hi");
    goalReached = false;
    openList.clear();
    checkedList.clear();

    int column = 0;
    int row = 0;

    while (row < maxRow && column < maxColumn) {
      node[column][row].open = false;
      node[column][row].checked = false;
      node[column][row].solid = false;
      if (node[column][row] != startNode && node[column][row] != goalNode) {
        node[column][row].setBackground(Color.white);
        node[column][row].setForeground(Color.black);
        node[column][row].setText(node[column][row].fCost + "," + node[column][row].gCost + "," + node[column][row].hCost);
        node[column][row].setOpaque(true);
      }
      column++;
      if (column == maxColumn) {
        column = 0;
        row++;
      }
    }
    currentNode = startNode;
  }

  private void openNode(Node node) { 
    if (!node.open && !node.solid && !node.checked) {
      node.setAsOpen();
      node.parent = currentNode;
      openList.add(node);
    }
  }

  private void trackPath() { 
    Node node = goalNode;
    while (node != startNode) {
      node = node.parent;
      if (node != startNode) {
        node.setAsPath();
        System.out.println(node.column + "," + node.row);
      }
    }
  }
}
