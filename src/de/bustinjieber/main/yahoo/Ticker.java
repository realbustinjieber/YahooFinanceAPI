package de.bustinjieber.main.yahoo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Helper Data-Class for storing data relating to a certain Ticker
 */
public class Ticker {

    private String ticker;
    private String quoteTitle;
    private Float price;
    private Float change;
    private Float changePercentage;
    /**
     * statistics-content 1/2
     */
    private Long volume;
    private Float avgVolume;
    private Float[] daysRange;
    private Float open;
    private Float prevClose;
    private Float[] fiftyTwoWeekRange;
    /**
     * statistics-content 2/2 - needs to be checked if null, as they don't always exit (example: S&P 500)
     */
    private Float marketCap;

    public Ticker(){}

    public Ticker(String t, String qT, Float p, Float c, Float cP){
        this.ticker = t;
        this.quoteTitle = qT;
        this.price = p;
        this.change = c;
        this.changePercentage = cP;
    }

    /**
     *
     * @return Basic information about the ticker.
     */
    public String toString(){
       return "Ticker: " + getTicker() + "\n"
               + "Title: " + getQuoteTitle() + "\n"
               + "Price: $ " + getPrice() + "\n"
               + "Change: $ " + getChange() + " / % " + getChangePercentage() + "\n"
               + "Open: $ " + getOpen() + "\n"
               + "Previous Close: $ " + getPrevClose() + "\n"
               + "Volume: " + getVolume() + "\n"
               + "Avg. Volume: " + getAvgVolume() + "\n"
               + "Market Cap: $ " + getMarketCap() + "\n"
               + "Days Range: $ " + getDaysRange()[0] + " - $ " + getDaysRange()[1] + "\n"
               + "52 Week Range: $ " + getFiftyTwoWeekRange()[0] + " - $ " + getFiftyTwoWeekRange()[1] + "\n";
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getQuoteTitle(){
        return quoteTitle;
    }

    public void setQuoteTitle(String qT){
        this.quoteTitle = qT;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getChange() {
        return change;
    }

    public void setChange(Float change) {
        this.change = change;
    }

    public Float getChangePercentage() {
        return changePercentage;
    }

    public void setChangePercentage(Float changePercentage) {
        this.changePercentage = changePercentage;
    }

    public Long getVolume() {
        return volume;
    }

    public void setVolume(Long volume) {
        this.volume = volume;
    }

    public Float getAvgVolume() {
        return avgVolume;
    }

    public void setAvgVolume(Float avgVolume) {
        this.avgVolume = avgVolume;
    }

    public Float[] getDaysRange() {
        return daysRange;
    }

    public void setDaysRange(Float[] daysRange) {
        this.daysRange = daysRange;
    }

    public Float getOpen() {
        return open;
    }

    public void setOpen(Float open) {
        this.open = open;
    }

    public Float getPrevClose() {
        return prevClose;
    }

    public void setPrevClose(Float prevClose) {
        this.prevClose = prevClose;
    }

    public Float[] getFiftyTwoWeekRange() {
        return fiftyTwoWeekRange;
    }

    public void setFiftyTwoWeekRange(Float[] fiftyTwoWeekRange) {
        this.fiftyTwoWeekRange = fiftyTwoWeekRange;
    }

    public Float getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(Float marketCap) {
        this.marketCap = marketCap;
    }
}
