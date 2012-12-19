package de.gkjava.addr.view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.JList;
import javax.swing.ListSelectionModel;

import de.gkjava.addr.controller.Controller;

/**
 * @author  vmadmin
 */
public class ViewList {
  /**
 * @uml.property  name="controller"
 * @uml.associationEnd  
 */
private Controller controller;
  /**
 * @uml.property  name="list"
 */
private JList list;

  public ViewList(Controller controller) {
    this.controller = controller;
    build();
  }

  private void build() {
    list = new JList();
    list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    MouseListener mouseListener = new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
          controller.doEdit();
        }
      }
    };
    list.addMouseListener(mouseListener);
  }

  /**
 * @return
 * @uml.property  name="list"
 */
public JList getList() {
    return list;
  }

  public void setListData(Vector<String> data) {
    list.setListData(data);
  }

  public void clearSelection() {
    list.clearSelection();
  }

  public int getSelectedIndex() {
    return list.getSelectedIndex();
  }

  public void setSelectedIndex(int idx) {
    list.setSelectedIndex(idx);
  }
}
