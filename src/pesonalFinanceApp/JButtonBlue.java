package pesonalFinanceApp;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

import static pesonalFinanceApp.GUI.border_color;
import static pesonalFinanceApp.GUI.primary_color;
import static pesonalFinanceApp.GUI.text_color;

public class JButtonBlue extends JButton {
    JButtonBlue(String str){
        super();
        setFont(new Font("Verdana", Font.BOLD, 11));
        setText(str);
        setForeground(text_color);
        setBackground(primary_color);
        Border line = new LineBorder(border_color);
        Border margin = new EmptyBorder(3,15,3,15);
        Border compound  = new CompoundBorder(line, margin);
        setBorder(compound);
    }
}
