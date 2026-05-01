package de.bustinjieber.main.yahoo;

/**
 * Converts human-readable intervals into less readable intervals, used for the historical-url.
 */
public enum Frequency {
    Daily("1d"),
    Weekly("1wk"),
    Monthly("1mo");

    private final String frequency;

    Frequency(String i) {
        this.frequency = i;
    }

    public String getFrequency(){
        return this.frequency;
    }
}
