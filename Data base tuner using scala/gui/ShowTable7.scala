package gui

import scala.swing._;
import java.awt.Color;
import service.DMLService;
import service.DDLService;
import java.util.Vector;
import javax.swing.table._
import utility.MyTableModel
import scala.swing.event.ButtonClicked
import javax.swing.table.AbstractTableModel

class ShowTable7(data: Array[Array[Any]], col: Seq[Any], t: String, tName: String) extends MainFrame {
  val srv = new DMLService();
  val srv1 = new DDLService();

  
  
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

    val mymodel = new MyModel2(data, col) {
      // write here to call the method of model class
    }
    model = mymodel

  }

  val sp = new ScrollPane() {
    contents = table
    visible = true
  }
  
   val colOperand: Seq[String] = Seq("select", "Operand");
  var dataOperand: Array[Array[Any]] = new Array[Array[Any]](7);
  var d1: Array[Any] = Array(false, "=");
  var d2: Array[Any] = Array(false, "!=");
  var d3: Array[Any] = Array(false, ">=");
  var d4: Array[Any] = Array(false, "<=");
  var d5: Array[Any] = Array(false, ">");
  var d6: Array[Any] = Array(false, "<");
  var d7: Array[Any] = Array(false, "like");

  dataOperand(0) = d1;
  dataOperand(1) = d2;
  dataOperand(2) = d3;
  dataOperand(3) = d4;
  dataOperand(4) = d5;
  dataOperand(5) = d6;
  dataOperand(6) = d7;

  val tableOperand = new Table(dataOperand, colOperand) {
    foreground = Color.blue
    enabled = true
    preferredSize = new Dimension(400, 150)
    visible = true

    val mymodel = new MyModel2(dataOperand, colOperand) {
      // write here to call the method of model class
    }
    model = mymodel

  }

  val spProp = new ScrollPane() {
    contents = tableOperand
    visible = true
    preferredSize = new Dimension(400, 150)
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

      if (this.title.equals("Read Specific column Row")) {
        var vecField: Vector[String] = new Vector[String];
        var vecType: Vector[String] = new Vector[String];
        var m = new MyModel2(data, col) {
          val r = getRowCount();
          var i: Int = 0;
          while (i < r) {
            if (getValueAt(i, 0) == true) {
              vecField.add(getValueAt(i, 1).asInstanceOf[String]);
              vecType.add(getValueAt(i, 2).asInstanceOf[String]);
            }
            i = i + 1;
          }
        }
        var speciColData: Array[Array[Any]] = srv.getSpecificColumnRow(vecField, tableName, vecType);
        if (speciColData == null) {
          Dialog.showMessage(contents.head, "Failed to get specific  column data", title = "You pressed me")
        } else {
          var sz: Int = vecField.size();
          var singleCol: Array[Any] = new Array[Any](sz);
          var k: Int = 0;
          while (k < sz) {
            singleCol(k) = vecField.get(k);
            k = k + 1;
          }
          var shTb1 = new ShowTables(speciColData, singleCol.toSeq, "Read Specific column Row");
          shTb1.visible_=(true);
        }
      }else if (this.title.equals("Read Specific column Row with condition")) {
        var vecField: Vector[String] = new Vector[String];
        var vecType: Vector[String] = new Vector[String];
        var m = new MyModel2(data, col) {
          val r = getRowCount();
          var i: Int = 0;
          while (i < r) {
            if (getValueAt(i, 0) == true) {
              vecField.add(getValueAt(i, 1).asInstanceOf[String]);
              vecType.add(getValueAt(i, 2).asInstanceOf[String]);
            }
            i = i + 1;
          }
        }
        var sql :String= "Select ";
        var j:Int =0;
        while(j<vecField.size()){
          sql= sql+ vecField.get(j)+",";
          j=j+1;
          
        }
        sql =sql.substring(0,sql.length()-1);
        sql = sql+" from "+tableName+" where "
        val newDesData:Array[Array[Any]] = srv1.describeTableForSelect(tableName);
        val conForm = new ReadConditionForm(sql,vecType,newDesData,col,"Read Specific column Row with condition",vecField);
        conForm.visible_=(true)
       
      }else if (this.title.equals("Read Specific column Row with Order")) {
        var vecField: Vector[String] = new Vector[String];
        var vecType: Vector[String] = new Vector[String];
        var m = new MyModel2(data, col) {
          val r = getRowCount();
          var i: Int = 0;
          while (i < r) {
            if (getValueAt(i, 0) == true) {
              vecField.add(getValueAt(i, 1).asInstanceOf[String]);
              vecType.add(getValueAt(i, 2).asInstanceOf[String]);
            }
            i = i + 1;
          }
        }
        var sql :String= "Select ";
        var j:Int =0;
        while(j<vecField.size()){
          sql= sql+ vecField.get(j)+",";
          j=j+1;
          
        }
        sql =sql.substring(0,sql.length()-1);
        sql = sql+" from "+tableName+" order by "
        val newDesData:Array[Array[Any]] = srv1.describeTableForSelect(tableName);
        val conForm = new ReadOrderForm(sql,vecType,newDesData,col,"Read Specific column Row with Order",vecField);
        conForm.visible_=(true)
       
      }else if (this.title.equals("Read Specific column Row with Max and Min")) {
        var vecField: Vector[String] = new Vector[String];
        var vecType: Vector[String] = new Vector[String];
        var m = new MyModel2(data, col) {
          val r = getRowCount();
          var i: Int = 0;
          while (i < r) {
            if (getValueAt(i, 0) == true) {
              vecField.add(getValueAt(i, 1).asInstanceOf[String]);
              vecType.add(getValueAt(i, 2).asInstanceOf[String]);
            }
            i = i + 1;
          }
        }
        var sql :String= "Select ";
        var j:Int =0;
        while(j<vecField.size()){
          sql= sql+ vecField.get(j)+",";
          j=j+1;
          
        }
        sql =sql.substring(0,sql.length()-1);
        sql = sql+" from "+tableName+" where "
        val newDesData:Array[Array[Any]] = srv1.describeTableNumericFieldForSelect(tableName);
        val conForm = new ReadMaxMinForm(sql,vecType,newDesData,col,"Read Specific column Row with Max and Min",vecField,tableName);
        conForm.visible_=(true)
       
      }else if (this.title.equals("Read Specific column Row with In Notin Rows")) {
        var vecField: Vector[String] = new Vector[String];
        var vecType: Vector[String] = new Vector[String];
        var m = new MyModel2(data, col) {
          val r = getRowCount();
          var i: Int = 0;
          while (i < r) {
            if (getValueAt(i, 0) == true) {
              vecField.add(getValueAt(i, 1).asInstanceOf[String]);
              vecType.add(getValueAt(i, 2).asInstanceOf[String]);
            }
            i = i + 1;
          }
        }
        var sql :String= "Select ";
        var j:Int =0;
        while(j<vecField.size()){
          sql= sql+ vecField.get(j)+",";
          j=j+1;
          
        }
        sql =sql.substring(0,sql.length()-1);
        sql = sql+" from "+tableName+" where "
        val newDesData:Array[Array[Any]] = srv1.describeTableForSelect(tableName);
        val conForm = new ReadInNotinForm(sql,vecType,newDesData,col,"Read Specific column Row with In Notin Rows",vecField);
        conForm.visible_=(true)
       
      }
      
      this.dispose();
  }

}
  
 