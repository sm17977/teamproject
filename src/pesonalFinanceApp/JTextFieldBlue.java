package pesonalFinanceApp;

import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/*
PENDING - UNUSED IMPORT STATMENT
import java.awt.*;
*/

import static pesonalFinanceApp.GUI.border_color;
import static pesonalFinanceApp.GUI.field_color;

public class JTextFieldBlue extends JTextField {
    JTextFieldBlue(int col) {
        super();
        setColumns(col);
        setBackground(field_color);
        Border line = new LineBorder(border_color);
        Border margin = new EmptyBorder(3,5,2,5);
        Border compound  = new CompoundBorder(line, margin);
        setBorder(compound);
    }
}
