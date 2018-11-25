package pesonalFinanceApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class GUI extends JFrame {
    //Each JPanel is a separate tab
    JPanel showDataTab;
    JPanel inputInfoTab;
    JTabbedPane tabPane;

    //JPanels for the inputInfo tab
    JPanel titlePanel;
    JPanel textPanel;
    JPanel inputInfoPanel;

    //component for title panel
    JLabel inputInfoTitle;

    //component for message panel
    JLabel message;

    //components for inputInfoPanel
    JLabel bankFieldLabel;
    JTextField bankField;
    JButton bankButton;
    JLabel companyFieldLabel;
    JTextField companyField;
    JLabel sharesFieldLabel;
    JTextField sharesField;
    JButton companySharesButton;

    //JPanels for the data tab
    JPanel top;
    JPanel centerInfo;
    JPanel bottom;
    //components for the data tab
    JButton currentValue;
    JButton search;
    JLabel dateLabel;
    JTextField dateInput;
    JLabel clientName;
    JTable data;
    String[] columnNames = { "Date", "Bank Balance", "Shares",  "Sold Share", "Bought Share" };
    CSV csv;

    //instance variable bankBalance holds the bank balance of the user when entered
    int bankBalance;

    //instance variable userShares holds the company symbol as a key and the number of shares as it's value in a map
    Map<String, Integer> userShares = new HashMap<>();


    public GUI(){



        //Each JPanel is a separate tab
        showDataTab = new JPanel(new BorderLayout());
        inputInfoTab = new JPanel(new BorderLayout());

       tabPane = new JTabbedPane();

        //adding the tabs to the tabbed pane
        tabPane.add("Input", inputInfoTab);
        tabPane.add("Data", showDataTab);

//FIRST TAB:

        //JPanels for the inputInfo tab
        titlePanel = new JPanel();
        textPanel = new JPanel(new BorderLayout());
        inputInfoPanel = new JPanel();

        //component for title panel
        inputInfoTitle = new JLabel("Please input your bank balance, the companies you have shares in and the amount of shares you have in each company");
        inputInfoTitle.setFont(inputInfoTitle.getFont().deriveFont(Font.BOLD, 15f));

        //component for message panel
        message = new JLabel("");
        message.setFont(message.getFont().deriveFont(15f) );

        //components for inputInfoPanel
        bankFieldLabel = new JLabel("Money in the Bank:");
        bankField = new JTextField(10);
        bankButton = new JButton("set balance");
        companyFieldLabel = new JLabel("Company:");
        companyField = new JTextField(10);
        sharesFieldLabel = new JLabel("Shares:");
        sharesField = new JTextField(10);
        companySharesButton = new JButton("add to your company shares");

        //adding components to titlePanel
        titlePanel.add(inputInfoTitle);

        //adding components to textPanel
        textPanel.add(message, BorderLayout.CENTER);
        message.setHorizontalAlignment(SwingConstants.CENTER);

        //adding components to inputInfoPanel
        inputInfoPanel.add(bankFieldLabel);
        inputInfoPanel.add(bankField);
        inputInfoPanel.add(bankButton);
        inputInfoPanel.add(companyFieldLabel);
        inputInfoPanel.add(companyField);
        inputInfoPanel.add(sharesFieldLabel);
        inputInfoPanel.add(sharesField);
        inputInfoPanel.add(companySharesButton);

        //adding JPanels to the tab
        inputInfoTab.add(titlePanel, BorderLayout.NORTH);
        inputInfoTab.add(textPanel, BorderLayout.CENTER);
        inputInfoTab.add(inputInfoPanel, BorderLayout.SOUTH);

        //bankButton action listener using inner class
        //sets the message JLabel to show an appropriate message
        //sets the instance variable to the users input in the bankField text field
        bankButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                message.setText("your bank balance has been set to " + bankField.getText());
                bankBalance = Integer.parseInt(bankField.getText());
                bankField.setText("");
            }
        });


        //companySharesButton action listener using inner class
        //sets the message JLabel to show an appropriate message
        //creates a new entry for the userShares instance variable, using the company as the key and shares as the value
        companySharesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(userShares.size() <= 4){

                    userShares.put(companyField.getText(), Integer.parseInt(sharesField.getText()));
                    message.setText("your shares: " + userShares.toString());
                    companyField.setText("");
                    sharesField.setText("");

                }

                else{
                    message.setText("you can only enter up to 5 companies. Your list:" + userShares.toString());
                    companyField.setText("");
                    sharesField.setText("");
                }
            }
        });






//SECOND TAB:

        //JPanels for the data tab
        top = new JPanel();
        centerInfo = new JPanel();
        bottom = new JPanel();
        //components for the data tab
        currentValue = new JButton("Total current value of investments");
        search = new JButton("Search");
        dateLabel = new JLabel("Please input a date:");
        dateInput = new JTextField(10);
        clientName = new JLabel("CLIENTS NAME");

        csv = new CSV();    //creating class object
        csv.lines();    //call to method counting lines in file
        String[][] lines = new String[csv.tot_lines][0];    //initialising new 2D array with length equal to lines in file
        lines = csv.read(lines);    //setting array to return value of method

        top.add(clientName);
        top.setBackground(Color.white);

        centerInfo.setBorder(BorderFactory.createEmptyBorder(200,50,200,50));
        centerInfo.setBackground(Color.gray);

        //Creation of table holding values read by file
        data = new JTable(lines, columnNames);
        data.setBounds(30, 40, 200, 300);   //size of table set
        JScrollPane sp = new JScrollPane(data);     //scroll pane created to the new table
        centerInfo.add(sp);     //scroll pane added to centre frame in order to display table

        bottom.add(currentValue);
        bottom.add(dateLabel);
        bottom.add(dateInput);
        bottom.add(search);
        bottom.setBackground(Color.white);

        showDataTab.add(top, BorderLayout.NORTH);
        showDataTab.add(centerInfo, BorderLayout.CENTER);
        showDataTab.add(bottom, BorderLayout.SOUTH);

        add(tabPane);
        setSize(1000,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        //setResizable(false);                              ****test purposes****
    }

    public static void main(String[] args){
        GUI frame = new GUI();
    }
}


//Class reads text file and counts its lines
class CSV {
    //Components initialised
    String file = "M:\\teamproject\\csv.txt";   //specifying location of file
    public String line = "";    //empty string to hold next line in file
    public int tot_lines = 0;   //holds number of lines in file
    public static final String DELIMITER = ", ";    //Delimeter specified to seperate values
    BufferedReader main;    //Buffered readers declared to iterate through file
    BufferedReader counter;

    //Initialising both buffered readers to current file
    public CSV(){
        //error checking done if file cannot be read
        try {
            main = new BufferedReader(new FileReader(file));
            counter = new BufferedReader(new FileReader(file));
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    //Counting lines in the file
    public void lines(){
        //error checking done if file cannot be read
        try {
            //loop through each line in which the next line can be ammended to the temporary string
            while ((line = counter.readLine()) != null){
                tot_lines += 1;     //variable incremented each line
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    //Return 2D array with values read from file
    public String[][] read(String[][] arr){
        int i = 0;      //incrementing variable
        lines();        //call to previous method sets correct numeric value
        String[][] parse = new String[tot_lines][0];    //new 2D array created with length equal to lines in file

        //error checking done if file cannot be read
        try {
            //loop through each line in which the next line can be ammended to the temporary string
            while ((line = main.readLine()) != null){
                String[] fields = line.split(DELIMITER);    //create an array which holds elements of the file seperated by delimeter
                parse[i] = fields;  //2D array ammended
                i++;
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        //value of 2D array ammended and returned
        arr = parse;
        return arr;
    }
}
