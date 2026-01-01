package pathfinder.core;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;

public abstract class PathfindingPanel extends JPanel {
  protected final int maxColumn;
  protected final int maxRow;
  protected final int nodeSize;
  protected final int screenWidth;
  protected final int screenHeight;

  protected Node[][] node;
  protected final ArrayList<Node> selections = new ArrayList<>();
  protected final ArrayList<Node> openList = new ArrayList<>();
  protected final ArrayList<Node> checkedList = new ArrayList<>();

  protected boolean goalReached = false;
  private boolean showCoordinates = false;

  private final JPanel gridPanel;
  private final JPanel controlPanel = new JPanel();
  private final JButton runButton = new JButton("Run");
  private final JButton restartButton = new JButton("Restart");
  private final JButton coordsButton = new JButton("Toggle Coords");
  private final JButton undoLastButton = new JButton("Undo Last");
  private final JButton backButton = new JButton("Home");
  private final PathfindingScreenHost host;

  public PathfindingPanel(int columns, int rows, int nodeSize, PathfindingScreenHost host) {
    this.maxColumn = columns;
    this.maxRow = rows;
    this.nodeSize = nodeSize;
    this.host = host;
    this.screenWidth = columns * nodeSize;
    this.screenHeight = (rows * nodeSize) + 80;

    this.node = new Node[maxColumn][maxRow];
    this.gridPanel = new JPanel(new GridLayout(maxRow, maxColumn));

    setPreferredSize(new Dimension(screenWidth, screenHeight));
    setBackground(Color.black);
    setDoubleBuffered(true);
    setLayout(new BorderLayout());
    setFocusable(true);

    for (int row = 0; row < maxRow; row++) {
      for (int col = 0; col < maxColumn; col++) {
        node[col][row] = new Node(col, row, this);
        gridPanel.add(node[col][row]);
      }
    }

    controlPanel.setBackground(Color.white);
    styleControlButton(runButton);
    styleControlButton(restartButton);
    styleControlButton(coordsButton);
    styleControlButton(undoLastButton);
    styleControlButton(backButton);

    controlPanel.add(runButton);
    controlPanel.add(restartButton);
    controlPanel.add(coordsButton);
    controlPanel.add(undoLastButton);
    controlPanel.add(backButton);

    add(gridPanel, BorderLayout.CENTER);
    add(controlPanel, BorderLayout.SOUTH);

    runButton.addActionListener(e -> computePathsFromSelections());
    restartButton.addActionListener(e -> resetBoard());
    coordsButton.addActionListener(e -> toggleCoordinates());
    undoLastButton.addActionListener(e -> undoLastSelection());
    backButton.addActionListener(e -> host.showHome());
  }

  public void handleNodeClick(Node clicked) {
    if (clicked == null) {
      return;
    }
    if (!selections.isEmpty() && selections.get(selections.size() - 1) == clicked) {
      return; // ignore duplicate consecutive click
    }
    selections.add(clicked);
    renderSelectionMarkers();
  }

  public boolean isShowingCoordinates() {
    return showCoordinates;
  }

  public void resetBoard() {
    selections.clear();
    clearAllNodes();
  }

  protected void clearAllNodes() {
    goalReached = false;
    openList.clear();
    checkedList.clear();

    for (int row = 0; row < maxRow; row++) {
      for (int col = 0; col < maxColumn; col++) {
        node[col][row].resetToDefault();
      }
    }
    refreshCoordinateDisplay();
  }

  protected void clearTransientSearchState() {
    goalReached = false;
    openList.clear();
    checkedList.clear();

    for (int row = 0; row < maxRow; row++) {
      for (int col = 0; col < maxColumn; col++) {
        Node cell = node[col][row];
        cell.open = false;
        cell.checked = false;
        cell.parent = null;
        cell.distance = Integer.MAX_VALUE;
        cell.gCost = Integer.MAX_VALUE;
        cell.hCost = Integer.MAX_VALUE;
        cell.fCost = Integer.MAX_VALUE;
        if (!cell.start && !cell.goal && !cell.solid && !cell.path) {
          cell.setBackground(Color.white);
          cell.setForeground(Color.black);
          cell.setBaseLabel("");
          cell.setOpaque(true);
        }
      }
    }
  }

  protected void renderSelectionMarkers() {
    clearAllNodes();
    for (int i = 0; i < selections.size(); i++) {
      Node target = selections.get(i);
      target.resetToDefault();
      if (i == 0) {
        target.setAsStart();
      } else {
        target.setBackground(Color.cyan);
        target.setForeground(Color.black);
        target.setBaseLabel("W" + i);
        target.setOpaque(true);
      }
    }
    refreshCoordinateDisplay();
  }

  protected void computePathsFromSelections() {
    clearAllNodes();
    if (selections.isEmpty()) {
      refreshCoordinateDisplay();
      return;
    }

    Node currentStart = selections.get(0);
    currentStart.setAsStart();

    for (int i = 1; i < selections.size(); i++) {
      Node target = selections.get(i);
      target.setAsGoal();
      clearTransientSearchState();
      List<Node> path = findPath(currentStart, target);
      if (path != null) {
        for (Node step : path) {
          if (step != currentStart && step != target) {
            step.setAsPath();
          }
        }
      }
      if (i < selections.size() - 1) {
        target.setAsPath();
        currentStart = target;
        currentStart.setAsStart();
      }
    }
    refreshCoordinateDisplay();
  }

  protected void undoLastSelection() {
    if (selections.isEmpty()) {
      return;
    }
    selections.remove(selections.size() - 1);
    renderSelectionMarkers();
  }

  protected void toggleCoordinates() {
    showCoordinates = !showCoordinates;
    refreshCoordinateDisplay();
  }

  protected void refreshCoordinateDisplay() {
    for (int row = 0; row < maxRow; row++) {
      for (int col = 0; col < maxColumn; col++) {
        node[col][row].refreshDisplay();
      }
    }
  }

  private void styleControlButton(JButton button) {
    button.setFocusPainted(false);
    button.setBackground(Color.white);
    button.setForeground(Color.black);
    button.setOpaque(true);
    button.setBorder(javax.swing.BorderFactory.createLineBorder(Color.black, 1));
    button.addMouseListener(new java.awt.event.MouseAdapter() {
      @Override
      public void mouseEntered(java.awt.event.MouseEvent e) {
        button.setBackground(new Color(235, 235, 235));
      }

      @Override
      public void mouseExited(java.awt.event.MouseEvent e) {
        button.setBackground(Color.white);
      }

      @Override
      public void mousePressed(java.awt.event.MouseEvent e) {
        button.setBackground(new Color(215, 215, 215));
      }

      @Override
      public void mouseReleased(java.awt.event.MouseEvent e) {
        button.setBackground(new Color(235, 235, 235));
      }
    });
  }

  protected List<Node> reconstructPath(Node end, Node start) {
    ArrayDeque<Node> path = new ArrayDeque<>();
    Node current = end;
    while (current != null && current != start) {
      path.addFirst(current);
      current = current.parent;
    }
    return new ArrayList<>(path);
  }

  protected boolean isWalkable(Node n) {
    return n != null && !n.solid;
  }

  protected Node neighbor(int col, int row) {
    if (col < 0 || col >= maxColumn || row < 0 || row >= maxRow) {
      return null;
    }
    return node[col][row];
  }

  protected int manhattan(Node a, Node b) {
    return Math.abs(a.column - b.column) + Math.abs(a.row - b.row);
  }

  protected abstract List<Node> findPath(Node start, Node goal);
}
