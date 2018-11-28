package pesonalFinanceApp;

import afu.org.checkerframework.checker.igj.qual.I;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;

//Class client to hold all information regarding the client (user)
public class Client {
    //Client bank balance
    private int bankBalance;
    //Client Total Value of all shares the client holds
    private int totalShares;
    //Map to hold the shares and quantity of shares the client holds
    private Map<String, Integer> userShares;
    //Count of unique client shares
    private int shareQuantity = 0;



    public Client(){

        userShares = new HashMap<>();
        //Write write = new Write();

    }


    //Print the map in a nice format
   public static <K, V> String printMap (Map<String, Integer> map){
        String result = "";
        Iterator<Map.Entry<String, Integer>> it = map.entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry<String, Integer> entry = it.next();
            result += "Company: " + entry.getKey() + " - Share Quantity: " + entry.getValue() + "\n";
        }

        return result;
   }

    //Method to retrieve bank balance
    int getBalance (){
    return this.bankBalance;
    }

    //Sets the bank balance from the user input
    void setBalance(int balance){
        this.bankBalance = balance;
    }

    Map<String, Integer> getUserShares(){
        return this.userShares;
    }

    void setUserShares(String companyName, int shareQuantity){
        this.userShares.put(companyName, shareQuantity);

    }

    void setShareQuantity(int shareQuantity){
        this.shareQuantity = shareQuantity;
    }

    int getShareQuantity(){
        return this.shareQuantity;
    }



    int getShareCount(){
        return userShares.size();

    }




}
