package gui

import scala.swing._;
import java.awt.Color;
import service.DDLService;
import scala.swing.event.ButtonClicked

class CreateTable(sq: String, t: String) extends MainFrame {

  val srv = new DDLService();

  var sqlQuery: String = sq;

  title = t;
  location = new Point(500, 200)
  preferredSize = new Dimension(400, 400)
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
  val btnAddMore = new Button("Add More Field") {
    foreground = Color.blue
    borderPainted = true
    enabled = true
    preferredSize = new Dimension(200, 30)

  }

  val colType: Seq[String] = Seq("select", "Type");
  var dataType: Array[Array[Any]] = new Array[Array[Any]](3);
  var d1: Array[Any] = Array(false, "int(11)");
  var d2: Array[Any] = Array(false, "varchar(20)");
  var d6: Array[Any] = Array(false, "date");

  dataType(0) = d1;
  dataType(1) = d2;
  dataType(2) = d6;

  val tableType = new Table(dataType, colType) {
    foreground = Color.blue
    enabled = true
    preferredSize = new Dimension(400, 100)
    visible = true

    val mymodel = new MyModel2(dataType, colType) {
      // write here to call the method of model class
    }
    model = mymodel

  }

  val spType = new ScrollPane() {
    contents = tableType
    visible = true
    preferredSize = new Dimension(400, 100)
  }
  val colProp: Seq[String] = Seq("select", "Prop");
  var dataProp: Array[Array[Any]] = new Array[Array[Any]](3);
  var d3: Array[Any] = Array(false, "not null");
  var d4: Array[Any] = Array(false, "primary key");
  var d5: Array[Any] = Array(false, "auto_increment");

  dataProp(0) = d3;
  dataProp(1) = d4;
  dataProp(2) = d5;

  val tableProp = new Table(dataProp, colProp) {
    foreground = Color.blue
    enabled = true
    preferredSize = new Dimension(400, 150)
    visible = true

    val mymodel = new MyModel2(dataProp, colProp) {
      // write here to call the method of model class
    }
    model = mymodel

  }

  val spProp = new ScrollPane() {
    contents = tableProp
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
      contents += spProp
    }
    contents += btnAddMore
    contents += btnEnter
  }

  listenTo(btnAddMore)
  listenTo(btnEnter)

  def prepareQuery(s: String): String = {

    var sql: String = s;
    val na: String = tfFieldName.text

    var valueType: Any = null;
    var m1 = new MyModel2(dataType, colType) {
      val r = getRowCount();
      var i: Int = 0;
      while (i < r) {
        if (getValueAt(i, 0) == true) {
          valueType = getValueAt(i, 1)
        }
        i = i + 1;
      }
    }
    sql = sql + na + " " + valueType.asInstanceOf[String] + " "
    var valueProp: Any = null;
    var m2 = new MyModel2(dataProp, colProp) {
      val r = getRowCount();
      var i: Int = 0;
      while (i < r) {
        if (getValueAt(i, 0) == true) {
          valueProp = getValueAt(i, 1)
          sql = sql + valueProp.asInstanceOf[String] + " "
        }
        i = i + 1;
      }
    }
    return sql;
  }

  reactions += {

    case ButtonClicked(component) if component == btnAddMore =>
      if (this.title.equals("Create New Table")) {
        sqlQuery = prepareQuery(sqlQuery);
        sqlQuery = sqlQuery + " ,"
        this.dispose();
        val fom = new CreateTable(sqlQuery, this.title);
        fom.visible_=(true);
      } else if (this.title.equals("Add new Column")) {
        sqlQuery = prepareQuery(sqlQuery);
        sqlQuery = sqlQuery + " ,"
        this.dispose();
        val fom = new CreateTable(sqlQuery, this.title);
        fom.visible_=(true);
      }
    case ButtonClicked(component) if component == btnEnter =>
      if (this.title.equals("Create New Table")) {
        sqlQuery = prepareQuery(sqlQuery);
        sqlQuery = sqlQuery + " )"
        this.dispose();
        val check = srv.createTable(sqlQuery);
        if (check == true) {
          Dialog.showMessage(contents.head, "Succseesfully Table has beeen cretaed", title = "You pressed me")
        } else {
          Dialog.showMessage(contents.head, "Failed to create table ", title = "You pressed me")
        }
      } else if (this.title.equals("Add new Column")) {
        sqlQuery = prepareQuery(sqlQuery);
        sqlQuery = sqlQuery + " )"
        this.dispose();
        val check = srv.addNewColumn(sqlQuery);
        if (check == true) {
          Dialog.showMessage(contents.head, "Succseesfully new Column Has been added", title = "You pressed me")
        } else {
          Dialog.showMessage(contents.head, "Failed to add new Column ", title = "You pressed me")
        }
      }
  }
}