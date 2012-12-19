package de.gkjava.addr.persistence;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public abstract class Broker<T> {
  // Einschub-Methode, die von Subklassen zu implementieren ist
  protected abstract T makeObject(ResultSet rs)
      throws SQLException;

  // SQL-Select
  protected List<T> query(String sql) throws IOException,
      SQLException {
    List<T> result = new ArrayList<T>();
    Connection con = null;
    Statement stmt = null;
    ResultSet rs = null;

    try {
      con = ConnectionManager.getConnection();
      stmt = con.createStatement();
      rs = stmt.executeQuery(sql);
      while (rs.next()) {
        result.add(makeObject(rs));
      }
      return result;
    } finally {
      if (rs != null)
        rs.close();
      if (stmt != null)
        stmt.close();
    }
  }

  // SQL-Update, -Insert oder -Delete
  protected int update(String sql) throws IOException,
      SQLException {
    Connection con = null;
    Statement stmt = null;
    int count = 0;

    try {
      con = ConnectionManager.getConnection();
      stmt = con.createStatement();
      count = stmt.executeUpdate(sql);
      return count;
    } finally {
      if (stmt != null)
        stmt.close();
    }
  }

  // SQL-Insert mit Rückgabe des automatisch erzeugten
  // Schlüssels
  protected int insertAndReturnKey(String sql)
      throws IOException, SQLException {
    Connection con = null;
    Statement stmt = null;
    ResultSet rs = null;

    try {
      con = ConnectionManager.getConnection();
      stmt = con.createStatement();
      stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
      rs = stmt.getGeneratedKeys();
      rs.next();
      int id = rs.getInt(1);
      return id;
    } finally {
      if (rs != null)
        rs.close();
      if (stmt != null)
        stmt.close();
    }
  }
}
