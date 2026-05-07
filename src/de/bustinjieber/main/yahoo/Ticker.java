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
    private Double price;
    private Double change;
    private Double changePercentage;
    private Scraper scraper;
    /**
     * statistics-content 1/2
     */
    private Double volume;
    private Double avgVolume;
    private Double[] daysRange;
    private Double open;
    private Double prevClose;
    private Double[] fiftyTwoWeekRange;
    /**
     * statistics-content 2/2 - needs to be checked if null, as they don't always exit (example: S&P 500)
     */
    private Double marketCap;
    private Double beta5YM;

    /**
     * historical-content - needs to be implemented correctly. (key: date, value: xyz)
     * open, high, low, close, adjClode, volume
     */
    private TreeMap<Date, Double> historicalOpen = new TreeMap<Date, Double>();
    private TreeMap<Date, Double> historicalHigh = new TreeMap<Date, Double>();
    private TreeMap<Date, Double> historicalLow = new TreeMap<Date, Double>();
    private TreeMap<Date, Double> historicalClose = new TreeMap<Date, Double>();
    private TreeMap<Date, Double> historicalAdjClose = new TreeMap<Date, Double>();
    private TreeMap<Date, Double> historicalVolume = new TreeMap<Date, Double>();
    private boolean historicalsExist = false;
    /**
     *  IncomeStatement
     */
    private TreeMap<Date, IncomeStatement> incomeStatements = new TreeMap<>();

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
    @Override
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

    public Double getPrice() {
        scraper.scrapePrice();
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getChange() {
        scraper.scrapeChange();
        return change;
    }

    public void setChange(Double change) {
        this.change = change;
    }

    public Double getChangePercentage() {
        scraper.scrapeChangePct();
        return changePercentage;
    }

    public void setChangePercentage(Double changePercentage) {
        this.changePercentage = changePercentage;
    }

    public Double getVolume() {
        scraper.scrapeVolume();
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public Double getAvgVolume() {
        scraper.scrapeAvgVolume();
        return avgVolume;
    }

    public void setAvgVolume(Double avgVolume) {
        this.avgVolume = avgVolume;
    }

    public Double[] getDaysRange() {
        scraper.scrapeDayRange();
        return daysRange;
    }

    public void setDaysRange(Double[] daysRange) {
        this.daysRange = daysRange;
    }

    public Double getOpen() {
        scraper.scrapeOpen();
        return open;
    }

    public void setOpen(Double open) {
        this.open = open;
    }

    public Double getPrevClose() {
        scraper.scrapePrevClose();
        return prevClose;
    }

    public void setPrevClose(Double prevClose) {
        this.prevClose = prevClose;
    }

    public Double[] getFiftyTwoWeekRange() {
        scraper.scrapeFiftyTwoWeekRange();
        return fiftyTwoWeekRange;
    }

    public void setFiftyTwoWeekRange(Double[] fiftyTwoWeekRange) {
        this.fiftyTwoWeekRange = fiftyTwoWeekRange;
    }

    public Double getMarketCap() {
        scraper.scrapeMarketCap();
        return marketCap;
    }

    public void setMarketCap(Double marketCap) {
        this.marketCap = marketCap;
    }

    public Scraper getScraper() {
        return scraper;
    }

    public Double getBeta5YM() {
        scraper.scrapeBeta5YM();
        return beta5YM;
    }

    public void setBeta5YM(Double beta5YM) {
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

    public TreeMap<Date, Double> getHistoricalOpen() {
        if(!doHistoricalsExist()){
            throw new RuntimeException("You have to run 'getHistoricals()' first!");
        }
        return historicalOpen;
    }

    public void putHistoricalOpen(Date d, Double f){
        this.historicalOpen.put(d,f);
    }

    public TreeMap<Date, Double> getHistoricalHigh() {
        if(!doHistoricalsExist()){
            throw new RuntimeException("You have to run 'getHistoricals()' first!");
        }
        return historicalHigh;
    }

    public void putHistoricalHigh(Date d, Double f){
        this.historicalHigh.put(d,f);
    }

    public TreeMap<Date, Double> getHistoricalLow() {
        if(!doHistoricalsExist()){
            throw new RuntimeException("You have to run 'getHistoricals()' first!");
        }
        return historicalLow;
    }

    public void putHistoricalLow(Date d, Double f){
        this.historicalLow.put(d,f);
    }

    public TreeMap<Date, Double> getHistoricalClose() {
        if(!doHistoricalsExist()){
            throw new RuntimeException("You have to run 'getHistoricals()' first!");
        }
        return historicalClose;
    }

    public void putHistoricalClose(Date d, Double f){
        this.historicalClose.put(d,f);
    }

    public TreeMap<Date, Double> getHistoricalAdjClose() {
        if(!doHistoricalsExist()){
            throw new RuntimeException("You have to run 'getHistoricals()' first!");
        }
        return historicalAdjClose;
    }

    public void putHistoricalAdjClose(Date d, Double f){
        this.historicalAdjClose.put(d,f);
    }

    public TreeMap<Date, Double> getHistoricalVolume() {
        if(!doHistoricalsExist()){
            throw new RuntimeException("You have to run 'getHistoricals()' first!");
        }
        return historicalVolume;
    }

    public void putHistoricalVolume(Date d, Double f){
        this.historicalVolume.put(d,f);
    }

    public boolean doHistoricalsExist() {
        return historicalsExist;
    }

    /**
     * Get all income-statements. Scrapes for them if not already happened.
     * (To get newer Statements, you need to run 'scrapeIncomeStatements()' first.
     * @return a TreeMap of all income-statements sorted by date (TTM is always assigned the current time and date in chicago).
     */
    public TreeMap<Date, IncomeStatement> getIncomeStatements() {
        if(incomeStatements.isEmpty()) getScraper().scrapeIncomeStatements();
        return incomeStatements;
    }

    public void setIncomeStatements(TreeMap<Date, IncomeStatement> incomeStatements) {
        this.incomeStatements = incomeStatements;
    }
}
