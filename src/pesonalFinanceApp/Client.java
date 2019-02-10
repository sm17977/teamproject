package pesonalFinanceApp;

import afu.org.checkerframework.checker.igj.qual.I;
import pl.zankowski.iextrading4j.api.stocks.Chart;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

//Singleton class client to hold all information regarding the client (user)
public class Client implements Serializable {
    private static Client single_client = null;

    public String clientName;
    private String portfolioName;
    public double bankBalance;
    HashMap<String, Double> stocksAmount;
    Map<String, List<Chart>> stocksHistory;
    public static ArrayList<Client> clientProfileList = new ArrayList<>();

    private Client(String clientName, String portfolioName, HashMap<String, Double> stocksAmount, Map<String, List<Chart>> stocksHistory ){
        this.clientName = clientName;
        this.portfolioName = portfolioName;
        this.stocksAmount = stocksAmount;
        this.stocksHistory = stocksHistory;
        clientProfileList.add(this);
        System.out.println("Client created... -> Name: " + clientName + " -- Portfolio Name: " + portfolioName);
        for(Client c : clientProfileList){
            System.out.println(c.toString());
        }
    }




    public static Client getInstance(String clientName, String portfolioName, HashMap<String, Double> stocksAmount, Map<String, List<Chart>> stocksHistory ){
        if(single_client == null){
            single_client = new Client(clientName, portfolioName, stocksAmount, stocksHistory);

        }
        return single_client;
    }
    public String toString(){
        return clientName;
    }

    public void resetClient(){
        single_client = null;
    }

}


