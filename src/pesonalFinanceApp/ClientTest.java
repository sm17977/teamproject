package pesonalFinanceApp;

import org.junit.Test;
import java.util.HashMap;


import static org.junit.Assert.*;


public class ClientTest {

    Client client = new Client("test", "myStocks", new HashMap<>(), new HashMap<>());

    @Test
    public void getInstance() {
        Client instance = Client.getInstance("test", "myStocks", new HashMap<>(), new HashMap<>());
        assertEquals(client.clientName, instance.clientName);
        assertEquals(client.stocksAmount, instance.stocksAmount);
        assertEquals(client.stocksHistory, instance.stocksHistory);
    }

    @Test
    public void resetClient() {
    }
}