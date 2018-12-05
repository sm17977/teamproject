package pesonalFinanceApp;

import javax.swing.*;
import java.util.List;

import pl.zankowski.iextrading4j.api.stocks.Chart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import java.awt.*;
import java.util.Map;

import static pesonalFinanceApp.GUI.primary_color;
import static pesonalFinanceApp.GUI.secondary_color;


public class Charts extends JPanel {
    private Map<String, List<Chart>> stockMap = null;
    private Map<String, Double> portfolioMap = null;
    private int days = 30;

    public Charts() {
        updateChart(stockMap, days, 0);
        setBackground(secondary_color);
    }


    private ChartPanel buildChart(JFreeChart chrt) {
        XYPlot plot = chrt.getXYPlot();
        ValueAxis yAxis = plot.getRangeAxis();
        ValueAxis xAxis = plot.getDomainAxis();
        Font font = new Font("Dialog", Font.PLAIN, 10);
        yAxis.setTickLabelFont(font);
        xAxis.setTickLabelFont(font);

        ChartPanel panel = new ChartPanel(chrt);

        panel.getChart().setBackgroundPaint(primary_color);

        chrt.setBackgroundPaint(primary_color);
        chrt.getPlot().setBackgroundPaint(Color.LIGHT_GRAY);
        chrt.getPlot().setOutlinePaint(Color.BLACK);
        chrt.getLegend().setBackgroundPaint(primary_color);

        panel.setPopupMenu(null);
        panel.setPreferredSize(new Dimension(990,350));
        return panel;
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



    private XYDataset createPortfolioDataset() {
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        TimeSeries series = new TimeSeries("Portfolio");


        if(portfolioMap != null) {
            int total_days = this.days;
            for(Map.Entry<String, Double> entry : portfolioMap.entrySet()) {
                if(total_days <= 0) break;
                String[] date_array = entry.getKey().split("-");

                Integer year = Integer.parseInt(date_array[0]);
                Integer month = Integer.parseInt(date_array[1]);
                Integer day = Integer.parseInt(date_array[2]);

                Day current_day = new Day(day, month, year);
                series.add(current_day, entry.getValue());
                --total_days;
            }
        }
        dataset.addSeries(series);
        return dataset;
    }

    private XYDataset createDataset() {
        TimeSeriesCollection dataset = new TimeSeriesCollection();

        if(stockMap != null) {
            stockMap.forEach((String str, List<Chart> list) -> {
                TimeSeries series = new TimeSeries(str);

                for (int i = list.size() - 1; i > list.size() - days; i--) {
                    String[] date_array = list.get(i).getDate().split("-");

                    Integer year = Integer.parseInt(date_array[0]);
                    Integer month = Integer.parseInt(date_array[1]);
                    Integer day = Integer.parseInt(date_array[2]);
                    Day current_day = new Day(day, month, year);
                    series.add(current_day, list.get(i).getClose());
                }
                dataset.addSeries(series);
            });
        } else {
            TimeSeries series = new TimeSeries("Empty");
            series.add(new Day(10,10, 2018), 0);
            dataset.addSeries(series);
        }

        return dataset;
    }
}
