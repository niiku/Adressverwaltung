package de.gkjava.addr.view;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import de.gkjava.addr.controller.Controller;

/**
 * @author  vmadmin
 */
@SuppressWarnings("serial")
public class ViewFrame extends JFrame {
  /**
 * @uml.property  name="controller"
 * @uml.associationEnd  
 */
private Controller controller;
  /**
 * @uml.property  name="toolBar"
 * @uml.associationEnd  
 */
private ViewToolBar toolBar;
  /**
 * @uml.property  name="list"
 * @uml.associationEnd  
 */
private ViewList list;
  /**
 * @uml.property  name="editPanel"
 * @uml.associationEnd  
 */
private ViewEditPanel editPanel;
  /**
 * @uml.property  name="splitPane"
 */
private JSplitPane splitPane;

  public ViewFrame(Controller controller) {
    this.controller = controller;
    build();
  }

  private void build() {
    setTitle(controller.getText("frame.title"));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Container container = getContentPane();

    toolBar = new ViewToolBar(controller);
    container.add(toolBar.getToolBar(), BorderLayout.NORTH);

    list = new ViewList(controller);
    editPanel = new ViewEditPanel(controller);
    editPanel.enable(false);

    splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
        true, new JScrollPane(list.getList()), editPanel
            .getPanel());
    container.add(splitPane, BorderLayout.CENTER);
  }

  /**
 * @return
 * @uml.property  name="list"
 */
public ViewList getList() {
    return list;
  }

  /**
 * @return
 * @uml.property  name="editPanel"
 */
public ViewEditPanel getEditPanel() {
    return editPanel;
  }

  /**
 * @return
 * @uml.property  name="splitPane"
 */
public JSplitPane getSplitPane() {
    return splitPane;
  }
}
