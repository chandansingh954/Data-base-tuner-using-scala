package gui

import scala.swing._;
import java.awt.Color;
import service.DMLService;
import javax.swing.table._
import java.util.Vector;
import utility.MyTableModel
import scala.swing.event.ButtonClicked
import javax.swing.table.AbstractTableModel

class MyModel4(var cells: Array[Array[Any]], val columns: Seq[Any]) extends AbstractTableModel {
  def getRowCount(): Int = cells.length

  def getColumnCount(): Int = columns.length

  def getValueAt(row: Int, col: Int): AnyRef = cells(row)(col).asInstanceOf[AnyRef]

  // override def getColumnClass(column: Int) = if(column == 0){ Boolean } else{  super.getColumnClass(column)}

  override def isCellEditable(row: Int, column: Int) = false

  override def setValueAt(value: Any, row: Int, col: Int) {
    cells(row)(col) = value
    fireTableCellUpdated(row, col)
  }

  override def getColumnName(column: Int): String = columns(column).toString
}

class ShowTable5(data: Array[Array[Any]], col: Seq[Any], t: String, tName: String) extends MainFrame {

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
    preferredSize = new Dimension(150, 30)

  }
  val table = new Table(data, col) {
    foreground = Color.blue
    enabled = true
    preferredSize = new Dimension(400, 100)
    visible = true

    val mymodel = new MyModel4(data, col) {
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
      if (this.title.matches("Insert")) {
        var value: Any = null;

        var vecField: Vector[Any] = new Vector[Any];
        var m = new MyModel4(data, col) {
          val r = getRowCount();
          println("row count " + r);
          var i: Int = 0;
          while (i < r) {
            if (getValueAt(i, 5).equals("auto_increment")) {
            } else if (getValueAt(i, 5).equals("")) {
              value = getValueAt(i, 0)
              vecField.add(value.asInstanceOf[String]);
            }
            i = i + 1;
          }
        }
        println(vecField);
        var l: Int = vecField.size();
        println(l);
        var data1: Array[Array[Any]] = new Array[Array[Any]](l);
        var j: Int = 0;
        while (j < l) {
          var d: Array[Any] = new Array[Any](2);
          println(vecField.get(j));
          d(0) = vecField.get(j);

          data1(j) = d;
          j = j + 1;
        }
        var col1: Seq[String] = Seq("Field", "Value");
        var iForm = new InsertForm(data1, col1, vecField, "Insert Form", tableName);
        iForm.visible_=(true);
      }else if(this.title.matches("Read All Data")){
        var vecType:Vector[String] = new Vector[String];
        var vecField:Vector[String]  = new Vector[String];
        var m = new MyModel4(data, col) {
          val r = getRowCount();
          var i: Int = 0;
          while (i < r) {
            vecField.add(getValueAt(i,0).asInstanceOf[String]);
            vecType.add(getValueAt(i, 1).asInstanceOf[String]);
            i = i + 1;
          }
        }
        var allData: Array[Array[Any]] = srv.getAllData(tableName, vecType);
        var allCol:Array[Any] = new Array[Any](vecField.size());
        var j :Int =0;
        while(j<vecField.size()){
          allCol(j) = vecField.get(j);
          j =j+1;
        }
        var st  = new ShowTables(allData,allCol.toSeq,"Read All Data");
        st.visible_=(true);
        
        
      } else if (this.title.matches("Update in Specific")) {
        var value: Any = null;
        var vecType: Vector[String] = new Vector[String];
        var vecField: Vector[Any] = new Vector[Any];
        var m = new MyModel4(data, col) {
          val r = getRowCount();
          var i: Int = 0;
          while (i < r) {
            value = getValueAt(i, 0)
            vecField.add(value.asInstanceOf[String]);
            vecType.add(getValueAt(i, 1).asInstanceOf[String]);
            i = i + 1;
          }
        }
        var columns: Array[Any] = new Array[Any](vecField.size() + 1);
        var j: Int = 0;
        while (j < vecField.size() + 1) {
          if (j == 0) {
            columns(j) = false;
          } else {
            columns(j) = vecField.get(j-1);
          }
          j = j + 1;
        }
        var allData: Array[Array[Any]] = srv.getAllDataWithSelect(tableName, vecType);
        val sh = new ShowTable8(allData, columns.toSeq, "Update in Specific",tableName,vecField);
        sh.visible_=(true)
      }else if (this.title.matches("Delete By show")) {
        var value: Any = null;
        var vecType: Vector[String] = new Vector[String];
        var vecField: Vector[Any] = new Vector[Any];
        var m = new MyModel4(data, col) {
          val r = getRowCount();
          var i: Int = 0;
          while (i < r) {
            value = getValueAt(i, 0)
            vecField.add(value.asInstanceOf[String]);
            vecType.add(getValueAt(i, 1).asInstanceOf[String]);
            i = i + 1;
          }
        }
        var columns: Array[Any] = new Array[Any](vecField.size() + 1);
        var j: Int = 0;
        while (j < vecField.size() + 1) {
          if (j == 0) {
            columns(j) = false;
          } else {
            columns(j) = vecField.get(j-1);
          }
          j = j + 1;
        }
        var allData: Array[Array[Any]] = srv.getAllDataWithSelect(tableName, vecType);
        val sh = new ShowTable8(allData, columns.toSeq, "Delete By show",tableName,vecField);
        sh.visible_=(true)
      }
      this.dispose();

  }

}