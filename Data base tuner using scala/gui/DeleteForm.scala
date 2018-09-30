package gui

import scala.swing._;
import java.awt.Color;
import service.DDLService;
import scala.swing.event.ButtonClicked

class DeleteForm extends MainFrame {

  val srv = new DDLService();

  title = "Delete Form"
  location = new Point(200, 200)
  preferredSize = new Dimension(450, 250)
  background = Color.BLUE

  val btnDelete1 = new Button("Delete") {
    foreground = Color.blue
    borderPainted = true
    enabled = true
    preferredSize = new Dimension(200, 30)

  }
  val btnDelete2 = new Button("Delete By Condition") {
    foreground = Color.blue
    borderPainted = true
    enabled = true
    preferredSize = new Dimension(200, 30)

  }
  val btnDelete3 = new Button("Delete By Between") {
    foreground = Color.blue
    borderPainted = true
    enabled = true
    preferredSize = new Dimension(200, 30)

  }
  
  val btnBack = new Button("Back") {
    foreground = Color.blue
    borderPainted = true
    enabled = true
    preferredSize = new Dimension(300, 30)

  }

  contents = new FlowPanel() {
    vGap = 40
    contents += btnDelete1
    contents += btnDelete2
    contents += btnDelete3
   
    contents += btnBack

  }

  listenTo(btnDelete1)
  listenTo(btnDelete2)
  listenTo(btnDelete3)
  listenTo(btnBack)

  reactions += {
    case ButtonClicked(component) if component == btnDelete1 =>
      var data: Array[Array[Any]] = srv.getTablesForSelect();
      var col: Seq[String] = Seq("Select", "Table name");
      var t = new ShowTables2(data, col, "Delete By show");
      t.visible_=(true)
    case ButtonClicked(component) if component == btnDelete2 =>
      var data: Array[Array[Any]] = srv.getTablesForSelect();
      var col: Seq[String] = Seq("Select", "Table name");
      var t = new ShowTables2(data, col, "Delete By Condition");
      t.visible_=(true)
    case ButtonClicked(component) if component == btnDelete3 =>
      var data: Array[Array[Any]] = srv.getTablesForSelect();
      var col: Seq[String] = Seq("Select", "Table name");
      var t = new ShowTables2(data, col, "Delete By Between");
      t.visible_=(true)
     
    case ButtonClicked(component) if component == btnBack =>
      this.dispose();
      val df = new DMLFrame();
      df.visible_=(true);
  }

}