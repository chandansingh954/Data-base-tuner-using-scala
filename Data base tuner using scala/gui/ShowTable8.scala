package gui

import scala.swing._;
import java.awt.Color;
import java.util.Vector;
import service.DDLService;
import service.DMLService
import javax.swing.table._
import utility.MyTableModel
import scala.swing.event.ButtonClicked
import javax.swing.table.AbstractTableModel

class ShowTable8(data: Array[Array[Any]], col: Seq[Any], t: String, tName: String, vField: Vector[Any]) extends MainFrame {

  val srv = new DMLService();

  var tableName = tName;
  var vecField: Vector[Any] = vField;

  title = t
  location = new Point(500, 200)
  preferredSize = new Dimension(400, 200)
  background = Color.BLUE

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
  }

  contents = new BoxPanel(Orientation.Vertical) {
    contents += sp;
    contents += Swing.VStrut(20)
    contents += Swing.Glue
    contents += new FlowPanel() {
      contents += btnOk
    }
  }

  listenTo(btnOk)

  reactions += {
    case ButtonClicked(component) if component == btnOk =>
      if (this.title.matches("Update in Specific")) {
        var vecValue: Vector[Any] = new Vector[Any];
        var m = new MyModel2(data, col) {
          val r = getRowCount();
          var i: Int = 0;
          while (i < r) {
            if (getValueAt(i, 0) == true) {
              var j: Int = 1
              while (j < vecField.size() + 1) {
                vecValue.add(getValueAt(i, j));
                j = j + 1;
              }
            }

            i = i + 1;
          }
        }
        var l: Int = vecField.size();
        var data1: Array[Array[Any]] = new Array[Array[Any]](l);
        var k: Int = 0;
        while (k < l) {
          var d: Array[Any] = new Array[Any](2);
          d(0) = vecField.get(k);
          d(1) = vecValue.get(k);
          data1(k) = d;
          k = k + 1;
        }
        var col1: Seq[String] = Seq("Field", "Value");
        var iForm = new UpdateSpecificForm(data1, col1, vecField, "Update Form", tableName);
        iForm.visible_=(true);
      } else if (this.title.matches("Delete By show")) {
        var vecValue: Vector[Any] = new Vector[Any]; //
        var m = new MyModel2(data, col) {
          val r = getRowCount();
          var i: Int = 0;
          while (i < r) {
            if (getValueAt(i, 0) == true) {
              vecValue.add(getValueAt(i, 1));
            }

            i = i + 1;
          }
        }
        val keyFieldName: String = vecField.get(0).asInstanceOf[String];
        var sql = " delete from " + tableName + " where " + keyFieldName + " IN (";
        var j: Int = 0;
        while (j < vecValue.size()) {
          sql = sql + "'" + vecValue.get(j) + "',"
          j = j + 1
        }
        sql = sql.substring(0, sql.length() - 1);
        sql = sql + ")";
        var check: Boolean = srv.delete(sql);
        if (check == true) {
          Dialog.showMessage(contents.head, "Succseesfully deleted", title = "You pressed me")
        } else {
          Dialog.showMessage(contents.head, "Failed to delete", title = "You pressed me")
        }
      }
      this.dispose();
  }
}