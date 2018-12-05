package pesonalFinanceApp;

import javax.swing.*;

import static pesonalFinanceApp.GUI.text_color;

public class JLabelBlue extends JLabel {
    JLabelBlue(String str) {
        super();
        setText(str);
        setForeground(text_color);
    }
}
