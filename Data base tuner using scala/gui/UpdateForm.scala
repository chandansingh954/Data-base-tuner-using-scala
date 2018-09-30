package gui

import scala.swing._;
import java.awt.Color;
import service.DDLService;
import scala.swing.event.ButtonClicked

class UpdateForm extends MainFrame {

  val srv = new DDLService();

  title = "Update Form"
  location = new Point(200, 200)
  preferredSize = new Dimension(450, 200)
  background = Color.BLUE

  val btnUpdateInAll = new Button("Update in All") {
    foreground = Color.blue
    borderPainted = true
    enabled = true
    preferredSize = new Dimension(200, 30)

  }
  val btnUpdateInSpecific = new Button("Update in Specific") {
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
    contents += btnUpdateInAll
    contents += btnUpdateInSpecific
   
    contents += btnBack

  }

  listenTo(btnUpdateInAll)
  listenTo(btnUpdateInSpecific)
  listenTo(btnBack)

  reactions += {
    case ButtonClicked(component) if component == btnUpdateInAll =>
      var data: Array[Array[Any]] = srv.getTablesForSelect();
      var col: Seq[String] = Seq("Select", "Table name");
      var t = new ShowTables2(data, col, "Update in All");
      t.visible_=(true)
    case ButtonClicked(component) if component == btnUpdateInSpecific =>
      var data: Array[Array[Any]] = srv.getTablesForSelect();
      var col: Seq[String] = Seq("Select", "Table name");
      var t = new ShowTables2(data, col, "Update in Specific");
      t.visible_=(true)
   
    case ButtonClicked(component) if component == btnBack =>
      this.dispose();
      val df = new DMLFrame();
      df.visible_=(true);
  }

}