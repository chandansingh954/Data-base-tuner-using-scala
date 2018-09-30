package gui

import scala.swing._;
import java.awt.Color;
import service.DDLService;
import scala.swing.event.ButtonClicked

class GetPosition(data: Array[Array[Any]], col: Seq[Any],ms: String) extends MainFrame {

  val  srv = new DDLService();

  title = ms;
  location = new Point(500, 200)
  preferredSize = new Dimension(400, 150)
  background = Color.BLUE

  val lbPos = new Label(" Position") {
    foreground = Color.blue
    preferredSize = new Dimension(150, 30)

  }
  val tfPos = new TextField() {
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

  contents = new FlowPanel() {
    vGap = 40
    contents += lbPos
    contents += tfPos
    contents += new FlowPanel() {
      contents += btnEnter
    }
  }
  listenTo(btnEnter)

 
  reactions += {
    case ButtonClicked(component) if component == btnEnter =>
      if (ms.equals("Get Database name")) {
        val stPos = tfPos.text;
        val pos = stPos.toInt;
        var value:Any =null;
        this.dispose();
        // now we have to call the method in ShowTable class in which we use the model
        val v = new MyModel1(data,col){
           value = getValueAt(pos - 1, 1);
        }
        var check:Boolean = srv.useDatabase(value.asInstanceOf[String]);
        if (check == true) {
          Dialog.showMessage(contents.head, "Succseesfully database has beeen selected", title = "You pressed me")
        } else {
          Dialog.showMessage(contents.head, "Failed to use database ", title = "You pressed me")
        }
        
      }else if (ms.equals("Get Table Name")) {
        
        val stPos = tfPos.text;
        val pos = stPos.toInt;
        var value:Any =null;
        this.dispose();
        // now we have to call the method in ShowTable class in which we use the model
        
        val v = new MyModel1(data,col){
           value = getValueAt(pos - 1, 1);
        }
        var desData:Array[Array[Any]] = srv.describeTable(value.asInstanceOf[String]);
        if (desData == null) {
          Dialog.showMessage(contents.head, "Some froblem occured to describe the table", title = "You pressed me")
        } else {
          var col: Seq[String] = Seq("Field","Type","null","Key","Default","Extra");
          val desTable = new ShowTables(desData,col,"Describe Table");
          desTable.visible_=(true)
        }
      }
  }
}