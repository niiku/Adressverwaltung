package de.gkjava.addr.persistence;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {
  private static final String FILE = "/dbparam.txt";
  private static String url;
  private static String user;
  private static String password;
  private static Connection con;

  // Verbindung herstellen
  public static Connection getConnection() throws IOException,
      SQLException {
    if (con == null) {
      init();
      con = DriverManager.getConnection(url, user, password);
    }
    return con;
  }

  // Verbindung schlieﬂen
  public static void closeConnection() {
    try {
      if (con != null) {
        con.close();
        con = null;
      }
    } catch (SQLException e) {
    }
  }

  // Verbindungseigenschaften laden
  private static void init() throws IOException {
    URL file = ConnectionManager.class.getResource(FILE);
    if (file == null)
      throw new IOException(FILE + " not found");

    InputStream in = file.openStream();
    Properties prop = new Properties();
    prop.load(in);
    in.close();

    url = prop.getProperty("url");
    user = prop.getProperty("user", "");
    password = prop.getProperty("password", "");
  }
}
