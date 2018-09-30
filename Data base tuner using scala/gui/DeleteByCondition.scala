package gui

import scala.swing._;

import java.awt.Color;
import java.util.Vector;
import scala.swing.event.ButtonClicked
import javax.swing.table.AbstractTableModel
import service.DMLService;

class DeleteByCondition(sql: String, keyFieldName: String, t: String) extends MainFrame {

  var sqlQuery = sql;
  val srv = new DMLService();

  title = t;
  location = new Point(500, 200)
  preferredSize = new Dimension(400, 500)
  background = Color.BLUE

  val lbFieldName = new Label(keyFieldName) {
    foreground = Color.blue
    preferredSize = new Dimension(150, 30)
    visible_=(true)
  }
  val tfFieldName = new TextField() {
    foreground = Color.blue
    enabled = true
    preferredSize = new Dimension(150, 30)
    visible_=(true)
  }
  val btnEnter = new Button("Enter") {
    foreground = Color.blue
    borderPainted = true
    enabled = true
    preferredSize = new Dimension(150, 30)

  }
  val btnAddValue = new Button("Add Value") {
    foreground = Color.blue
    borderPainted = true
    enabled = true
    preferredSize = new Dimension(200, 30)

  }
  val colOperand: Seq[String] = Seq("select", "Operand");
  var dataOperand: Array[Array[Any]] = new Array[Array[Any]](6);
  var d1: Array[Any] = Array(false, "=");
  var d2: Array[Any] = Array(false, "!=");
  var d3: Array[Any] = Array(false, ">=");
  var d4: Array[Any] = Array(false, "<=");
  var d5: Array[Any] = Array(false, ">");
  var d6: Array[Any] = Array(false, "<");

  dataOperand(0) = d1;
  dataOperand(1) = d2;
  dataOperand(2) = d3;
  dataOperand(3) = d4;
  dataOperand(4) = d5;
  dataOperand(5) = d6;

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

  val colLogical: Seq[String] = Seq("select", "Logical");
  var dataLogical: Array[Array[Any]] = new Array[Array[Any]](2);
  var d8: Array[Any] = Array(false, "AND");
  var d9: Array[Any] = Array(false, "OR");

  dataLogical(0) = d8;
  dataLogical(1) = d9;

  val tableLogical = new Table(dataLogical, colLogical) {
    foreground = Color.blue
    enabled = true
    preferredSize = new Dimension(400, 150)
    visible = true

    val mymodel = new MyModel2(dataLogical, colLogical) {
      // write here to call the method of model class
    }
    model = mymodel

  }

  val spLogical = new ScrollPane() {
    contents = tableLogical
    visible = false
    preferredSize = new Dimension(400, 150)
  }

  contents = new FlowPanel() {

    contents += lbFieldName
    contents += tfFieldName
    contents += Swing.VStrut(10)
    contents += Swing.Glue
    contents += new BoxPanel(Orientation.Vertical) {
      contents += spProp;
      contents += spLogical;

    }
    contents += btnAddValue
    contents += btnEnter
  }

  listenTo(btnAddValue)
  listenTo(btnEnter)

  def prepareQuery(s: String): String = {

    var sql: String = s;

    sql = sql + keyFieldName + " ";
    var valueOperand: Any = null;
    var m2 = new MyModel2(dataOperand, colOperand) {
      val r = getRowCount();
      var i: Int = 0;
      while (i < r) {
        if (getValueAt(i, 0) == true) {
          valueOperand = getValueAt(i, 1)
        }
        i = i + 1;
      }
    }
    sql = sql + valueOperand.asInstanceOf[String]+" "
    
    sql = sql+"'"+tfFieldName.text+"' "
    
    if (spLogical.visible) {
      var valueLogical: Any = null;
      var m2 = new MyModel2(dataLogical, colLogical) {
        val r = getRowCount();
        var j: Int = 0;
        while ( j < r) {
          if (getValueAt(j, 0) == true) {
            valueLogical = getValueAt(j, 1)
          }
          j = j + 1;
        }
      }
      sql = sql + valueLogical.asInstanceOf[String]+" "
    }

    return sql;
  }

  reactions += {

    case ButtonClicked(component) if component == btnEnter =>
      if (this.title.equals("Delete by Condition")) {
        sqlQuery = prepareQuery(sqlQuery);
        if(spLogical.visible){
          var dbc = new DeleteByCondition(sqlQuery,keyFieldName,"Delete by Condition"); 
          dbc.visible_=(true);
        }else{
          var check :Boolean = srv.delete(sqlQuery);
          if(check == true){
            Dialog.showMessage(contents.head, "successfullly delete", title = "You pressed me")
          }else{
            Dialog.showMessage(contents.head, "Failed in delete", title = "You pressed me")
          }
        }
        this.dispose();
      }
    case ButtonClicked(component) if component == btnAddValue =>
      if (this.title.equals("Delete by Condition")) {
        spLogical.visible_=(true)

      }
  }
}