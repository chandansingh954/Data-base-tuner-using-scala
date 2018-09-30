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

class MyModel2(var cells: Array[Array[Any]], val columns: Seq[Any]) extends AbstractTableModel {
  def getRowCount(): Int = cells.length

  def getColumnCount(): Int = columns.length

  def getValueAt(row: Int, col: Int): AnyRef = cells(row)(col).asInstanceOf[AnyRef]

  // override def getColumnClass(column: Int) = if(column == 0){ Boolean } else{  super.getColumnClass(column)}

  override def isCellEditable(row: Int, column: Int) = if (column == 0) true else false

  override def setValueAt(value: Any, row: Int, col: Int) {
    cells(row)(col) = value
    fireTableCellUpdated(row, col)
  }

  override def getColumnName(column: Int): String = columns(column).toString
}
class ShowTables2(data: Array[Array[Any]], col: Seq[Any], t: String) extends MainFrame {

  val srv = new DDLService();
  val srv1 = new DMLService();
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
      var value: Any = null;
      if (this.title.equals("Select Database")) {
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
        var check: Boolean = srv.useDatabase(value.asInstanceOf[String]);
        if (check == true) {
          Dialog.showMessage(contents.head, "Succseesfully database has beeen selected", title = "You pressed me")
        } else {
          Dialog.showMessage(contents.head, "Failed to use database ", title = "You pressed me")
        }
      } else if (this.title.equals("Select Table")) {
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
        var desData: Array[Array[Any]] = srv.describeTable(value.asInstanceOf[String]);
        if (desData == null) {
          Dialog.showMessage(contents.head, "Some froblem occured to describe the table", title = "You pressed me")
        } else {
          var col1: Seq[String] = Seq("Field", "Type", "null", "Key", "Default", "Extra");
          val desTable = new ShowTables(desData, col1, "Describe Table");
          desTable.visible_=(true)
        }
      } else if (this.title.equals("Drop Table")) {
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
        var check: Boolean = srv.dropTable(value.asInstanceOf[String]);
        if (check == true) {
          Dialog.showMessage(contents.head, "Succseesfully drop the table", title = "You pressed me")
        } else {
          Dialog.showMessage(contents.head, "Failed to drop the table", title = "You pressed me")
        }
      } else if (this.title.equals("Add new Column")) {
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
        var sql: String = "alter table   " + value.asInstanceOf[String] + " add ( ";
        val v = new CreateTable(sql, "Add new Column");
        v.visible_=(true);
      } else if (this.title.equals("Drop Column")) {
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
        var desData: Array[Array[Any]] = srv.describeTableForSelect(value.asInstanceOf[String]);
        if (desData == null) {
          Dialog.showMessage(contents.head, "Some froblem occured to describe the table", title = "You pressed me")
        } else {
          var col1: Seq[String] = Seq("Select", "Field", "Type", "null", "Key", "Default", "Extra");
          val desTable = new ShowTables4(desData, col1, "Drop column", value.asInstanceOf[String]);
          desTable.visible_=(true)
        }
      } else if (this.title.equals("Modify Column")) {
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
        var desData: Array[Array[Any]] = srv.describeTableForSelect(value.asInstanceOf[String]);
        if (desData == null) {
          Dialog.showMessage(contents.head, "Some froblem occured to describe the table", title = "You pressed me")
        } else {
          var col1: Seq[String] = Seq("Select", "Field", "Type", "null", "Key", "Default", "Extra");
          val desTable = new ShowTables4(desData, col1, "Modify column", value.asInstanceOf[String]);
          desTable.visible_=(true)
        }
      } else if (this.title.equals("Insert")) {
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
        var desData: Array[Array[Any]] = srv.describeTable(value.asInstanceOf[String]);
        if (desData == null) {
          Dialog.showMessage(contents.head, "Some froblem occured to describe the table", title = "You pressed me")
        } else {
          var col1: Seq[String] = Seq("Field", "Type", "null", "Key", "Default", "Extra");
          val desTable = new ShowTable5(desData, col1, "Insert", value.asInstanceOf[String]);
          desTable.visible_=(true)
        }
      } else if (this.title.equals("Read All Data")) {
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
        var desData: Array[Array[Any]] = srv.describeTable(value.asInstanceOf[String]);
        if (desData == null) {
          Dialog.showMessage(contents.head, "Some problem to describe the table", title = "You pressed me")
        } else {
          var col1: Seq[String] = Seq("Field", "Type", "null", "Key", "Default", "Extra");
          val desTable = new ShowTable5(desData, col1, "Read All Data", value.asInstanceOf[String]);
          desTable.visible_=(true)
        }
      } else if (this.title.equals("Read Single Row")) {
        var m = new MyModel2(data, col) {
          val r = getRowCount();
          var i: Int = 0;
          while (i < r) {
            if (getValueAt(i, 0) == true) {
              value = getValueAt(i, 1); // table name
            }
            i = i + 1;
          }
        }
        var desData: Array[Array[Any]] = srv.describeTable(value.asInstanceOf[String]);
        if (desData == null) {
          Dialog.showMessage(contents.head, "Some problem to describe the table", title = "You pressed me")
        } else {
          var value1: Any = null; // key field name
          var value2: Any = null; // key field type
          var vecType: Vector[String] = new Vector[String]; // types
          var vecField: Vector[String] = new Vector[String]; // fields
          var col1: Seq[String] = Seq("Field", "Type", "null", "Key", "Default", "Extra");
          var m1 = new MyModel1(desData, col1) {
            val r1 = getRowCount();
            var j: Int = 0;
            while (j < r1) {
              if (getValueAt(j, 3).equals("PRI")) {
                value1 = getValueAt(j, 0);
                value2 = getValueAt(j, 1);
              }
              j = j + 1;
            }
            var k: Int = 0;
            while (k < r1) {

              vecField.add(getValueAt(k, 0).asInstanceOf[String]);
              vecType.add(getValueAt(k, 1).asInstanceOf[String]);
              k = k + 1;
            }
          }

          var keyData: Array[Array[Any]] = srv1.getKeyField(value1.asInstanceOf[String], value2.asInstanceOf[String], value.asInstanceOf[String]);
          if (keyData == null) {
            Dialog.showMessage(contents.head, "Some problem to get the key data", title = "You pressed me")
          } else {
            val keyCol: Seq[Any] = Seq("Select", value1.asInstanceOf[String]);
            val agForm = new ShowTable6(keyData, keyCol, "Read Single Row", value1.asInstanceOf[String], value.asInstanceOf[String], vecField, vecType);
            agForm.visible_=(true)

          }
        }
      } else if (this.title.equals("Read Specific column Row")) {
        var m = new MyModel2(data, col) {
          val r = getRowCount();
          var i: Int = 0;
          while (i < r) {
            if (getValueAt(i, 0) == true) {
              value = getValueAt(i, 1); // table name
            }
            i = i + 1;
          }
        }
        var desData: Array[Array[Any]] = srv.describeTableForSelect(value.asInstanceOf[String]);
        if (desData == null) {
          Dialog.showMessage(contents.head, "Some problem to describe the table", title = "You pressed me")
        } else {
          var col1: Seq[String] = Seq("Select", "Field", "Type", "null", "Key", "Default", "Extra");
          val desTable = new ShowTable7(desData, col1, "Read Specific column Row", value.asInstanceOf[String]);
          desTable.visible_=(true)
        }
      } else if (this.title.equals("Read Specific column Row with condition")) {
        var m = new MyModel2(data, col) {
          val r = getRowCount();
          var i: Int = 0;
          while (i < r) {
            if (getValueAt(i, 0) == true) {
              value = getValueAt(i, 1); // table name
            }
            i = i + 1;
          }
        }
        var desData: Array[Array[Any]] = srv.describeTableForSelect(value.asInstanceOf[String]);
        if (desData == null) {
          Dialog.showMessage(contents.head, "Some problem to describe the table", title = "You pressed me")
        } else {
          var col1: Seq[String] = Seq("Select", "Field", "Type", "null", "Key", "Default", "Extra");
          val desTable = new ShowTable7(desData, col1, "Read Specific column Row with condition", value.asInstanceOf[String]);
          desTable.visible_=(true);
        }
      } else if (this.title.equals("Read Specific column Row with Order")) {
        var m = new MyModel2(data, col) {
          val r = getRowCount();
          var i: Int = 0;
          while (i < r) {
            if (getValueAt(i, 0) == true) {
              value = getValueAt(i, 1); // table name
            }
            i = i + 1;
          }
        }
        var desData: Array[Array[Any]] = srv.describeTableForSelect(value.asInstanceOf[String]);
        if (desData == null) {
          Dialog.showMessage(contents.head, "Some problem to describe the table", title = "You pressed me")
        } else {
          var col1: Seq[String] = Seq("Select", "Field", "Type", "null", "Key", "Default", "Extra");
          val desTable = new ShowTable7(desData, col1, "Read Specific column Row with Order", value.asInstanceOf[String]);
          desTable.visible_=(true)
        }
      } else if (this.title.equals("Read Specific column Row with Max and Min")) {
        var m = new MyModel2(data, col) {
          val r = getRowCount();
          var i: Int = 0;
          while (i < r) {
            if (getValueAt(i, 0) == true) {
              value = getValueAt(i, 1); // table name
            }
            i = i + 1;
          }
        }
        var desData: Array[Array[Any]] = srv.describeTableForSelect(value.asInstanceOf[String]);
        if (desData == null) {
          Dialog.showMessage(contents.head, "Some problem to describe the table", title = "You pressed me")
        } else {
          var col1: Seq[String] = Seq("Select", "Field", "Type", "null", "Key", "Default", "Extra");
          val desTable = new ShowTable7(desData, col1, "Read Specific column Row with Max and Min", value.asInstanceOf[String]);
          desTable.visible_=(true)
        }

      } else if (this.title.equals("Read Specific column Row with In Notin Rows")) {
        var m = new MyModel2(data, col) {
          val r = getRowCount();
          var i: Int = 0;
          while (i < r) {
            if (getValueAt(i, 0) == true) {
              value = getValueAt(i, 1); // table name
            }
            i = i + 1;
          }
        }
        var desData: Array[Array[Any]] = srv.describeTableForSelect(value.asInstanceOf[String]);
        if (desData == null) {
          Dialog.showMessage(contents.head, "Some problem to describe the table", title = "You pressed me")
        } else {
          var col1: Seq[String] = Seq("Select", "Field", "Type", "null", "Key", "Default", "Extra");
          val desTable = new ShowTable7(desData, col1, "Read Specific column Row with In Notin Rows", value.asInstanceOf[String]);
          desTable.visible_=(true)
        }

      } else if (this.title.equals("Read Specific column Row with Aggregatefunction")) {
        var m = new MyModel2(data, col) {
          val r = getRowCount();
          var i: Int = 0;
          while (i < r) {
            if (getValueAt(i, 0) == true) {
              value = getValueAt(i, 1); // table name
            }
            i = i + 1;
          }
        }
        var desData: Array[Array[Any]] = srv.describeTableNumericFieldForSelect(value.asInstanceOf[String]);
        if (desData == null) {
          Dialog.showMessage(contents.head, "Some problem to describe the table", title = "You pressed me")
        } else {
          var col1: Seq[String] = Seq("Select", "Field", "Type", "null", "Key", "Default", "Extra");
          val desTable = new ReadAggregateForm(desData, col1, "Read Specific column Row with Aggregatefunction", value.asInstanceOf[String]);
          desTable.visible_=(true)
        }

      } else if (this.title.equals("Update in All")) {
        var m = new MyModel2(data, col) {
          val r = getRowCount();
          var i: Int = 0;
          while (i < r) {
            if (getValueAt(i, 0) == true) {
              value = getValueAt(i, 1); // table name
            }
            i = i + 1;
          }
        }
        var desData: Array[Array[Any]] = srv.describeTableNumericFieldForSelect(value.asInstanceOf[String]);
        if (desData == null) {
          Dialog.showMessage(contents.head, "Some problem to describe the table", title = "You pressed me")
        } else {
          var col1: Seq[String] = Seq("Select", "Field", "Type", "null", "Key", "Default", "Extra");
          val guf = new GetUpdateForm(desData, col1, "Update in All", value.asInstanceOf[String]);
          guf.visible_=(true)
        }
      } else if (this.title.equals("Update in Specific")) {
        var m = new MyModel2(data, col) {
          val r = getRowCount();
          var i: Int = 0;
          while (i < r) {
            if (getValueAt(i, 0) == true) {
              value = getValueAt(i, 1); // table name
            }
            i = i + 1;
          }
        }
        var desData: Array[Array[Any]] = srv.describeTable(value.asInstanceOf[String]);
        if (desData == null) {
          Dialog.showMessage(contents.head, "Some problem to describe the table", title = "You pressed me")
        } else {
          var col1: Seq[String] = Seq("Field", "Type", "null", "Key", "Default", "Extra");
          val guf = new ShowTable5(desData, col1, "Update in Specific", value.asInstanceOf[String]);
          guf.visible_=(true)
        }

      } else if (this.title.equals("Delete By show")) {
        var m = new MyModel2(data, col) {
          val r = getRowCount();
          var i: Int = 0;
          while (i < r) {
            if (getValueAt(i, 0) == true) {
              value = getValueAt(i, 1); // table name
            }
            i = i + 1;
          }
        }
        var desData: Array[Array[Any]] = srv.describeTable(value.asInstanceOf[String]);
        if (desData == null) {
          Dialog.showMessage(contents.head, "Some problem to describe the table", title = "You pressed me")
        } else {
          var col1: Seq[String] = Seq("Field", "Type", "null", "Key", "Default", "Extra");
          val guf = new ShowTable5(desData, col1, "Delete By show", value.asInstanceOf[String]);
          guf.visible_=(true)
        }

      } else if (this.title.equals("Delete By Condition")) {
        var m = new MyModel2(data, col) {
          val r = getRowCount();
          var i: Int = 0;
          while (i < r) {
            if (getValueAt(i, 0) == true) {
              value = getValueAt(i, 1); // table name
            }
            i = i + 1;
          }
        }
        var desData: Array[Array[Any]] = srv.describeTable(value.asInstanceOf[String]);
        if (desData == null) {
          Dialog.showMessage(contents.head, "Some problem to describe the table", title = "You pressed me")
        } else {
          var keyFieldName: String = null;
          var col1: Seq[String] = Seq("Field", "Type", "null", "Key", "Default", "Extra");
          var m = new MyModel2(desData, col1) {
            val r = getRowCount();
            var j: Int = 0;
            while (j < r) {
              if (getValueAt(j, 3).asInstanceOf[String].contains("PRI")) {
                keyFieldName = getValueAt(j, 0).asInstanceOf[String];
              }
              j = j + 1;
            }
          }
          var sql:String = "Delete from "+value.asInstanceOf[String]+" where ";
          var dbc = new DeleteByCondition(sql,keyFieldName,"Delete by Condition"); 
          dbc.visible_=(true);
        }

      }else if (this.title.equals("Delete By Between")) {
        var m = new MyModel2(data, col) {
          val r = getRowCount();
          var i: Int = 0;
          while (i < r) {
            if (getValueAt(i, 0) == true) {
              value = getValueAt(i, 1); // table name
            }
            i = i + 1;
          }
        }
        var desData: Array[Array[Any]] = srv.describeTable(value.asInstanceOf[String]);
        if (desData == null) {
          Dialog.showMessage(contents.head, "Some problem to describe the table", title = "You pressed me")
        } else {
          var keyFieldName: String = null;
          var col1: Seq[String] = Seq("Field", "Type", "null", "Key", "Default", "Extra");
          var m = new MyModel2(desData, col1) {
            val r = getRowCount();
            var j: Int = 0;
            while (j < r) {
              if (getValueAt(j, 3).asInstanceOf[String].contains("PRI")) {
                keyFieldName = getValueAt(j, 0).asInstanceOf[String];
              }
              j = j + 1;
            }
          }
          var sql:String = "Delete from "+value.asInstanceOf[String]+" where ";
          var dbb = new DeleteByBetween(sql,keyFieldName,"Delete By Between"); 
          dbb.visible_=(true);
        }

      }


      this.dispose();

  }
}

