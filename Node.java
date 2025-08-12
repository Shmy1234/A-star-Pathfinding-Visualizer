
import java.awt.Color;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class Node extends JButton implements ActionListener{
  Node parent; //past node
  int column;
  int row;
  int gCost;
  int hCost;
  int fCost;
  boolean start;
  boolean goal;
  boolean solid; //can't move into
  boolean open; //if we can slide into here
  boolean checked; 

  private Panel panel;

  public Node(int column, int row, Panel panel) {
    this.column = column;
    this.row = row;
    this.panel = panel;

    setBackground(Color.white);
    setForeground(Color.black);
    setOpaque(true);
    addActionListener(this);
  }

  public void setAsStart() {
    setBackground(Color.pink);
    setForeground(Color.black);
    setText("S");
    start = true;
    setOpaque(true);
  }

  public void setAsGoal() {
    setBackground(Color.blue);
    setForeground(Color.black);
    setText("G");
    goal = true;
    setOpaque(true);
  }
  public void setAsSolid() {
    setBackground(Color.red);
    setForeground(Color.black);
    solid = true;
    setOpaque(true);
  }
  public void setAsOpen() {
    open = true;
  }
  public void setAsChecked() {
    if(start==false && goal==false){
      setBackground(Color.orange);
      setForeground(Color.black);
      setOpaque(true);
    }
    checked = true;
  }

  public void setAsPath() {
    setBackground(Color.gray);
    setForeground(Color.black);
    setText("P");
    setOpaque(true);
  }

  @Override
  public void actionPerformed(java.awt.event.ActionEvent e) {
    if(!start && !goal && !solid) {
      setAsSolid();
    }
    else if (start) {
      panel.search();
    }
    else if (goal) {
      panel.resetSearch();
    }
  }
}
