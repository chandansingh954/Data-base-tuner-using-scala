package gui

import scala.swing._;
import java.awt.Color;
import service.DDLService;
import javax.swing.table._
import utility.MyTableModel
import scala.swing.event.ButtonClicked
import javax.swing.table.AbstractTableModel

class ShowTables3(data: Array[Array[Any]], col: Seq[Any], t: String) extends MainFrame {
  val srv = new DDLService();

  title = t
  location = new Point(500, 200)
  preferredSize = new Dimension(400, 300)
  background = Color.BLUE

  val lbName = new Label(" New Table Name") {
    foreground = Color.blue
    preferredSize = new Dimension(150, 30)

  }
  val tfName = new TextField() {
    foreground = Color.blue
    enabled = true
    preferredSize = new Dimension(150, 30)

  }

  val btnOk = new Button("Ok ") {
    foreground = Color.blue
    borderPainted = true
    enabled = true
    preferredSize = new Dimension(150, 30)

  }
  val table = new Table(data, col) {
    foreground = Color.blue
    enabled = true
    preferredSize = new Dimension(400, 100)
    visible = true

    val mymodel = new MyModel2(data, col) {
      // write here to call the method of model class
    }
    model = mymodel

  }

  val sp = new ScrollPane() {
    contents = table
    visible = true
    preferredSize = new Dimension(400, 100)
  }

  contents = new FlowPanel() {
    contents += lbName
    contents += tfName
    contents += new BoxPanel(Orientation.Vertical) {
      contents += sp;
      contents += Swing.VStrut(10)
      contents += Swing.Glue
    }
    contents += new FlowPanel() {
      contents += btnOk
    }
  }

  listenTo(btnOk)

  reactions += {
    case ButtonClicked(component) if component == btnOk =>

      var tfValue: String = tfName.text
      var value: Any = null;
      if (this.title.equals("Table New Name")) {
        var m = new MyModel2(data, col) {
          val r = getRowCount();
          var i: Int = 0;
          while (i < r) {
            if (getValueAt(i, 0) == true) {
              value = getValueAt(i, 1)
            }
            i = i + 1;
          }
        }
        var check: Boolean = srv.createNewTableFromExisting(value.asInstanceOf[String], tfValue);
        if (check == true) {
          Dialog.showMessage(contents.head, "Succseesfully new table has been created from existing table", title = "You pressed me")
        } else {
          Dialog.showMessage(contents.head, "Failed to create existing table ", title = "You pressed me")
        }
      } else if (this.title.equals("Rename Table")) {
        var m = new MyModel2(data, col) {
          val r = getRowCount();
          var i: Int = 0;
          while (i < r) {
            if (getValueAt(i, 0) == true) {
              value = getValueAt(i, 1)
            }
            i = i + 1;
          }
        }
        var check: Boolean = srv.renameTable(value.asInstanceOf[String], tfValue);
        if (check == true) {
          Dialog.showMessage(contents.head, "Succseesfully rename the table", title = "You pressed me")
        } else {
          Dialog.showMessage(contents.head, "Failed to crename the table ", title = "You pressed me")
        }
      }

      this.dispose();

  }
}

