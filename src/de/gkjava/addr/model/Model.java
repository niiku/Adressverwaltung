package de.gkjava.addr.model;

import java.util.Collections;
import java.util.List;
import java.util.Vector;

import de.gkjava.addr.persistence.AddressBroker;

/**
 * @author  vmadmin
 */
public class Model {
  /**
 * @uml.property  name="data"
 */
private List<Address> data;

  public void setData() throws Exception {
    data = AddressBroker.getInstance().findAll();
  }

  /**
 * @return
 * @uml.property  name="data"
 */
public List<Address> getData() {
    return data;
  }

  // Erzeugung der sortierten Namensliste für die Anzeige
  public Vector<String> getNames() {
    Collections.sort(data);
    Vector<String> names = new Vector<String>();
    for (Address address : data) {
      String firstname = address.getFirstname();
      if (firstname.length() > 0) {
        names.add(address.getLastname() + ", " + firstname);
      } else {
        names.add(address.getLastname());
      }
    }
    return names;
  }

  // Ermittlung der Position einer Adresse in der Liste
  public int getIndex(int id) {
    int size = data.size();
    for (int i = 0; i < size; i++) {
      if (data.get(i).getId() == id) {
        return i;
      }
    }
    return -1;
  }

  public Address get(int idx) {
    return data.get(idx);
  }

  public void set(int idx, Address address) {
    data.set(idx, address);
  }

  public void remove(int idx) {
    data.remove(idx);
  }

  public void add(Address address) {
    data.add(address);
  }
}
