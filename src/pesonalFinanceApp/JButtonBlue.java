package pesonalFinanceApp;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class JButtonBlue extends JButton {
    JButtonBlue(String str){
        super();
        setFont(new Font("Verdana", Font.BOLD, 11));
        setText(str);
        setForeground(new Color(54,78,89));
        setBackground(new Color(222, 231, 235));
        Border line = new LineBorder(new Color(91,132,150));
        Border margin = new EmptyBorder(3,15,3,15);
        Border compound  = new CompoundBorder(line, margin);
        setBorder(compound);
    }
}
