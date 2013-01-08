package de.gkjava.addr.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;

import de.gkjava.addr.controller.Controller;
import de.gkjava.addr.controller.Export;

/**
 * @author  vmadmin
 */
public class ViewToolBar {
  /**
 * @uml.property  name="controller"
 * @uml.associationEnd  
 */
private Controller controller;
  /**
 * @uml.property  name="toolBar"
 */
private JToolBar toolBar;
  private JButton newButton;
  private JButton editButton;
  private JButton deleteButton;
  private JButton exportButton;

  public ViewToolBar(Controller controller) {
    this.controller = controller;
    build();
  }

  private void build() {
    toolBar = new JToolBar();
    toolBar.setFloatable(false);

    Icon icon = new ImageIcon(getClass()
        .getResource("new.png"));
    newButton = new JButton(icon);
    newButton.setToolTipText(controller.getText("button.new"));
    newButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        controller.doNew();
      }
    });

    icon = new ImageIcon(getClass().getResource("edit.png"));
    editButton = new JButton(icon);
    editButton.setToolTipText(controller
        .getText("button.edit"));
    editButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        controller.doEdit();
      }
    });

    icon = new ImageIcon(getClass().getResource("delete.png"));
    deleteButton = new JButton(icon);
    deleteButton.setToolTipText(controller
        .getText("button.delete"));
    deleteButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        controller.doDelete();
      }
    });

    icon = new ImageIcon(getClass().getResource("export.png"));
    exportButton = new JButton(icon);
    exportButton.setToolTipText(controller
        .getText("button.export.csv"));
    exportButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        controller.doExport(Export.CSV);
      }
    });
    
        icon = new ImageIcon(getClass().getResource("object.png"));
    exportButton = new JButton(icon);
    exportButton.setToolTipText(controller
        .getText("button.export.object"));
    exportButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        controller.doExport(Export.OBJECT);
      }
    });

    toolBar.add(newButton);
    toolBar.add(editButton);
    toolBar.add(deleteButton);
    toolBar.add(exportButton);
  }

  /**
 * @return
 * @uml.property  name="toolBar"
 */
public JToolBar getToolBar() {
    return toolBar;
  }
}
