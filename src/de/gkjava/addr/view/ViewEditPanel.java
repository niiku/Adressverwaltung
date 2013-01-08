package de.gkjava.addr.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.gkjava.addr.controller.Controller;
import de.gkjava.addr.model.Address;

/**
 * @author vmadmin
 */
public class ViewEditPanel {

    /**
     * @uml.property name="controller"
     * @uml.associationEnd
     */
    private Controller controller;
    private int id; // Schlüssel der Adresse
    /**
     * @uml.property name="panel"
     */
    private JPanel panel;
    private JTextField lastnameField;
    private JTextField firstnameField;
    private JTextField emailField;
    private JTextField emailAdditionalField;
    private JTextField homepageField;
    private JTextField fixedNetwork;
    private JTextField mobile;
    private JButton okButton;
    private JButton cancelButton;

    public ViewEditPanel(Controller controller) {
        this.controller = controller;
        build();
    }

    private void build() {
        JPanel labelPanel = new JPanel(new GridLayout(7, 1, 0, 10));
        labelPanel.add(new JLabel(controller
                .getText("address.lastname"), JLabel.RIGHT));
        labelPanel.add(new JLabel(controller
                .getText("address.firstname"), JLabel.RIGHT));
        labelPanel.add(new JLabel(controller
                .getText("address.email"), JLabel.RIGHT));
        labelPanel.add(new JLabel(controller
                .getText("address.email_additional"), JLabel.RIGHT));
        labelPanel.add(new JLabel(controller
                .getText("address.homepage"), JLabel.RIGHT));
        labelPanel.add(new JLabel(controller
                .getText("address.fixed_network"), JLabel.RIGHT));
        labelPanel.add(new JLabel(controller
                .getText("address.mobile"), JLabel.RIGHT));

        JPanel fieldPanel = new JPanel(new GridLayout(7, 1, 0, 10));
        lastnameField = new JTextField();
        firstnameField = new JTextField();
        emailField = new JTextField();
        emailAdditionalField = new JTextField();
        homepageField = new JTextField();
        fixedNetwork = new JTextField();
        mobile = new JTextField();
        fieldPanel.add(lastnameField);
        fieldPanel.add(firstnameField);
        fieldPanel.add(emailField);
        fieldPanel.add(emailAdditionalField);
        fieldPanel.add(homepageField);
        fieldPanel.add(fixedNetwork);
        fieldPanel.add(mobile);

        JPanel buttonPanel = new JPanel(new FlowLayout(
                FlowLayout.RIGHT));
        okButton = new JButton(controller.getText("button.ok"));
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.doOk();
            }
        });
        cancelButton = new JButton(controller
                .getText("button.cancel"));
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.doCancel();
            }
        });
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        JPanel centerPanel = new JPanel(new BorderLayout(10, 0));
        centerPanel.add(labelPanel, BorderLayout.WEST);
        centerPanel.add(fieldPanel, BorderLayout.CENTER);

        JPanel panel1 = new JPanel(new BorderLayout());
        panel1
                .add(Box.createVerticalStrut(20), BorderLayout.NORTH);
        panel1.add(Box.createHorizontalStrut(20),
                BorderLayout.WEST);
        panel1.add(centerPanel, BorderLayout.CENTER);
        panel1.add(Box.createHorizontalStrut(20),
                BorderLayout.EAST);

        JPanel panel2 = new JPanel(new BorderLayout());
        panel2.add(buttonPanel, BorderLayout.CENTER);
        panel2.add(Box.createHorizontalStrut(20),
                BorderLayout.EAST);
        panel2
                .add(Box.createVerticalStrut(20), BorderLayout.SOUTH);

        panel = new JPanel(new BorderLayout());
        panel.add(panel1, BorderLayout.NORTH);
        panel.add(panel2, BorderLayout.SOUTH);
    }

    /**
     * @return @uml.property name="panel"
     */
    public JPanel getPanel() {
        return panel;
    }

    // Übertragung Adress-Objekt --> Formularfelder
    public void setAddress(Address address) {
        id = address.getId();
        lastnameField.setText(address.getLastname());
        firstnameField.setText(address.getFirstname());
        emailField.setText(address.getEmail());
        emailAdditionalField.setText(address.getEmailAdditional());
        homepageField.setText(address.getHomepage());
        fixedNetwork.setText(address.getFixedNetwork());
        mobile.setText(address.getMobile());
    }

    // Übertragung Formularfelder --> Adress-Objekt
    public Address getAddress() {
        Address address = new Address();
        address.setId(id);
        address.setLastname(lastnameField.getText().trim());
        address.setFirstname(firstnameField.getText().trim());
        address.setEmail(emailField.getText().trim());
        address.setEmailAdditional(emailAdditionalField.getText()
                .trim());
        address.setHomepage(homepageField.getText().trim());
        address.setFixedNetwork(fixedNetwork.getText().trim());
        address.setMobile(mobile.getText().trim());
        return address;
    }

    public void clear() {
        lastnameField.setText("");
        firstnameField.setText("");
        emailField.setText("");
        emailAdditionalField.setText("");
        homepageField.setText("");
        fixedNetwork.setText("");
        mobile.setText("");
    }

    public void enable(boolean b) {
        lastnameField.setEnabled(b);
        firstnameField.setEnabled(b);
        emailField.setEnabled(b);
        emailAdditionalField.setEnabled(b);
        homepageField.setEnabled(b);
        fixedNetwork.setEnabled(b);
        mobile.setEnabled(b);
        okButton.setEnabled(b);
        cancelButton.setEnabled(b);
    }
}
