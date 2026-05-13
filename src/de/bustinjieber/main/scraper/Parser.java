package de.bustinjieber.main.scraper;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
     * (VibeCoded): Returns the Unix timestamp (seconds since 1970-01-01T00:00:00Z) for the end of the next year
     * at 23:59:59 in UTC.
     *
     * Example: if the current year is 2026, this returns the epoch seconds for 2027-12-31T23:59:59Z.
     *
     * @return epoch seconds for next year's 31 December 23:59:59 UTC
     */
    public long endOfNextYearUnix() {
        int nextYear = ZonedDateTime.now(ZoneOffset.UTC).getYear() + 1;
        ZonedDateTime endOfNextYear = LocalDateTime.of(nextYear, 12, 31, 23, 59, 59).atZone(ZoneOffset.UTC);
        return endOfNextYear.toEpochSecond();
    }

    public LocalDate toLocalDate(String s){
        return LocalDate.parse(s,DateTimeFormatter.ISO_LOCAL_DATE);
    }
}
