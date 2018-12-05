package pesonalFinanceApp;

import org.jfree.data.time.Day;
import pl.zankowski.iextrading4j.api.stocks.Chart;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.List;
import static java.lang.Math.toIntExact;



public class GUI extends JFrame {

    Market market;
    double bankBalance = 0;
    Charts chart = new Charts();

    //instance variable userShares holds the company symbol as a key and the number of shares as it's value in a map
    Map<String, Double> userShares = new HashMap<>();
    Map<String, List<Chart>> stockMap = new HashMap<>();

    // Second Tab
    JFormattedTextField dateInput;
    JLabel outputData;

    public GUI(){
        market = new Market();


        // -- Set icon --
        File f = new File("images\\growth.png");
        ImageIcon img = new ImageIcon(f.getAbsolutePath());
        setIconImage(img.getImage());

        // -- Set window title --
        setTitle("Portfolio Manager");

        // -- Two tabs --
        JPanel showDataTab = new JPanel(new BorderLayout());
        JPanel inputInfoTab = new JPanel(new BorderLayout());

        JTabbedPane tabPane = new JTabbedPane();

        //adding the tabs to the tabbed pane
        tabPane.add("Input", inputInfoTab);
        tabPane.add("Data", showDataTab);

        // --- FIRST TAB ---

        //JPanels for the inputInfo tab
        JPanel titlePanel = new JPanel();
        JPanel textPanel = new JPanel(new BorderLayout());
        JPanel inputInfoPanel = new JPanel();

        //component for title panel
        JLabel inputInfoTitle = new JLabel("Input bank balance, company and amount of stock owned.");
        inputInfoTitle.setFont(inputInfoTitle.getFont().deriveFont(Font.BOLD, 15f));

        //component for message panel
        JLabel message = new JLabel("");
        message.setFont(message.getFont().deriveFont(15f) );

        //components for inputInfoPanel
        JLabel bankFieldLabel = new JLabel("Bank balance:");
        JTextField bankField = new JTextFieldBlue(10);
        JButton bankButton = new JButtonBlue("Set balance");
        JLabel companyFieldLabel = new JLabel("Company:");
        JTextField companyField = new JTextFieldBlue(10);
        JLabel sharesFieldLabel = new JLabel("Stock:");
        JTextField sharesField = new JTextFieldBlue(10);
        JButton companySharesButton = new JButtonBlue("Add stock");

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
                        String company_symbol = companyField.getText();
                        Double share_amount = Double.parseDouble(sharesField.getText());

                        userShares.put(company_symbol, share_amount);

                        List<Chart> stock_list = market.getStockPrice(company_symbol);
                        stockMap.put(company_symbol, stock_list);

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

        // --- SECOND TAB ---

        //JPanels for the data tab
        JPanel top = new JPanelBlue();
        JPanel centerInfo = new JPanelBlue();
        JPanel bottom = new JPanelBlue();

        //components for the data tab
        JButton stockValue = new JButtonBlue("Stock Value");
        JButton currentValue = new JButtonBlue("Portfolio Value");
        JButton search = new JButtonBlue("Search"); // Unnecessary

        // Label for TextField for input
        JLabel dateLabel = new JLabel("Input date:");
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        dateInput = new JFormattedTextField(format);
        restyleFormattedTextField(dateInput);
        dateInput.setColumns(10);

        outputData = new JLabel("DATA WILL BE OUTPUT HERE");
        JLabel clientName = new JLabel("CLIENTS NAME");

        top.add(clientName);
        top.setBackground(Color.white);

        centerInfo.add(chart);
        centerInfo.setBackground(Color.LIGHT_GRAY);

        bottom.add(stockValue);
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

        // Portfolio Value Handler
        CurrentValueHandler currentValueH = new CurrentValueHandler(this);
        currentValue.addActionListener(currentValueH);

        // Stock Value Handler
        StockValueHandler stockValueH = new StockValueHandler(this);
        stockValue.addActionListener(stockValueH);

        // Search Handler
        SearchHandler searchH = new SearchHandler(this);
        search.addActionListener(searchH);

    }

    // -- Helper function (for textfield in second tab) --
    private void restyleFormattedTextField(JFormattedTextField f) {
        setBackground(new Color(246,249,250));
        Border line = new LineBorder(new Color(91,132,150));
        Border margin = new EmptyBorder(3,5,2,5);
        Border compound  = new CompoundBorder(line, margin);
        f.setBorder(compound);
    }

}

// Second Tab : Search Button (Obsolete)
class SearchHandler implements ActionListener {
    GUI app;

    SearchHandler(GUI app) {
        this.app = app;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            //gets current date as string
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = new Date();

            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            LocalDate date1 = LocalDate.parse(simpleDateFormat.format(date), dateFormat);
            LocalDate date2 = LocalDate.parse(app.dateInput.getText(), dateFormat);

            // Get the days between the current date and the input date
            int days = toIntExact(ChronoUnit.DAYS.between(date1, date2));

            //creates a double variable to hold the total investment value
            //set as the bank balance the user has entered. If nothing has entered the bank  balance is 0
            double total = app.bankBalance;

            //iterator created to iterate through each entry set in the map of companies and shares
            Iterator it = app.userShares.entrySet().iterator();

            //loops until there are no more entry sets in the map
            while (it.hasNext()) {

                Map.Entry currentEntry = (Map.Entry) it.next();

                //sets the company in the current entry set as a String variable and the share amount as a double variable
                String currentCompany = (String) currentEntry.getKey();
                double currentShare = (Double) currentEntry.getValue();

                //sets the current companies stock prices in a list
                List<Chart> currentCompanyChart = app.market.getStockPrice(currentCompany);

                //gets the stock price of the most recent day
                Chart stockPrice = currentCompanyChart.get((int) Math.abs(days));

                //sets the value of the shares as a double variable
                //this is done by multiplying the current share amount by the closing stock price on the most recent day
                double shareVal = currentShare * stockPrice.getClose().doubleValue();

                //the value of the users shares in the current company are added to the total
                total += shareVal;
            }

            //center panel prints the current total of the users investments
            app.outputData.setText("The total value of your investments on " + app.dateInput.getText() + " would be: £" + String.format("%.2f", total));

            app.dateInput.setText("");

        } catch (Exception e1) {
            app.outputData.setText("Please enter a valid date within the last 3 years in the form dd/mm/yyyy.");
        }
    }
}

// Second Tab : Button Portfolio Value
class CurrentValueHandler implements ActionListener {
    GUI app;

    CurrentValueHandler(GUI app) {
        this.app = app;
    }

    public void actionPerformed(ActionEvent e) {
        Map<String, Double> portfolio = new LinkedHashMap<>();

        if(!app.stockMap.isEmpty()) {
            app.stockMap.forEach((String company, List<Chart> list) -> {

                for (int i = list.size() - 1; i > 0; i--) {
                    String currentDay = list.get(i).getDate();

                    Double shareCount = app.userShares.get(company);
                    Double sharePrice = list.get(i).getClose().doubleValue();
                    Double shareTotal = shareCount * sharePrice;

                    if (portfolio.containsKey(currentDay)) {
                        portfolio.put(currentDay, portfolio.get(currentDay) + shareTotal);
                    } else {
                        portfolio.put(currentDay, app.bankBalance + shareTotal);
                    }
                }

            });
            int days = 30;

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = new Date();

            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            LocalDate currentDate = LocalDate.parse(simpleDateFormat.format(date), dateFormat);
            LocalDate inputDate = LocalDate.parse(app.dateInput.getText(), dateFormat);

            // Get the days between the current date and the input date
            days = toIntExact(ChronoUnit.DAYS.between(inputDate, currentDate));

            app.chart.removeAll();
            app.chart.updateChart(portfolio, days);
            app.revalidate();
        }
    }
}

// Second Tab : Stock Value Button
class StockValueHandler implements ActionListener {
    GUI app;

    StockValueHandler(GUI app) {
        this.app = app;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(!app.stockMap.isEmpty()) {
            int days = 30;

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = new Date();

            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            LocalDate currentDate = LocalDate.parse(simpleDateFormat.format(date), dateFormat);
            LocalDate inputDate = LocalDate.parse(app.dateInput.getText(), dateFormat);

            // Get the days between the current date and the input date
            days = toIntExact(ChronoUnit.DAYS.between(inputDate, currentDate));

            app.chart.removeAll();
            app.chart.updateChart(app.stockMap, days, 0);
            app.revalidate();
        }
    }
}






