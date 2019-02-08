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
        System.out.println("Client created\nName: " + clientName + " -- Portfolio Name: " + portfolioName);


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

    public static Client[] toArray(ArrayList<Client> arrayList){
        Client[] clientArray = arrayList.toArray(new Client[arrayList.size()]);
        return clientArray;
    }

    public void saveProfile(Client c) throws IOException {
        clientProfileList.add(c);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("profiles.bin"));
        objectOutputStream.writeObject(clientProfileList);

    }
    public Client loadProfile(String clientName) throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("profiles.bin"));
        ArrayList<Client> loadedProfiles = (ArrayList<Client>) objectInputStream.readObject();
        for(Client c : loadedProfiles){
            System.out.println(c.clientName);
            if(c.clientName.equals(clientName) ){
                System.out.println("Loaded " + c.clientName + " with a balance of: " + c.bankBalance + " and " + c.stocksAmount.toString());

                return c;
            }
            else{
                System.out.println("Couldn't load profile for " + clientName);
            }
        }
        return null;
    }
    public void resetClient(){
        single_client = null;
    }

}


