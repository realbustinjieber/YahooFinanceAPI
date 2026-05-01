package de.bustinjieber.main.scraper;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.Cookie;
import de.bustinjieber.main.yahoo.Frequency;
import de.bustinjieber.main.yahoo.Ticker;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Scraper {

    private String id;
    private Ticker ticker;
    private Document doc;
    private DebugPrinter debugPrinter;

    private void getDocument(String url){
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

    /**
     * Connects to yahoo via playwright & saves the received cookies inorder to scrape the website with jsoup
     * using another connection.
     * @param url the url used to connect to yahoo.
     */
    private void getDocumentWithCookies(String url){
        debugPrinter.print(this, url);
        try {
            // extract url via playwright and accepting the cookie popup, thus generating the needed breadcrumb etc.
            Map<String, String> cookies = new HashMap<>();
            try (Playwright playwright = Playwright.create()) {
                Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
                BrowserContext context = browser.newContext();
                Page page = context.newPage();

                page.navigate(url);

                try {
                    page.click("button[name='agree']", new Page.ClickOptions().setTimeout(3000));
                } catch (Exception ignored) {}

                List<Cookie> playwrightCookies = context.cookies();
                for (Cookie cookie : playwrightCookies) {
                    cookies.put(cookie.name, cookie.value);
                }
                browser.close();
            }

            // using the generated cookies in order to scrape the table via JSoup (way faster than using playwright)
            doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0 Safari/537.36")
                    .header("Accept-Language", "en-US,en;q=0.9")
                    .header("Accept", "text/html")
                    .cookies(cookies)
                    .referrer("https://finance.yahoo.com/")
                    .timeout(20_000)
                    .followRedirects(true)
                    .get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Scraper(Ticker t){
        this.ticker = t;
        this.debugPrinter = new DebugPrinter(true);
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
        float mC_f = 0f;

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
     * Converts the DateString given by Hist-Data into a LocalTime-Obj.
     * @param s String (ex: "Dec 10, 2002") that needs to be converted into a LocalDate.
     * @return LocalDate in correct format (ex: "2002-12-10").
     */
    private LocalDate convertLocalDate(String s){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, uuuu", Locale.ENGLISH);
        return LocalDate.parse(s, formatter);
    }

    /**
     * Scrapes /history for all historical data
     * @param f Monthly, Weekly, Daily interval
     * (using 0 & 9999999999 as periods returns the max length, without browser interaction)
     */
    public void scrapeHistoricals(Frequency f){
        String url = "https://finance.yahoo.com/quote/" + ticker.getTicker() + "/history/?period1=0&period2=9999999999&frequency=" + f.getFrequency();
        getDocumentWithCookies(url);

        Element tableContainer = doc.select("[data-testid=history-table]").first();
        if (tableContainer != null) {
            Elements rows = tableContainer.select("table tbody tr");
            for (Element row : rows) {
                Elements columns = row.select("td");
                if (columns.size() >= 7) {
                    String date = columns.get(0).text();
                    ticker.putHistoricalOpen(
                            convertLocalDate(date),
                            Float.parseFloat(
                                    columns.get(1).text().replace(",","")
                            )
                    );
                    ticker.putHistoricalHigh(
                            convertLocalDate(date),
                            Float.parseFloat(
                                    columns.get(2).text().replace(",","")
                            )
                    );
                    ticker.putHistoricalLow(
                            convertLocalDate(date),
                            Float.parseFloat(
                                    columns.get(3).text().replace(",","")
                            )
                    );
                    ticker.putHistoricalClose(
                            convertLocalDate(date),
                            Float.parseFloat(
                                    columns.get(4).text().replace(",","")
                            )
                    );
                    ticker.putHistoricalAdjClose(
                            convertLocalDate(date),
                            Float.parseFloat(
                                    columns.get(5).text().replace(",","")
                            )
                    );
                    ticker.putHistoricalVolume(
                            convertLocalDate(date),
                            Float.parseFloat(
                                    columns.get(6).text().replace(",","")
                            )
                    );
                }
            }
        }
        //Document auf standard zurücksetzen:
        getDocument("https://finance.yahoo.com/quote/" + ticker.getTicker());
    }

    /**
     * Scrapes /history for all historical data, but only in between the selected periods.
     * @param f Monthly, Weekly, Daily interval.
     * @param period1 starting period
     * @param period2 ending period
     * (using 0 & 9999999999 as periods returns the max length, without browser interaction)
     */
    public void scrapeHistoricals(Frequency f, int period1, int period2){
        String url = "https://finance.yahoo.com/quote/" + ticker.getTicker() + "/history/?period1=" + period1 + "&period2=" + period2 + "&frequency=" + f.getFrequency();
        getDocumentWithCookies(url);

        Element tableContainer = doc.select("[data-testid=history-table]").first();
        if (tableContainer != null) {
            Elements rows = tableContainer.select("table tbody tr");
            for (Element row : rows) {
                Elements columns = row.select("td");
                if (columns.size() >= 7) {
                    String date = columns.get(0).text();
                    ticker.putHistoricalOpen(
                            convertLocalDate(date),
                            Float.parseFloat(
                                    columns.get(1).text().replace(",","")
                            )
                    );
                    ticker.putHistoricalHigh(
                            convertLocalDate(date),
                            Float.parseFloat(
                                    columns.get(2).text().replace(",","")
                            )
                    );
                    ticker.putHistoricalLow(
                            convertLocalDate(date),
                            Float.parseFloat(
                                    columns.get(3).text().replace(",","")
                            )
                    );
                    ticker.putHistoricalClose(
                            convertLocalDate(date),
                            Float.parseFloat(
                                    columns.get(4).text().replace(",","")
                            )
                    );
                    ticker.putHistoricalAdjClose(
                            convertLocalDate(date),
                            Float.parseFloat(
                                    columns.get(5).text().replace(",","")
                            )
                    );
                    ticker.putHistoricalVolume(
                            convertLocalDate(date),
                            Float.parseFloat(
                                    columns.get(6).text().replace(",","")
                            )
                    );
                }
            }
        }
        //Document auf standard zurücksetzen:
        getDocument("https://finance.yahoo.com/quote/" + ticker.getTicker());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
