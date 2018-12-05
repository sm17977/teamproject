package pesonalFinanceApp;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class JTextFieldBlue extends JTextField {
    JTextFieldBlue(int col){
        super();
        setColumns(col);
        setBackground(new Color(246,249,250));
        Border line = new LineBorder(new Color(91,132,150));
        Border margin = new EmptyBorder(3,5,2,5);
        Border compound  = new CompoundBorder(line, margin);
        setBorder(compound);
    }
}

