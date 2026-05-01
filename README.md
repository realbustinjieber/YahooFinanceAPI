# YahooFinanceAPi v1.0

A high-performance Java library designed to extract real-time and historical financial data from Yahoo Finance.

## Features
- **Hybrid Extraction:** Combines **Playwright** for robust session/cookie handling and **Jsoup** for lightning-fast HTML parsing.
- **Smart Data Structures:** Uses `TreeMap<Date, Float>` to ensure all historical records are automatically sorted chronologically.
- **Flexible Intervals:** Supports Daily, Weekly, and Monthly data frequencies.
- **Market Ready:** Hardcoded for US Market standards (Locale.ENGLISH) and precise time-zoning (America/Chicago).
- **Statistics Support:** Extract Price, Change, Volume, Day's Range, 52-Week Range, Market Cap, and Beta.

## Installation
Ensure you have the following dependencies in your `pom.xml`:
- `com.microsoft.playwright:playwright`
- `org.jsoup:jsoup`
- `org.knowm.xchart:xchart` (for visualization)

## Quick Start Example
This example demonstrates how to initialize a ticker, fetch historical data, and print basic stats.

```java
import de.bustinjieber.main.yahoo.Ticker;
import de.bustinjieber.main.yahoo.Frequency;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        // 1. Initialize Ticker with a Symbol
        Ticker ticker = new Ticker("UPS");

        // 2. Fetch Historical Data (e.g., Monthly)
        // This triggers the hybrid Playwright/Jsoup engine
        ticker.getHistoricals(Frequency.Monthly);

        // 3. Access Real-Time Stats
        System.out.println("Current Price: " + ticker.getPrice());
        System.out.println("Market Cap: " + ticker.getMarketCap());

        // 4. Access Historical Data (automatically sorted)
        ticker.getHistoricalClose().forEach((date, price) -> {
            System.out.println(date + " - Close: $" + price);
        });
    }
}
```

## How it Works
1. **Playwright Phase:** The engine launches a headless Chromium instance to navigate to Yahoo and accept cookie consent dialogs.
2. **Session Transfer:** Extracted session cookies are passed to Jsoup.
3. **Jsoup Phase:** The engine performs a direct GET request using the validated session to pull historical tables via optimized CSS selectors.

## Roadmap
- [ ] Add fundamental statistics (P/E Ratio, EPS, Dividends).
- [ ] Implement asynchronous multi-ticker scraping.
- [ ] Add CSV/JSON export functionality.

## License
Initial Release v1.0 - Developed by bustinjieber
