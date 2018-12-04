package pesonalFinanceApp;

import pl.zankowski.iextrading4j.api.stocks.Chart;

import javax.swing.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Market instance to retrieve data from
        Market market = new Market();

        // Go to https://iextrading.com/trading/eligible-symbols/
        // to find symbols of any company
        // When calling .getStockPrice() <- insert symbol as a string

        // example: Daily IBM stock for last 5 years

        GUI frame = new GUI();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(3);

    }
}