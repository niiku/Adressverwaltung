package de.gkjava.addr.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import de.gkjava.addr.model.Address;

// Singelton
/**
 * @author  vmadmin
 */
public class AddressBroker extends Broker<Address> {
  /**
 * @uml.property  name="instance"
 * @uml.associationEnd  
 */
private static AddressBroker instance;

  private AddressBroker() {
  }

  // Bereitstellung einer Instanz (Singleton)
  /**
 * @return
 * @uml.property  name="instance"
 */
public static AddressBroker getInstance() {
    if (instance == null)
      instance = new AddressBroker();
    return instance;
  }

  // Erzeugung eines Objekts zu einer Ergebniszeile
  protected Address makeObject(ResultSet rs)
      throws SQLException {
    Address a = new Address();
    a.setId(rs.getInt(1));
    a.setLastname(rs.getString(2));
    a.setFirstname(rs.getString(3));
    a.setEmail(rs.getString(4));
    a.setEmailAdditional(rs.getString(5));
    a.setHomepage(rs.getString(6));
    return a;
  }

  // Alle Adressen holen
  public List<Address> findAll() throws Exception {
    return query("select * from address order by lastname, firstname");
  }

  // Eine neue Adresse speichern mit Rückgabe des generierten
  // Schlüssels
  public int insert(Address a) throws Exception {
    return insertAndReturnKey("insert into address "
        + "(lastname, firstname, email, " 
        +	"email_additional, homepage) "
        + "values ('" + a.getLastname() + "','"
        + a.getFirstname() + "','" + a.getEmail() + "','"
        + a.getEmailAdditional() + "','" + a.getHomepage()
        + "')");
  }

  // Eine Adresse ändern
  public void update(Address a) throws Exception {
    update("update address set " + "lastname = '"
        + a.getLastname() + "', firstname = '"
        + a.getFirstname() + "', email = '" + a.getEmail()
        + "', email_additional = '" + a.getEmailAdditional()
        + "', homepage = '" + a.getHomepage()
        + "' where id = " + a.getId());
  }

  // Eine Adresse löschen
  public void delete(int id) throws Exception {
    update("delete from address where id = " + id);
  }
}
