package gui

import scala.swing._;

import java.awt.Color;
import java.util.Vector;
import scala.swing.event.ButtonClicked
import javax.swing.table.AbstractTableModel
import service.DMLService;

class ReadConditionForm(sq: String, vecType: Vector[String], desData: Array[Array[Any]], desCol: Seq[Any], t: String, vecField: Vector[String]) extends MainFrame {

  val srv = new DMLService();

  var sqlQuery: String = sq;

  title = t;
  location = new Point(500, 200)
  preferredSize = new Dimension(420, 550)
  background = Color.BLUE

  val lbFieldName = new Label(" Field Name") {
    foreground = Color.blue
    preferredSize = new Dimension(150, 30)

  }
  val tfFieldName = new TextField() {
    foreground = Color.blue
    enabled = true
    preferredSize = new Dimension(150, 30)

  }
  val btnEnter = new Button("Enter") {
    foreground = Color.blue
    borderPainted = true
    enabled = true
    preferredSize = new Dimension(150, 30)

  }
  val btnAddMoreCondition = new Button("Add More Condition") {
    foreground = Color.blue
    borderPainted = true
    enabled = true
    preferredSize = new Dimension(200, 30)

  }

  val tableDes = new Table(desData, desCol) {
    foreground = Color.blue
    enabled = true
    preferredSize = new Dimension(400, 100)
    visible = true

    val mymodel = new MyModel2(desData, desCol) {
      // write here to call the method of model class
    }
    model = mymodel

  }

  val spType = new ScrollPane() {
    contents = tableDes
    visible = true
    preferredSize = new Dimension(400, 100)
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
      contents += spType;
      contents += spProp
      contents += spLogical
    }
    contents += btnAddMoreCondition
    contents += btnEnter
  }

  listenTo(btnAddMoreCondition)
  listenTo(btnEnter)

  def prepareQuery(s: String): String = {

    var sql: String = s;
    val na: String = tfFieldName.text

    var valueDes: Any = null;
    var m1 = new MyModel2(desData, desCol) {
      val r = getRowCount();
      var i: Int = 0;
      while (i < r) {
        if (getValueAt(i, 0) == true) {
          valueDes = getValueAt(i, 1)
        }
        i = i + 1;
      }
    }
    sql = sql + valueDes.asInstanceOf[String] + " "
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
    sql = sql + valueOperand.asInstanceOf[String] + " "
    if (valueOperand.asInstanceOf[String] == "like") {
      sql = sql + "'%" + na + "%'";
    } else {
      sql = sql + "'" + na + "'";
    }
    if (spLogical.visible) {
      var valueLogical: Any = null;
      var m3 = new MyModel2(dataLogical, colLogical) {
        val r = getRowCount();
        var i: Int = 0;
        while (i < r) {
          if (getValueAt(i, 0) == true) {
            valueLogical = getValueAt(i, 1)
          }
          i = i + 1;
        }
      }
      sql = sql + valueLogical.asInstanceOf[String] + " "
    }
    return sql;
  }

  reactions += {

    case ButtonClicked(component) if component == btnAddMoreCondition =>
      if (this.title.equals("Read Specific column Row with condition")) {
        spLogical.visible_=(true);

      }
    case ButtonClicked(component) if component == btnEnter =>
      if (this.title.equals("Read Specific column Row with condition")) {
        if (spLogical.visible) {
          sqlQuery = prepareQuery(sqlQuery);
          this.dispose();
          val fom = new ReadConditionForm(sqlQuery, vecType, desData, desCol, this.title, vecField);
          fom.visible_=(true);
        } else {
          sqlQuery = prepareQuery(sqlQuery);
          this.dispose();
          val codData: Array[Array[Any]] = srv.getSpecificColumnWithCondtition(sqlQuery, vecType, vecField);
          if (codData == null) {
            Dialog.showMessage(contents.head, "Failed to get the data", title = "You pressed me")
          } else {
            var sz: Int = vecField.size();
            var singleCol: Array[Any] = new Array[Any](sz);
            var k: Int = 0;
            while (k < sz) {
              singleCol(k) = vecField.get(k);
              k = k + 1;
            }
            var shTb1 = new ShowTables(codData, singleCol.toSeq, "Read Specific column Row with condition");
            shTb1.visible_=(true);
          }
        }
      }
  }
}