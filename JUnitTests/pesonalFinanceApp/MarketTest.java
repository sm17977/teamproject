package pesonalFinanceApp;

import org.junit.Test;
import pl.zankowski.iextrading4j.api.stocks.Chart;
import pl.zankowski.iextrading4j.api.stocks.ChartRange;
import pl.zankowski.iextrading4j.client.IEXTradingClient;
import pl.zankowski.iextrading4j.client.rest.request.stocks.ChartRequestBuilder;

import java.util.List;

import static org.junit.Assert.*;

public class MarketTest {

    final private IEXTradingClient tradingClient = IEXTradingClient.create();
    MarketTest() { }
    @Test
    List<Chart> getStockPrice(String symbol) {
        final List<Chart> chartList = tradingClient.executeRequest(new ChartRequestBuilder()
                .withChartRange(ChartRange.FIVE_YEARS)
                .withSymbol(symbol)
                .build());
        return chartList;
    }
}