package de.bustinjieber.main.scraper;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class Parser {

    /**
     * Converts a String to a Double, while checking for a valid input (-- isnt one!)
     * @param s String
     * @return Double
     */
    public Double parseAmount(String s){
        if(s.equals("--"))return 0d;
        return Double.parseDouble(
                s.replace(",","").trim()
        );
    }

    public Double[] parseAmountArray(String s){
        if(s.equals("--"))return new Double[]{0d,0d};
        return new Double[]{
                Double.parseDouble(
                        s.split("-")[0].replace(",","")
                                .trim()
                ),
                Double.parseDouble(
                        s.split("-")[1].replace(",","")
                                .trim()
                )
        };
    }

    public Double parsePercentage(String s){
        if(s.equals("--"))return 0d;
        if(s.contains("(") || s.contains(")")) {
            return Double.valueOf(
                    s
                            .replace("%", "")
                            .replace("(", "")
                            .replace(")", "")
                            .trim()
            );
        }else {
            return Double.parseDouble(
                    s
                            .replace("%","")
                            .replace(",",""));
        }
    }

    /**
     * Converts the DateString given by Hist-Data into a Date-Obj. (using American.Central Time)
     * @param s String (ex: "Dec 10, 2002") that needs to be converted into a Date.
     * @return LocalDate in correct format (ex: "2002-12-10").
     */
    public Date convertStrToDate(String s){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, uuuu", Locale.ENGLISH);
        LocalDate localDate = LocalDate.parse(s, formatter);
        return Date.from(localDate.atStartOfDay(ZoneId.of("America/Chicago")).toInstant());
    }

    /**
     * Vibecoded Method for converting string to Util.Date - in the format (M/D/YYYY).
     * @param s Date in String-form.
     * @return a Util.Date Obj.
     */
    public Date toUtilDate(String s) {
        ZoneId zone = ZoneId.of("America/Chicago");
        if (s.contains("TTM")) {
            LocalDate ld = LocalDate.now(zone);
            return Date.from(ld.atStartOfDay(zone).toInstant());
        }
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("M/d/yyyy");
        LocalDate ld = LocalDate.parse(s, fmt);
        return Date.from(ld.atStartOfDay(zone).toInstant());
    }
}
