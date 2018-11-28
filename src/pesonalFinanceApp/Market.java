package pesonalFinanceApp;

import pl.zankowski.iextrading4j.api.refdata.ExchangeSymbol;
import pl.zankowski.iextrading4j.api.refdata.IEXSymbolDirectory;
import pl.zankowski.iextrading4j.api.refdata.SymbolType;
import pl.zankowski.iextrading4j.api.stocks.Chart;
import pl.zankowski.iextrading4j.api.stocks.ChartRange;
import pl.zankowski.iextrading4j.api.stocks.Company;
import pl.zankowski.iextrading4j.api.stocks.Point;
import pl.zankowski.iextrading4j.client.IEXTradingClient;
import pl.zankowski.iextrading4j.client.rest.request.IRestRequestBuilder;
import pl.zankowski.iextrading4j.client.rest.request.refdata.SymbolsRequestBuilder;
import pl.zankowski.iextrading4j.client.rest.request.stocks.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


class Market {
    private String symbol;
    private int shareCount;
    private int totalShareVal;
    private int bankBalance;
    //Get symbol string from GUI
    final private IEXTradingClient tradingClient = IEXTradingClient.create();
    //List containing all IEX symbols in
    final List<ExchangeSymbol> exchangeSymbolList = tradingClient.executeRequest(new SymbolsRequestBuilder().build());
    //List which contains: date/share value/symbol of last 5 years (everyday)





    Market(String symbol, int shareCount, int bankBalance) {
        this.symbol = symbol;
        this.shareCount = shareCount;
        this.bankBalance = bankBalance;
    }



    //Generates current day share value data for the specified company
    ArrayList generateChart (ArrayList list){
        final List<Chart> chartList = tradingClient.executeRequest(new ChartRequestBuilder()
                .withChartRange(ChartRange.FIVE_YEARS)
                .withSymbol(symbol)
                .build());
        for(Chart c: chartList){
            list.add("\""  + c.getDate() + "\"" +  "," + "\"" + bankBalance + "\"" + "," + "\"" +  symbol + "\"" + "," + "\"" +  c.getClose() + "\"" + "," + "\"" + shareCount + "\"" + "," + "\""+ (c.getClose().intValue() * shareCount) + "\"");
            //list.add("Date: " +  c.getDate() + "\nBank Balance: £" + bankBalance + "\nCompany " + symbol + "\nCurrent Share Value: £" + c.getClose() + "\nShare Quantity: " + shareCount + "\nTotal Share Value: £" + (c.getClose().intValue() * shareCount));
        }
        return list;
    }



    //Convert the List of type <ExchangeSymbol> to ArrayList so it can be used to compare strings
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


