package de.gkjava.addr;

import de.gkjava.addr.controller.Controller;
import de.gkjava.addr.model.Model;
import de.gkjava.addr.view.ViewFrame;

public class Main {
  public static void main(String[] args) {
    Model model = new Model();
    final Controller controller = new Controller(model);
    ViewFrame frame = new ViewFrame(controller);

    controller.setFrame(frame);
    controller.load();

    frame.setSize(800, 600);
    frame.setVisible(true);
    frame.getSplitPane().setDividerLocation(0.25);

    // Wird bei Beendigung des Programms ausgeführt
    Runtime.getRuntime().addShutdownHook(new Thread() {
      public void run() {
        controller.exit();
      }
    });
  }
}
