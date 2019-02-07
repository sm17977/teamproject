package pesonalFinanceApp;

import pl.zankowski.iextrading4j.api.stocks.Chart;
import pl.zankowski.iextrading4j.api.exception.IEXTradingException;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
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
import com.github.lgooddatepicker.components.DatePicker;
import net.miginfocom.swing.MigLayout;

import static java.lang.Math.toIntExact;


public class GUI extends JFrame {

    // Blue theme of the program, use these when colouring components
    public static final Color primary_color = new Color(103,121,150);
    public static final Color secondary_color = new Color(61,86,125);
    public static final Color text_color = new Color(232,231,237);
    public static final Color border_color = new Color(45, 59, 90);
    public static final Color field_color = new Color(232,231,237);

    Market market;
    double bankBalance = 0;
    Charts chart = new Charts();
    CompanyData companyData = new CompanyData("NASDAQ.csv", "NYSE.csv");

    // Respective stock amount and its historical data.
    // Eg.: IBM - 30, IBM - (Day1, Day2...)
    Map<String, Double> stocksAmount = new HashMap<>();
    Map<String, List<Chart>> stocksHistory = new HashMap<>();

    // Second Tab

    DatePicker dateInput = new DatePicker();
    JLabelBlue dataOutput;

    public GUI(){
        market = new Market();
        getContentPane().setBackground(Color.WHITE);

        // -- Set icon --
        File f = new File("images\\growth.png");
        ImageIcon img = new ImageIcon(f.getAbsolutePath());
        setIconImage(img.getImage());

        // -- Set window title --
        setTitle("Portfolio Manager");

        // -- Two tabs --
        JPanel showDataTab = new JPanel(new BorderLayout());
        JPanel inputInfoTab = new JPanel(new BorderLayout());

        // -- Tab pane / styling --
        JTabbedPane tabPane = new JTabbedPane();
        UIManager.put("TabbedPane.contentBorderInsets", new Insets(0, 0, 0, 0));
        UIManager.put("TabbedPane.selected", secondary_color);
        tabPane.setUI(tabUIStyle);
        tabPane.setBackground(primary_color);
        tabPane.setForeground(text_color);

        // adding the tabs to the tabbed pane
        tabPane.add("Input", inputInfoTab);
        tabPane.add("Data", showDataTab);

        // --- FIRST TAB ---

        //JPanels for the inputInfo tab
        JPanel titlePanel = new JPanelBlue();
        JPanel textPanel = new JPanelBlue(new GridBagLayout());
        JPanel inputInfoPanel = new JPanelBlue(new BorderLayout());
        JPanel clientStartPanel = new JPanel();
        JPanel clientAddPanel = new JPanel();
        JPanel clientLoadPanel = new JPanel();

        //component for title panel
        JLabel inputInfoTitle = new JLabelBlue("Input bank balance, company and amount of stock owned.");
        inputInfoTitle.setFont(inputInfoTitle.getFont().deriveFont(Font.BOLD, 15f));

        //component for message panel
        JLabel message = new JLabelBlue("");
        message.setFont(message.getFont().deriveFont(15f) );

        //components for inputInfoPanel
        JButton newClientButton = new JButtonBlue("New Client");
        JButton loadButton = new JButtonBlue("Load Data");
        JButtonBlue backButton = new JButtonBlue("Back");
        JLabel bankFieldLabel = new JLabelBlue("Bank balance:");
        JTextField bankField = new JTextFieldBlue(10);
        JButton bankButton = new JButtonBlue("Set balance");
        JLabel companyFieldLabel = new JLabelBlue("Company:");
        Java2sAutoComboBox companyField;
        JLabel sharesFieldLabel = new JLabelBlue("Stock:");
        JTextField sharesField = new JTextFieldBlue(10);
        JButton companySharesButton = new JButtonBlue("Add stock");
        JLabel clientStartTitle = new JLabel("Portfolio Manager");
        JLabel newClientTitle = new JLabel("Create new Portfolio");

        // populate company suggestion field
        ArrayList<String> suggestionWords = new ArrayList<>();
        suggestionWords = companyData.getCompanies();
        companyField = new Java2sAutoComboBox(suggestionWords);
        companyField.setStrict(false);

        // adding components to titlePanel
        titlePanel.add(inputInfoTitle);

        // --adding components to clientStartPanel--
        CardLayout cardLayout = new CardLayout();
        // panel to contain cards
        JPanel switchPanel = new JPanel(cardLayout);
        CardSwitcher cardSwitcher = new CardSwitcher(switchPanel, cardLayout);


        // clientStartPanel components (1/3)
        Font font = new Font("Segoe UI", Font.PLAIN,40);
        clientStartPanel.setLayout(new MigLayout("fillx", "[center]", "40[]30[]20"));
        clientStartTitle.setFont(font);
        clientStartPanel.setBackground(Color.WHITE);
        clientStartPanel.add(clientStartTitle, "cell 0 0, al center, span");
        clientStartPanel.add(newClientButton, "cell 0 1, al center, span");
        clientStartPanel.add(loadButton, "cell 0 2, al center, span");
        newClientButton.setPreferredSize(new Dimension(300, 30));
        loadButton.setPreferredSize(new Dimension(300, 30));
        newClientButton.addActionListener(new NewClientHandler(1, cardSwitcher));

        // clientAddPanel components (2/3)
        clientAddPanel.setLayout(new MigLayout("fillx", "[center]", "10[]25[]20"));
        newClientTitle.setFont(font.deriveFont(26.0f));
        clientAddPanel.setBackground(Color.WHITE);
        clientAddPanel.add(backButton, "cell 0 0 , al left");
        clientAddPanel.add(newClientTitle, "cell 0 1, al center");
        backButton.addActionListener(new NewClientHandler(2, cardSwitcher));

        // loadClientPanel components (3/3)



        // adding components to textPanel
        switchPanel.add(clientStartPanel, "1");
        switchPanel.add(clientAddPanel, "2");
        textPanel.setLayout(new MigLayout("fillx, debug", "[center]", "25[center]"));
        textPanel.setBackground(Color.LIGHT_GRAY);
        textPanel.add(switchPanel, " cell 0 0, width 400!, height 300!, al center");


        JPanelBlue inputInfoPanelRow1 = new JPanelBlue();
        JPanelBlue inputInfoPanelRow2 = new JPanelBlue();


        // adding components to inputInfoPanel

        inputInfoPanelRow1.add(bankFieldLabel);
        inputInfoPanelRow1.add(bankField);
        inputInfoPanelRow1.add(bankButton);

        inputInfoPanelRow2.add(companyFieldLabel);
        inputInfoPanelRow2.add(companyField);
        inputInfoPanelRow2.add(sharesFieldLabel);
        inputInfoPanelRow2.add(sharesField);
        inputInfoPanelRow2.add(companySharesButton);

        inputInfoPanel.add(inputInfoPanelRow1, BorderLayout.NORTH);
        inputInfoPanel.add(inputInfoPanelRow2, BorderLayout.CENTER);

        // adding JPanels to the tab
        inputInfoTab.add(titlePanel, BorderLayout.NORTH);
        inputInfoTab.add(textPanel, BorderLayout.CENTER);
        inputInfoTab.add(inputInfoPanel, BorderLayout.SOUTH);

        // bankButton action listener using inner class
        // sets the message JLabel to show an appropriate message
        // sets the instance variable to the users input in the bankField text field
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
        //creates a new entry for the stocksAmount instance variable, using the company as the key and shares as the value
        companySharesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(stocksAmount.size() <= 4){
                    try {
                        String company_symbol = companyData.getSymbol(companyField.getText());
                        System.out.println(company_symbol);
                        Double share_amount = Double.parseDouble(sharesField.getText());

                        List<Chart> stock_list = market.getStockPrice(company_symbol);
                        stocksAmount.put(company_symbol, share_amount);
                        stocksHistory.put(company_symbol, stock_list);

                        message.setText("Your shares: " + stocksAmount.toString());
                    }
                    catch (IEXTradingException ex) {
                        message.setText("Please provide a valid company name.");
                    }
                    catch (Exception ex){
                        message.setText("Please provide an integer value for the share amount.");
                        System.err.println(ex.toString());
                    }
                    sharesField.setText("");
                }

                else {
                    message.setText("You can only enter up to 5 companies. Your list:" + stocksAmount.toString());
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
        JButton portfolioValue = new JButtonBlue("Portfolio Value");

        // Label for TextField for input
        JLabel dateLabel = new JLabelBlue("Input date:");
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");


        dataOutput = new JLabelBlue("DATA WILL BE OUTPUT HERE");
        JLabel clientName = new JLabelBlue("CLIENTS NAME");

        top.add(clientName);

        centerInfo.add(chart);

        bottom.add(stockValue);
        bottom.add(portfolioValue);
        bottom.add(dateLabel);
        bottom.add(dateInput);

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
        portfolioValue.addActionListener(currentValueH);

        // Stock Value Handler
        StockValueHandler stockValueH = new StockValueHandler(this);
        stockValue.addActionListener(stockValueH);

    }

    // -- Helper function ( for JFormattedTextField in second tab ) --
    private void restyleFormattedTextField(JFormattedTextField f) {
        setBackground(field_color);
        Border line = new LineBorder(border_color);
        Border margin = new EmptyBorder(3,5,2,5);
        Border compound  = new CompoundBorder(line, margin);
        f.setBorder(compound);
    }

    // -- Helper function ( for JTabbedPane ) --
     BasicTabbedPaneUI tabUIStyle =  new BasicTabbedPaneUI() {
        @Override
        protected void installDefaults() {
            super.installDefaults();
            highlight = secondary_color;
            lightHighlight = secondary_color;
            shadow = secondary_color;
            darkShadow = secondary_color;
            focus = secondary_color;
            tabInsets = new Insets(1,35,1,35);
        }
    };

}

// Second Tab : Button Portfolio Value
class CurrentValueHandler implements ActionListener {
    GUI app;

    CurrentValueHandler(GUI app) {
        this.app = app;
    }

    public void actionPerformed(ActionEvent e) {
        Map<String, Double> portfolio = new LinkedHashMap<>();

        if(!app.stocksHistory.isEmpty()) {
            app.stocksHistory.forEach((String company, List<Chart> list) -> {

                for (int i = list.size() - 1; i > 0; i--) {
                    String currentDay = list.get(i).getDate();

                    Double shareCount = app.stocksAmount.get(company);
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

            SimpleDateFormat inputDf = new SimpleDateFormat("d MMMMM yyyy");
            SimpleDateFormat outputDf = new SimpleDateFormat("dd/MM/yyyy");

            try {
                // Input date
                Date dateFormatted = inputDf.parse(app.dateInput.getText());
                String inputFormatted = outputDf.format(dateFormatted);

                // Current date
                Date today = new Date();
                String todayFormatted = outputDf.format(today);

                DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate inputDate = LocalDate.parse(inputFormatted, df);
                LocalDate currentDate = LocalDate.parse(todayFormatted, df);

                // Get the days between the current date and the input date
                days = toIntExact(ChronoUnit.DAYS.between(inputDate, currentDate));
            }
            catch (Exception ex) {}


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
        if(!app.stocksHistory.isEmpty()) {
            // Default days: 30
            int days = 30;

            SimpleDateFormat inputDf = new SimpleDateFormat("d MMMMM yyyy");
            SimpleDateFormat outputDf = new SimpleDateFormat("dd/MM/yyyy");

            try {
                // Input date
                Date dateFormatted = inputDf.parse(app.dateInput.getText());
                String inputFormatted = outputDf.format(dateFormatted);

                // Current date
                Date today = new Date();
                String todayFormatted = outputDf.format(today);

                DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate inputDate = LocalDate.parse(inputFormatted, df);
                LocalDate currentDate = LocalDate.parse(todayFormatted, df);

                // Get the days between the current date and the input date
                days = toIntExact(ChronoUnit.DAYS.between(inputDate, currentDate));
            }
            catch (Exception ex) {}


            app.chart.removeAll();
            app.chart.updateChart(app.stocksHistory, days, 0);
            app.revalidate();
        }
    }
}

class NewClientHandler implements ActionListener {
    int state;
    CardSwitcher switcher;

    public NewClientHandler(int state, CardSwitcher switcher) {
        this.state = state;
        this.switcher = switcher;
    }

    public void actionPerformed(ActionEvent evt) {
        switch (state){
            case 1: switcher.switchTo("2");
                break;
            case 2: switcher.switchTo("1");
                break;
            case 3:
                break;
        }

        //JOptionPane.showInternalInputDialog(null, "text");

    }
}

    class CardSwitcher {
        CardLayout layout;
        Container container;
        public CardSwitcher(Container container, CardLayout layout) {
            this.layout = layout;
            this.container = container;
        }
        public void switchTo(String card) {
            layout.show(container, card);
        }
    }














