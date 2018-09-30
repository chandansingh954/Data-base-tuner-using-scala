package gui

import scala.swing._;

import java.awt.Color;
import java.util.Vector;
import scala.swing.event.ButtonClicked
import javax.swing.table.AbstractTableModel
import service.DMLService;

class MyModel5(var cells: Array[Array[Any]], val columns: Seq[Any]) extends AbstractTableModel {
  def getRowCount(): Int = cells.length

  def getColumnCount(): Int = columns.length

  def getValueAt(row: Int, col: Int): AnyRef = cells(row)(col).asInstanceOf[AnyRef]

  // override def getColumnClass(column: Int) = if(column == 0){ Boolean } else{  super.getColumnClass(column)}

  override def isCellEditable(row: Int, column: Int) = if(column == 1) true else  false;

  override def setValueAt(value: Any, row: Int, col: Int) {
    cells(row)(col) = value
    fireTableCellUpdated(row, col)
  }

  override def getColumnName(column: Int): String = columns(column).toString
}
class InsertForm(data:Array[Array[Any]],  col:Seq[Any],vecField:Vector[Any],t:String,tName:String) extends MainFrame {
  
  var srv = new DMLService();
  var tableName = tName;
  title = t;
  location = new Point(500, 200)
  preferredSize = new Dimension(400, 300)
  background = Color.BLUE

   val table = new Table(data, col) {
    foreground = Color.blue
    enabled = true
    preferredSize = new Dimension(350, 150)
    visible = true

    val mymodel = new MyModel5(data, col) {
      // write here to call the method of model class
    }
    model = mymodel

  }
  val sp = new ScrollPane() {
    contents = table
    preferredSize = new Dimension(350, 150)
    visible = true
  }
  
  val btnEnter = new Button("Enter") {
    foreground = Color.blue
    borderPainted = true
    enabled = true
    preferredSize = new Dimension(150, 30)

  }

  contents = new FlowPanel() {
    vGap = 40
    contents += sp;
    contents += btnEnter;
  }
  listenTo(btnEnter)

  reactions += {
    case ButtonClicked(component) if component == btnEnter =>
     var vecValue:Vector[Any] = new Vector[Any];
     var value:Any =null;
     var m = new MyModel5(data,col){
         val r = getRowCount();
          var i: Int = 0;
          while (i < r) {
             value = getValueAt(i,1); 
             vecValue.add(value);
            i = i + 1;
          }
      }
     var check = srv.insert(tableName, vecField, vecValue);
      if (check == true) {
        Dialog.showMessage(contents.head, "Succseesfully insert the data", title = "You pressed me")
      } else {
        Dialog.showMessage(contents.head, "Failed to insert ", title = "You pressed me")
      }
      this.dispose();
  }

}  