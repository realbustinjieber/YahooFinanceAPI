package de.bustinjieber.main;

import de.bustinjieber.main.yahoo.Frequency;
import de.bustinjieber.main.yahoo.Ticker;

public class Main {

    public static void main(String[] args) {
        Ticker t1 = new Ticker("UPS");

        t1.getScraper().scrapeHistoricalsMax(Frequency.Monthly);
    }
}
