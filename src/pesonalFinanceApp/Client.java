package pesonalFinanceApp;

/*
PENDING - UNUSED IMPORT STATEMENT
import afu.org.checkerframework.checker.igj.qual.I;
*/

import pl.zankowski.iextrading4j.api.stocks.Chart;

import java.io.Serializable;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/*
PENDING - UNUSED IMPORT STATEMENT
import java.util.stream.Collectors;
*/

// -- Singleton class client to hold all information regarding the client (user) --
public class Client implements Serializable {
    private static Client single_client = null;
    static final long serialVersionUID = 1L;

    public String clientName;
    private String portfolioName;
    public double bankBalance;
    HashMap<String, Double> stocksAmount;
    Map<String, List<Chart>> stocksHistory;
    public static ArrayList<Client> clientProfileList = new ArrayList<>();

    protected Client(String clientName, String portfolioName, HashMap<String, Double> stocksAmount, Map<String, List<Chart>> stocksHistory){
        this.clientName = clientName;
        this.portfolioName = portfolioName;
        this.stocksAmount = stocksAmount;
        this.stocksHistory = stocksHistory;
        clientProfileList.add(this);
        System.out.println("-- New client portfolio created --" + "\n" +
                "Client Name: " + clientName + "\n" +
                "Portfolio Name: " + portfolioName + "\n");
        System.out.println("-- Current client portfolios -- ");
        for(Client c : clientProfileList){
            System.out.println(c.toString());
        }
        /*
        PENDING - Space for legibility. Is it unnecessary?
         */
        System.out.println();
    }

    public static Client getInstance(String clientName, String portfolioName, HashMap<String, Double> stocksAmount, Map<String, List<Chart>> stocksHistory ){
        if(single_client == null) {
            single_client = new Client(clientName, portfolioName, stocksAmount, stocksHistory);
        }
        return single_client;
    }

    public String toString() {
        return clientName;
    }

    public void resetClient() {
        single_client = null;
    }
}
