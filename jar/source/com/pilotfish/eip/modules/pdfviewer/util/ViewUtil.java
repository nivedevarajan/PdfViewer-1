package com.pilotfish.eip.modules.pdfviewer.util;

import com.incors.plaf.alloy.AlloyLookAndFeel;

import javax.swing.*;
import javax.swing.text.DefaultEditorKit;
import java.awt.event.KeyEvent;

/**
 * Created by craigmiller on 2/5/16.
 */
public class ViewUtil {

    public static void initAlloyUI() {
        AlloyLookAndFeel laf = new com.incors.plaf.alloy.AlloyLookAndFeel(new com.incors.plaf.alloy.themes.glass.GlassTheme());
        if(!UIManager.getLookAndFeel().equals(laf)){
            try {
                com.incors.plaf.alloy.AlloyLookAndFeel.setProperty("alloy.licenseCode", "a#PilotFish_Technology#17euvi4#c9pwjw");
                // UIManager.setLookAndFeel(new
                // com.sun.java.swing.plaf.windows.WindowsLookAndFeel());
                UIManager.setLookAndFeel(laf);
                // UIManager.setLookAndFeel(new
                // com.incors.plaf.alloy.AlloyLookAndFeel(new
                // com.incors.plaf.alloy.themes.bedouin.BedouinTheme()));
                // UIManager.setLookAndFeel(new
                // com.incors.plaf.alloy.AlloyLookAndFeel());
                JFrame.setDefaultLookAndFeelDecorated(true);
                JDialog.setDefaultLookAndFeelDecorated(true);

                //initializeKeysForMac();

            } catch (UnsupportedLookAndFeelException e) {
                e.printStackTrace();
            }
        }
    }

    private static void initializeKeysForMac() {
        if (System.getProperty("os.name").contains("Mac OS")) {
            InputMap im = (InputMap) UIManager.get("TextField.focusInputMap");
            initInputMap(im);
            im = (InputMap) UIManager.get("EditorPane.focusInputMap");
            initInputMap(im);
            im = (InputMap) UIManager.get("FormattedTextField.focusInputMap");
            initInputMap(im);
            im = (InputMap) UIManager.get("PasswordField.focusInputMap");
            initInputMap(im);
            im = (InputMap) UIManager.get("TextArea.focusInputMap");
            initInputMap(im);
            im = (InputMap) UIManager.get("TextPane.focusInputMap");
            initInputMap(im);
        }
    }

    private static void initInputMap(InputMap im) {
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.META_DOWN_MASK), DefaultEditorKit.copyAction);
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.META_DOWN_MASK), DefaultEditorKit.pasteAction);
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.META_DOWN_MASK), DefaultEditorKit.cutAction);
    }

}
