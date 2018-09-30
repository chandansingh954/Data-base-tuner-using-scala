package gui

import scala.swing._;
import java.awt.Color;
import service.DDLService;
import scala.swing.event.ButtonClicked

class FieldNameType(var tableName: String, var oldColumnName: String, t: String) extends MainFrame {
  var srv = new DDLService();
  
  title = t;
  location = new Point(500, 200)
  preferredSize = new Dimension(400, 400)
  background = Color.BLUE

  val lbName = new Label("New Field Name") {
    foreground = Color.blue
    preferredSize = new Dimension(150, 30)

  }
  val tfName = new TextField() {
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

  contents = new FlowPanel() {
    vGap = 40
    contents += lbName
    contents += tfName
    contents += new BoxPanel(Orientation.Vertical) {
      contents += tableType
      contents += Swing.VStrut(10)
      contents += Swing.Glue
    }
    contents += btnEnter
  }
  listenTo(btnEnter)

  reactions += {
    case ButtonClicked(component) if component == btnEnter =>
      var value:Any = null;
      if (this.title.equals("Modify Column")) {
        val newColumnName: String = tfName.text;
        var m = new MyModel2(dataType, colType) {
          val r = getRowCount();
          var i: Int = 0;
          while (i < r) {
            if (getValueAt(i, 0) == true) {
              value = getValueAt(i, 1);
            }
            i = i + 1;
          }
        }
        var check: Boolean = srv.modifyColumn(tableName,oldColumnName,newColumnName,value.asInstanceOf[String]);
        if (check == true) {
          Dialog.showMessage(contents.head, "Succseesfully column has been modify", title = "You pressed me")
        } else {
          Dialog.showMessage(contents.head, "Failed to modify the column ", title = "You pressed me")
        }
      }
      this.dispose();
  }

}  