/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import de.gkjava.addr.controller.Controller;
import java.awt.Component;
import java.io.File;
import javax.swing.JFileChooser;

/**
 *
 * @author niiku
 */
public class GuiUtils {

    private enum DialogType {
        OPEN, SAVE
    };

    private static File fileFromDialog(DialogType type, Component parent) {
        JFileChooser chooser = new JFileChooser();
        int opt = JFileChooser.CANCEL_OPTION;
        switch (type) {
            case SAVE:
                opt = chooser.showSaveDialog(parent);
                break;
            case OPEN:
                opt = chooser.showOpenDialog(parent);
                break;
        }

        if (opt != JFileChooser.APPROVE_OPTION) {
            return null;
        }
        return chooser.getSelectedFile();
    }

    public static File getFileFromSaveDialog(Component parent) {
        return fileFromDialog(DialogType.SAVE, parent);
    }

    public static File getFileFromOpenDialog(Component parent) {
        return fileFromDialog(DialogType.OPEN, parent);
    }
}
