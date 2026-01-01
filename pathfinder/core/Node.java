package pathfinder.core;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class Node extends JButton implements ActionListener {
  public final int column;
  public final int row;

  public Node parent;
  public int gCost;
  public int hCost;
  public int fCost;
  public int distance = Integer.MAX_VALUE;
  public boolean start;
  public boolean goal;
  public boolean solid;
  public boolean open;
  public boolean checked;
  public boolean path;

  private final PathfindingPanel panel;
  private String baseLabel = "";

  public Node(int column, int row, PathfindingPanel panel) {
    this.column = column;
    this.row = row;
    this.panel = panel;

    setBackground(Color.white);
    setForeground(Color.black);
    setOpaque(true);
    addActionListener(this);
  }

  public void resetToDefault() {
    parent = null;
    start = false;
    goal = false;
    solid = false;
    open = false;
    checked = false;
    path = false;
    distance = Integer.MAX_VALUE;
    setBaseLabel("");
    setBackground(Color.white);
    setForeground(Color.black);
    setOpaque(true);
  }

  public void setAsStart() {
    start = true;
    goal = false;
    path = false;
    setBackground(Color.pink);
    setForeground(Color.black);
    setBaseLabel("");
    setOpaque(true);
  }

  public void setAsGoal() {
    goal = true;
    start = false;
    path = false;
    setBackground(Color.blue);
    setForeground(Color.white);
    setBaseLabel("G");
    setOpaque(true);
  }

  public void setAsSolid() {
    solid = true;
    path = false;
    setBackground(Color.red);
    setForeground(Color.black);
    setOpaque(true);
  }

  public void setAsOpen() {
    open = true;
    if (!start && !goal && !path) {
      setBackground(new Color(200, 255, 200));
    }
  }

  public void setAsChecked() {
    if (!start && !goal && !path) {
      setBackground(Color.orange);
      setForeground(Color.black);
      setOpaque(true);
    }
    checked = true;
  }

  public void setAsPath() {
    path = true;
    start = false;
    goal = false;
    setBackground(Color.gray);
    setForeground(Color.black);
    setBaseLabel("P");
    setOpaque(true);
  }

  public void setBaseLabel(String label) {
    baseLabel = label == null ? "" : label;
    refreshDisplay();
  }

  public void refreshDisplay() {
    if (panel.isShowingCoordinates()) {
      setText(column + "," + row);
    } else {
      setText(baseLabel);
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    panel.handleNodeClick(this);
  }
}
