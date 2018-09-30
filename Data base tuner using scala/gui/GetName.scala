package gui

import scala.swing._;
import java.awt.Color;
import scala.swing.event.ButtonClicked

class GetName(t: String) extends MainFrame {
  title = t;
  location = new Point(500, 200)
  preferredSize = new Dimension(400, 200)
  background = Color.BLUE

  val lbName = new Label(" Name") {
    foreground = Color.blue
    preferredSize = new Dimension(150, 30)

  }
  val tfName = new TextField() {
    foreground = Color.blue
    enabled = true
    preferredSize = new Dimension(150, 30)

  }
  val btnEnter = new Button("Enter") {
    foreground = Color.blue
    borderPainted = true
    enabled = true
    preferredSize = new Dimension(150, 30)

  }

  contents = new FlowPanel() {
    vGap = 40
    contents += lbName
    contents += tfName
    contents += new FlowPanel() {
      contents += btnEnter
    }
  }
  listenTo(btnEnter)

  reactions += {
    case ButtonClicked(component) if component == btnEnter =>
      if (this.title.equals("New Table Name")) {
        val value: String = tfName.text;
        this.dispose();
        var sql: String = " create table " + value + " ( ";
        val v = new CreateTable(sql,"Create New Table");
        v.visible_=(true);
      } 
  }

}  