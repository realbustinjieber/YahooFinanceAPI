package de.bustinjieber.main.yahoo;

import de.bustinjieber.main.scraper.Scraper;

import java.util.Date;
import java.util.TreeMap;

/**
 * Helper Data-Class for storing data relating to a certain Ticker
 */
public class Ticker {

    private String ticker;
    private String quoteTitle;
    private Float price;
    private Float change;
    private Float changePercentage;
    private Scraper scraper;
    /**
     * statistics-content 1/2
     */
    private Float volume;
    private Float avgVolume;
    private Float[] daysRange;
    private Float open;
    private Float prevClose;
    private Float[] fiftyTwoWeekRange;
    /**
     * statistics-content 2/2 - needs to be checked if null, as they don't always exit (example: S&P 500)
     */
    private Float marketCap;
    private Float beta5YM;

    /**
     * historical-content - needs to be implemented correctly. (key: date, value: xyz)
     * open, high, low, close, adjClode, volume
     */
    private TreeMap<Date, Float> historicalOpen = new TreeMap<Date, Float>();
    private TreeMap<Date, Float> historicalHigh = new TreeMap<Date, Float>();
    private TreeMap<Date, Float> historicalLow = new TreeMap<Date, Float>();
    private TreeMap<Date, Float> historicalClose = new TreeMap<Date, Float>();
    private TreeMap<Date, Float> historicalAdjClose = new TreeMap<Date, Float>();
    private TreeMap<Date, Float> historicalVolume = new TreeMap<Date, Float>();
    private boolean historicalsExist = false;

    /**
     * Initializes a new Ticker (with the according Symbol) and gives it its own scraper.
     * @param t Symbol needed to indetify the ticker (ex: AAPL)
     */
    public Ticker(String t){
        this.ticker = t;
        scraper = new Scraper(this);
    }

    /**
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
               + "52 Week Range: $ " + getFiftyTwoWeekRange()[0] + " - $ " + getFiftyTwoWeekRange()[1] + "\n"
               + "Beta (5Y Monthly): " + getBeta5YM() + "\n";
    }

    public String getTicker() {
        return ticker;
    }

    public String getQuoteTitle(){
        scraper.scrapeTitle();
        return quoteTitle;
    }

    public void setQuoteTitle(String qT){
        this.quoteTitle = qT;
    }

    public Float getPrice() {
        scraper.scrapePrice();
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getChange() {
        scraper.scrapeChange();
        return change;
    }

    public void setChange(Float change) {
        this.change = change;
    }

    public Float getChangePercentage() {
        scraper.scrapeChangePct();
        return changePercentage;
    }

    public void setChangePercentage(Float changePercentage) {
        this.changePercentage = changePercentage;
    }

    public Float getVolume() {
        scraper.scrapeVolume();
        return volume;
    }

    public void setVolume(Float volume) {
        this.volume = volume;
    }

    public Float getAvgVolume() {
        scraper.scrapeAvgVolume();
        return avgVolume;
    }

    public void setAvgVolume(Float avgVolume) {
        this.avgVolume = avgVolume;
    }

    public Float[] getDaysRange() {
        scraper.scrapeDayRange();
        return daysRange;
    }

    public void setDaysRange(Float[] daysRange) {
        this.daysRange = daysRange;
    }

    public Float getOpen() {
        scraper.scrapeOpen();
        return open;
    }

    public void setOpen(Float open) {
        this.open = open;
    }

    public Float getPrevClose() {
        scraper.scrapePrevClose();
        return prevClose;
    }

    public void setPrevClose(Float prevClose) {
        this.prevClose = prevClose;
    }

    public Float[] getFiftyTwoWeekRange() {
        scraper.scrapeFiftyTwoWeekRange();
        return fiftyTwoWeekRange;
    }

    public void setFiftyTwoWeekRange(Float[] fiftyTwoWeekRange) {
        this.fiftyTwoWeekRange = fiftyTwoWeekRange;
    }

    public Float getMarketCap() {
        scraper.scrapeMarketCap();
        return marketCap;
    }

    public void setMarketCap(Float marketCap) {
        this.marketCap = marketCap;
    }

    public Scraper getScraper() {
        return scraper;
    }

    public Float getBeta5YM() {
        scraper.scrapeBeta5YM();
        return beta5YM;
    }

    public void setBeta5YM(Float beta5YM) {
        this.beta5YM = beta5YM;
    }

    /**
     * Scrapes the historical Data needed for functions such as: getHistoricalOpen() etc.
     * @param f Monthly, Weekly, Daily interval.
     */
    public void getHistoricals(Frequency f){
        historicalsExist = true;
        getScraper().scrapeHistoricals(f);
    }

    /**
     * Scrapes the historical Data needed for functions such as: getHistoricalOpen() etc.
     * @param f Monthly, Weekly, Daily interval.
     * @param period1 starting period
     * @param period2 ending period
     */
    public void getHistoricals(Frequency f, int period1, int period2){
        historicalsExist = true;
        getScraper().scrapeHistoricals(f, period1, period2);
    }

    public TreeMap<Date, Float> getHistoricalOpen() {
        if(!doHistoricalsExist()){
            throw new RuntimeException("You have to run 'getHistoricals' first!");
        }
        return historicalOpen;
    }

    public void putHistoricalOpen(Date d, Float f){
        this.historicalOpen.put(d,f);
    }

    public TreeMap<Date, Float> getHistoricalHigh() {
        if(!doHistoricalsExist()){
            throw new RuntimeException("You have to run 'getHistoricals' first!");
        }
        return historicalHigh;
    }

    public void putHistoricalHigh(Date d, Float f){
        this.historicalHigh.put(d,f);
    }

    public TreeMap<Date, Float> getHistoricalLow() {
        if(!doHistoricalsExist()){
            throw new RuntimeException("You have to run 'getHistoricals' first!");
        }
        return historicalLow;
    }

    public void putHistoricalLow(Date d, Float f){
        this.historicalLow.put(d,f);
    }

    public TreeMap<Date, Float> getHistoricalClose() {
        if(!doHistoricalsExist()){
            throw new RuntimeException("You have to run 'getHistoricals' first!");
        }
        return historicalClose;
    }

    public void putHistoricalClose(Date d, Float f){
        this.historicalClose.put(d,f);
    }

    public TreeMap<Date, Float> getHistoricalAdjClose() {
        if(!doHistoricalsExist()){
            throw new RuntimeException("You have to run 'getHistoricals' first!");
        }
        return historicalAdjClose;
    }

    public void putHistoricalAdjClose(Date d, Float f){
        this.historicalAdjClose.put(d,f);
    }

    public TreeMap<Date, Float> getHistoricalVolume() {
        if(!doHistoricalsExist()){
            throw new RuntimeException("You have to run 'getHistoricals' first!");
        }
        return historicalVolume;
    }

    public void putHistoricalVolume(Date d, Float f){
        this.historicalVolume.put(d,f);
    }

    public boolean doHistoricalsExist() {
        return historicalsExist;
    }
}
