package de.bustinjieber.main.yahoo;

/**
 * Helper Data-Class for storing data relating to a certain Ticker
 */
public class Ticker {

    private String ticker;
    private String quoteTitle;
    private Float price;
    private Float change;
    private Float changePercentage;

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
               + "Price: " + getPrice() + "\n"
               + "Change: " + getChange() + "\n"
               + "Change %: " + getChangePercentage();
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
}
