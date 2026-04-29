package de.bustinjieber.main;

import de.bustinjieber.main.yahoo.Ticker;

import java.io.IOException;

public class Main {

    // Beispiel: https://finance.yahoo.com/quote/AAPL
    public static void main(String[] args) throws IOException {
        Ticker t1 = new Ticker("BBAI");
        System.out.println(t1.toString());
    }
}
