package de.bustinjieber.main.scraper;

/**
 * Util-class for printing Debug-Messages (if activated in Scraper-constructor).
 */
public class DebugPrinter {

    private boolean debugging = false;

    public DebugPrinter(boolean d){
        this.debugging = d;
    }

    public void print(Object o, String s){
        if(!debugging) return;
        System.out.println("[" + o.getClass() + "]: " + s);
    }

}
