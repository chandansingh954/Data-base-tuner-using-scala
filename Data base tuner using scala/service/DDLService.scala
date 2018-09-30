package service
import database.DB;
import java.util.Vector;
import java.sql.ResultSet;

class DDLService {

  def covertToArrayAddingCheckbox(v: Vector[Vector[Any]], nRow: Int, nCol: Int): Array[Array[Any]] = {
    var rows: Array[Array[Any]] = new Array[Array[Any]](nRow);

    var i: Int = 0;
    while (i < nRow) {
      var v1: Vector[Any] = new Vector[Any];
      v1 = v.get(i);
      var a1: Array[Any] = new Array[Any](nCol + 1);
      var j: Int = 0;
      while (j < nCol + 1) {
        if (j == 0) {
          a1(j) = false;
        } else {
          a1(j) = v1.get(j - 1);
          println(a1(j));
        }
        j = j + 1;
      }
      rows(i) = a1;

      i = i + 1;
    }
    return rows

  }
  def convertToArray(v: Vector[Vector[Any]], nRow: Int, nCol: Int): Array[Array[Any]] = {

    var rows: Array[Array[Any]] = new Array[Array[Any]](nRow);

    var i: Int = 0;
    while (i < nRow) {
      var v1: Vector[Any] = new Vector[Any];
      v1 = v.get(i);
      var a1: Array[Any] = new Array[Any](nCol);
      var j: Int = 0;
      while (j < nCol) {
        a1(j) = v1.get(j);
        println(a1(j));

        j = j + 1;
      }
      rows(i) = a1;

      i = i + 1;
    }
    return rows

  }

  def getTables(): Array[Array[Any]] = {
    var rows: Vector[Vector[Any]] = new Vector[Vector[Any]];
    try {
      val sql = "show tables";
      val rs: ResultSet = DB.query(sql);
      var i: Int = 0;
      while (rs.next()) {
        var row: Vector[Any] = new Vector[Any];
        row.add(rs.getString(1));
        println(rs.getString(1));

        rows.add(row);
        i = i + 1;
      }

      val value: Array[Array[Any]] = convertToArray(rows, i, 1);
      println("------------");

      return value;
    } catch {
      case e: Exception =>
        println("exception caught: " + e);
        return null;
    }

  }
  def getTablesForSelect(): Array[Array[Any]] = {
    var rows: Vector[Vector[Any]] = new Vector[Vector[Any]];
    try {
      val sql = "show tables";
      val rs: ResultSet = DB.query(sql);
      var i: Int = 0;
      while (rs.next()) {
        var row: Vector[Any] = new Vector[Any];
        row.add(rs.getString(1));
        println(rs.getString(1));

        rows.add(row);
        i = i + 1;
      }

      val value: Array[Array[Any]] = covertToArrayAddingCheckbox(rows, i, 1);
      println("------------");

      return value;
    } catch {
      case e: Exception =>
        println("exception caught: " + e);
        return null;
    }

  }
  def createDatabase(tx: String): Boolean = {
    try {
      val sql = "create database " + tx;
      DB.update(sql)
      return true;
    } catch {
      case e: Exception =>
        println("exception in create database: " + e);
        return false;
    }
  }
  def getDatabase(): Array[Array[Any]] = {
    var rows: Vector[Vector[Any]] = new Vector[Vector[Any]];
    try {
      val sql = "show databases";
      val rs: ResultSet = DB.query(sql);
      var i: Int = 0;
      while (rs.next()) {
        var row: Vector[Any] = new Vector[Any];
        row.add(rs.getString(1));
        println(rs.getString(1));

        rows.add(row);
        i = i + 1;
      }

      val value: Array[Array[Any]] = convertToArray(rows, i, 1);
      println("------------");

      return value;
    } catch {
      case e: Exception =>
        println("exception to get database : " + e);
        return null;
    }
  }
  def getDatabaseForSelect(): Array[Array[Any]] = {
    var rows: Vector[Vector[Any]] = new Vector[Vector[Any]];
    try {
      val sql = "show databases";
      val rs: ResultSet = DB.query(sql);
      var i: Int = 0;
      while (rs.next()) {
        var row: Vector[Any] = new Vector[Any];
        row.add(rs.getString(1));
        println(rs.getString(1));

        rows.add(row);
        i = i + 1;
      }

      val value: Array[Array[Any]] = covertToArrayAddingCheckbox(rows, i, 1);
      println("------------");

      return value;
    } catch {
      case e: Exception =>
        println("exception to get database : " + e);
        return null;
    }
  }
  def selectDatabase(): String = {
    try {
      val sql = "select  database()";
      val rs: ResultSet = DB.query(sql);
      var msg: String = ""
      while (rs.next()) {
        msg = rs.getString(1);
      }
      return msg;
    } catch {
      case e: Exception =>
        println("exception in select database: " + e);
        return "sorry to show select database";
    }
  }
  def useDatabase(tx: String): Boolean = {
    try {
      //val sql = "use " + tx;
      //println(tx)
      //update(sql)
      DB.d = tx;

      return true;
    } catch {
      case e: Exception =>
        println("exception in change database: " + e);
        return false;
    }
  }
  def describeTable(tableName: String): Array[Array[Any]] = {
    var rows: Vector[Vector[Any]] = new Vector[Vector[Any]];
    try {
      val sql = "describe " + tableName;
      val rs: ResultSet = DB.query(sql);
      var i: Int = 0;
      while (rs.next()) {
        var row: Vector[Any] = new Vector[Any];
        row.add(rs.getString(1));
        row.add(rs.getString(2));
        row.add(rs.getString(3));
        row.add(rs.getString(4));
        row.add(rs.getString(5));
        row.add(rs.getString(6));
        rows.add(row);
        i = i + 1;
      }

      val value: Array[Array[Any]] = convertToArray(rows, i, 6);
      println("------------");

      return value;
    } catch {
      case e: Exception =>
        println("exception to get database : " + e);
        return null;
    }
  }
  def describeTableForSelect(tableName: String): Array[Array[Any]] = {
    var rows: Vector[Vector[Any]] = new Vector[Vector[Any]];
    try {
      val sql = "describe " + tableName;
      val rs: ResultSet = DB.query(sql);
      var i: Int = 0;
      while (rs.next()) {
        var row: Vector[Any] = new Vector[Any];
        row.add(rs.getString(1));
        row.add(rs.getString(2));
        row.add(rs.getString(3));
        row.add(rs.getString(4));
        row.add(rs.getString(5));
        row.add(rs.getString(6));
        rows.add(row);
        i = i + 1;
      }

      val value: Array[Array[Any]] = covertToArrayAddingCheckbox(rows, i, 6);
      println("------------");

      return value;
    } catch {
      case e: Exception =>
        println("exception to get database : " + e);
        return null;
    }
  }
  def describeTableNumericFieldForSelect(tableName: String): Array[Array[Any]] = {
    var rows: Vector[Vector[Any]] = new Vector[Vector[Any]];
    try {
      val sql = "describe " + tableName;
      val rs: ResultSet = DB.query(sql);
      var i: Int = 0;
      while (rs.next()) {
        if (rs.getString(2).contains("int") || rs.getString(2).contains("float")) {
          var row: Vector[Any] = new Vector[Any];
          row.add(rs.getString(1));
          row.add(rs.getString(2));
          row.add(rs.getString(3));
          row.add(rs.getString(4));
          row.add(rs.getString(5));
          row.add(rs.getString(6));
          rows.add(row);
          i = i + 1;
        }
        
      }

      val value: Array[Array[Any]] = covertToArrayAddingCheckbox(rows, i, 6);
      println("------------");

      return value;
    } catch {
      case e: Exception =>
        println("exception to get database : " + e);
        return null;
    }
  }
  def createTable(sql: String): Boolean = {
    try {
      println(sql)
      DB.update(sql)
      return true;
    } catch {
      case e: Exception =>
        println("exception in create table: " + e);
        return false;
    }

  }

  def createNewTableFromExisting(oldName: String, newName: String): Boolean = {
    try {
      val sql = "create table if not exists " + newName + " as select * from " + oldName;
      DB.update(sql)
      return true;
    } catch {
      case e: Exception =>
        println("exception in create  existing table: " + e);
        return false;
    }

  }
  def renameTable(oldName: String, newName: String): Boolean = {
    try {
      val sql = "Rename table " + oldName + " to " + newName;
      DB.update(sql)
      return true;
    } catch {
      case e: Exception =>
        println("exception in create  rename table: " + e);
        return false;
    }

  }

  def dropTable(name: String): Boolean = {
    try {
      val sql = "drop table " + name;
      DB.update(sql)
      return true;
    } catch {
      case e: Exception =>
        println("exception in create  drop table: " + e);
        return false;
    }

  }
  def addNewColumn(sql: String): Boolean = {
    try {
      DB.update(sql)
      return true;
    } catch {
      case e: Exception =>
        println("exception in add  new table: " + e);
        return false;
    }

  }
  def dropOneColumn(tableName: String, columnName: String): Boolean = {
    try {
      var sql = "alter table " + tableName + " drop " + columnName;
      DB.update(sql)
      return true;
    } catch {
      case e: Exception =>
        println("exception in drop  one column: " + e);
        return false;
    }

  }
  def modifyColumn(tableName: String, oldColumnName: String, newColumnName: String, columnType: String): Boolean = {
    try {
      var sql = "alter table " + tableName + " change " + oldColumnName + " " + newColumnName + " " + columnType;
      DB.update(sql)
      return true;
    } catch {
      case e: Exception =>
        println("exception in modify  one column: " + e);
        return false;
    }

  }

}






