package gui

import scala.swing._;
import java.awt.Color;
import service.DMLService;
import javax.swing.table._
import java.util.Vector;
import utility.MyTableModel
import scala.swing.event.ButtonClicked
import javax.swing.table.AbstractTableModel

class ShowTable6(data: Array[Array[Any]], col: Seq[Any], t: String, keyField: String, tName: String, vecField: Vector[String], vecType: Vector[String]) extends MainFrame {

  var tableName = tName;
  val srv = new DMLService();
  title = t
  location = new Point(500, 200)
  preferredSize = new Dimension(400, 200)
  background = Color.BLUE

  val btnOk = new Button("Ok ") {
    foreground = Color.blue
    borderPainted = true
    enabled = true
    preferredSize = new Dimension(100, 30)

  }
  val btnCancel = new Button("Cancel ") {
    foreground = Color.blue
    borderPainted = true
    enabled = true
    preferredSize = new Dimension(100, 30)

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
  }

  contents = new BoxPanel(Orientation.Vertical) {
    contents += sp;
    contents += Swing.VStrut(20)
    contents += Swing.Glue
    contents += new FlowPanel() {
      contents += btnCancel
      contents += btnOk
    }
  }

  listenTo(btnOk)

  reactions += {
    case ButtonClicked(component) if component == btnOk =>

      if (this.title.equals("Read Single Row")) {
        var value: Any = null;
        var m = new MyModel2(data, col) {
          val r = getRowCount();
          var i: Int = 0;
          while (i < r) {
            if (getValueAt(i, 0) == true) {
              value = getValueAt(i, 1);
            }
            i = i + 1;
          }
        }
        var singleData: Array[Array[Any]] = srv.getSingleFieldData(tableName, keyField, value.asInstanceOf[Int], vecType);
        if (singleData == null) {
          Dialog.showMessage(contents.head, "Some problem to get single the table", title = "You pressed me")
        } else {
          var sz: Int = vecField.size();
          var singleCol: Array[Any] = new Array[Any](sz);
          var j: Int = 0;
          while (j < sz) {
            singleCol(j) = vecField.get(j);
            j = j + 1;
          }
          var shTb1 = new ShowTables(singleData, singleCol.toSeq, "Read Single Row");
          shTb1.visible_=(true);
        }
      }
    case ButtonClicked(component) if component == btnCancel =>
      this.dispose();
  }
}