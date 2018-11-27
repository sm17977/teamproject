package pesonalFinanceApp;

import pl.zankowski.iextrading4j.api.refdata.ExchangeSymbol;
import pl.zankowski.iextrading4j.api.refdata.IEXSymbolDirectory;
import pl.zankowski.iextrading4j.api.refdata.SymbolType;
import pl.zankowski.iextrading4j.api.stocks.Chart;
import pl.zankowski.iextrading4j.api.stocks.ChartRange;
import pl.zankowski.iextrading4j.api.stocks.Company;
import pl.zankowski.iextrading4j.client.IEXTradingClient;
import pl.zankowski.iextrading4j.client.rest.request.IRestRequestBuilder;
import pl.zankowski.iextrading4j.client.rest.request.refdata.SymbolsRequestBuilder;
import pl.zankowski.iextrading4j.client.rest.request.stocks.*;

import java.util.ArrayList;
import java.util.List;


class Market {
    final private IEXTradingClient tradingClient = IEXTradingClient.create();
    //List containing all IEX symbols in
    final List<ExchangeSymbol> exchangeSymbolList = tradingClient.executeRequest(new SymbolsRequestBuilder().build());

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

    List<Chart> list = getStockPrice("IBM");

    void getCurrentShareVal() {
        Chart point = list.get(list.size() - 1);
        System.out.println(point.getClose() + " " + point.getDate());
    }

    ArrayList getSymbolList(ArrayList list){
        for(ExchangeSymbol symbol: exchangeSymbolList){
            list.add(symbol.getSymbol());
        }
        return list;
    }



    //Check if user entered Company is an IEX Symbol
    boolean validateSymbol (String symbol, ArrayList list){
        if(!list.contains(symbol)){
            return false;
        }
        else {
            return true;
        }

    }










}


