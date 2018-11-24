package pesonalFinanceApp;

import pl.zankowski.iextrading4j.api.refdata.ExchangeSymbol;
import pl.zankowski.iextrading4j.api.stocks.Chart;
import pl.zankowski.iextrading4j.api.stocks.ChartRange;
import pl.zankowski.iextrading4j.client.IEXTradingClient;
import pl.zankowski.iextrading4j.client.rest.request.refdata.SymbolsRequestBuilder;
import pl.zankowski.iextrading4j.client.rest.request.stocks.*;

import java.util.List;


class Market {
    final private IEXTradingClient tradingClient = IEXTradingClient.create();

    Market() {
    }
    // Current stock price of company
    // Symbols for companies are listed here:
    // https://iextrading.com/trading/eligible-symbols/

    // List of quotes for company for last 5 years
    List<Chart> getStockPrice(String symbol) {
        final List<Chart> chartList = tradingClient.executeRequest(new ChartRequestBuilder()
                .withChartRange(ChartRange.FIVE_YEARS)
                .withSymbol(symbol)
                .build());
        return chartList;
    }

}

