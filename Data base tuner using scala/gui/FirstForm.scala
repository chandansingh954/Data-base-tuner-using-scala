package gui

import scala.swing._;
import java.awt.{ Color, Graphics2D }
import scala.swing.event.ButtonClicked

class FirstForm extends MainFrame {
  title = "MAin Frame"
  location = new Point(400, 200)
  preferredSize = new Dimension(320, 300)
  background = Color.BLUE

  val btnDDL = new Button("DDL") {
    foreground = Color.blue
    borderPainted = true
    enabled = true
    preferredSize = new Dimension(250, 50)

  }
  val btnDML = new Button("DML") {
    foreground = Color.blue
    borderPainted = true
    enabled = true
    preferredSize = new Dimension(250, 50)
  }
  val btnExit = new Button("EXIT") {
    foreground = Color.blue
    borderPainted = true
    enabled = true
    preferredSize = new Dimension(250, 50)

  }
  contents = new FlowPanel() {
    vGap = 40
    contents += btnDDL
    contents += btnDML
    contents += btnExit

  }

  listenTo(btnDDL)
  listenTo(btnDML)
  listenTo(btnExit)

  reactions += {
    case ButtonClicked(component) if component == btnDDL =>
      println("Button  DDL click")
      this.dispose()
      var ddlFrame = new DDLFrame();
      ddlFrame.visible_=(true)

    case ButtonClicked(component) if component == btnDML =>
      println("Button  DML click")
      this.dispose()
      var dmlFrame = new DMLFrame();
      dmlFrame.visible_=(true)
    case ButtonClicked(component) if component == btnExit =>
      this.dispose();
  }

}

