package de.bustinjieber.main.scraper;

import de.bustinjieber.main.yahoo.Ticker;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Map;

public class Scraper {

    private String id;
    private Ticker ticker;
    private Document doc;

    public void getDocument(String url){
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

    public void getDocumentWithCookies(String url){
        try {
            Connection.Response resp = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0 Safari/537.36")
                    .header("Accept-Language", "en-US,en;q=0.9")
                    .header("Accept", "text/html")
                    .referrer("https://www.google.com/")
                    .timeout(15_000)
                    .followRedirects(true)
                    .execute();

            Map<String,String> cookies = resp.cookies();

            doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0 Safari/537.36")
                    .header("Accept-Language", "en-US,en;q=0.9")
                    .header("Accept", "text/html")
                    .cookies(cookies)
                    .referrer("https://finance.yahoo.com/")
                    .timeout(20_000)
                    .followRedirects(true)
                    .get();

            System.out.println(doc);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Scraper(Ticker t){
        this.ticker = t;
        getDocument("https://finance.yahoo.com/quote/" + ticker.getTicker());
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

    /**
     * Wirft gerade noch 404 - GRRR
     */
    public void scrapeHistoricalDataMax(){
        // Hier werden unix-timecodes verwendet (theoretishc kann man beides auch auf beliebige werte setzen, selbst wenn diese größer als tatsächlich bestehende daten sind.
        int period1 = 1527255000;
        int period2 = 1777472647;
        String url = "https://finance.yahoo.com/quote/" + ticker.getTicker() + "/history/?period1=" + period1 + "&period2=" + period2;
        getDocumentWithCookies(url);

        Element tableContainer = doc.select("[data-testid=history-table]").first();

        if (tableContainer != null) {
            // 2. Alle Zeilen innerhalb des Bodys holen
            Elements rows = tableContainer.select("table tbody tr");

            for (Element row : rows) {
                Elements columns = row.select("td");

                // Sicherstellen, dass die Zeile Daten enthält
                if (columns.size() >= 7) {
                    String date = columns.get(0).text();     // "Dec 10, 2002"
                    String open = columns.get(1).text();     // "0.26"
                    String high = columns.get(2).text();     // "0.28"
                    String low = columns.get(3).text();      // "0.26"
                    String close = columns.get(4).text();    // "0.27"
                    String adjClose = columns.get(5).text(); // "0.23"
                    String volume = columns.get(6).text();   // "308,610,400"

                    System.out.println(date + " | Close: " + close + " | Vol: " + volume);
                }
            }
        }

        //[data-testid="history-table"]

        //Document auf standard zurücksetzen:
        getDocument("https://finance.yahoo.com/quote/" + ticker.getTicker());
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
