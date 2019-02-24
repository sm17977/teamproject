package pesonalFinanceApp;

import javax.swing.JPanel;

import java.awt.BorderLayout;

import static pesonalFinanceApp.GUI.secondary_color;

public class JPanelBlue extends JPanel {
    JPanelBlue() {
        setBackground(secondary_color);
    }

    JPanelBlue(BorderLayout brd) {
        super(brd);
        setBackground(secondary_color);
    }
}
