package de.bustinjieber.main;

import de.bustinjieber.main.yahoo.statements.BalanceSheet;
import de.bustinjieber.main.yahoo.statements.CashFlow;
import de.bustinjieber.main.yahoo.statements.IncomeStatement;
import de.bustinjieber.main.yahoo.utils.Frequency;
import de.bustinjieber.main.yahoo.Ticker;
import de.bustinjieber.main.yahoo.utils.Period;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.Styler;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Demo {

    public static void main(String[] args) {
        Ticker t1 = new Ticker("AAPL");
        t1.getHistoricals(Frequency.Monthly);

        // Showcase of the current data
        System.out.println(t1);

        // Showcase of Cash Flow, Balance Sheets & Income Statements (Quarterly or Yearly)
        System.out.println("--- Cash Flow ---");
        for(CashFlow cF : t1.getCashFlows(Period.Quarterly).values()){
            System.out.println(cF);
        }

        System.out.println("--- Balance Sheet ---");
        for(BalanceSheet bS : t1.getBalanceSheets(Period.Quarterly).values()){
            System.out.println(bS);
        }

        System.out.println("--- Income Statement ---");
        for(IncomeStatement iS : t1.getIncomeStatements(Period.Quarterly).values()){
            System.out.println(iS);
        }

        // Showcase of the historical data

        List<Date> xData = new ArrayList<>();
        List<Double> yData = new ArrayList<>();

        for(Date d : t1.getHistoricalOpen().keySet()){
            xData.add(d);
            yData.add(t1.getHistoricalOpen().get(d));
        }

        XYChart chart = new XYChartBuilder()
                .width(1200).height(600)
                .title(t1.getQuoteTitle())
                .xAxisTitle("Date")
                .yAxisTitle("$")
                .build();

        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
        chart.getStyler().setMarkerSize(0);
        chart.getStyler().setYAxisDecimalPattern("#,###.##");

        chart.addSeries("Hist. Open", xData, yData);

        xData = new ArrayList<>();
        yData = new ArrayList<>();
        for(Date d : t1.getHistoricalClose().keySet()){
            xData.add(d);
            yData.add(t1.getHistoricalClose().get(d));
        }
        chart.addSeries("Hist. Close", xData, yData);

        xData = new ArrayList<>();
        yData = new ArrayList<>();
        for(Date d : t1.getHistoricalLow().keySet()){
            xData.add(d);
            yData.add(t1.getHistoricalLow().get(d));
        }
        chart.addSeries("Hist. Low", xData, yData);
        new SwingWrapper(chart).displayChart();
    }
}
