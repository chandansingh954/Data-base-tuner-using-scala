package gui

import scala.swing._;
import java.awt.Color;
import service.DMLService;
import scala.swing.event.ButtonClicked

class GetUpdateForm(desData:Array[Array[Any]],desCol:Seq[Any],t:String,tableName:String) extends MainFrame {
  title = t;
  val srv = new DMLService();
  
  location = new Point(500, 200)
  preferredSize = new Dimension(450, 300)
  background = Color.BLUE

  val lbName = new Label(" Data") {
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

  contents = new FlowPanel() {
    vGap = 40
    contents += lbName
    contents += tfName
    contents += spType
    contents += new FlowPanel() {
      contents += btnEnter
    }
  }
  listenTo(btnEnter)

  reactions += {
    case ButtonClicked(component) if component == btnEnter =>
      if (this.title.equals("Update in All")) {
        val fieldValue: String = tfName.text;
        var value :String = null;
        var m = new MyModel2(desData, desCol) {
          val r = getRowCount();
          var i: Int = 0;
          while (i < r) {
            if (getValueAt(i, 0) == true) {
              value = getValueAt(i, 1).toString(); 
            }
            i = i + 1;
          }
        }
        val check:Boolean = srv.updateInAll(tableName,value,fieldValue);
        if(check == true){
          Dialog.showMessage(contents.head, "Successfully update ", title = "You pressed me")
        }else{
          Dialog.showMessage(contents.head, "Failed to update in all ", title = "You pressed me")
        }
      } 
  }

}  