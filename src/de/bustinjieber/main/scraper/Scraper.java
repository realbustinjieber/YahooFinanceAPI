package de.bustinjieber.main.scraper;

import de.bustinjieber.main.yahoo.Ticker;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class Scraper {

    private String id;
    private Ticker ticker;
    private Document doc;

    public void setup(){
        String url = "https://finance.yahoo.com/quote/" + ticker.getTicker();
        try {
            doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0 Safari/537.36")
                    .header("Accept-Language", "en-US,en;q=0.9")
                    .referrer("https://www.google.com/")
                    .timeout(15_000)
                    .followRedirects(true)
                    .get();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public Scraper(Ticker t){
        this.ticker = t;
        setup();
    }

    public void scrapeTitle(){
        Element quoteElem = doc.selectFirst("[data-testid=quote-title]");
        ticker.setQuoteTitle(
                quoteElem.text()
                        .split("\\(")[0]
        );
    }

    public void scrapePrice(){
        Element priceElem = doc.selectFirst("[data-testid=qsp-price]");
        ticker.setPrice(
                Float.valueOf(
                        priceElem.text()
                                .replace(",", "")
                )
        );
    }

    public void scrapeChange(){
        Element changeElem = doc.selectFirst("[data-testid=qsp-price-change]");
        ticker.setChange(
                Float.valueOf(
                        changeElem.text()
                )
        );
    }

    public void scrapePrevClose(){
        Element prevCloseElem = doc.selectFirst("[data-field=regularMarketPreviousClose]");
        ticker.setPrevClose(
                Float.valueOf(
                        prevCloseElem.text()
                                .replace(",","")
                )
        );
    }

    public void scrapeOpen(){
        Element openElem = doc.selectFirst("[data-field=regularMarketOpen]");
        ticker.setOpen(
                Float.valueOf(
                        openElem.text()
                                .replace(",","")
                )
        );
    }

    public void scrapeChangePct(){
        Element changePctElem = doc.selectFirst("[data-testid=qsp-price-change-percent]");
        ticker.setChangePercentage(
                Float.valueOf(
                        changePctElem.text().
                                replace("%","").
                                replace("(","").
                                replace(")","")
                )
        );
    }

    public void scrapeDayRange(){
        Element daysRangeElem = doc.selectFirst("[data-field=regularMarketDayRange]");
        ticker.setDaysRange(new Float[]{
                Float.parseFloat(
                        daysRangeElem.text()
                                .split("-")[0]
                                .replace(",", "")),
                Float.parseFloat(
                        daysRangeElem.text()
                                .split("-")[1]
                                .replace(",", ""))
                }
        );
    }

    public void scrapeVolume(){
        Element volumeElem = doc.selectFirst("[data-field=regularMarketVolume]");
        ticker.setVolume(
                Float.parseFloat(
                        volumeElem.text()
                                .replace(",","")
                )
        );
    }

    public void scrapeAvgVolume(){
        Element avgVolumeElem = doc.selectFirst("[data-field=averageVolume]");
        ticker.setAvgVolume(
                Float.parseFloat(
                        avgVolumeElem.text()
                                .replace(",","")
                )
        );
    }

    public void scrapeFiftyTwoWeekRange(){
        Element fiftyTwoWeekRangeElem = doc.selectFirst("[data-field=fiftyTwoWeekRange]");
        ticker.setFiftyTwoWeekRange(new Float[]{
                        Float.parseFloat(
                                fiftyTwoWeekRangeElem.text()
                                        .split("-")[0]
                                        .replace(",", "")),
                        Float.parseFloat(
                                fiftyTwoWeekRangeElem.text()
                                        .split("-")[1]
                                        .replace(",", ""))

                }
        );
    }

    /**
     * Scrapes the Market Cap & reinterprets the Value. (Example: 3.00B -> 300000000000000)
     */
    public void scrapeMarketCap(){
        Element marketCapElem = doc.selectFirst("[data-field=marketCap]");

        if(marketCapElem == null) return;
        String mC_s = marketCapElem.text().replace(",","");
        Float mC_f = 0f;

        if(mC_s.contains("B")){
            mC_f = Float.parseFloat(mC_s.replace("B", "")) * 1000000000000f;
        }

        ticker.setMarketCap(
                mC_f
        );
    }

    public void scrapeBeta5YM(){
        Element labelSpan = doc.select("span:contains(Beta (5Y Monthly))").first();
        if (labelSpan == null)return;
        Element valueSpan = labelSpan.nextElementSibling();
        if(valueSpan == null)return;

        ticker.setBeta5YM(
                Float.parseFloat(
                        valueSpan.text()
                                .replace(",","")
                                .trim()
                )
        );
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Ticker getTicker(){
        return this.ticker;
    }
}
