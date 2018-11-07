import pl.zankowski.iextrading4j.api.stocks.Quote;
import pl.zankowski.iextrading4j.client.IEXTradingClient;
import pl.zankowski.iextrading4j.client.rest.request.stocks.QuoteRequestBuilder;

import java.math.BigDecimal;

class Market {
    private IEXTradingClient tradingClient;
    Market() {
        tradingClient = IEXTradingClient.create();
    }

    // Current stock price of company
    // Symbols for companies are listed here:
    // https://iextrading.com/trading/eligible-symbols/
    String getStockPrice(String symbol) {
        BigDecimal price;
        Quote quote = tradingClient.executeRequest(new QuoteRequestBuilder()
                .withSymbol(symbol).build());
        price = quote.getLatestPrice();
        return price.toString();
        }
}

