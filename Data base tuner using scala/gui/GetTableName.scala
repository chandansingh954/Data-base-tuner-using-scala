package gui

import scala.swing._;
import java.awt.Color;
import service.DDLService;
import scala.swing.event.ButtonClicked

class GetTableName(ms: String) extends MainFrame {

  val srv = new DDLService();

  title = " Table Name";
  location = new Point(500, 200)
  preferredSize = new Dimension(400, 200)
  background = Color.BLUE

  val lbName = new Label("Table Name") {
    foreground = Color.blue
    preferredSize = new Dimension(150, 50)

  }
  val tfName = new TextField() {
    foreground = Color.blue
    enabled = true
    preferredSize = new Dimension(150, 50)

  }
  val btnCreateDatabase = new Button("Enter") {
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
      contents += btnCreateDatabase
    }
  }
  listenTo(btnCreateDatabase)

  reactions += {
    case ButtonClicked(component) if component == btnCreateDatabase =>
      if (ms.equals("createDatabase")) {
        val check = srv.createDatabase(tfName.text);
        if (check == true) {
          Dialog.showMessage(contents.head, "Succseesfully database is  created", title = "You pressed me")
        } else {
          Dialog.showMessage(contents.head, "Failed to create database ", title = "You pressed me")
        }
      }
      else if (ms.equals("useDatabase")) {
        val check = srv.useDatabase(tfName.text);
        if (check == true) {
          Dialog.showMessage(contents.head, "Succseesfully database is  changed", title = "You pressed me")
        } else {
          Dialog.showMessage(contents.head, "Failed to change database ", title = "You pressed me")
        }
      }
  }
}