package de.bustinjieber.main;

import de.bustinjieber.main.yahoo.Ticker;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class Main {

    public Float calcMarketCap(String mC){
        if(mC.contains("B")){
            return Float.parseFloat(mC.replace("B", "")) * 1000000000000f;
        }
        return 0f;
    }

    // Beispiel: https://finance.yahoo.com/quote/AAPL
    public static void main(String[] args) throws IOException {
        String ticker = "UPS"; // anpassen
        String url = "https://finance.yahoo.com/quote/" + ticker;

        // Setze user-agent und optional Timeout
        Document doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 "
                        + "(KHTML, like Gecko) Chrome/115.0 Safari/537.36")
                .timeout(15_000)
                .get();

        Element priceElem = doc.selectFirst("[data-testid=qsp-price]");
        Element changeElem = doc.selectFirst("[data-testid=qsp-price-change]");
        Element changePctElem = doc.selectFirst("[data-testid=qsp-price-change-percent]");
        Element quoteElem = doc.selectFirst("[data-testid=quote-title]");
        Element prevCloseElem = doc.selectFirst("[data-field=regularMarketPreviousClose]");
        Element openElem = doc.selectFirst("[data-field=regularMarketOpen]");
        Element daysRangeElem = doc.selectFirst("[data-field=regularMarketDayRange]");
        Element volumeElem = doc.selectFirst("[data-field=regularMarketVolume]");
        Element avgVolumeElem = doc.selectFirst("[data-field=averageVolume]");
        Element fiftyTwoWeekRangeElem = doc.selectFirst("[data-field=fiftyTwoWeekRange]");
        Element marketCapElem = doc.selectFirst("[data-field=marketCap]");

        Ticker t1 = new Ticker(
                quoteElem.text().split("\\(")[1].replace("(","").replace(")",""),
                quoteElem.text().split("\\(")[0],
                Float.parseFloat(priceElem.text().replace(",", "")),
                Float.parseFloat(changeElem.text()),
                Float.parseFloat(changePctElem.text().replace("%","").replace("(","").replace(")",""))
        );

        t1.setPrevClose(Float.parseFloat(prevCloseElem.text().replace(",","")));
        t1.setOpen(Float.parseFloat(openElem.text().replace(",","")));
        t1.setDaysRange(new Float[]{
                Float.parseFloat(daysRangeElem.text().split("-")[0].replace(",", "")),
                Float.parseFloat(daysRangeElem.text().split("-")[1].replace(",", ""))
        });
        t1.setVolume(Long.parseLong(volumeElem.text().replace(",","")));
        t1.setAvgVolume(Float.parseFloat(avgVolumeElem.text().replace(",","")));
        t1.setFiftyTwoWeekRange(new Float[]{
                Float.parseFloat(fiftyTwoWeekRangeElem.text().split("-")[0].replace(",", "")),
                Float.parseFloat(fiftyTwoWeekRangeElem.text().split("-")[1].replace(",", ""))
        });
        Main m = new Main();
        t1.setMarketCap(m.calcMarketCap(marketCapElem.text().replace(",","")));


        System.out.println(t1.toString());
//
//        String price = priceElem != null ? priceElem.text() : "n/a";
//        String change = changeElem != null ? changeElem.text() : "n/a";
//        String changePct = changePctElem != null ? changePctElem.text() : "n/a";
//
//        System.out.println("Ticker: " + ticker);
//        System.out.println("Title: " + quoteElem.text().split("\\(")[0]);
//        System.out.println("Preis: " + price);
//        System.out.println("Änderung: " + change + " (" + changePct + ")");
    }
}
