package pesonalFinanceApp;

import pl.zankowski.iextrading4j.api.stocks.Chart;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class GUI extends JFrame {

    public static void main(String[] args){
        GUI frame = new GUI();
    }

    //instance variable bankBalance holds the bank balance of the user when entered
    int bankBalance;

    //instance variable userShares holds the company symbol as a key and the number of shares as it's value in a map
    Map<String, Integer> userShares = new HashMap<>();



    public GUI(){

        Market market = new Market();



        //Each JPanel is a separate tab
        JPanel showDataTab = new JPanel(new BorderLayout());
        JPanel inputInfoTab = new JPanel(new BorderLayout());

        JTabbedPane tabPane = new JTabbedPane();

        //adding the tabs to the tabbed pane
        tabPane.add("Input", inputInfoTab);
        tabPane.add("Data", showDataTab);

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
        JButton bankButton = new JButton("Set balance");
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
                message.setText("Current bank balance has been set to: £" + bankField.getText());

                try{
                    bankBalance = Integer.parseInt(bankField.getText());
                }
                catch(NumberFormatException n){
                   message.setText("Please provide an Integer value for the Bank Balance.");
                }
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
                    try {
                        userShares.put(companyField.getText(), Integer.parseInt(sharesField.getText()));
                        message.setText("your shares: " + userShares.toString());
                    }
                    catch (NumberFormatException n){
                        message.setText("Please provide an Integer value for the Share amount.");
                    }
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
        JPanel top = new JPanel();
        JPanel centerInfo = new JPanel();
        JPanel bottom = new JPanel();
        //components for the data tab
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

        showDataTab.add(top, BorderLayout.NORTH);
        showDataTab.add(centerInfo, BorderLayout.CENTER);
        showDataTab.add(bottom, BorderLayout.SOUTH);

        add(tabPane);
        setSize(1000,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);

        currentValue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //creates a double variable to hold the total investment value
                //set as the bank balance the user has entered. If nothing has entered the bank  balance is 0
                double total = bankBalance;

                //iterator created to iterate through each entry set in the map of companies and shares
                Iterator it = userShares.entrySet().iterator();

                //loops until there are no more entry sets in the map
                while(it.hasNext()){

                    Map.Entry currentEntry = (Map.Entry)it.next();

                    //sets the company in the current entry set as a String variable and the share amount as a double variable
                    String currentCompany = (String)currentEntry.getKey();
                    double currentShare = (Integer)currentEntry.getValue();

                    //sets the current companies stock prices in a list
                    List<Chart> currentCompanyChart = market.getStockPrice(currentCompany);

                    //gets the stock price of the most recent day
                    Chart stockPrice = currentCompanyChart.get(0);

                    //sets the value of the shares as a double variable
                    //this is done by multiplying the current share amount by the closing stock price on the most recent day
                    double shareVal = currentShare * stockPrice.getClose().doubleValue();

                    //the value of the users shares in the current company are added to the total
                    total += shareVal;
                }

                //center panel prints the current total of the users investments
                outputData.setText("The total of your current investments are: £" + String.format("%.2f", total));

            }
        });

    }




}
