package de.bustinjieber.main;

import de.bustinjieber.main.yahoo.Ticker;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Main {
    // Beispiel: https://finance.yahoo.com/quote/AAPL
    public static void main(String[] args) throws IOException {
        String ticker = "^GSPC"; // anpassen
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

        System.out.println();

        Ticker t1 = new Ticker(
                quoteElem.text().split("\\(")[1].replace("(","").replace(")",""),
                quoteElem.text().split("\\(")[0],
                Float.parseFloat(priceElem.text().replace(",", "")),
                Float.parseFloat(changeElem.text()),
                Float.parseFloat(changePctElem.text().replace("%","").replace("(","").replace(")",""))
        );

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
