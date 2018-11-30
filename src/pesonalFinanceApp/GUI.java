package pesonalFinanceApp;

import pl.zankowski.iextrading4j.api.stocks.Chart;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.List;


public class GUI extends JFrame {


    //instance variable bankBalance holds the bank balance of the user when entered
    double bankBalance;

    //instance variable userShares holds the company symbol as a key and the number of shares as it's value in a map
    Map<String, Double> userShares = new HashMap<>();



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
        JLabel bankFieldLabel = new JLabel("Bank balance:");
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
        textPanel.setBackground(Color.LIGHT_GRAY);

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


                try{

                    //formats balance to 2 decimal places
                    String formattedBalance = String.format("%.2f", Double.parseDouble(bankField.getText()));

                    message.setText("Current bank balance has been set to: £" + formattedBalance);

                    bankBalance = Double.parseDouble(formattedBalance);
                }
                catch(Exception n){
                   message.setText("Please provide a numerical value for the Bank Balance.");
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
                        userShares.put(companyField.getText(), Double.parseDouble(sharesField.getText()));
                        message.setText("your shares: " + userShares.toString());
                    }
                    catch (Exception n){
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
        centerInfo.setBackground(Color.LIGHT_GRAY);

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
                    double currentShare = (Double) currentEntry.getValue();

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
                outputData.setText("The total value of your current investments are: £" + String.format("%.2f", total));

            }
        });

        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    //gets current date as string
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    Date date = new Date();


                    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");


                    LocalDate date1 = LocalDate.parse(simpleDateFormat.format(date), dateFormat);
                    LocalDate date2 = LocalDate.parse(dateInput.getText(), dateFormat);

                    //gets the days between the current date and the input date
                    long days = ChronoUnit.DAYS.between(date1, date2);


                    //creates a double variable to hold the total investment value
                    //set as the bank balance the user has entered. If nothing has entered the bank  balance is 0
                    double total = bankBalance;

                    //iterator created to iterate through each entry set in the map of companies and shares
                    Iterator it = userShares.entrySet().iterator();

                    //loops until there are no more entry sets in the map
                    while (it.hasNext()) {

                        Map.Entry currentEntry = (Map.Entry) it.next();

                        //sets the company in the current entry set as a String variable and the share amount as a double variable
                        String currentCompany = (String) currentEntry.getKey();
                        double currentShare = (Double) currentEntry.getValue();

                        //sets the current companies stock prices in a list
                        List<Chart> currentCompanyChart = market.getStockPrice(currentCompany);

                        //gets the stock price of the most recent day
                        Chart stockPrice = currentCompanyChart.get((int) Math.abs(days));

                        //sets the value of the shares as a double variable
                        //this is done by multiplying the current share amount by the closing stock price on the most recent day
                        double shareVal = currentShare * stockPrice.getClose().doubleValue();

                        //the value of the users shares in the current company are added to the total
                        total += shareVal;
                    }

                    //center panel prints the current total of the users investments
                    outputData.setText("The total value of your investments on " + dateInput.getText() + " would be: £" + String.format("%.2f", total));

                    dateInput.setText("");

                } catch (Exception e1) {
                    outputData.setText("Please enter a valid date within the last 3 years in the form dd/mm/yyyy.");
                }

            }

        });

    }



}
