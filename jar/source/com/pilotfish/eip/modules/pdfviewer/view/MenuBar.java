package com.pilotfish.eip.modules.pdfviewer.view;

import com.pilotfish.eip.modules.pdfviewer.PDFViewer;
import com.pilotfish.eip.modules.pdfviewer.PDFViewerHelper;
import com.sun.glass.events.KeyEvent;

import javax.swing.*;
import java.beans.PropertyChangeEvent;

import static com.pilotfish.eip.modules.pdfviewer.view.ActionCommands.*;

/**
 * Created by craigmiller on 2/5/16.
 */
public class MenuBar extends AbstractView {

    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenu exportMenu;

    public MenuBar(PDFViewerHelper helper) {
        super(helper);
        init();
    }

    private void init(){
        menuBar = new JMenuBar();

        fileMenu = createMenu("File", KeyEvent.VK_F);

        JMenuItem newPdfItem = createMenuItem("Open New PDF", "Open a new PDF", LOAD_NEW_PDF_COMMAND);
        JMenuItem exitItem = createMenuItem("Exit", "Exit the program", EXIT_COMMAND);

        fileMenu.add(newPdfItem);
        fileMenu.add(exitItem);

        exportMenu = createMenu("Export", KeyEvent.VK_R);

        JMenuItem listItem = createMenuItem("Export As List", "Export as a list of page numbers and their coordinates", EXPORT_LIST_COMMAND);

        exportMenu.add(listItem);

        menuBar.add(fileMenu);
        menuBar.add(exportMenu);
    }

    private JMenu createMenu(String menuTitle, int mnemonic){
        JMenu menu = new JMenu(menuTitle);
        menu.setMnemonic(mnemonic);
        return menu;
    }

    private JMenuItem createMenuItem(String name, String toolTipText, String actionCommand){
        JMenuItem item = new JMenuItem(name);
        item.setToolTipText(toolTipText);
        item.setActionCommand(actionCommand);
        item.addActionListener(helper.getActionListener(PDFViewer.MENU_BAR_CONTROLLER));

        return item;
    }

    public JMenuBar getMenuBar(){
        return menuBar;
    }

    @Override
    public void updateView(PropertyChangeEvent evt) {

    }
}
