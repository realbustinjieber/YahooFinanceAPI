package de.bustinjieber.main;

import com.microsoft.playwright.*;
import java.util.ArrayList;
import java.util.List;

public class YahooPlaywright {
    public static void main(String[] args) {
        try (Playwright playwright = Playwright.create()) {
            // Headless auf true, da wir keine UI-Interaktion mehr brauchen
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
            Page page = browser.newPage();

            // Wir setzen period1=0 (entspricht MAX) und interval=1d (täglich)
            // Das umgeht das komplette Kalender-Menü!
            String symbol = "UPS";
            String url = "https://finance.yahoo.com/quote/" + symbol + "/history/?period1=0&period2=9999999999&interval=1d";

            System.out.println("Navigiere direkt zur MAX-Ansicht...");
            page.navigate(url);

            // 1. Cookie Banner (muss trotzdem weg, da er oft das HTML überlagert)
            try {
                page.click("button[name='agree']", new Page.ClickOptions().setTimeout(5000));
            } catch (Exception e) {
                // Banner nicht da oder bereits weg
            }

            // 2. Warten, bis die Tabelle da ist
            System.out.println("Extrahiere Daten...");
            page.waitForSelector("table.table");

            // 3. Daten hocheffizient auslesen
            System.out.println("Extrahiere Daten via JavaScript-Injektion...");

// Wir lassen den Browser alle Zeilentexte in einem Rutsch sammeln.
// Das verhindert Timeouts bei einzelnen (vielleicht unsichtbaren) Zeilen.
            Object result = page.evaluate("() => {" +
                    "  const rows = Array.from(document.querySelectorAll('table.table tbody tr'));" +
                    "  return rows.map(row => row.innerText.trim()).filter(text => text.length > 0);" +
                    "}");

            List<String> dataList = (List<String>) result;

// 4. Browser schließen
            browser.close();
            System.out.println("Browser geschlossen.");

// 5. Ergebnisse ausgeben
            for (String line : dataList) {
                // Tabulatoren durch Trenner ersetzen für die Konsole
                System.out.println(line.replace("\n", " | ").replace("\t", " | "));
            }
            System.out.println("Fertig! Datensätze gefunden: " + dataList.size());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}