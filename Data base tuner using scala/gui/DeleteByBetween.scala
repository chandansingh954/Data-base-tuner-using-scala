package gui

import scala.swing._;

import java.awt.Color;
import java.util.Vector;
import scala.swing.event.ButtonClicked
import javax.swing.table.AbstractTableModel
import service.DMLService;

class DeleteByBetween(sql: String, keyFieldName: String, t: String) extends MainFrame {

  var sqlQuery = sql;
  val srv = new DMLService();

  title = t;
  location = new Point(500, 200)
  preferredSize = new Dimension(400, 400)
  background = Color.BLUE

  val lbFieldName1 = new Label("From "+keyFieldName) {
    foreground = Color.blue
    preferredSize = new Dimension(200, 30)
    visible_=(true)
  }
  val tfFieldName1 = new TextField() {
    foreground = Color.blue
    enabled = true
    preferredSize = new Dimension(150, 30)
    visible_=(true)
  }
  val lbFieldName2 = new Label("To "+keyFieldName) {
    foreground = Color.blue
    preferredSize = new Dimension(200, 30)
    visible_=(true)
  }
  val tfFieldName2 = new TextField() {
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

    contents += lbFieldName1
    contents += tfFieldName1
    contents += lbFieldName2
    contents += tfFieldName2
    contents += Swing.VStrut(10)
    contents += Swing.Glue
    contents += new BoxPanel(Orientation.Vertical) {
      contents += spLogical;

    }
    contents += btnAddValue
    contents += btnEnter
  }

  listenTo(btnAddValue)
  listenTo(btnEnter)

  def prepareQuery(s: String): String = {

    var sql: String = s;

    sql = sql + keyFieldName + " Between ";
    
    
    sql = sql+"'"+tfFieldName1.text+"' and  '"+tfFieldName2.text+"' "
    
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
      if (this.title.equals("Delete By Between")) {
        sqlQuery = prepareQuery(sqlQuery);
        if(spLogical.visible){
          var dbc = new DeleteByBetween(sqlQuery,keyFieldName,"Delete By Between"); 
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
      if (this.title.equals("Delete By Between")) {
        spLogical.visible_=(true)

      }
  }
}