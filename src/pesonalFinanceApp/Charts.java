package pesonalFinanceApp;

import javax.swing.*;
import java.util.HashMap;
import java.util.List;

import org.jfree.data.time.*;
import pl.zankowski.iextrading4j.api.stocks.Chart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import java.awt.*;
import java.util.Map;

import static pesonalFinanceApp.GUI.secondary_color;



public class Charts extends JPanel {
    private Map<String, List<Chart>> stockMap = null;
    private Map<String, Double> portfolioMap = null;
    private int days = 30;

    public Charts() {
        updateChart(stockMap, days, 0);
    }

    // Stock
    public void updateChart(Map<String, List<Chart>> data, int display_days, int flag){
        stockMap = data;
        days = display_days;

        XYDataset dataset = createDataset();
        JFreeChart chart = ChartFactory.createTimeSeriesChart("Stock Prices",
                "Date",
                "Price (USD)",
                dataset);

        ChartPanel panel = buildChart(chart);
        add(panel);
    }

    // Portfolio
    public void updateChart(Map<String, Double> data, int display_days){
        portfolioMap = data;
        days = display_days;

        XYDataset dataset = createPortfolioDataset();
        JFreeChart chart = ChartFactory.createTimeSeriesChart("Stock Prices",
                "Date",
                "Price (USD)",
                dataset);

        ChartPanel panel = buildChart(chart);
        add(panel);
    }

    private ChartPanel buildChart(JFreeChart chart) {
        JFreeChart chrt = chart;
        XYPlot plot = chrt.getXYPlot();
        ValueAxis yAxis = plot.getRangeAxis();
        ValueAxis xAxis = plot.getDomainAxis();
        Font font = new Font("Dialog", Font.PLAIN, 10);
        yAxis.setTickLabelFont(font);
        xAxis.setTickLabelFont(font);

        styleChart(chrt);

        ChartPanel panel = new ChartPanel(chrt);

        panel.setPopupMenu(null);
        panel.setPreferredSize(new Dimension(990,350));
        return panel;
    }

    private void styleChart(JFreeChart chrt) {
        // -- Outside of chart
        chrt.setBackgroundPaint(new Color(199,198,211));
        chrt.getLegend().setBackgroundPaint(new Color(199,198,211));
        this.setBackground(secondary_color);

        // -- Inside of chart
        chrt.getPlot().setBackgroundPaint(secondary_color);
        chrt.getPlot().setOutlinePaint(Color.BLACK);
    }



    // -- Portfolio Data Set --
    private XYDataset createPortfolioDataset() {
        TimeSeriesCollection portfolioSeries = new TimeSeriesCollection();
        TimeSeries portfolioLinePlot = new TimeSeries("Portfolio");


        if(portfolioMap != null) {
            int total_days = this.days;
            for(Map.Entry<String, Double> entry : portfolioMap.entrySet()) {
                if(total_days <= 0) break;
                String[] date_array = entry.getKey().split("-");

                Integer year = Integer.parseInt(date_array[0]);
                Integer month = Integer.parseInt(date_array[1]);
                Integer day = Integer.parseInt(date_array[2]);

                Day current_day = new Day(day, month, year);
                portfolioLinePlot.add(current_day, entry.getValue());
                --total_days;
            }
        }
        portfolioSeries.addSeries(portfolioLinePlot);
        return portfolioSeries;
    }

    // -- Stock Map Data Set --
    private XYDataset createDataset() {
        TimeSeriesCollection stockSeries = new TimeSeriesCollection();

        if(stockMap != null) {
            stockMap.forEach((String stockName, List<Chart> stockGroup) -> {
                TimeSeries stockLinePlot = new TimeSeries(stockName);
                if(days > 1100) { days = 1100; }
                for (int i = stockGroup.size() - 1; i > stockGroup.size() - days; i--) {
                    String[] stockDatePoint = stockGroup.get(i).getDate().split("-");

                    Integer stockDateYear = Integer.parseInt(stockDatePoint[0]);
                    Integer stockDateMonth = Integer.parseInt(stockDatePoint[1]);
                    Integer stockDateDay = Integer.parseInt(stockDatePoint[2]);
                    Day stockDateObject = new Day(stockDateDay, stockDateMonth, stockDateYear);

                    stockLinePlot.add(stockDateObject, stockGroup.get(i).getClose());
                }
                stockSeries.addSeries(stockLinePlot);
            });
            // If more than 1 stock, calculate average of stocks
            if(stockMap.size() > 1){
                TimeSeries averageLinePlot = buildAverageLinePlot(stockSeries);
                stockSeries.addSeries(averageLinePlot);
            }
        } else {
            TimeSeries emptyStockPlot = new TimeSeries("Empty");
            emptyStockPlot.add(new Day(10,10, 2018), 0);
            stockSeries.addSeries(emptyStockPlot);
        }

        return stockSeries;
    }

    private TimeSeries buildAverageLinePlot(TimeSeriesCollection stocks) {
        TimeSeries averageLinePlot;
        HashMap<RegularTimePeriod, Float> datesAndValues;

        datesAndValues = getDatesAndValuesFromCollection(stocks);

        Integer linePlotCount = stocks.getSeriesCount();
        averageLinePlot = calculateAveragesFromMap(datesAndValues, linePlotCount);

        return averageLinePlot;
    }

    private HashMap<RegularTimePeriod, Float> getDatesAndValuesFromCollection(TimeSeriesCollection stocks) {
        HashMap<RegularTimePeriod, Float> datesAndValues = new HashMap<>();

        for(int i = 0; i < stocks.getSeriesCount(); i++) {
            TimeSeries stockSeries = stocks.getSeries(i);

            for(int j = 0; j < stockSeries.getItemCount(); j++) {
                TimeSeriesDataItem dayItem = stockSeries.getDataItem(j);

                if( datesAndValues.containsKey(dayItem.getPeriod()) ) {
                    Float totalValue = datesAndValues.get( dayItem.getPeriod() );
                    Float dayValue = Float.valueOf(dayItem.getValue().toString());
                    datesAndValues.put(dayItem.getPeriod(), totalValue + dayValue);
                }
                else {
                    Float dayValue = Float.valueOf(dayItem.getValue().toString());
                    datesAndValues.put(dayItem.getPeriod(), dayValue);
                }
            }
        }

        return datesAndValues;
    }

    private TimeSeries calculateAveragesFromMap(HashMap<RegularTimePeriod, Float> map, Integer linePlotCount) {

        TimeSeries averageLinePlot = new TimeSeries("Average");
        for(Map.Entry<RegularTimePeriod, Float> entry : map.entrySet()) {
            RegularTimePeriod date = entry.getKey();
            Float valueAverage = entry.getValue() / linePlotCount;

            averageLinePlot.add(date, valueAverage);
        }

        return averageLinePlot;
    }
}
