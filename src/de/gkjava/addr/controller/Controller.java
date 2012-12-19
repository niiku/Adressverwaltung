package de.gkjava.addr.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import de.gkjava.addr.model.Address;
import de.gkjava.addr.model.Model;
import de.gkjava.addr.persistence.AddressBroker;
import de.gkjava.addr.persistence.ConnectionManager;
import de.gkjava.addr.view.ViewFrame;

/**
 * @author  vmadmin
 */
public class Controller {
  /**
 * @uml.property  name="model"
 * @uml.associationEnd  
 */
private Model model;
  /**
 * @uml.property  name="frame"
 * @uml.associationEnd  
 */
private ViewFrame frame;

  private boolean update;
  private int selectedIndex = -1;

  private static final String BUNDLE = "de.gkjava.addr.bundle";
  private ResourceBundle bundle;

  public Controller(Model model) {
    this.model = model;
    bundle = ResourceBundle.getBundle(BUNDLE);
  }

  /**
 * @param frame
 * @uml.property  name="frame"
 */
public void setFrame(ViewFrame frame) {
    this.frame = frame;
  }

  // Lädt alle Adressen aus der Datenbank
  public void load() {
    try {
      model.setData();
      frame.getList().setListData(model.getNames());
    } catch (Exception e) {
      JOptionPane.showMessageDialog(frame, e.getMessage(),
          getText("message.error"), JOptionPane.ERROR_MESSAGE);
    }
  }

  // Bereitet die Erfassung einer neuen Adresse vor
  public void doNew() {
    frame.getList().clearSelection();
    frame.getEditPanel().clear();
    frame.getEditPanel().enable(true);
    update = false;
  }

  // Bereitet die Änderung einer Adresse vor
  public void doEdit() {
    selectedIndex = frame.getList().getSelectedIndex();
    if (selectedIndex >= 0) {
      Address address = model.get(selectedIndex);
      frame.getEditPanel().setAddress(address);
      frame.getEditPanel().enable(true);
      update = true;
    }
  }

  // Löscht eine Adresse
  public void doDelete() {
    selectedIndex = frame.getList().getSelectedIndex();
    if (selectedIndex >= 0) {
      try {
        int id = model.get(selectedIndex).getId();
        AddressBroker.getInstance().delete(id);
        model.remove(selectedIndex);

        Vector<String> names = model.getNames();
        frame.getList().setListData(names);

        // Selektiert den Eintrag oberhalb des gelöschten
        // Eintrags
        int idx = Math.max(0, selectedIndex - 1);
        frame.getList().setSelectedIndex(idx);

        frame.getEditPanel().clear();
        frame.getEditPanel().enable(false);
      } catch (Exception e) {
        JOptionPane.showMessageDialog(frame, e.getMessage(),
            getText("message.error"),
            JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  // Speichert eine neue bzw. geänderte Adresse
  public void doOk() {
    Address address = frame.getEditPanel().getAddress();
    if (hasErrors(address)) {
      return;
    }

    if (update) {
      if (selectedIndex >= 0) {
        try {
          AddressBroker.getInstance().update(address);
          model.set(selectedIndex, address);
          frame.getList().setListData(model.getNames());
          // Selektiert den Listeneintrag zur geänderten
          // Adresse
          int idx = model.getIndex(address.getId());
          frame.getList().setSelectedIndex(idx);
        } catch (Exception e) {
          JOptionPane.showMessageDialog(frame, e.getMessage(),
              getText("message.error"),
              JOptionPane.ERROR_MESSAGE);
        }
      }
    } else {
      try {
        int newId = AddressBroker.getInstance()
            .insert(address);
        address.setId(newId);
        model.add(address);
        frame.getList().setListData(model.getNames());
        // Selektiert den Listeneintrag zur neuen Adresse
        int idx = model.getIndex(newId);
        frame.getList().setSelectedIndex(idx);
      } catch (Exception e) {
        JOptionPane.showMessageDialog(frame, e.getMessage(),
            getText("message.error"),
            JOptionPane.ERROR_MESSAGE);
      }
    }

    frame.getEditPanel().clear();
    frame.getEditPanel().enable(false);
  }

  // Setzt Änderungen zurück
  public void doCancel() {
    if (selectedIndex >= 0) {
      Address address = model.get(selectedIndex);
      frame.getEditPanel().setAddress(address);
    }
    frame.getEditPanel().clear();
    frame.getEditPanel().enable(false);
  }

  // Exportiert alle Adressen im CSV-Format
  public void doExport() {
    JFileChooser chooser = new JFileChooser();
    int opt = chooser.showSaveDialog(frame);
    if (opt != JFileChooser.APPROVE_OPTION)
      return;

    try {
      File file = chooser.getSelectedFile();
      export(file);
      JOptionPane.showMessageDialog(frame, file,
          getText("message.filesaved"),
          JOptionPane.INFORMATION_MESSAGE);
    } catch (Exception e) {
      JOptionPane.showMessageDialog(frame, e.getMessage(),
          getText("message.error"), JOptionPane.ERROR_MESSAGE);
    }
  }

  private void export(File file) throws Exception {
    List<Address> data = model.getData();
    PrintWriter out = new PrintWriter(new FileWriter(file));
    String quote = "\"";
    String sep = ";";

    StringBuilder sb = new StringBuilder();
    sb.append(quote + getText("address.id") + quote + sep);
    sb.append(quote + getText("address.lastname") + quote
        + sep);
    sb.append(quote + getText("address.firstname") + quote
        + sep);
    sb.append(quote + getText("address.email") + quote + sep);
    sb.append(quote + getText("address.email_additional")
        + quote + sep);
    sb.append(quote + getText("address.homepage") + quote);
    out.println(sb);

    for (Address a : data) {
      sb = new StringBuilder();
      sb.append(quote + a.getId() + quote + sep);
      sb.append(quote + a.getLastname() + quote + sep);
      sb.append(quote + a.getFirstname() + quote + sep);
      sb.append(quote + a.getEmail() + quote + sep);
      sb.append(quote + a.getEmailAdditional() + quote + sep);
      sb.append(quote + a.getHomepage() + quote);
      out.println(sb);
    }

    out.close();
  }

  // Prüft einen Adresseintrag
  public boolean hasErrors(Address address) {
    if (address.getLastname().length() == 0) {
      JOptionPane.showMessageDialog(frame,
          getText("message.lastname.invalid"),
          getText("message.error"), JOptionPane.ERROR_MESSAGE);
      return true;
    }
    return false;
  }

  // Liefert den sprachabhängigen Text
  public String getText(String key) {
    try {
      return bundle.getString(key);
    } catch (MissingResourceException e) {
      return key;
    }
  }

  // Schließt die Datenbank-Verbindung
  public void exit() {
    ConnectionManager.closeConnection();
  }
}
