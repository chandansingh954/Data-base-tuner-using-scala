package gui

import scala.swing._;

import java.awt.Color;
import java.util.Vector;
import scala.swing.event.ButtonClicked
import javax.swing.table.AbstractTableModel
import service.DMLService;

class ReadOrderForm(sq: String, vecType: Vector[String], desData: Array[Array[Any]], desCol: Seq[Any], t: String, vecField: Vector[String]) extends MainFrame {

  val srv = new DMLService();

  var sqlQuery: String = sq;

  title = t;
  location = new Point(500, 200)
  preferredSize = new Dimension(400, 400)
  background = Color.BLUE

  val lbFieldName = new Label(" Field Name") {
    foreground = Color.blue
    preferredSize = new Dimension(150, 30)
    visible_=(false)
  }
  val tfFieldName = new TextField() {
    foreground = Color.blue
    enabled = true
    preferredSize = new Dimension(150, 30)
    visible_=(false)
  }
  val btnEnter = new Button("Enter") {
    foreground = Color.blue
    borderPainted = true
    enabled = true
    preferredSize = new Dimension(150, 30)

  }
  val btnAddLimit = new Button("Add Limit") {
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
  val colOrder: Seq[String] = Seq("select", "Order Type");
  var dataOrder: Array[Array[Any]] = new Array[Array[Any]](2);
  var d1: Array[Any] = Array(false, "asc");
  var d2: Array[Any] = Array(false, "desc");

  dataOrder(0) = d1;
  dataOrder(1) = d2;

  val tableOrder = new Table(dataOrder, colOrder) {
    foreground = Color.blue
    enabled = true
    preferredSize = new Dimension(400, 150)
    visible = true

    val mymodel = new MyModel2(dataOrder, colOrder) {
      // write here to call the method of model class
    }
    model = mymodel

  }

  val spOrder = new ScrollPane() {
    contents = tableOrder
    visible = true
    preferredSize = new Dimension(400, 150)
  }

  contents = new FlowPanel() {

    contents += lbFieldName
    contents += tfFieldName
    contents += Swing.VStrut(10)
    contents += Swing.Glue
    contents += new BoxPanel(Orientation.Vertical) {
      contents += spType;
      contents += spOrder
    }
    contents += btnAddLimit
    contents += btnEnter
  }

  listenTo(btnAddLimit)
  listenTo(btnEnter)

  def prepareQuery(s: String): String = {

    var sql: String = s;

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
    var m2 = new MyModel2(dataOrder, colOrder) {
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

    return sql;
  }

  reactions += {

    case ButtonClicked(component) if component == btnEnter =>
      if (this.title.equals("Read Specific column Row with Order")) {
        sqlQuery = prepareQuery(sqlQuery);
        if (lbFieldName.visible) {
          val valu = tfFieldName.text
          sqlQuery = sqlQuery + " limit " + valu.toInt
        }
        this.dispose();
        val ordData: Array[Array[Any]] = srv.getSpecificColumnWithCondtition(sqlQuery, vecType, vecField);
        if (ordData == null) {
          Dialog.showMessage(contents.head, "Failed to get the data", title = "You pressed me")
        } else {
          var sz: Int = vecField.size();
          var singleCol: Array[Any] = new Array[Any](sz);
          var k: Int = 0;
          while (k < sz) {
            singleCol(k) = vecField.get(k);
            k = k + 1;
          }
          var shTb1 = new ShowTables(ordData, singleCol.toSeq, "Read Specific column Row with Order");
          shTb1.visible_=(true);
        }
      }
    case ButtonClicked(component) if component == btnAddLimit =>
      if (this.title.equals("Read Specific column Row with Order")) {
        
        lbFieldName.visible_=(true)
        tfFieldName.visible_=(true)
        
      }
  }
}