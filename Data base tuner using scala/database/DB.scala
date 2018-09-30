/*
 * If we want to make the the static variable then we make the class as Object (Singlton) and then we callall the memebers of that object
 *  as static member and we whereever it call is go to same value. means if we make the classas singlton then we  can not make the object of that siglton class
 *  and we only call the member of that siglton object by Class name (act as static) and common value go everywhere.
 */
package database

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

object DB { 
 
  var  d: String = "tuner"; //Act as static member.  when we change the database then we change that variable 
  
  def update(sql: String) { // we call by class name
    val driver = "com.mysql.jdbc.Driver";
    val url = "jdbc:mysql://localhost/"+d;
    println(url)

    val username = "root"
    val password = ""

    var statement: Statement = null;

    var con: Connection = null;
    try {
      Class.forName(driver);
      val con = DriverManager.getConnection(url, username, password);
      statement = con.createStatement();
    } catch {
      case e: Exception => println("exception caught: " + e);
    }
    statement.executeUpdate(sql);
  }

  def query(sql: String): ResultSet = {
    val driver = "com.mysql.jdbc.Driver";
    val url = "jdbc:mysql://localhost/"+d;
    println(url)
    val username = "root"
    val password = ""

    var statement: Statement = null;

    var con: Connection = null;
    try {
      Class.forName(driver);
      val con = DriverManager.getConnection(url, username, password);
      statement = con.createStatement();
    } catch {
      case e: Exception => println("exception caught: " + e);
    }
    var rs: ResultSet = statement.executeQuery(sql);
    return rs;
  }

}