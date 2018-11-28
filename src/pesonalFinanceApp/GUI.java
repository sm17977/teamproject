package pesonalFinanceApp;

import pl.zankowski.iextrading4j.api.refdata.ExchangeSymbol;
import pl.zankowski.iextrading4j.api.refdata.SymbolType;
import pl.zankowski.iextrading4j.api.stocks.Chart;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;
import java.util.List;

import static pesonalFinanceApp.Client.printMap;

public class GUI extends JFrame {

    public static void main(String[] args){
        GUI frame = new GUI();
        frame.setVisible(true);
    }

    //instance variable bankBalance holds the bank balance of the user when entered
    //instance variable totalShares wil calc the total value of all shares the client holds
    //instance variable userShares holds the company symbol as a key and the number of shares as it's value in a map



    JTable data;
    String[] columnNames = { "Date", "Bank Balance", "Company",  "Share Close Value", "Share Quantity", "Total Share Value" };
    CSV csv;
    public GUI(){

        //Create instance of new client
        Client client = new Client();



        //Each JPanel is a separate tab
        JPanel showDataTab = new JPanel(new BorderLayout());
        JPanel inputInfoTab = new JPanel(new BorderLayout());

        JTabbedPane tabPane = new JTabbedPane();

        //adding the tabs to the tabbed pane
        tabPane.add("Input", inputInfoTab);
        tabPane.add("Data", showDataTab);

        csv = new CSV();
        csv.lines();
        String[][] lines = new String[csv.tot_lines][0];
        lines = csv.read(lines);


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
        JButton companySharesButton = new JButton("Add Share");

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

                message.setText("Your bank balance has been set to £" + bankField.getText());
                try {
                    client.setBalance(Integer.parseInt(bankField.getText()));
                }
                catch(NumberFormatException n){
                    message.setText("Please enter an Integer value for your Bank Balance.");
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


                Market market = new Market(companyField.getText(), Integer.parseInt(sharesField.getText()), Integer.parseInt(bankField.getText()));
                ArrayList symbolList = new ArrayList();
                market.getSymbolList(symbolList);



                //Validate Share count
                if (client.getShareCount() <= 4) {

                    //Validate Symbol
                    if(market.validateSymbol(companyField.getText(), symbolList)){

                        //Set share quantity
                        client.setShareQuantity(Integer.parseInt(sharesField.getText()));

                        //Add Share and Quantity to map
                        client.setUserShares(companyField.getText(), Integer.parseInt(sharesField.getText()));
                        message.setText("<html>" + printMap(client.getUserShares()) + "|" + "<br/></html>");




                        System.out.println(printMap(client.getUserShares()));

                        ArrayList generatedList = new ArrayList<>();
                        market.generateChart(generatedList);

                        Write write = new Write();
                        write.writeToFile(generatedList);

                        //Generate list of share values for current day
                        System.out.println(generatedList.get(generatedList.size() - 1));

                        companyField.setText("");
                        sharesField.setText("");


                    }
                    else{
                        message.setText("Could not find Company. Please use valid IEX Symbol format.");
                    }



                }



                else{
                    message.setText("you can only enter up to 5 companies. Your list:" + client.getUserShares().toString());
                    companyField.setText("");
                    sharesField.setText("");
                }


                //Add code to validate if name/symbol of share is in the iex share list.
                //Maybe change hashmap to <String, List<String>>? to store {shareName, {shareQuantity/totalShareValue/singleShareValue}}?




















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

        data = new JTable(lines, columnNames);
        data.setBounds(30, 40, 200, 300);
        JScrollPane sp = new JScrollPane(data);
        centerInfo.add(sp);

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
        //setResizable(false);
    }
}

class CSV {
    String file = "C:\\Users\\Sean\\Desktop\\Computer Games Y2\\CE291\\teamproject\\csv.txt";
    public Scanner input = new Scanner(System.in);
    public String line = "";
    public int tot_lines = 0;
    public static final String DELIMITER = ", ";
    BufferedReader main;
    BufferedReader counter;

    public CSV(){
        try {
            main = new BufferedReader(new FileReader(file));
            counter = new BufferedReader(new FileReader(file));
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void lines(){
        try {
            while ((line = counter.readLine()) != null){
                tot_lines += 1;
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public String[][] read(String[][] arr){
        int i = 0;
        lines();
        String[][] parse = new String[tot_lines][0];


        try {
        while ((line = main.readLine()) != null){
            String[] fields = line.split(DELIMITER);
            parse[i] = fields;
            i++;
        }
    } catch (IOException e){
        e.printStackTrace();
    }
    arr = parse;
        return arr;
    }
}
