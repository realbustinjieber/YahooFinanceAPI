package de.bustinjieber.main.yahoo.utils;

/**
 * Converts human-readable intervals into less readable intervals, used for the historical-url (Daily, Weekly, Monthly).
 */
public enum Period {
    Annually("annual"),
    Quarterly("quarterly");

    private final String period;

    Period(String i) {
        this.period = i;
    }

    public String getPeriod(){
        return this.period;
    }
}
