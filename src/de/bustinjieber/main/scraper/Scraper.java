package de.bustinjieber.main.scraper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.Cookie;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;
import de.bustinjieber.main.yahoo.*;
import de.bustinjieber.main.yahoo.statements.BalanceSheet;
import de.bustinjieber.main.yahoo.statements.CashFlow;
import de.bustinjieber.main.yahoo.statements.IncomeStatement;
import de.bustinjieber.main.yahoo.utils.Frequency;
import de.bustinjieber.main.yahoo.utils.Period;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.function.BiConsumer;

public class Scraper {

    private String id;
    private Ticker ticker;
    private Document doc;
    private DebugPrinter debugPrinter;
    private final Parser parser = new Parser();

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
     * Scrapes the JSON-Response from the Backend.
     * @param url given Backed-Url / API.
     * @return a JsonNode Obj.
     * @throws IOException if the connection fails.
     */
    private JsonNode getJSON(String url) throws IOException {
        try {
            Connection.Response response = Jsoup.connect(url)
                    .ignoreContentType(true)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0 Safari/537.36")
                    .timeout(15_000)
                    .execute();

            ObjectMapper mapper = new ObjectMapper();
            return mapper.readTree(response.body());
        }catch (IOException e){
            throw new IOException(e);
        }
    }

    /**
     * Connects to yahoo via playwright & saves the received cookies inorder to scrape the website with jsoup
     * using another connection.
     * @param url the url used to connect to yahoo.
     */
    private void getDocumentWithCookies(String url){
        debugPrinter.print(this, url);
        String html;
        try {
            // extract url via playwright and accepting the cookie popup, thus generating the needed breadcrumb etc.
            Map<String, String> cookies = new HashMap<>();
            try (Playwright playwright = Playwright.create()) {
                Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
                BrowserContext context = browser.newContext();
                Page page = context.newPage();

                page.navigate(url);
                page.waitForLoadState(LoadState.NETWORKIDLE);

                try {
                    page.click("button[name='agree']", new Page.ClickOptions().setTimeout(3000));
                } catch (Exception ignored) {}

                if(url.contains("balance-sheet")) {
                    try {
                        Locator btn = page.locator("button:has-text(\"Expand All\")");
                        btn.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(10000));
                        page.evaluate("() => { const blockers = document.querySelectorAll('header, .overlay, .loading, nav[aria-label], .sticky'); blockers.forEach(e => e.style.pointerEvents = 'none'); }");
                        btn.click();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                List<Cookie> playwrightCookies = context.cookies();
                for (Cookie cookie : playwrightCookies) {
                    cookies.put(cookie.name, cookie.value);
                }
                    html = page.content();
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

            doc = Jsoup.parse(html,"https://finance.yahoo.com/");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Scraper(Ticker t){
        this.ticker = t;
        this.debugPrinter = new DebugPrinter(false);
        getDocument("https://finance.yahoo.com/quote/" + ticker.getTicker());
    }

    public Scraper(Ticker t, boolean b){
        this.ticker = t;
        this.debugPrinter = new DebugPrinter(b);
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
        Element element = doc.selectFirst("[data-testid=qsp-price]");
        ticker.setPrice(
                parser.parseAmount(element.text())
        );
    }

    public void scrapeChange(){
        Element element = doc.selectFirst("[data-testid=qsp-price-change]");
        ticker.setChange(
                parser.parseAmount(element.text())
        );
    }

    public void scrapePrevClose(){
        Element element = doc.selectFirst("[data-field=regularMarketPreviousClose]");
        ticker.setPrevClose(
                parser.parseAmount(element.text())
        );
    }

    public void scrapeOpen(){
        Element element = doc.selectFirst("[data-field=regularMarketOpen]");
        ticker.setOpen(
                parser.parseAmount(element.text())
        );
    }

    public void scrapeChangePct(){
        Element element = doc.selectFirst("[data-testid=qsp-price-change-percent]");
        ticker.setChangePercentage(
                parser.parsePercentage(element.text())
        );
    }

    public void scrapeDayRange(){
        Element element = doc.selectFirst("[data-field=regularMarketDayRange]");
        ticker.setDaysRange(
                parser.parseAmountArray(element.text())
        );
    }

    public void scrapeVolume(){
        Element element = doc.selectFirst("[data-field=regularMarketVolume]");
        ticker.setVolume(
                parser.parseAmount(element.text())
        );
    }

    public void scrapeAvgVolume(){
        Element element = doc.selectFirst("[data-field=averageVolume]");
        ticker.setAvgVolume(
                parser.parseAmount(element.text())
        );
    }

    public void scrapeFiftyTwoWeekRange(){
        Element element = doc.selectFirst("[data-field=fiftyTwoWeekRange]");
        ticker.setFiftyTwoWeekRange(
                parser.parseAmountArray(element.text())
        );
    }

    /**
     * Scrapes the Market Cap & reinterprets the Value. (Example: 3.00B -> 300000000000000)
     */
    public void scrapeMarketCap(){
        Element marketCapElem = doc.selectFirst("[data-field=marketCap]");

        if(marketCapElem == null) return;
        if(marketCapElem.text().contains("--"))return;
        String mC_s = marketCapElem.text().replace(",","");
        Double mC_f = 0d;

        if(mC_s.contains("B")){
            mC_f = Double.parseDouble(mC_s.replace("B", "")) * 1000000000f;
        }
        else if(mC_s.contains("T"))
        {
            mC_f = Double.parseDouble(mC_s.replace("T", "")) * 1000000000000f;
        }
        else if(mC_s.contains("M"))
        {
            mC_f = Double.parseDouble(mC_s.replace("M", "")) * 1000000f;
        }

        ticker.setMarketCap(
                mC_f
        );
    }

    public void scrapeBeta5YM(){
        Element labelSpan = doc.select("span:contains(Beta (5Y Monthly))").first();
        if (labelSpan == null)return;
        Element valueSpan = labelSpan.nextElementSibling();
        if(valueSpan == null || valueSpan.text().contains("--"))return;

        ticker.setBeta5YM(
                parser.parseAmount(valueSpan.text())
        );
    }

    /**
     * Actual Scraping method, as I didn't want to have the scraper duplicated.
     * @param url url that points to the historicals.
     */
    private void scrapeHistoricalBackend(String url){
        getDocumentWithCookies(url);

        Element tableContainer = doc.selectFirst("[data-testid=history-table]");
        if (tableContainer != null) {
            Elements rows = tableContainer.select("table tbody tr");
            for (Element row : rows) {
                Elements columns = row.select("td");
                if (columns.size() >= 7) {
                    Date date = parser.convertStrToDate(columns.get(0).text());
                    ticker.putHistoricalOpen(
                            date,
                            parser.parseAmount(columns.get(1).text())
                    );
                    ticker.putHistoricalHigh(
                            date,
                            parser.parseAmount(columns.get(2).text())
                    );
                    ticker.putHistoricalLow(
                            date,
                            parser.parseAmount(columns.get(3).text())
                    );
                    ticker.putHistoricalClose(
                            date,
                            parser.parseAmount(columns.get(4).text())
                    );
                    ticker.putHistoricalAdjClose(
                            date,
                            parser.parseAmount(columns.get(5).text())
                    );
                    ticker.putHistoricalVolume(
                            date,
                            parser.parseAmount(columns.get(6).text())
                    );
                }
            }
        }
        //Document auf standard zurücksetzen:
        getDocument("https://finance.yahoo.com/quote/" + ticker.getTicker());
    }

    /**
     * Scrapes /history for all historical data
     * @param f Monthly, Weekly, Daily interval
     * (using 0 & 9999999999 as periods returns the max length, without browser interaction)
     */
    public void scrapeHistoricals(Frequency f){
        String url = "https://finance.yahoo.com/quote/" + ticker.getTicker() + "/history/?period1=0&period2=9999999999&frequency=" + f.getFrequency();
        scrapeHistoricalBackend(url);
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
        scrapeHistoricalBackend(url);
    }

    /**
     * updates the various setters included in the income-statement.
     * @param dI TreeMap of our Date & IncomeStatement
     * @param d Date
     * @param cC String-Array
     * @param setter Setter that needs to be called
     */
    private void updateStatements(TreeMap<LocalDate, IncomeStatement> dI, List<LocalDate> d, String[] cC, BiConsumer<IncomeStatement, Double> setter) {
        for (int i = 0; i < cC.length; i++) {
            setter.accept(
                    dI.get(d.get(i)),
                    parser.parseAmount(cC[i])
            );
        }
    }

//    /* https://finance.yahoo.com/quote/RYAN/financials/
//    Table is called: data-testid="qsp-financials"
//    Reihenfolge der Rows: TTM 9/30/2025 9/30/2024 9/30/2023 9/30/2022
//    ToDo: Currently only annual statements, as you would need to press the quarterly button to switch!
//    ToDo: Implement with reflections.
//     */
//    public void scrapeIncomeStatements(){
//        String url = "https://finance.yahoo.com/quote/" + ticker.getTicker() + "/financials/";
//        getDocumentWithCookies(url);
//
//        Elements dateElements = doc.select(".tableHeader .row > .column");
//        TreeMap<LocalDate, IncomeStatement> statements = new TreeMap<>();
//        List<LocalDate> dates = new ArrayList<>();
//        for(Element e : dateElements){
//            if(e.text().contains("Breakdown"))continue;
//            LocalDate date = parser.toLocalDate(e.text());
//            IncomeStatement statement = new IncomeStatement(date);
//            statement.setTicker(ticker);
//            statements.put(date,statement);
//            dates.add(date);
//        }
//
//        Element tableElement = doc.selectFirst("[data-testid=qsp-financials]");
//        if(tableElement==null)return;
//
//        Elements rowsElement = doc.select("div.row.lv-0");
//
//        for(Element row : rowsElement){
//            Element titleRowElement = row.selectFirst(".rowTitle");
//            String elementTitle = titleRowElement.attr("title");
//
//            Elements columnElement = row.select("div.column");
//            String columnContent = columnElement.text()
//                    .replace(
//                            titleRowElement.attr("title"),
//                            ""
//                    )
//                    .substring(1);
//            String[] columnContents = columnContent.split(" ");
//
//            switch (elementTitle) {
//                case "Total Revenue":
//                    updateStatements(statements,dates,columnContents,IncomeStatement::setTotalRevenue);
//                    break;
//                case "Cost of Revenue":
//                    updateStatements(statements,dates,columnContents,IncomeStatement::setCostOfRevenue);
//                    break;
//                case "Gross Profit":
//                    updateStatements(statements,dates,columnContents,IncomeStatement::setGrossProfit);
//                    break;
//                case "Operating Expense":
//                    updateStatements(statements,dates,columnContents,IncomeStatement::setOperatingExpenses);
//                    break;
//                case "Operating Income":
//                    updateStatements(statements,dates,columnContents,IncomeStatement::setOperatingIncome);
//                    break;
//                case "Total Expenses":
//                    updateStatements(statements,dates,columnContents,IncomeStatement::setTotalExpenses);
//                    break;
//                case "Pretax Income":
//                    updateStatements(statements,dates,columnContents,IncomeStatement::setPreTaxIncome);
//                    break;
//                case "Tax Provision":
//                    updateStatements(statements,dates,columnContents,IncomeStatement::setTaxProvision);
//                    break;
//                case "Basic EPS":
//                    updateStatements(statements,dates,columnContents,IncomeStatement::setBasicEarningsPerShare);
//                    break;
//                case "Diluted EPS":
//                    updateStatements(statements,dates,columnContents,IncomeStatement::setDilutedEarningsPerShare);
//                    break;
//                case "Normalized Income":
//                    updateStatements(statements,dates,columnContents,IncomeStatement::setNormalizedIncome);
//                    break;
//                case "EBIT":
//                    updateStatements(statements,dates,columnContents,IncomeStatement::setEBIT);
//                    break;
//                case "Reconciled Depreciation":
//                    updateStatements(statements,dates,columnContents,IncomeStatement::setReconciledDepreciation);
//                    break;
//                case "EBITDA":
//                    updateStatements(statements,dates,columnContents,IncomeStatement::setEBITDA);
//                    break;
//                case "Net Non Operating Interest Income Expense":
//                    updateStatements(statements, dates, columnContents, IncomeStatement::setNetNonOperatingInterestLoss);
//                    break;
//                case "Other Income Expense":
//                    updateStatements(statements, dates, columnContents, IncomeStatement::setOtherIncomeExpenses);
//                    break;
//                case "Other Non Operating Income Expenses":
//                    updateStatements(statements, dates, columnContents, IncomeStatement::setOtherIncome);
//                    break;
//                case "Net Income From Continuing Operation Net Minority Interest":
//                    updateStatements(statements, dates, columnContents, IncomeStatement::setNetIncomeFromContinuingOperations);
//                    break;
//                case "Net Income Common Stockholders":
//                    updateStatements(statements, dates, columnContents, IncomeStatement::setNetIncomeCommonStockholders);
//                    break;
//                case "Basic Average Shares":
//                    updateStatements(statements, dates, columnContents, IncomeStatement::setBasicAverageShares);
//                    break;
//                case "Diluted Average Shares":
//                    updateStatements(statements, dates, columnContents, IncomeStatement::setDilutedAverageShares);
//                    break;
//                case "Total Depreciation And Amortization":
//                    updateStatements(statements, dates, columnContents, IncomeStatement::setTotalDepreciation);
//                    break;
//                case "Reconciled Cost of Revenue":
//                    updateStatements(statements, dates, columnContents, IncomeStatement::setReconciledCostOfRevenue);
//                    break;
//                case "Interest Expense":
//                    updateStatements(statements, dates, columnContents, IncomeStatement::setTotalInterestExpense);
//                    break;
//                case "Net Interest Income":
//                    updateStatements(statements, dates, columnContents, IncomeStatement::setNetInterestIncome);
//                    break;
//                case "Total Unusual Items":
//                    updateStatements(statements, dates, columnContents, IncomeStatement::setTotalUnusualItems);
//                    break;
//                case "Normalized EBITDA":
//                    updateStatements(statements, dates, columnContents, IncomeStatement::setNormalizedEBITDA);
//                    break;
//                default:
//                    break;
//            }
//        }
//
//        ticker.setIncomeStatements(statements);
//
//        //Document auf standard zurücksetzen:
//        getDocument("https://finance.yahoo.com/quote/" + ticker.getTicker());
//    }

    // ToDo: need to find a way to integrate scrapeCashFlow, scrapeBalanceSheet & scrapeIncomeStatement, as they mostly rely on the same code.
    public void scrapeIncomeStatements(Period p){
        IncomeStatement incomeStatement = new IncomeStatement(ticker,p);
        TreeMap<LocalDate, IncomeStatement> incomeStatements = new TreeMap<>();

        try {
            JsonNode results = getJSON(incomeStatement.getUrl()).path("timeseries").path("result");

            // Scrapes the Dates from the first Entry!
            List<LocalDate> localDates = new ArrayList<>();

            // Makes sure, that there are always 4-cashflow dates available
            for(int i=0; i< results.size(); i++) {
                String type = results.get(i).path("meta").path("type").get(0).asText();
                // checks weather four entries exist & they aren't null. (I know this is horrible practice...)
                if(results.get(i).path(type).size()<4
                        || results.get(i).path(type).get(0).isNull()
                        || results.get(i).path(type).get(1).isNull()
                        || results.get(i).path(type).get(2).isNull()
                        || results.get(i).path(type).get(3).isNull()
                )continue;

                for (JsonNode entry: results.get(i).path(type)) {
                    localDates.add(parser.toLocalDate(entry.path("asOfDate").asText()));
                }
                break;
            }

            // Creates a new BalanceSheet for every Date (4x).
            for(LocalDate d : localDates){
                incomeStatements.put(d,new IncomeStatement(ticker,d,p));
            }

            // Scrapes the actual fin-Data & puts it into the corresponding balance-sheet.
            for (JsonNode node : results) {
                String type = node.path("meta").path("type").get(0).asText();
                if(type.contains("trailing"))continue; // skips all the trailing entries, only uses annual.

                for (JsonNode entry : node.path(type)) {
                    if(entry.isNull())continue;
                    if(entry.size()<4)continue; //Only accesses entries that have values for all four-dates

                    LocalDate currDate = parser.toLocalDate(entry.path("asOfDate").asText());
                    IncomeStatement currSheet = incomeStatements.get(currDate);

                    String fieldName = Character.toLowerCase(
                            type.replace(p.getPeriod(),"")
                                    .charAt(0)
                    ) +
                            type.replace(p.getPeriod(),"")
                                    .substring(1);
                    // Workaround for fields like EBITDA or EBIT, which need to be capitalized.
                    if(fieldName.length()<=6){
                        fieldName = Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
                    }


                    // Dynamic Field-finding with reflections (never used those before...)
                    try {
                        java.lang.reflect.Field field = IncomeStatement.class.getDeclaredField(fieldName);
                        field.setAccessible(true);
                        field.setDouble(currSheet, entry.path("reportedValue").path("raw").asDouble());
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        debugPrinter.print(this,e.getMessage());
                    }
                }
            }

            ticker.setIncomeStatements(incomeStatements);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void scrapeBalanceSheet(Period p){
        BalanceSheet tmpSheet = new BalanceSheet(ticker,p);
        TreeMap<LocalDate, BalanceSheet> balanceSheets = new TreeMap<>();

        try {
            JsonNode results = getJSON(tmpSheet.getUrl()).path("timeseries").path("result");

            // Scrapes the Dates from the first Entry!
            List<LocalDate> localDates = new ArrayList<>();

            // Makes sure, that there are always 4-cashflow dates available
            for(int i=0; i< results.size(); i++) {
                String type = results.get(i).path("meta").path("type").get(0).asText();
                // checks weather four entries exist & they aren't null. (I know this is horrible practice...)
                if(results.get(i).path(type).size()<4
                        || results.get(i).path(type).get(0).isNull()
                        || results.get(i).path(type).get(1).isNull()
                        || results.get(i).path(type).get(2).isNull()
                        || results.get(i).path(type).get(3).isNull()
                )continue;

                for (JsonNode entry: results.get(i).path(type)) {
                    localDates.add(parser.toLocalDate(entry.path("asOfDate").asText()));
                }
                break;
            }

            // Creates a new BalanceSheet for every Date (4x).
            for(LocalDate d : localDates){
                balanceSheets.put(d,new BalanceSheet(ticker,d,p));
            }

            // Scrapes the actual fin-Data & puts it into the corresponding balance-sheet.
            for (JsonNode node : results) {
                String type = node.path("meta").path("type").get(0).asText();
                if(type.contains("trailing"))continue; // skips all the trailing entries, only uses annual.

                for (JsonNode entry : node.path(type)) {
                    if(entry.isNull())continue;
                    if(entry.size()<4)continue; //Only accesses entries that have values for all four-dates

                    LocalDate currDate = parser.toLocalDate(entry.path("asOfDate").asText());
                    BalanceSheet currSheet = balanceSheets.get(currDate);

                    String fieldName = Character.toLowerCase(
                            type.replace(p.getPeriod(),"")
                                    .charAt(0)
                    ) +
                            type.replace(p.getPeriod(),"")
                                    .substring(1);

                    // Dynamic Field-finding with reflections (never used those before...)
                    try {
                        java.lang.reflect.Field field = BalanceSheet.class.getDeclaredField(fieldName);
                        field.setAccessible(true);
                        field.setDouble(currSheet, entry.path("reportedValue").path("raw").asDouble());
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        debugPrinter.print(this,e.getMessage());
                    }
                }
            }

            ticker.setBalanceSheets(balanceSheets);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void scrapeCashflow(Period p){
        CashFlow tmpCash = new CashFlow(ticker,p);
        TreeMap<LocalDate, CashFlow> cashFlows = new TreeMap<>();

        try {
            JsonNode results = getJSON(tmpCash.getUrl()).path("timeseries").path("result");

            // Scrapes the Dates from the first Entry!
            List<LocalDate> localDates = new ArrayList<>();

            // Makes sure, that there are always 4-cashflow dates available
            for(int i=0; i< results.size(); i++) {
                String type = results.get(i).path("meta").path("type").get(0).asText();
                // checks weather four entries exist & they aren't null. (I know this is horrible practice...)
                if(results.get(i).path(type).size()<4
                        || results.get(i).path(type).get(0).isNull()
                        || results.get(i).path(type).get(1).isNull()
                        || results.get(i).path(type).get(2).isNull()
                        || results.get(i).path(type).get(3).isNull()
                )continue;

                for (JsonNode entry: results.get(i).path(type)) {
                    localDates.add(parser.toLocalDate(entry.path("asOfDate").asText()));
                }
                break;
            }

            // Creates a new BalanceSheet for every Date (4x).
            for(LocalDate d : localDates){
                cashFlows.put(d,new CashFlow(ticker,d,p));
            }

            // Scrapes the actual fin-Data & puts it into the corresponding balance-sheet.
            for (JsonNode node : results) {
                String type = node.path("meta").path("type").get(0).asText();
                if(type.contains("trailing"))continue; // skips all the trailing entries, only uses annual.

                for (JsonNode entry : node.path(type)) {
                    if(entry.isNull())continue;
                    if(entry.size()<4)continue; //Only accesses entries that have values for all four-dates

                    LocalDate currDate = parser.toLocalDate(entry.path("asOfDate").asText());
                    CashFlow currSheet = cashFlows.get(currDate);

                    String fieldName = Character.toLowerCase(
                            type.replace(p.getPeriod(),"")
                                    .charAt(0)
                    ) +
                            type.replace(p.getPeriod(),"")
                                    .substring(1);

                    // Dynamic Field-finding with reflections (never used those before...)
                    try {
                        java.lang.reflect.Field field = CashFlow.class.getDeclaredField(fieldName);
                        field.setAccessible(true);
                        field.setDouble(currSheet, entry.path("reportedValue").path("raw").asDouble());
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        debugPrinter.print(this,e.getMessage());
                    }
                }
            }

            ticker.setCashFlows(cashFlows);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DebugPrinter getDebugPrinter(){
        return this.debugPrinter;
    }
}
