package gui

import scala.swing._;
import java.awt.Color;
import service.DDLService;
import scala.swing.event.ButtonClicked

class DDLFrame extends MainFrame {

  val srv = new DDLService();

  title = "DDL Frame"
  location = new Point(200, 200)
  preferredSize = new Dimension(600, 500)
  background = Color.BLUE

  val btnShowTables = new Button("Show tables") {
    foreground = Color.blue
    borderPainted = true
    enabled = true
    preferredSize = new Dimension(150, 30)

  }
  val btnCreateDatabase = new Button("Create Database") {
    foreground = Color.blue
    borderPainted = true
    enabled = true
    preferredSize = new Dimension(150, 30)

  }
  val btnShowDatabase = new Button("Show Database") {
    foreground = Color.blue
    borderPainted = true
    enabled = true
    preferredSize = new Dimension(150, 30)

  }
  val btnSelectDatabase = new Button("Select Database") {
    foreground = Color.blue
    borderPainted = true
    enabled = true
    preferredSize = new Dimension(150, 30)

  }
  val btnUseDatabase = new Button("Use Database") {
    foreground = Color.blue
    borderPainted = true
    enabled = true
    preferredSize = new Dimension(150, 30)

  }
  val btnDescribeTable = new Button("Describe table") {
    foreground = Color.blue
    borderPainted = true
    enabled = true
    preferredSize = new Dimension(150, 30)

  }

  val btnCreateTable = new Button("Create table") {
    foreground = Color.blue
    borderPainted = true
    enabled = true
    preferredSize = new Dimension(150, 30)

  }
  val btnCreateTableFromExistingtable = new Button("Create table From Existing table") {
    foreground = Color.blue
    borderPainted = true
    enabled = true
    preferredSize = new Dimension(200, 30)

  }
  val btnRenameTable = new Button("Rename table") {
    foreground = Color.blue
    borderPainted = true
    enabled = true
    preferredSize = new Dimension(200, 30)

  }
  val btnDropTable = new Button("Drop table") {
    foreground = Color.blue
    borderPainted = true
    enabled = true
    preferredSize = new Dimension(200, 30)

  }
  val btnAlterNewColumn = new Button("Add new Column") {
    foreground = Color.blue
    borderPainted = true
    enabled = true
    preferredSize = new Dimension(200, 30)

  }
  val btnDropColumn = new Button("Drop Column") {
    foreground = Color.blue
    borderPainted = true
    enabled = true
    preferredSize = new Dimension(200, 30)

  }
  val btnModifyColumn = new Button("Modify Column") {
    foreground = Color.blue
    borderPainted = true
    enabled = true
    preferredSize = new Dimension(200, 30)

  }
  val btnBack = new Button("Back") {
    foreground = Color.blue
    borderPainted = true
    enabled = true
    preferredSize = new Dimension(200, 30)

  }

  contents = new FlowPanel() {
    vGap = 40
    contents += btnShowTables
    contents += btnCreateDatabase
    contents += btnShowDatabase
    contents += btnSelectDatabase
    contents += btnUseDatabase
    contents += btnDescribeTable
    contents += btnCreateTable
    contents += btnCreateTableFromExistingtable
    contents += btnRenameTable
    contents += btnDropTable
    contents += btnAlterNewColumn
    contents += btnDropColumn
    contents += btnModifyColumn
    contents += btnBack
  }
  listenTo(btnShowTables)
  listenTo(btnCreateDatabase)
  listenTo(btnShowDatabase)
  listenTo(btnSelectDatabase)
  listenTo(btnUseDatabase)
  listenTo(btnDescribeTable)
  listenTo(btnCreateTable)
  listenTo(btnCreateTableFromExistingtable)
  listenTo(btnRenameTable)
  listenTo(btnDropTable)
  listenTo(btnAlterNewColumn)
  listenTo(btnDropColumn)
  listenTo(btnModifyColumn)
  listenTo(btnBack)
  
  def getDatabaseShowSelection(tit: String) {
    var data: Array[Array[Any]] = srv.getDatabase();
    var col: Seq[String] = Seq("Database name");
    var t = new ShowTables(data, col, tit);
    t.visible_=(true)
  }
  def getTableShowSelection(tit: String) {
    var data: Array[Array[Any]] = srv.getTables();
    var col: Seq[String] = Seq("Table name");
    var t = new ShowTables(data, col, tit);
    t.visible_=(true)
  }

  reactions += {
    case ButtonClicked(component) if component == btnShowTables =>
      var tit = "Show Tables"
      getTableShowSelection(tit);
    case ButtonClicked(component) if component == btnCreateDatabase =>
      var dn = new GetDatabaseName("createDatabase");
      dn.visible_=(true)
    case ButtonClicked(component) if component == btnShowDatabase =>
      var tit = "Show Database"
      getDatabaseShowSelection(tit);
    case ButtonClicked(component) if component == btnSelectDatabase =>
      var msg: String = srv.selectDatabase();
      Dialog.showMessage(contents.head, "Selected database is :  " + msg, title = "You pressed me")
    case ButtonClicked(component) if component == btnUseDatabase =>
      var data: Array[Array[Any]] = srv.getDatabaseForSelect();
      var col: Seq[String] = Seq("Selct", "Database name");
      var t = new ShowTables2(data, col, "Select Database");
      t.visible_=(true)
    case ButtonClicked(component) if component == btnDescribeTable =>
      var data: Array[Array[Any]] = srv.getTablesForSelect();
      var col: Seq[String] = Seq("Select", "Table name");
      var t = new ShowTables2(data, col, "Select Table");
      t.visible_=(true)
    case ButtonClicked(component) if component == btnCreateTable =>
      var v = new GetName("New Table Name");
      v.visible = true;
    case ButtonClicked(component) if component == btnCreateTableFromExistingtable =>
      var ms: String = "Table New Name";
      var data: Array[Array[Any]] = srv.getTablesForSelect();
      var col: Seq[String] = Seq("Select", "Table name");
      var t = new ShowTables3(data, col, ms);
      t.visible_=(true);
    case ButtonClicked(component) if component == btnRenameTable =>
      var ms: String = "Rename Table";
      var data: Array[Array[Any]] = srv.getTablesForSelect();
      var col: Seq[String] = Seq("Select", "Table name");
      var t = new ShowTables3(data, col, ms);
      t.visible_=(true);  
    case ButtonClicked(component) if component == btnDropTable =>
      var data: Array[Array[Any]] = srv.getTablesForSelect();
      var col: Seq[String] = Seq("Select", "Table name");
      var t = new ShowTables2(data, col, "Drop Table");
      t.visible_=(true) 
    case ButtonClicked(component) if component == btnAlterNewColumn =>
      var data: Array[Array[Any]] = srv.getTablesForSelect();
      var col: Seq[String] = Seq("Select", "Table name");
      var t = new ShowTables2(data, col, "Add new Column");
      t.visible_=(true)
    case ButtonClicked(component) if component == btnDropColumn =>
      var data: Array[Array[Any]] = srv.getTablesForSelect();
      var col: Seq[String] = Seq("Select", "Table name");
      var t = new ShowTables2(data, col, "Drop Column");
      t.visible_=(true)
    case ButtonClicked(component) if component == btnModifyColumn =>
      var data: Array[Array[Any]] = srv.getTablesForSelect();
      var col: Seq[String] = Seq("Select", "Table name");
      var t = new ShowTables2(data, col, "Modify Column");
      t.visible_=(true)  
    case ButtonClicked(component) if component == btnBack =>
      this.dispose();
      val ff  = new FirstForm();
      ff.visible_=(true)
  }

}