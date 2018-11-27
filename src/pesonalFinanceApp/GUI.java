package pesonalFinanceApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class GUI extends JFrame {

    public static void main(String[] args){
        GUI frame = new GUI();
    }

    //instance variable bankBalance holds the bank balance of the user when entered
    int bankBalance;

    //instance variable userShares holds the company symbol as a key and the number of shares as it's value in a map
    Map<String, Integer> userShares = new HashMap<>();

    //JPanels for the data tab
    JPanel top;
    JPanel centerInfo;
    JPanel bottom;
    //components for the data tab
    JLabel dateLabel;
    JTextField dateInput;
    JLabel clientName;
    JLabel data_dates;

    //implementation of objects needed to run the CSV class
    JTable data;
    String[] columnNames = { "Date", "Bank Balance", "Shares",  "Sold Share", "Bought Share" };
    String [][] lines;
    CSV csv;

    public GUI(){



        //Each JPanel is a separate tab
        JPanel showDataTab = new JPanel(new BorderLayout());
        JPanel inputInfoTab = new JPanel(new BorderLayout());

        JTabbedPane tabPane = new JTabbedPane();

        //adding the tabs to the tabbed pane
        tabPane.add("Input", inputInfoTab);
        tabPane.add("Data", showDataTab);

        csv = new CSV();        //new object of type CSV created
        csv.lines();        //call to method which calculates number of lines in file
        lines = new String[csv.tot_lines][0];        //implement new 2D array equal to length of file
        lines = csv.read(lines);    //call to method which alters the array

//FIRST TAB:

        //JPanels for the inputInfo tab
        JPanel titlePanel = new JPanel();
        JPanel textPanel = new JPanel(new BorderLayout());
        JPanel inputInfoPanel = new JPanel();

        //component for title panel
        JLabel inputInfoTitle = new JLabel("Please input your bank balance, the companies you have shares in and the amount of shares you have in each company");
        inputInfoTitle.setFont(inputInfoTitle.getFont().deriveFont(Font.BOLD, 15f));


        //component for message panel
        JLabel message = new JLabel("");
        message.setFont(message.getFont().deriveFont(15f) );

        //components for inputInfoPanel
        JLabel bankFieldLabel = new JLabel("Money in the Bank:");
        JTextField bankField = new JTextField(10);
        JButton bankButton = new JButton("set balance");
        JLabel companyFieldLabel = new JLabel("Company:");
        JTextField companyField = new JTextField(10);
        JLabel sharesFieldLabel = new JLabel("Shares:");
        JTextField sharesField = new JTextField(10);
        JButton companySharesButton = new JButton("add to your company shares");

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
        JButton currentValue = new JButton("Total current value of investments");
        JButton search = new JButton("Search");
        dateLabel = new JLabel("Please input a date:");
        data_dates = new JLabel("");
        dateInput = new JTextField(10);
        clientName = new JLabel("CLIENTS NAME");

        top.add(clientName);
        top.setBackground(Color.white);
        
        centerInfo.setBorder(BorderFactory.createEmptyBorder(200,50,200,50));
        centerInfo.setBackground(Color.gray);


        //actionlistener added to search button
        search.addActionListener(new ButtonHandler(this, 1));
        currentValue.addActionListener(new ButtonHandler(this, 2));

        data = new JTable(lines, columnNames);      //implementing new JTable with parameters equal to arrays created
        data.setBounds(30, 40, 200, 300);       //shift size of table
        JScrollPane sp = new JScrollPane(data);     //adding the table to a new scroll pane
        centerInfo.add(sp);     //scroll pane added to frame
        centerInfo.add(data_dates, BorderLayout.SOUTH);

        bottom.add(currentValue);
        bottom.add(dateLabel);
        bottom.add(dateInput);
        bottom.add(search);
        bottom.setBackground(Color.white);

        showDataTab.add(top, BorderLayout.NORTH);
        showDataTab.add(centerInfo, BorderLayout.CENTER);
        showDataTab.add(bottom, BorderLayout.SOUTH);

        add(tabPane);
        setSize(1000,1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        //setResizable(false);
    }
}

class ButtonHandler implements ActionListener {
    GUI j;
    int act;

    public ButtonHandler(GUI frame_ref1, int action) {
        this.j = frame_ref1;
        this.act = action;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        Boolean found = false;
        if(this.act == 1){
            for (int i = 0; i < this.j.csv.tot_lines; i++){
                //System.out.println(this.j.lines[i][0] + " " + this.j.dateInput.getText() + "3");
                if (this.j.dateInput.getText().equals(this.j.lines[i][0])) {
                    System.out.println(Arrays.toString(this.j.lines[i]));
                    this.j.data_dates.setText("Found: " + Arrays.toString(this.j.lines[i]));
                    found = true;
                    break;
                }
            }
            if (found == false){
                this.j.data_dates.setText("No Matching date");
                System.out.println("No Matching date");
            }
        }else if (this.act == 2){
            int tot_bank = 0;
            int tot_shares = 0;
            int tot = 0;
            String temp = "";

            for (int i = 0; i < this.j.csv.tot_lines; i++) {
                temp = (this.j.lines[i][1].substring(1));
                tot += Integer.parseInt(temp);
            }
            for (int i = 0; i < this.j.csv.tot_lines; i++) {
                if (this.j.dateInput.getText().equals(this.j.lines[i][0])) {
                    temp = (this.j.lines[i][1].substring(1));
                    tot_bank = Integer.parseInt(temp);
                    temp = (this.j.lines[i][2].substring(1));
                    tot_shares = Integer.parseInt(temp);
                }
            }
            this.j.data_dates.setText("Total in bank is " + tot_bank + "\n Total shares are " + tot_shares +"\n Total investments are " + tot );
        }
    }
}
//class reads from file and appends values found to a 2D array seperated by a delimeter
class CSV {
    String file = "M:\\teamproject\\csv.txt";       //decleration of main .txt file
    public String line = "";        //decleration of string which will contain each line read
    public int tot_lines = 0;       //integer value holds number of lines in the file
    public static final String DELIMITER = ", ";        //delimeter seperates values with the given string
    //creating buffered readers which will hold the text file data
    BufferedReader main;
    BufferedReader counter;

    //implementation of bufered readers which access the file
    public CSV(){
        //error checking occurs if file cannot be opened
        try {
            main = new BufferedReader(new FileReader(file));
            counter = new BufferedReader(new FileReader(file));
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void lines(){

        //error checking occurs if file cannot be opened
        try {
            while ((line = counter.readLine()) != null){
                tot_lines += 1;     //local variable incremented through each line
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public String[][] read(String[][] arr){
        int i = 0;
        lines();    //call to method which changes local variable data
        String[][] parse = new String[tot_lines][0];        //variable used to determine length of 2D array

        //error checking occurs if file cannot be opened
        try {
            while ((line = main.readLine()) != null){
                String[] fields = line.split(DELIMITER);        //new array created through each delimited line
                parse[i] = fields;      //new 2D array given new value of array through each point of iteration
                i++;
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        arr = parse;        //array paramter intake switched to hold the new array created
        return arr;
    }
}