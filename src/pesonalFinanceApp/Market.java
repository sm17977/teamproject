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
    //String symbol is retrieved from the GUI from the Company text field, and is used to validate against list of all IEX Stock Symbols
    private String symbol;
    //int ShareCount Holds the quantity of shares for the Company that is inputted into the GUI
    private int shareCount;
    //Not used yet
    private int totalShareVal;
    //int bankBalance holds the value of the clients bank balance which is retrieved from the GUI text field bank balance
    private int bankBalance;
    //tradingClient is an IEXTrading Client object that allows information to be retrieved using the IEX API
    final private IEXTradingClient tradingClient = IEXTradingClient.create();
    //exchangeSymbolList is a list of type "ExchangeSymbol" that contains all valid IEX symbols
    final List<ExchangeSymbol> exchangeSymbolList = tradingClient.executeRequest(new SymbolsRequestBuilder().build());

    //Market constructor gets variables from GUI input, so can be accessed and used in method generateChart
    Market(String symbol, int shareCount, int bankBalance) {
        this.symbol = symbol;
        this.shareCount = shareCount;
        this.bankBalance = bankBalance;
    }

    //Generates current day share value data for the company specified in the GUI input, uses IEX Chart type and converts to ArrayList
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


