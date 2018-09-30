package service

import database.DB;
import java.util.Vector;
import java.sql.ResultSet;

class DMLService {

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

  def insert(tName: String, vField: Vector[Any], vName: Vector[Any]): Boolean = {
    try {
      println(vName)
      var sql: String = "insert into " + tName + " ( ";
      var i: Int = 0;
      while (i < vField.size()) {
        sql = sql + vField.get(i).asInstanceOf[String] + " ,"
        i = i + 1;
      }
      sql = sql.substring(0, sql.length - 1);
      sql = sql + " ) Values ( "
      var j: Int = 0;
      while (j < vName.size()) {
        sql = sql + "'" + vName.get(j) + "' ,"
        j = j + 1;
      }
      sql = sql.substring(0, sql.length - 1);
      sql = sql + " )";
      println(sql);
      DB.update(sql)
      return true;
    } catch {
      case e: Exception =>
        println("exception in insert data: " + e);
        return false;
    }
  }
  def getAllData(tName: String, vecType: Vector[String]): Array[Array[Any]] = {
    try {
      var rows: Vector[Vector[Any]] = new Vector[Vector[Any]];
      var s: Int = vecType.size();

      var sql: String = "Select * from " + tName;
      val rs: ResultSet = DB.query(sql);
      while (rs.next()) {
        var row: Vector[Any] = new Vector[Any];
        var i: Int = 0;
        while (i < s) {
          if (vecType.get(i).contains("int")) {
            row.add(rs.getInt(i + 1)); // in sql index is start from 1
          } else if (vecType.get(i).contains("float")) {
            row.add(rs.getFloat(i + 1));
          } else if (vecType.get(i).contains("varchar")) {
            row.add(rs.getString(i + 1));
          } else if (vecType.get(i).contains("date")) {
            row.add(rs.getString(i + 1));
          }
          i = i + 1;
        }
        rows.add(row)
      }

      val value: Array[Array[Any]] = convertToArray(rows, rows.size(), s); // rows.size() is no of rows and s is no of columns
      return value;
    } catch {
      case e: Exception =>
        println("exception caught to read all data: " + e);
        return null;
    }

  }
  def getAllDataWithSelect(tName: String, vecType: Vector[String]): Array[Array[Any]] = {
    try {
      var rows: Vector[Vector[Any]] = new Vector[Vector[Any]];
      var s: Int = vecType.size();

      var sql: String = "Select * from " + tName;
      val rs: ResultSet = DB.query(sql);
      while (rs.next()) {
        var row: Vector[Any] = new Vector[Any];
        var i: Int = 0;
        while (i < s) {
          if (vecType.get(i).contains("int")) {
            row.add(rs.getInt(i + 1)); // in sql index is start from 1
          } else if (vecType.get(i).contains("float")) {
            row.add(rs.getFloat(i + 1));
          } else if (vecType.get(i).contains("varchar")) {
            row.add(rs.getString(i + 1));
          } else if (vecType.get(i).contains("date")) {
            row.add(rs.getString(i + 1));
          }
          i = i + 1;
        }
        rows.add(row)
      }

      val value: Array[Array[Any]] = covertToArrayAddingCheckbox(rows, rows.size(), s); // rows.size() is no of rows and s is no of columns
      return value;
    } catch {
      case e: Exception =>
        println("exception caught to read all data: " + e);
        return null;
    }

  }
  def getKeyField(field: String, tp: String, tName: String): Array[Array[Any]] = {
    try {
      var rows: Vector[Vector[Any]] = new Vector[Vector[Any]];
      var sql: String = "select " + field + " from " + tName;
      val rs: ResultSet = DB.query(sql);
      while (rs.next()) {
        var row: Vector[Any] = new Vector[Any];
        if (tp.contains("int")) {
          row.add(rs.getInt(field)); // in sql index is start from 1
        } else if (tp.contains("float")) {
          row.add(rs.getFloat(field));
        } else if (tp.contains("varchar")) {
          row.add(rs.getString(field));
        } else if (tp.contains("date")) {
          row.add(rs.getString(field));
        }

        rows.add(row)
      }
      val value: Array[Array[Any]] = covertToArrayAddingCheckbox(rows, rows.size(), 1);
      return value;
    } catch {
      case e: Exception =>
        println("exception caught to read key data: " + e);
        return null;
    }

  }
  def getSingleFieldData(tName: String, keyField: String, keyData: Int, vecType: Vector[String]): Array[Array[Any]] = {
    try {
      var rows: Vector[Vector[Any]] = new Vector[Vector[Any]];
      var s: Int = vecType.size();

      var sql: String = "Select * from " + tName + " where " + keyField + " = '" + keyData + "'";
      println(sql)
      val rs: ResultSet = DB.query(sql);
      while (rs.next()) {
        var row: Vector[Any] = new Vector[Any];
        var i: Int = 0;
        while (i < s) {
          if (vecType.get(i).contains("int")) {
            row.add(rs.getInt(i + 1)); // in sql index is start from 1
          } else if (vecType.get(i).contains("float")) {
            row.add(rs.getFloat(i + 1));
          } else if (vecType.get(i).contains("varchar")) {
            row.add(rs.getString(i + 1));
          } else if (vecType.get(i).contains("date")) {
            row.add(rs.getString(i + 1));
          }
          i = i + 1;
        }
        rows.add(row)
      }

      val value: Array[Array[Any]] = convertToArray(rows, rows.size(), s); // rows.size() is no of rows and s is no of columns
      return value;
    } catch {
      case e: Exception =>
        println("exception caught to read single data: " + e);
        return null;
    }

  }
  def getSpecificColumnRow(vecField: Vector[String], tName: String, vecType: Vector[String]): Array[Array[Any]] = {
    try {
      var rows: Vector[Vector[Any]] = new Vector[Vector[Any]];
      var s: Int = vecField.size();

      var sql: String = "Select ";
      var j: Int = 0;
      while (j < s) {
        sql = sql + vecField.get(j) + ",";
        j = j + 1;
      }
      sql = sql.substring(0, sql.length() - 1);
      sql = sql + " from " + tName;
      val rs: ResultSet = DB.query(sql);
      while (rs.next()) {
        var row: Vector[Any] = new Vector[Any];
        var i: Int = 0;
        while (i < s) {
          if (vecType.get(i).contains("int")) {
            row.add(rs.getInt(vecField.get(i))); // in sql index is start from 1
          } else if (vecType.get(i).contains("float")) {
            row.add(rs.getFloat(vecField.get(i)));
          } else if (vecType.get(i).contains("varchar")) {
            row.add(rs.getString(vecField.get(i)));
          } else if (vecType.get(i).contains("date")) {
            row.add(rs.getString(vecField.get(i)));
          }
          i = i + 1;
        }
        rows.add(row)
      }

      val value: Array[Array[Any]] = convertToArray(rows, rows.size(), s); // rows.size() is no of rows and s is no of columns
      return value;
    } catch {
      case e: Exception =>
        println("exception caught to read specific column data: " + e);
        return null;
    }

  }
  def getSpecificColumnWithCondtition(sql: String, vecType: Vector[String], vecField: Vector[String]): Array[Array[Any]] = {
    try {
      println(sql);
      val s: Int = vecType.size()
      var rows: Vector[Vector[Any]] = new Vector[Vector[Any]];
      val rs: ResultSet = DB.query(sql);
      while (rs.next()) {
        var row: Vector[Any] = new Vector[Any];
        var i: Int = 0;
        while (i < s) {
          if (vecType.get(i).contains("int")) {
            row.add(rs.getInt(vecField.get(i))); // in sql index is start from 1
          } else if (vecType.get(i).contains("float")) {
            row.add(rs.getFloat(vecField.get(i)));
          } else if (vecType.get(i).contains("varchar")) {
            row.add(rs.getString(vecField.get(i)));
          } else if (vecType.get(i).contains("date")) {
            row.add(rs.getString(vecField.get(i)));
          }
          i = i + 1;
        }
        rows.add(row)
      }

      val value: Array[Array[Any]] = convertToArray(rows, rows.size(), s); // rows.size() is no of rows and s is no of columns
      return value;
    } catch {
      case e: Exception =>
        println("exception caught to read specific column data: " + e);
        return null;
    }

  }
  def getAggregateColumnValue(sql: String, fieldType: String): Array[Array[Any]] = {
    try {
      println(sql);
      var rows: Vector[Vector[Any]] = new Vector[Vector[Any]];
      val rs: ResultSet = DB.query(sql);
      while (rs.next()) {
        var row: Vector[Any] = new Vector[Any];
        if (fieldType.contains("int")) {
          row.add(rs.getInt(1));
        } else if (fieldType.contains("float")) {
          row.add(rs.getFloat(1));
        }
        rows.add(row);
      }
      val value: Array[Array[Any]] = convertToArray(rows, 1, 1);
      return value;

    } catch {
      case e: Exception =>
        println("exception caught to read specific column data: " + e);
        return null;
    }

  }
  def updateInAll(tableName: String, fieldName: String, fieldValue: String): Boolean = {
    try {
      val sql = "Update " + tableName + " set " + fieldName + " = " + fieldName + " + " + fieldValue;
      println(sql)
      DB.update(sql)
      return true;
    } catch {
      case e: Exception =>
        println("exception in update in all: " + e);
        return false;
    }
  }
  def updateInSpecific(tableName: String, vecField: Vector[Any], vecValue: Vector[Any]): Boolean = {
    try {
      var sql = "Update " + tableName + " set ";
      var i: Int = 1;
      while (i < vecField.size()) {
        sql = sql + vecField.get(i).asInstanceOf[String] + " = '" + vecValue.get(i) + "' ,";
        i = i + 1;
      }
      sql = sql.substring(0, sql.length() - 1);
      sql = sql + " where " + vecField.get(0).asInstanceOf[String] + " = '" + vecValue.get(0) + "' ";

      println(sql)
      DB.update(sql)
      return true;
    } catch {
      case e: Exception =>
        println("exception in update in specific: " + e);
        return false;
    }
  }
  def delete(sql: String): Boolean = {
    try {
      println(sql)
      DB.update(sql)
      return true;

    } catch {
      case e: Exception =>
        println("exception in delete: " + e);
        return false;
    }

  }

}