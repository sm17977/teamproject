package pesonalFinanceApp;

import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {

    public static void main(String[] args){
        GUI frame = new GUI();
    }

    public GUI(){

        JPanel top = new JPanel();
        JPanel centerInfo = new JPanel();
        JPanel bottom = new JPanel();

        JButton currentValue = new JButton("Total current value of investments");
        JButton search = new JButton("Search");
        JLabel dateLabel = new JLabel("Please input a date:");
        JTextField dateInput = new JTextField(10);
        JLabel outputData = new JLabel("DATA WILL BE OUTPUT HERE");
        JLabel clientName = new JLabel("CLIENTS NAME");

        top.add(clientName);
        top.setBackground(Color.white);

        outputData.setVerticalAlignment(JLabel.CENTER);
        outputData.setHorizontalAlignment(JLabel.CENTER);
        centerInfo.add(outputData);
        centerInfo.setBorder(BorderFactory.createEmptyBorder(200,50,200,50));
        centerInfo.setBackground(Color.gray);

        bottom.add(currentValue);
        bottom.add(dateLabel);
        bottom.add(dateInput);
        bottom.add(search);
        bottom.setBackground(Color.white);

        add(top, BorderLayout.NORTH);
        add(centerInfo, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);
        setSize(1000,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
    }







}
