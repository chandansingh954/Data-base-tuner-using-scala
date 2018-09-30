package gui

import scala.swing._;
import java.awt.Color;
import service.DDLService;
import scala.swing.event.ButtonClicked

class DMLFrame extends MainFrame {

  val srv = new DDLService();

  title = "DML Frame"
  location = new Point(200, 200)
  preferredSize = new Dimension(400, 300)
  background = Color.BLUE

  val btnInsert = new Button("Insert") {
    foreground = Color.blue
    borderPainted = true
    enabled = true
    preferredSize = new Dimension(150, 30)

  }
  val btnRead = new Button("Read") {
    foreground = Color.blue
    borderPainted = true
    enabled = true
    preferredSize = new Dimension(150, 30)

  }
  val btnUpdate = new Button("Update") {
    foreground = Color.blue
    borderPainted = true
    enabled = true
    preferredSize = new Dimension(150, 30)

  }
  val btnDelete = new Button("Delete") {
    foreground = Color.blue
    borderPainted = true
    enabled = true
    preferredSize = new Dimension(150, 30)

  }
  val btnBack = new Button("Back") {
    foreground = Color.blue
    borderPainted = true
    enabled = true
    preferredSize = new Dimension(150, 30)

  }

  contents = new FlowPanel() {
    vGap = 40
    contents += btnInsert
    contents += btnRead
    contents += btnUpdate
    contents += btnDelete
    contents += btnBack

  }
  listenTo(btnInsert)
  listenTo(btnRead)
  listenTo(btnUpdate)
  listenTo(btnDelete)
  listenTo(btnBack)

  reactions += {
    case ButtonClicked(component) if component == btnInsert =>
      var data: Array[Array[Any]] = srv.getTablesForSelect();
      var col: Seq[String] = Seq("Select", "Table name");
      var t = new ShowTables2(data, col, "Insert");
      t.visible_=(true)
    case ButtonClicked(component) if component == btnRead =>
      this.dispose();
      var rf = new ReadForm();
      rf.visible_=(true);
    case ButtonClicked(component) if component == btnUpdate =>
      this.dispose();
      var uf = new UpdateForm();
      uf.visible_=(true);
    case ButtonClicked(component) if component == btnDelete =>
      this.dispose();
      var df = new DeleteForm();
      df.visible_=(true);  
    case ButtonClicked(component) if component == btnBack =>
      this.dispose()
      val ff = new FirstForm();
      ff.visible_=(true);
  }

}