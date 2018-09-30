package gui

import scala.swing._;
import java.awt.Color;
import service.DDLService;
import javax.swing.table._
import utility.MyTableModel
import scala.swing.event.ButtonClicked
import javax.swing.table.AbstractTableModel

class MyModel1(var cells: Array[Array[Any]], val columns: Seq[Any]) extends AbstractTableModel {
  def getRowCount(): Int = cells.length

  def getColumnCount(): Int = columns.length

  def getValueAt(row: Int, col: Int): AnyRef = cells(row)(col).asInstanceOf[AnyRef]

  override def getColumnClass(column: Int) = getValueAt(0, column).getClass

  override def isCellEditable(row: Int, column: Int) = false

  override def setValueAt(value: Any, row: Int, col: Int) {
    cells(row)(col) = value
    fireTableCellUpdated(row, col)
  }

  override def getColumnName(column: Int): String = columns(column).toString
}
class ShowTables(data: Array[Array[Any]], col: Seq[Any], t: String) extends MainFrame {
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

    val mymodel = new MyModel1(data, col) {
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
      this.dispose();
  }

}