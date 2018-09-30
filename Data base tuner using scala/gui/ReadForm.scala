package gui

import scala.swing._;
import java.awt.Color;
import service.DDLService;
import scala.swing.event.ButtonClicked

class ReadForm extends MainFrame {

  val srv = new DDLService();

  title = "Read Form"
  location = new Point(200, 200)
  preferredSize = new Dimension(650, 450)
  background = Color.BLUE

  val btnReadAllData = new Button("Read All Data") {
    foreground = Color.blue
    borderPainted = true
    enabled = true
    preferredSize = new Dimension(300, 30)

  }
  val btnReadSingleRow = new Button("Read Single Row") {
    foreground = Color.blue
    borderPainted = true
    enabled = true
    preferredSize = new Dimension(300, 30)

  }
  val btnReadSpecificColumn = new Button("Read Specific column Row") {
    foreground = Color.blue
    borderPainted = true
    enabled = true
    preferredSize = new Dimension(300, 30)

  }
  val btnReadSpecificColumnCondition = new Button("Read Specific column Row with condition") {
    foreground = Color.blue
    borderPainted = true
    enabled = true
    preferredSize = new Dimension(300, 30)

  }
  val btnReadSpecificColumnByOrder = new Button("Read Specific column Row with Order") {
    foreground = Color.blue
    borderPainted = true
    enabled = true
    preferredSize = new Dimension(300, 30)

  }
  val btnReadSpecificColumnByMAxMin = new Button("Read Specific column Row with Max and min") {
    foreground = Color.blue
    borderPainted = true
    enabled = true
    preferredSize = new Dimension(300, 30)

  }
  val btnReadSpecificColumnByInNotIn = new Button("Read Specific column Row with In NotIn rows") {
    foreground = Color.blue
    borderPainted = true
    enabled = true
    preferredSize = new Dimension(300, 30)

  }
  val btnReadSpecificColumnByAggregate = new Button("Read Specific column Row with Aggregate fun") {
    foreground = Color.blue
    borderPainted = true
    enabled = true
    preferredSize = new Dimension(300, 30)

  }
  val btnBack = new Button("Back") {
    foreground = Color.blue
    borderPainted = true
    enabled = true
    preferredSize = new Dimension(300, 30)

  }

  contents = new FlowPanel() {
    vGap = 40
    contents += btnReadAllData
    contents += btnReadSingleRow
    contents += btnReadSpecificColumn
    contents += btnReadSpecificColumnCondition
    contents += btnReadSpecificColumnByOrder
    contents += btnReadSpecificColumnByMAxMin
    contents += btnReadSpecificColumnByInNotIn
    contents += btnReadSpecificColumnByAggregate
    contents += btnBack

  }

  listenTo(btnReadAllData)
  listenTo(btnReadSingleRow)
  listenTo(btnReadSpecificColumn)
  listenTo(btnReadSpecificColumnCondition)
  listenTo(btnReadSpecificColumnByOrder)
  listenTo(btnReadSpecificColumnByMAxMin)
  listenTo(btnReadSpecificColumnByInNotIn)
  listenTo(btnReadSpecificColumnByAggregate)
  listenTo(btnBack)

  reactions += {
    case ButtonClicked(component) if component == btnReadAllData =>
      var data: Array[Array[Any]] = srv.getTablesForSelect();
      var col: Seq[String] = Seq("Select", "Table name");
      var t = new ShowTables2(data, col, "Read All Data");
      t.visible_=(true)
    case ButtonClicked(component) if component == btnReadSingleRow =>
      var data: Array[Array[Any]] = srv.getTablesForSelect();
      var col: Seq[String] = Seq("Select", "Table name");
      var t = new ShowTables2(data, col, "Read Single Row");
      t.visible_=(true)
    case ButtonClicked(component) if component == btnReadSpecificColumn =>
      var data: Array[Array[Any]] = srv.getTablesForSelect();
      var col: Seq[String] = Seq("Select", "Table name");
      var t = new ShowTables2(data, col, "Read Specific column Row");
      t.visible_=(true)
    case ButtonClicked(component) if component == btnReadSpecificColumnCondition =>
      var data: Array[Array[Any]] = srv.getTablesForSelect();
      var col: Seq[String] = Seq("Select", "Table name");
      var t = new ShowTables2(data, col, "Read Specific column Row with condition");
      t.visible_=(true)
    case ButtonClicked(component) if component == btnReadSpecificColumnByOrder =>
      var data: Array[Array[Any]] = srv.getTablesForSelect();
      var col: Seq[String] = Seq("Select", "Table name");
      var t = new ShowTables2(data, col, "Read Specific column Row with Order");
      t.visible_=(true)
    case ButtonClicked(component) if component == btnReadSpecificColumnByMAxMin =>
      var data: Array[Array[Any]] = srv.getTablesForSelect();
      var col: Seq[String] = Seq("Select", "Table name");
      var t = new ShowTables2(data, col, "Read Specific column Row with Max and Min");
      t.visible_=(true)
    case ButtonClicked(component) if component == btnReadSpecificColumnByInNotIn =>
      var data: Array[Array[Any]] = srv.getTablesForSelect();
      var col: Seq[String] = Seq("Select", "Table name");
      var t = new ShowTables2(data, col, "Read Specific column Row with In Notin Rows");
      t.visible_=(true) 
    case ButtonClicked(component) if component == btnReadSpecificColumnByAggregate =>
      var data: Array[Array[Any]] = srv.getTablesForSelect();
      var col: Seq[String] = Seq("Select", "Table name");
      var t = new ShowTables2(data, col, "Read Specific column Row with Aggregatefunction");
      t.visible_=(true)   
    case ButtonClicked(component) if component == btnBack =>
      this.dispose();
      val df = new DMLFrame();
      df.visible_=(true);
  }

}