package gui

import scala.swing._;

import java.awt.Color;
import java.util.Vector;
import scala.swing.event.ButtonClicked
import javax.swing.table.AbstractTableModel
import service.DMLService;

class ReadAggregateForm(  desData: Array[Array[Any]], desCol: Seq[Any], t: String,tableName:String) extends MainFrame {

  val srv = new DMLService();
  var sqlQuery:String ="";
  var colField :String ="";
  var preCol:String = "";

  title = t;
  location = new Point(500, 200)
  preferredSize = new Dimension(400, 400)
  background = Color.BLUE

  
  
  val btnEnter = new Button("Enter") {
    foreground = Color.blue
    borderPainted = true
    enabled = true
    preferredSize = new Dimension(150, 30)

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
  val colOrder: Seq[String] = Seq("select", "Function");
  var dataOrder: Array[Array[Any]] = new Array[Array[Any]](5);
  var d1: Array[Any] = Array(false, "max");
  var d2: Array[Any] = Array(false, "min");
  var d3: Array[Any] = Array(false, "avg");
  var d4: Array[Any] = Array(false, "count");
  var d5: Array[Any] = Array(false, "sum");

  dataOrder(0) = d1;
  dataOrder(1) = d2;
  dataOrder(2) = d3;
  dataOrder(3) = d4;
  dataOrder(4) = d5;

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

    
    contents += new BoxPanel(Orientation.Vertical) {
      contents += spType;
      contents += spOrder
    }
   
    contents += btnEnter
  }

  listenTo(btnEnter)

  def prepareQuery(s: String): String = {

    var sql: String = s;
    sql = sql +" select "

    var valueDes: Any = null;
    var m1 = new MyModel2(desData, desCol) {
      val r = getRowCount();
      var i: Int = 0;
      while (i < r) {
        if (getValueAt(i, 0) == true) {
          valueDes = getValueAt(i, 1)
          colField = getValueAt(i,2).toString()
        }
        i = i + 1;
      }
    }
    
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
    sql = sql + valueOperand.asInstanceOf[String]+ "("+valueDes.asInstanceOf[String]+") from "+tableName;
    preCol = valueOperand.asInstanceOf[String]+"("+valueDes.asInstanceOf[String]+")"
    return sql;
  }

  reactions += {

    case ButtonClicked(component) if component == btnEnter =>
      if (this.title.equals("Read Specific column Row with Aggregatefunction")) {
        sqlQuery = prepareQuery(sqlQuery);
        
        this.dispose();
        val ordData: Array[Array[Any]] = srv.getAggregateColumnValue(sqlQuery,colField);
        if (ordData == null) {
          Dialog.showMessage(contents.head, "Failed to get the data", title = "You pressed me")
        } else {
          val aggCol:Seq[Any]= Seq(preCol); 
          var shTb1 = new ShowTables(ordData, aggCol, "Read Specific column Row with Aggregatefunction");
          shTb1.visible_=(true);
        }
      }
    
  }
}