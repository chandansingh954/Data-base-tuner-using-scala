package gui

import scala.swing._;
import java.awt.Color;
import service.DDLService;
import javax.swing.table._
import utility.MyTableModel
import scala.swing.event.ButtonClicked
import javax.swing.table.AbstractTableModel

class MyModel3(var cells: Array[Array[Any]], val columns: Seq[Any]) extends AbstractTableModel {
  def getRowCount(): Int = cells.length

  def getColumnCount(): Int = columns.length

  def getValueAt(row: Int, col: Int): AnyRef = cells(row)(col).asInstanceOf[AnyRef]

  // override def getColumnClass(column: Int) = if(column == 0){ Boolean } else{  super.getColumnClass(column)}

  override def isCellEditable(row: Int, column: Int) = if (column == 0 || row != 0) true else false

  override def setValueAt(value: Any, row: Int, col: Int) {
    cells(row)(col) = value
    fireTableCellUpdated(row, col)
  }

  override def getColumnName(column: Int): String = columns(column).toString
}

class ShowTables4(data: Array[Array[Any]], col: Seq[Any], t: String, tName: String) extends MainFrame {
  val srv = new DDLService();

  var tableName = tName;
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

    val mymodel = new MyModel3(data, col) {
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
      var value: Any = null;
      if (this.title.equals("Drop column")) {
        var m = new MyModel3(data, col) {
          val r = getRowCount();
          var i: Int = 0;
          while (i < r) {
            if (getValueAt(i, 0) == true) {
              value = getValueAt(i, 1)
            }
            i = i + 1;
          }
        }
        var check = srv.dropOneColumn(tableName, value.asInstanceOf[String]);
        if (check == true) {
          Dialog.showMessage(contents.head, "Succseesfully drop one column", title = "You pressed me")
        } else {
          Dialog.showMessage(contents.head, "Failed to drop one column ", title = "You pressed me")
        }
      }else if (this.title.equals("Modify column")) {
        var m = new MyModel3(data, col) {
          val r = getRowCount();
          var i: Int = 0;
          while (i < r) {
            if (getValueAt(i, 0) == true) {
              value = getValueAt(i, 1)
            }
            i = i + 1;
          }
        }
        val v = new FieldNameType(tableName, value.asInstanceOf[String],"Modify Column" );
        v.visible_=(true)

        
      }
      this.dispose();
  }

}
  
 