import pl.zankowski.iextrading4j.api.stocks.Quote;
import pl.zankowski.iextrading4j.client.IEXTradingClient;
import pl.zankowski.iextrading4j.client.rest.request.stocks.QuoteRequestBuilder;

public class Market {
    private IEXTradingClient tradingClient;
    public Market() {
        tradingClient = IEXTradingClient.create();
    }

    public void requestStock(String symbol) {
        Quote quote = tradingClient.executeRequest(new QuoteRequestBuilder()
                                                    .withSymbol(symbol).build());
        System.out.println(quote.getLatestPrice());
        }
}

