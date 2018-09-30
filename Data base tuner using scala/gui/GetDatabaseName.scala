package gui

import scala.swing._;
import java.awt.Color;
import service.DDLService;
import scala.swing.event.ButtonClicked

class GetDatabaseName(ms: String) extends MainFrame {

  val srv = new DDLService();

  title = " Database Name";
  location = new Point(500, 200)
  preferredSize = new Dimension(400, 200)
  background = Color.BLUE

  val lbName = new Label(" Database Name") {
    foreground = Color.blue
    preferredSize = new Dimension(150, 30)

  }
  val tfName = new TextField() {
    foreground = Color.blue
    enabled = true
    preferredSize = new Dimension(150, 30)

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
        this.dispose();
        val check = srv.createDatabase(tfName.text);
        if (check == true) {
          Dialog.showMessage(contents.head, "Succseesfully database is  created", title = "You pressed me")
        } else {
          Dialog.showMessage(contents.head, "Failed to create database ", title = "You pressed me")
        }
      }
  }
}