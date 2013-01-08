package de.gkjava.addr.controller;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JOptionPane;

import de.gkjava.addr.model.Address;
import de.gkjava.addr.model.Model;
import de.gkjava.addr.persistence.AddressBroker;
import de.gkjava.addr.persistence.ConnectionManager;
import de.gkjava.addr.view.ViewFrame;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.GuiUtils;
import utils.StringUtils;

/**
 * @author vmadmin
 */
public class Controller {

    private Model model;
    private ViewFrame frame;
    private boolean update;
    private int selectedIndex = -1;
    private static final String BUNDLE = "de.gkjava.addr.bundle";
    private ResourceBundle bundle;
    private String sep = ",";
    private String quote = "\"";

    public Controller(Model model) {
        this.model = model;
        bundle = ResourceBundle.getBundle(BUNDLE);
    }

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
    public void doExport(DataType type) {
        try {
            File file = GuiUtils.getFileFromSaveDialog(frame);
            if (file == null) {
                return;
            }
            switch (type) {
                case CSV:
                    csvExport(file);
                    break;
                case OBJECT:
                    objectExport(file);
                    break;
            }

            JOptionPane.showMessageDialog(frame, file,
                    getText("message.filesaved"),
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, ex.getMessage(),
                    getText("message.error"), JOptionPane.ERROR_MESSAGE);
        }
    }

    public void doImport(DataType type) {
        try {
            File file = GuiUtils.getFileFromOpenDialog(frame);
            if (file == null) {
                return;
            }
            switch (type) {
                case CSV:
                    csvImport(file);
                    break;
                case OBJECT:
                    objectImport(file);
                    break;
            }
            JOptionPane.showMessageDialog(frame, file,
                    getText("message.import.success"),
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, ex.getMessage(),
                    getText("message.error"), JOptionPane.ERROR_MESSAGE);
        }
    }

    private void importAddresses(List<Address> newAddresses) {
        try {
            AddressBroker broker = AddressBroker.getInstance();
            List<Address> oldAddresses = broker.findAll();
            for (Address newAddress : newAddresses) {
                if (oldAddresses.contains(newAddress)) {
                    broker.update(newAddress);
                } else {
                    broker.insert(newAddress);
                }
            }
            load();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, getText(ex.getMessage()), getText("message.error"), JOptionPane.ERROR_MESSAGE);
            Logger
                    .getLogger(Controller.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void csvImport(File file) throws FileNotFoundException, IOException {
        CSVReader csvReader = new CSVReader(new FileReader(file), ',', '"');
        List<String[]> addresses = csvReader.readAll();
        if (addresses.get(0)[0].equals("id")) {
            addresses.remove(0);
        }
        List<Address> newAddresses = new ArrayList<>();
        Address address;
        for (String[] line : addresses) {
            address = new Address();
            address.setId(new Integer(line[0]));
            address.setLastname(line[1]);
            address.setFirstname(line[2]);
            address.setEmail(line[3]);
            address.setEmailAdditional(line[4]);
            address.setHomepage(line[5]);
            address.setFixedNetwork(line[6]);
            address.setMobile(line[7]);
            newAddresses.add(address);
        }

        importAddresses(newAddresses);

    }

    private void objectImport(File file) throws FileNotFoundException, IOException, ClassNotFoundException {
        FileInputStream fileIn =
                new FileInputStream(file);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        List<Address> newAddresses = (List<Address>) in.readObject();
        in.close();
        fileIn.close();
        importAddresses(newAddresses);
    }

    private void objectExport(File file) throws FileNotFoundException, IOException {
        FileOutputStream fileOut =
                new FileOutputStream(file);
        ObjectOutputStream out =
                new ObjectOutputStream(fileOut);
        out.writeObject(model.getData());
        out.close();
        fileOut.close();

    }

    private void csvExport(File file) throws Exception {
        List<Address> data = model.getData();
        PrintWriter out = new PrintWriter(new FileWriter(file));
        String columns = quote + StringUtils.join(Address.COLUMN_NAMES, quote + sep + quote) + quote;
        out.println(columns);
        String addressRow;
        for (Address address : data) {
            addressRow = quote + StringUtils.join(address.getAddressDataAsStringList(), quote + sep + quote) + quote;
            out.println(addressRow);
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
